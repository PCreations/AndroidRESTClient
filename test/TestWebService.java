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

	public RESTRequest test() {
		mCurrentResource = new TestResource("test");
		return get("http://pcreations.fr/labs/facteo/addresses/get/2.json");
	}

}
