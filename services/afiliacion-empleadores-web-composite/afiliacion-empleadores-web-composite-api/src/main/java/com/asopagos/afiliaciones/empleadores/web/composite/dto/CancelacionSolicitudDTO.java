package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;

public class CancelacionSolicitudDTO implements Serializable{

	private static final long serialVersionUID = -697446921517161723L;
	
	private Long idSolicitud;

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

}
