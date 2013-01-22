package com.pcreations.restclient;

import java.sql.SQLException;

public interface DaoAccess<R> {

	abstract public void updateOrCreate(R resource)throws SQLException;
	abstract public R findByName(String string) throws SQLException;
	abstract public R findById(int resourceId) throws SQLException;
	abstract public R findByNameAndId(String name, int resourceID) throws SQLException;
	
}
