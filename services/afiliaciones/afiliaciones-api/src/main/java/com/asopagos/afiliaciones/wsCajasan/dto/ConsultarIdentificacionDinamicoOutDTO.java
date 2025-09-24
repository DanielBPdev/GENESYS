package com.asopagos.afiliaciones.wsCajasan.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ConsultarIdentificacionDinamicoOutDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String cargo;
    private String categoria;
    private String celular;
    private String codPais;
    private String direccion;
    private Long edad;
    private String email;
    private String estCivil;
    private Long factVulnera;
    private Date fecIngreso;
    private Date fechaAfiliacion;
    private Date fechaNto;
    private String genero;
    private Long internoEmpresa;
    private String municipio;
    private String nitEmpresa;
    private String nivEscol;
    private String numeroDcto;
    private String numeroDctoPrincipal;
    private String oriSexual;
    private String parentesco;
    private String pertEtnica;
    private String primerApellido;
    private String primerNombre;
    private String profesion;
    private String razonSocial;
    private String segundoApellido;
    private String segundoNombre;
    private String telefono;
    private Long tenenVivi;
    private String tipVivi;
    private String tipoAfiliado;
    private String tipoDto;
    private String viculacion;
// Constructor vacío
public ConsultarIdentificacionDinamicoOutDTO() {
}

// Constructor completo
public ConsultarIdentificacionDinamicoOutDTO(String cargo, String categoria, String celular, String codPais,
        String direccion, Long edad, String email, String estCivil, Long factVulnera, Date fecIngreso,
        Date fechaAfiliacion, Date fechaNto, String genero, Long internoEmpresa, String municipio,
        String nitEmpresa, String nivEscol, String numeroDcto, String numeroDctoPrincipal, String oriSexual,
        String parentesco, String pertEtnica, String primerApellido, String primerNombre, String profesion,
        String razonSocial, String segundoApellido, String segundoNombre, String telefono, Long tenenVivi,
        String tipVivi, String tipoAfiliado, String tipoDto, String viculacion) {
    
    this.cargo = cargo;
    this.categoria = categoria;
    this.celular = celular;
    this.codPais = codPais;
    this.direccion = direccion;
    this.edad = edad;
    this.email = email;
    this.estCivil = estCivil;
    this.factVulnera = factVulnera;
    this.fecIngreso = fecIngreso;
    this.fechaAfiliacion = fechaAfiliacion;
    this.fechaNto = fechaNto;
    this.genero = genero;
    this.internoEmpresa = internoEmpresa;
    this.municipio = municipio;
    this.nitEmpresa = nitEmpresa;
    this.nivEscol = nivEscol;
    this.numeroDcto = numeroDcto;
    this.numeroDctoPrincipal = numeroDctoPrincipal;
    this.oriSexual = oriSexual;
    this.parentesco = parentesco;
    this.pertEtnica = pertEtnica;
    this.primerApellido = primerApellido;
    this.primerNombre = primerNombre;
    this.profesion = profesion;
    this.razonSocial = razonSocial;
    this.segundoApellido = segundoApellido;
    this.segundoNombre = segundoNombre;
    this.telefono = telefono;
    this.tenenVivi = tenenVivi;
    this.tipVivi = tipVivi;
    this.tipoAfiliado = tipoAfiliado;
    this.tipoDto = tipoDto;
    this.viculacion = viculacion;
}
    // Getters y Setters

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getEdad() {
        return edad;
    }

    public void setEdad(Long edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstCivil() {
        return estCivil;
    }

    public void setEstCivil(String estCivil) {
        this.estCivil = estCivil;
    }

    public Long getFactVulnera() {
        return factVulnera;
    }

    public void setFactVulnera(Long factVulnera) {
        this.factVulnera = factVulnera;
    }

    public Date getFecIngreso() {
        return fecIngreso;
    }

    public void setFecIngreso(Date fecIngreso) {
        this.fecIngreso = fecIngreso;
    }

    public Date getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(Date fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public Date getFechaNto() {
        return fechaNto;
    }

    public void setFechaNto(Date fechaNto) {
        this.fechaNto = fechaNto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Long getInternoEmpresa() {
        return internoEmpresa;
    }

    public void setInternoEmpresa(Long internoEmpresa) {
        this.internoEmpresa = internoEmpresa;
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

    public String getNivEscol() {
        return nivEscol;
    }

    public void setNivEscol(String nivEscol) {
        this.nivEscol = nivEscol;
    }

    public String getNumeroDcto() {
        return numeroDcto;
    }

    public void setNumeroDcto(String numeroDcto) {
        this.numeroDcto = numeroDcto;
    }

    public String getNumeroDctoPrincipal() {
        return numeroDctoPrincipal;
    }

    public void setNumeroDctoPrincipal(String numeroDctoPrincipal) {
        this.numeroDctoPrincipal = numeroDctoPrincipal;
    }

    public String getOriSexual() {
        return oriSexual;
    }

    public void setOriSexual(String oriSexual) {
        this.oriSexual = oriSexual;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getPertEtnica() {
        return pertEtnica;
    }

    public void setPertEtnica(String pertEtnica) {
        this.pertEtnica = pertEtnica;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getTenenVivi() {
        return tenenVivi;
    }

    public void setTenenVivi(Long tenenVivi) {
        this.tenenVivi = tenenVivi;
    }

    public String getTipVivi() {
        return tipVivi;
    }

    public void setTipVivi(String tipVivi) {
        this.tipVivi = tipVivi;
    }

    public String getTipoAfiliado() {
        return tipoAfiliado;
    }

    public void setTipoAfiliado(String tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    public String getTipoDto() {
        return tipoDto;
    }

    public void setTipoDto(String tipoDto) {
        this.tipoDto = tipoDto;
    }

    public String getViculacion() {
        return viculacion;
    }

    public void setViculacion(String viculacion) {
        this.viculacion = viculacion;
    }
}
