package com.pcreations.restclient;

import java.sql.SQLException;
import java.util.List;

public interface DaoAccess {

	abstract public void updateOrCreate(ResourceRepresentation<?> resource)throws SQLException;
	abstract public ResourceRepresentation<?> findByName(String string) throws SQLException;
	abstract public ResourceRepresentation<?> findById(int resourceId) throws SQLException;
	abstract public ResourceRepresentation<?> findByNameAndId(String name, int resourceID) throws SQLException;
	abstract public List<ResourceRepresentation<?>> queryForAll() throws SQLException;
	
}
