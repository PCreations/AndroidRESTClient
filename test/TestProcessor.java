package com.pcreations.restclient.test;

import java.io.InputStream;
import java.sql.SQLException;

import android.util.Log;

import com.pcreations.restclient.DaoAccess;
import com.pcreations.restclient.HTTPVerb;
import com.pcreations.restclient.Processor;
import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.ResourceRepresentation;
import com.pcreations.restclient.RestService;
import com.pcreations.restclient.SimpleJacksonParser;

public class TestProcessor extends Processor {

	public TestProcessor() {
		super();
	}
	
	@Override
	protected <T extends ResourceRepresentation<?>> void postProcess(RESTRequest<T> r, InputStream resultStream) {
		//resultStream.
		if(r.getVerb() == HTTPVerb.GET) {
			Log.i(RestService.TAG, "postProcess start + resource class = " + r.getResourceName());
			if(r.getResourceName().equals("Address")) {
				try {
					Address a = (Address) mParser.parseToObject(resultStream);
					//TODO SAVE NOTE WITH SETADDRESS
					DaoAccess<ResourceRepresentation<?>> noteDao = mDaoFactory.getDao(Note.class);
					for(Note n : a.getNotes()) {
						n.setAddress(a);
						try {
							noteDao.updateOrCreate(n);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					r.setResourceRepresentation(a);
					Log.i(RestService.TAG, "ADDRESS = " + a.toString());
				} catch (ParsingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.i(RestService.TAG, "postProcess end");
		}
	}

	@Override
	public void setDaoFactory() {
		// TODO Auto-generated method stub
		mDaoFactory = new ORMLiteDaoFactory();
	}

	@Override
	public void setParser() {
		setSimpleJacksonParser();
	}
	
	/*private String inputStreamToString(InputStream is) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = bufferedReader.readLine();
        while(line != null){
            inputStringBuilder.append(line);inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        return inputStringBuilder.toString();
	}*/
	

}
