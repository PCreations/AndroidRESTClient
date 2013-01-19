package com.pcreations.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

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
	private static final int URI_SYNTAX_EXCEPTION = 1;
	private static final int CLIENT_PROTOCOL_EXCEPTION = 2;
	private static final int IO_EXCEPTION = 3;
	private static final int UNKNOWN_HOST_EXCEPTION = 4;
	private static final int MALFORMED_URL_EXCEPTION = 5;
	private static final int UNKNOWN_SERVICE_EXCEPTION = 6;
	
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
			processRequest();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mProcessorCallback.callAction(URI_SYNTAX_EXCEPTION, null);
		}
	}
	
	public void post(String url) {
		mRequest = new HttpPost(url);
		mRequest.setHeader("Content-Type", "application/json");
		try {
			mRequest.setURI(new URI(url));
			processRequest();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mProcessorCallback.callAction(URI_SYNTAX_EXCEPTION, null);
		}
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
			statusCode = CLIENT_PROTOCOL_EXCEPTION;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(e instanceof UnknownHostException)
				statusCode = UNKNOWN_HOST_EXCEPTION;
			else if(e instanceof MalformedURLException)
				statusCode = MALFORMED_URL_EXCEPTION;
			else if(e instanceof UnknownServiceException)
				statusCode = UNKNOWN_SERVICE_EXCEPTION;
			else
				statusCode = IO_EXCEPTION;
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
