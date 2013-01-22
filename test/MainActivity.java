package com.pcreations.restclient.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pcreations.rest.R;
import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.RESTRequest.OnFinishedRequestListener;
import com.pcreations.restclient.RestService;

public class MainActivity extends Activity  {

	private TestWebService ws;
	private RESTRequest failedRequest;
	private RESTRequest testRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ws = new TestWebService(this);
        failedRequest = ws.newRequest();
        testRequest = ws.newRequest();
        //ws.failed(failedRequest);
        ws.test(testRequest);
        //Log.e(RestService.TAG, "chupeeRequestID = " + testRequest.toString());
    }
    
    public void testRequest(View button) {
    	Log.d(RestService.TAG, "CLIQUE");
    	ws.test(testRequest);
    }
    
    public void onResume() {
    	super.onResume();
    	
    	testRequest.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode) {
				// TODO Auto-generated method stub
				Log.d(RestService.TAG, "TEST REQUEST resultCode = " + String.valueOf(resultCode));
				Log.d(RestService.TAG, "TEST REQUEST terminée : " + testRequest.toString());
			}
    	});
    	
    	/*failedRequest.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode) {
				// TODO Auto-generated method stub
				Log.d(RestService.TAG, "FAILED REQUEST resultCode = " + String.valueOf(resultCode));
				Log.d(RestService.TAG, "FAILED REQUEST terminée : " + failedRequest.toString());
			}
    	});*/
    	
    }
    
    public void onPause() {
    	super.onPause();
    	testRequest.setOnFinishedRequestListener(null);
    	//failedRequest.setOnFinishedRequestListener(null);
    }
    
}
