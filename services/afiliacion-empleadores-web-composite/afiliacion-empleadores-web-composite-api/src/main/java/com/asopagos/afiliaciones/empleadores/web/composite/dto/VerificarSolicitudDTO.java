package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;

public class VerificarSolicitudDTO implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String numeroRadicado;
	
	private SolicitudAfiliacionEmpleador solicitudAfiliacion;
	
	private Integer resultadoVerifBack;
	
	private String analista;
	 
	private String tiempoLimiteCorreccionInfo;

	private Boolean requiereAnalisisEsp;
	
	private Long idTarea;

	private EscalamientoSolicitudDTO solicitudEscalamiento;
	/**
	 * @return the numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * @param numeroRadicado the numeroRadicado to set
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/**
	 * @return the solicitudAfiliacion
	 */
	public SolicitudAfiliacionEmpleador getSolicitudAfiliacion() {
		return solicitudAfiliacion;
	}

	/**
	 * @param solicitudAfiliacion the solicitudAfiliacion to set
	 */
	public void setSolicitudAfiliacion(SolicitudAfiliacionEmpleador solicitudAfiliacion) {
		this.solicitudAfiliacion = solicitudAfiliacion;
	}

	/**
	 * @return the resultadoVerifBack
	 */
	public Integer getResultadoVerifBack() {
		return resultadoVerifBack;
	}

	/**
	 * @param resultadoVerifBack the resultadoVerifBack to set
	 */
	public void setResultadoVerifBack(Integer resultadoVerifBack) {
		this.resultadoVerifBack = resultadoVerifBack;
	}

	/**
	 * @return the analista
	 */
	public String getAnalista() {
		return analista;
	}

	/**
	 * @param analista the analista to set
	 */
	public void setAnalista(String analista) {
		this.analista = analista;
	}

	/**
	 * @return the tiempoLimiteCorreccionInfo
	 */
	public String getTiempoLimiteCorreccionInfo() {
		return tiempoLimiteCorreccionInfo;
	}

	/**
	 * @param tiempoLimiteCorreccionInfo the tiempoLimiteCorreccionInfo to set
	 */
	public void setTiempoLimiteCorreccionInfo(String tiempoLimiteCorreccionInfo) {
		this.tiempoLimiteCorreccionInfo = tiempoLimiteCorreccionInfo;
	}

	/**
	 * @return the requiereAnalisisEsp
	 */
	public Boolean getRequiereAnalisisEsp() {
		return requiereAnalisisEsp;
	}

	/**
	 * @param requiereAnalisisEsp the requiereAnalisisEsp to set
	 */
	public void setRequiereAnalisisEsp(Boolean requiereAnalisisEsp) {
		this.requiereAnalisisEsp = requiereAnalisisEsp;
	}

	/**
	 * @return the idTarea
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * @param idTarea the idTarea to set
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	public EscalamientoSolicitudDTO getSolicitudEscalamiento() {
		return solicitudEscalamiento;
	}

	/**
	 * @param solicitudEscalamiento the solicitudEscalamiento to set
	 */
	public void setSolicitudEscalamiento(EscalamientoSolicitudDTO solicitudEscalamiento) {
		this.solicitudEscalamiento = solicitudEscalamiento;
	}
}
