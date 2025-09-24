package com.asopagos.usuarios.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO para el servicio consultar todos los roles existentes
 * <b>Historia de Usuario:158-Seguridad</b>
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@XmlRootElement
public class RolDTO implements Serializable {

	protected String id;
	protected String name;
	protected String description;
	protected Boolean scopeParamRequired;
	protected Boolean composite;
	private Boolean clientRole;
	private String containerId;
	private boolean habilitado;

	public RolDTO() {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the scopeParamRequired
	 */
	public Boolean getScopeParamRequired() {
		return scopeParamRequired;
	}

	/**
	 * @param scopeParamRequired
	 *            the scopeParamRequired to set
	 */
	public void setScopeParamRequired(Boolean scopeParamRequired) {
		this.scopeParamRequired = scopeParamRequired;
	}

	/**
	 * @return the composite
	 */
	public Boolean isComposite() {
		return composite;
	}

	/**
	 * @param composite
	 *            the composite to set
	 */
	public void setComposite(Boolean composite) {
		this.composite = composite;
	}

	/**
	 * @return the clientRole
	 */
	public Boolean getClientRole() {
		return clientRole;
	}

	/**
	 * @param clientRole
	 *            the clientRole to set
	 */
	public void setClientRole(Boolean clientRole) {
		this.clientRole = clientRole;
	}

	/**
	 * @return the containerId
	 */
	public String getContainerId() {
		return containerId;
	}

	/**
	 * @param containerId
	 *            the containerId to set
	 */
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	/**
	 * @return the habilitado
	 */
	public boolean isHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado
	 *            the habilitado to set
	 */
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

}
