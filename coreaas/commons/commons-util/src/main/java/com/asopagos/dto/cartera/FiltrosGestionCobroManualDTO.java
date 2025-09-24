package com.asopagos.dto.cartera;

import java.io.Serializable;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> Clase que contiene los filtros para la consulta de
 * aportantes por gestión de cobro manual.<br/>
 * <b>Historia de Usuario: HU-TRA-236 Panel de Control Gestión de Cartera -
 * Supervisor</b>
 * 
 * @author <a href="mailto:atoro@heinsohn.com.co">Angélica Toro Murillo</a>
 */
public class FiltrosGestionCobroManualDTO implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -5496341517339746188L;
	/**
	 * Tipo Identificacion
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	/**
	 * Tipo Identificacion
	 */
	private String numeroIdentificacion;
	/**
	 * Primer nombre.
	 */
	private String primerNombre;
	/**
	 * Primer apellido.
	 */
	private String primerApellido;
	/**
	 * Segundo nombre.
	 */
	private String segundoNombre;
	/**
	 * Segundo apellido.
	 */
	private String segundoApellido;

	/**
	 * Razón social.
	 */
	private String razonSocial;
	/**
	 * Estado de la solcitud manual.
	 */
	private EstadoFiscalizacionEnum estado;
	/**
	 * Tipo de solicitante.
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

	/**
	 * Indica si el usuario autenticado tiene rol Supervisor de Cartera
	 */
	private Boolean esSupervisor;

	/**
	 * Método que retorna el valor de tipoIdentificacion.
	 * 
	 * @return valor de tipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacion.
	 * 
	 * @param valor
	 *            para modificar tipoIdentificacion.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * 
	 * @return valor de numeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacion.
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Método que retorna el valor de primerNombre.
	 * 
	 * @return valor de primerNombre.
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * Método que retorna el valor de primerApellido.
	 * 
	 * @return valor de primerApellido.
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Método que retorna el valor de segundoNombre.
	 * 
	 * @return valor de segundoNombre.
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * Método que retorna el valor de segundoApellido.
	 * 
	 * @return valor de segundoApellido.
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Método que retorna el valor de estado.
	 * 
	 * @return valor de estado.
	 */
	public EstadoFiscalizacionEnum getEstado() {
		return estado;
	}

	/**
	 * Método que retorna el valor de tipoSolicitante.
	 * 
	 * @return valor de tipoSolicitante.
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Método encargado de modificar el valor de primerNombre.
	 * 
	 * @param valor
	 *            para modificar primerNombre.
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * Método encargado de modificar el valor de primerApellido.
	 * 
	 * @param valor
	 *            para modificar primerApellido.
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombre.
	 * 
	 * @param valor
	 *            para modificar segundoNombre.
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellido.
	 * 
	 * @param valor
	 *            para modificar segundoApellido.
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * Método encargado de modificar el valor de estado.
	 * 
	 * @param valor
	 *            para modificar estado.
	 */
	public void setEstado(EstadoFiscalizacionEnum estado) {
		this.estado = estado;
	}

	/**
	 * Método encargado de modificar el valor de tipoSolicitante.
	 * 
	 * @param valor
	 *            para modificar tipoSolicitante.
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * Método que retorna el valor de razonSocial.
	 * 
	 * @return valor de razonSocial.
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Método encargado de modificar el valor de razonSocial.
	 * 
	 * @param valor
	 *            para modificar razonSocial.
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * Obtiene el valor de esSupervisor
	 * 
	 * @return El valor de esSupervisor
	 */
	public Boolean getEsSupervisor() {
		return esSupervisor;
	}

	/**
	 * Establece el valor de esSupervisor
	 * 
	 * @param esSupervisor
	 *            El valor de esSupervisor por asignar
	 */
	public void setEsSupervisor(Boolean esSupervisor) {
		this.esSupervisor = esSupervisor;
	}
}
