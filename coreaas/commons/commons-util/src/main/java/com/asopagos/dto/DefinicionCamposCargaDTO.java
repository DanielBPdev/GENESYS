package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO usado en la proyección de la consulta
 * 
 * @author jusanchez
 *
 */
@XmlRootElement
public class DefinicionCamposCargaDTO implements Serializable {
	
	private String label;
	private String name;
	
	public DefinicionCamposCargaDTO() {
		
	}
	
	public DefinicionCamposCargaDTO(String label, String name) {
		this.label = label;
		this.name = name;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
