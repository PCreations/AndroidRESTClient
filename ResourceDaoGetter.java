package com.pcreations.restclient;

public interface ResourceDaoGetter<T> {
	
	abstract public DaoAccess<T> getResourceDao();
	
}
