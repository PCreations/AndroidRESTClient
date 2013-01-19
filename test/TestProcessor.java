package com.pcreations.restclient.test;

import com.pcreations.restclient.Processor;
import com.pcreations.restclient.exceptions.DatabaseManagerNotInitializedException;

public class TestProcessor extends Processor {

	@Override
	public void setResourceDaoGetter() {
		// TODO Auto-generated method stub
		try {
			mResourceDaoGetter = DatabaseManager.getInstance().getHelper();
		} catch (DatabaseManagerNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
