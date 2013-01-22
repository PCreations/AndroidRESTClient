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
	
	public RESTRequest newRequest() {
		RESTRequest r = new RESTRequest(generateID());
		mRequestCollection.add(r);
		return r;
	}
	
	protected abstract void setProcessor();
	
	protected void get(RESTRequest r, String uri) {
		Log.d(RestService.TAG, "WebService.get()");
		initRequest(r, HTTPVerb.GET,  uri);
		try {
			initAndStartService(r);
		} catch (CurrentResourceNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void get(RESTRequest r, String uri, Bundle extraParams) {
		Log.d(RestService.TAG, "WebService.get()");
		initRequest(r, HTTPVerb.GET, uri, extraParams);
		try {
			initAndStartService(r);
		} catch (CurrentResourceNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void initRequest(RESTRequest r, HTTPVerb verb, String uri) {
		r.setVerb(verb);
		r.setUrl(uri);
	}
	
	protected void initRequest(RESTRequest r, HTTPVerb verb, String uri, Bundle extraParams) {
		r.setVerb(verb);
		r.setUrl(uri);
		r.setExtraParams(extraParams);
	}
	
	protected void initAndStartService(RESTRequest request) throws CurrentResourceNotInitializedException{
		Log.e(RestService.TAG, "get : mRequestCollection.size() = " + String.valueOf(mRequestCollection.size()));
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
		Log.w(RestService.TAG, "dans onReceiveResult" + r.getResourceRepresentation().toString());
		for(RESTRequest request : mRequestCollection) {
			if(request.getID().equals(r.getID())) {
				if(request.getListener() != null) {
					request.setResourceRepresentation(r.getResourceRepresentation());
					request.getListener().onFinishedRequest(resultCode);
				}
				Intent i = resultData.getParcelable(RestService.INTENT_KEY);
				mContext.stopService(i);
				if(resultCode == 200)
					mRequestCollection.remove(request);
			}
		}
		Log.e(RestService.TAG, "onReceiveResult : mRequestCollection.size() = " + String.valueOf(mRequestCollection.size()));
	}
}
