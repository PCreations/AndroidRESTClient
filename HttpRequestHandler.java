package com.pcreations.restclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

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
	
	public Bundle post(String url, List<NameValuePair> params) {
		mRequest = new HttpPost(url);
		mRequest.setHeader("Content-Type", "application/json");
		UrlEncodedFormEntity query;
		try {
			query = new UrlEncodedFormEntity(params);
			((HttpResponse) mRequest).setEntity(query);
			Log.d(LOG, "Executing POST request: " + url);
		} catch (UnsupportedEncodingException e) {
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
			result.putString(RESPONSE_KEY, EntityUtils.toString(responseEntity));
			Log.d(LOG, "result code : " + String.valueOf(statusCode));
			Log.d(LOG, "result string : " + EntityUtils.toString(responseEntity));
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
