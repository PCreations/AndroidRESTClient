package com.pcreations.restclient.test;

import java.sql.SQLException;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.pcreations.restclient.DaoAccess;
import com.pcreations.restclient.RestService;

public class TestResourceDao extends BaseDaoImpl<TestResource, Integer> implements DaoAccess<TestResource>{

	public TestResourceDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, TestResource.class);
		// TODO Auto-generated constructor stub
	}
	
	public TestResource findByName(String name) throws SQLException {
		TestResource resource = null;
		PreparedQuery<TestResource> q = queryBuilder().where().eq(TestResource.NAME_COLUMN, name).prepare();
		resource = queryForFirst(q);
		return resource;
	}

	@Override
	public void updateOrCreate(TestResource resource) throws SQLException {
		createOrUpdate(resource);
	}

	@Override
	public TestResource findById(int resourceId) throws SQLException {
		return queryForId(resourceId);
	}
	
	@Override
	public TestResource findByNameAndId(String name, int resourceId) throws SQLException {
		TestResource resource = null;
		PreparedQuery<TestResource> q = queryBuilder().where()
				.eq(TestResource.NAME_COLUMN, name)
				.and()
				.eq(TestResource.RESOURCE_ID_COLUMN, resourceId)
				.prepare();
		resource = queryForFirst(q);
		Log.i(RestService.TAG, "findByNameAndId statement = " + q.toString());
		return resource;
	}

}
