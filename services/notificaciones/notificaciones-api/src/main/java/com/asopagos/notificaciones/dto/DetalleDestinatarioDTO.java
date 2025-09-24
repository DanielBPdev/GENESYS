package com.asopagos.notificaciones.dto;

import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Persona;

@XmlRootElement
public class DetalleDestinatarioDTO{
	
	/**
	 * Entidad Persona a la que se envía el comunicado
	 */
	private Persona persona;
	
	/**
	 * Id del empleador en (caso de estar asociado al comunicado)
	 */
	private Long idEmpleador;
	
	/**
	 * Indica si la entidad objeto del comunicado autoriza el envío del mismo
	 */
	private Boolean autorizaEnvio;
	
	public DetalleDestinatarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public DetalleDestinatarioDTO(Persona persona, Long idEmpleador){
		this.persona = persona;
		this.idEmpleador = idEmpleador;
	}
	
	public DetalleDestinatarioDTO(Persona persona, Long idEmpleador, Ubicacion ubicacion){
		this.persona = persona;
		this.persona.setUbicacionPrincipal(ubicacion);
		this.idEmpleador = idEmpleador;
	}
	
	/**
     * @param persona
     * @param idEmpleador
     * @param autorizaEnvio
     */
    public DetalleDestinatarioDTO(Persona persona, Long idEmpleador, Ubicacion ubicacion, Boolean autorizaEnvio) {
        this.persona = persona;
        this.persona.setUbicacionPrincipal(ubicacion);
        this.idEmpleador = idEmpleador;
        this.autorizaEnvio = autorizaEnvio;
    }

    /**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
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
     * @return the autorizaEnvio
     */
    public Boolean getAutorizaEnvio() {
        return autorizaEnvio;
    }

    /**
     * @param autorizaEnvio the autorizaEnvio to set
     */
    public void setAutorizaEnvio(Boolean autorizaEnvio) {
        this.autorizaEnvio = autorizaEnvio;
    }
}
