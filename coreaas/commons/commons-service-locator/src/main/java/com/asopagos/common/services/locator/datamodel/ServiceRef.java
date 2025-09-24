package com.asopagos.common.services.locator.datamodel;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceRef implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "service_name")
	public String serviceName;
	

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
