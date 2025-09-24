package com.asopagos.coreaas.bpm.workitems.rest.properties.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class ServicesWrapper implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Service> service;

	public List<Service> getService() {
		return service;
	}

	public void setService(List<Service> service) {
		this.service = service;
	}

	public void addService(Service s) {
		if(service==null){
			service = new ArrayList<Service>();
		}
		service.add(s);
	}
}
