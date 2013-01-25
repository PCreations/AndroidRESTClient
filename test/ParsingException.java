package com.pcreations.restclient.test;

import android.util.Log;

import com.pcreations.restclient.RestService;

public class ParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4067579685861287754L;

	public ParsingException(int code) {
		Log.e(RestService.TAG, "Error parsing : error code " + String.valueOf(code));
	}
	
}
