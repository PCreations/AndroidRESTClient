package com.pcreations.restclient;

import java.io.InputStream;
import java.sql.SQLException;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.pcreations.restclient.HttpRequestHandler.ProcessorCallback;
import com.pcreations.restclient.test.TestResource;

public abstract class Processor {

	protected HttpRequestHandler mHttpRequestHandler;
	protected RESTServiceCallback mRESTServiceCallback;
	protected ResourceDaoGetter<ResourceRepresentation> mResourceDaoGetter; //could be a DatabaseHelper;
	
	public Processor() {
		mHttpRequestHandler = new HttpRequestHandler();
		setResourceDaoGetter();
	}

	abstract public void setResourceDaoGetter();
	
	protected void preRequestProcess(RESTRequest r) {
		//GESTION BDD
		if(WebService.FLAG_RESOURCE) {
			ResourceRepresentation resource = r.getResourceRepresentation();
			resource.setTransactingFlag(true);
			switch(r.getVerb()) {
				case GET:
					resource.setState(RequestState.STATE_RETRIEVING);
					break;
				case POST:
					resource.setState(RequestState.STATE_POSTING);
					break;
				case PUT:
					resource.setState(RequestState.STATE_UPDATING);
					break;
				case DELETE:
					resource.setState(RequestState.STATE_DELETING);
					break;
			}
			try {
				mResourceDaoGetter.getResourceDao().updateOrCreate(resource);
				processRequest(r);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			processRequest(r);
	}
	
	protected void processRequest(RESTRequest r) {
		mHttpRequestHandler.setProcessorCallback(new ProcessorCallback() {

			@Override
			public void callAction(int statusCode, InputStream resultStream) {
				// TODO Auto-generated method stub
				handleHttpRequestHandlerCallback(statusCode, resultStream);
			}
			
		});
		//TODO handle other verb
		switch(r.getVerb()) {
			case GET:
				mHttpRequestHandler.get(r);
				break;
		}
		
	}
	
	protected void handleHttpRequestHandlerCallback(int statusCode, InputStream resultStream) {
		//GESTION BDD EN FONCTION RESULTAT REQUETE
		Log.d(RestService.TAG, "handleHttpRequestHandlerCallback");
		mRESTServiceCallback.callAction(statusCode);
	}
	
	public void setRESTServiceCallback(RESTServiceCallback callback) {
		mRESTServiceCallback = callback;
	}
	
	public interface RESTServiceCallback {
		abstract public void callAction(int statusCode);
	}
	
}
