package com.asopagos.sat.dto;

import com.asopagos.sat.dto.SolicitudGlobalDTO;

public class SolicitudAfiliacionEmpleadorDTO {
    private String idSolicitudAfiliacionEmpleador;
    private SolicitudGlobalDTO solicitudGlobal;
    private String idEmpleador;
    private String estadoSolicitud;
    
    public SolicitudAfiliacionEmpleadorDTO() {}
    
    public SolicitudAfiliacionEmpleadorDTO(String idSolicitudAfiliacionEmpleador, SolicitudGlobalDTO solicitudGlobal, String idEmpleador, String estadoSolicitud) {
        this.idSolicitudAfiliacionEmpleador = idSolicitudAfiliacionEmpleador;
        this.solicitudGlobal = solicitudGlobal;
        this.idEmpleador = idEmpleador;
        this.estadoSolicitud = estadoSolicitud;
    }
    
    public String getIdSolicitudAfiliacionEmpleador() {
        return idSolicitudAfiliacionEmpleador;
    }
    
    public void setIdSolicitudAfiliacionEmpleador(String idSolicitudAfiliacionEmpleador) {
        this.idSolicitudAfiliacionEmpleador = idSolicitudAfiliacionEmpleador;
    }
    
    public SolicitudGlobalDTO getSolicitudGlobal() {
        return solicitudGlobal;
    }
    
    public void setSolicitudGlobal(SolicitudGlobalDTO solicitudGlobal) {
        this.solicitudGlobal = solicitudGlobal;
    }
    
    public String getIdEmpleador() {
        return idEmpleador;
    }
    
    public void setIdEmpleador(String idEmpleador) {
        this.idEmpleador = idEmpleador;
    }
    
    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }
    
    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
}
