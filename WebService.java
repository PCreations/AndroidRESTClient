package com.pcreations.restclient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.pcreations.restclient.RESTRequest.OnFinishedRequestListener;
import com.pcreations.restclient.exceptions.CurrentResourceNotInitializedException;

public abstract class WebService implements RestResultReceiver.Receiver{

	public static final boolean FLAG_RESOURCE = true;
	protected RestResultReceiver mReceiver;
	protected Context mContext;
	protected Processor mProcessor;
	protected OnFinishedRequestListener onFinishedRequestListener;
	protected List<RESTRequest> mRequestCollection;
	protected ResourceRepresentation mCurrentResource;
	
	public WebService(Context context) {
		super();
		mContext = context;
		setProcessor();
		RestService.setProcessor(mProcessor);
		mReceiver = new RestResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        mRequestCollection = new ArrayList<RESTRequest>();
	}
	
	protected abstract void setProcessor();
	
	protected RESTRequest get(String uri) {
		UUID requestID = generateID();
		Log.d(RestService.TAG, "WebService.get()");
		RESTRequest r = createNewRequest(HTTPVerb.GET, requestID, uri);
		try {
			initAndStartService(r);
		} catch (CurrentResourceNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	protected RESTRequest get(String uri, Bundle extraParams) {
		UUID requestID = generateID();
		Log.d(RestService.TAG, "WebService.get()");
		RESTRequest r = createNewRequest(HTTPVerb.GET, requestID, uri, extraParams);
		try {
			initAndStartService(r);
		} catch (CurrentResourceNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	protected RESTRequest createNewRequest(HTTPVerb verb, UUID requestID, String uri) {
		RESTRequest r = new RESTRequest(verb, requestID, uri);
		mRequestCollection.add(r);
		return r;
	}
	
	protected RESTRequest createNewRequest(HTTPVerb verb, UUID requestID, String uri, Bundle extraParams) {
		RESTRequest r = new RESTRequest(verb, requestID, uri, extraParams);
		mRequestCollection.add(r);
		return r;
	}
	
	protected void initAndStartService(RESTRequest request) throws CurrentResourceNotInitializedException{
		boolean proceedRequest = true;
		if(FLAG_RESOURCE) {
			if(null == mCurrentResource)
				throw new CurrentResourceNotInitializedException();
			request.setResourceRepresentation(mCurrentResource);
			proceedRequest = mProcessor.checkRequest(request);
		}
		if(proceedRequest) {
			Intent i = new Intent(mContext, RestService.class);
			i.setData(Uri.parse(request.getUrl()));
			i.putExtra(RestService.REQUEST_KEY, request);
			i.putExtra(RestService.RECEIVER_KEY, mReceiver);
			Log.d(RestService.TAG, "startService");
			mContext.startService(i);
		}
	}
	
	protected void setCurrentResource(ResourceRepresentation resource) {
		mCurrentResource = resource;
	}
	
	protected UUID generateID() {
		return UUID.randomUUID();
	}
	

	public void setOnFinishedRequestListener(OnFinishedRequestListener listener) {
		onFinishedRequestListener = listener;
	}

	
	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		Log.d(RestService.TAG, "onReceiveResult");
		RESTRequest r = (RESTRequest) resultData.getSerializable(RestService.REQUEST_KEY);
		for(RESTRequest request : mRequestCollection) {
			if(request.getID().equals(r.getID())) {
				if(request.getListener() != null) {
					request.getListener().onFinishedRequest(resultCode);
				}
				Intent i = resultData.getParcelable(RestService.INTENT_KEY);
				mContext.stopService(i);
			}
		}
	}	
}
