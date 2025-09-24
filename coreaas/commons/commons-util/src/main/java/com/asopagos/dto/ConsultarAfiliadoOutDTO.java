package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConsultarAfiliadoOutDTO implements Serializable {

	private IdentificacionUbicacionPersonaDTO identificacionUbicacionPersona;
	
	private List<InformacionLaboralTrabajadorDTO> informacionLaboralTrabajador;
	
	private List<IdentificacionUbicacionPersonaDTO> lstIdentificacionUbicacionPersona;

	/**
	 * @return the identificacionUbicacionPersona
	 */
	public IdentificacionUbicacionPersonaDTO getIdentificacionUbicacionPersona() {
		return identificacionUbicacionPersona;
	}

	/**
	 * @param identificacionUbicacionPersona the identificacionUbicacionPersona to set
	 */
	public void setIdentificacionUbicacionPersona(IdentificacionUbicacionPersonaDTO identificacionUbicacionPersona) {
		this.identificacionUbicacionPersona = identificacionUbicacionPersona;
	}

	/**
	 * @return the informacionLaboralTrabajador
	 */
	public List<InformacionLaboralTrabajadorDTO> getInformacionLaboralTrabajador() {
		return informacionLaboralTrabajador;
	}

	/**
	 * @param informacionLaboralTrabajador the informacionLaboralTrabajador to set
	 */
	public void setInformacionLaboralTrabajador(List<InformacionLaboralTrabajadorDTO> informacionLaboralTrabajador) {
		this.informacionLaboralTrabajador = informacionLaboralTrabajador;
	}

	/**
	 * @return the lstIdentificacionUbicacionPersona
	 */
	public List<IdentificacionUbicacionPersonaDTO> getLstIdentificacionUbicacionPersona() {
		return lstIdentificacionUbicacionPersona;
	}

	/**
	 * @param lstIdentificacionUbicacionPersona the lstIdentificacionUbicacionPersona to set
	 */
	public void setLstIdentificacionUbicacionPersona(
			List<IdentificacionUbicacionPersonaDTO> lstIdentificacionUbicacionPersona) {
		this.lstIdentificacionUbicacionPersona = lstIdentificacionUbicacionPersona;
	}

}
