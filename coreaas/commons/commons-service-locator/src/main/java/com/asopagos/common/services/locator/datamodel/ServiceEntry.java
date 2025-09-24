package com.asopagos.common.services.locator.datamodel;

public class ServiceEntry {

	private String endpoint="";
	
	private String path="";

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

    @Override
    public String toString() {
        return endpoint + "/" + path;
    }
    
}
