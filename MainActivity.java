package com.pcreations.restclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.pcreations.rest.R;

public class MainActivity extends Activity {

	public RestResultReceiver mReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("REST", "HttpRequestHandler test");
        HttpRequestHandler request = new HttpRequestHandler();
        request.get("http://chupee.fr/json/countries.json");
    }
   
}
