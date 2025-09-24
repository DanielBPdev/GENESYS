/**
 * 
 */
package com.asopagos.aportes.dto;

import java.util.List;

import com.asopagos.enumeraciones.aportes.TipoReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

/**
 * DTO para consultar los movimientos de ingresos.
 * 
 * @author <a href="mailto:criparra@heinsohn.com.co>Cristian David Parra
 *         Zuluaga</a>
 */
public class ConsultaMovimientoIngresosDTO {
	/**
	 * Grupo de campos para seleccionar el tipo de entidad: empleador,
	 * pensionado o independiente.
	 */
	private List<TipoSolicitanteMovimientoAporteEnum> tipoEntidad;

	/**
	 * Fecha inicial del rango de busqueda.
	 */
	private Long fechaInicio;

	/**
	 * Fecha final del rango.
	 */
	private Long fechaFin;

	/**
	 * Grupo de campos para seleccionar el tipo de reconocimiento: Registro de
	 * ingreso u otros ingresos.
	 */
	private List<TipoReconocimientoAporteEnum> tipoReconocimiento;

	/**
	 * Método que retorna el valor de tipoEntidad.
	 * 
	 * @return valor de tipoEntidad.
	 */
	public List<TipoSolicitanteMovimientoAporteEnum> getTipoEntidad() {
		return tipoEntidad;
	}

	/**
	 * Método encargado de modificar el valor de tipoEntidad.
	 * 
	 * @param valor
	 *            para modificar tipoEntidad.
	 */
	public void setTipoEntidad(List<TipoSolicitanteMovimientoAporteEnum> tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	/**
	 * Método que retorna el valor de fechaInicio.
	 * 
	 * @return valor de fechaInicio.
	 */
	public Long getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Método encargado de modificar el valor de fechaInicio.
	 * 
	 * @param valor
	 *            para modificar fechaInicio.
	 */
	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Método que retorna el valor de fechaFin.
	 * 
	 * @return valor de fechaFin.
	 */
	public Long getFechaFin() {
		return fechaFin;
	}

	/**
	 * Método encargado de modificar el valor de fechaFin.
	 * 
	 * @param valor
	 *            para modificar fechaFin.
	 */
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Obtiene el valor de tipoReconocimiento
	 * 
	 * @return El valor de tipoReconocimiento
	 */
	public List<TipoReconocimientoAporteEnum> getTipoReconocimiento() {
		return tipoReconocimiento;
	}

	/**
	 * Establece el valor de tipoReconocimiento
	 * 
	 * @param tipoReconocimiento
	 *            El valor de tipoReconocimiento por asignar
	 */
	public void setTipoReconocimiento(List<TipoReconocimientoAporteEnum> tipoReconocimiento) {
		this.tipoReconocimiento = tipoReconocimiento;
	}
}
