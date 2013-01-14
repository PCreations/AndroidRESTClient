package com.pcreations.restclient;

import java.io.IOException;
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
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.util.Log;

public class HttpRequestHandler {

	private static final String LOG = HttpRequestHandler.class.getName();
	public static final String STATUS_CODE_KEY = "com.pcreations.restclient.HttpRequestHandler.STATUS_CODE";
	public static final String RESPONSE_KEY = "com.pcreations.restclient.HttpRequestHandler.RESPONSE";
	private HttpClient mHttpClient;
	private HttpRequestBase mRequest;
	
	public HttpRequestHandler() {
		mHttpClient = new DefaultHttpClient();
	}
	
	public Bundle get(String url) {
		mRequest = new HttpGet();
		Log.d(LOG, "Executing GET request: " + url);
		try {
			mRequest.setURI(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processRequest();
	}
	
	public Bundle post(String url) {
		mRequest = new HttpPost(url);
		mRequest.setHeader("Content-Type", "application/json");
		try {
			mRequest.setURI(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processRequest();
	}
	
	private Bundle processRequest() {
		HttpResponse response = null;
		Bundle result = null;
		try {
			response = mHttpClient.execute(mRequest);
			HttpEntity responseEntity = response.getEntity();
			StatusLine responseStatus = response.getStatusLine();
			int        statusCode     = responseStatus != null ? responseStatus.getStatusCode() : 0;
			result = new Bundle();
			result.putInt(STATUS_CODE_KEY, statusCode);
			String responseEntityS = EntityUtils.toString(responseEntity);
			result.putString(WebService.RESULT_KEY, responseEntityS);
			Log.d(LOG, "result code : " + String.valueOf(statusCode));
			Log.d(LOG, "result string : " + responseEntityS);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
