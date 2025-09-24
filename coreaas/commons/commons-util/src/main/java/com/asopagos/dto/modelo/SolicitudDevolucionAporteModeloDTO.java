/**
 * 
 */
package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.aportes.SolicitudDevolucionAporte;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos de una solicitud de
 * devolución de aportes <br/>
 * <b>Historia de Usuario: </b> HU-485, HU-486
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class SolicitudDevolucionAporteModeloDTO extends SolicitudModeloDTO {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1329224899754814244L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long idSolicitudDevolucionAporte;

	/**
	 * Estado de la solicitud de devolución
	 */
	private EstadoSolicitudAporteEnum estadoSolicitud;

	/**
	 * Tipo de persona que hace la solicitud
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

	/**
	 * Observaciones ingresadas por el analista de aportes
	 */
	private String observacionAnalista;

	/**
	 * Observaciones ingresadas por el supervisor de aportes
	 */
	private String observacionSupervisor;

	/**
	 * Resultado del proceso de análisis de la solicitud de devolución
	 */
	private ResultadoProcesoEnum resultadoAnalista;

	/**
	 * Resultado de la solicitud, evaluada por el supervisor de aportes
	 */
	private ResultadoProcesoEnum resultadoSupervisor;

	/**
	 * Identificador de la persona -> Referencia a la tabla <code>Persona</code>
	 */
	private Long idPersona;

	/**
	 * Identificador de la devolución -> Referencia a la tabla
	 * <code>DevolucionAporte</code>
	 */
	private Long idDevolucionAporte;
	
	/**
	 * Constructor por defecto
	 * */
	public SolicitudDevolucionAporteModeloDTO(){
	    super();
	}
	
	/**
	 * Constructor con base en entity
	 * */
	public SolicitudDevolucionAporteModeloDTO(SolicitudDevolucionAporte solicitudDevolucionAporte){
	    super();
	    convertToDTO(solicitudDevolucionAporte);
	}

	/**
	 * Método que convierte una entidad <code>SolicitudDevolucionAporte</code>
	 * en DTO
	 * 
	 * @param solicitudDevolucionAporte
	 *            La entidad a convertir
	 */
	public void convertToDTO(SolicitudDevolucionAporte solicitudDevolucionAporte) {
		this.idSolicitudDevolucionAporte = solicitudDevolucionAporte.getIdSolicitudDevolucionAporte();
		this.estadoSolicitud = solicitudDevolucionAporte.getEstadoSolicitud();
		this.tipoSolicitante = solicitudDevolucionAporte.getTipoSolicitante();
		this.observacionAnalista = solicitudDevolucionAporte.getObservacionAnalista();
		this.observacionSupervisor = solicitudDevolucionAporte.getObservacionSupervisor();
		this.resultadoAnalista = solicitudDevolucionAporte.getResultadoAnalista();
		this.resultadoSupervisor = solicitudDevolucionAporte.getResultadoSupervisor();
		this.idDevolucionAporte = solicitudDevolucionAporte.getDevolucionAporte();
		this.idPersona = solicitudDevolucionAporte.getPersona();

		if (solicitudDevolucionAporte.getSolicitudGlobal() != null) {
			super.convertToDTO(solicitudDevolucionAporte.getSolicitudGlobal());
		}
	}

	/**
	 * Método que convierte el DTO en una entidad
	 * <code>SolicitudDevolucionAporte</code>
	 * 
	 * @return Una entidad <code>SolicitudDevolucionAporte</code> con los datos
	 *         equivalentes
	 */
	public SolicitudDevolucionAporte convertToEntity() {
		SolicitudDevolucionAporte solicitudDevolucionAporte = new SolicitudDevolucionAporte();
		solicitudDevolucionAporte.setIdSolicitudDevolucionAporte(this.idSolicitudDevolucionAporte);
		solicitudDevolucionAporte.setEstadoSolicitud(this.estadoSolicitud);
		solicitudDevolucionAporte.setTipoSolicitante(this.tipoSolicitante);
		solicitudDevolucionAporte.setObservacionAnalista(this.observacionAnalista);
		solicitudDevolucionAporte.setObservacionSupervisor(this.observacionSupervisor);
		solicitudDevolucionAporte.setResultadoAnalista(this.resultadoAnalista);
		solicitudDevolucionAporte.setResultadoSupervisor(this.resultadoSupervisor);
		solicitudDevolucionAporte.setDevolucionAporte(this.idDevolucionAporte);
		solicitudDevolucionAporte.setPersona(this.idPersona);

		Solicitud solicitudGlobal = super.convertToSolicitudEntity();
		solicitudDevolucionAporte.setSolicitudGlobal(solicitudGlobal);

		return solicitudDevolucionAporte;
	}

	/**
	 * Obtiene el valor de idSolicitudDevolucionAporte
	 * 
	 * @return El valor de idSolicitudDevolucionAporte
	 */
	public Long getIdSolicitudDevolucionAporte() {
		return idSolicitudDevolucionAporte;
	}

	/**
	 * Establece el valor de idSolicitudDevolucionAporte
	 * 
	 * @param idSolicitudDevolucionAporte
	 *            El valor de idSolicitudDevolucionAporte por asignar
	 */
	public void setIdSolicitudDevolucionAporte(Long idSolicitudDevolucionAporte) {
		this.idSolicitudDevolucionAporte = idSolicitudDevolucionAporte;
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
	 * Obtiene el valor de observacionAnalista
	 * 
	 * @return El valor de observacionAnalista
	 */
	public String getObservacionAnalista() {
		return observacionAnalista;
	}

	/**
	 * Establece el valor de observacionAnalista
	 * 
	 * @param observacionAnalista
	 *            El valor de observacionAnalista por asignar
	 */
	public void setObservacionAnalista(String observacionAnalista) {
		this.observacionAnalista = observacionAnalista;
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
	 * Obtiene el valor de resultadoAnalista
	 * 
	 * @return El valor de resultadoAnalista
	 */
	public ResultadoProcesoEnum getResultadoAnalista() {
		return resultadoAnalista;
	}

	/**
	 * Establece el valor de resultadoAnalista
	 * 
	 * @param resultadoAnalista
	 *            El valor de resultadoAnalista por asignar
	 */
	public void setResultadoAnalista(ResultadoProcesoEnum resultadoAnalista) {
		this.resultadoAnalista = resultadoAnalista;
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
	 * Obtiene el valor de idDevolucionAporte
	 * 
	 * @return El valor de idDevolucionAporte
	 */
	public Long getIdDevolucionAporte() {
		return idDevolucionAporte;
	}

	/**
	 * Establece el valor de idDevolucionAporte
	 * 
	 * @param idDevolucionAporte
	 *            El valor de idDevolucionAporte por asignar
	 */
	public void setIdDevolucionAporte(Long idDevolucionAporte) {
		this.idDevolucionAporte = idDevolucionAporte;
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
}