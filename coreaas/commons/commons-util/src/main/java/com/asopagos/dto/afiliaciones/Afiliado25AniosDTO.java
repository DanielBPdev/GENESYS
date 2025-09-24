package com.asopagos.dto.afiliaciones;

import java.io.Serializable;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * <b>Descripcion:</b> DTO que contiene los datos necesarios para llevar a cabo
 * una
 * afiliacion masiva de benficiarios <br/>
 * <b>Módulo:</b> Asopagos <br/>
 *
 * @author Diego Nicolas Gonzalez
 */

public class Afiliado25AniosDTO implements Serializable {
    private static final long serialVersionUID = 3020065212560358257L;

    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String fechaNacimiento;
    private String fechaRecepcionDocumentos;
    private String departamento;
    private String municipio;
    private String direccion;
    private String genero;
    private String estadoCivil;
    private Long telefono;
    private Long celular;
    private String correoElectronico;
    private String pagadorPension;
    private Long valorMesadaPensional;
    private String archivo;
    private Long noLinea;



    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getPrimerNombre() {
        return this.primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return this.segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaRecepcionDocumentos() {
        return this.fechaRecepcionDocumentos;
    }

    public void setFechaRecepcionDocumentos(String fechaRecepcionDocumentos) {
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
    }

    public String getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Long getTelefono() {
        return this.telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public Long getCelular() {
        return this.celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPagadorPension() {
        return this.pagadorPension;
    }

    public void setPagadorPension(String pagadorPension) {
        this.pagadorPension = pagadorPension;
    }

    public Long getValorMesadaPensional() {
        return this.valorMesadaPensional;
    }

    public void setValorMesadaPensional(Long valorMesadaPensional) {
        this.valorMesadaPensional = valorMesadaPensional;
    }

    public String getArchivo() {
        return this.archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }


    public Long getNoLinea() {
        return this.noLinea;
    }

    public void setNoLinea(Long noLinea) {
        this.noLinea = noLinea;
    }


    @JsonIgnore
    public String getNombreCompleto(){
        return this.getPrimerNombre() + this.getSegundoNombre() + this.getPrimerApellido() + this.getSegundoApellido();
    }



    @Override
    public String toString() {
        return "{" +
            " tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", fechaRecepcionDocumentos='" + getFechaRecepcionDocumentos() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", genero='" + getGenero() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", celular='" + getCelular() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", pagadorPension='" + getPagadorPension() + "'" +
            ", valorMesadaPensional='" + getValorMesadaPensional() + "'" +
            "}";
    }

}
