package com.pcreations.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.net.ParseException;
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
	private static final int CONNECT_TIMEOUT_EXCEPTION = 7;
	private static final int SOCKET_TIMEOUT_EXCEPTION = 8;
	private static final int TIMEOUT_CONNECTION = 10000;
	private static final int TIMEOUT_SOCKET = 10000;
	
	private ProcessorCallback mProcessorCallback;
	private HashMap<UUID, HTTPContainer> httpRequests;
	
	public HttpRequestHandler() {
		httpRequests = new HashMap<UUID, HTTPContainer>();
	}
	
	public void get(RESTRequest<? extends ResourceRepresentation<?>> r) {
		Log.d("tag", "Executing GET request: " + r.getUrl());
		try {
			httpRequests.put(r.getID(), new HTTPContainer(new HttpGet(), new URI(r.getUrl()), r.getHeaders()));
			processRequest(r);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mProcessorCallback.callAction(URI_SYNTAX_EXCEPTION, r, null);
		}
	}
	
	public void post(RESTRequest<? extends ResourceRepresentation<?>> r, InputStream holder) {
		try {
			httpRequests.put(r.getID(), new HTTPContainer(new HttpPost(r.getUrl()), new URI(r.getUrl()), r.getHeaders()));
			processRequest(r, holder);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mProcessorCallback.callAction(URI_SYNTAX_EXCEPTION, r, null);
		}
	}
	
	private void processRequest(final RESTRequest<? extends ResourceRepresentation<?>> request, final InputStream holder) {
		Log.i(RestService.TAG, "Holder");
		Log.i(RestService.TAG, holder.toString());
		new Thread(new Runnable() {
	        public void run() {
	        	Log.e(RestService.TAG, "PROCESS HTTP REQUEST");
	    		HTTPContainer currentHttpContainer = httpRequests.get(request.getID());
	    		HttpResponse response = null;
	    		HttpEntity responseEntity = null;
	    		int statusCode = 0;
	    		InputStream IS = null;
	    		try {
	    			response = currentHttpContainer.execute(holder);
	    			responseEntity = response.getEntity();
	    			StatusLine responseStatus = response.getStatusLine();
	    			statusCode                = responseStatus != null ? responseStatus.getStatusCode() : 0;
	    			IS = responseEntity.getContent();
	    			Log.i(RestService.TAG, "RESPONSE = " + getContentBody(responseEntity, IS));
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
	    		} finally {
	    			try {
	    				if(null != responseEntity)
	    					responseEntity.consumeContent();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		}
	    		if(WebService.FLAG_RESOURCE) {
	    			request.getResourceRepresentation().setResultCode(statusCode);
	    			request.getResourceRepresentation().setTransactingFlag(false);
	    		}
	    		mProcessorCallback.callAction(statusCode, request, IS);
	        }
	    }).start();
	}
	
	private void processRequest(final RESTRequest<? extends ResourceRepresentation<?>> request) {
		new Thread(new Runnable() {
	        public void run() {
	        	Log.e(RestService.TAG, "PROCESS HTTP REQUEST");
	    		HTTPContainer currentHttpContainer = httpRequests.get(request.getID());
	    		HttpResponse response = null;
	    		HttpEntity responseEntity = null;
	    		int statusCode = 0;
	    		InputStream IS = null;
	    		try {
	    			response = currentHttpContainer.execute();
	    			responseEntity = response.getEntity();
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
	    		} finally {
	    			try {
	    				if(null != responseEntity)
	    					responseEntity.consumeContent();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		}
	    		if(WebService.FLAG_RESOURCE && request.getVerb() != HTTPVerb.GET) {
	    			request.getResourceRepresentation().setResultCode(statusCode);
	    			request.getResourceRepresentation().setTransactingFlag(false);
	    		}
	    		mProcessorCallback.callAction(statusCode, request, IS);
	        }
	    }).start();
	}
	
	private String getContentBody(HttpEntity entity, InputStream instream) throws UnsupportedEncodingException {
		if (instream == null) { return ""; }

		if (entity.getContentLength() > Integer.MAX_VALUE) { throw new IllegalArgumentException(

		"HTTP entity too large to be buffered in memory"); }

		String charset = getContentCharSet(entity);

		if (charset == null) {

		charset = HTTP.DEFAULT_CONTENT_CHARSET;

		}

		Reader reader = new InputStreamReader(instream, charset);

		StringBuilder buffer = new StringBuilder();

		try {

		char[] tmp = new char[1024];

		int l;

		try {
			while ((l = reader.read(tmp)) != -1) {

			buffer.append(tmp, 0, l);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		} finally {

		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}

		return buffer.toString();
	}
	
	private String getContentCharSet(final HttpEntity entity) throws ParseException {

		if (entity == null) { throw new IllegalArgumentException("HTTP entity may not be null"); }

		String charset = null;

		if (entity.getContentType() != null) {

		HeaderElement values[] = entity.getContentType().getElements();

		if (values.length > 0) {

		NameValuePair param = values[0].getParameterByName("charset");

		if (param != null) {

		charset = param.getValue();

		}

		}

		}

		return charset;

		}
	
	public interface ProcessorCallback {
		abstract public void callAction(int statusCode, RESTRequest<? extends ResourceRepresentation<?>> request, InputStream resultStream);
	}
	
	public void setProcessorCallback(ProcessorCallback callback) {
		mProcessorCallback = callback;
	}
	
	private class HTTPContainer {
		private HttpClient mHttpClient;
		private HttpContext mHttpContext;
		private HttpRequestBase mRequest;
		private HttpParams mHttpParams;
		
		public HTTPContainer(HttpRequestBase request, URI uri, List<SerializableHeader> headers) {
			mHttpParams = new BasicHttpParams();
			mHttpContext = new BasicHttpContext();
			HttpConnectionParams.setConnectionTimeout(mHttpParams, TIMEOUT_CONNECTION);
			HttpConnectionParams.setSoTimeout(mHttpParams, TIMEOUT_SOCKET);
			mHttpClient = new DefaultHttpClient(mHttpParams);
			mRequest = request;
			mRequest.setURI(uri);
			setHeaders(headers);
		}

		private void setHeaders(List<SerializableHeader> headers) {
			if(null != headers) {
				for(Header h : headers) {
					mRequest.addHeader(h);
				}
			}
		}
		
		public HttpResponse execute() throws ClientProtocolException, IOException {
			if(mRequest instanceof HttpGet)
				return mHttpClient.execute(mRequest, mHttpContext);
			return null;
		}

		public HttpResponse execute(InputStream holder) throws ClientProtocolException, IOException {
			if(mRequest instanceof HttpPost || mRequest instanceof HttpPut) {
				mRequest.setHeader("Accept", "application/json");
				mRequest.setHeader("Content-type", "application/json");
				Log.i(RestService.TAG, "Holder execute");
				Log.i(RestService.TAG, "{\"Address\":{\"name\":\"18 rue du Ponceau\"}}");
				StringEntity se = new StringEntity("{\"Address\":{\"name\":\"18 rue du Ponceau\", \"distribution_center_id\":1}}");
				if(mRequest instanceof HttpPost)
					((HttpPost) mRequest).setEntity(se);
				else
					((HttpPut) mRequest).setEntity(se);
				return mHttpClient.execute(mRequest, mHttpContext);
			}
			return null;
		}
		
		/*private String inputStreamToString(InputStream is) throws IOException {
			StringBuilder inputStringBuilder = new StringBuilder();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String line = bufferedReader.readLine();
	        while(line != null){
	            inputStringBuilder.append(line);inputStringBuilder.append('\n');
	            line = bufferedReader.readLine();
	        }
	        return inputStringBuilder.toString();
		}*/
	}
	
}
