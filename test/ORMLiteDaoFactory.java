package com.pcreations.restclient.test;

import com.pcreations.restclient.DaoAccess;
import com.pcreations.restclient.DaoFactory;
import com.pcreations.restclient.ResourceRepresentation;
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
	protected <D extends DaoAccess<T>, T extends ResourceRepresentation<?>> D getDao(
			Class<T> clazz) {
		if(clazz.getName().equals("Address")) {
			return (D) mHelper.getAddressDao();
		}
		if(clazz.getName().equals("Note")) {
			return (D) mHelper.getNoteDao();
		}
		return null;
	}

}
