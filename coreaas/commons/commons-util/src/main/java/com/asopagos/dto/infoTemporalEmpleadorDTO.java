package com.asopagos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class infoTemporalEmpleadorDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private EmpleadorDTO empleador;
	
	private String dominio;
	
	private InformacionContactoDTO informacionContacto;
	
	private String tipoEmpleador;
	
	private Boolean correoEmpleadorNuevo;
	
	public infoTemporalEmpleadorDTO() {
	}

	/**
	 * @return the empleador
	 */
	public EmpleadorDTO getEmpleador() {
		return empleador;
	}

	/**
	 * @param empleador the empleador to set
	 */
	public void setEmpleador(EmpleadorDTO empleador) {
		this.empleador = empleador;
	}

	/**
	 * @return the dominio
	 */
	public String getDominio() {
		return dominio;
	}

	/**
	 * @param dominio the dominio to set
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	/**
	 * @return the informacionContacto
	 */
	public InformacionContactoDTO getInformacionContacto() {
		return informacionContacto;
	}

	/**
	 * @param informacionContacto the informacionContacto to set
	 */
	public void setInformacionContacto(InformacionContactoDTO informacionContacto) {
		this.informacionContacto = informacionContacto;
	}

	/**
	 * @return the tipoEmpleador
	 */
	public String getTipoEmpleador() {
		return tipoEmpleador;
	}

	/**
	 * @param tipoEmpleador the tipoEmpleador to set
	 */
	public void setTipoEmpleador(String tipoEmpleador) {
		this.tipoEmpleador = tipoEmpleador;
	}

	/**
	 * @return the correoEmpleadorNuevo
	 */
	public Boolean getCorreoEmpleadorNuevo() {
		return correoEmpleadorNuevo;
	}

	/**
	 * @param correoEmpleadorNuevo the correoEmpleadorNuevo to set
	 */
	public void setCorreoEmpleadorNuevo(Boolean correoEmpleadorNuevo) {
		this.correoEmpleadorNuevo = correoEmpleadorNuevo;
	}
	
	
}
