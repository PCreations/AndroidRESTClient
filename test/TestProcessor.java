package com.pcreations.restclient.test;

import java.io.InputStream;

import com.pcreations.restclient.Processor;
import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.exceptions.DatabaseManagerNotInitializedException;

public class TestProcessor extends Processor {

	@Override
	protected void postProcess(RESTRequest r, InputStream resultStream) {}

	@Override
	public void setDaoFactory() {
		// TODO Auto-generated method stub
		mDaoFactory = new ORMLiteDaoFactory();
	}
	

}
