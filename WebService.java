package com.pcreations.restclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class WebService implements RestResultReceiver.Receiver{

	public final static String METHOD_KEY = "com.pcreations.restclient.webservice.METHOD_KEY";
	public final static String PARAMS_KEY = "com.pcreations.restclient.webservice.PARAMS_KEY";
	public final static String RECEIVER_KEY = "com.pcreations.restclient.webservice.RECEIVER_KEY";
	public final static String RESULT_KEY = "com.pcreations.restclient.webservice.RESULT_KEY";
	public final static int GET = 0;
	public final static int POST = 1;
	public final static int PUT = 2;
	public final static int DELETE = 3;
	
	private RestResultReceiver mReceiver;
	private Context mContext;
	private Uri mUri;
	private Intent mIntent;
	
	public WebService(Context context) {
		super();
		mContext = context;
		mIntent = new Intent(mContext, RestService.class);
		mReceiver = new RestResultReceiver(new Handler());
        mReceiver.setReceiver(this);
	}
	
	public void get(String uri) {
		initService(GET, uri);
		startService();
	}
	
	private void initService(int method, String uri) {
		mUri = Uri.parse(uri);
		mIntent.setData(mUri);
		mIntent.putExtra(METHOD_KEY, method);
		mIntent.putExtra(RECEIVER_KEY, mReceiver);
	}
	
	private void startService() {
		mContext.startService(mIntent);
	}
	
	public static InputStream getJSONResult(String result) {
		return new ByteArrayInputStream(result.getBytes());
	}

	public Uri getUri() {
		return mUri;
	}

	public void setUri(Uri mUri) {
		this.mUri = mUri;
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		Log.d("WEBSERVICE : resultCode = ", String.valueOf(resultCode));
		Log.d("WEBSERVICE : resultData = ", resultData.getString(RESULT_KEY));
		
	}
	
	
	
	
	
}
