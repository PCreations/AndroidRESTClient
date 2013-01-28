package com.pcreations.restclient;

import java.io.InputStream;

import com.pcreations.restclient.exceptions.ParsingException;

public interface Parser<T extends ResourceRepresentation<?>> {

	public T parseToObject(InputStream content) throws ParsingException;

	public <R extends ResourceRepresentation<?>> InputStream parseToInputStream(R resource) throws ParsingException;
	
}
