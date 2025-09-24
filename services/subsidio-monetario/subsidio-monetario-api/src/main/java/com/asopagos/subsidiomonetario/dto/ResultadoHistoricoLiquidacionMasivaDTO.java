package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>Descripcion:</b> DTO que contiene la información de resultado para una consulta de una liquidación histórica de tipo masiva<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-523<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoHistoricoLiquidacionMasivaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** número del registro */
    private Long numero;

    /** Número que identifica la operación de liquidación */
    private String numeroOperacion;

    /** Periodo para el cual se realizo la liquidación */
    private Date periodo;

    /** Fecha en la cual se generó la dispersion del proceso de liquidación */
    private Date fecha;

    /**
     * Método constructor por defecto
     */
    public ResultadoHistoricoLiquidacionMasivaDTO() {

    }

    /**
     * Método constructor con los atributos de clase
     * @param numeroOperacion
     *        valor del número de operación
     * @param periodo
     *        valor del periodo
     * @param fecha
     *        valor de la fecha en la que se dispersó la liquidación
     */
    public ResultadoHistoricoLiquidacionMasivaDTO(String numeroOperacion, Date periodo, Date fecha) {
        this.numeroOperacion = numeroOperacion;
        this.periodo = periodo;
        this.fecha = fecha;
    }

    /**
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * @param numero
     *        the numero to set
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * @return the numeroOperacion
     */
    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * @param numeroOperacion
     *        the numeroOperacion to set
     */
    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * @return the periodo
     */
    public Date getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo
     *        the periodo to set
     */
    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     *        the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
