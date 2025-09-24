package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;

public class GestionarPNCSDTO  implements Serializable{
		
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String numeroRadicado;
	
	private Long idTarea;
	
	private Integer resultadoGestion;

	public String getNumeroRadicado() {
		return numeroRadicado;
	}

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
}
