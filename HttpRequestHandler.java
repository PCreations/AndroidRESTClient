package com.pcreations.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

import com.pcreations.restclient.RESTRequest.SerializableHeader;

public class HttpRequestHandler {

	public static final String STATUS_CODE_KEY = "com.pcreations.restclient.HttpRequestHandler.STATUS_CODE";
	public static final String RESPONSE_KEY = "com.pcreations.restclient.HttpRequestHandler.RESPONSE";
	private static final int URI_SYNTAX_EXCEPTION = 1;
	private static final int CLIENT_PROTOCOL_EXCEPTION = 2;
	private static final int IO_EXCEPTION = 3;
	private static final int UNKNOWN_HOST_EXCEPTION = 4;
	private static final int MALFORMED_URL_EXCEPTION = 5;
	private static final int UNKNOWN_SERVICE_EXCEPTION = 6;
	private static final int CONNECT_TIMEOUT_EXCEPTION = 7;
	private static final int SOCKET_TIMEOUT_EXCEPTION = 8;
	private static final int TIMEOUT_CONNECTION = 10000;
	private static final int TIMEOUT_SOCKET = 10000;
	
	private HttpClient mHttpClient;
	private HttpRequestBase mRequest;
	private HttpParams mHttpParams;
	private ProcessorCallback mProcessorCallback;
	
	public HttpRequestHandler() {
		mHttpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(mHttpParams, TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(mHttpParams, TIMEOUT_SOCKET);
		mHttpClient = new DefaultHttpClient(mHttpParams);
	}
	
	public void get(RESTRequest r) {
		mRequest = new HttpGet();
		setHeaders(mRequest, r.getHeaders());
		Log.d("tag", "Executing GET request: " + r.getUrl());
		try {
			mRequest.setURI(new URI(r.getUrl()));
			processRequest();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mProcessorCallback.callAction(URI_SYNTAX_EXCEPTION, null);
		}
	}
	
	public void post(RESTRequest r) {
		mRequest = new HttpPost(r.getUrl());
		mRequest.setHeader("Content-Type", "application/json");
		try {
			mRequest.setURI(new URI(r.getUrl()));
			processRequest();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mProcessorCallback.callAction(URI_SYNTAX_EXCEPTION, null);
		}
	}
	
	private void setHeaders(HttpRequestBase httpRequest, List<SerializableHeader> headers) {
		if(null != headers) {
			for(Header h : headers) {
				httpRequest.addHeader(h);
			}
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
			Log.e(RestService.TAG, "CLIENT_PROTOCOL_EXCEPTION");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(e instanceof UnknownHostException)
				statusCode = UNKNOWN_HOST_EXCEPTION;
			else if(e instanceof MalformedURLException)
				statusCode = MALFORMED_URL_EXCEPTION;
			else if(e instanceof UnknownServiceException)
				statusCode = UNKNOWN_SERVICE_EXCEPTION;
			else if(e instanceof ConnectTimeoutException)
				statusCode = CONNECT_TIMEOUT_EXCEPTION;
			else if(e instanceof SocketTimeoutException)
				statusCode = SOCKET_TIMEOUT_EXCEPTION;
			else
				statusCode = IO_EXCEPTION;
			Log.e(RestService.TAG, "IO_EXCEPTION");
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
