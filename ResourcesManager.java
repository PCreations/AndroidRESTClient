package com.pcreations.restclient;

abstract public class ResourcesManager {
	
	public ResourcesManager() {
		super();
	}

	abstract public void update(ResourceRepresentation ressource);
	abstract public void create(ResourceRepresentation ressource);
	
}
