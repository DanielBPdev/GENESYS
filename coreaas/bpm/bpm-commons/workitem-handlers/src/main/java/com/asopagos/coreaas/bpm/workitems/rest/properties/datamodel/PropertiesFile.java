package com.asopagos.coreaas.bpm.workitems.rest.properties.datamodel;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PropertiesFile implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private ServicesWrapper services=new ServicesWrapper();

	public ServicesWrapper getServices() {
		return services;
	}

	public void setServices(ServicesWrapper services) {
		this.services = services;
	}
}
