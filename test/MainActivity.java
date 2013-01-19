package com.pcreations.restclient.test;

import android.app.Activity;
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
			public void onFinishedRequest(int resultCode) {
				// TODO Auto-generated method stub
				Log.d(RestService.TAG, "resultCode = " + String.valueOf(resultCode));
				Log.d(RestService.TAG, "Requête terminé : " + testRequest.getID().toString());
			}
    	});
    	
    }
    
    public void onPause() {
    	super.onPause();
    	testRequest.setOnFinishedRequestListener(null);
    }
    
}
