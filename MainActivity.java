package com.pcreations.restclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.pcreations.country_web_service.CountryWebService;
import com.pcreations.rest.R;
import com.pcreations.restclient.WebService.OnFinishedRequestListener;

public class MainActivity extends Activity  {

	CountryWebService ws;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ws = new CountryWebService(this);
        ws.getAddress(2);
        /*
         * request = ws.getDistribCenter(1);
         * ws.setOnFinishedRequestListener(new OnFinishedRequestListener(
         * 	public void onFinishedRequest(Addresses a...) {
         *    //gérer affichage liste
         * }
         * )
         */
    }
    
    public void onResume() {
    	super.onResume();
    	
    	ws.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(Bundle resultData) {
				Log.d("onFinishedRequest : resultData = ", resultData.getString(WebService.RESULT_KEY));
			}
    		
    	});
    }
    
    public void onPause() {
    	super.onPause();
    	
    	ws.setOnFinishedRequestListener(null);
    }
    
}
