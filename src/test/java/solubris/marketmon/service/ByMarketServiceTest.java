package solubris.marketmon.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import solubris.marketmon.ITestBase;
import solubris.marketmon.domain.EventType;
import solubris.marketmon.domain.Market;
import solubris.marketmon.domain.PriceSize;
import solubris.marketmon.domain.PriceSizeForBack;
import solubris.marketmon.domain.PriceSizeForLay;
import solubris.marketmon.domain.Runner;
import solubris.marketmon.domain.types.MarketStatus;
import solubris.marketmon.json.ByMarket;
import solubris.marketmon.requestor.HttpRequestor;
import solubris.marketmon.requestor.MockRequestor;
import solubris.marketmon.requestor.Requestor;
import solubris.marketmon.requestor.RequestorException;
import solubris.marketmon.service.ByMarketService;

//needed to force ROO not to generate another annotation which overrides definition in super class
@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext-test-override.xml" })
public class ByMarketServiceTest extends ITestBase {
    @PersistenceContext
    transient EntityManager entityManager;

	@Autowired
	ByMarketService byMarketService;

	@Test
	public void bymarketRequest() throws IOException, RequestorException {
		Market market;
		market=new Market();
		market.setMarketId("1.104802827");
		market.persist();
		byMarketService.bymarketRequest(market, false);
		market.flush();
		entityManager.clear();
		long runnerCount1 = Runner.countRunners();
		
		market=Market.findMarket(104802827L);
		byMarketService.bymarketRequest(market, false);
		long runnerCount2 = Runner.countRunners();
		/////////// assert market \\\\\\\\\
		assertEquals("1.104802827", market.getMarketId());

		assertEquals(runnerCount1, runnerCount2);
//		assertEquals(96, PriceSizeForBack.countPriceSizeForBacks() + PriceSizeForLay.countPriceSizeForLays());
	}

	@Test
	public void bymarketRequestNotFound() throws IOException, RequestorException {
		Market market;
		market=new Market();
		market.setMarketId("1.9999999");
		market.persist();
		byMarketService.bymarketRequest(market, false);
		market.flush();
		entityManager.clear();
		
		/////////// assert market \\\\\\\\\
		assertEquals("1.9999999", market.getMarketId());
		assertEquals(MarketStatus.NOT_FOUND, market.getState().getStatus());
	}

