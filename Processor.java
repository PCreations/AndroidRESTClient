package com.pcreations.restclient;

import java.io.InputStream;

import android.content.Context;
import android.util.Log;

import com.pcreations.restclient.HttpRequestHandler.ProcessorCallback;

public abstract class Processor {

	protected Context mContext;
	protected HttpRequestHandler mHttpRequestHandler;
	protected RESTServiceCallback mRESTServiceCallback;
	
	
	public Processor(Context context) {
		mContext = context;
		mHttpRequestHandler = new HttpRequestHandler();
	}

	public void get(String url, int method) {
		//GESTION BDD
		processRequest(url, method);
	}
	
	private void processRequest(String url, int method) {
		mHttpRequestHandler.setProcessorCallback(new ProcessorCallback() {

			@Override
			public void callAction(int statusCode, InputStream resultStream) {
				// TODO Auto-generated method stub
				handleHttpRequestHandlerCallback(statusCode, resultStream);
			}
			
		});
		mHttpRequestHandler.get(url);
	}
	
	private void handleHttpRequestHandlerCallback(int statusCode, InputStream resultStream) {
		//GESTION BDD EN FONCTION RESULTAT REQUETE
		Log.d(RestService.TAG, "Dans handleHttpRequestHandlerCallback");
		mRESTServiceCallback.callAction(statusCode);
	}
	
	public interface RESTServiceCallback {
		abstract public void callAction(int statusCode);
	}
	
	public void setRESTServiceCallback(RESTServiceCallback callback) {
		mRESTServiceCallback = callback;
	}
	
}
