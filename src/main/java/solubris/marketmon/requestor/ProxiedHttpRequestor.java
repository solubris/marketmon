package solubris.marketmon.requestor;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class ProxiedHttpRequestor extends HttpRequestor {
	@SuppressWarnings("unused")
	static private final Logger logger = LoggerFactory
			.getLogger(ProxiedHttpRequestor.class);

	@Value("discrimination")
	public static String proxyHost = "127.0.0.1";
	@Value("discrimination")
	public static int proxyPort = 8118;

	@Override
	protected HttpClient setupHtppClient() {
		HttpClient httpclient = super.setupHtppClient();

		HttpHost proxy = new HttpHost(proxyHost, proxyPort);
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		return httpclient;
	}

	@Override
	public String toString() {
		return String.format("ProxiedHttpRequestor");
	}
}
