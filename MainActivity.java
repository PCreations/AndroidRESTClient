package com.pcreations.restclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.pcreations.rest.R;

public class MainActivity extends Activity implements RestResultReceiver.Receiver {

	public RestResultReceiver mReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReceiver = new RestResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        WebService ws = new WebService(this, mReceiver);
        Log.i("onCreate", "ws.get()");
        ws.get("http://chupee.fr/json/countries.json");
    }

	public void onReceiveResult(int resultCode, Bundle resultData) {
		Log.d("resultCode", String.valueOf(resultCode));
		Log.d("resultData", resultData.getString(WebService.RESULT_KEY));
	}

    
}
