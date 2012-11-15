package com.pcreations.restclient;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class RestService extends IntentService{
	
	
	
	public RestService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle bundle = intent.getExtras();
		//Uri action = Uri.parse(bundle.getString(URI_KEY));
	}

}
