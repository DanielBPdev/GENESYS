package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;

import com.asopagos.enumeraciones.fovis.ModalidadEnum;

/**
 * <b>Descripción: </b> DTO que almacena el resumen de asignación FOVIS FOVIS
 * <br/>
 * <b>Historia de Usuario: </b> HU-047
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class ResumenAsignacionDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1879875454454L;

	/**
	 * Modalidad
	 */
	private ModalidadEnum modalidad;

	/**
	 * Cantidad de hogares asignados por modalidad
	 */
	private Integer cantidadHogaresAsignados;

	/**
	 * Cantidad de hogares calificados por modalidad
	 */
	private Integer cantidadHogaresCalificados;

	/**
	 * Total valor SFV asignado por modalidad
	 */
	private BigDecimal valorSubsidioAsignado;

	/**
	 * Constructor
	 */
	public ResumenAsignacionDTO() {
		this.cantidadHogaresAsignados = 0;
		this.cantidadHogaresCalificados = 0;
		this.valorSubsidioAsignado = BigDecimal.ZERO;
	}

	/**
	 * Obtiene el valor de modalidad
	 * 
	 * @return El valor de modalidad
	 */
	public ModalidadEnum getModalidad() {
		return modalidad;
	}

	/**
	 * Establece el valor de modalidad
	 * 
	 * @param modalidad
	 *            El valor de modalidad por asignar
	 */
	public void setModalidad(ModalidadEnum modalidad) {
		this.modalidad = modalidad;
	}

	/**
	 * Obtiene el valor de cantidadHogaresAsignados
	 * 
	 * @return El valor de cantidadHogaresAsignados
	 */
	public Integer getCantidadHogaresAsignados() {
		return cantidadHogaresAsignados;
	}

	/**
	 * Establece el valor de cantidadHogaresAsignados
	 * 
	 * @param cantidadHogaresAsignados
	 *            El valor de cantidadHogaresAsignados por asignar
	 */
	public void setCantidadHogaresAsignados(Integer cantidadHogaresAsignados) {
		this.cantidadHogaresAsignados = cantidadHogaresAsignados;
	}

	/**
	 * Obtiene el valor de cantidadHogaresCalificados
	 * 
	 * @return El valor de cantidadHogaresCalificados
	 */
	public Integer getCantidadHogaresCalificados() {
		return cantidadHogaresCalificados;
	}

	/**
	 * Establece el valor de cantidadHogaresCalificados
	 * 
	 * @param cantidadHogaresCalificados
	 *            El valor de cantidadHogaresCalificados por asignar
	 */
	public void setCantidadHogaresCalificados(Integer cantidadHogaresCalificados) {
		this.cantidadHogaresCalificados = cantidadHogaresCalificados;
	}

	/**
	 * Obtiene el valor de valorSubsidioAsignado
	 * 
	 * @return El valor de valorSubsidioAsignado
	 */
	public BigDecimal getValorSubsidioAsignado() {
		return valorSubsidioAsignado;
	}

	/**
	 * Establece el valor de valorSubsidioAsignado
	 * 
	 * @param valorSubsidioAsignado
	 *            El valor de valorSubsidioAsignado por asignar
	 */
	public void setValorSubsidioAsignado(BigDecimal valorSubsidioAsignado) {
		this.valorSubsidioAsignado = valorSubsidioAsignado;
	}
}
