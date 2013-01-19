package com.pcreations.restclient.test;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pcreations.rest.R;
import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.RESTRequest.OnFinishedRequestListener;
import com.pcreations.restclient.RestService;

public class MainActivity extends Activity  {

	private TestWebService ws;
	private RESTRequest testRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ws = new TestWebService(this);
        testRequest = ws.test();
        Log.e(RestService.TAG, "chupeeRequestID = " + testRequest.toString());
    }
    
    public void onResume() {
    	super.onResume();
    	
    	testRequest.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode, Bundle bundle) {
				// TODO Auto-generated method stub
				Intent i = bundle.getParcelable(RestService.INTENT_KEY);
				Bundle extras = i.getExtras();
				UUID requestID = (UUID) extras.getSerializable(RestService.REQUEST_ID);
				Log.d(RestService.TAG, "Requête terminé : " + requestID.toString());
				Log.d(RestService.TAG, "Result code : " + String.valueOf(resultCode));
			}
    		
    	});
    	
    }
    
    public void onPause() {
    	super.onPause();
    	testRequest.setOnFinishedRequestListener(null);
    }
    
}
