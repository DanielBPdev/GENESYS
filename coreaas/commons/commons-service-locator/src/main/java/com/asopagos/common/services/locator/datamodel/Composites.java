package com.asopagos.common.services.locator.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Composites implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private List<Composite> composite = new ArrayList<Composite>();

	private List<Service> service = new ArrayList<Service>();

	public List<Composite> getComposite() {
		return composite;
	}

	public void setComposite(List<Composite> composite) {
		this.composite = composite;
	}

	public List<Service> getService() {
		return service;
	}

	public void setService(List<Service> service) {
		this.service = service;
	}

}
