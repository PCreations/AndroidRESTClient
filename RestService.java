package com.pcreations.restclient;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.pcrestion.restclient.exceptions.ProcessorNotInitializedException;

public class RestService extends IntentService{
	
	private final static String TAG = "Http";
	private static Processor processor = null;
	
	public RestService() {
		super("RestService");
	}

	@Override
	protected void onHandleIntent(Intent intent){
		Uri uri = intent.getData();
		Bundle bundle = intent.getExtras();
		int method = bundle.getInt(WebService.METHOD_KEY);
		//Bundle params = bundle.getParcelable(WebService.PARAMS_KEY);
		ResultReceiver receiver = bundle.getParcelable(WebService.RECEIVER_KEY);
        HttpRequestHandler http = new HttpRequestHandler();
        Bundle requestResult = null;
        switch(method) {
            case WebService.GET:
                requestResult = http.get(uri.toString());
            break;
            case WebService.POST:
                requestResult = http.post(uri.toString());
            break;
        }
        Bundle resultData = new Bundle();
        resultData.putString(HttpRequestHandler.RESPONSE_KEY, requestResult.getString(HttpRequestHandler.RESPONSE_KEY));
        try {
			executeProcessor(resultData);
		} catch (ProcessorNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        receiver.send(requestResult.getInt(HttpRequestHandler.STATUS_CODE_KEY), resultData);
	}
	
	protected void executeProcessor(Bundle resultData) throws ProcessorNotInitializedException {
		if(null == RestService.processor)
			throw new ProcessorNotInitializedException();
		RestService.processor.execute(resultData);
	}
	
	public static void setProcessor(Processor processor) {
		RestService.processor = processor;
	}

}
