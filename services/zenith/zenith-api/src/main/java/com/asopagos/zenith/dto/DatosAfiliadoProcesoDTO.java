package com.asopagos.zenith.dto;

import java.io.Serializable;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class DatosAfiliadoProcesoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TipoIdentificacionEnum tipoId;
	private String numeroId;
	
	/**
	 * @param tipoId
	 * @param numeroId
	 */
	public DatosAfiliadoProcesoDTO(TipoIdentificacionEnum tipoId, String numeroId) {
		this.tipoId = tipoId;
		this.numeroId = numeroId;
	}

	/**
	 * @return the tipoId
	 */
	public TipoIdentificacionEnum getTipoId() {
		return tipoId;
	}

	/**
	 * @param tipoId the tipoId to set
	 */
	public void setTipoId(TipoIdentificacionEnum tipoId) {
		this.tipoId = tipoId;
	}

	/**
	 * @return the numeroId
	 */
	public String getNumeroId() {
		return numeroId;
	}

	/**
	 * @param numeroId the numeroId to set
	 */
	public void setNumeroId(String numeroId) {
		this.numeroId = numeroId;
	}
	
	

}
