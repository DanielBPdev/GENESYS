package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que contiene los criterios de busqueda para las diferentes pantallas<br/>
 * <b>Módulo:</b> Asopagos - HU 389 <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */
public class CriteriosDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Número de la planilla */
    private String numeroPlanilla;
    
    /** Año y mes (aaaa-mm) */
    private String periodo;
    
    /** Tipo de identificacion de la persona */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /** Número de identificacion de la persona */
    private String numeroIdentificacion;
    
    /** Primero nombre de la persona */
    private String primerNombre;
    
    /** Segundo nombre de la persona */
    private String segundoNombre;
    
    /** Primer apellido de la persona */
    private String primerApellido;
    
    /** Segundo apellido de la persona */
    private String segundoApellido;
    
    /** Digito Verificacion de la empresa */
    private Short digitoVerificacion;
    
    /** Fecha en la cual una empresa ingresa a bandeja */
    private Long fechaIngresobandeja;
    
    /** id registro controgol en staging (idindiceplanilla) */
    private Long registroControl;

    /**
     * @return the numeroPlanilla
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla the numeroPlanilla to set
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the digitoVerificacion
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @param digitoVerificacion the digitoVerificacion to set
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * @return the fechaIngresobandeja
     */
    public Long getFechaIngresobandeja() {
        return fechaIngresobandeja;
    }

    /**
     * @param fechaIngresobandeja the fechaIngresobandeja to set
     */
    public void setFechaIngresobandeja(Long fechaIngresobandeja) {
        this.fechaIngresobandeja = fechaIngresobandeja;
    }

    /**
     * @return the registroControl
     */
    public Long getRegistroControl() {
        return registroControl;
    }

    /**
     * @param registroControl the registroControl to set
     */
    public void setRegistroControl(Long registroControl) {
        this.registroControl = registroControl;
    }
}
