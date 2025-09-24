package com.asopagos.coreaas.bpm.workitems.rest.properties.datamodel;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Service implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String host;
	private Integer port;
	private String name;
	private String protocol;
	private OperationsWrapper operation=new OperationsWrapper();
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public OperationsWrapper getOperations() {		
		return operation;
	}

	public void setOperations(OperationsWrapper operations) {
		this.operation = operations;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}
