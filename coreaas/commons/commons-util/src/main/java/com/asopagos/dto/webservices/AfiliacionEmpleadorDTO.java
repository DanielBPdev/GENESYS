package com.asopagos.dto.webservices;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

import javax.validation.constraints.*;

import com.asopagos.enumeraciones.core.*;
import com.asopagos.enumeraciones.personas.*;

public class AfiliacionEmpleadorDTO {
    
    @NotNull(message = "EL campo clasificacion es obligatorio.")
    private ClasificacionEnum clasificacion;
    
    @NotNull(message = "EL campo fechaConstitucion es obligatorio.")
    @Past(message = "La fecha de constitucion no puede ser mayor a la fecha actual.")
    private Date fechaConstitucion;

    @NotNull(message = "EL campo tipoNat es obligatorio.")
    private NaturalezaJuridicaEnum tipoNat;

    @NotNull(message = "EL campo ciiu es obligatorio.")
    private String ciiu;

    @NotNull(message = "El campo numeroTotalTrabajadores es obligatrio.")
    private Integer numeroTotalTrabajadores;
    
    @NotNull(message = "EL campo mpio es obligatorio.")
    private Long mpio;

    @NotNull(message = "EL campo barrio es obligatorio.")
    private String barrio;

    @NotNull(message = "EL campo direccion es obligatorio.")
    private String direccion;

    @NotNull(message = "EL campo celular es obligatorio.")
    private String celular;

    @NotNull(message = "EL campo correo es obligatorio.")
    private String correo;

    @NotNull(message = "EL campo tipoNit es obligatorio.")
    private TipoIdentificacionEnum tipoNit;

    @NotNull(message = "EL campo numeroNit es obligatorio.")
    private String numeroNit;

    @NotNull(message = "EL campo razon es obligatorio.")
    private String razon;

    @NotNull(message = "EL campo tipoRep es obligatorio.")
    private TipoIdentificacionEnum tipoRep;

    @NotNull(message = "EL campo numeroRep es obligatorio.")
    private String numeroRep;

    @NotNull(message = "EL campo priape es obligatorio.")
    private String priape;

    private String segape;

    @NotNull(message = "EL campo prinom es obligatorio.")
    private String prinom;

    private String segnom;

    @NotNull(message = "EL campo celRep es obligatorio.")
    private String celRep;

    @NotNull(message = "EL campo correp es obligatorio.")
    private String correp;

    @NotNull(message = "EL campo tipoAportes es obligatorio.")
    private TipoIdentificacionEnum tipoAportes;

    @NotNull(message = "EL campo numeroAportes es obligatorio.")
    private String numeroAportes;

    @NotNull(message = "EL campo priapeAportes es obligatorio.")
    private String priapeAportes;

    @NotNull(message = "EL campo prinomAportes es obligatorio.")
    private String prinomAportes;

    @NotNull(message = "EL campo celAportes es obligatorio.")
    private String celAportes;

    @NotNull(message = "EL campo corAportes es obligatorio.")
    private String corAportes;

    @NotNull(message = "EL campo tipoSubsidio es obligatorio.")
    private TipoIdentificacionEnum tipoSubsidio;

    @NotNull(message = "EL campo numeroSubsidio es obligatorio.")
    private String numeroSubsidio;

    @NotNull(message = "EL campo priapeSubsidio es obligatorio.")
    private String priapeSubsidio;

    @NotNull(message = "EL campo prinomSubsidio es obligatorio.")
    private String prinomSubsidio;

    @NotNull(message = "EL campo celSubsidio es obligatorio.")
    private String celSubsidio;

    @NotNull(message = "EL campo corSubsidio es obligatorio.")
    private String corSubsidio;

    public AfiliacionEmpleadorDTO() {
    }

