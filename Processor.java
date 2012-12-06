package com.pcreations.restclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.content.Context;
import android.os.Bundle;
import fr.chupee.jsonparser.parser.CountryParser;

public class Processor {

	private CountryParser mParser;
	private Context mContext;
	
	public Processor(Context context) {
		mContext = context;
	}

	public void parse(Bundle resultData) {
		// TODO Auto-generated method stub
		InputStream result = new ByteArrayInputStream(resultData.getString(WebService.RESULT_KEY).getBytes());
		mParser = new CountryParser(mContext);
		mParser.parse(result);
	}
	
}
