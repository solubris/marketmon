package solubris.marketmon.requestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("HttpRequestor")
public class HttpRequestor implements Requestor {
	static private final Logger logger = LoggerFactory
			.getLogger(HttpRequestor.class);

	@Value("discrimination")
	public static String accept = "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5";
	//"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	@Value("discrimination")
	public static String charset = "ISO-8859-1,utf-8;q=0.7,*;q=0.3";
	@Value("discrimination")
	public static String encoding = null; //"gzip,deflate,sdch";
	@Value("discrimination")
	public static String language = "en-GB,en-US;q=0.8,en;q=0.6";
	@Value("discrimination")
	public static String userAgent = "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.121 Safari/535.2";
//	public static String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7";
//	public static String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16";
	@Value("discrimination")
	public static String robotPattern = "CAPTCHA";

//			Avail-Dictionary:slP-scw4
//			Cache-Control:max-age=0
//			Connection:keep-alive
//			Cookie:PREF=ID=c844d3aece30052b:U=afe9ea6e2c69730a:FF=0:TM=1312926645:LM=1312926646:S=-LMpu9l4jBWkPgXI; SID=DQAAAFMBAAAqX_i07ino_baSAOa2dbDOcVFVUylshoIncvDtztlAqRWI2GdtTZGc_3MUuNFeHbNylcBGS-BH2XT3dFkv24Hvyvl2VKXkD-FquazwFZRI5ZqNhlmoT5JUFuDDFFUMoQJ5DcPqQCYMRLmSNH0ZIe2JdY3_a6uLXUOHIeZhjruSPtE_SzjTmt5nPC6fkmnj8xEpdbqZXLoa75tAOV4ntAxzOxVStx0qOOgK97wCk3L8cjfBATw3WS2YRungfmSSTQl8TPapFE_MTM_fd3IA79nKAYA3OwYan7y6x7N4KS7-7XCk9r1UR2OZYkpDtGcniaATn2dMjT1drQrpS-FZHyJbnV9BRPqoZSBW3GLlFNvFcp1zLtSHSNH6U40G6eDTXdNljJYjKYVygZwbAFF505aH2uY4Jv6-x-lWa_jXTkqrhplYKJK-L-NZQv_mbaJa-Q1eeEBZV4_20am0gRZgX6Cp; HSID=AKWAIfFhudEUul74D; NID=53=ObfBgHBB8IB88noytsyshqXBI195WjWbtQY7F8Brj9R25I4UuRGUdgWmXg71tFQ2C6GnTfZdY4TjlMrDl7lUdkjDe4DVltx9ZnK2NWDUeW8gj-K2_37s0hgT2z2_CLei
//			Host:www.google.co.uk
	
	protected HttpClient setupHtppClient() {
/*
 * Connection manager, need to reuse httpclient for this to work
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxForRoute(new HttpRoute(localhost), 50);

		// allow for compressed encoding
		httpclient.addRequestInterceptor(new RequestAcceptEncoding());
		httpclient.addResponseInterceptor(new ResponseContentEncoding());
*/

		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setUserAgent(params, userAgent);
		HttpProtocolParams.setContentCharset(params, charset);

		DefaultHttpClient httpclient = new DefaultHttpClient(params);
//		ContentEncodingHttpClient httpclient = new ContentEncodingHttpClient(params);
		// no cookie handling yet
		httpclient.removeRequestInterceptorByClass(RequestAddCookies.class);
		httpclient.removeResponseInterceptorByClass(ResponseProcessCookies.class);
		// XXX allow dynamic cookies to be sent to make requests more authentic

		// use request interceptor to set accept headers
		httpclient.addRequestInterceptor(new RequestAcceptHeaders(accept, encoding, language));

		return httpclient;
	}

	protected String readRequestContent(HttpUriRequest request, HttpEntity entity)
			throws IOException {
		if (entity == null || entity.getContent()==null) {
			logger.warn("no entity/content found for url: {}", request.getURI());
			return null;
		}

		// If the response does not enclose an entity, there is no need
		// to bother about connection release
		try {
			return EntityUtils.toString(entity, "UTF-8");

		    /*
			StringBuilder result = new StringBuilder(4000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent()));
		    String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			*/
		} catch (IOException ex) {
			// In case of an IOException the connection will be released
			// back to the connection manager automatically
			throw ex;
		} catch (RuntimeException ex) {
			// In case of an unexpected exception you may want to abort
			// the HTTP request in order to shut down the underlying
			// connection and release it back to the connection manager.
			request.abort();
			throw ex;
		} finally {
			// Closing the input stream will trigger connection release
//			reader.close();
		}

//		return result.toString();
	}

	public String get(String url) throws RequestorException {
		HttpClient httpclient = setupHtppClient();
		url = Helper.randomizeUrl(url);

		try {
			logger.info("Requesting url={}", url);
			HttpUriRequest request = new HttpGet(url);
			HttpResponse response = null; 
			try {
				response=httpclient.execute(request);
			} catch(UnknownHostException e) {
				logger.warn("UnknownHostException for url: {}", url);
				request.abort();
				throw new PermanentRequestorException("UnknownHostException, will not retry", e);
			} catch(HttpHostConnectException e) {
				logger.warn("HttpHostConnectException for url: {} caused by {}", url, e.getLocalizedMessage());
				request.abort();
				return null;
			}

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 404) {
				logger.warn("404 error return for url: {}", url);
				request.abort();
				return "404"; // return a string here, so no retries on 404
				// return null;
			} else if (statusCode == 401) {
				logger.warn("401 error return for url: {}", url);
				request.abort();
				return null;
			} else if (statusCode == 403) {
				logger.warn("{} error returned for url: {} reason: {}", new Object []{statusCode, url, response.getStatusLine().getReasonPhrase()});
				logger.warn("response headers for error: {}", new Object []{Arrays.toString(response.getAllHeaders())});
				logger.warn("response content for error: {}", new Object []{readRequestContent(request, entity)});
				request.abort();
				throw new PermanentRequestorException("403 status returned, will not retry");
			} else if (statusCode == 503) {	// service unavailable, should retry
				String responseStr=readRequestContent(request, entity);
				Pattern p = Pattern.compile(robotPattern, Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(responseStr);
				if(m.find()) {
					request.abort();
					throw new RobotRequestorException("blocked by robot detection, will retry");
				}

				logger.warn("{} error returned for url: {} reason: {}", new Object []{statusCode, url, response.getStatusLine().getReasonPhrase()});
				logger.warn("response headers for error: {}", new Object []{Arrays.toString(response.getAllHeaders())});
				logger.warn("response content for error: {}", new Object []{responseStr});
				request.abort();
				throw new TemporaryRequestorException("503 status returned, will retry");
			} else if (statusCode >= 500) {
				logger.warn("{} error returned for url: {} reason: {}", new Object []{statusCode, url, response.getStatusLine().getReasonPhrase()});
				request.abort();
				return null;
			} else if (statusCode != 200) {
				logger.warn("status of {} returned for url: {}", statusCode, url);
			} else {
				// 200 is ok, so continue
			}

			return readRequestContent(request, entity);
		} catch (ClientProtocolException e) {
			logger.warn("error requesting url: {}", url, e);
		} catch (IOException e) {
			logger.warn("error requesting url: {}", url, e);
			// this error comes here
			// java.net.SocketException: Software caused connection abort: recv failed
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format("HttpRequestor");
	}
}
