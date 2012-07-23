package solubris.marketmon.requestor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class DiscriminatedRequestor implements Requestor {
	static private final Logger logger = LoggerFactory
			.getLogger(DiscriminatedRequestor.class);

	// want to have a map here, so each regex maps to a different requestor class
	// discriminatorPattern.* => HttpRequestor.class
	// public String discriminatorPattern="facebook.com|google.com|google.co.uk";
	@Value("discrimination")
	static public Map<String, Class<?>> discrimination;

	public String get(String url) throws RequestorException {
		Class<?> requestorClass = null;
		Requestor requestor = null;
		Set<String> keys = discrimination.keySet();
		Iterator<String> i = keys.iterator();
		while (i.hasNext()) {
			String key = i.next();
			if (url.matches(key)) {
				requestorClass = discrimination.get(key);
				break;
			}
		}

		if (requestorClass != null) {
			try {
				requestor = (Requestor) requestorClass.newInstance();
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

	public DiscriminatedRequestor() {
		super();

		// discrimination = new HashMap<String, Class<?>>();
		// discrimination.put("http://[^/]*google.*", TorHttpRequestor.class);
		// discrimination.put(".*", HttpRequestor.class);
	}
}
