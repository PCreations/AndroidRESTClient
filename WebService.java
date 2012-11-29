package com.pcreations.restclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class WebService {

	public final static String METHOD_KEY = "com.pcreations.restclient.webservice.METHOD_KEY";
	public final static String PARAMS_KEY = "com.pcreations.restclient.webservice.PARAMS_KEY";
	public final static String RECEIVER_KEY = "com.pcreations.restclient.webservice.RECEIVER_KEY";
	public final static String RESULT_KEY = "com.pcreations.restclient.webservice.RESULT_KEY";
	public final static int GET = 0;
	public final static int POST = 1;
	public final static int PUT = 2;
	public final static int DELETE = 3;
	
	private Context mContext;
	private Uri mUri;
	private Intent mIntent;
	private static WebService ws;
	
	public static WebService getInstance(Context context) {
		if(null == ws) {
			ws = new WebService(context);
		}
		ws.setContext(context);
		return ws;
	}
	
	private WebService(Context context) {
		super();
		mContext = context;
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
	}
	
	private void startService() {
		mContext.startService(mIntent);
	}
	
	public static InputStream getJSONResult(String result) {
		return new ByteArrayInputStream(result.getBytes());
	}

	public void setContext(Context context) {
		mContext = context;
	}

	public Uri getUri() {
		return mUri;
	}

	public void setUri(Uri mUri) {
		this.mUri = mUri;
	}
	
	
	
	
	
}
