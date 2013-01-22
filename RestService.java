package com.pcreations.restclient;

import java.util.HashMap;
import java.util.UUID;

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
	private HashMap<UUID, Intent> mIntentsMap;
	
	public RestService() {
		super("RestService");
		mIntentsMap = new HashMap<UUID, Intent>();
	}

	@Override
	protected void onHandleIntent(Intent intent){
		Bundle bundle = intent.getExtras();
		RESTRequest r = (RESTRequest) bundle.getSerializable(RestService.REQUEST_KEY);
		mIntentsMap.put(r.getID(), intent);
		Log.e(RestService.TAG, "onHandleIntent() "+ String.valueOf(r.getID()));
		RestService.processor.setRESTServiceCallback(new RESTServiceCallback() {

			@Override
			public void callAction(int statusCode, RESTRequest r) {
				// TODO Auto-generated method stub
				handleRESTServiceCallback(statusCode, r);
			}
     
        });
        RestService.processor.preRequestProcess(r);
	}
	
	private void handleRESTServiceCallback(int statusCode, RESTRequest r) {
		Intent currentIntent = mIntentsMap.get(r.getID());
		Bundle bundle = currentIntent.getExtras();
		ResultReceiver receiver = bundle.getParcelable(RestService.RECEIVER_KEY);
		//Log.e(RestService.TAG, "resource dans handleRESTServiceCallback = " + r.getResourceRepresentation().toString());
		Bundle resultData = new Bundle();
        resultData.putSerializable(RestService.REQUEST_KEY, r);
        resultData.putParcelable(RestService.INTENT_KEY, currentIntent);
        receiver.send(statusCode, resultData);
	}
	
	public static void setProcessor(Processor processor) {
		RestService.processor = processor;
	}

}
