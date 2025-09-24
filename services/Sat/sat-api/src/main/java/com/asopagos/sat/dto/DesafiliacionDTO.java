/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.dto;

/**
 *
 * @author Sergio Reyes
 */
public class DesafiliacionDTO {
    private Long id;
    private String radicado;
    private String numeroTransaccion;    
    private String tipoDocumentoEmpleador;
    private String numeroDocumentoEmpleador;
    private String serialSAT;
    private String fechaSolicitudDesafiliacion;
    private String fechaEfectividadDesafiliacion;
    private String departamentoAfiliacion;
    private String pazYSalvo;
    private String fechaPazYSalvo;
    private String autorizacionDatosPersonales;
    private String autorizacionEnvioNotificaciones;    
    private String estado;
    private String estadoAuditoria;
    private String mensajeAuditoria;
    private String fechaAuditoria;    
    private Long idAuditoria;
    private String observacionesAuditoria;
    private String glosa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRadicado() {
        return radicado;
    }

    public void setRadicado(String radicado) {
        this.radicado = radicado;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getTipoDocumentoEmpleador() {
        return tipoDocumentoEmpleador;
    }

    public void setTipoDocumentoEmpleador(String tipoDocumentoEmpleador) {
        this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
    }

    public String getNumeroDocumentoEmpleador() {
        return numeroDocumentoEmpleador;
    }

    public void setNumeroDocumentoEmpleador(String numeroDocumentoEmpleador) {
        this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
    }

    public String getSerialSAT() {
        return serialSAT;
    }

    public void setSerialSAT(String serialSAT) {
        this.serialSAT = serialSAT;
    }

    public String getFechaSolicitudDesafiliacion() {
        return fechaSolicitudDesafiliacion;
    }

    public void setFechaSolicitudDesafiliacion(String fechaSolicitudDesafiliacion) {
        this.fechaSolicitudDesafiliacion = fechaSolicitudDesafiliacion;
    }

    public String getFechaEfectividadDesafiliacion() {
        return fechaEfectividadDesafiliacion;
    }

    public void setFechaEfectividadDesafiliacion(String fechaEfectividadDesafiliacion) {
        this.fechaEfectividadDesafiliacion = fechaEfectividadDesafiliacion;
    }

    public String getDepartamentoAfiliacion() {
        return departamentoAfiliacion;
    }

    public void setDepartamentoAfiliacion(String departamentoAfiliacion) {
        this.departamentoAfiliacion = departamentoAfiliacion;
    }

    public String getPazYSalvo() {
        return pazYSalvo;
    }

    public void setPazYSalvo(String pazYSalvo) {
        this.pazYSalvo = pazYSalvo;
    }

    public String getFechaPazYSalvo() {
        return fechaPazYSalvo;
    }

    public void setFechaPazYSalvo(String fechaPazYSalvo) {
        this.fechaPazYSalvo = fechaPazYSalvo;
    }

    public String getAutorizacionDatosPersonales() {
        return autorizacionDatosPersonales;
    }

    public void setAutorizacionDatosPersonales(String autorizacionDatosPersonales) {
        this.autorizacionDatosPersonales = autorizacionDatosPersonales;
    }

    public String getAutorizacionEnvioNotificaciones() {
        return autorizacionEnvioNotificaciones;
    }

    public void setAutorizacionEnvioNotificaciones(String autorizacionEnvioNotificaciones) {
        this.autorizacionEnvioNotificaciones = autorizacionEnvioNotificaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoAuditoria() {
        return estadoAuditoria;
    }

    public void setEstadoAuditoria(String estadoAuditoria) {
        this.estadoAuditoria = estadoAuditoria;
    }

    public String getMensajeAuditoria() {
        return mensajeAuditoria;
    }

    public void setMensajeAuditoria(String mensajeAuditoria) {
        this.mensajeAuditoria = mensajeAuditoria;
    }

    public String getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(String fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public Long getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Long idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public String getObservacionesAuditoria() {
        return observacionesAuditoria;
    }

    public void setObservacionesAuditoria(String observacionesAuditoria) {
        this.observacionesAuditoria = observacionesAuditoria;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public String getGlosa() {
        return glosa;
    }
}