    public AfiliacionEmpleadorDTO(ClasificacionEnum clasificacion, Date fechaConstitucion, NaturalezaJuridicaEnum tipoNat, String ciiu, Integer numeroTotalTrabajadores, Long mpio, String barrio, String direccion, String celular, String correo, TipoIdentificacionEnum tipoNit, String numeroNit, String razon, TipoIdentificacionEnum tipoRep, String numeroRep, String priape, String segape, String prinom, String segnom, String celRep, String correp, TipoIdentificacionEnum tipoAportes, String numeroAportes, String priapeAportes, String prinomAportes, String celAportes, String corAportes, TipoIdentificacionEnum tipoSubsidio, String numeroSubsidio, String priapeSubsidio, String prinomSubsidio, String celSubsidio, String corSubsidio) {
        this.clasificacion = clasificacion;
        this.fechaConstitucion = fechaConstitucion;
        this.tipoNat = tipoNat;
        this.ciiu = ciiu;
        this.numeroTotalTrabajadores = numeroTotalTrabajadores;
        this.mpio = mpio;
        this.barrio = barrio;
        this.direccion = direccion;
        this.celular = celular;
        this.correo = correo;
        this.tipoNit = tipoNit;
        this.numeroNit = numeroNit;
        this.razon = razon;
        this.tipoRep = tipoRep;
        this.numeroRep = numeroRep;
        this.priape = priape;
        this.segape = segape;
        this.prinom = prinom;
        this.segnom = segnom;
        this.celRep = celRep;
        this.correp = correp;
        this.tipoAportes = tipoAportes;
        this.numeroAportes = numeroAportes;
        this.priapeAportes = priapeAportes;
        this.prinomAportes = prinomAportes;
        this.celAportes = celAportes;
        this.corAportes = corAportes;
        this.tipoSubsidio = tipoSubsidio;
        this.numeroSubsidio = numeroSubsidio;
        this.priapeSubsidio = priapeSubsidio;
        this.prinomSubsidio = prinomSubsidio;
        this.celSubsidio = celSubsidio;
        this.corSubsidio = corSubsidio;
    }

    public ClasificacionEnum getClasificacion() {
        return this.clasificacion;
    }

    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Date getFechaConstitucion() {
        return this.fechaConstitucion;
    }

