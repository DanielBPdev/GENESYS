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
public class InicioRelacionLaboralDTO {
    private Long id;
    private String radicado;
    private String numeroTransaccion;
    private String tipoDocumentoEmpleador;
    private String numeroDocumentoEmpleador;
    private String serialSAT;    
    private String tipoInicio;
    private String fechaInicio;
    private String tipoDocumentoTrabajador;
    private String numeroDocumentoTrabajador;
    private String primerNombreTrabajador;
    private String segundoNombreTrabajador;
    private String primerApellidoTrabajador;
    private String segundoApellidoTrabajador;
    private String sexoTrabajador;
    private String fechaNacimientoTrabajador;    
    private String departamentoCausaSalarios;
    private String municipioCausaSalarios;
    private String direccionMunicipioCausaSalarios;
    private Long telefonoMunicipioCausaSalarios;
    private String correoElectronicoContactoTrabajador;
    private String salario;
    private String tipoSalario;
    private String horasTrabajoMensuales;    
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

    public String getTipoInicio() {
        return tipoInicio;
    }

    public void setTipoInicio(String tipoInicio) {
        this.tipoInicio = tipoInicio;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getTipoDocumentoTrabajador() {
        return tipoDocumentoTrabajador;
    }

    public void setTipoDocumentoTrabajador(String tipoDocumentoTrabajador) {
        this.tipoDocumentoTrabajador = tipoDocumentoTrabajador;
    }

    public String getNumeroDocumentoTrabajador() {
        return numeroDocumentoTrabajador;
    }

    public void setNumeroDocumentoTrabajador(String numeroDocumentoTrabajador) {
        this.numeroDocumentoTrabajador = numeroDocumentoTrabajador;
    }

    public String getPrimerNombreTrabajador() {
        return primerNombreTrabajador;
    }

    public void setPrimerNombreTrabajador(String primerNombreTrabajador) {
        this.primerNombreTrabajador = primerNombreTrabajador;
    }

    public String getSegundoNombreTrabajador() {
        return segundoNombreTrabajador;
    }

    public void setSegundoNombreTrabajador(String segundoNombreTrabajador) {
        this.segundoNombreTrabajador = segundoNombreTrabajador;
    }

    public String getPrimerApellidoTrabajador() {
        return primerApellidoTrabajador;
    }

    public void setPrimerApellidoTrabajador(String primerApellidoTrabajador) {
        this.primerApellidoTrabajador = primerApellidoTrabajador;
    }

    public String getSegundoApellidoTrabajador() {
        return segundoApellidoTrabajador;
    }

    public void setSegundoApellidoTrabajador(String segundoApellidoTrabajador) {
        this.segundoApellidoTrabajador = segundoApellidoTrabajador;
    }

    public String getSexoTrabajador() {
        return sexoTrabajador;
    }

    public void setSexoTrabajador(String sexoTrabajador) {
        this.sexoTrabajador = sexoTrabajador;
    }

    public String getFechaNacimientoTrabajador() {
        return fechaNacimientoTrabajador;
    }

    public void setFechaNacimientoTrabajador(String fechaNacimientoTrabajador) {
        this.fechaNacimientoTrabajador = fechaNacimientoTrabajador;
    }

    public String getDepartamentoCausaSalarios() {
        return departamentoCausaSalarios;
    }

    public void setDepartamentoCausaSalarios(String departamentoCausaSalarios) {
        this.departamentoCausaSalarios = departamentoCausaSalarios;
    }

    public String getMunicipioCausaSalarios() {
        return municipioCausaSalarios;
    }

    public void setMunicipioCausaSalarios(String municipioCausaSalarios) {
        this.municipioCausaSalarios = municipioCausaSalarios;
    }

    public String getDireccionMunicipioCausaSalarios() {
        return direccionMunicipioCausaSalarios;
    }

    public void setDireccionMunicipioCausaSalarios(String direccionMunicipioCausaSalarios) {
        this.direccionMunicipioCausaSalarios = direccionMunicipioCausaSalarios;
    }

    public Long getTelefonoMunicipioCausaSalarios() {
        return telefonoMunicipioCausaSalarios;
    }

    public void setTelefonoMunicipioCausaSalarios(Long telefonoMunicipioCausaSalarios) {
        this.telefonoMunicipioCausaSalarios = telefonoMunicipioCausaSalarios;
    }

    public String getCorreoElectronicoContactoTrabajador() {
        return correoElectronicoContactoTrabajador;
    }

    public void setCorreoElectronicoContactoTrabajador(String correoElectronicoContactoTrabajador) {
        this.correoElectronicoContactoTrabajador = correoElectronicoContactoTrabajador;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getTipoSalario() {
        return tipoSalario;
    }

    public void setTipoSalario(String tipoSalario) {
        this.tipoSalario = tipoSalario;
    }

    public String getHorasTrabajoMensuales() {
        return horasTrabajoMensuales;
    }

    public void setHorasTrabajoMensuales(String horasTrabajoMensuales) {
        this.horasTrabajoMensuales = horasTrabajoMensuales;
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

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }
}
