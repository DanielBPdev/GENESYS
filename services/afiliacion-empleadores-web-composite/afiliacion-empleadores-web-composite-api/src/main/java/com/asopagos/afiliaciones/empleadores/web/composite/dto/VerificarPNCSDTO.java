package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;

public class VerificarPNCSDTO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer resultadoVerifGestion;
	
	private String numeroRadicado;
	
	private Long idTarea;
	

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

	/**
	 * @return the resultadoVerifGestion
	 */
	public Integer getResultadoVerifGestion() {
		return resultadoVerifGestion;
	}

	/**
	 * @param resultadoVerifGestion the resultadoVerifGestion to set
	 */
	public void setResultadoVerifGestion(Integer resultadoVerifGestion) {
		this.resultadoVerifGestion = resultadoVerifGestion;
	}

}
