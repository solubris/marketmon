package solubris.marketmon.requestor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class RoundRobinRequestor implements Requestor {
	static private final Logger logger = LoggerFactory
			.getLogger(RoundRobinRequestor.class);

	private static int requestorIndex=0;

	// XXX cant use static as prolly want multiple instances of the same requestor class
	@Value("discrimination")
	static public Class<? extends Requestor> []requestorList;

	public String get(String url) throws RequestorException {
		Class<? extends Requestor> requestorClass = null;
		Requestor requestor = null;
		requestorClass = getNextRequestor();

		if (requestorClass != null) {
			try {
				requestor = requestorClass.newInstance();
			} catch (InstantiationException e) {
				logger.error("Couldn't instatiate request {}", requestor, e);
				return null;
			} catch (IllegalAccessException e) {
				logger.error("Couldn't instatiate request {}", requestor, e);
				return null;
			}

			return requestor.get(url);
		}
		return null;
	}

	private synchronized Class<? extends Requestor> getNextRequestor() {
		Class<? extends Requestor> requestorClass;
		requestorClass = requestorList[requestorIndex];
		requestorIndex++;
		// loop index back to zero
		if(requestorIndex>=requestorList.length) {
			requestorIndex=0;
		}
		return requestorClass;
	}

	public RoundRobinRequestor() {
		super();
	}
}
