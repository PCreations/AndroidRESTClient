package com.pcreations.restclient.test;

import java.util.Date;

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
	
	public TestResource(String mName, int mResourceId) {
		super();
		this.mName = mName;
		this.mResourceId = mResourceId;
	}
	
	public TestResource(int id, int mResourceId, Date mTimestamp, String mName, int mState,
			boolean mTransactingFlag, int mResultCode) {
		super();
		this.id = id;
		this.mResourceId = mResourceId;
		this.mName = mName;
		this.mState = mState;
		this.mTransactingFlag = mTransactingFlag;
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
		return "name[" + mName + "], resourceID = " + String.valueOf(mResourceId) + "mState = [" + String.valueOf(mState) + "], mResultCode = [" + String.valueOf(mResultCode) + "]" + "mTransactingFlas = " + String.valueOf(mTransactingFlag);
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

	@Override
	public boolean getTransactingFlag() {
		return mTransactingFlag;
	}

}
