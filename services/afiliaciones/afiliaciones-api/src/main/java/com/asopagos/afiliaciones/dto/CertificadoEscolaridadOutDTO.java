package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class CertificadoEscolaridadOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Fecha de recepción del certificado de escolaridad por la CCF
	 */
	private String fechaRecepcion;
	
	/**
    * Fecha de vencimiento del certificado de escolaridad vigente
    */
    private String fechaVencimientoCertificadoEscolar;
    
    /**
     * Indica si el beneficiario cuenta con certificado de escolaridad
     */
    private Boolean certificadoEscolaridad;

	/**
	 * 
	 */
	public CertificadoEscolaridadOutDTO() {
	}

	/**
	 * @return the fechaRecepcion
	 */
	public String getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaRecepcion(String fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	/**
	 * @return the fechaVencimientoCertificadoEscolar
	 */
	public String getFechaVencimientoCertificadoEscolar() {
		return fechaVencimientoCertificadoEscolar;
	}

	/**
	 * @param fechaVencimientoCertificadoEscolar the fechaVencimientoCertificadoEscolar to set
	 */
	public void setFechaVencimientoCertificadoEscolar(String fechaVencimientoCertificadoEscolar) {
		this.fechaVencimientoCertificadoEscolar = fechaVencimientoCertificadoEscolar;
	}

	/**
	 * @return the certificadoEscolaridad
	 */
	public Boolean getCertificadoEscolaridad() {
		return certificadoEscolaridad;
	}

	/**
	 * @param certificadoEscolaridad the certificadoEscolaridad to set
	 */
	public void setCertificadoEscolaridad(Boolean certificadoEscolaridad) {
		this.certificadoEscolaridad = certificadoEscolaridad;
	}


}
