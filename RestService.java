package com.pcreations.restclient;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

public class RestService extends IntentService{
	
	private final static String TAG = "Http";
	private Processor mProcessor;
	
	public RestService() {
		super("RestService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Uri uri = intent.getData();
		Bundle bundle = intent.getExtras();
		int method = bundle.getInt(WebService.METHOD_KEY);
		//Bundle params = bundle.getParcelable(WebService.PARAMS_KEY);
		ResultReceiver receiver = bundle.getParcelable(WebService.RECEIVER_KEY);
        HttpRequestHandler http = new HttpRequestHandler();
        Bundle requestResult = null;
        switch (method) {
            case WebService.GET:
                requestResult = http.get(uri.toString());
            break;
            case WebService.POST:
                requestResult = http.post(uri.toString());
            break;
        }
        Bundle resultData = new Bundle();
        resultData.putString(HttpRequestHandler.RESPONSE_KEY, requestResult.getString(HttpRequestHandler.RESPONSE_KEY));
        mProcessor.parse(resultData);
        receiver.send(requestResult.getInt(HttpRequestHandler.STATUS_CODE_KEY), resultData);
	}

}
