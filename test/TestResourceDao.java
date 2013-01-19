package com.pcreations.restclient.test;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.pcreations.restclient.DaoAccess;

public class TestResourceDao extends BaseDaoImpl<TestResource, Integer> implements DaoAccess<TestResource>{

	protected TestResourceDao(ConnectionSource connectionSource,
			Class<TestResource> dataClass) throws SQLException {
		super(connectionSource, dataClass);
		// TODO Auto-generated constructor stub
	}
	
	public TestResource findByName(String name) {
		TestResource resource = null;
		try {
			PreparedQuery<TestResource> q = queryBuilder().where().eq(TestResource.NAME_COLUMN, name).prepare();
			resource = queryForFirst(q);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resource;
	}

	@Override
	public void updateOrCreate(TestResource resource) throws SQLException {
		createOrUpdate(resource);
	}

}
