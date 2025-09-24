package com.asopagos.dto.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.aportes.SolicitudCorreccionAporte;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos de una solicitud de
 * corrección de aportes <br/>
 * <b>Historia de Usuario: </b> HU-105
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class SolicitudCorreccionAporteModeloDTO extends SolicitudModeloDTO implements Serializable{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 4614534101537222553L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long idSolicitudCorreccionAporte;

	/**
	 * Estado de la solicitud de corrección.
	 */
	private EstadoSolicitudAporteEnum estadoSolicitud;

	/**
	 * Tipo de persona que hace la solicitud
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

	/**
	 * Observaciones incluidas por el supervisor de aportes
	 */
	private String observacionSupervisor;

	/**
	 * Resultado del proceso por parte del supervisor.
	 */
	private ResultadoProcesoEnum resultadoSupervisor;

	/**
	 * Identificador de la persona que hace la solicitud -> Referencia a la
	 * tabla <code>Persona</code>
	 */
	private Long idPersona;

	/**
	 * Identificador del aporte general sobre el que se radica la solicitud.
	 * Referencia a la tabla <code>AporteGeneral</code>
	 */
	private Long idAporteGeneralNuevo;

	/**
	 * Método que convierte una entidad <code>SolicitudCorreccionAporte</code>
	 * en DTO
	 * 
	 * @param solicitudCorreccionAporte
	 *            La entidad a convertir
	 */
    public void convertToDTO(SolicitudCorreccionAporte solicitudCorreccionAporte) {
        if (solicitudCorreccionAporte != null) {
            this.idSolicitudCorreccionAporte = solicitudCorreccionAporte.getIdSolicitudCorreccionAporte();
            this.estadoSolicitud = solicitudCorreccionAporte.getEstadoSolicitud();
            this.tipoSolicitante = solicitudCorreccionAporte.getTipoSolicitante();
            this.observacionSupervisor = solicitudCorreccionAporte.getObservacionSupervisor();
            this.resultadoSupervisor = solicitudCorreccionAporte.getResultadoSupervisor();
            this.idPersona = solicitudCorreccionAporte.getIdPersona();
            this.idAporteGeneralNuevo = solicitudCorreccionAporte.getIdAporteGeneralNuevo();

            if (solicitudCorreccionAporte.getSolicitudGlobal() != null) {
                super.convertToDTO(solicitudCorreccionAporte.getSolicitudGlobal());
            }
        }
    }

	/**
	 * Método que convierte el DTO en una entidad
	 * <code>SolicitudCorreccionAporte</code>
	 * 
	 * @return Una entidad <code>SolicitudCorreccionAporte</code> con los datos
	 *         equivalentes
	 */
	public SolicitudCorreccionAporte convertToEntity() {
		SolicitudCorreccionAporte solicitudCorreccionAporte = new SolicitudCorreccionAporte();
		solicitudCorreccionAporte.setIdSolicitudCorreccionAporte(this.idSolicitudCorreccionAporte);
		solicitudCorreccionAporte.setEstadoSolicitud(this.estadoSolicitud);
		solicitudCorreccionAporte.setTipoSolicitante(this.tipoSolicitante);
		solicitudCorreccionAporte.setObservacionSupervisor(this.observacionSupervisor);
		solicitudCorreccionAporte.setResultadoSupervisor(this.resultadoSupervisor);
		solicitudCorreccionAporte.setIdPersona(this.idPersona);
		solicitudCorreccionAporte.setIdAporteGeneralNuevo(this.idAporteGeneralNuevo);

		Solicitud solicitudGlobal = super.convertToSolicitudEntity();
		solicitudCorreccionAporte.setSolicitudGlobal(solicitudGlobal);

		return solicitudCorreccionAporte;
	}

	/**
	 * Obtiene el valor de idSolicitudCorreccionAporte
	 * 
	 * @return El valor de idSolicitudCorreccionAporte
	 */
	public Long getIdSolicitudCorreccionAporte() {
		return idSolicitudCorreccionAporte;
	}

	/**
	 * Establece el valor de idSolicitudCorreccionAporte
	 * 
	 * @param idSolicitudCorreccionAporte
	 *            El valor de idSolicitudCorreccionAporte por asignar
	 */
	public void setIdSolicitudCorreccionAporte(Long idSolicitudCorreccionAporte) {
		this.idSolicitudCorreccionAporte = idSolicitudCorreccionAporte;
	}

	/**
	 * Obtiene el valor de estadoSolicitud
	 * 
	 * @return El valor de estadoSolicitud
	 */
	public EstadoSolicitudAporteEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * Establece el valor de estadoSolicitud
	 * 
	 * @param estadoSolicitud
	 *            El valor de estadoSolicitud por asignar
	 */
	public void setEstadoSolicitud(EstadoSolicitudAporteEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	/**
	 * Obtiene el valor de tipoSolicitante
	 * 
	 * @return El valor de tipoSolicitante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Establece el valor de tipoSolicitante
	 * 
	 * @param tipoSolicitante
	 *            El valor de tipoSolicitante por asignar
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * Obtiene el valor de observacionSupervisor
	 * 
	 * @return El valor de observacionSupervisor
	 */
	public String getObservacionSupervisor() {
		return observacionSupervisor;
	}

	/**
	 * Establece el valor de observacionSupervisor
	 * 
	 * @param observacionSupervisor
	 *            El valor de observacionSupervisor por asignar
	 */
	public void setObservacionSupervisor(String observacionSupervisor) {
		this.observacionSupervisor = observacionSupervisor;
	}

	/**
	 * Obtiene el valor de resultadoSupervisor
	 * 
	 * @return El valor de resultadoSupervisor
	 */
	public ResultadoProcesoEnum getResultadoSupervisor() {
		return resultadoSupervisor;
	}

	/**
	 * Establece el valor de resultadoSupervisor
	 * 
	 * @param resultadoSupervisor
	 *            El valor de resultadoSupervisor por asignar
	 */
	public void setResultadoSupervisor(ResultadoProcesoEnum resultadoSupervisor) {
		this.resultadoSupervisor = resultadoSupervisor;
	}

	/**
	 * Obtiene el valor de idPersona
	 * 
	 * @return El valor de idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Establece el valor de idPersona
	 * 
	 * @param idPersona
	 *            El valor de idPersona por asignar
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Obtiene el valor de idAporteGeneralNuevo
	 * 
	 * @return El valor de idAporteGeneralNuevo
	 */
	public Long getIdAporteGeneralNuevo() {
		return idAporteGeneralNuevo;
	}

	/**
	 * Establece el valor de idAporteGeneralNuevo
	 * 
	 * @param idAporteGeneralNuevo
	 *            El valor de idAporteGeneralNuevo por asignar
	 */
	public void setIdAporteGeneralNuevo(Long idAporteGeneralNuevo) {
		this.idAporteGeneralNuevo = idAporteGeneralNuevo;
	}
}