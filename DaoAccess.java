package com.pcreations.restclient;

import java.sql.SQLException;

public interface DaoAccess<R> {

	abstract public void updateOrCreate(R resource)throws SQLException;
	
}
