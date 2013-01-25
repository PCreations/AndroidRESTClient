package com.pcreations.restclient;

import java.io.InputStream;

import com.pcreations.restclient.test.ParsingException;

public interface Parser<T extends ResourceRepresentation<?>> {

	public ResourceRepresentation<?> parse(InputStream content) throws ParsingException;
	
}
