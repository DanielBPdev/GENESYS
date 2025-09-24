package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.ClasificacionEnum;

@XmlRootElement
public class ReintegroEmpleadorDTO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean decision;
	private Long idEmpleador;
	private String datosTemporales;
	private ClasificacionEnum tipoEmpleador;
	private String email;
	/**
	 * @return the decision
	 */
	public Boolean getDecision() {
		return decision;
	}
	/**
	 * @param decision the decision to set
	 */
	public void setDecision(Boolean decision) {
		this.decision = decision;
	}
	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}
	/**
	 * @param idEmpleador the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}
	/**
	 * @return the datosTemporales
	 */
	public String getDatosTemporales() {
		return datosTemporales;
	}
	/**
	 * @param datosTemporales the datosTemporales to set
	 */
	public void setDatosTemporales(String datosTemporales) {
		this.datosTemporales = datosTemporales;
	}
	/**
	 * @return the tipoEmpleador
	 */
	public ClasificacionEnum getTipoEmpleador() {
		return tipoEmpleador;
	}
	/**
	 * @param tipoEmpleador the tipoEmpleador to set
	 */
	public void setTipoEmpleador(ClasificacionEnum tipoEmpleador) {
		this.tipoEmpleador = tipoEmpleador;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
