package com.pcreations.restclient;

import java.io.Serializable;
import java.util.Date;

public interface ResourceRepresentation extends Serializable {
	
	abstract public int getResourceId();
	abstract public Date getResourceTimestamp();
	abstract public String getName();
	abstract public int getState();
	abstract public int getResultCode();
	abstract public boolean getTransactingFlag();
	abstract public void setResourceId(int id);
	abstract public void setResourceTimestamp(Date timestamp);
	abstract public void setName(String name);
	abstract public void setState(int stateRetrieving);
	abstract public void setTransactingFlag(boolean transacting);
	abstract public void setResultCode(int resultCode);
}