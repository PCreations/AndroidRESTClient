package com.pcreations.restclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

public class WebService {

	public final static String METHOD_KEY = "com.pcreations.restclient.webservice.METHOD_KEY";
	public final static String PARAMS_KEY = "com.pcreations.restclient.webservice.PARAMS_KEY";
	public final static String RECEIVER_KEY = "com.pcreations.restclient.webservice.RECEIVER_KEY";

	
	private final static int GET = 0;
	private final static int POST = 1;
	private final static int PUT = 2;
	private final static int DELETE = 3;
	
	private ResultReceiver mReceiver;
	private Context mContext;
	private Uri mUri;
	private Bundle mParams;
	private Intent mIntent;
	
	public WebService(Context context, ResultReceiver receiver, String uri) {
		super();
		mContext = context;
		mUri = Uri.parse(uri);
		mReceiver = receiver;
		mIntent = new Intent(mContext, RestService.class);
	}
	
	public void setParams(Bundle params) {
		mParams = params;
	}
	
	public void addParams(String key, String value) {
		mParams.putString(key, value);
	}
	
	public void get() {
		initService(GET);
		startService();
	}
	
	private void initService(int method) {
		mIntent.setData(mUri);
		mIntent.putExtra(METHOD_KEY, method);
		mIntent.putExtra(PARAMS_KEY, mParams);
		mIntent.putExtra(RECEIVER_KEY, mReceiver);
	}
	
	private void startService() {
		mContext.startService(mIntent);
	}
	
	public ResultReceiver getReceiver() {
		return mReceiver;
	}

	public void setReceiver(ResultReceiver mReceiver) {
		this.mReceiver = mReceiver;
	}

	public Uri getUri() {
		return mUri;
	}

	public void setUri(Uri mUri) {
		this.mUri = mUri;
	}
	
	
	
	
	
}
