package com.pcreations.restclient.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

import com.pcreations.restclient.Processor;
import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.ResourceRepresentation;
import com.pcreations.restclient.RestService;

public class TestProcessor extends Processor {

	private AddressParser mAddressParser;
	
	public TestProcessor() {
		super();
		mAddressParser = new AddressParser();
	}
	
	@Override
	protected <T extends ResourceRepresentation<?>> void postProcess(RESTRequest<T> r, InputStream resultStream) {
		//resultStream.
		Log.i(RestService.TAG, "postProcess start + resource class = " + r.getResourceName());
		if(r.getResourceName().equals("Address")) {
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
	
	private String inputStreamToString(InputStream is) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = bufferedReader.readLine();
        while(line != null){
            inputStringBuilder.append(line);inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        return inputStringBuilder.toString();
	}
	

}
