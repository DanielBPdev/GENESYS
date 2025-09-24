package com.asopagos.afiliaciones.empleadores.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripción:</b> DTO para retornar los datos de la solicitud afiliación empleador
 * <b>Historia de Usuario:</b> 124
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
@XmlRootElement
public class SolicitudAfiliacionEmpleadorDTO implements Serializable {
    /**
     * serialVersionUID
     */
    public static final long serialVersionUID = 1L;

    private Long idSolicitud;
    private Long idSolicitudAfiliacionEmpleador;
    private String idInstanciaProceso;
    private CanalRecepcionEnum canalRecepcion;
    private EstadoDocumentacionEnum	estadoDocumentacion;
    private FormatoEntregaDocumentoEnum	metodoEnvio;
    private Long idCajaCorrespondencia;
    private TipoTransaccionEnum	tipoTransaccion;
    private ClasificacionEnum clasificacion;
    private TipoRadicacionEnum tipoRadicacion;
    private String numeroRadicacion;
    private String usuarioRadicacion;
    private Date fechaRadicacion;
    private String ciudadSedeRadicacion;
    private String destinatario;
    private String sedeDestinatario;
    private Date fechaCreacion;
    private Long idEmpleador;
    private Boolean anulada;
    
    /**
     * Método que convierte la entidad solicitud de afiliación al dto
     * 
     * @param solAfi
     * @return
     */
    public static SolicitudAfiliacionEmpleadorDTO convertToDTO(SolicitudAfiliacionEmpleador solAfi) {
        Solicitud sol = solAfi.getSolicitudGlobal();
        
        SolicitudAfiliacionEmpleadorDTO solicitud = new SolicitudAfiliacionEmpleadorDTO();
        solicitud.setIdSolicitud(sol.getIdSolicitud());
        solicitud.setIdEmpleador(solAfi.getIdEmpleador());
        solicitud.setIdSolicitudAfiliacionEmpleador(solAfi.getIdSolicitudAfiliacionEmpleador());
        solicitud.setIdInstanciaProceso(sol.getIdInstanciaProceso());
        solicitud.setCanalRecepcion(sol.getCanalRecepcion());
        solicitud.setEstadoDocumentacion(sol.getEstadoDocumentacion());
        solicitud.setMetodoEnvio(sol.getMetodoEnvio());
        solicitud.setIdCajaCorrespondencia(sol.getIdCajaCorrespondencia());
        solicitud.setTipoTransaccion(sol.getTipoTransaccion());
        solicitud.setClasificacion(sol.getClasificacion());
        solicitud.setTipoRadicacion(sol.getTipoRadicacion());
        solicitud.setNumeroRadicacion(sol.getNumeroRadicacion());
        solicitud.setUsuarioRadicacion(sol.getUsuarioRadicacion());
        solicitud.setFechaRadicacion(sol.getFechaRadicacion());
        solicitud.setCiudadSedeRadicacion(sol.getCiudadSedeRadicacion());
        solicitud.setDestinatario(sol.getDestinatario());
        solicitud.setSedeDestinatario(sol.getSedeDestinatario());
        solicitud.setFechaCreacion(sol.getFechaCreacion());
        solicitud.setAnulada(sol.getAnulada());
        return solicitud;
    }
    
	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	/**
	 * @return the idSolicitudAfiliacionEmpleador
	 */
	public Long getIdSolicitudAfiliacionEmpleador() {
		return idSolicitudAfiliacionEmpleador;
	}
	/**
	 * @param idSolicitudAfiliacionEmpleador the idSolicitudAfiliacionEmpleador to set
	 */
	public void setIdSolicitudAfiliacionEmpleador(Long idSolicitudAfiliacionEmpleador) {
		this.idSolicitudAfiliacionEmpleador = idSolicitudAfiliacionEmpleador;
	}
	/**
	 * @return the idInstanciaProceso
	 */
	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}
	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
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
	 * @param canalRecepcion the canalRecepcion to set
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
	 * @param estadoDocumentacion the estadoDocumentacion to set
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
	 * @param metodoEnvio the metodoEnvio to set
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
	 * @param idCajaCorrespondencia the idCajaCorrespondencia to set
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
	 * @param tipoTransaccion the tipoTransaccion to set
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
	 * @param clasificacion the clasificacion to set
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
	 * @param tipoRadicacion the tipoRadicacion to set
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
	 * @param numeroRadicacion the numeroRadicacion to set
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
	 * @param usuarioRadicacion the usuarioRadicacion to set
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
	 * @param fechaRadicacion the fechaRadicacion to set
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
	 * @param ciudadSedeRadicacion the ciudadSedeRadicacion to set
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
	 * @param destinatario the destinatario to set
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
	 * @param sedeDestinatario the sedeDestinatario to set
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
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
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

	
}
