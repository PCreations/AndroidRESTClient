package com.pcreations.restclient.test;

import java.io.InputStream;

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
		get(r, "http://pcreations.fr/labs/facteo/addresses/get/2.json");
	}
	
	public void failed(RESTRequest r) {
		get(r, "http://pcreafhdtions.fr/labs/facteo/addresses/get/2.json");
	}
	
	public void addAddress(RESTRequest r) {
		post(r, "http://pcreations.fr/labs/facteo/addresses/add.json");
	}
}
