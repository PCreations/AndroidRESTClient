package com.pcreations.restclient.test;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class AddressParser extends AbstractParser<Address>{

	@Override
	public Address parse(InputStream content) throws ParsingException{
		Address JSONObjResponse = null;
		try {
			JSONObjResponse = mJSONMapper.readValue(content, Address.class);
			setResultCode(DATA_OK);
		} catch (JsonParseException e) {
			setResultCode(PARSER_KO_JSON_MALFORMED);
			//e.printStackTrace();
		} catch (JsonMappingException e) {
			//e.printStackTrace();
			setResultCode(PARSER_KO_JSON_OBJETS_INVALID);
		} catch (IOException e) {
			e.printStackTrace();
			setResultCode(PARSER_KO);
		}

		if(mResultCode != 0)
			throw new ParsingException(mResultCode);
		return JSONObjResponse;
	}

}
