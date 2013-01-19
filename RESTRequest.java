package com.pcreations.restclient;

import java.util.UUID;

import android.os.Bundle;

public class RESTRequest {
	
	private UUID mID;
	
	public RESTRequest(UUID id) {
		super();
		mID = id;
	}

	protected OnFinishedRequestListener mOnFinishedRequestListener;
	
	public void setOnFinishedRequestListener(OnFinishedRequestListener listener) {
		mOnFinishedRequestListener = listener;
	}

	public UUID getID() {
		return mID;
	}
	
	public OnFinishedRequestListener getListener() {
		return mOnFinishedRequestListener;
	}
	
	public interface OnFinishedRequestListener {
        public abstract void onFinishedRequest(int resultCode, Bundle bundle);
    }
	
}
