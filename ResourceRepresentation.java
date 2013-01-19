package com.pcreations.restclient;

public interface ResourceRepresentation {
	
	abstract public String getName();
	abstract public RequestState getState();
	abstract public int getResultCode();
	abstract public void setName(String name);
	abstract public void setState(RequestState state);
	abstract public void setResultCode(int resultCode);
}