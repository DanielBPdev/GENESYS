package com.asopagos.afiliaciones.personas.web.composite.dto;

import java.io.Serializable;

public class CancelacionSolicitudPersonasDTO implements Serializable{

	private static final long serialVersionUID = -697446921517161724L;
	
	private Long idSolicitud;

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

}
