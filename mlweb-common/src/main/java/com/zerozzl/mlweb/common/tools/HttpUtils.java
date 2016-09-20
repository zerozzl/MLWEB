package com.zerozzl.mlweb.common.tools;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpUtils {

	public static String post (String url, int timeout)
			throws ClientProtocolException, IOException {
		return post(url, timeout, false, "", -1, "", "");
	}
	
	public static String post (String url, int timeout, boolean useProxy,
			String proxyHost, int proxyPort, String userName, String password)
					throws ClientProtocolException, IOException {
		String response = null;
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = null;
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		
		if(useProxy) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
					new UsernamePasswordCredentials(userName, password));
			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
			requestConfig = RequestConfig.custom().setProxy(proxy)
					.setSocketTimeout(timeout).setConnectTimeout(timeout).build();
		} else {
			httpClient = HttpClients.createDefault();
			requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeout).setConnectTimeout(timeout).build();
		}
		httpPost.setConfig(requestConfig);
		response = httpClient.execute(httpPost, responseHandler);
		return response;
	}
	
}
