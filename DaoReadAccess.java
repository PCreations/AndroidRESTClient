package com.pcreations.restclient;

import java.sql.SQLException;

public interface DaoReadAccess<T> {
	/* Must return number of rows affected */
	abstract public int create(T resource) throws SQLException;
	abstract public int update(T resource) throws SQLException;
}
