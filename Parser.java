package com.pcreations.restclient;

import java.io.InputStream;

import com.pcreations.restclient.test.ParsingException;

public interface Parser<T extends ResourceRepresentation<?>> {

	public T parseToObject(InputStream content) throws ParsingException;

	public InputStream parseToInputStream(ResourceRepresentation<?> resource) throws ParsingException;
	
}
