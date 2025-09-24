package com.asopagos.personas.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO que contiene los datos de una persona para el proceso offcore

 */
@XmlRootElement
public class ConsultarPersonaProcesoOffcoreDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idTipoDcto;
    private String numIdentificacion;
    private String primerApellido;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;
    private String fechaNacimiento;
    private String idSexo;
    private String idEstadoCivil;
    private String direccion;
    private String codDepartamentoResidencia;
    private String codCiudadResidencia;
    private String telefono;
    private String correo;
    private String celular;

    public ConsultarPersonaProcesoOffcoreDTO() {
    }

    public String getIdTipoDcto() {
        return this.idTipoDcto;
    }

    public void setIdTipoDcto(String idTipoDcto) {
        this.idTipoDcto = idTipoDcto;
    }

    public String getNumIdentificacion() {
        return this.numIdentificacion;
    }

    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
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

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIdSexo() {
        return this.idSexo;
    }

    public void setIdSexo(String idSexo) {
        this.idSexo = idSexo;
    }

    public String getIdEstadoCivil() {
        return this.idEstadoCivil;
    }

    public void setIdEstadoCivil(String idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodDepartamentoResidencia() {
        return this.codDepartamentoResidencia;
    }

    public void setCodDepartamentoResidencia(String codDepartamentoResidencia) {
        this.codDepartamentoResidencia = codDepartamentoResidencia;
    }

    public String getCodCiudadResidencia() {
        return this.codCiudadResidencia;
    }

    public void setCodCiudadResidencia(String codCiudadResidencia) {
        this.codCiudadResidencia = codCiudadResidencia;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

}
