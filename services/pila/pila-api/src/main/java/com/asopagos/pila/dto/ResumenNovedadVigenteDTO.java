/**
 * 
 */
package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripcion:</b> DTO que presenta los datos básicos de una novedad vigente en BD Core<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResumenNovedadVigenteDTO implements Serializable {
    private static final long serialVersionUID = -7525832111788363590L;
    
    /**
     * Tipo de novedad (Transacción)
     * */
    private TipoTransaccionEnum novedad;
    
    /**
     * Fecha inicio novedad
     * */
    private Long fechaInicioNovedad;
    
    /**
     * Fecha fin novedad
     * */
    private Long fechaFinNovedad;
    
    /**
     * Constructor por defecto para JSON converter
     * */
    public ResumenNovedadVigenteDTO(){}
    
    /**
     * Constructor para la consulta PilaService.NovedadDetallada.ConsultarNovedadesVigentesPorCotizante
     * @param novedad
     * @param fechaInicioNovedad
     * @param fechaFinNovedad
     */
    public ResumenNovedadVigenteDTO(TipoTransaccionEnum novedad, Date fechaInicioNovedad, Date fechaFinNovedad){
        this.novedad = novedad;
        if(fechaInicioNovedad != null){
            this.fechaInicioNovedad = fechaInicioNovedad.getTime();
        } 
        if(fechaFinNovedad != null){
            this.fechaFinNovedad = fechaFinNovedad.getTime();
        }
    }

    /**
     * @return the novedad
     */
    public TipoTransaccionEnum getNovedad() {
        return novedad;
    }

    /**
     * @param novedad the novedad to set
     */
    public void setNovedad(TipoTransaccionEnum novedad) {
        this.novedad = novedad;
    }

    /**
     * @return the fechaInicioNovedad
     */
    public Long getFechaInicioNovedad() {
        return fechaInicioNovedad;
    }

    /**
     * @param fechaInicioNovedad the fechaInicioNovedad to set
     */
    public void setFechaInicioNovedad(Long fechaInicioNovedad) {
        this.fechaInicioNovedad = fechaInicioNovedad;
    }

    /**
     * @return the fechaFinNovedad
     */
    public Long getFechaFinNovedad() {
        return fechaFinNovedad;
    }

    /**
     * @param fechaFinNovedad the fechaFinNovedad to set
     */
    public void setFechaFinNovedad(Long fechaFinNovedad) {
        this.fechaFinNovedad = fechaFinNovedad;
    }
}
