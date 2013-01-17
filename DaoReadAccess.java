package com.pcreations.restclient;

public interface DaoReadAccess {
	abstract public void create(ResourceRepresentation resource);
	abstract public void save(ResourceRepresentation resource);
	abstract public void update(ResourceRepresentation resource);
}
