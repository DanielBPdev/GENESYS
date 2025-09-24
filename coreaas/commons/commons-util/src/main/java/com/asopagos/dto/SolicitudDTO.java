package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripción:</b> DTO para wrapper de datos de la entidad Solictud
 * <b>Historia de Usuario:</b> Transversal
 *
 * @author Luis Arturo Zarate Ayala<lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class SolicitudDTO implements Serializable {

	private Long idSolicitud;

	private String idInstanciaProceso;

	private CanalRecepcionEnum canalRecepcion;

	private EstadoDocumentacionEnum estadoDocumentacion;

	private FormatoEntregaDocumentoEnum metodoEnvio;

	private Long idCajaCorrespondencia;

	private TipoTransaccionEnum tipoTransaccion;

	private ClasificacionEnum clasificacion;

	private TipoRadicacionEnum tipoRadicacion;

	private String numeroRadicacion;

	private String usuarioRadicacion;

	private Date fechaRadicacion;

	private String ciudadSedeRadicacion;

	private String destinatario;

	private String sedeDestinatario;

	private Date fechaCreacion;
	
	private String observacion;
	
	private Long cargaAfiliacionMultiple;
	
	private String estadoSolicitud;
	
	private Long fechaActivacion;
	
	private Boolean anulada;
	
	private ResultadoProcesoEnum resultadoProceso;

	/**
	 * Contructor vacio
	 */
	public SolicitudDTO() {
		
	}
	
	public SolicitudDTO(Solicitud solicitud) {
		super();
		this.idSolicitud = solicitud.getIdSolicitud();
		this.idInstanciaProceso = solicitud.getIdInstanciaProceso();
		this.canalRecepcion = solicitud.getCanalRecepcion();
		this.estadoDocumentacion = solicitud.getEstadoDocumentacion();
		this.metodoEnvio = solicitud.getMetodoEnvio();
		this.idCajaCorrespondencia = solicitud.getIdCajaCorrespondencia();
		this.tipoTransaccion = solicitud.getTipoTransaccion();
		this.clasificacion = solicitud.getClasificacion();
		this.tipoRadicacion = solicitud.getTipoRadicacion();
		this.numeroRadicacion = solicitud.getNumeroRadicacion();
		this.usuarioRadicacion = solicitud.getUsuarioRadicacion();
		this.fechaRadicacion = solicitud.getFechaRadicacion();
		this.ciudadSedeRadicacion = solicitud.getCiudadSedeRadicacion();
		this.destinatario = solicitud.getDestinatario();
		this.sedeDestinatario = solicitud.getSedeDestinatario();
		this.fechaCreacion = solicitud.getFechaCreacion();
		this.anulada = solicitud.getAnulada();
		this.resultadoProceso = solicitud.getResultadoProceso();
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

	/**
	 * @return the idInstanciaProceso
	 */
	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso
	 *            the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	/**
	 * @return the canalRecepcion
	 */
	public CanalRecepcionEnum getCanalRecepcion() {
		return canalRecepcion;
	}

	/**
	 * @param canalRecepcion
	 *            the canalRecepcion to set
	 */
	public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
		this.canalRecepcion = canalRecepcion;
	}

	/**
	 * @return the estadoDocumentacion
	 */
	public EstadoDocumentacionEnum getEstadoDocumentacion() {
		return estadoDocumentacion;
	}

	/**
	 * @param estadoDocumentacion
	 *            the estadoDocumentacion to set
	 */
	public void setEstadoDocumentacion(EstadoDocumentacionEnum estadoDocumentacion) {
		this.estadoDocumentacion = estadoDocumentacion;
	}

	/**
	 * @return the metodoEnvio
	 */
	public FormatoEntregaDocumentoEnum getMetodoEnvio() {
		return metodoEnvio;
	}

	/**
	 * @param metodoEnvio
	 *            the metodoEnvio to set
	 */
	public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
		this.metodoEnvio = metodoEnvio;
	}

	/**
	 * @return the idCajaCorrespondencia
	 */
	public Long getIdCajaCorrespondencia() {
		return idCajaCorrespondencia;
	}

	/**
	 * @param idCajaCorrespondencia
	 *            the idCajaCorrespondencia to set
	 */
	public void setIdCajaCorrespondencia(Long idCajaCorrespondencia) {
		this.idCajaCorrespondencia = idCajaCorrespondencia;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion
	 *            the tipoTransaccion to set
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the clasificacion
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}

	/**
	 * @param clasificacion
	 *            the clasificacion to set
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}

	/**
	 * @return the tipoRadicacion
	 */
	public TipoRadicacionEnum getTipoRadicacion() {
		return tipoRadicacion;
	}

	/**
	 * @param tipoRadicacion
	 *            the tipoRadicacion to set
	 */
	public void setTipoRadicacion(TipoRadicacionEnum tipoRadicacion) {
		this.tipoRadicacion = tipoRadicacion;
	}

	/**
	 * @return the numeroRadicacion
	 */
	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	/**
	 * @param numeroRadicacion
	 *            the numeroRadicacion to set
	 */
	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	/**
	 * @return the usuarioRadicacion
	 */
	public String getUsuarioRadicacion() {
		return usuarioRadicacion;
	}

	/**
	 * @param usuarioRadicacion
	 *            the usuarioRadicacion to set
	 */
	public void setUsuarioRadicacion(String usuarioRadicacion) {
		this.usuarioRadicacion = usuarioRadicacion;
	}

	/**
	 * @return the fechaRadicacion
	 */
	public Date getFechaRadicacion() {
		return fechaRadicacion;
	}

	/**
	 * @param fechaRadicacion
	 *            the fechaRadicacion to set
	 */
	public void setFechaRadicacion(Date fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	/**
	 * @return the ciudadSedeRadicacion
	 */
	public String getCiudadSedeRadicacion() {
		return ciudadSedeRadicacion;
	}

	/**
	 * @param ciudadSedeRadicacion
	 *            the ciudadSedeRadicacion to set
	 */
	public void setCiudadSedeRadicacion(String ciudadSedeRadicacion) {
		this.ciudadSedeRadicacion = ciudadSedeRadicacion;
	}

	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario
	 *            the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the sedeDestinatario
	 */
	public String getSedeDestinatario() {
		return sedeDestinatario;
	}

	/**
	 * @param sedeDestinatario
	 *            the sedeDestinatario to set
	 */
	public void setSedeDestinatario(String sedeDestinatario) {
		this.sedeDestinatario = sedeDestinatario;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 *            the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the cargaAfiliacionMultiple
	 */
	public Long getCargaAfiliacionMultiple() {
		return cargaAfiliacionMultiple;
	}

	/**
	 * @param cargaAfiliacionMultiple the cargaAfiliacionMultiple to set
	 */
	public void setCargaAfiliacionMultiple(Long cargaAfiliacionMultiple) {
		this.cargaAfiliacionMultiple = cargaAfiliacionMultiple;
	}

	/**
	 * @return the estadoSolicitud
	 */
	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * @param estadoSolicitud the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

    /**
     * @return the fechaActivacion
     */
    public Long getFechaActivacion() {
        return fechaActivacion;
    }

    /**
     * @param fechaActivacion the fechaActivacion to set
     */
    public void setFechaActivacion(Long fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    /**
     * @return the anulada
     */
    public Boolean getAnulada() {
        return anulada;
    }

    /**
     * @param anulada the anulada to set
     */
    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }

    /**
     * @return the resultadoProceso
     */
    public ResultadoProcesoEnum getResultadoProceso() {
        return resultadoProceso;
    }

    /**
     * @param resultadoProceso
     *        the resultadoProceso to set
     */
    public void setResultadoProceso(ResultadoProcesoEnum resultadoProceso) {
        this.resultadoProceso = resultadoProceso;
    }

}