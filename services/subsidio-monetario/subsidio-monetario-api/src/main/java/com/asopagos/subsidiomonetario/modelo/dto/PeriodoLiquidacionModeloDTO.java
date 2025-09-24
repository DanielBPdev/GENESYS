package com.asopagos.subsidiomonetario.modelo.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoPeriodoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa un periodo de liquidación <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

public class PeriodoLiquidacionModeloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria del periodo para el proceso de liquidacion de subsidio monerario
     */
    private Long idPeriodoLiquidacion;

    /**
     * Referencia el periodo de liquidación para el procesamiento
     */
    private Long idPeriodo;

    /**
     * Referencia el periodo de liquidación retroactivo para el procesamiento
     */
    private Long idPeriodoRetroactivo;

    /**
     * Descripción del tipo de periodo indicado para el proceso de liquidacion de subsidio monerario
     */
    private TipoPeriodoEnum tipoPeriodo;

    /**
     * @return the idPeriodoLiquidacion
     */
    public Long getIdPeriodoLiquidacion() {
        return idPeriodoLiquidacion;
    }

    /**
     * @param idPeriodoLiquidacion the idPeriodoLiquidacion to set
     */
    public void setIdPeriodoLiquidacion(Long idPeriodoLiquidacion) {
        this.idPeriodoLiquidacion = idPeriodoLiquidacion;
    }

    /**
     * @return the idPeriodo
     */
    public Long getIdPeriodo() {
        return idPeriodo;
    }

    /**
     * @param idPeriodo the idPeriodo to set
     */
    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    /**
     * @return the idPeriodoRetroactivo
     */
    public Long getIdPeriodoRetroactivo() {
        return idPeriodoRetroactivo;
    }

    /**
     * @param idPeriodoRetroactivo the idPeriodoRetroactivo to set
     */
    public void setIdPeriodoRetroactivo(Long idPeriodoRetroactivo) {
        this.idPeriodoRetroactivo = idPeriodoRetroactivo;
    }

    /**
     * @return the tipoPeriodo
     */
    public TipoPeriodoEnum getTipoPeriodo() {
        return tipoPeriodo;
    }

    /**
     * @param tipoPeriodo the tipoPeriodo to set
     */
    public void setTipoPeriodo(TipoPeriodoEnum tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }
}
