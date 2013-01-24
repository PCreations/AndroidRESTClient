package com.pcreations.restclient;

abstract public class DaoFactory {

	protected abstract <D extends DaoAccess<T>, T extends ResourceRepresentation<?>> D getDao(Class<T> clazz);
	
}
