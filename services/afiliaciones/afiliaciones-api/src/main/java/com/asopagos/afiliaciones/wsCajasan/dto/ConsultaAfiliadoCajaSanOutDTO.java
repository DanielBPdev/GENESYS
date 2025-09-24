package com.asopagos.afiliaciones.wsCajasan.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ConsultaAfiliadoCajaSanOutDTO implements Serializable{
    //Tipo de documento del trabajador
    private String tipoDto;
    //Número de documento del trabajador
    private Long numeroDoc;  
    //Nombres y apellidos del trabajador afiliado
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    //Telefono del trabajador
    private String telefono;
    //Celular del trabajador
    private String celular;
    //Direccion del trabajador
    private String direccion;
    //Edad del trabajador
    private Integer edad;
    //Email del trabajador
    private String email;
    //Fecha de afiliacion a la caja
    private String fechaAfiliacion;
    //Tipo de afiliado del titular
    private String tipoAfiliado;
    //Fecha de ingreso a la empresa
    private String fechaIngreso;
    //Fecha de nacimiento del trabajador
    private String fechaNto;
    //Genero del trabajador (F-M)
    private String genero;
    //Código del país donde reside el trabajador.
    private String codPais;
    //Código del municipio donde reside 
    private String municipio;
    //NIT de la empresa donde labora
    private String nitEmpresa;
    //Nombre de la empresa
    private String razonSocial;
    //Cargo del trabajador
    private String cargo;
    //Profesión del trabajador
    private String profesion;
    //Vinculación de afiliado (E – T – S)
    private String vinculacion;
    //Parentesco del trabajador “TR”
    private String parentesco;
    //A – B – C - D
    private String categoria;
    //Estado civil del trabajador (S – U – D – V – P – C)
    private String estCivil;
    //Factor de vulnerabilidad del trabajador (1-13)
    private String factVulnera;
    //Nivel de escolaridad del trabajador (1-13)
    private String nivelEscol;
    //Orientación sexual del trabajador ( 1-4))
    private String orientSexual;
    //Pertenencia Étnica del trabajador (1-8)
    private String pertEtnica;
    //Tenencia de vivienda del trabajador (1-4)
    private String tienenVivi;
    //Tipo de vivienda del trabajador (1-4)
    private String tipoVivi;

    // Getters y Setters

    public String getTipoDto() {
        return tipoDto;
    }

    public void setTipoDto(String tipoDto) {
        this.tipoDto = tipoDto;
    }

    public Long getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(Long numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(String fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public String getTipoAfiliado() {
        return tipoAfiliado;
    }

    public void setTipoAfiliado(String tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaNto() {
        return fechaNto;
    }

    public void setFechaNto(String fechaNto) {
        this.fechaNto = fechaNto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getVinculacion() {
        return vinculacion;
    }

    public void setVinculacion(String vinculacion) {
        this.vinculacion = vinculacion;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstCivil() {
        return estCivil;
    }

    public void setEstCivil(String estCivil) {
        this.estCivil = estCivil;
    }

    public String getFactVulnera() {
        return factVulnera;
    }

    public void setFactVulnera(String factVulnera) {
        this.factVulnera = factVulnera;
    }

    public String getNivelEscol() {
        return nivelEscol;
    }

    public void setNivelEscol(String nivelEscol) {
        this.nivelEscol = nivelEscol;
    }

    public String getOrientSexual() {
        return orientSexual;
    }

    public void setOrientSexual(String orientSexual) {
        this.orientSexual = orientSexual;
    }

    public String getPertEtnica() {
        return pertEtnica;
    }

    public void setPertEtnica(String pertEtnica) {
        this.pertEtnica = pertEtnica;
    }

    public String getTienenVivi() {
        return tienenVivi;
    }

    public void setTienenVivi(String tienenVivi) {
        this.tienenVivi = tienenVivi;
    }

    public String getTipoVivi() {
        return tipoVivi;
    }

    public void setTipoVivi(String tipoVivi) {
        this.tipoVivi = tipoVivi;
    }


}