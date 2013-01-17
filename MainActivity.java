package com.pcreations.restclient;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pcreations.country_web_service.CountryWebService;
import com.pcreations.rest.R;
import com.pcreations.restclient.WebService.OnFinishedRequestListener;

public class MainActivity extends Activity  {

	private CountryWebService ws;
	private UUID chupeeRequestID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ws = new CountryWebService(this);
        chupeeRequestID = ws.getChupee();
    }
    
    public void onResume() {
    	super.onResume();
    	
    	ws.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode, Bundle bundle) {
				// TODO Auto-generated method stub
				handleFinishedRequest(resultCode, bundle);
			}
    		
    	});
    }
    
    public void onPause() {
    	super.onPause();
    	
    	ws.setOnFinishedRequestListener(null);
    }
    
    private void handleFinishedRequest(int resultCode, Bundle bundle) {
    	Log.d(RestService.TAG, "onFinishedRequest result code = " + String.valueOf(resultCode));
		Intent originalIntent = bundle.getParcelable(RestService.INTENT_KEY);
		Log.d(RestService.TAG, "onFinishedRequest original intent uri = " + originalIntent.getData());
		Bundle bundle2 = originalIntent.getExtras();
		Log.d(RestService.TAG, "onFinishedRequest original intent methode = " + bundle2.getInt(RestService.METHOD_KEY));
		Log.d(RestService.TAG, "onFinishedRequest original intent requestID = " + bundle2.getSerializable(RestService.REQUEST_ID));
    }
    
}
