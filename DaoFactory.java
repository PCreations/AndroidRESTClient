package com.pcreations.restclient;

abstract public class DaoFactory {

	protected abstract DaoAccess<? extends ResourceRepresentation<?>> getDao(String name);
	
}
