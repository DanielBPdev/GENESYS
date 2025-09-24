package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;

public class ConsultarConceptoEscalamientoDTO implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Integer resultadoEscalamiento;
	
	private String numeroRadicado;
	
	private Long idTarea;

	/**
	 * @return the resultadoEscalamiento
	 */
	public Integer getResultadoEscalamiento() {
		return resultadoEscalamiento;
	}

	/**
	 * @param resultadoEscalamiento the resultadoEscalamiento to set
	 */
	public void setResultadoEscalamiento(Integer resultadoEscalamiento) {
		this.resultadoEscalamiento = resultadoEscalamiento;
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
	
}
