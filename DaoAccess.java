package com.pcreations.restclient;

import java.sql.SQLException;
import java.util.List;

public interface DaoAccess<T,ID> {

	abstract public void updateOrCreate(T resource)throws SQLException;
	abstract public T findByName(String string) throws SQLException;
	abstract public T findById(ID resourceId) throws SQLException;
	abstract public List<T> queryForAll() throws SQLException;
	
}
