package com.asopagos.aportes.dto;

import java.util.List;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos de consulta de aportes que
 * se encuentran en estado <i>RELACIONADO</i> <br/>
 * <b>Historia de Usuario: </b> HU-261
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class ConsultaAporteRelacionadoDTO {

	/**
	 * Grupo de campos para seleccionar el tipo de entidad: empleador,
	 * pensionado o independiente
	 */
	private List<TipoSolicitanteMovimientoAporteEnum> tipoEntidad;

	/**
	 * Fecha inicial del rango de búsqueda
	 */
	private Long fechaInicio;

	/**
	 * Fecha final del rango
	 */
	private Long fechaFin;

	/**
	 * Antigüedad del recaudo
	 */
	private Long antiguedadRecaudo;

	/**
	 * Obtiene el valor de tipoEntidad
	 * 
	 * @return El valor de tipoEntidad
	 */
	public List<TipoSolicitanteMovimientoAporteEnum> getTipoEntidad() {
		return tipoEntidad;
	}

	/**
	 * Establece el valor de tipoEntidad
	 * 
	 * @param tipoEntidad
	 *            El valor de tipoEntidad por asignar
	 */
	public void setTipoEntidad(List<TipoSolicitanteMovimientoAporteEnum> tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	/**
	 * Obtiene el valor de fechaInicio
	 * 
	 * @return El valor de fechaInicio
	 */
	public Long getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Establece el valor de fechaInicio
	 * 
	 * @param fechaInicio
	 *            El valor de fechaInicio por asignar
	 */
	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Obtiene el valor de fechaFin
	 * 
	 * @return El valor de fechaFin
	 */
	public Long getFechaFin() {
		return fechaFin;
	}

	/**
	 * Establece el valor de fechaFin
	 * 
	 * @param fechaFin
	 *            El valor de fechaFin por asignar
	 */
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Obtiene el valor de antiguedadRecaudo
	 * 
	 * @return El valor de antiguedadRecaudo
	 */
	public Long getAntiguedadRecaudo() {
		return antiguedadRecaudo;
	}

	/**
	 * Establece el valor de antiguedadRecaudo
	 * 
	 * @param antiguedadRecaudo
	 *            El valor de antiguedadRecaudo por asignar
	 */
	public void setAntiguedadRecaudo(Long antiguedadRecaudo) {
		this.antiguedadRecaudo = antiguedadRecaudo;
	}
}
