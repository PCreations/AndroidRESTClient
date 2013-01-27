package com.pcreations.restclient.test;

import android.content.Context;
import android.util.Log;

import com.pcreations.restclient.RestService;
import com.pcreations.restclient.exceptions.DatabaseManagerNotInitializedException;

public class DatabaseManager {
	 
	static private DatabaseManager instance;
 
	static public void init(Context context) {
		if(null==instance) {
			instance = new DatabaseManager(context);
		}
	}
 
	static public DatabaseManager getInstance() throws DatabaseManagerNotInitializedException {
		if(null==instance) {
			throw new DatabaseManagerNotInitializedException();
		}
		return instance;
	}
 
	private DatabaseHelper helper;
 
	private DatabaseManager(Context context) {
		helper = new DatabaseHelper(context);
	}
 
	public DatabaseHelper getHelper() {
		return helper;
	}
}
 