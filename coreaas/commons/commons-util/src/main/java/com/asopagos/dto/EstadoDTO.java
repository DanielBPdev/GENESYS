package com.asopagos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

@XmlRootElement
public class EstadoDTO implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Representa el tipo de identificación
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Representa el numero de identificación
	 */
	private String numeroIdentificacion;
	
	/**
	 * Representa el estado de una persona o empleador
	 */
	private EstadoAfiliadoEnum estado;
	
	public EstadoDTO() {
	}

	public EstadoDTO(String estado) {
		this.estado = estado != null ? EstadoAfiliadoEnum.valueOf(estado) : null;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public EstadoAfiliadoEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoAfiliadoEnum estado) {
		this.estado = estado;
	}
	
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
