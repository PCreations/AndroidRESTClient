package com.pcreations.restclient;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcreations.restclient.test.ParsingException;

public class SimpleJacksonParser {

	
	
	public final static int DATA_OK = 0;
	public final static int PARSER_KO = -1;
	public final static int PARSER_KO_JSON_MALFORMED = -2;
	public final static int PARSER_KO_JSON_OBJETS_INVALID = -3;
	public ObjectMapper mJSONMapper;
	protected int mResultCode;
	protected String mSimpleClassName;
	protected Class<?> mClazz;
	
	public <T extends ResourceRepresentation<?>> SimpleJacksonParser(Class<T> clazz)
	{
		super();
		mJSONMapper = new ObjectMapper(); // can reuse, share globally
		mJSONMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mJSONMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mResultCode = 0;
		mClazz = clazz;
		mSimpleClassName = clazz.getSimpleName();
		Log.i(RestService.TAG, "Simple class Name JacksonParser = " + mSimpleClassName);
	}
	
	public ResourceRepresentation<?> parse(InputStream content) throws ParsingException{
		ResourceRepresentation<?> JSONObjResponse = null;
		try {
			JSONObjResponse = (ResourceRepresentation<?>) mJSONMapper.readValue(content, mClazz);
			setResultCode(DATA_OK);
		} catch (JsonParseException e) {
			setResultCode(PARSER_KO_JSON_MALFORMED);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			setResultCode(PARSER_KO_JSON_OBJETS_INVALID);
		} catch (IOException e) {
			e.printStackTrace();
			setResultCode(PARSER_KO);
		}

		if(mResultCode != 0)
			throw new ParsingException(mResultCode);
		return JSONObjResponse;
	}
	
	protected void setResultCode(int resultCode) {
		mResultCode = resultCode;
	}

	/**
	 * @return the mResultCode
	 */
	public int getResultCode() {
		return mResultCode;
	}
	
}
