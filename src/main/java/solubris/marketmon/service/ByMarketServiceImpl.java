package solubris.marketmon.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import solubris.marketmon.domain.Event;
import solubris.marketmon.domain.EventType;
import solubris.marketmon.domain.Market;
import solubris.marketmon.domain.MarketState;
import solubris.marketmon.domain.MarketStatusTime;
import solubris.marketmon.domain.PriceSize;
import solubris.marketmon.domain.Runner;
import solubris.marketmon.domain.types.MarketStatus;
import solubris.marketmon.json.ByMarket;
import solubris.marketmon.requestor.HttpRequestor;
import solubris.marketmon.requestor.MockRequestor;
import solubris.marketmon.requestor.Requestor;
import solubris.marketmon.requestor.RequestorException;
import solubris.mon4roo.core.Monitor;

@Service
@Transactional
public class ByMarketServiceImpl implements ByMarketService {
	static private final Logger logger = LoggerFactory.getLogger(ByMarketService.class);
//	private boolean addBestPriceOnly=false;
	private static final String urlFormat = "http://uk-api.betfair.com/www/sports/exchange/readonly/v1.0/bymarket?alt=json&currencyCode=GBP&locale=en_GB&types=EVENT,RUNNER_EXCHANGE_PRICES_BEST,MARKET_STATE,MARKET_DESCRIPTION,RUNNER_DESCRIPTION,RUNNER_STATE&marketIds=%s";

	@Autowired
	@Qualifier("HttpRequestor")
	Requestor requestor;

	@Autowired
	ByNodeService byNodeService;

	@Monitor
	@Override
	@Scheduled(fixedRate=5000)
	@Transactional
	public void run() throws IOException, RequestorException {
		List<Market> markets=Market.findMarketsByStatusOrStatus(MarketStatus.OPEN, MarketStatus.SUSPENDED);
		bymarketRequest(markets, true);
    }

	@Override
	public void bymarketRequest(List<Market> markets, boolean addBestPriceOnly) throws IOException, RequestorException {
		for(Market market:markets) {
			bymarketRequest(market, addBestPriceOnly);
		}
	}
	
	@Override
	public void bymarketRequest(Market market, boolean addBestPriceOnly) throws IOException, RequestorException {
		String url=String.format(urlFormat, market.getMarketId());
		String json = requestor.get(url);

		ByMarket bm = ByMarket.fromJsonToByMarket(json);
		List<EventType> eventTypeList = bm.getEventTypes();
		if(eventTypeList.size()==0) {
			logger.warn("no eventTypes returned from API, check marketId {}", market.getMarketId());
			if(market.getState()==null) {
				market.setState(new MarketState());
			}
			// change market status so its no longer query for
			market.getState().setStatus(MarketStatus.NOT_FOUND);
			return;
		}
		if(addBestPriceOnly) {
			bm.useBestPriceOnly();
		}
    	bm.inverseMapping();
		EventType eventTypeJson=eventTypeList.get(0);

		Market marketJson=eventTypeJson.getEventNodes().get(0).getMarketNodes().get(0);
//		markets.setRunners(marketJson.getRunners());
		List<Runner> runners=new ArrayList<Runner>();
		for(Runner runnerJson:marketJson.getRunners()) {
			Runner runner=findRunnerInMarket(market, runnerJson.getSelectionId());
			if(runner==null) {
				logger.warn("runner not found, using json {}", runnerJson.getSelectionId());
				// runner could exist in another market
				if(Runner.findRunner(runnerJson.getSelectionId())==null) {
					// this is new runner, so save it to db
					runnerJson.linkPricesToMarket(market);
					runnerJson.persist();
					runners.add(runnerJson);
				} else {
					// this runner is new for this market, but exists in another market
					Runner runnerOtherMarket=Runner.findRunner(runnerJson.getSelectionId());
					runnerOtherMarket.addPricesForBack(runnerJson.getExchange().getAvailableToBack(), market);
					runnerOtherMarket.addPricesForLay(runnerJson.getExchange().getAvailableToLay(), market);
					runnerOtherMarket.setDescription(runnerJson.getDescription());
					runnerOtherMarket.setHandicap(runnerJson.getHandicap());
					runnerOtherMarket.setState(runnerJson.getState());
					runners.add(runnerOtherMarket);
				}
			} else {
				logger.warn("runner found, using from db {}", runner.getSelectionId());
				runner.addPricesForBack(runnerJson.getExchange().getAvailableToBack(), market);
				runner.addPricesForLay(runnerJson.getExchange().getAvailableToLay(), market);
				runner.setDescription(runnerJson.getDescription());
				runner.setHandicap(runnerJson.getHandicap());
				runner.setState(runnerJson.getState());
				runners.add(runner);
			}
		}

		// create event if it doesn't already exist for the market
		if(market.getEvent()==null) {
			Event eventJson=eventTypeJson.getEventNodes().get(0);
			Event event=Event.findEvent(eventJson.getEventId());
			if(event==null) {
				List<Market> markets=new ArrayList<Market>();
				markets.add(market);
				eventJson.setMarketNodes(markets);
				eventJson.persist();
				market.setEvent(eventJson);
			} else {
				market.setEvent(event);
			}
		}

		// market must have event by now, but does it have an event type?
		if(market.getEvent().getEventType()==null){
			EventType eventType=EventType.findEventType(eventTypeJson.getEventTypeId());
			if(eventType==null) {
				List<Event> events=new ArrayList<Event>();
				events.add(market.getEvent());
				eventTypeJson.setEventNodes(events);
				eventTypeJson.persist();
				market.getEvent().setEventType(eventTypeJson);
				market.getEvent().merge();
				byNodeService.bynodeRequest(eventTypeJson);
			} else {
				market.getEvent().setEventType(eventType);
				market.getEvent().merge();
			}
		}

		// set suspendTime if market suspended (or unsuspended)
		if(market.getState()==null || marketJson.getState().getStatus() != market.getState().getStatus()) {
			MarketStatusTime marketStatusTime=new MarketStatusTime();
			marketStatusTime.setMarket(market);
			marketStatusTime.setStatus(marketJson.getState().getStatus());
			marketStatusTime.persist();
			logger.warn("setting marketStateTime for status {}", marketJson.getState().getStatus());
		}

		// set inplayTime if market has gone inplay since last check
		if(market.getState()==null || marketJson.getState().getInplay() != market.getState().getInplay()) {
			MarketStatusTime marketStatusTime=new MarketStatusTime();
			marketStatusTime.setMarket(market);
			marketStatusTime.setInplay(marketJson.getState().getInplay());
			marketStatusTime.persist();
			logger.warn("setting marketStateTime for status {}", marketJson.getState().getInplay());
//			market.setInplayTime(new Date());
//			logger.warn("setting inplayTime={}", market.getInplayTime());
		}
		// copy data from json to db market
		market.setDescription(marketJson.getDescription());
		market.setMarketDataDelayed(marketJson.getMarketDataDelayed());
		market.setState(marketJson.getState());
		market.setRunners(runners);
		market.merge();
	}

	private Runner findRunnerInMarket(Market market, Long selectionId) {
		for(Runner runner:market.getRunners()) {
			logger.debug("comparing runner in market {} with selectionId {}", runner.getSelectionId(), selectionId);
			if(runner.getSelectionId().equals(selectionId)) {
				return runner;
			}
		}
		return null;
	}

	@Override
	public void setRequestor(MockRequestor mockRequestor) {
		requestor=mockRequestor;
	}

}
