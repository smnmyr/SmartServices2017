package at.tugraz.iti.httptesting.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtilities {

	static Logger logger = Logger.getLogger(HttpUtilities.class.getName());

	static {
		logger.setLevel(Level.INFO);
	}

	public static CloseableHttpResponse sendGetRequest(URL url, String contentType) {
		logger.info("GETting from URL " + url + " (content type: " + contentType.toString() + ")");
		HttpGet getRequest = createSimpleGetRequest(url);
		getRequest.setHeader("Content-Type", contentType.toString());
		CloseableHttpResponse response = executeRequest(getRequest);
		return response;
	}

	public static CloseableHttpResponse sendPostRequest(String myString, URL url, String contentType) {
		logger.info("POSTing string " + myString + " to URL " + url + " (content type: " + contentType.toString() + ")");
		HttpPost postRequest = createSimplePostRequest(url, myString);
		postRequest.setHeader("Content-Type", contentType.toString());
		CloseableHttpResponse response = executeRequest(postRequest);
		return response;
	}

	public static CloseableHttpResponse sendDeleteRequest(URL url) {
		logger.info("DELETEing at URL " + url);
		HttpDelete deleteRequest = createSimpleDeleteRequest(url);
		CloseableHttpResponse response = executeRequest(deleteRequest);
		return response;
	}
	
	public static String getRawStringFromResponse(CloseableHttpResponse response) {
		try {
			return EntityUtils.toString(response.getEntity());
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static HttpGet createSimpleGetRequest(URL url) {
		try {
			return new HttpGet(url.toURI());
		} catch (URISyntaxException e) {
			System.err.println("Exception when creating GET request to " + url + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private static HttpPost createSimplePostRequest(URL url, String content) {
		try {
			HttpPost postRequest = new HttpPost(url.toURI());
			postRequest.setEntity(new StringEntity(content));
			return postRequest;
		} catch (URISyntaxException | UnsupportedEncodingException e) {
			System.err.println("Exception when creating POST request to " + url + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private static HttpDelete createSimpleDeleteRequest(URL url) {
		try {
			return new HttpDelete(url.toURI());
		} catch (URISyntaxException e) {
			System.err.println("Exception when creating DELETE request to " + url + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private static RequestConfig createDefaultRequestConfiguration() {
		return RequestConfig.custom()
				.setSocketTimeout(1000)
				.setConnectTimeout(1000)
				.build();
	}

	private static CloseableHttpResponse executeRequest(HttpRequestBase httpRequest) {
		try {
			RequestConfig config = createDefaultRequestConfiguration();
			CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(config).build();
			CloseableHttpResponse response = httpclient.execute(httpRequest);
			return response;
		} catch (IOException e) {
			logger.warning("Exception when sending " + httpRequest.getMethod() + " to " + httpRequest.getURI() + ": " + e.getMessage());
			return null;
		}	
	}
}
