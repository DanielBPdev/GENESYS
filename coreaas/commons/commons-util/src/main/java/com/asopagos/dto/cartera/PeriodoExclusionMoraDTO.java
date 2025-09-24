package com.asopagos.dto.cartera;

import java.io.Serializable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.cartera.PeriodoExclusionMora;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;

/**
 * DTO que contiene los datos del periodo de exclusion de cartera
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 */
@XmlRootElement
public class PeriodoExclusionMoraDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1567272024661346018L;

    /**
     * Identificador del periodo de exclusion de mora
     */
    private Long idPeriodoExclusionMora;
    /**
     * Fecha en que se toma el periodo
     */
    private Long fechaPeriodo;
    /**
     * Identificador de la exclusion de cartera
     */
    private Long idExclusionCartera;

    /**
     * Estado del periodo de exclusión
     */
    private EstadoActivoInactivoEnum estadoPeriodo;
    
    /**
    * 
    */
    public PeriodoExclusionMoraDTO() {
 
    }

    /**
     * Método constructor
     * @param periodoExclusionMora
     */
    public PeriodoExclusionMoraDTO(PeriodoExclusionMora periodoExclusionMora) {
        super();
        this.idPeriodoExclusionMora = periodoExclusionMora.getIdPeriodoExclusionMora();
        if (periodoExclusionMora.getFechaPeriodo() != null) {
            this.fechaPeriodo = periodoExclusionMora.getFechaPeriodo().getTime();
        }
        this.idExclusionCartera = periodoExclusionMora.getIdExclusionCartera();
        this.estadoPeriodo= periodoExclusionMora.getEstadoPeriodo();
    }

    /**
     * Método que retorna el valor de idPeriodoExclusionMora.
     * @return valor de idPeriodoExclusionMora.
     */
    public Long getIdPeriodoExclusionMora() {
        return idPeriodoExclusionMora;
    }

    /**
     * Método que retorna el valor de fechaPeriodo.
     * @return valor de fechaPeriodo.
     */
    public Long getFechaPeriodo() {
        return fechaPeriodo;
    }

    /**
     * Método que retorna el valor de idExclusionCartera.
     * @return valor de idExclusionCartera.
     */
    public Long getIdExclusionCartera() {
        return idExclusionCartera;
    }

    /**
     * Método encargado de modificar el valor de idPeriodoExclusionMora.
     * @param valor
     *        para modificar idPeriodoExclusionMora.
     */
    public void setIdPeriodoExclusionMora(Long idPeriodoExclusionMora) {
        this.idPeriodoExclusionMora = idPeriodoExclusionMora;
    }

    /**
     * Método encargado de modificar el valor de fechaPeriodo.
     * @param valor
     *        para modificar fechaPeriodo.
     */
    public void setFechaPeriodo(Long fechaPeriodo) {
        this.fechaPeriodo = fechaPeriodo;
    }

    /**
     * Método encargado de modificar el valor de idExclusionCartera.
     * @param valor
     *        para modificar idExclusionCartera.
     */
    public void setIdExclusionCartera(Long idExclusionCartera) {
        this.idExclusionCartera = idExclusionCartera;
    }

    /**
     * Método que retorna el valor de estadoPeriodo.
     * @return valor de estadoPeriodo.
     */
    public EstadoActivoInactivoEnum getEstadoPeriodo() {
        return estadoPeriodo;
    }

    /**
     * Método encargado de modificar el valor de estadoPeriodo.
     * @param valor para modificar estadoPeriodo.
     */
    public void setEstadoPeriodo(EstadoActivoInactivoEnum estadoPeriodo) {
        this.estadoPeriodo = estadoPeriodo;
    }

}