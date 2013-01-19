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

public abstract class WebService implements RestResultReceiver.Receiver{

	public final static int GET = 0;
	public final static int POST = 1;
	public final static int PUT = 2;
	public final static int DELETE = 3;
	
	protected RestResultReceiver mReceiver;
	protected Context mContext;
	protected Uri mUri;
	protected Intent mIntent;
	protected Processor mProcessor;
	protected OnFinishedRequestListener onFinishedRequestListener;
	protected List<RESTRequest> mRequestCollection;
	
	public WebService(Context context) {
		super();
		mContext = context;
		setProcessor();
		RestService.setProcessor(mProcessor);
		mIntent = new Intent(mContext, RestService.class);
		mReceiver = new RestResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        mRequestCollection = new ArrayList<RESTRequest>();
	}
	
	protected abstract void setProcessor();
	
	protected RESTRequest get(String uri) {
		UUID requestID = generateID();
		Log.d(RestService.TAG, "WebService.get()");
		startService();
		RESTRequest r = createNewRequest(requestID, uri);
		initService(r);
		return r;
	}
	
	protected RESTRequest get(String uri, Bundle extraParams) {
		UUID requestID = generateID();
		Log.d(RestService.TAG, "WebService.get()");
		RESTRequest r = createNewRequest(requestID, uri, extraParams);
		initService(r);
		return r;
	}
	
	protected RESTRequest createNewRequest(UUID requestID, String uri) {
		RESTRequest r = new RESTRequest(requestID, uri);
		mRequestCollection.add(r);
		return r;
	}
	
	protected RESTRequest createNewRequest(UUID requestID, String uri, Bundle extraParams) {
		RESTRequest r = new RESTRequest(requestID, uri, extraParams);
		mRequestCollection.add(r);
		return r;
	}
	
	protected void initService(RESTRequest request) {
		setData(request.getUrl());
		mIntent.putExtra(RestService.REQUEST_KEY, request);
		mIntent.putExtra(RestService.RECEIVER_KEY, mReceiver);
		//TODO requestID
	}
	
	private void setData(String uri) {
		mUri = Uri.parse(uri);
		mIntent.setData(mUri);
	}
	
	protected void startService() {
		Log.d(RestService.TAG, "startService");
		mContext.startService(mIntent);
	}
	
	protected UUID generateID() {
		return UUID.randomUUID();
	}
	
	public Uri getUri() {
		return mUri;
	}

	public void setUri(Uri mUri) {
		this.mUri = mUri;
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
				request.getListener().onFinishedRequest(resultCode);
			}
		}
		mContext.stopService(mIntent);
	}	
}
