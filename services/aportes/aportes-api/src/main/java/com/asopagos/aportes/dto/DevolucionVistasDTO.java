package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class DevolucionVistasDTO implements Serializable {

    /**
     * Serial version 
     */
    private static final long serialVersionUID = 3145124607831570662L;

    /**
     * Identificador único, llave primaria numero de operación de la solicitud de devolucion de aportes
     */
    private Long idSolicitudDevolucionAporte;

    /**
     * Código identificador de llave primaria de la solicitud global 
     */
    private Long idSolicitud;
    
    /**
     * Monto de los aportes solicitados para devolución
     */
    private BigDecimal montoDevolucion;

    /**
     * Monto de intereses solicitados para devolución
     */
    private BigDecimal interesesDevolucion;
    
    /**
     * Monto de intereses solicitados para devolución
     */
    private BigDecimal totalDevolucion;
    
    /**
     * Fecha de radicación de la solicitud
     */
    private Date fechaRadicacion;

    /**
     * Identificador del aporte general
     */
    private List<Long> idAporteGeneral;

    /**
     *  Método constructor
     */
    public DevolucionVistasDTO() {
    }

    /**
     * @return the idSolicitudDevolucionAporte
     */
    public Long getIdSolicitudDevolucionAporte() {
        return idSolicitudDevolucionAporte;
    }

    /**
     * @param idSolicitudDevolucionAporte the idSolicitudDevolucionAporte to set
     */
    public void setIdSolicitudDevolucionAporte(Long idSolicitudDevolucionAporte) {
        this.idSolicitudDevolucionAporte = idSolicitudDevolucionAporte;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the montoDevolucion
     */
    public BigDecimal getMontoDevolucion() {
        return montoDevolucion;
    }

    /**
     * @param montoDevolucion the montoDevolucion to set
     */
    public void setMontoDevolucion(BigDecimal montoDevolucion) {
        this.montoDevolucion = montoDevolucion;
    }

    /**
     * @return the interesesDevolucion
     */
    public BigDecimal getInteresesDevolucion() {
        return interesesDevolucion;
    }

    /**
     * @param interesesDevolucion the interesesDevolucion to set
     */
    public void setInteresesDevolucion(BigDecimal interesesDevolucion) {
        this.interesesDevolucion = interesesDevolucion;
    }

    /**
     * @return the totalDevolucion
     */
    public BigDecimal getTotalDevolucion() {
        return totalDevolucion;
    }

    /**
     * @param totalDevolucion the totalDevolucion to set
     */
    public void setTotalDevolucion(BigDecimal totalDevolucion) {
        this.totalDevolucion = totalDevolucion;
    }

    /**
     * @return the fechaRadicacion
     */
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion the fechaRadicacion to set
     */
    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the idAporteGeneral
     */
    public List<Long> getIdAporteGeneral() {
        return idAporteGeneral;
    }

    /**
     * @param idAporteGeneral the idAporteGeneral to set
     */
    public void setIdAporteGeneral(List<Long> idAporteGeneral) {
        this.idAporteGeneral = idAporteGeneral;
    }
  
}
