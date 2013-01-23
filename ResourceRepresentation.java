package com.pcreations.restclient;

import java.io.Serializable;
import java.util.Date;

public interface ResourceRepresentation<ID> extends Serializable {
	
	abstract public ID getId();
	abstract public int getState();
	abstract public int getResultCode();
	abstract public boolean getTransactingFlag();
	abstract public void setId(ID id);
	abstract public void setState(int stateRetrieving);
	abstract public void setTransactingFlag(boolean transacting);
	abstract public void setResultCode(int resultCode);
}