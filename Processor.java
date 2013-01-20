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
		}
		
	}
	
	protected void handleHttpRequestHandlerCallback(int statusCode, RESTRequest request, InputStream resultStream) {
		//GESTION BDD EN FONCTION RESULTAT REQUETE
		try {
			mResourceDaoGetter.getResourceDao().updateOrCreate(request.getResourceRepresentation());
			Log.d(RestService.TAG, "handleHttpRequestHandlerCallback");
			mRESTServiceCallback.callAction(statusCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRESTServiceCallback(RESTServiceCallback callback) {
		mRESTServiceCallback = callback;
	}
	
	public interface RESTServiceCallback {
		abstract public void callAction(int statusCode);
	}

	public boolean checkRequest(RESTRequest request) {
		/*
		 * 1. Est-ce qu'une requ�te est d�j� en cours pour cet id de resource ?
		 * 2. Si oui quel le transacting flag vaut il true ?
		 * 3. Si oui alors on ne fait rien on attend
		 * 4. Si non le code de retour vaut il 200 ?
		 * 5. Si oui alors tout s'est bien pass� on ne refait rien
		 * 6. Si non alors on relance la requ�te
		 */
		try {
			ResourceRepresentation resource = mResourceDaoGetter.getResourceDao().findById(request.getResourceRepresentation().getResourceId());
			if(null != resource) {
				Log.w(RestService.TAG, resource.toString());
				if(!resource.getTransactingFlag()) {
					if(resource.getResultCode() == 200) {
						Log.e(RestService.TAG, "La requ�te s'est bien d�roul�e : je ne fait rien et je renvoie false");
						return false;
					}
					Log.e(RestService.TAG, "La requ�te s'est mal d�roul�e : je la relance et je renvoie true");
					return true;
				}
				Log.e(RestService.TAG, "La requ�te est en cours : j'attends et je renvoie false");
				return false;
			}
			Log.e(RestService.TAG, "Je ne manipule pas la m�me resource et je renvoie true");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
}
