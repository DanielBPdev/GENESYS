/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.dto;

/**
 *
 * @author Maria Cuellar
 */
public class BusquedaSAT {
    private String estado;
    private String numeroDocumentoEmpleador;
    private String fechaInicio;
    private String fechaFin;
    private String numeroDocumentoTrabajador;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumeroDocumentoEmpleador() {
        return numeroDocumentoEmpleador;
    }

    public void setNumeroDocumentoEmpleador(String numeroDocumentoEmpleador) {
        this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNumeroDocumentoTrabajador() {
        return numeroDocumentoTrabajador;
    }

    public void setNumeroDocumentoTrabajador(String numeroDocumentoTrabajador) {
        this.numeroDocumentoTrabajador = numeroDocumentoTrabajador;
    }
   
}
