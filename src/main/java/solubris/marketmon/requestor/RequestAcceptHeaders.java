package solubris.marketmon.requestor;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.protocol.HttpContext;

/**
 * Class responsible for handling Content Encoding requests in HTTP.
 * <p>
 * Instances of this class are stateless, therefore they're thread-safe and immutable.
 *
 * @see "http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5"
 *
 * @since 4.1
 */
@Immutable
public class RequestAcceptHeaders implements HttpRequestInterceptor {

	String accept;
	String encoding;
	String language;

	public RequestAcceptHeaders(String accept, String encoding, String language) {
		super();
		this.accept = accept;
		this.encoding = encoding;
		this.language = language;
	}

    /**
     * Adds the header {@code "Accept-Encoding: gzip,deflate"} to the request.
     */
    public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
    	if(accept!=null)
    		request.addHeader("Accept", accept);
    	if(encoding!=null)
    		request.addHeader("Accept-Encoding", encoding);
    	if(language!=null)
    		request.addHeader("Accept-Language", language);
    }
}
