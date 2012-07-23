package solubris.marketmon.requestor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockRequestor implements Requestor {
	static private final Logger logger = LoggerFactory
			.getLogger(MockRequestor.class);

	String mockResponse;
	
	public String getMockResponse() {
		return mockResponse;
	}

	public void setMockResponse(String mockResponse) {
		this.mockResponse = mockResponse;
	}

	public String get(String url) throws RequestorException {
		logger.debug("returning mock response for {}", url);
		return mockResponse;
	}
}
