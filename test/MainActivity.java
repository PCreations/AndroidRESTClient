package com.pcreations.restclient.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	private RESTRequest postRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(RestService.TAG, "START");
        setContentView(R.layout.activity_main);
        ws = new TestWebService(this);
        postRequest = ws.newRequest();
        ws.addAddress(postRequest);
        Log.e(RestService.TAG, "chupeeRequestID = " + postRequest.toString());
    }
    
    public void testRequest(View button) {
    	Log.d(RestService.TAG, "CLIQUE");
    }
    
    public void onResume() {
    	super.onResume();
    	
    	postRequest.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode) {
				// TODO Auto-generated method stub
				Log.d(RestService.TAG, "POST REQUEST resultCode = " + String.valueOf(resultCode));
				Log.d(RestService.TAG, "POST REQUEST terminée : " + postRequest.toString());
			}
    	});
    	
    }
    
    public void onPause() {
    	super.onPause();
    	postRequest.setOnFinishedRequestListener(null);
    }

}
