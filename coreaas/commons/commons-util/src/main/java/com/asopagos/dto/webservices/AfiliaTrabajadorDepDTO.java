package com.asopagos.dto.webservices;

import java.io.Serializable;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import java.math.BigDecimal;
import java.util.Date;

public class AfiliaTrabajadorDepDTO extends AfiliaTrabajadorDTO implements Serializable,Cloneable  {

    @NotNull(message = "El campo tipoIdentificacionEmpleador es obligatorio.")
    private TipoIdentificacionEnum tipoIdentificacionEmpleador;

    @NotNull(message = "El campo nit es obligatorio.")
    private String nit;

    @NotNull(message = "El campo razonSocialEmpleador es obligatorio.")
    private String razonSocialEmpleador;

    @NotNull(message = "El campo tipoSolicitante es obligatorio.")
    private TipoTipoSolicitanteEnum tipoSolicitante; 

    @NotNull(message = "El campo fechaIngreso es obligatorio.")
    private Date fechaIngreso;

    @NotNull(message = "El campo sueldo es obligatorio.")
    private BigDecimal sueldo;

    public AfiliaTrabajadorDepDTO() {
        super();
    }

    public AfiliaTrabajadorDepDTO(TipoIdentificacionEnum tipoIdentificacionEmpleador, String nit, String razonSocialEmpleador, TipoTipoSolicitanteEnum tipoSolicitante, Date fechaIngreso, BigDecimal sueldo) {
        super();
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
        this.nit = nit;
        this.razonSocialEmpleador = razonSocialEmpleador;
        this.tipoSolicitante = tipoSolicitante;
        this.fechaIngreso = fechaIngreso;
        this.sueldo = sueldo;
    }

    public TipoIdentificacionEnum getTipoIdentificacionEmpleador() {
        return this.tipoIdentificacionEmpleador;
    }

    public void setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
    }

    public String getNit() {
        return this.nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocialEmpleador() {
        return this.razonSocialEmpleador;
    }

    public void setRazonSocialEmpleador(String razonSocialEmpleador) {
        this.razonSocialEmpleador = razonSocialEmpleador;
    }

    public TipoTipoSolicitanteEnum getTipoSolicitante() {
        return this.tipoSolicitante;
    }

    public void setTipoSolicitante(TipoTipoSolicitanteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public BigDecimal getSueldo() {
        return this.sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public AfiliaTrabajadorDepDTO tipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
        setTipoIdentificacionEmpleador(tipoIdentificacionEmpleador);
        return this;
    }

    public AfiliaTrabajadorDepDTO nit(String nit) {
        setNit(nit);
        return this;
    }

    public AfiliaTrabajadorDepDTO razonSocialEmpleador(String razonSocialEmpleador) {
        setRazonSocialEmpleador(razonSocialEmpleador);
        return this;
    }

    public AfiliaTrabajadorDepDTO tipoSolicitante(TipoTipoSolicitanteEnum tipoSolicitante) {
        setTipoSolicitante(tipoSolicitante);
        return this;
    }

    public AfiliaTrabajadorDepDTO fechaIngreso(Date fechaIngreso) {
        setFechaIngreso(fechaIngreso);
        return this;
    }

    public AfiliaTrabajadorDepDTO sueldo(BigDecimal sueldo) {
        setSueldo(sueldo);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " tipoIdentificacionEmpleador='" + getTipoIdentificacionEmpleador() + "'" +
            ", nit='" + getNit() + "'" +
            ", razonSocialEmpleador='" + getRazonSocialEmpleador() + "'" +
            ", tipoSolicitante='" + getTipoSolicitante() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", sueldo='" + getSueldo() + "'" +
            "}";
    }

}
