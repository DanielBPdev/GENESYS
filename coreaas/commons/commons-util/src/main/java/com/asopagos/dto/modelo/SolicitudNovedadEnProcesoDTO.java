package com.asopagos.dto.modelo;

import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;


/**
 * DTO que contiene los campos de un Afiliado.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudNovedadEnProcesoDTO  {
	/**
	 * Código identificador de la solicitud de novedad.
	 */
	private Long idSolicitudNovedad;
    
	/**
	 * Descripción del estado de la solicitud de novedad.
	 */
    private EstadoSolicitudNovedadEnum estadoSolicitud;
	
	/**
	 * Id de la novedad asociada a la solicitud (es la parametrizacion de Novedad).
	 */
	private Long idNovedad;
	
	/**
	 * Observaciones de la novedad.
	 */
	private String observacionesNovedad;

	/**
	 * Atributo que indica si la solicitud se genero por una carga multiple.
	 */
	private Boolean cargaMultiple;

    /**
	 * Atributo que indica si la solicitud se genero por una carga multiple.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

    /**
	 * Atributo que indica si la solicitud se genero por una carga multiple.
	 */
	private String numeroIdentificacion;

    /**
	 * Descripción del canal de recepción de las solicitudes
	 */
    private CanalRecepcionEnum canalRecepcion;

    /**
	 * Descripción de la clasificación del tipo solicitante
	 */
	private ClasificacionEnum clasificacion;

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
	 * Fecha de creación de la solicitud
	 */
	private Long fechaCreacion;

    /**
	 * Descripción del estado del proceso.
	 */
	private ResultadoProcesoEnum resultadoProceso;

    /**
	 * Código identificador de llave primaria de la solicitud 
	 */
	private Long idSolicitud;

    /**
	 * Código identificador de llave primaria de la solicitud 
	 */
	private TipoTransaccionEnum tipoTransaccion;



	/**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud entidad a convertir.
     */
    public void convertToDTO(Solicitud solicitud){

        this.setCanalRecepcion(solicitud.getCanalRecepcion());
        this.setClasificacion(solicitud.getClasificacion());
        if (solicitud.getFechaCreacion() != null) {
			this.setFechaCreacion(solicitud.getFechaCreacion().getTime());
		}
		if (solicitud.getFechaRadicacion() != null) {
			this.setFechaRadicacion(solicitud.getFechaRadicacion().getTime());
		}
        this.setNumeroRadicacion(solicitud.getNumeroRadicacion());
        this.setResultadoProceso(solicitud.getResultadoProceso());
        this.setTipoTransaccion(solicitud.getTipoTransaccion());
        this.setUsuarioRadicacion(solicitud.getUsuarioRadicacion());
		this.setIdSolicitud(solicitud.getIdSolicitud());
    	
    }
    public void convertToDTOSoli(SolicitudNovedad solicitudNovedad){
        if(solicitudNovedad.getSolicitudGlobal()!=null){
    		convertToDTO(solicitudNovedad.getSolicitudGlobal());
    	}
        this.setIdSolicitudNovedad(solicitudNovedad.getIdSolicitudNovedad());
    	this.setIdNovedad(solicitudNovedad.getIdNovedad());
    	this.setEstadoSolicitud(solicitudNovedad.getEstadoSolicitud());
    	this.setObservacionesNovedad(solicitudNovedad.getObservaciones());
    	this.setCargaMultiple(solicitudNovedad.getCargaMultiple());
    }

	public SolicitudNovedadEnProcesoDTO(
			Long idSolicitudNovedad,
			String estadoSolicitud,
			Long idNovedad,
			String observacionesNovedad,
			Boolean cargaMultiple,
			String tipoIdentificacion,
			String numeroIdentificacion,
			String canalRecepcion,
			String clasificacion,
			String numeroRadicacion,
			String usuarioRadicacion,
			Long fechaRadicacion,
			Long fechaCreacion,
			String resultadoProceso,
			Long idSolicitud,
			String tipoTransaccion
	) {
		this.idSolicitudNovedad = idSolicitudNovedad;
		this.estadoSolicitud = estadoSolicitud != null ? EstadoSolicitudNovedadEnum.valueOf(estadoSolicitud) : null;
		this.idNovedad = idNovedad;
		this.observacionesNovedad = observacionesNovedad;
		this.cargaMultiple = cargaMultiple;
		this.tipoIdentificacion = tipoIdentificacion != null ? TipoIdentificacionEnum.valueOf(tipoIdentificacion) : null;
		this.numeroIdentificacion = numeroIdentificacion;
		this.canalRecepcion = canalRecepcion != null ? CanalRecepcionEnum.valueOf(canalRecepcion) : null;
		this.clasificacion = clasificacion != null ? ClasificacionEnum.valueOf(clasificacion) : null;
		this.numeroRadicacion = numeroRadicacion;
		this.usuarioRadicacion = usuarioRadicacion;
		this.fechaRadicacion = fechaRadicacion;
		this.fechaCreacion = fechaCreacion;
		this.resultadoProceso = resultadoProceso != null ? ResultadoProcesoEnum.valueOf(resultadoProceso) : null;
		this.idSolicitud = idSolicitud;
		this.tipoTransaccion = tipoTransaccion != null ? TipoTransaccionEnum.valueOf(tipoTransaccion) : null;
	}
	/**
	 * Método que retorna el valor de idSolicitudNovedad.
	 * @return valor de idSolicitudNovedad.
	 */
	public Long getIdSolicitudNovedad() {
		return idSolicitudNovedad;
	}
	/**
	 * Método encargado de modificar el valor de idSolicitudNovedad.
	 * @param valor para modificar idSolicitudNovedad.
	 */
	public void setIdSolicitudNovedad(Long idSolicitudNovedad) {
		this.idSolicitudNovedad = idSolicitudNovedad;
	}
	/**
	 * Método que retorna el valor de estadoSolicitud.
	 * @return valor de estadoSolicitud.
	 */
	public EstadoSolicitudNovedadEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}
	/**
	 * Método encargado de modificar el valor de estadoSolicitud.
	 * @param valor para modificar estadoSolicitud.
	 */
	public void setEstadoSolicitud(EstadoSolicitudNovedadEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
	/**
	 * Método que retorna el valor de idNovedad.
	 * @return valor de idNovedad.
	 */
	public Long getIdNovedad() {
		return idNovedad;
	}
	/**
	 * Método encargado de modificar el valor de idNovedad.
	 * @param valor para modificar idNovedad.
	 */
	public void setIdNovedad(Long idNovedad) {
		this.idNovedad = idNovedad;
	}
	/**
	 * Método que retorna el valor de observacionesNovedad.
	 * @return valor de observacionesNovedad.
	 */
	public String getObservacionesNovedad() {
		return observacionesNovedad;
	}
	/**
	 * Método encargado de modificar el valor de observacionesNovedad.
	 * @param valor para modificar observacionesNovedad.
	 */
	public void setObservacionesNovedad(String observacionesNovedad) {
		this.observacionesNovedad = observacionesNovedad;
	}
	/**
	 * Método que retorna el valor de cargaMultiple.
	 * @return valor de cargaMultiple.
	 */
	public final Boolean getCargaMultiple() {
		return cargaMultiple;
	}
	/**
	 * Método encargado de modificar el valor de cargaMultiple.
	 * @param valor para modificar cargaMultiple.
	 */
	public final void setCargaMultiple(Boolean cargaMultiple) {
		this.cargaMultiple = cargaMultiple;
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
	 * Método que retorna el valor de resultadoProceso.
	 * @return valor de NumeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	/**
	 * Método encargado de modificar el valor de resultadoProceso.
	 * @param valor para modificar resultadoProceso.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}	

     /**
	 * Método que retorna el valor de resultadoProceso.
	 * @return valor de TipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	/**
	 * Método encargado de modificar el valor de resultadoProceso.
	 * @param valor para modificar resultadoProceso.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
}