    public void setFechaConstitucion(Date fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    public NaturalezaJuridicaEnum getTipoNat() {
        return this.tipoNat;
    }

    public void setTipoNat(NaturalezaJuridicaEnum tipoNat) {
        this.tipoNat = tipoNat;
    }

    public String getCiiu() {
        return this.ciiu;
    }

    public void setCiiu(String ciiu) {
        this.ciiu = ciiu;
    }

    public Integer getNumeroTotalTrabajadores() {
        return this.numeroTotalTrabajadores;
    }

    public void setNumeroTotalTrabajadores(Integer numeroTotalTrabajadores) {
        this.numeroTotalTrabajadores = numeroTotalTrabajadores;
    }

    public Long getMpio() {
        return this.mpio;
    }

    public void setMpio(Long mpio) {
        this.mpio = mpio;
    }

    public String getBarrio() {
        return this.barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public TipoIdentificacionEnum getTipoNit() {
        return this.tipoNit;
    }

    public void setTipoNit(TipoIdentificacionEnum tipoNit) {
        this.tipoNit = tipoNit;
    }

    public String getNumeroNit() {
        return this.numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getRazon() {
        return this.razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public TipoIdentificacionEnum getTipoRep() {
        return this.tipoRep;
    }

    public void setTipoRep(TipoIdentificacionEnum tipoRep) {
        this.tipoRep = tipoRep;
    }

    public String getNumeroRep() {
        return this.numeroRep;
    }

    public void setNumeroRep(String numeroRep) {
        this.numeroRep = numeroRep;
    }

    public String getPriape() {
        return this.priape;
    }

    public void setPriape(String priape) {
        this.priape = priape;
    }

    public String getSegape() {
        return this.segape;
    }

    public void setSegape(String segape) {
        this.segape = segape;
    }

    public String getPrinom() {
        return this.prinom;
    }

    public void setPrinom(String prinom) {
        this.prinom = prinom;
    }

    public String getSegnom() {
        return this.segnom;
    }

    public void setSegnom(String segnom) {
        this.segnom = segnom;
    }

    public String getCelRep() {
        return this.celRep;
    }

    public void setCelRep(String celRep) {
        this.celRep = celRep;
    }

    public String getCorrep() {
        return this.correp;
    }

    public void setCorrep(String correp) {
        this.correp = correp;
    }

    public TipoIdentificacionEnum getTipoAportes() {
        return this.tipoAportes;
    }

    public void setTipoAportes(TipoIdentificacionEnum tipoAportes) {
        this.tipoAportes = tipoAportes;
    }

    public String getNumeroAportes() {
        return this.numeroAportes;
    }

    public void setNumeroAportes(String numeroAportes) {
        this.numeroAportes = numeroAportes;
    }

    public String getPriapeAportes() {
        return this.priapeAportes;
    }

    public void setPriapeAportes(String priapeAportes) {
        this.priapeAportes = priapeAportes;
    }

    public String getPrinomAportes() {
        return this.prinomAportes;
    }

    public void setPrinomAportes(String prinomAportes) {
        this.prinomAportes = prinomAportes;
    }

    public String getCelAportes() {
        return this.celAportes;
    }

    public void setCelAportes(String celAportes) {
        this.celAportes = celAportes;
    }

    public String getCorAportes() {
        return this.corAportes;
    }

    public void setCorAportes(String corAportes) {
        this.corAportes = corAportes;
    }

    public TipoIdentificacionEnum getTipoSubsidio() {
        return this.tipoSubsidio;
    }

    public void setTipoSubsidio(TipoIdentificacionEnum tipoSubsidio) {
        this.tipoSubsidio = tipoSubsidio;
    }

    public String getNumeroSubsidio() {
        return this.numeroSubsidio;
    }

    public void setNumeroSubsidio(String numeroSubsidio) {
        this.numeroSubsidio = numeroSubsidio;
    }

    public String getPriapeSubsidio() {
        return this.priapeSubsidio;
    }

    public void setPriapeSubsidio(String priapeSubsidio) {
        this.priapeSubsidio = priapeSubsidio;
    }

    public String getPrinomSubsidio() {
        return this.prinomSubsidio;
    }

    public void setPrinomSubsidio(String prinomSubsidio) {
        this.prinomSubsidio = prinomSubsidio;
    }

    public String getCelSubsidio() {
        return this.celSubsidio;
    }

    public void setCelSubsidio(String celSubsidio) {
        this.celSubsidio = celSubsidio;
    }

    public String getCorSubsidio() {
        return this.corSubsidio;
    }

    public void setCorSubsidio(String corSubsidio) {
        this.corSubsidio = corSubsidio;
    }

    public AfiliacionEmpleadorDTO clasificacion(ClasificacionEnum clasificacion) {
        setClasificacion(clasificacion);
        return this;
    }

    public AfiliacionEmpleadorDTO fechaConstitucion(Date fechaConstitucion) {
        setFechaConstitucion(fechaConstitucion);
        return this;
    }

    public AfiliacionEmpleadorDTO tipoNat(NaturalezaJuridicaEnum tipoNat) {
        setTipoNat(tipoNat);
        return this;
    }

    public AfiliacionEmpleadorDTO ciiu(String ciiu) {
        setCiiu(ciiu);
        return this;
    }

    public AfiliacionEmpleadorDTO numeroTotalTrabajadores(Integer numeroTotalTrabajadores) {
        setNumeroTotalTrabajadores(numeroTotalTrabajadores);
        return this;
    }

    public AfiliacionEmpleadorDTO mpio(Long mpio) {
        setMpio(mpio);
        return this;
    }

    public AfiliacionEmpleadorDTO barrio(String barrio) {
        setBarrio(barrio);
        return this;
    }

    public AfiliacionEmpleadorDTO direccion(String direccion) {
        setDireccion(direccion);
        return this;
    }

    public AfiliacionEmpleadorDTO celular(String celular) {
        setCelular(celular);
        return this;
    }

    public AfiliacionEmpleadorDTO correo(String correo) {
        setCorreo(correo);
        return this;
    }

    public AfiliacionEmpleadorDTO tipoNit(TipoIdentificacionEnum tipoNit) {
        setTipoNit(tipoNit);
        return this;
    }

    public AfiliacionEmpleadorDTO numeroNit(String numeroNit) {
        setNumeroNit(numeroNit);
        return this;
    }

    public AfiliacionEmpleadorDTO razon(String razon) {
        setRazon(razon);
        return this;
    }

    public AfiliacionEmpleadorDTO tipoRep(TipoIdentificacionEnum tipoRep) {
        setTipoRep(tipoRep);
        return this;
    }

    public AfiliacionEmpleadorDTO numeroRep(String numeroRep) {
        setNumeroRep(numeroRep);
        return this;
    }

    public AfiliacionEmpleadorDTO priape(String priape) {
        setPriape(priape);
        return this;
    }

    public AfiliacionEmpleadorDTO segape(String segape) {
        setSegape(segape);
        return this;
    }

    public AfiliacionEmpleadorDTO prinom(String prinom) {
        setPrinom(prinom);
        return this;
    }

    public AfiliacionEmpleadorDTO segnom(String segnom) {
        setSegnom(segnom);
        return this;
    }

    public AfiliacionEmpleadorDTO celRep(String celRep) {
        setCelRep(celRep);
        return this;
    }

    public AfiliacionEmpleadorDTO correp(String correp) {
        setCorrep(correp);
        return this;
    }

    public AfiliacionEmpleadorDTO tipoAportes(TipoIdentificacionEnum tipoAportes) {
        setTipoAportes(tipoAportes);
        return this;
    }

    public AfiliacionEmpleadorDTO numeroAportes(String numeroAportes) {
        setNumeroAportes(numeroAportes);
        return this;
    }

    public AfiliacionEmpleadorDTO priapeAportes(String priapeAportes) {
        setPriapeAportes(priapeAportes);
        return this;
    }

    public AfiliacionEmpleadorDTO prinomAportes(String prinomAportes) {
        setPrinomAportes(prinomAportes);
        return this;
    }

    public AfiliacionEmpleadorDTO celAportes(String celAportes) {
        setCelAportes(celAportes);
        return this;
    }

    public AfiliacionEmpleadorDTO corAportes(String corAportes) {
        setCorAportes(corAportes);
        return this;
    }

    public AfiliacionEmpleadorDTO tipoSubsidio(TipoIdentificacionEnum tipoSubsidio) {
        setTipoSubsidio(tipoSubsidio);
        return this;
    }

    public AfiliacionEmpleadorDTO numeroSubsidio(String numeroSubsidio) {
        setNumeroSubsidio(numeroSubsidio);
        return this;
    }

    public AfiliacionEmpleadorDTO priapeSubsidio(String priapeSubsidio) {
        setPriapeSubsidio(priapeSubsidio);
        return this;
    }

    public AfiliacionEmpleadorDTO prinomSubsidio(String prinomSubsidio) {
        setPrinomSubsidio(prinomSubsidio);
        return this;
    }

    public AfiliacionEmpleadorDTO celSubsidio(String celSubsidio) {
        setCelSubsidio(celSubsidio);
        return this;
    }

    public AfiliacionEmpleadorDTO corSubsidio(String corSubsidio) {
        setCorSubsidio(corSubsidio);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " clasificacion='" + getClasificacion() + "'" +
            ", fechaConstitucion='" + getFechaConstitucion() + "'" +
            ", tipoNat='" + getTipoNat() + "'" +
            ", ciiu='" + getCiiu() + "'" +
            ", numeroTotalTrabajadores='" + getNumeroTotalTrabajadores() + "'" +
            ", mpio='" + getMpio() + "'" +
            ", barrio='" + getBarrio() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", celular='" + getCelular() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", tipoNit='" + getTipoNit() + "'" +
            ", numeroNit='" + getNumeroNit() + "'" +
            ", razon='" + getRazon() + "'" +
            ", tipoRep='" + getTipoRep() + "'" +
            ", numeroRep='" + getNumeroRep() + "'" +
            ", priape='" + getPriape() + "'" +
            ", segape='" + getSegape() + "'" +
            ", prinom='" + getPrinom() + "'" +
            ", segnom='" + getSegnom() + "'" +
            ", celRep='" + getCelRep() + "'" +
            ", correp='" + getCorrep() + "'" +
            ", tipoAportes='" + getTipoAportes() + "'" +
            ", numeroAportes='" + getNumeroAportes() + "'" +
            ", priapeAportes='" + getPriapeAportes() + "'" +
            ", prinomAportes='" + getPrinomAportes() + "'" +
            ", celAportes='" + getCelAportes() + "'" +
            ", corAportes='" + getCorAportes() + "'" +
            ", tipoSubsidio='" + getTipoSubsidio() + "'" +
            ", numeroSubsidio='" + getNumeroSubsidio() + "'" +
            ", priapeSubsidio='" + getPriapeSubsidio() + "'" +
            ", prinomSubsidio='" + getPrinomSubsidio() + "'" +
            ", celSubsidio='" + getCelSubsidio() + "'" +
            ", corSubsidio='" + getCorSubsidio() + "'" +
            "}";
    }
}
