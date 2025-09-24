package com.asopagos.fovis.composite.dto;

import java.io.Serializable;

/**
 * DTO con los datos de la solicitud para cancelarla por TimeOut
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class CancelacionSolicitudPostulacionDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la Solicitud Global
	 */
	private Long idSolicitud;
	
	
	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

}
