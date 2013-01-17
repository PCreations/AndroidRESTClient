package com.pcreations.restclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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
	
	public WebService(Context context) {
		super();
		mContext = context;
		setProcessor();
		RestService.setProcessor(mProcessor);
		mIntent = new Intent(mContext, RestService.class);
		mReceiver = new RestResultReceiver(new Handler());
        mReceiver.setReceiver(this);
	}
	
	protected abstract void setProcessor();
	
	public void get(String uri) {
		initService(GET, uri);
		startService();
	}
	
	protected void initService(int method, String uri) {
		mUri = Uri.parse(uri);
		mIntent.setData(mUri);
		mIntent.putExtra(RestService.METHOD_KEY, method);
		mIntent.putExtra(RestService.RECEIVER_KEY, mReceiver);
		//TODO requestID
	}
	
	abstract protected void initService(int method, String uri, Bundle extraParams);
	
	protected void startService() {
		Log.d(RestService.TAG, "startService");
		mContext.startService(mIntent);
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
		onFinishedRequestListener.onFinishedRequest(resultCode, resultData);
		//TODO close service
	}
	
	public interface OnFinishedRequestListener {
        public abstract void onFinishedRequest(int resultCode, Bundle bundle);
    }
	
	
	
	
	
}
