package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class CorreccionVistasDTO implements Serializable{

    /**
     * Serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único, llave primaria numero de operación de la solicitud de corrección de aportes
     */
    private Long idSolicitudCorreccionAporte;

    /**
     * Código identificador de llave primaria de la solicitud global 
     */
    private Long idSolicitud;
    
    /**
     * Monto de los aportes solicitados para corrección
     */
    private BigDecimal montoCorreccion;

    /**
     * Monto de intereses solicitados para corrección
     */
    private BigDecimal interesesCorreccion;
    
    /**
     * Monto de intereses solicitados para corrección
     */
    private BigDecimal totalCorreccion;
    
    /**
     * Identificador del aporte general
     */
    private Long idAporteGeneral;
    
    /**
     * Método constructor
     */
    public CorreccionVistasDTO() {
    }

    /**
     * @return the idSolicitudCorreccionAporte
     */
    public Long getIdSolicitudCorreccionAporte() {
        return idSolicitudCorreccionAporte;
    }

    /**
     * @param idSolicitudCorreccionAporte the idSolicitudCorreccionAporte to set
     */
    public void setIdSolicitudCorreccionAporte(Long idSolicitudCorreccionAporte) {
        this.idSolicitudCorreccionAporte = idSolicitudCorreccionAporte;
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
     * @return the montoCorreccion
     */
    public BigDecimal getMontoCorreccion() {
        return montoCorreccion;
    }

    /**
     * @param montoCorreccion the montoCorreccion to set
     */
    public void setMontoCorreccion(BigDecimal montoCorreccion) {
        this.montoCorreccion = montoCorreccion;
    }

    /**
     * @return the interesesCorreccion
     */
    public BigDecimal getInteresesCorreccion() {
        return interesesCorreccion;
    }

    /**
     * @param interesesCorreccion the interesesCorreccion to set
     */
    public void setInteresesCorreccion(BigDecimal interesesCorreccion) {
        this.interesesCorreccion = interesesCorreccion;
    }

    /**
     * @return the totalCorreccion
     */
    public BigDecimal getTotalCorreccion() {
        return totalCorreccion;
    }

    /**
     * @param totalCorreccion the totalCorreccion to set
     */
    public void setTotalCorreccion(BigDecimal totalCorreccion) {
        this.totalCorreccion = totalCorreccion;
    }

    /**
     * @return the idAporteGeneral
     */
    public Long getIdAporteGeneral() {
        return idAporteGeneral;
    }

    /**
     * @param idAporteGeneral the idAporteGeneral to set
     */
    public void setIdAporteGeneral(Long idAporteGeneral) {
        this.idAporteGeneral = idAporteGeneral;
    }

}
