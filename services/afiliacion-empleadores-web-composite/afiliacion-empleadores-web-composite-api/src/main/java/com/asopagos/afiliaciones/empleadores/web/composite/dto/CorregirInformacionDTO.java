package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;

public class CorregirInformacionDTO implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Long idTarea;
	
	private Integer resultadoGestion;
	
	private String numeroRadicado;

	private Long idInstanciaProceso;

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
	 * @return the resultadoGestion
	 */
	public Integer getResultadoGestion() {
		return resultadoGestion;
	}

	/**
	 * @param resultadoGestion the resultadoGestion to set
	 */
	public void setResultadoGestion(Integer resultadoGestion) {
		this.resultadoGestion = resultadoGestion;
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
	 * @return the idInstanciaProceso
	 */
	public Long getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(Long idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}
}
