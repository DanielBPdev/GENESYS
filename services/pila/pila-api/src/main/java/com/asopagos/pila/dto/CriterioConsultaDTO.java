package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los criterios definidos para la consulta de planillas pendientes de
 * gestión manual<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class CriterioConsultaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Número de planilla */
    private Long numeroPlanilla;

    /** Fecha de ingreso de planilla */
    private Date fechaIngreso;
    
    /** Tipo de ID del aportante */
    private TipoIdentificacionEnum tipoIdAportante;

    /** Número de ID del aportante */
    private String numeroIdAportante;

    /** Tipo de ID del cotizante */
    private TipoIdentificacionEnum tipoIdCotizante;

    /** Número de ID del cotizante */
    private String numeroIdCotizante;

    /** Fecha de ingreso de referencia */
    private Date fechaIngresoReferencia;

    /** Fecha de retiro de referencia */
    private Date fechaRetiroReferencia;

    /** Indicador de cotizante dependiente */
    private Boolean esDependiente;
    

    /**
     * @return the numeroPlanilla
     */
    public Long getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla
     *        the numeroPlanilla to set
     */
    public void setNumeroPlanilla(Long numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngresoInMillis
     *        the fechaIngreso to set
     */
    public void setFechaIngreso(Long fechaIngresoInMillis) {
        if(fechaIngresoInMillis != null){
            this.fechaIngreso = new Date(fechaIngresoInMillis);
        }
    }

    /**
     * @return the tipoIdAportante
     */
    public TipoIdentificacionEnum getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante the tipoIdAportante to set
     */
    public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }

    /**
     * @param numeroIdAportante the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    /**
     * @return the tipoIdCotizante
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(TipoIdentificacionEnum tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the numeroIdCotizante
     */
    public String getNumeroIdCotizante() {
        return numeroIdCotizante;
    }

    /**
     * @param numeroIdCotizante the numeroIdCotizante to set
     */
    public void setNumeroIdCotizante(String numeroIdCotizante) {
        this.numeroIdCotizante = numeroIdCotizante;
    }

    /**
     * @return the fechaIngresoReferencia
     */
    public Date getFechaIngresoReferencia() {
        return fechaIngresoReferencia;
    }

    /**
     * @param fechaIngresoReferencia the fechaIngresoReferencia to set
     */
    public void setFechaIngresoReferencia(Date fechaIngresoReferencia) {
        this.fechaIngresoReferencia = fechaIngresoReferencia;
    }

    /**
     * @return the fechaRetiroReferencia
     */
    public Date getFechaRetiroReferencia() {
        return fechaRetiroReferencia;
    }

    /**
     * @param fechaRetiroReferencia the fechaRetiroReferencia to set
     */
    public void setFechaRetiroReferencia(Date fechaRetiroReferencia) {
        this.fechaRetiroReferencia = fechaRetiroReferencia;
    }

    /**
     * @return the esDependiente
     */
    public Boolean getEsDependiente() {
        return esDependiente;
    }

    /**
     * @param esDependiente the esDependiente to set
     */
    public void setEsDependiente(Boolean esDependiente) {
        this.esDependiente = esDependiente;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
