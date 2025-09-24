package com.asopagos.sat.dto;

public class TareaActivaDTO {
    private String idTarea;
    private String ruleNav;
    private String idInstanciaProceso;
    private String fechaActivacion;
    private String idSolicitud;
    private String numeroRadicado;
    private String usuarioRadicador;

    public TareaActivaDTO() {}

    public TareaActivaDTO(String idTarea, String ruleNav, String idInstanciaProceso, String fechaActivacion, String idSolicitud, String numeroRadicado, String usuarioRadicador) {
        this.idTarea = idTarea;
        this.ruleNav = ruleNav;
        this.idInstanciaProceso = idInstanciaProceso;
        this.fechaActivacion = fechaActivacion;
        this.idSolicitud = idSolicitud;
        this.numeroRadicado = numeroRadicado;
        this.usuarioRadicador = usuarioRadicador;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    public String getRuleNav() {
        return ruleNav;
    }

    public void setRuleNav(String ruleNav) {
        this.ruleNav = ruleNav;
    }

    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    public String getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(String fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public String getUsuarioRadicador() {
        return usuarioRadicador;
    }

    public void setUsuarioRadicador(String usuarioRadicador) {
        this.usuarioRadicador = usuarioRadicador;
    }
}
