package com.pcreations.restclient;

import java.io.InputStream;

import com.pcreations.restclient.HttpRequestHandler.ProcessorCallback;

public abstract class Processor {

	protected HttpRequestHandler mHttpRequestHandler;
	protected RESTServiceCallback mRESTServiceCallback;
	
	public Processor() {
		mHttpRequestHandler = new HttpRequestHandler();
	}

	abstract public void setResourcesManager();
	
	public void get(String url, int method) {
		//GESTION BDD
		//mCurrentResource.setName(mCurrentResource);
		//mCurrentResource.setState(RequestState.STATE_RETRIEVING);
		//mResourcesManager.createOrupdate(mCurrentResource);
		processRequest(url, method);
	}
	
	protected void processRequest(String url, int method) {
		mHttpRequestHandler.setProcessorCallback(new ProcessorCallback() {

			@Override
			public void callAction(int statusCode, InputStream resultStream) {
				// TODO Auto-generated method stub
				handleHttpRequestHandlerCallback(statusCode, resultStream);
			}
			
		});
		mHttpRequestHandler.get(url);
	}
	
	protected void handleHttpRequestHandlerCallback(int statusCode, InputStream resultStream) {
		//GESTION BDD EN FONCTION RESULTAT REQUETE
		mRESTServiceCallback.callAction(statusCode);
	}
	
	public interface RESTServiceCallback {
		abstract public void callAction(int statusCode);
	}
	
	public void setRESTServiceCallback(RESTServiceCallback callback) {
		mRESTServiceCallback = callback;
	}
	
}
