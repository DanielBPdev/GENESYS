package com.asopagos.aportes.composite.dto;

import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que permite el envío de datos consultados de un cotizante a un consultante dado.
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra Zuluaga </a>
 */
public class DatosCotizanteDTO {
	
	/**
	 * Tipo de afiliado.
	 */
	private TipoAfiliadoEnum tipoAfiliado;
	
	/**
	 * Tipo de identificación del cotizante.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	
	/**
	 * Número de identificación del cotizante.
	 */
	private String numeroIdentificacion;
	
	/**
	 * Tipo de identificación del empleador.
	 */
	private TipoIdentificacionEnum tipoIdenficacionEmpleador;
	
	/**
	 * Número de identificación del empleador.
	 */
	private String numeroIdentificacionEmpleador;

	/**
	 * Estado del afiliado.
	 */
	private EstadoAfiliadoEnum estadoAfiliado;
	
	/**
	 * Fecha de ingreso al sistema.
	 */
	private Long fechaIngreso;
	
	/**
	 * Fecha de retiro, si la tiene.
	 */
	private Long fechaRetiro;

	/**
	 * Método que retorna el valor de tipoAfiliado.
	 * @return valor de tipoAfiliado.
	 */
	public TipoAfiliadoEnum getTipoAfiliado() {
		return tipoAfiliado;
	}

	/**
	 * Método encargado de modificar el valor de tipoAfiliado.
	 * @param valor para modificar tipoAfiliado.
	 */
	public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacion.
	 * @return valor de tipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacion.
	 * @param valor para modificar tipoIdentificacion.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * @return valor de numeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacion.
	 * @param valor para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Método que retorna el valor de tipoIdenficacionEmpleador.
	 * @return valor de tipoIdenficacionEmpleador.
	 */
	public TipoIdentificacionEnum getTipoIdenficacionEmpleador() {
		return tipoIdenficacionEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdenficacionEmpleador.
	 * @param valor para modificar tipoIdenficacionEmpleador.
	 */
	public void setTipoIdenficacionEmpleador(TipoIdentificacionEnum tipoIdenficacionEmpleador) {
		this.tipoIdenficacionEmpleador = tipoIdenficacionEmpleador;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionEmpleador.
	 * @return valor de numeroIdentificacionEmpleador.
	 */
	public String getNumeroIdentificacionEmpleador() {
		return numeroIdentificacionEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionEmpleador.
	 * @param valor para modificar numeroIdentificacionEmpleador.
	 */
	public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
		this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
	}

	/**
	 * Método que retorna el valor de estadoAfiliado.
	 * @return valor de estadoAfiliado.
	 */
	public EstadoAfiliadoEnum getEstadoAfiliado() {
		return estadoAfiliado;
	}

	/**
	 * Método encargado de modificar el valor de estadoAfiliado.
	 * @param valor para modificar estadoAfiliado.
	 */
	public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
		this.estadoAfiliado = estadoAfiliado;
	}

	/**
	 * Método que retorna el valor de fechaIngreso.
	 * @return valor de fechaIngreso.
	 */
	public Long getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * Método encargado de modificar el valor de fechaIngreso.
	 * @param valor para modificar fechaIngreso.
	 */
	public void setFechaIngreso(Long fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * Método que retorna el valor de fechaRetiro.
	 * @return valor de fechaRetiro.
	 */
	public Long getFechaRetiro() {
		return fechaRetiro;
	}

	/**
	 * Método encargado de modificar el valor de fechaRetiro.
	 * @param valor para modificar fechaRetiro.
	 */
	public void setFechaRetiro(Long fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

}
