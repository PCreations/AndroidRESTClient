package com.pcreations.restclient.test;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pcreations.restclient.ResourceRepresentation;

@DatabaseTable(tableName = "notes", daoClass=NoteDao.class) // annotation pour le nom de la table SQL
@JsonSerialize(using=NoteJsonSerializer.class)
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
	
	@DatabaseField(canBeNull = true)
	private String picture;
	
	@DatabaseField
	private boolean privacy;
	
	@DatabaseField
	private long imei;
	
	@DatabaseField
	private int order;
	
	@DatabaseField
	private boolean problem;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = ADDRESS_ID)
	private Address address;
	
	@DatabaseField
	private boolean transactingFlag;
	
	@DatabaseField
	private int state;
	
	private int address_id;
	
	@DatabaseField
	private int resultCode;
	
	public Note() {}
	
	public Note(int id, String content, String picture, boolean privacy, long imei, boolean problem, Address address, int order){
		this.id = id;
		this.content = content;
		this.picture = picture;
		this.privacy = privacy;
		this.imei = imei;
		this.problem = problem;
		this.address = address;
		this.order = order;
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
	
	public boolean getPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	
	public String getPicture() {
		return picture;
	}

	public long getImei() {
		return imei;
	}

	public int getOrder() {
		return order;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setImei(long imei) {
		this.imei = imei;
	}

	public void setOrder(int order) {
		this.order = order;
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

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	@Override
	public String toString() {
		return "Note "+id+"[isProblem="+problem+"] [privacy="+privacy+"] [imei="+imei+"] [content=" + content+"] [picture="+picture+"] [order="+order+"]";
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
