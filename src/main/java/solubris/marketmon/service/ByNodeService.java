package solubris.marketmon.service;

import java.io.IOException;

import solubris.marketmon.domain.EventType;
import solubris.marketmon.requestor.RequestorException;

public interface ByNodeService {

	void bynodeRequest(EventType eventType) throws IOException,
			RequestorException;
}
