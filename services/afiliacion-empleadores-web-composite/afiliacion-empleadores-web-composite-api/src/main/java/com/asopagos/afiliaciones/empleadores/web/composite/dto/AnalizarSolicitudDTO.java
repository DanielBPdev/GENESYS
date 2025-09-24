package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;
import com.asopagos.dto.EscalamientoSolicitudDTO;

public class AnalizarSolicitudDTO implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Long idTarea;
	
	private EscalamientoSolicitudDTO solicitudEscalamiento;
	
	private String numeroRadicado;

	/**
	 * @return the idTarea
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * @param idTarea the idTarea to set
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * @return the numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * @param numeroRadicado the numeroRadicado to set
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/**
	 * @return the solicitudEscalamiento
	 */
	public EscalamientoSolicitudDTO getSolicitudEscalamiento() {
		return solicitudEscalamiento;
	}

	/**
	 * @param solicitudEscalamiento the solicitudEscalamiento to set
	 */
	public void setSolicitudEscalamiento(EscalamientoSolicitudDTO solicitudEscalamiento) {
		this.solicitudEscalamiento = solicitudEscalamiento;
	}
	
}
