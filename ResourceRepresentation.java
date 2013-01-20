package com.pcreations.restclient;

import java.io.Serializable;

public interface ResourceRepresentation extends Serializable {
	
	abstract public int getResourceId();
	abstract public void setResourceId(int id);
	abstract public String getName();
	abstract public int getState();
	abstract public int getResultCode();
	abstract public void setName(String name);
	abstract public void setState(int stateRetrieving);
	abstract public void setTransactingFlag(boolean transacting);
	abstract public void setResultCode(int resultCode);
}