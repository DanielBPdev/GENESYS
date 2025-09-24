package com.asopagos.dto.modelo;

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
 * DTO que contiene los campos de un Afiliado.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudModeloDTO implements Serializable {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2233386651445406761L;

	/**
	 * Código identificador de llave primaria de la solicitud 
	 */
	private Long idSolicitud;
    
	/**
	 * Descripción de la instancia del proceso
	 */
	private String idInstanciaProceso;
    
	/**
	 * Descripción del canal de recepción de las solicitudes
	 */
    private CanalRecepcionEnum canalRecepcion;
    
	/**
	 * Descripción del estado de documentación de la solicitud de afiliación
	 */
	private EstadoDocumentacionEnum estadoDocumentacion;   
    
	/**
	 * Descripción de los formatos de entrega de documentos al back
	 */
	private FormatoEntregaDocumentoEnum metodoEnvio;    
	
	/**
	 * Id que identifica a la caja de correspondencia asociada a la solicitud
	 */
	private Long idCajaCorrespondencia;
	
	/**
	 * Descripción del tipo de transacción dentro del proceso de la solicitud
	 */
	private TipoTransaccionEnum tipoTransaccion;
    
	/**
	 * Descripción de la clasificación del tipo solicitante
	 */
	private ClasificacionEnum clasificacion;
    
	/**
	 * Descripción del tipo de radicación de los estados de documentación de la afiliación de un nuevo empleador
	 */
	private TipoRadicacionEnum tipoRadicacion;
	
	/**
	 * Número de radicación de la solicitud
	 */
	private String numeroRadicacion;	
	
	/**
	 * Usuario de la radicación
	 */
	private String usuarioRadicacion;	
	
	/**
	 * Fecha de radicación de la solicitud
	 */
	private Long fechaRadicacion;    

	/**
	 * Descripción del nombre de la ciudad del usuario de radiación
	 */
    private String ciudadSedeRadicacion;

	/**
	 * Usuario destinatario
	 */
	private String destinatario;    
	
	/**
	 * Descripción del código de la sede del destinatario
	 */
	private String sedeDestinatario; 
    
	/**
	 * Fecha de creación de la solicitud
	 */
	private Long fechaCreacion;    
	
	/**
	 * Observaciones de la solicitud
	 */
	private String observacion;
	
	/**
     * Id que identifica a la solicitud de carga múltiple 
     * por la cual se inicío el proceso de afiliación
     */
    private Long cargaAfiliacionMultiple;
    
	/**
	 * Descripción del estado del proceso.
	 */
	private ResultadoProcesoEnum resultadoProceso;
	
	/**
     * Identificador diferencias cargue actualizacion
     */
    private Long idDiferenciaCargueActualizacion;
    
    /**
     * Identificador de anularidad de la solciitud
     */
    private Boolean anulada;


    /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public Solicitud convertToSolicitudEntity(){
    	
    	Solicitud solicitud = new Solicitud();
    	solicitud.setCanalRecepcion(this.getCanalRecepcion());
    	solicitud.setCargaAfiliacionMultiple(this.getCargaAfiliacionMultiple());
    	solicitud.setCiudadSedeRadicacion(this.getCiudadSedeRadicacion());
    	solicitud.setClasificacion(this.getClasificacion());
    	solicitud.setDestinatario(this.getDestinatario());
    	solicitud.setEstadoDocumentacion(this.getEstadoDocumentacion());
    	if(this.getFechaCreacion()!=null){
    		solicitud.setFechaCreacion(new Date(this.getFechaCreacion()));
    	}
    	if(this.getFechaRadicacion()!=null){
    		solicitud.setFechaRadicacion(new Date(this.getFechaRadicacion()));
    	}
    	solicitud.setIdCajaCorrespondencia(this.getIdCajaCorrespondencia());
    	solicitud.setIdInstanciaProceso(this.getIdInstanciaProceso());
    	solicitud.setIdSolicitud(this.getIdSolicitud());
    	solicitud.setMetodoEnvio(this.getMetodoEnvio());
    	solicitud.setNumeroRadicacion(this.getNumeroRadicacion());
    	solicitud.setObservacion(this.getObservacion());
    	solicitud.setResultadoProceso(this.getResultadoProceso());
    	solicitud.setSedeDestinatario(this.getSedeDestinatario());
    	solicitud.setTipoRadicacion(this.getTipoRadicacion());
    	solicitud.setTipoTransaccion(this.getTipoTransaccion());
    	solicitud.setUsuarioRadicacion(this.getUsuarioRadicacion());
    	solicitud.setIdDiferenciaCargueActualizacion(this.getIdDiferenciaCargueActualizacion());
    	solicitud.setAnulada(this.getAnulada());
    	
    	return solicitud;
    }
    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud entidad a convertir.
     */
    public void convertToDTO(Solicitud solicitud){
		this.setCanalRecepcion(solicitud.getCanalRecepcion());
		this.setCargaAfiliacionMultiple(solicitud.getCargaAfiliacionMultiple());
		this.setCiudadSedeRadicacion(solicitud.getCiudadSedeRadicacion());
		this.setClasificacion(solicitud.getClasificacion());
		this.setDestinatario(solicitud.getDestinatario());
		this.setEstadoDocumentacion(solicitud.getEstadoDocumentacion());
		if (solicitud.getFechaCreacion() != null) {
			this.setFechaCreacion(solicitud.getFechaCreacion().getTime());
		}
		if (solicitud.getFechaRadicacion() != null) {
			this.setFechaRadicacion(solicitud.getFechaRadicacion().getTime());
		}
		this.setIdCajaCorrespondencia(solicitud.getIdCajaCorrespondencia());
		this.setIdInstanciaProceso(solicitud.getIdInstanciaProceso());
		this.setIdSolicitud(solicitud.getIdSolicitud());
		this.setMetodoEnvio(solicitud.getMetodoEnvio());
		this.setNumeroRadicacion(solicitud.getNumeroRadicacion());
		this.setObservacion(solicitud.getObservacion());
		this.setResultadoProceso(solicitud.getResultadoProceso());
		this.setSedeDestinatario(solicitud.getSedeDestinatario());
		this.setTipoRadicacion(solicitud.getTipoRadicacion());
		this.setTipoTransaccion(solicitud.getTipoTransaccion());
		this.setUsuarioRadicacion(solicitud.getUsuarioRadicacion());
		this.setTipoTransaccion(solicitud.getTipoTransaccion());
		this.setIdDiferenciaCargueActualizacion(solicitud.getIdDiferenciaCargueActualizacion());
		this.anulada = solicitud.getAnulada();
    }
    
    /**
     * Método encargado de copiar un DTO  a una Entidad.
     * @param solicitud previamente consultado.
     */
	public Solicitud  copyDTOToEntiy(Solicitud solicitud) {
		if (this.getCanalRecepcion() != null) {
			solicitud.setCanalRecepcion(this.getCanalRecepcion());
		}
		if (this.getCargaAfiliacionMultiple() != null) {
			solicitud.setCargaAfiliacionMultiple(this.getCargaAfiliacionMultiple());
		}
		if (this.getCiudadSedeRadicacion() != null) {
			solicitud.setCiudadSedeRadicacion(this.getCiudadSedeRadicacion());
		}
		if (this.getClasificacion() != null) {
			solicitud.setClasificacion(this.getClasificacion());
		}
		if (this.getDestinatario() != null) {
			solicitud.setDestinatario(this.getDestinatario());
		}
		if (this.getEstadoDocumentacion() != null) {
			solicitud.setEstadoDocumentacion(this.getEstadoDocumentacion());
		}
		if (this.getFechaCreacion() != null) {
			solicitud.setFechaCreacion(new Date(this.getFechaCreacion()));
		}
		if (this.getFechaRadicacion() != null) {
			solicitud.setFechaRadicacion(new Date(this.getFechaRadicacion()));
		}
		if (this.getIdCajaCorrespondencia() != null) {
			solicitud.setIdCajaCorrespondencia(this.getIdCajaCorrespondencia());
		}
		if (this.getIdInstanciaProceso() != null) {
			solicitud.setIdInstanciaProceso(this.getIdInstanciaProceso());
		}
		if (this.getIdSolicitud() != null) {
			solicitud.setIdSolicitud(this.getIdSolicitud());
		}
		if (this.getMetodoEnvio() != null) {
			solicitud.setMetodoEnvio(this.getMetodoEnvio());
		}
		if (this.getNumeroRadicacion() != null) {
			solicitud.setNumeroRadicacion(this.getNumeroRadicacion());
		}
		if (this.getObservacion() != null) {
			solicitud.setObservacion(this.getObservacion());
		}
		if (this.getResultadoProceso() != null) {
			solicitud.setResultadoProceso(this.getResultadoProceso());
		}
		if (this.getSedeDestinatario() != null) {
			solicitud.setSedeDestinatario(this.getSedeDestinatario());
		}
		if (this.getTipoRadicacion() != null) {
			solicitud.setTipoRadicacion(this.getTipoRadicacion());
		}
		if (this.getUsuarioRadicacion() != null) {
			solicitud.setUsuarioRadicacion(this.getUsuarioRadicacion());
		}
		if(this.getTipoTransaccion()!= null){
			solicitud.setTipoTransaccion(this.getTipoTransaccion());
		}
		if (this.getIdDiferenciaCargueActualizacion() != null) {
            solicitud.setIdDiferenciaCargueActualizacion(this.getIdDiferenciaCargueActualizacion());
        }
		if(this.getAnulada() != null){
		    solicitud.setAnulada(this.getAnulada());
		}
		return solicitud;
	}
	
	/**
	 * Método que retorna el valor de idSolicitud.
	 * @return valor de idSolicitud.
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * Método encargado de modificar el valor de idSolicitud.
	 * @param valor para modificar idSolicitud.
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	/**
	 * Método que retorna el valor de idInstanciaProceso.
	 * @return valor de idInstanciaProceso.
	 */
	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}
	/**
	 * Método encargado de modificar el valor de idInstanciaProceso.
	 * @param valor para modificar idInstanciaProceso.
	 */
	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}
	/**
	 * Método que retorna el valor de canalRecepcion.
	 * @return valor de canalRecepcion.
	 */
	public CanalRecepcionEnum getCanalRecepcion() {
		return canalRecepcion;
	}
	/**
	 * Método encargado de modificar el valor de canalRecepcion.
	 * @param valor para modificar canalRecepcion.
	 */
	public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
		this.canalRecepcion = canalRecepcion;
	}
	/**
	 * Método que retorna el valor de estadoDocumentacion.
	 * @return valor de estadoDocumentacion.
	 */
	public EstadoDocumentacionEnum getEstadoDocumentacion() {
		return estadoDocumentacion;
	}
	/**
	 * Método encargado de modificar el valor de estadoDocumentacion.
	 * @param valor para modificar estadoDocumentacion.
	 */
	public void setEstadoDocumentacion(EstadoDocumentacionEnum estadoDocumentacion) {
		this.estadoDocumentacion = estadoDocumentacion;
	}
	/**
	 * Método que retorna el valor de metodoEnvio.
	 * @return valor de metodoEnvio.
	 */
	public FormatoEntregaDocumentoEnum getMetodoEnvio() {
		return metodoEnvio;
	}
	/**
	 * Método encargado de modificar el valor de metodoEnvio.
	 * @param valor para modificar metodoEnvio.
	 */
	public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
		this.metodoEnvio = metodoEnvio;
	}
	/**
	 * Método que retorna el valor de idCajaCorrespondencia.
	 * @return valor de idCajaCorrespondencia.
	 */
	public Long getIdCajaCorrespondencia() {
		return idCajaCorrespondencia;
	}
	/**
	 * Método encargado de modificar el valor de idCajaCorrespondencia.
	 * @param valor para modificar idCajaCorrespondencia.
	 */
	public void setIdCajaCorrespondencia(Long idCajaCorrespondencia) {
		this.idCajaCorrespondencia = idCajaCorrespondencia;
	}
	/**
	 * Método que retorna el valor de tipoTransaccion.
	 * @return valor de tipoTransaccion.
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}
	/**
	 * Método encargado de modificar el valor de tipoTransaccion.
	 * @param valor para modificar tipoTransaccion.
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	/**
	 * Método que retorna el valor de clasificacion.
	 * @return valor de clasificacion.
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}
	/**
	 * Método encargado de modificar el valor de clasificacion.
	 * @param valor para modificar clasificacion.
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}
	/**
	 * Método que retorna el valor de tipoRadicacion.
	 * @return valor de tipoRadicacion.
	 */
	public TipoRadicacionEnum getTipoRadicacion() {
		return tipoRadicacion;
	}
	/**
	 * Método encargado de modificar el valor de tipoRadicacion.
	 * @param valor para modificar tipoRadicacion.
	 */
	public void setTipoRadicacion(TipoRadicacionEnum tipoRadicacion) {
		this.tipoRadicacion = tipoRadicacion;
	}
	/**
	 * Método que retorna el valor de numeroRadicacion.
	 * @return valor de numeroRadicacion.
	 */
	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}
	/**
	 * Método encargado de modificar el valor de numeroRadicacion.
	 * @param valor para modificar numeroRadicacion.
	 */
	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}
	/**
	 * Método que retorna el valor de usuarioRadicacion.
	 * @return valor de usuarioRadicacion.
	 */
	public String getUsuarioRadicacion() {
		return usuarioRadicacion;
	}
	/**
	 * Método encargado de modificar el valor de usuarioRadicacion.
	 * @param valor para modificar usuarioRadicacion.
	 */
	public void setUsuarioRadicacion(String usuarioRadicacion) {
		this.usuarioRadicacion = usuarioRadicacion;
	}
	/**
	 * Método que retorna el valor de fechaRadicacion.
	 * @return valor de fechaRadicacion.
	 */
	public Long getFechaRadicacion() {
		return fechaRadicacion;
	}
	/**
	 * Método encargado de modificar el valor de fechaRadicacion.
	 * @param valor para modificar fechaRadicacion.
	 */
	public void setFechaRadicacion(Long fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}
	/**
	 * Método que retorna el valor de ciudadSedeRadicacion.
	 * @return valor de ciudadSedeRadicacion.
	 */
	public String getCiudadSedeRadicacion() {
		return ciudadSedeRadicacion;
	}
	/**
	 * Método encargado de modificar el valor de ciudadSedeRadicacion.
	 * @param valor para modificar ciudadSedeRadicacion.
	 */
	public void setCiudadSedeRadicacion(String ciudadSedeRadicacion) {
		this.ciudadSedeRadicacion = ciudadSedeRadicacion;
	}
	/**
	 * Método que retorna el valor de destinatario.
	 * @return valor de destinatario.
	 */
	public String getDestinatario() {
		return destinatario;
	}
	/**
	 * Método encargado de modificar el valor de destinatario.
	 * @param valor para modificar destinatario.
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	/**
	 * Método que retorna el valor de sedeDestinatario.
	 * @return valor de sedeDestinatario.
	 */
	public String getSedeDestinatario() {
		return sedeDestinatario;
	}
	/**
	 * Método encargado de modificar el valor de sedeDestinatario.
	 * @param valor para modificar sedeDestinatario.
	 */
	public void setSedeDestinatario(String sedeDestinatario) {
		this.sedeDestinatario = sedeDestinatario;
	}
	/**
	 * Método que retorna el valor de fechaCreacion.
	 * @return valor de fechaCreacion.
	 */
	public Long getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * Método encargado de modificar el valor de fechaCreacion.
	 * @param valor para modificar fechaCreacion.
	 */
	public void setFechaCreacion(Long fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * Método que retorna el valor de observacion.
	 * @return valor de observacion.
	 */
	public String getObservacion() {
		return observacion;
	}
	/**
	 * Método encargado de modificar el valor de observacion.
	 * @param valor para modificar observacion.
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	/**
	 * Método que retorna el valor de cargaAfiliacionMultiple.
	 * @return valor de cargaAfiliacionMultiple.
	 */
	public Long getCargaAfiliacionMultiple() {
		return cargaAfiliacionMultiple;
	}
	/**
	 * Método encargado de modificar el valor de cargaAfiliacionMultiple.
	 * @param valor para modificar cargaAfiliacionMultiple.
	 */
	public void setCargaAfiliacionMultiple(Long cargaAfiliacionMultiple) {
		this.cargaAfiliacionMultiple = cargaAfiliacionMultiple;
	}
	/**
	 * Método que retorna el valor de resultadoProceso.
	 * @return valor de resultadoProceso.
	 */
	public ResultadoProcesoEnum getResultadoProceso() {
		return resultadoProceso;
	}
	/**
	 * Método encargado de modificar el valor de resultadoProceso.
	 * @param valor para modificar resultadoProceso.
	 */
	public void setResultadoProceso(ResultadoProcesoEnum resultadoProceso) {
		this.resultadoProceso = resultadoProceso;
	}
    /**
     * @return the idDiferenciaCargueActualizacion
     */
    public Long getIdDiferenciaCargueActualizacion() {
        return idDiferenciaCargueActualizacion;
    }
    /**
     * @param idDiferenciaCargueActualizacion the idDiferenciaCargueActualizacion to set
     */
    public void setIdDiferenciaCargueActualizacion(Long idDiferenciaCargueActualizacion) {
        this.idDiferenciaCargueActualizacion = idDiferenciaCargueActualizacion;
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
