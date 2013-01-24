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

	@Override
	protected <D extends DaoAccess<T>, T extends ResourceRepresentation<?>> D getDao(
			Class<T> clazz) {
		if(clazz.getName().equals("TestResource")) {
			return (D) mHelper.getResourceDao();
		}
		return null;
	}

}
