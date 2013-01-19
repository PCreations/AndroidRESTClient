package com.pcreations.restclient;

import java.sql.SQLException;

import com.pcreations.restclient.test.TestResource;

public interface DaoAccess<R> {

	abstract public void updateOrCreate(R resource)throws SQLException;
	abstract public TestResource findByName(String string);
	
}
