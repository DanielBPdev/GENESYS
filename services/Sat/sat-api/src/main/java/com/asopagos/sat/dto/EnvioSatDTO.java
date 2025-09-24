package com.asopagos.sat.dto;

public class EnvioSatDTO {
    private String numRadicado;
    private String numTransaccion;
    private String tipoDocIdEmpleador;
    private String numDocIdEmpleador;
    private String serialSatOrgTerritoriales;
    private String resultadoTramite;
    private String fechaEfectivaAfiliacion;
    private String motivoRechazo;

    public EnvioSatDTO(String numTransaccion, String tipoDocIdEmpleador, String numDocIdEmpleador,
                       String serialSatOrgTerritoriales, String resultadoTramite, String fechaEfectivaAfiliacion,
                       String motivoRechazo, String numRadicado) {
        this.numTransaccion = numTransaccion;
        this.tipoDocIdEmpleador = tipoDocIdEmpleador;
        this.numDocIdEmpleador = numDocIdEmpleador;
        this.serialSatOrgTerritoriales = serialSatOrgTerritoriales;
        this.resultadoTramite = resultadoTramite;
        this.fechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
        this.motivoRechazo = motivoRechazo;
        this.numRadicado = numRadicado;
    }

    public EnvioSatDTO(){
        
    }

    // Getters and Setters
    public String getNumTransaccion() {
        return numTransaccion;
    }

    public void setNumTransaccion(String numTransaccion) {
        this.numTransaccion = numTransaccion;
    }

    public String getTipoDocIdEmpleador() {
        return tipoDocIdEmpleador;
    }

    public void setTipoDocIdEmpleador(String tipoDocIdEmpleador) {
        this.tipoDocIdEmpleador = tipoDocIdEmpleador;
    }

    public String getNumDocIdEmpleador() {
        return numDocIdEmpleador;
    }

    public void setNumDocIdEmpleador(String numDocIdEmpleador) {
        this.numDocIdEmpleador = numDocIdEmpleador;
    }

    public String getSerialSatOrgTerritoriales() {
        return serialSatOrgTerritoriales;
    }

    public void setSerialSatOrgTerritoriales(String serialSatOrgTerritoriales) {
        this.serialSatOrgTerritoriales = serialSatOrgTerritoriales;
    }

    public String getResultadoTramite() {
        return resultadoTramite;
    }

    public void setResultadoTramite(String resultadoTramite) {
        this.resultadoTramite = resultadoTramite;
    }

    public String getFechaEfectivaAfiliacion() {
        return fechaEfectivaAfiliacion;
    }

    public void setFechaEfectivaAfiliacion(String fechaEfectivaAfiliacion) {
        this.fechaEfectivaAfiliacion = fechaEfectivaAfiliacion;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
    public String getNumRadicado() {
        return numRadicado;
    }
    public void setNumRadicado(String numRadicado) {
        this.numRadicado = numRadicado;
    }
}