package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase DTO para la respuesta del servicio CapturarResultadoReexpedicionBloqueo <br/>
 * <b>Módulo:</b> Control de cambíos Tarjeta (Isla)<br/>
 *
 * @author <a href="mailto:squintero@heinsohn.com.co"> Steven Quintero González</a>
 */
@XmlRootElement
public class ResultadoReexpedicionBloqueoOutDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id_proceso;
	
	private String estadoRecepcion;

	public ResultadoReexpedicionBloqueoOutDTO() {
	}

	public ResultadoReexpedicionBloqueoOutDTO(Long id_proceso, String estadoRecepcion) {
		this.id_proceso = id_proceso;
		this.estadoRecepcion = estadoRecepcion;
	}

	/**
	 * @return the id_proceso
	 */
	public Long getId_proceso() {
		return id_proceso;
	}

	/**
	 * @param id_proceso the id_proceso to set
	 */
	public void setId_proceso(Long id_proceso) {
		this.id_proceso = id_proceso;
	}

	/**
	 * @return the estadoRecepcion
	 */
	public String getEstadoRecepcion() {
		return estadoRecepcion;
	}

	/**
	 * @param estadoRecepcion the estadoRecepcion to set
	 */
	public void setEstadoRecepcion(String estadoRecepcion) {
		this.estadoRecepcion = estadoRecepcion;
	}
}
