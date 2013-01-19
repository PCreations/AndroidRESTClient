package com.pcreations.restclient;

import java.io.InputStream;

import com.pcreations.restclient.HttpRequestHandler.ProcessorCallback;

public abstract class Processor {

	protected HttpRequestHandler mHttpRequestHandler;
	protected RESTServiceCallback mRESTServiceCallback;
	protected ResourceRepresentation mCurrentResource;
	protected ResourcesManager mResourcesManager;
	
	public Processor() {
		mHttpRequestHandler = new HttpRequestHandler();
	}

	abstract public void setResourcesManager();
	
	protected void preRequestProcess(RESTRequest r) {
		//GESTION BDD
		//mCurrentResource.setName(mCurrentResource);
		//mCurrentResource.setState(RequestState.STATE_RETRIEVING);
		//mResourcesManager.createOrupdate(mCurrentResource);
		processRequest(r.getUrl(), r.getVerb());
	}
	
	protected void processRequest(String url, HTTPVerb verb) {
		mHttpRequestHandler.setProcessorCallback(new ProcessorCallback() {

			@Override
			public void callAction(int statusCode, InputStream resultStream) {
				// TODO Auto-generated method stub
				handleHttpRequestHandlerCallback(statusCode, resultStream);
			}
			
		});
		//TODO handle other verb
		switch(verb) {
			case GET:
				mHttpRequestHandler.get(url);
				break;
		}
		
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
