package solubris.marketmon;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.MethodReplacer;

/**
 * Replace the ReplacementRun method with a method that does nothing
 * 
 * This is used to allow test context to override the root context
 * 
 * @author walterst
 *
 */
public class ReplacementBymarketRequest implements MethodReplacer {
	@SuppressWarnings("unused")
	static private final Logger logger = LoggerFactory.getLogger(ReplacementBymarketRequest.class);

	public ReplacementBymarketRequest() {
		super();
	}

	public Object reimplement(Object o, Method m, Object[] args) throws Throwable {
		return null;
	}

}