package com.asopagos.pila.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;

/**
 * <b>Descripcion:</b> DTO que mapea los campos de busqueda de archivos procesados finalizados <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda </a>
 */
public class CriteriosBusquedaArchivosProcesados implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Tipo de identificacion de la persona */
    private String tipoIdentificacion;
    
    /** Numero de identificacion de la persona */
    private String numeroIdentificacion;
    
    /** Digito de verificacion de la persona */
    private Short digitoVerificacion;
    
    /** Fecha inicial de carga del archivo */
    private Long fechaInicio;
    
    /** Fecha final de carga del archivo */
    private Long fechaFin;
    
    /** Tipo de operador de informacion */
    private TipoOperadorEnum tipoOperador;
    
    /** Numero de planilla */
    private Long numeroPlanilla;
    
    /** Codigo del Banco */
    private String idBanco;

    //** Tipo de busqueda */
    private String tipoBusqueda;
    
    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
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
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the tipoOperador
     */
    public TipoOperadorEnum getTipoOperador() {
        return tipoOperador;
    }

    /**
     * @param tipoOperador the tipoOperador to set
     */
    public void setTipoOperador(TipoOperadorEnum tipoOperador) {
        this.tipoOperador = tipoOperador;
    }

    /**
     * @return the numeroPlanilla
     */
    public Long getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla the numeroPlanilla to set
     */
    public void setNumeroPlanilla(Long numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the idBanco
     */
    public String getIdBanco() {
        return idBanco;
    }

    /**
     * @param idBanco2 the idBanco to set
     */
    public void setIdBanco(String idBanco2) {
        this.idBanco = idBanco2;
    }


    public String getTipoBusqueda() {
        return this.tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

}
