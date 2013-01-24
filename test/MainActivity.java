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
	private RESTRequest<Address> getAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(RestService.TAG, "START");
        setContentView(R.layout.activity_main);
        ws = new TestWebService(this);
        getAddress = ws.newRequest(Address.class);
        ws.getAddress(getAddress);
        Log.e(RestService.TAG, "chupeeRequestID = " + getAddress.toString());
    }
    
    public void testRequest(View button) {
    	Log.d(RestService.TAG, "CLIQUE");
    }
    
    public void onResume() {
    	super.onResume();
    	
    	getAddress.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode) {
				// TODO Auto-generated method stub
				Log.d(RestService.TAG, "POST REQUEST resultCode = " + String.valueOf(resultCode));
				Log.d(RestService.TAG, "POST REQUEST terminée : " + getAddress.toString());
			}
    	});
    	
    }
    
    public void onPause() {
    	super.onPause();
    	getAddress.setOnFinishedRequestListener(null);
    }

}
