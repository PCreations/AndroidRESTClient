package com.pcreations.restclient.test;

import android.util.Log;

import com.pcreations.restclient.DaoAccess;
import com.pcreations.restclient.DaoFactory;
import com.pcreations.restclient.ResourceRepresentation;
import com.pcreations.restclient.RestService;
import com.pcreations.restclient.exceptions.DatabaseManagerNotInitializedException;

public class ORMLiteDaoFactory extends DaoFactory{
	
	private DatabaseHelper mHelper;
	
	public ORMLiteDaoFactory() {
		try {
			mHelper = DatabaseManager.getInstance().getHelper();
		} catch (DatabaseManagerNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends DaoAccess<T>, T extends ResourceRepresentation<?>> D getDao(
			Class<T> clazz) {
		Log.i(RestService.TAG, "getDao OF : " + clazz.getSimpleName());
		if(clazz.getSimpleName().equals("Address")) {
			return (D) mHelper.getAddressDao();
		}
		if(clazz.getSimpleName().equals("Note")) {
			return (D) mHelper.getNoteDao();
		}
		return null;
	}

}
