package solubris.marketmon.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import solubris.marketmon.domain.Event;
import solubris.marketmon.domain.EventType;
import solubris.marketmon.json.Node;
import solubris.marketmon.domain.PriceSize;
import solubris.marketmon.domain.Runner;
import solubris.marketmon.json.ByNode;
import solubris.marketmon.requestor.HttpRequestor;
import solubris.marketmon.requestor.Requestor;
import solubris.marketmon.requestor.RequestorException;

@Service
@Transactional
public class ByNodeServiceImpl implements ByNodeService {
	static private final Logger logger = LoggerFactory.getLogger(ByNodeService.class);
//	private boolean addBestPriceOnly=false;
	private static final String urlFormat = "http://uk-api.betfair.com/www/sports/navigation/v1.0/graph/bynode?alt=json&locale=en_GB&nodeIds=%s";
	
	@Override
	public void bynodeRequest(EventType eventType) throws IOException, RequestorException {
		Requestor requestor=new HttpRequestor();
		String url=String.format(urlFormat, "EVENT_TYPE:" + eventType.getEventTypeId());
		String json = requestor.get(url);

		ByNode bm = ByNode.fromJsonToByNode(json);
		List<Node> nodeList = bm.getNodes();
		if(nodeList.size()==0) {
			logger.warn("no eventTypes returned from API, check marketId {}", eventType.getEventTypeId());
			return;
		}
		eventType.setName(nodeList.get(0).getName());
		eventType.merge();
	}
}
