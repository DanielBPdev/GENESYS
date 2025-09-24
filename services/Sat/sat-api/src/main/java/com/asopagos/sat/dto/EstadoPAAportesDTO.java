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
public class EstadoPAAportesDTO {
    private Long id;
    private String radicado;
    private String tipoDocumentoEmpleador;
    private String numeroDocumentoEmpleador;
    private String serialSAT;    
    private String estadoPagoAportes;    
    private String estado;
    private String estadoAuditoria;
    private String mensajeAuditoria;
    private String fechaAuditoria;    
    private Long idAuditoria;
    private String observacionesAuditoria;
    private  String glosa;

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

    public String getEstadoPagoAportes() {
        return estadoPagoAportes;
    }

    public void setEstadoPagoAportes(String estadoPagoAportes) {
        this.estadoPagoAportes = estadoPagoAportes;
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

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }
}
