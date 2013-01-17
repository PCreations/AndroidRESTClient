package com.pcreations.restclient;

import java.util.List;

abstract public class ResourcesManager {

	private List<DaoReadAccess> mDAOs;
	
	abstract public DaoReadAccess<? extends ResourceRepresentation> getResourceDao(ResourceRepresentation resource);
	
	public interface ResourceRepresentation {
		abstract public String getName();
		abstract public RequestState getState();
		abstract public int getResultCode();
		abstract public void setName(String name);
		abstract public void setState(RequestState state);
		abstract public void setResultCode(int resultCode);
	}
	
	public interface DaoReadAccess<R> {
		abstract public void create(R resource);
		abstract public void save(R resource);
		abstract public void update(R resource);
	}
	
}
