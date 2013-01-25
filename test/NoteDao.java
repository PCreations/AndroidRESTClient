package com.pcreations.restclient.test;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.pcreations.restclient.DaoAccess;

public class NoteDao extends BaseDaoImpl<Note, Integer> implements DaoAccess<Note>{
	public NoteDao(ConnectionSource connectionSource)
		throws SQLException {
		super(connectionSource, Note.class);
	}

	@Override
	public void updateOrCreate(Note resource) throws SQLException {
		createOrUpdate(resource);
	}

	@Override
	public <ID> Note findById(ID resourceId) throws SQLException {
		return queryForId((Integer) resourceId);
	}

}
