package solubris.marketmon.requestor;

public interface Requestor {

	/**
	 * get url and return content as string
	 * throw either temporary or permanent exception depending on whether it can retried
	 * 
	 * XXX return null is also used for temporary exception which is not good idea
	 * XXX should use URL or URI instead of string
	 * 
	 * @param urlStr
	 * @return
	 * @throws RequestorException
	 */
	public String get(String urlStr) throws RequestorException;

}