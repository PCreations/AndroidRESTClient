package com.pcreations.restclient;

abstract public class DaoFactory {

	protected abstract <T> DaoAccess<T> getDao(Class<T> clazz, String name);
	
}
