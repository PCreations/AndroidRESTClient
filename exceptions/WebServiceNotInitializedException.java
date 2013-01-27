package com.pcreations.restclient.exceptions;

import android.util.Log;

import com.pcreations.restclient.RestService;

public class WebServiceNotInitializedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3189957130125110077L;

	public WebServiceNotInitializedException() {
		super();
		Log.e(RestService.TAG, "Web service not initialized");
	}

}
