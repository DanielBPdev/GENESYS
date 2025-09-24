package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import java.util.Map;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el mapa de los resultados de la actualización del estado de 
 * índices de planilla PILA por aprobación o reproceso de registros con inconsistencias<br/>
 * <b>Módulo:</b> Asopagos - HU-211-399 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoActualizacionEstadoDTO implements Serializable {
    private static final long serialVersionUID = -4976082876779671916L;
    
    /** ID del registro general */
    private Long idRegistroGeneral;
    
    /** Estado asignado al registro */
    private EstadoProcesoArchivoEnum estado;
    
    /** Indicador de que el estado ya fue aplicado para la planilla */
    private Boolean aplicado;

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the estado
     */
    public EstadoProcesoArchivoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoProcesoArchivoEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the aplicado
     */
    public Boolean getAplicado() {
        return aplicado;
    }

    /**
     * @param aplicado the aplicado to set
     */
    public void setAplicado(Boolean aplicado) {
        this.aplicado = aplicado;
    }
}
