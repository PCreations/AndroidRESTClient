package com.pcreations.restclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class RestResultReceiver extends ResultReceiver {
	private Receiver mReceiver;

    public RestResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
