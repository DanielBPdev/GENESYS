package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>Descripcion:</b> DTO que contiene elementos de respuesta enviados a pantallas
 * y que se usan en varios servicios <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

public class RespuestaGenericaDTO implements Serializable {

    private static final long serialVersionUID = -8849142300203645170L;

    /**
     * Resultado de una operacion
     */
    private Boolean operacionExitosa;
    
    /**
     * Id solicitud generado global
     */
    private Long idSolicitud;
    
    /**
     * Sede caja compensacion
     */
    private String sedeCajaCompensacion;
    
    /**
     * Id solicitud de liquidacion
     */
    private Long idSolicitudLiquidacion;
    
    /**
     * Indica que hay una operacion en proceso
     */
    private Boolean ejecucionEnProceso;
    
    /**
     * Causa error en caso de existir
     */
    private String causaError; 
    
    /**
     * Existe una liquidacion masiva en proceso
     */
    private Boolean liquidacionMasivaEnProceso;
    
    /**
     * Id de la liquidacion masiva en proceso
     */
    private Long idLiquidacionMasivaEnProceso;
    
    /**
     * Periodo actual
     */
    private Date periodo;
    
    /**
     * Numero de radicado asociado a la solicitud actual
     */
    private String numeroRadicado;
    
    public RespuestaGenericaDTO(){}
    
    /**
     * Constructor 
     * @param idSolicitud
     * @param idSolicitudLiquidacion
     * @param periodo
     * @param numeroRadicado
     */
    public RespuestaGenericaDTO(Long idSolicitud, Long idSolicitudLiquidacion, Date periodo, String numeroRadicado) {
        this.idSolicitud = idSolicitud;
        this.idSolicitudLiquidacion = idSolicitudLiquidacion;
        this.periodo = periodo;
        this.numeroRadicado = numeroRadicado;
    }    
    
    /**
     * @return the operacionExitosa
     */
    public Boolean getOperacionExitosa() {
        return operacionExitosa;
    }

    /**
     * @param operacionExitosa the operacionExitosa to set
     */
    public void setOperacionExitosa(Boolean operacionExitosa) {
        this.operacionExitosa = operacionExitosa;
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
     * @return the sedeCajaCompensacion
     */
    public String getSedeCajaCompensacion() {
        return sedeCajaCompensacion;
    }

    /**
     * @param sedeCajaCompensacion the sedeCajaCompensacion to set
     */
    public void setSedeCajaCompensacion(String sedeCajaCompensacion) {
        this.sedeCajaCompensacion = sedeCajaCompensacion;
    }

    /**
     * @return the idSolicitudLiquidacion
     */
    public Long getIdSolicitudLiquidacion() {
        return idSolicitudLiquidacion;
    }

    /**
     * @param idSolicitudLiquidacion the idSolicitudLiquidacion to set
     */
    public void setIdSolicitudLiquidacion(Long idSolicitudLiquidacion) {
        this.idSolicitudLiquidacion = idSolicitudLiquidacion;
    }

    /**
     * @return the ejecucionEnProceso
     */
    public Boolean getEjecucionEnProceso() {
        return ejecucionEnProceso;
    }

    /**
     * @param ejecucionEnProceso the ejecucionEnProceso to set
     */
    public void setEjecucionEnProceso(Boolean ejecucionEnProceso) {
        this.ejecucionEnProceso = ejecucionEnProceso;
    }

    /**
     * @return the mensajeError
     */
    public String getCausaError() {
        return causaError;
    }

    /**
     * @param mensajeError the mensajeError to set
     */
    public void setCausaError(String causaError) {
        this.causaError = causaError;
    }

    /**
     * @return the liquidacionMasivaEnProceso
     */
    public Boolean getLiquidacionMasivaEnProceso() {
        return liquidacionMasivaEnProceso;
    }

    /**
     * @param liquidacionMasivaEnProceso the liquidacionMasivaEnProceso to set
     */
    public void setLiquidacionMasivaEnProceso(Boolean liquidacionMasivaEnProceso) {
        this.liquidacionMasivaEnProceso = liquidacionMasivaEnProceso;
    }

    /**
     * @return the idLiquidacionMasivaEnProceso
     */
    public Long getIdLiquidacionMasivaEnProceso() {
        return idLiquidacionMasivaEnProceso;
    }

    /**
     * @param idLiquidacionMasivaEnProceso the idLiquidacionMasivaEnProceso to set
     */
    public void setIdLiquidacionMasivaEnProceso(Long idLiquidacionMasivaEnProceso) {
        this.idLiquidacionMasivaEnProceso = idLiquidacionMasivaEnProceso;
    }

    /**
     * @return the periodo
     */
    public Date getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }
}
