package com.pcreations.restclient.test;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pcreations.restclient.ResourceRepresentation;

@DatabaseTable(tableName = "resources", daoClass=TestResourceDao.class)
public class TestResource implements ResourceRepresentation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3031641666317099499L;

	public static final String NAME_COLUMN = "name";

	@DatabaseField(generatedId=true)
	private int id;
	
	@DatabaseField
	private int mResourceId;
	
	@DatabaseField(columnName=NAME_COLUMN)
	private String mName;
	
	@DatabaseField
	private int mState;
	
	@DatabaseField
	private boolean mTransactingFlag;
	
	@DatabaseField
	private int mResultCode;
	
	public TestResource() {
		super();
	}
	
	public TestResource(String mName) {
		super();
		this.mName = mName;
	}
	
	public TestResource(int id, String mName, int mState,
			int mResultCode) {
		super();
		this.id = id;
		this.mName = mName;
		this.mState = mState;
		this.mResultCode = mResultCode;
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public int getState() {
		return mState;
	}

	@Override
	public int getResultCode() {
		return mResultCode;
	}

	@Override
	public void setName(String name) {
		mName = name;
	}

	@Override
	public void setState(int state) {
		mState = state;
	}

	@Override
	public void setResultCode(int resultCode) {
		mResultCode = resultCode;
	}

	public String toString() {
		return "name[" + mName + "], mState = [" + String.valueOf(mState) + "], mResultCode = [" + String.valueOf(mResultCode) + "]";
	}

	@Override
	public int getResourceId() {
		return mResourceId;
	}

	@Override
	public void setResourceId(int id) {
		mResourceId = id;
		
	}

	@Override
	public void setTransactingFlag(boolean transacting) {
		mTransactingFlag = transacting;
	}
}
