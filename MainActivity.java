package com.pcreations.restclient;

import android.app.Activity;
import android.os.Bundle;

import com.pcreations.rest.R;

public class MainActivity extends Activity {

	public RestResultReceiver mReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
   
}
