package com.asopagos.validaciones.fovis.dto;

import java.io.Serializable;

public class ParametrosRegistroCivilDTO implements Serializable{

    private String nombre1;

    private String token;

    private String nombre2;

    private String apellido1;

    private String apellido2;

    private String fechaNac;

    private String genero;

    private String serial;

    private String nuip;

    private String cedula;

    private String tipoBusqueda;

    public ParametrosRegistroCivilDTO(){

    }

    public String getTipoBusqueda() {
        return this.tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre1() {
        return this.nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return this.nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return this.apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return this.apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getFechaNac() {
        return this.fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNuip() {
        return this.nuip;
    }

    public void setNuip(String nuip) {
        this.nuip = nuip;
    }

    public String getCedula() {
        return this.cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    
}
