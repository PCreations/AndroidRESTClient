package com.pcreations.restclient;

import java.io.InputStream;

import android.content.Context;

import com.pcreations.restclient.HttpRequestHandler.ProcessorCallback;

public abstract class Processor implements RestResultReceiver.Receiver {

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
		mHttpRequestHandler.get(url);
		mHttpRequestHandler.setProcessorCallback(new ProcessorCallback() {

			@Override
			public void callAction(int statusCode, InputStream resultStream) {
				// TODO Auto-generated method stub
				handleHttpRequestHandlerCallback(statusCode, resultStream);
			}
			
		});
	}
	
	private void handleHttpRequestHandlerCallback(int statusCode, InputStream resultStream) {
		//GESTION BDD EN FONCTION RESULTAT REQUETE
		mRESTServiceCallback.callAction();
	}
	
	public interface RESTServiceCallback {
		abstract public void callAction();
	}
	
	public void setRESTServiceCallback(RESTServiceCallback callback) {
		mRESTServiceCallback = callback;
	}
	
}
