package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;

@XmlRootElement
public class InfoPersonaReexpedicionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	private String nombreCompleto;
	private String numeroTarjeta;
	private BigDecimal saldoTarjeta;
	private EstadoTarjetaMultiserviciosEnum estadoTarjeta;
	private Long idMEdioPagoParaActualizar ;
	private String numeroTarjetaPersona;
	
	
	
	public InfoPersonaReexpedicionDTO() {
	}

	public InfoPersonaReexpedicionDTO(Long idPersona, String nombreCompleto, String numeroTarjeta, String estadoTarjeta) {
		this.idPersona = idPersona;
		this.numeroTarjeta = numeroTarjeta;
		this.nombreCompleto = nombreCompleto;
		this.estadoTarjeta = estadoTarjeta != null ? EstadoTarjetaMultiserviciosEnum.valueOf(estadoTarjeta) : null;
	}
	
	
	public InfoPersonaReexpedicionDTO(Long idPersona, String numeroTarjeta, String estadoTarjeta) {
		this.idPersona = idPersona;
		this.numeroTarjeta = numeroTarjeta;
		this.estadoTarjeta = estadoTarjeta != null ? EstadoTarjetaMultiserviciosEnum.valueOf(estadoTarjeta) : null;
	}
	
	public InfoPersonaReexpedicionDTO(Long idPersona, String numeroTarjeta, BigDecimal saldoTarjeta,
			String estadoTarjeta) {
		this.idPersona = idPersona;
		this.numeroTarjeta = numeroTarjeta;
		this.saldoTarjeta = saldoTarjeta;
		this.estadoTarjeta = estadoTarjeta != null ? EstadoTarjetaMultiserviciosEnum.valueOf(estadoTarjeta) : null;
	}


	public InfoPersonaReexpedicionDTO(Long idPersona, String nombreCompleto, String numeroTarjeta, BigDecimal saldoTarjeta, EstadoTarjetaMultiserviciosEnum estadoTarjeta, Long idMEdioPagoParaActualizar) {
		this.idPersona = idPersona;
		this.nombreCompleto = nombreCompleto;
		this.numeroTarjeta = numeroTarjeta;
		this.saldoTarjeta = saldoTarjeta;
		this.estadoTarjeta = estadoTarjeta;
		this.idMEdioPagoParaActualizar = idMEdioPagoParaActualizar;
	}


	public InfoPersonaReexpedicionDTO(Long idPersona, String nombreCompleto, String numeroTarjeta, String estadoTarjeta, Long idMEdioPagoParaActualizar, String numeroTarjetaPersona) {
		this.idPersona = idPersona;
		this.nombreCompleto = nombreCompleto;
		this.numeroTarjeta = numeroTarjeta;
		this.estadoTarjeta = EstadoTarjetaMultiserviciosEnum.valueOf(estadoTarjeta);
		this.idMEdioPagoParaActualizar = idMEdioPagoParaActualizar;
		this.numeroTarjetaPersona = numeroTarjetaPersona;
	}

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}
	
	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	/**
	 * @return the numeroTarjeta
	 */
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	
	/**
	 * @param numeroTarjeta the numeroTarjeta to set
	 */
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	
	/**
	 * @return the saldoTarjeta
	 */
	public BigDecimal getSaldoTarjeta() {
		return saldoTarjeta;
	}
	
	/**
	 * @param saldoTarjeta the saldoTarjeta to set
	 */
	public void setSaldoTarjeta(BigDecimal saldoTarjeta) {
		this.saldoTarjeta = saldoTarjeta;
	}
	
	/**
	 * @return the estadoTarjeta
	 */
	public EstadoTarjetaMultiserviciosEnum getEstadoTarjeta() {
		return estadoTarjeta;
	}
	
	/**
	 * @param estadoTarjeta the estadoTarjeta to set
	 */
	public void setEstadoTarjeta(EstadoTarjetaMultiserviciosEnum estadoTarjeta) {
		this.estadoTarjeta = estadoTarjeta;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public Long getIdMEdioPagoParaActualizar() {
		return this.idMEdioPagoParaActualizar;
	}

	public void setIdMEdioPagoParaActualizar(Long idMEdioPagoParaActualizar) {
		this.idMEdioPagoParaActualizar = idMEdioPagoParaActualizar;
	}


	public String getNumeroTarjetaPersona() {
		return this.numeroTarjetaPersona;
	}

	public void setNumeroTarjetaPersona(String numeroTarjetaPersona) {
		this.numeroTarjetaPersona = numeroTarjetaPersona;
	}


	@Override
	public String toString() {
		return "{" +
			" idPersona='" + getIdPersona() + "'" +
			", nombreCompleto='" + getNombreCompleto() + "'" +
			", numeroTarjeta='" + getNumeroTarjeta() + "'" +
			", saldoTarjeta='" + getSaldoTarjeta() + "'" +
			", estadoTarjeta='" + getEstadoTarjeta() + "'" +
			", idMEdioPagoParaActualizar='" + getIdMEdioPagoParaActualizar() + "'" +
			", numeroTarjetaPersona='" + getNumeroTarjetaPersona() + "'" +
			"}";
	}


}