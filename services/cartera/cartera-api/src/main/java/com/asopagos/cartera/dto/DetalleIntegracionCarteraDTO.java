package com.asopagos.cartera.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripción: </b> Clase que representa la estructura de datos detalle de
 * respuesta para los servicios de integración de cartera <br/>
 * <b>Historia de Usuario: </b> Portafolio de servicios - Cartera
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class DetalleIntegracionCarteraDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 4549854895890001L;

	/**
	 * Deuda presunta del aportante
	 */
	private BigDecimal deudaPresunta;

	/**
	 * Estado de cartera del aportante
	 */
	private EstadoCarteraEnum estadoCartera;

	/**
	 * Periodo de deuda del aportante
	 */
	private String periodoEnMora;

	/**
	 * Obtiene el valor de deudaPresunta
	 * 
	 * @return El valor de deudaPresunta
	 */
	public BigDecimal getDeudaPresunta() {
		return deudaPresunta;
	}

	/**
	 * Establece el valor de deudaPresunta
	 * 
	 * @param deudaPresunta
	 *            El valor de deudaPresunta por asignar
	 */
	public void setDeudaPresunta(BigDecimal deudaPresunta) {
		this.deudaPresunta = deudaPresunta;
	}

	/**
	 * Obtiene el valor de estadoCartera
	 * 
	 * @return El valor de estadoCartera
	 */
	public EstadoCarteraEnum getEstadoCartera() {
		return estadoCartera;
	}

	/**
	 * Establece el valor de estadoCartera
	 * 
	 * @param estadoCartera
	 *            El valor de estadoCartera por asignar
	 */
	public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
		this.estadoCartera = estadoCartera;
	}

	/**
	 * Obtiene el valor de periodoEnMora
	 * 
	 * @return El valor de periodoEnMora
	 */
	public String getPeriodoEnMora() {
		return periodoEnMora;
	}

	/**
	 * Establece el valor de periodoEnMora
	 * 
	 * @param periodoEnMora
	 *            El valor de periodoEnMora por asignar
	 */
	public void setPeriodoEnMora(String periodoEnMora) {
		this.periodoEnMora = periodoEnMora;
	}
}
