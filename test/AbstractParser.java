package com.pcreations.restclient.test;

import java.io.InputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class AbstractParser<T> {

	public final static int DATA_OK = 0;
	public final static int PARSER_KO = -1;
	public final static int PARSER_KO_JSON_MALFORMED = -2;
	public final static int PARSER_KO_JSON_OBJETS_INVALID = -3;
	public ObjectMapper mJSONMapper;
	protected int mResultCode;
	
	public AbstractParser()
	{
		super();
		mJSONMapper = new ObjectMapper(); // can reuse, share globally
		mJSONMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mJSONMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mResultCode = 0;
		
	}
	
	/***
	 * Permet de deserialiser la chaine de caractère JSON passée en paramètre et de remplir les champs du parser
	 * @param content le contenu JSON à deserialiser
	 */
	abstract public T parse(InputStream content) throws ParsingException;
	
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
