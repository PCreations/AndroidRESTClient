package com.pcreations.restclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ResultReceiver;

public class WebService {

	public final static String METHOD_KEY = "com.pcreations.restclient.webservice.METHOD_KEY";
	public final static String PARAMS_KEY = "com.pcreations.restclient.webservice.PARAMS_KEY";
	public final static String RECEIVER_KEY = "com.pcreations.restclient.webservice.RECEIVER_KEY";
	public final static String RESULT_KEY = "com.pcreations.restclient.webservice.RESULT_KEY";
	public final static int GET = 0;
	public final static int POST = 1;
	public final static int PUT = 2;
	public final static int DELETE = 3;
	
	private ResultReceiver mReceiver;
	private Context mContext;
	private Uri mUri;
	private Intent mIntent;
	
	public WebService(Context context, ResultReceiver receiver) {
		super();
		mContext = context;
		mReceiver = receiver;
		mIntent = new Intent(mContext, RestService.class);
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
	
	public ResultReceiver getReceiver() {
		return mReceiver;
	}
	
	public static InputStream getJSONResult(String result) {
		return new ByteArrayInputStream(result.getBytes());
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
