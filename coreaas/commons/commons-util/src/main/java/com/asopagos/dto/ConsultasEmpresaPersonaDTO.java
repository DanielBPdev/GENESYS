/**
 * 
 */
package com.asopagos.dto;

import java.util.Date;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos de una empresa que contiene datos de cotizantes <br/>
 * <b>Módulo:</b> Asopagos - HU 389 <br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 */
public class ConsultasEmpresaPersonaDTO {
    private String tipoIdentificacion;
    private Long numIdentificacion;
    private String digitoVerificacionNit;
    private String tipificacion;
    private Date fechaInicio;
    private Date fechaFin;

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numIdentificacion
     */
    public Long getNumIdentificacion() {
        return numIdentificacion;
    }

    /**
     * @param numIdentificacion
     *        the numIdentificacion to set
     */
    public void setNumIdentificacion(Long numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    /**
     * @return the digitoVerificacionNit
     */
    public String getDigitoVerificacionNit() {
        return digitoVerificacionNit;
    }

    /**
     * @param digitoVerificacionNit
     *        the digitoVerificacionNit to set
     */
    public void setDigitoVerificacionNit(String digitoVerificacionNit) {
        this.digitoVerificacionNit = digitoVerificacionNit;
    }

    /**
     * @return the tipificacion
     */
    public String getTipificacion() {
        return tipificacion;
    }

    /**
     * @param tipificacion
     *        the tipificacion to set
     */
    public void setTipificacion(String tipificacion) {
        this.tipificacion = tipificacion;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ConsultasEmpresaPersonaDTO() {

    }

    /**
     * Metodo constructor del DTO
     * @param tipoIdentificacion
     * @param numIdentificacion
     * @param digitoVerificacionNit
     * @param tipificacion
     * @param fechaInicio
     * @param fechaFin
     */
    public ConsultasEmpresaPersonaDTO(String tipoIdentificacion, Long numIdentificacion, String digitoVerificacionNit, String tipificacion,
            Date fechaInicio, Date fechaFin) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numIdentificacion = numIdentificacion;
        this.digitoVerificacionNit = digitoVerificacionNit;
        this.tipificacion = tipificacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
