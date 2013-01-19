package com.pcreations.restclient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.pcreations.restclient.Processor.RESTServiceCallback;

public class RestService extends IntentService{
	
	public final static String REQUEST_KEY = "com.pcreations.restclient.restservice.REQUEST_KEY";
	public final static String RECEIVER_KEY = "com.pcreations.restclient.restservice.RECEIVER_KEY";
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
		Bundle bundle = intent.getExtras();
		RESTRequest r = (RESTRequest) bundle.getSerializable(RestService.REQUEST_KEY);
        RestService.processor.preRequestProcess(r);
	}
	
	private void handleRESTServiceCallback(int statusCode) {
		Bundle bundle = mIntent.getExtras();
		ResultReceiver receiver = bundle.getParcelable(RestService.RECEIVER_KEY);
		RESTRequest r = (RESTRequest) bundle.getSerializable(RestService.REQUEST_KEY);
		Bundle resultData = new Bundle();
        resultData.putSerializable(RestService.REQUEST_KEY, r);
        resultData.putParcelable(RestService.INTENT_KEY, mIntent);
        receiver.send(statusCode, resultData);
	}
	
	public static void setProcessor(Processor processor) {
		RestService.processor = processor;
	}

}
