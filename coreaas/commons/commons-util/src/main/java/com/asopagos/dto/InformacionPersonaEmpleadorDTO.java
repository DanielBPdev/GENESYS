package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <b>Descripción:</b> DTO que transporta los datos básicos de una persona *
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformacionPersonaEmpleadorDTO implements Serializable {

    private TipoIdentificacionEnum tipoIdentificacionEmpleador;

    private String numeroIdentificacionEmpleador;
    
    private Short digitoVerificacion;

    private String nombreEmpleador;

    private String sucursalEmpleador;

    private String nombreSucursalEmpleador;

    private String fechaIngresoEmpresa;

    private BigDecimal salario;

    private BigDecimal porcentajeAporte;

    private String cargo;

    private String fechaRetiro;

    private String motivoDesafiliacion;

    private String ultimoPeriodoPagoAportes;

    public InformacionPersonaEmpleadorDTO() {
    }

    public InformacionPersonaEmpleadorDTO(TipoIdentificacionEnum tipoIdentificacionEmpleador, String numeroIdentificacionEmpleador, Short digitoVerificacion, String nombreEmpleador, String sucursalEmpleador, String nombreSucursalEmpleador, String fechaIngresoEmpresa, BigDecimal salario, BigDecimal porcentajeAporte, String cargo, String fechaRetiro, String motivoDesafiliacion, String ultimoPeriodoPagoAportes) {
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
        this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
        this.digitoVerificacion = digitoVerificacion;
        this.nombreEmpleador = nombreEmpleador;
        this.sucursalEmpleador = sucursalEmpleador;
        this.nombreSucursalEmpleador = nombreSucursalEmpleador;
        this.fechaIngresoEmpresa = fechaIngresoEmpresa;
        this.salario = salario;
        this.porcentajeAporte = porcentajeAporte;
        this.cargo = cargo;
        this.fechaRetiro = fechaRetiro;
        this.motivoDesafiliacion = motivoDesafiliacion;
        this.ultimoPeriodoPagoAportes = ultimoPeriodoPagoAportes;
    }

    public TipoIdentificacionEnum getTipoIdentificacionEmpleador() {
        return this.tipoIdentificacionEmpleador;
    }

    public void setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
    }

    public String getNumeroIdentificacionEmpleador() {
        return this.numeroIdentificacionEmpleador;
    }

    public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
        this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
    }

    public Short getDigitoVerificacion() {
        return this.digitoVerificacion;
    }

    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    public String getNombreEmpleador() {
        return this.nombreEmpleador;
    }

    public void setNombreEmpleador(String nombreEmpleador) {
        this.nombreEmpleador = nombreEmpleador;
    }

    public String getSucursalEmpleador() {
        return this.sucursalEmpleador;
    }

    public void setSucursalEmpleador(String sucursalEmpleador) {
        this.sucursalEmpleador = sucursalEmpleador;
    }

    public String getNombreSucursalEmpleador() {
        return this.nombreSucursalEmpleador;
    }

    public void setNombreSucursalEmpleador(String nombreSucursalEmpleador) {
        this.nombreSucursalEmpleador = nombreSucursalEmpleador;
    }

    public String getFechaIngresoEmpresa() {
        return this.fechaIngresoEmpresa;
    }

    public void setFechaIngresoEmpresa(String fechaIngresoEmpresa) {
        this.fechaIngresoEmpresa = fechaIngresoEmpresa;
    }

    public BigDecimal getSalario() {
        return this.salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getPorcentajeAporte() {
        return this.porcentajeAporte;
    }

    public void setPorcentajeAporte(BigDecimal porcentajeAporte) {
        this.porcentajeAporte = porcentajeAporte;
    }

    public String getCargo() {
        return this.cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFechaRetiro() {
        return this.fechaRetiro;
    }

    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getMotivoDesafiliacion() {
        return this.motivoDesafiliacion;
    }

    public void setMotivoDesafiliacion(String motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    public String getUltimoPeriodoPagoAportes() {
        return this.ultimoPeriodoPagoAportes;
    }

    public void setUltimoPeriodoPagoAportes(String ultimoPeriodoPagoAportes) {
        this.ultimoPeriodoPagoAportes = ultimoPeriodoPagoAportes;
    }

    @Override
    public String toString() {
        return "{" +
            " tipoIdentificacionEmpleador='" + getTipoIdentificacionEmpleador() + "'" +
            ", numeroIdentificacionEmpleador='" + getNumeroIdentificacionEmpleador() + "'" +
            ", digitoVerificacion='" + getDigitoVerificacion() + "'" +
            ", nombreEmpleador='" + getNombreEmpleador() + "'" +
            ", sucursalEmpleador='" + getSucursalEmpleador() + "'" +
            ", nombreSucursalEmpleador='" + getNombreSucursalEmpleador() + "'" +
            ", fechaIngresoEmpresa='" + getFechaIngresoEmpresa() + "'" +
            ", salario='" + getSalario() + "'" +
            ", porcentajeAporte='" + getPorcentajeAporte() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", fechaRetiro='" + getFechaRetiro() + "'" +
            ", motivoDesafiliacion='" + getMotivoDesafiliacion() + "'" +
            ", ultimoPeriodoPagoAportes='" + getUltimoPeriodoPagoAportes() + "'" +
            "}";
    }

}
