package com.pcreations.restclient;

import java.util.List;

abstract public class ResourcesManager {

	protected List<DaoReadAccess> mDAOs;
	
	public ResourcesManager(List<DaoReadAccess> mDAOs) {
		super();
		this.mDAOs = mDAOs;
	}

	abstract public DaoReadAccess getResourceDao(ResourceRepresentation resource);
	
}
