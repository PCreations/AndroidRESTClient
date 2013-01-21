package com.pcreations.restclient.exceptions;

import android.util.Log;

import com.pcreations.restclient.RestService;

public class DatabaseManagerNotInitializedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3189957130125110077L;

	public DatabaseManagerNotInitializedException() {
		super();
		Log.e(RestService.TAG, "DatabaseManager not initialized");
	}

}
