package com.pcreations.restclient.test;

import android.content.Context;

import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.ResourceRepresentation;
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

	public void getAddress(RESTRequest<Address> r) {
		get(r, "http://pcreations.fr/labs/facteo/addresses/get/2.json");
	}
	
	public void addNote(RESTRequest<Note> r, Note n) {
		post(r, "http://pcreations.fr/labs/facteo/notes/add.json", n);
	}
	
	public void failed(RESTRequest<? extends ResourceRepresentation<?>> r) {
		get(r, "http://pcreafhdtions.fr/labs/facteo/addresses/get/2.json");
	}
	
	public void addAddress(RESTRequest<? extends ResourceRepresentation<?>> r, Address a) {
		post(r, "http://pcreations.fr/labs/facteo/addresses/add.json", a);
	}
}
