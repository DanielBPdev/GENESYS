package com.asopagos.dto;

import java.io.Serializable;

import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;

/**
 * Se encarga de contener los filtros que se utilizan en la consulta de
 * solicitudes
 * 
 * @author jbuitrago
 * 
 */
public class FiltroSolicitudDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Filtros transaciconales de la solicitud
	 */

	/*
	 * numero de radicacion de la solicitud
	 */
	private String numeroSolicitud;
	private ProcesoEnum proceso;
	private CanalRecepcionEnum canalRecepcion;
	private String estadoSolicitud;
	private String usuarioRadicacion;
	private Long fechaInicio;
	private Long fechaFin;

	/*
	 * Filtros de servicios de BPM
	 */
	private String sucursalRadicacion;
	private String usuarioAsignado;

	/*
	 * Atributos para los filtros de empleador y persona
	 */
	private Long idEmpleador;
	private Long idPersona;

	/*
	 * Atributos para el manejo de la paginación
	 */
	private Integer limit;
	private Integer offSet;
	private Integer totalRecord;
	/*
	 * Getter - Setter
	 */

	public ProcesoEnum getProceso() {
		return proceso;
	}

	public String getNumeroSolicitud() {
		return numeroSolicitud;
	}

	public void setNumeroSolicitud(String numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}

	public void setProceso(ProcesoEnum proceso) {
		this.proceso = proceso;
	}

	public CanalRecepcionEnum getCanalRecepcion() {
		return canalRecepcion;
	}

	public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
		this.canalRecepcion = canalRecepcion;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getSucursalRadicacion() {
		return sucursalRadicacion;
	}

	public void setSucursalRadicacion(String sucursalRadicacion) {
		this.sucursalRadicacion = sucursalRadicacion;
	}

	public String getUsuarioRadicacion() {
		return usuarioRadicacion;
	}

	public void setUsuarioRadicacion(String usuarioRadicacion) {
		this.usuarioRadicacion = usuarioRadicacion;
	}

	public Long getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Long getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(String usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public Long getIdEmpleador() {
		return idEmpleador;
	}

	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffSet() {
		return offSet;
	}

	public void setOffSet(Integer offSet) {
		this.offSet = offSet;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/*
	 * Constructores
	 */
	public FiltroSolicitudDTO() {
	}

}