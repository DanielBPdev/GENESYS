package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;

public class GruposMedioTarjetaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idGrupo;
	
	private Short relacionGrupo;


	public GruposMedioTarjetaDTO(){
		
	}
	
	public GruposMedioTarjetaDTO(Long idGrupo, Short relacionGrupo) {
		this.idGrupo = idGrupo;
		this.relacionGrupo = relacionGrupo;
	}

	/**
	 * @return the idGrupo
	 */
	public Long getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return the relacionGrupo
	 */
	public Short getRelacionGrupo() {
		return relacionGrupo;
	}

	/**
	 * @param relacionGrupo the relacionGrupo to set
	 */
	public void setRelacionGrupo(Short relacionGrupo) {
		this.relacionGrupo = relacionGrupo;
	}
}
