package com.pcreations.restclient.test;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pcreations.restclient.ResourceRepresentation;

@DatabaseTable(tableName = "notes", daoClass=NoteDao.class) // annotation pour le nom de la table SQL
public class Note implements ResourceRepresentation<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7167598868315971181L;

	public static final String ADDRESS_ID = "address_id";

	@DatabaseField(id=true)

	private int id;
	
	@DatabaseField
	private String content;
	
	@DatabaseField
	private long privacy;
	
	@DatabaseField
	private boolean problem;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = ADDRESS_ID)
	private Address address;
	
	@DatabaseField
	private boolean transactingFlag;
	
	@DatabaseField
	private int state;
	
	@DatabaseField
	private int resultCode;
	
	public Note() {}
	
	public Note(int id, String content, long privacy, boolean problem, Address address){
		this.id = id;
		this.content = content;
		this.privacy = privacy;
		this.problem = problem;
		this.address = address;
	}

	/* GETTERS AND SETTERS */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public long getPrivacy() {
		return privacy;
	}

	public void setPrivacy(long privacy) {
		this.privacy = privacy;
	}
	
	public boolean getProblem() {
		return problem;
	}

	public void setProblem(boolean problem) {
		this.problem = problem;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "Note [content=" + content+"]";
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public int getResultCode() {
		return resultCode;
	}

	@Override
	public boolean getTransactingFlag() {
		return transactingFlag;
	}

	@Override
	public void setState(int state) {
		this.state = state;
	}

	@Override
	public void setTransactingFlag(boolean transacting) {
		this.transactingFlag = transacting;
	}

	@Override
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}