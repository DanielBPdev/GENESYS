package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Descripcion:</b> DTO que contiene la información de descuentos asociados
 * a una liquidación y trabajador.<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - Vista 360 Persona<br/>
 *
 * @author <a href="mailto:flopez@heinsohn.com.co">Fabian López</a>
 */
public class ConsultaDescuentosSubsidioTrabajadorGrupoDTO implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;

	/** Indica el Número del Grupo Familiar*/
	private Byte numeroGrupoFamiliar;

	/** Monto descontado */
	private BigDecimal montoDescontado;
	
	/**Identificador de la Entidad de descuento*/
	private Long idEntidadDescuento;
	
	/** Nombre de la entidad de descuento */
	private String nombreEntidadDescuento;
	
	/**Código de Referencia*/
	private String codigoReferencia;

	/**
	 * @return the numeroGrupoFamiliar
	 */
	public Byte getNumeroGrupoFamiliar() {
		return numeroGrupoFamiliar;
	}

	/**
	 * @param numeroGrupoFamiliar the numeroGrupoFamiliar to set
	 */
	public void setNumeroGrupoFamiliar(Byte numeroGrupoFamiliar) {
		this.numeroGrupoFamiliar = numeroGrupoFamiliar;
	}

	/**
	 * @return the montoDescontado
	 */
	public BigDecimal getMontoDescontado() {
		return montoDescontado;
	}

	/**
	 * @param montoDescontado the montoDescontado to set
	 */
	public void setMontoDescontado(BigDecimal montoDescontado) {
		this.montoDescontado = montoDescontado;
	}

	/**
	 * @return the nombreEntidadDescuento
	 */
	public String getNombreEntidadDescuento() {
		return nombreEntidadDescuento;
	}

	/**
	 * @param nombreEntidadDescuento the nombreEntidadDescuento to set
	 */
	public void setNombreEntidadDescuento(String nombreEntidadDescuento) {
		this.nombreEntidadDescuento = nombreEntidadDescuento;
	}

	/**
	 * @return the codigoReferencia
	 */
	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	/**
	 * @param codigoReferencia the codigoReferencia to set
	 */
	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	/**
	 * @return the idEntidadDescuento
	 */
	public Long getIdEntidadDescuento() {
		return idEntidadDescuento;
	}

	/**
	 * @param idEntidadDescuento the idEntidadDescuento to set
	 */
	public void setIdEntidadDescuento(Long idEntidadDescuento) {
		this.idEntidadDescuento = idEntidadDescuento;
	}
}
