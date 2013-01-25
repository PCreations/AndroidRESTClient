package com.pcreations.restclient.test;


import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.pcreations.restclient.DaoAccess;


public class AddressDao extends BaseDaoImpl<Address, Integer> implements DaoAccess<Address>{
	public AddressDao(ConnectionSource connectionSource)
		throws SQLException {
		super(connectionSource, Address.class);
	}

	@Override
	public void updateOrCreate(Address resource) throws SQLException {
		createOrUpdate(resource);
	}

	@Override
	public <ID> Address findById(ID resourceId) throws SQLException {
		return queryForId((Integer) resourceId);
	}

}
 
