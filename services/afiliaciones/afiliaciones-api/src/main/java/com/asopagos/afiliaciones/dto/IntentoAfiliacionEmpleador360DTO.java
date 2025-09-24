package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

public class IntentoAfiliacionEmpleador360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String fechaCreacion;
    private String numeroRadicacion;
    private String canalRecepcion;
    private String causaIntentoFallido;
    private String identificaArchivoComunicado;
    private Long idSolicitud;
    private Long idDatoTemporal;
    /**
     * Estado de la solicitud
     */
    private String estadoSolcitud;

    /**
     * 
     */
    public IntentoAfiliacionEmpleador360DTO() {
    }
    
    /**
     * @param fechaCreacion
     * @param numeroRadicacion
     * @param canalRecepcion
     * @param causaIntentoFallido
     * @param identificaArchivoComunicado
     */
    public IntentoAfiliacionEmpleador360DTO(String fechaCreacion, String numeroRadicacion, String canalRecepcion,
            String causaIntentoFallido, String identificaArchivoComunicado) {
        this.fechaCreacion = fechaCreacion;
        this.numeroRadicacion = numeroRadicacion;
        this.canalRecepcion = canalRecepcion;
        this.causaIntentoFallido = causaIntentoFallido;
        this.identificaArchivoComunicado = identificaArchivoComunicado;
    }
    
    /**
     * @param fechaCreacion
     * @param numeroRadicacion
     * @param canalRecepcion
     * @param causaIntentoFallido
     * @param identificaArchivoComunicado
     * @param idSolicitud
     * @param idDatoTemporal
     * @param estadoSolcitud
     */
    public IntentoAfiliacionEmpleador360DTO(String fechaCreacion, String numeroRadicacion, String canalRecepcion,
            String causaIntentoFallido, String identificaArchivoComunicado, Long idSolicitud, Long idDatoTemporal, String estadoSolcitud) {
        super();
        this.fechaCreacion = fechaCreacion;
        this.numeroRadicacion = numeroRadicacion;
        this.canalRecepcion = canalRecepcion;
        this.causaIntentoFallido = causaIntentoFallido;
        this.identificaArchivoComunicado = identificaArchivoComunicado;
        this.idSolicitud = idSolicitud;
        this.idDatoTemporal = idDatoTemporal;
        this.estadoSolcitud = estadoSolcitud;
    }

    /**
     * @return the fechaCreacion
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    
    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
     * @return the canalRecepcion
     */
    public String getCanalRecepcion() {
        return canalRecepcion;
    }
    
    /**
     * @param canalRecepcion the canalRecepcion to set
     */
    public void setCanalRecepcion(String canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }
    
    /**
     * @return the causaIntentoFallido
     */
    public String getCausaIntentoFallido() {
        return causaIntentoFallido;
    }
    
    /**
     * @param causaIntentoFallido the causaIntentoFallido to set
     */
    public void setCausaIntentoFallido(String causaIntentoFallido) {
        this.causaIntentoFallido = causaIntentoFallido;
    }
    
    /**
     * @return the identificaArchivoComunicado
     */
    public String getIdentificaArchivoComunicado() {
        return identificaArchivoComunicado;
    }
    
    /**
     * @param identificaArchivoComunicado the identificaArchivoComunicado to set
     */
    public void setIdentificaArchivoComunicado(String identificaArchivoComunicado) {
        this.identificaArchivoComunicado = identificaArchivoComunicado;
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
	 * @return the idDatoTemporal
	 */
	public Long getIdDatoTemporal() {
		return idDatoTemporal;
	}

	/**
	 * @param idDatoTemporal the idDatoTemporal to set
	 */
	public void setIdDatoTemporal(Long idDatoTemporal) {
		this.idDatoTemporal = idDatoTemporal;
	}

    /**
     * @return the estadoSolcitud
     */
    public String getEstadoSolcitud() {
        return estadoSolcitud;
    }

    /**
     * @param estadoSolcitud the estadoSolcitud to set
     */
    public void setEstadoSolcitud(String estadoSolcitud) {
        this.estadoSolcitud = estadoSolcitud;
    }
	
}
