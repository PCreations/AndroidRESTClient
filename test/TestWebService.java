package com.pcreations.restclient.test;

import android.content.Context;

import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.WebService;

public class TestWebService extends WebService {
	
	public TestWebService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setProcessor() {
		// TODO Auto-generated method stub
		mProcessor = new TestProcessor();
	}

	public RESTRequest test() {
		return get("http://pcreations.fr/labs/facteo/addresses/get/2.json");
	}

}