	@Test
	public void bymarketRequestClosed() throws IOException, RequestorException {
		Market market;
		market=new Market();
		market.setMarketId("1.104910123");
		market.persist();
		MockRequestor mockRequestor=new MockRequestor();
		String json = readFileContents("/bymarket-closed.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market, true);

		/////////// assert market \\\\\\\\\
		assertEquals("1.104910123", market.getMarketId());
		assertEquals(MarketStatus.CLOSED, market.getState().getStatus());
		assertEquals(3, Runner.countRunners());
	}

	@Test
	public void bymarketRequestBestPriceOnly() throws IOException, RequestorException {
		Market market;
		market=new Market();
		market.setMarketId("1.104802827");
		market.persist();
		byMarketService.bymarketRequest(market, true);
		market.flush();
		entityManager.clear();
		long runnerCount1 = Runner.countRunners();

		market=Market.findMarket(104802827L);
		byMarketService.bymarketRequest(market, true);
		long runnerCount2 = Runner.countRunners();
		/////////// assert market \\\\\\\\\
		assertEquals("1.104802827", market.getMarketId());

		assertEquals(runnerCount1, runnerCount2);
//		assertEquals(34, PriceSizeForBack.countPriceSizeForBacks() + PriceSizeForLay.countPriceSizeForLays());
//		assertEquals(94, PriceSizeForBack.countPriceSizeForBacks());
//		assertEquals(94, PriceSizeForLay.countPriceSizeForLays());
	}

	@Test
	public void bymarketRequestGoingInPlay() throws IOException, RequestorException {
		Market market;
		market=new Market();
		market.setMarketId("1.104802827");
		market.persist();
		MockRequestor mockRequestor=new MockRequestor();
		String json = readFileContents("/bymarket.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market, true);
		market.flush();
		entityManager.clear();

		market=Market.findMarket(104802827L);
		json = readFileContents("/bymarket-inplay.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market, true);
		/////////// assert market \\\\\\\\\
		assertEquals("1.104802827", market.getMarketId());
		// inplay-on is 3rd item, 1st=open, 2nd=inplay-off
		assertTrue("inplay should be true", market.getStatusTimes().get(2).getInplay());

		assertEquals(9, Runner.countRunners());
		assertEquals(34, PriceSizeForBack.countPriceSizeForBacks() + PriceSizeForLay.countPriceSizeForLays());
//		assertEquals(94, PriceSizeForBack.countPriceSizeForBacks());
//		assertEquals(94, PriceSizeForLay.countPriceSizeForLays());
	}

	@Test
	public void bymarketRequest2MarketsOnEvent() throws IOException, RequestorException {
		Market market1;
		String json;
		MockRequestor mockRequestor;
		market1=new Market();
		market1.setMarketId("1.104982218");
		market1.persist();
		mockRequestor=new MockRequestor();
		json = readFileContents("/bymarket-matchodds.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market1, true);

		Market market2=new Market();
		market2.setMarketId("1.104982219");
		market2.persist();
		mockRequestor=new MockRequestor();
		json = readFileContents("/bymarket-setbetting.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market2, true);
		/////////// assert market \\\\\\\\\
		assertEquals("1.104982218", market1.getMarketId());
		assertEquals("1.104982219", market2.getMarketId());
//		assertNotNull("inplay time should not be null", market1.getInplayTime());
		assertEquals(market1.getEvent().getEventId(), market2.getEvent().getEventId());

//		assertEquals(9, Runner.countRunners());
//		assertEquals(34, PriceSizeForBack.countPriceSizeForBacks() + PriceSizeForLay.countPriceSizeForLays());
	}

	@Test
	public void bymarketRequestSharedRunnersBetween3Markets() throws IOException, RequestorException {
		Market market1;
		String json;
		MockRequestor mockRequestor;
		market1=new Market();
		market1.setMarketId("1.104969629");
		market1.persist();
		mockRequestor=new MockRequestor();
		json = readFileContents("/bymarket-football-nextgoal.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market1, true);

		Market market2=new Market();
		market2.setMarketId("1.104969642");
		market2.persist();
		mockRequestor=new MockRequestor();
		json = readFileContents("/bymarket-football-halftime.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market2, true);

		Market market3=new Market();
		market3.setMarketId("1.104969651");
		market3.persist();
		mockRequestor=new MockRequestor();
		json = readFileContents("/bymarket-football-matchodds.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market3, true);

		/////////// assert market \\\\\\\\\
		assertEquals("1.104969629", market1.getMarketId());
		assertEquals("1.104969642", market2.getMarketId());
		assertEquals("1.104969651", market3.getMarketId());
//		assertNotNull("inplay time should not be null", market1.getInplayTime());
		assertEquals(market1.getEvent().getEventId(), market2.getEvent().getEventId());
		assertEquals(market1.getEvent().getEventId(), market3.getEvent().getEventId());
		assertEquals(market1.getRunners().size(), market2.getRunners().size());
		assertEquals(market1.getRunners().size(), market3.getRunners().size());

		// next goal and match odds share runners, halftime has different runners
		assertEquals(market1.getRunners().get(0), market3.getRunners().get(0));
		assertEquals(market1.getRunners().get(1), market3.getRunners().get(1));
		assertFalse(market1.getRunners().get(2).equals(market3.getRunners().get(2)));

//		assertEquals(9, Runner.countRunners());
//		assertEquals(34, PriceSizeForBack.countPriceSizeForBacks() + PriceSizeForLay.countPriceSizeForLays());
	}

	@Test
	public void bymarketRequestTwoEvents_OneEventType() throws IOException, RequestorException {
		Market market1;
		String json;
		MockRequestor mockRequestor;
		market1=new Market();
		market1.setMarketId("1.104981858");
		market1.persist();
		mockRequestor=new MockRequestor();
		json = readFileContents("/bymarket-tennis1.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market1, true);

		Market market2=new Market();
		market2.setMarketId("1.104981885");
		market2.persist();
		mockRequestor=new MockRequestor();
		json = readFileContents("/bymarket-tennis2.json");
		mockRequestor.setMockResponse(json);
		byMarketService.setRequestor(mockRequestor);
		byMarketService.bymarketRequest(market2, true);
		/////////// assert market \\\\\\\\\
		assertEquals("1.104981858", market1.getMarketId());
		assertEquals("1.104981885", market2.getMarketId());
//		assertNotNull("inplay time should not be null", market1.getInplayTime());
		assertFalse(market1.getEvent().getEventId()==market2.getEvent().getEventId());
		assertEquals(market1.getEvent().getEventType().getEventTypeId(), market2.getEvent().getEventType().getEventTypeId());

//		assertEquals(9, Runner.countRunners());
//		assertEquals(34, PriceSizeForBack.countPriceSizeForBacks() + PriceSizeForLay.countPriceSizeForLays());
	}
}
