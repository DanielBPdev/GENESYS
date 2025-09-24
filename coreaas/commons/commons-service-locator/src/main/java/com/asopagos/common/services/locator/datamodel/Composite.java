package com.asopagos.common.services.locator.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Composite implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@XmlAttribute
	public String name;	
	private List<ServiceRef> serviceRef = new ArrayList<ServiceRef>();

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceRef> getServiceRef() {
		return serviceRef;
	}

	public void setServiceRef(List<ServiceRef> serviceRefs) {
		this.serviceRef = serviceRefs;
	}

}
