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

	public void test(RESTRequest r) {
		mCurrentResource = new TestResource("test", 1);
		get(r, "http://pcreations.fr/labs/facteo/distribution_centers/get/1.json");
	}

}
