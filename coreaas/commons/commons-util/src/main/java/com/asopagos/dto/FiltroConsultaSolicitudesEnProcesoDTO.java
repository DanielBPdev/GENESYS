package com.asopagos.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

public class FiltroConsultaSolicitudesEnProcesoDTO {

	private TipoIdentificacionEnum tipoIdentificacionEmpleador;

	private String numeroIdentificacionEmpleador;

	private TipoTipoSolicitanteEnum tipoSolicitante;

	private TipoIdentificacionEnum tipoIdentificacionSolicitante;

	private String numeroIdentificacionSolicitante;

	private String numeroSolicitud;

	private Long fechaInicio;

	private Long fechaFin;


	public TipoIdentificacionEnum getTipoIdentificacionEmpleador() {
		return this.tipoIdentificacionEmpleador;
	}

	public void setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
		this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
	}

	public String getNumeroIdentificacionEmpleador() {
		return this.numeroIdentificacionEmpleador;
	}

	public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
		this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
	}

	public TipoTipoSolicitanteEnum getTipoSolicitante() {
		return this.tipoSolicitante;
	}

	public void setTipoSolicitante(TipoTipoSolicitanteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	public TipoIdentificacionEnum getTipoIdentificacionSolicitante() {
		return this.tipoIdentificacionSolicitante;
	}

	public void setTipoIdentificacionSolicitante(TipoIdentificacionEnum tipoIdentificacionSolicitante) {
		this.tipoIdentificacionSolicitante = tipoIdentificacionSolicitante;
	}

	public String getNumeroIdentificacionSolicitante() {
		return this.numeroIdentificacionSolicitante;
	}

	public void setNumeroIdentificacionSolicitante(String numeroIdentificacionSolicitante) {
		this.numeroIdentificacionSolicitante = numeroIdentificacionSolicitante;
	}

	public String getNumeroSolicitud() {
		return this.numeroSolicitud;
	}

	public void setNumeroSolicitud(String numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}

	public Long getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Long getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String toString() {
		return "{" +
			" tipoIdentificacionEmpleador='" + getTipoIdentificacionEmpleador() + "'" +
			", numeroIdentificacionEmpleador='" + getNumeroIdentificacionEmpleador() + "'" +
			", tipoSolicitante='" + getTipoSolicitante() + "'" +
			", tipoIdentificacionSolicitante='" + getTipoIdentificacionSolicitante() + "'" +
			", numeroIdentificacionSolicitante='" + getNumeroIdentificacionSolicitante() + "'" +
			", numeroSolicitud='" + getNumeroSolicitud() + "'" +
			", fechaInicio='" + getFechaInicio() + "'" +
			", fechaFin='" + getFechaFin() + "'" +
			"}";
	}
}
