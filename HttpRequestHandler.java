package com.pcreations.restclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.util.Log;

public class HttpRequestHandler {

	private static final String LOG = HttpRequestHandler.class.getName();
	private static final String STATUS_CODE_KEY = "com.pcreations.restclient.HttpRequstHandler.STATUS_CODE";
	private static final String RESPONSE_KEY = "com.pcreations.restclient.HttpRequestHandler.RESPONSE";
	private HttpClient mHttpClient;
	private HttpRequestBase mRequest;
	
	public HttpRequestHandler() {
		mHttpClient = new DefaultHttpClient();
	}
	
	public Bundle get(String url) {
		mRequest = new HttpGet(url);
		Log.d(LOG, "Executing request: " + url);
		return processRequest();
	}
	
	private Bundle processRequest() {
		HttpResponse response = null;
		try {
			response = mHttpClient.execute(mRequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		HttpEntity responseEntity = response.getEntity();
		StatusLine responseStatus = response.getStatusLine();
		int        statusCode     = responseStatus != null ? responseStatus.getStatusCode() : 0;
		Bundle result = new Bundle();
		result.putInt(STATUS_CODE_KEY, statusCode);
		result.putString(RESPONSE_KEY, responseEntity.toString());
		return result;
	}
	
}
