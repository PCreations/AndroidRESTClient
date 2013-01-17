package com.pcreations.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpRequestHandler {

	public static final String STATUS_CODE_KEY = "com.pcreations.restclient.HttpRequestHandler.STATUS_CODE";
	public static final String RESPONSE_KEY = "com.pcreations.restclient.HttpRequestHandler.RESPONSE";
	private HttpClient mHttpClient;
	private HttpRequestBase mRequest;
	private ProcessorCallback mProcessorCallback;
	
	public HttpRequestHandler() {
		mHttpClient = new DefaultHttpClient();
	}
	
	public void get(String url) {
		mRequest = new HttpGet();
		Log.d("tag", "Executing GET request: " + url);
		try {
			mRequest.setURI(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processRequest();
	}
	
	public void post(String url) {
		mRequest = new HttpPost(url);
		mRequest.setHeader("Content-Type", "application/json");
		try {
			mRequest.setURI(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processRequest();
	}
	
	private void processRequest() {
		HttpResponse response = null;
		int statusCode = 0;
		InputStream IS = null;
		try {
			response = mHttpClient.execute(mRequest);
			HttpEntity responseEntity = response.getEntity();
			StatusLine responseStatus = response.getStatusLine();
			statusCode                = responseStatus != null ? responseStatus.getStatusCode() : 0;
			IS = responseEntity.getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mProcessorCallback.callAction(statusCode, IS);
	}
	
	public interface ProcessorCallback {
		abstract public void callAction(int statusCode, InputStream resultStream);
	}
	
	public void setProcessorCallback(ProcessorCallback callback) {
		mProcessorCallback = callback;
	}
	
}
