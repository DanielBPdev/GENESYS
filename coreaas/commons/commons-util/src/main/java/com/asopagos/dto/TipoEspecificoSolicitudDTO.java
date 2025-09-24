package com.asopagos.dto;

import java.io.Serializable;

/**
 * Clase que contiene la informacion de la consulta nativa para construir una
 * consulta dinamica de acuerdo al tipo especifico de solicitud
 * 
 * @author jbuitrago
 * 
 */
public class TipoEspecificoSolicitudDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Seccion de la consulta que tiene informacion sobre las tablas y enlaces
	 */
	private String consultaDinamicaTablas;

	/*
	 * Seccion que contiene informacion sobre los campos de las restricciones
	 * que debe tener la consulta dinamica
	 */
	private String consultaDinamicaRestricciones;

	/*
	 * Nombre del campo de la tabla de la solicitud especifica donde se indica
	 * el estado de la misma
	 */
	private String campoEstadoSolicitud;
	/*
	 * Enumeracion generica del estado de la solicitud
	 */
	private Enum estadoSolicitud;

	/*
	 * Getter - Setter
	 */
	public String getCampoEstadoSolicitud() {
		return campoEstadoSolicitud;
	}

	public void setCampoEstadoSolicitud(String campoEstadoSolicitud) {
		this.campoEstadoSolicitud = campoEstadoSolicitud;
	}

	public Enum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(Enum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getConsultaDinamicaTablas() {
		return consultaDinamicaTablas;
	}

	public void setConsultaDinamicaTablas(String consultaDinamicaTablas) {
		this.consultaDinamicaTablas = consultaDinamicaTablas;
	}

	public String getConsultaDinamicaRestricciones() {
		return consultaDinamicaRestricciones;
	}

	public void setConsultaDinamicaRestricciones(
			String consultaDinamicaRestricciones) {
		this.consultaDinamicaRestricciones = consultaDinamicaRestricciones;
	}

	/*
	 * Constructores
	 */
	public TipoEspecificoSolicitudDTO() {

	}

	public TipoEspecificoSolicitudDTO(String campoEstadoSolicitud,
			Enum estadoSolicitud, String consultaDinamicaTablas,
			String consultaDinamicaRestricciones) {
		this.campoEstadoSolicitud = campoEstadoSolicitud;
		this.estadoSolicitud = estadoSolicitud;
		this.consultaDinamicaTablas = consultaDinamicaTablas;
		this.consultaDinamicaRestricciones = consultaDinamicaRestricciones;
	}

	public TipoEspecificoSolicitudDTO(String campoEstadoSolicitud,
			Enum estadoSolicitud, String consultaDinamicaTablas) {
		this.campoEstadoSolicitud = campoEstadoSolicitud;
		this.estadoSolicitud = estadoSolicitud;
		this.consultaDinamicaTablas = consultaDinamicaTablas;
	}

}
