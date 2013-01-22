package com.pcreations.restclient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
	
	private ProcessorCallback mProcessorCallback;
	private HashMap<UUID, HTTPContainer> httpRequests;
	
	public HttpRequestHandler() {
		httpRequests = new HashMap<UUID, HTTPContainer>();
	}
	
	public void get(RESTRequest r) {
		Log.e(RestService.TAG, "Executing GET request: " + r.getUrl());
		try {
			httpRequests.put(r.getID(), new HTTPContainer(new HttpGet(), new URI(r.getUrl()), r.getHeaders()));
			processRequest(r);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mProcessorCallback.callAction(URI_SYNTAX_EXCEPTION, r, null);
		}
	}
	
	public void post(RESTRequest r) {
		try {
			httpRequests.put(r.getID(), new HTTPContainer(new HttpPost(r.getUrl()), new URI(r.getUrl()), r.getHeaders()));
			processRequest(r);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mProcessorCallback.callAction(URI_SYNTAX_EXCEPTION, r, null);
		}
	}
	
	private void processRequest(RESTRequest request) {
		Log.e(RestService.TAG, "PROCESS REQUEST");
		URL url;
		int statusCode = 0;
		InputStream in = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(request.getUrl());
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			//urlConnection.setReadTimeout(10000);
		    in = new BufferedInputStream(urlConnection.getInputStream());
		    statusCode = urlConnection.getResponseCode();
		} catch (MalformedURLException e1) {
			statusCode = MALFORMED_URL_EXCEPTION;
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
			//e.printStackTrace();
		} finally {
	    	urlConnection.disconnect();
	    }
	    
		/*try {
			response = currentHttpContainer.execute();
			HttpEntity responseEntity = response.getEntity();
			StatusLine responseStatus = response.getStatusLine();
			statusCode                = responseStatus != null ? responseStatus.getStatusCode() : 0;
			IS = responseEntity.getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			statusCode = CLIENT_PROTOCOL_EXCEPTION;
			Log.e(RestService.TAG, "CLIENT_PROTOCOL_EXCEPTION");
			//e.printStackTrace();
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
			//e.printStackTrace();
		}*/
		if(WebService.FLAG_RESOURCE) {
			request.getResourceRepresentation().setResultCode(statusCode);
			request.getResourceRepresentation().setTransactingFlag(false);
		}
		mProcessorCallback.callAction(statusCode, request, in);
	}
	
	public interface ProcessorCallback {
		abstract public void callAction(int statusCode, RESTRequest request, InputStream resultStream);
	}
	
	public void setProcessorCallback(ProcessorCallback callback) {
		mProcessorCallback = callback;
	}
	
	private class HTTPContainer {
		private HttpClient mHttpClient;
		private HttpRequestBase mRequest;
		private HttpParams mHttpParams;
		
		public HTTPContainer(HttpRequestBase request, URI uri, List<SerializableHeader> headers) {
			mHttpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(mHttpParams, TIMEOUT_CONNECTION);
			HttpConnectionParams.setSoTimeout(mHttpParams, TIMEOUT_SOCKET);
			mHttpClient = new DefaultHttpClient(mHttpParams);
			mRequest = request;
			mRequest.setURI(uri);
			setHeaders(mRequest, headers);
		}
		
		private void setHeaders(HttpRequestBase httpRequest, List<SerializableHeader> headers) {
			if(null != headers) {
				for(Header h : headers) {
					httpRequest.addHeader(h);
				}
			}
		}
		
		public HttpResponse execute() throws ClientProtocolException, IOException {
			return mHttpClient.execute(mRequest);
		}
	}
	
}
