package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;

public class DiligenciarFormularioAfiliacionDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private SolicitudAfiliacionEmpleador solicitudAfiliacion;
	
	private Long idSolicitud;
	
	private Long idTarea;
	
	private Boolean datosCorrectos;
	
	private String plazoRadFormulario;
	
	private Long idInstanciaProceso;

	/**
	 * @return the solicitudAfiliacion
	 */
	public SolicitudAfiliacionEmpleador getSolicitudAfiliacion() {
		return solicitudAfiliacion;
	}

	/**
	 * @param solicitudAfiliacion
	 *            the solicitudAfiliacion to set
	 */
	public void setSolicitudAfiliacion(SolicitudAfiliacionEmpleador solicitudAfiliacion) {
		this.solicitudAfiliacion = solicitudAfiliacion;
	}

	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud
	 *            the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * @return the datosCorrectos
	 */
	public Boolean getDatosCorrectos() {
		return datosCorrectos;
	}

	/**
	 * @param datosCorrectos the datosCorrectos to set
	 */
	public void setDatosCorrectos(Boolean datosCorrectos) {
		this.datosCorrectos = datosCorrectos;
	}

	/**
	 * @return the plazoRadFormulario
	 */
	public String getPlazoRadFormulario() {
		return plazoRadFormulario;
	}

	/**
	 * @param plazoRadFormulario the plazoRadFormulario to set
	 */
	public void setPlazoRadFormulario(String plazoRadFormulario) {
		this.plazoRadFormulario = plazoRadFormulario;
	}

	/**
	 * @return the idInstanciaProceso
	 */
	public Long getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(Long idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

}
