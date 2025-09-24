package com.asopagos.clienteanibol.dto;

import java.io.Serializable;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class ConsultaSubsidioFosfecDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoIdentificacionEnum tipoIdentificacion;
	private String numeroIdentificacion;
	private String codigoCCF;
	
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	public String getCodigoCCF() {
		return codigoCCF;
	}
	public void setCodigoCCF(String codigoCCF) {
		this.codigoCCF = codigoCCF;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ConsultaSubsidioFosfecDTO (){}

}
