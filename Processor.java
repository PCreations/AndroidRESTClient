package com.pcreations.restclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.pcreations.restclient.HttpRequestHandler.ProcessorCallback;

public abstract class Processor {

	protected HttpRequestHandler mHttpRequestHandler;
	protected RESTServiceCallback mRESTServiceCallback;
	protected DaoFactory mDaoFactory; //could be a DatabaseHelper;
	
	public Processor() {
		mHttpRequestHandler = new HttpRequestHandler();
		setDaoFactory();
	}

	abstract public void setDaoFactory();
	abstract protected void postProcess(RESTRequest r, InputStream resultStream);
	
	protected void preRequestProcess(RESTRequest r) {
		//GESTION BDD
		if(WebService.FLAG_RESOURCE) {
			if(null == mDaoFactory) {
				throw new DaoFactoryNotInitializedException();
			}
			if(null != r.getResourceRepresentation()) {
				ResourceRepresentation<?> resource = r.getResourceRepresentation();
				resource.setTransactingFlag(true);
				Log.e(RestService.TAG, "resource dans preProcessRequest = " + r.getResourceRepresentation().toString());
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
		}
		else
			processRequest(r);
	}
	
	protected void processRequest(RESTRequest r) {
		mHttpRequestHandler.setProcessorCallback(new ProcessorCallback() {

			@Override
			public void callAction(int statusCode, RESTRequest request, InputStream resultStream) {
				// TODO Auto-generated method stub
				handleHttpRequestHandlerCallback(statusCode, request, resultStream);
			}
			
		});
		//TODO handle other verb
		switch(r.getVerb()) {
			case GET:
				mHttpRequestHandler.get(r);
				break;
			case POST:
				mHttpRequestHandler.post(r, stringToInputStream("{\"Address\":{\"name\":\"18 rue du Ponceau\"}}"));
		}
		
	}
	
	private InputStream stringToInputStream(String str) {
    	// convert String into InputStream
    	InputStream is = new ByteArrayInputStream(str.getBytes());
     
    	// read it with BufferedReader
    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
     
    	String line;
    	try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			try {
				br.close();
				return is;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
	
	protected void handleHttpRequestHandlerCallback(int statusCode, RESTRequest request, InputStream resultStream) {
		//GESTION BDD EN FONCTION RESULTAT REQUETE
		//Log.e(RestService.TAG, "resource dans HttpRequestHandlerCallback = " + request.getResourceRepresentation().toString());
		try {
			if(WebService.FLAG_RESOURCE) {
				mResourceDaoGetter.getResourceDao().updateOrCreate(request.getResourceRepresentation());
				Log.d(RestService.TAG, "handleHttpRequestHandlerCallback");
				postProcess(request, resultStream);
			}
			mRESTServiceCallback.callAction(statusCode, request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRESTServiceCallback(RESTServiceCallback callback) {
		mRESTServiceCallback = callback;
	}
	
	public interface RESTServiceCallback {
		abstract public void callAction(int statusCode, RESTRequest r);
	}

	public boolean checkRequest(RESTRequest request) {
		Log.w(RestService.TAG, "Resource = " + request.getResourceRepresentation().toString());
		Log.e(RestService.TAG, "LISTE RESOURCES = ");
		List<ResourceRepresentation<?>> resourcesList;
		try {
			resourcesList = mResourceDaoGetter.getResourceDao().queryForAll();
			for(ResourceRepresentation<?> r : resourcesList) {
				Log.e(RestService.TAG, r.toString());
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.e(RestService.TAG, "FIN LISTE RESOURCES");
		try {
			ResourceRepresentation resource = mResourceDaoGetter.getResourceDao().findByNameAndId(request.getResourceRepresentation().getName(), request.getResourceRepresentation().getResourceId());
			if(null != resource) {
				Log.w(RestService.TAG, resource.toString());
				if(!resource.getTransactingFlag()) {
					if(resource.getResultCode() == 200) {
						Log.e(RestService.TAG, "La requête s'est bien déroulée : je ne fait rien et je renvoie false");
						return false;
					}
					Log.e(RestService.TAG, "La requête s'est mal déroulée : je la relance et je renvoie true");
					return true;
				}
				Log.e(RestService.TAG, "La requête est en cours : j'attends et je renvoie false");
				return false;
			}
			Log.e(RestService.TAG, "Je ne manipule pas la même resource et je renvoie true");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
}
