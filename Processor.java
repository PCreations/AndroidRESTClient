package com.pcreations.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.pcreations.restclient.HttpRequestHandler.ProcessorCallback;
import com.pcreations.restclient.exceptions.DaoFactoryNotInitializedException;
import com.pcreations.restclient.test.ParsingException;

public abstract class Processor {

	protected HttpRequestHandler mHttpRequestHandler;
	protected RESTServiceCallback mRESTServiceCallback;
	protected DaoFactory mDaoFactory; //could be a DatabaseHelper;
	protected ParserFactory mParserFactory;
	
	public Processor() {
		mHttpRequestHandler = new HttpRequestHandler();
		setDaoFactory();
		setParserFactory();
	}

	abstract public void setDaoFactory();
	abstract public void setParserFactory();
	
	abstract protected <T extends ResourceRepresentation<?>> void postProcess(RESTRequest<T> r, InputStream resultStream);
	
	protected void preRequestProcess(RESTRequest<? extends ResourceRepresentation<?>> r) throws DaoFactoryNotInitializedException {
		//GESTION BDD
		Log.i(RestService.TAG, "preRequestProcess start");
		if(WebService.FLAG_RESOURCE && r.getVerb() != HTTPVerb.GET) {
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
					DaoAccess<ResourceRepresentation<?>> dao = mDaoFactory.getDao(resource.getClass());
					dao.updateOrCreate(resource);
					processRequest(r);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
			processRequest(r);
		Log.i(RestService.TAG, "preRequestProcess end");
	}
	
	protected void processRequest(RESTRequest<? extends ResourceRepresentation<?>> r) {
		Log.i(RestService.TAG, "processRequest start");
		mHttpRequestHandler.setProcessorCallback(new ProcessorCallback() {

			@Override
			public void callAction(int statusCode, RESTRequest<? extends ResourceRepresentation<?>> request, InputStream resultStream) {
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
				ResourceRepresentation<?> resource = r.getResourceRepresentation();
				try {
					InputStream is = mParserFactory.getParser(resource.getClass()).parseToInputStream(resource);
					//TODO afficher is
					//Log.i(RestService.TAG, "INPUT STREAM NOTE = " + inputStreamToString(is));
					mHttpRequestHandler.post(r, is);
				} catch (ParsingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		}
		Log.i(RestService.TAG, "processRequest end");
	}
	
	/*private InputStream stringToInputStream(String str) {
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
    }*/
	
	
	protected void handleHttpRequestHandlerCallback(int statusCode, RESTRequest<? extends ResourceRepresentation<?>> request, InputStream resultStream) {
		Log.i(RestService.TAG, "handleHTTpREquestHandlerCallback start");
		/*Log.i(RestService.TAG, "RESPONSE SERVER JSON = " + inputStreamToString(resultStream));
		Log.i(RestService.TAG, "Breakpoint");*/
		postProcess(request, resultStream);
		//TODO setup StrategyProcess to decide what to do here
		//By default store object
		try {
			if(WebService.FLAG_RESOURCE && request.getResourceRepresentation() != null) {
				ResourceRepresentation<?> resource = request.getResourceRepresentation();
				DaoAccess<ResourceRepresentation<?>> dao = mDaoFactory.getDao(resource.getClass());
				dao.updateOrCreate(request.getResourceRepresentation());
				Log.d(RestService.TAG, "handleHttpRequestHandlerCallback");
			}
			mRESTServiceCallback.callAction(statusCode, request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(RestService.TAG, "handleHTTpREquestHandlerCallback end");
	}
	
	public void setRESTServiceCallback(RESTServiceCallback callback) {
		mRESTServiceCallback = callback;
	}
	
	public interface RESTServiceCallback {
		abstract public void callAction(int statusCode, RESTRequest<? extends ResourceRepresentation<?>> r);
	}

	public boolean checkRequest(RESTRequest<? extends ResourceRepresentation<?>> request) {
		Log.e(RestService.TAG, "LISTE RESOURCES = ");
		List<ResourceRepresentation<?>> resourcesList;
		ResourceRepresentation<?> requestResource = request.getResourceRepresentation();
		DaoAccess<ResourceRepresentation<?>> dao = mDaoFactory.getDao(requestResource.getClass());
		try {
			resourcesList = dao.queryForAll();
			for(ResourceRepresentation<?> r : resourcesList) {
				Log.e(RestService.TAG, r.toString());
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.e(RestService.TAG, "FIN LISTE RESOURCES");
		try {
			ResourceRepresentation<?> resource = dao.findById(request.getResourceRepresentation().getId());
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
	
	private String inputStreamToString(InputStream is) {
        BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder inputStringBuilder = new StringBuilder();
	        String line;
			try {
				line = bufferedReader.readLine();
				while(line != null){
		            inputStringBuilder.append(line);inputStringBuilder.append('\n');
		            try {
						line = bufferedReader.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
				return inputStringBuilder.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
	}

}
