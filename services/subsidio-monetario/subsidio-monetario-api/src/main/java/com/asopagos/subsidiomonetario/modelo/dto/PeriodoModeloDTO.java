package com.asopagos.subsidiomonetario.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.subsidiomonetario.liquidacion.Periodo;

/**
 * <b>Descripcion:</b> Clase DTO que representa un periodo <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

public class PeriodoModeloDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria del periodo
     */
    private Long idPeriodo;

    /**
     * Fecha del periodo
     */
    private Long fechaPeriodo;

    /**
     * @return the idPeriodo
     */
    public Long getIdPeriodo() {
        return idPeriodo;
    }

    /**
     * @param idPeriodo
     *        the idPeriodo to set
     */
    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    /**
     * @return the fechaPeriodo
     */
    public Long getFechaPeriodo() {
        return fechaPeriodo;
    }

    /**
     * @param fechaPeriodo
     *        the fechaPeriodo to set
     */
    public void setFechaPeriodo(Long fechaPeriodo) {
        this.fechaPeriodo = fechaPeriodo;
    }

    /**
     * Convierte una entidad en DTO
     * @param Entidad
     *        periodo a convertir en DTO
     * @return DTO que representa la entidad
     */
    public void convertToDTO(Periodo periodo) {
        this.idPeriodo = periodo.getIdPeriodo();
        if (periodo.getFechaPeriodo() != null) {
            this.fechaPeriodo = periodo.getFechaPeriodo().getTime();
        }
    }

    /**
     * Convierte un DTO a una entidad
     * @return entidad Periodo
     */
    public Periodo convertToEntity() {
        Periodo periodo = new Periodo();
        periodo.setIdPeriodo(this.getIdPeriodo());
        if (this.getFechaPeriodo() != null) {
            periodo.setFechaPeriodo(new Date(this.getFechaPeriodo()));
        }
        return periodo;
    }
}