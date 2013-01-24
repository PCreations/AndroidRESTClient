package com.pcreations.restclient.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.pcreations.restclient.ResourceRepresentation;

@DatabaseTable(tableName = "addresses", daoClass=AddressDao.class) // annotation pour le nom de la table SQL
public class Address implements ResourceRepresentation<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2733956474171378006L;

	@DatabaseField(id=true)
	private int id;
	
	@DatabaseField
	private String addressName;

	@ForeignCollectionField(eager = false)
	private Collection<Note> notes = null;
	
	@DatabaseField
	private boolean transactingFlag;
	
	@DatabaseField
	private int state;
	
	@DatabaseField
	private int resultCode;
	
	public Address() {}
	
	public Address(int id, String addressName, Collection<Note> notes) {
		this.id = id;
		this.addressName = addressName;
		this.notes = notes;
	}

	/* GETTERS AND SETTERS */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String name) {
		this.addressName = name;
	}
	
	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}
 
	public List<Note> getNotes() {
		ArrayList<Note> notes = new ArrayList<Note>();
		for(Note note : this.notes) {
			notes.add(note);
		}
		return notes;
	}
	
	@Override
	public String toString() {
		String str = "Address [name=" + addressName +"]";
		if(null != notes) {
			for(Note n : notes) {
				str += n.toString();
			}
		}
		return str;
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
