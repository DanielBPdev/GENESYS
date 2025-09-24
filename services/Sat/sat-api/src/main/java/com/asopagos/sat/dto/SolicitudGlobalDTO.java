package com.asopagos.sat.dto;


public class SolicitudGlobalDTO {
    private String idSolicitud;
    private String idInstanciaProceso;
    private String canalRecepcion;
    private String metodoEnvio;
    private String tipoTransaccion;
    private String clasificacion;
    private String usuarioRadicacion;
    private String fechaCreacion;
    
    // Constructor por defecto
    public SolicitudGlobalDTO() {}
    
    // Constructor con parámetros
    public SolicitudGlobalDTO(String idSolicitud, String idInstanciaProceso, String canalRecepcion, String metodoEnvio,
                              String tipoTransaccion, String clasificacion, String usuarioRadicacion, String fechaCreacion) {
        this.idSolicitud = idSolicitud;
        this.idInstanciaProceso = idInstanciaProceso;
        this.canalRecepcion = canalRecepcion;
        this.metodoEnvio = metodoEnvio;
        this.tipoTransaccion = tipoTransaccion;
        this.clasificacion = clasificacion;
        this.usuarioRadicacion = usuarioRadicacion;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Setters
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }
    
    public void setCanalRecepcion(String canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }
    
    public void setMetodoEnvio(String metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }
    
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    
    public void setUsuarioRadicacion(String usuarioRadicacion) {
        this.usuarioRadicacion = usuarioRadicacion;
    }
    
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters
    public String getIdSolicitud() {
        return idSolicitud;
    }
    
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }
    
    public String getCanalRecepcion() {
        return canalRecepcion;
    }
    
    public String getMetodoEnvio() {
        return metodoEnvio;
    }
    
    public String getTipoTransaccion() {
        return tipoTransaccion;
    }
    
    public String getClasificacion() {
        return clasificacion;
    }
    
    public String getUsuarioRadicacion() {
        return usuarioRadicacion;
    }
    
    public String getFechaCreacion() {
        return fechaCreacion;
    }
}
