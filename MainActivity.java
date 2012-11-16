package com.pcreations.restclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
        //WebService ws = new WebService(this, mReceiver, "https://maps.googleapis.com/maps/api/place/details/json?reference=CmRYAAAAciqGsTRX1mXRvuXSH2ErwW-jCINE1aLiwP64MCWDN5vkXvXoQGPKldMfmdGyqWSpm7BEYCgDm-iv7Kc2PF7QA7brMAwBbAcqMr5i1f4PwTpaovIZjysCEZTry8Ez30wpEhCNCXpynextCld2EBsDkRKsGhSLayuRyFsex6JA6NPh9dyupoTH3g&sensor=true&key=AIzaSyAir6DKOMRfmXyHgXGHgHSg_FovG7yuMYY");
        Log.i("onCreate", "ws.get()");
        Intent intent = new Intent(MainActivity.this, RestService.class);
        intent.setData(Uri.parse("http://chupee.fr/json/countries.json"));
        intent.putExtra(WebService.METHOD_KEY, WebService.GET);
        intent.putExtra(WebService.RECEIVER_KEY, mReceiver);
        this.startService(intent);
        //ws.get();
    }

	public void onReceiveResult(int resultCode, Bundle resultData) {
		Log.d("resultCode", String.valueOf(resultCode));
		Log.d("resultData", resultData.getString(WebService.RESULT_KEY));
	}

    
}
