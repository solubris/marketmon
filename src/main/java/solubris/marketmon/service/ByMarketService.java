package solubris.marketmon.service;

import java.io.IOException;
import java.util.List;

import solubris.marketmon.domain.Market;
import solubris.marketmon.requestor.MockRequestor;
import solubris.marketmon.requestor.RequestorException;

public interface ByMarketService {

	void run() throws IOException, RequestorException;

	void bymarketRequest(Market market, boolean addBestPriceOnly)
			throws IOException, RequestorException;

	void bymarketRequest(List<Market> markets, boolean addBestPriceOnly)
			throws IOException, RequestorException;

	void setRequestor(MockRequestor mockRequestor);
}
