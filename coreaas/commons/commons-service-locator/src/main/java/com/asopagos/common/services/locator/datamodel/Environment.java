package com.asopagos.common.services.locator.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "environment")
public class Environment {

	@XmlAttribute(required = true)
	protected String name;
	@XmlAttribute(required = true)
	protected String host;
	@XmlAttribute
	protected String port; 
	
	public String getEndpoint(){
		return host + ((port!=null)?":"+port:"");	
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
}
