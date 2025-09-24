package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.TipoSolicitud360Enum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> Clase que define la estructura de datos de consulta de
 * solicitudes para la vista 360 <br/>
 * <b>Historia de Usuario: </b> Req-Consultas-Vista_360
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class Solicitud360DTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 8165113429636897671L;

	/**
	 * Número de identificación del aportante
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del aportante
	 */
	private String numeroIdentificacion;

	/**
	 * Tipos de aportante
	 */
	private List<TipoSolicitanteMovimientoAporteEnum> tiposAportante;

	/**
	 * Número de radicado de la solicitud
	 */
	private String numeroRadicado;

	/**
	 * Identificador único de la solicitud
	 */
	private Long idSolicitud;
	
	/**
	 * Identificador único de la solicitud de afiliación
	 */
	private Long idSolicitudAfiliacion;

	/**
	 * Tipos de solicitud
	 */
	private List<TipoSolicitud360Enum> tiposSolicitud;

	/**
	 * Estados de la solicitud (depende del tipo de solicitud)
	 */
	private List<String> estadosSolicitud;

	/**
	 * Fecha >= a la fecha de radicación de la solicitud
	 */
	private Long fechaInicio;

	/**
	 * Fecha <= a la fecha de radicación de la solicitud
	 */
	private Long fechaFin;
	
	/**
	 * Fecha de radicado (criterio de consulta)
	 * */
	private Long fechaRadicado;

	/**
	 * Fecha de radicación (hace parte del response)
	 */
	private Long fechaRadicadoRespuesta;

	/**
	 * Tipo de solicitud (hace parte del response)
	 */
	private TipoSolicitud360Enum tipoSolicitudRespuesta;

	/**
	 * Estado de la solicitud (hace parte de la respuesta)
	 */
	private String estadoSolicitudRespuesta;

	/**
	 * Canal de recepción de la solicitud
	 */
	private CanalRecepcionEnum canalSolicitud; 
	
	/**
	 * Obtiene el valor de tipoIdentificacion
	 * 
	 * @return El valor de tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Establece el valor de tipoIdentificacion
	 * 
	 * @param tipoIdentificacion
	 *            El valor de tipoIdentificacion por asignar
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Obtiene el valor de numeroIdentificacion
	 * 
	 * @return El valor de numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Establece el valor de numeroIdentificacion
	 * 
	 * @param numeroIdentificacion
	 *            El valor de numeroIdentificacion por asignar
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Obtiene el valor de numeroRadicado
	 * 
	 * @return El valor de numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * Establece el valor de numeroRadicado
	 * 
	 * @param numeroRadicado
	 *            El valor de numeroRadicado por asignar
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/**
	 * Obtiene el valor de tiposSolicitud
	 * 
	 * @return El valor de tiposSolicitud
	 */
	public List<TipoSolicitud360Enum> getTiposSolicitud() {
		return tiposSolicitud;
	}

	/**
	 * Establece el valor de tiposSolicitud
	 * 
	 * @param tiposSolicitud
	 *            El valor de tiposSolicitud por asignar
	 */
	public void setTiposSolicitud(List<TipoSolicitud360Enum> tiposSolicitud) {
		this.tiposSolicitud = tiposSolicitud;
	}

	/**
	 * Obtiene el valor de fechaInicio
	 * 
	 * @return El valor de fechaInicio
	 */
	public Long getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Establece el valor de fechaInicio
	 * 
	 * @param fechaInicio
	 *            El valor de fechaInicio por asignar
	 */
	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Obtiene el valor de fechaFin
	 * 
	 * @return El valor de fechaFin
	 */
	public Long getFechaFin() {
		return fechaFin;
	}

	/**
	 * Establece el valor de fechaFin
	 * 
	 * @param fechaFin
	 *            El valor de fechaFin por asignar
	 */
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Obtiene el valor de estadosSolicitud
	 * 
	 * @return El valor de estadosSolicitud
	 */
	public List<String> getEstadosSolicitud() {
		return estadosSolicitud;
	}

	/**
	 * Establece el valor de estadosSolicitud
	 * 
	 * @param estadosSolicitud
	 *            El valor de estadosSolicitud por asignar
	 */
	public void setEstadosSolicitud(List<String> estadosSolicitud) {
		this.estadosSolicitud = estadosSolicitud;
	}

	/**
	 * Obtiene el valor de fechaRadicadoRespuesta
	 * 
	 * @return El valor de fechaRadicadoRespuesta
	 */
	public Long getFechaRadicadoRespuesta() {
		return fechaRadicadoRespuesta;
	}

	/**
	 * Establece el valor de fechaRadicadoRespuesta
	 * 
	 * @param fechaRadicadoRespuesta
	 *            El valor de fechaRadicadoRespuesta por asignar
	 */
	public void setFechaRadicadoRespuesta(Long fechaRadicadoRespuesta) {
		this.fechaRadicadoRespuesta = fechaRadicadoRespuesta;
	}

	/**
	 * Obtiene el valor de tipoSolicitudRespuesta
	 * 
	 * @return El valor de tipoSolicitudRespuesta
	 */
	public TipoSolicitud360Enum getTipoSolicitudRespuesta() {
		return tipoSolicitudRespuesta;
	}

	/**
	 * Establece el valor de tipoSolicitudRespuesta
	 * 
	 * @param tipoSolicitudRespuesta
	 *            El valor de tipoSolicitudRespuesta por asignar
	 */
	public void setTipoSolicitudRespuesta(TipoSolicitud360Enum tipoSolicitudRespuesta) {
		this.tipoSolicitudRespuesta = tipoSolicitudRespuesta;
	}

	/**
	 * Obtiene el valor de estadoSolicitudRespuesta
	 * 
	 * @return El valor de estadoSolicitudRespuesta
	 */
	public String getEstadoSolicitudRespuesta() {
		return estadoSolicitudRespuesta;
	}

	/**
	 * Establece el valor de estadoSolicitudRespuesta
	 * 
	 * @param estadoSolicitudRespuesta
	 *            El valor de estadoSolicitudRespuesta por asignar
	 */
	public void setEstadoSolicitudRespuesta(String estadoSolicitudRespuesta) {
		this.estadoSolicitudRespuesta = estadoSolicitudRespuesta;
	}

	/**
	 * Obtiene el valor de idSolicitud
	 * 
	 * @return El valor de idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Establece el valor de idSolicitud
	 * 
	 * @param idSolicitud
	 *            El valor de idSolicitud por asignar
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Obtiene el valor de tiposAportante
	 * 
	 * @return El valor de tiposAportante
	 */
	public List<TipoSolicitanteMovimientoAporteEnum> getTiposAportante() {
		return tiposAportante;
	}

	/**
	 * Establece el valor de tiposAportante
	 * 
	 * @param tiposAportante
	 *            El valor de tiposAportante por asignar
	 */
	public void setTiposAportante(List<TipoSolicitanteMovimientoAporteEnum> tiposAportante) {
		this.tiposAportante = tiposAportante;
	}

	/**Obtiene el valor de idSolicitudAfiliacion
	 * @return El valor de idSolicitudAfiliacion
	 */
	public Long getIdSolicitudAfiliacion() {
		return idSolicitudAfiliacion;
	}

	/** Establece el valor de idSolicitudAfiliacion
	 * @param idSolicitudAfiliacion El valor de idSolicitudAfiliacion por asignar
	 */
	public void setIdSolicitudAfiliacion(Long idSolicitudAfiliacion) {
		this.idSolicitudAfiliacion = idSolicitudAfiliacion;
	}

	/**
	 * @return the fechaRadicado
	 */
	public Long getFechaRadicado() {
		return fechaRadicado;
	}

	/**
	 * @param fechaRadicado the fechaRadicado to set
	 */
	public void setFechaRadicado(Long fechaRadicado) {
		this.fechaRadicado = fechaRadicado;
	}

    /**
     * @return the canalSolicitud
     */
    public CanalRecepcionEnum getCanalSolicitud() {
        return canalSolicitud;
    }

    /**
     * @param canalSolicitud the canalSolicitud to set
     */
    public void setCanalSolicitud(CanalRecepcionEnum canalSolicitud) {
        this.canalSolicitud = canalSolicitud;
    }
}
