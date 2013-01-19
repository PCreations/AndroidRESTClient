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
		initService(requestID, GET, uri);
		Log.d(RestService.TAG, "WebService.get()");
		startService();
		RESTRequest r = createNewRequest(requestID);
		return r;
	}
	
	protected RESTRequest get(String uri, Bundle extraParams) {
		UUID requestID = generateID();
		initService(requestID, GET, uri, extraParams);
		Log.d(RestService.TAG, "WebService.get()");
		RESTRequest r = createNewRequest(requestID);
		return r;
	}
	
	protected RESTRequest createNewRequest(UUID requestID) {
		RESTRequest r = new RESTRequest(requestID);
		mRequestCollection.add(r);
		return r;
	}
	
	protected void initService(UUID requestID, int method, String uri) {
		setData(uri);
		mIntent.putExtra(RestService.REQUEST_ID, requestID);
		mIntent.putExtra(RestService.METHOD_KEY, method);
		mIntent.putExtra(RestService.RECEIVER_KEY, mReceiver);
		//TODO requestID
	}
	
	private void setData(String uri) {
		mUri = Uri.parse(uri);
		mIntent.setData(mUri);
	}
	
	abstract protected void initService(UUID requestID, int method, String uri, Bundle extraParams);
	
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
		Intent i = resultData.getParcelable(RestService.INTENT_KEY);
		Bundle extras = i.getExtras();
		UUID requestID = (UUID) extras.getSerializable(RestService.REQUEST_ID);
		for(RESTRequest request : mRequestCollection) {
			if(request.getID().equals(requestID)) {
				request.getListener().onFinishedRequest(resultCode, resultData);
			}
		}
		mContext.stopService(mIntent);
	}	
}
