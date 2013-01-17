package com.pcreations.restclient;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.pcreations.restclient.Processor.RESTServiceCallback;

public class RestService extends IntentService{
	
	public final static String REQUEST_ID = "com.pcreations.restclient.restservice.REQUEST_ID";
	public final static String METHOD_KEY = "com.pcreations.restclient.restservice.METHOD_KEY";
	public final static String PARAMS_KEY = "com.pcreations.restclient.restservice.PARAMS_KEY";
	public final static String RECEIVER_KEY = "com.pcreations.restclient.restservice.RECEIVER_KEY";
	public final static String RESULT_KEY = "com.pcreations.restclient.restservice.RESULT_KEY";
	public final static String INTENT_KEY = "com.pcreations.restclient.restservice.INTENT_KEY";
	public final static String TAG = "com.pcreations.restclient.restservice";
	private static Processor processor = null;
	private Intent mIntent;
	
	public RestService() {
		super("RestService");
	}

	@Override
	protected void onHandleIntent(Intent intent){
		Log.d(TAG, "onHandleIntent()");
		RestService.processor.setRESTServiceCallback(new RESTServiceCallback() {

			@Override
			public void callAction(int statusCode) {
				// TODO Auto-generated method stub
				handleRESTServiceCallback(statusCode);
			}
     
        });
		mIntent = intent;
		Uri uri = intent.getData();
		Bundle bundle = intent.getExtras();
		int method = bundle.getInt(RestService.METHOD_KEY);
		//Bundle params = bundle.getParcelable(WebService.PARAMS_KEY);
        switch(method) {
            case WebService.GET:
                RestService.processor.get(uri.toString(), method);
            break;
            case WebService.POST:
                //RestService.processor.post(uri.toString(), method);
            break;
        }
	}
	
	private void handleRESTServiceCallback(int statusCode) {
		Bundle bundle = mIntent.getExtras();
		ResultReceiver receiver = bundle.getParcelable(RestService.RECEIVER_KEY);
		Bundle resultData = new Bundle();
        resultData.putParcelable(RestService.INTENT_KEY, mIntent);
        receiver.send(statusCode, resultData);
	}
	
	public static void setProcessor(Processor processor) {
		RestService.processor = processor;
	}

}
