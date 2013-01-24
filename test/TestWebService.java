package com.pcreations.restclient.test;

import android.content.Context;

import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.WebService;

public class TestWebService extends WebService {
	
	public TestWebService(Context context) {
		super(context);
	}

	@Override
	protected void setProcessor() {
		// TODO Auto-generated method stub
		DatabaseManager.init(mContext);
		mProcessor = new TestProcessor();
	}

	public void getAddress(RESTRequest r) {
		get(r, "http://pcreations.fr/labs/facteo/addresses/get/2.json");
	}
	
	public void failed(RESTRequest r) {
		get(r, "http://pcreafhdtions.fr/labs/facteo/addresses/get/2.json");
	}
	
	public void addAddress(RESTRequest r, Address a) {
		post(r, "http://pcreations.fr/labs/facteo/addresses/add.json", a);
	}
}
