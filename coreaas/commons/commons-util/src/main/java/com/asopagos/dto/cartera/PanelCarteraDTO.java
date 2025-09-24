package com.asopagos.dto.cartera;

import java.math.BigDecimal;
import java.lang.String;
import java.lang.Long;


public class PanelCarteraDTO {

    private Long numeroRadicado;
    private String accionCobro;
    private String lineaCobro;
    private String tipoDeuda;
    private BigDecimal montoDeuda;
    private String estadoCartera;
    private String tipoSolicitante;
    private String numeroIdentificacion;
    private String tipoIdentificacion;
    private String nombreCompleto;
    private String deuda;
    private String fechaIngreso;
    private Long idCartera;

    public PanelCarteraDTO(Long numeroRadicado, String accionCobro, String lineaCobro, String tipoDeuda, BigDecimal montoDeuda, String estadoCartera, String tipoSolicitante, String numeroIdentificacion, String tipoIdentificacion, String nombreCompleto, String deuda, String fechaIngreso, Long idCartera) {
        this.numeroRadicado = numeroRadicado;
        this.accionCobro = accionCobro;
        this.lineaCobro = lineaCobro;
        this.tipoDeuda = tipoDeuda;
        this.montoDeuda = montoDeuda;
        this.estadoCartera = estadoCartera;
        this.tipoSolicitante = tipoSolicitante;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.nombreCompleto = nombreCompleto;
        this.deuda = deuda;
        this.fechaIngreso = fechaIngreso;
        this.idCartera = idCartera;
    }

    public Long getNumeroRadicado() {
        return this.numeroRadicado;
    }

    public void setNumeroRadicado(Long numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public String getAccionCobro() {
        return this.accionCobro;
    }

    public void setAccionCobro(String accionCobro) {
        this.accionCobro = accionCobro;
    }

    public String getLineaCobro() {
        return this.lineaCobro;
    }

    public void setLineaCobro(String lineaCobro) {
        this.lineaCobro = lineaCobro;
    }

    public String getTipoDeuda() {
        return this.tipoDeuda;
    }

    public void setTipoDeuda(String tipoDeuda) {
        this.tipoDeuda = tipoDeuda;
    }

    public BigDecimal getMontoDeuda() {
        return this.montoDeuda;
    }

    public void setMontoDeuda(BigDecimal montoDeuda) {
        this.montoDeuda = montoDeuda;
    }

    public String getEstadoCartera() {
        return this.estadoCartera;
    }

    public void setEstadoCartera(String estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    public String getTipoSolicitante() {
        return this.tipoSolicitante;
    }

    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDeuda() {
        return this.deuda;
    }

    public void setDeuda(String deuda) {
        this.deuda = deuda;
    }

    public String getFechaIngreso() {
        return this.fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Long getIdCartera() {
        return this.idCartera;
    }

    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    

    
}