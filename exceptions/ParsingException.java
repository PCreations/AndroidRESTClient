package com.pcreations.restclient.exceptions;

import android.util.Log;

import com.pcreations.restclient.RestService;

public class ParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7301793342104343843L;

	public ParsingException(int resultCode) {
		Log.e(RestService.TAG, "Parsing exception with code " + String.valueOf(resultCode));
	}
	
}
