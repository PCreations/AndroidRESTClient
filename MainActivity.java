package com.pcreations.restclient;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pcreations.country_web_service.CountryWebService;
import com.pcreations.rest.R;
import com.pcreations.restclient.RESTRequest.OnFinishedRequestListener;

public class MainActivity extends Activity  {

	private CountryWebService ws;
	private RESTRequest chupeeRequest;
	private RESTRequest facteoRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ws = new CountryWebService(this);
        chupeeRequest = ws.getChupee();
        Log.d(RestService.TAG, "chupeeRequestID = " + chupeeRequest.toString());
        facteoRequest = ws.getFacteo();
        Log.d(RestService.TAG, "facteoRequestID = " + facteoRequest.toString());
    }
    
    public void onResume() {
    	super.onResume();
    	
    	
    	chupeeRequest.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode, Bundle bundle) {
				// TODO Auto-generated method stub
				Intent i = bundle.getParcelable(RestService.INTENT_KEY);
				Bundle extras = i.getExtras();
				UUID requestID = (UUID) extras.getSerializable(RestService.REQUEST_ID);
				Log.d(RestService.TAG, "Requête terminé : " + requestID.toString());
			}
    		
    	});
    	
    	facteoRequest.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode, Bundle bundle) {
				// TODO Auto-generated method stub
				Intent i = bundle.getParcelable(RestService.INTENT_KEY);
				Bundle extras = i.getExtras();
				UUID requestID = (UUID) extras.getSerializable(RestService.REQUEST_ID);
				Log.d(RestService.TAG, "Requête terminé : " + requestID.toString());
			}
    		
    	});
    }
    
    public void onPause() {
    	super.onPause();
    	
    	chupeeRequest.setOnFinishedRequestListener(null);
    	facteoRequest.setOnFinishedRequestListener(null);
    }
    
}
