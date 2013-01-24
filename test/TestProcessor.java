package com.pcreations.restclient.test;

import java.io.InputStream;

import android.util.Log;

import com.pcreations.restclient.Processor;
import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.RestService;
import com.pcreations.restclient.exceptions.DatabaseManagerNotInitializedException;

public class TestProcessor extends Processor {

	private AddressParser mAddressParser;
	
	public TestProcessor() {
		super();
		mAddressParser = new AddressParser();
	}
	
	@Override
	protected void postProcess(RESTRequest r, InputStream resultStream) {
		Log.i(RestService.TAG, "postProcess start");
		if(r.getResourceRepresentation().getClass().equals("Address")) {
			try {
				Address a = mAddressParser.parse(resultStream);
				a.toString();
			} catch (ParsingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i(RestService.TAG, "postProcess end");
	}

	@Override
	public void setDaoFactory() {
		// TODO Auto-generated method stub
		mDaoFactory = new ORMLiteDaoFactory();
	}
	

}
