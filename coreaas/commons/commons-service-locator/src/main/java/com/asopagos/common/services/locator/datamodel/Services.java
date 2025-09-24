package com.asopagos.common.services.locator.datamodel;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "service", "environment" })
@XmlRootElement(name = "services")
public class Services {
    
	@XmlElement(required = true)
	protected List<Environment> environment;     

	@XmlElement(required = true)
	protected List<Service> service;
   
	public List<Environment> getEnviroment() {
		if (environment == null) {
			environment = new ArrayList<>();
		}
		return environment;
	}

	public void setEnvironment(List<Environment> environment) {
		this.environment = environment;
	}

	public List<Service> getService() {
		if (service == null) {
			service = new ArrayList<>();
		}
		return service;
	}

	public void setService(List<Service> service) {
		this.service = service;
	}

	public Environment getEnvironment(String environment) {
		for (Environment e : this.environment) {
			if (e.getName().equals(environment)) {
				return e;
			}
		}
		return null;
	}
    
}
