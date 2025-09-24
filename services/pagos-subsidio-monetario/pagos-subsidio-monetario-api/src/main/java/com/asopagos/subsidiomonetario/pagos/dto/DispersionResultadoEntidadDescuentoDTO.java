package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene las entidades de descuento y sus descuentos en
 * la dispersión masiva de liquidación <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionResultadoEntidadDescuentoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1104903200840193918L;

    /** Fecha del periodo liquidado */
    private Date periodoLiquidado;

    /** Fecha de liquidación */
    private Date fechaLiquidacion;

    /** Fecha de generaciíon del archivo */
    private Date fechaGeneracion;

    /** Monto total descontado en la entidad */
    private BigDecimal montoTotalDescontado;

    /** Lista de los descuentos por cada entidad */
    private List<DetalleResultadoEntidadDescuentoDTO> lstDescuentos;

    /** Lista de identificadores de prioridades de las entidades de descuento */
    private List<Long> identificadoresPrioridades;

    /** Lista de identificadores de condiciones para las personas en el proceso de liquidación */
    private List<Long> identificadoresCondiciones;

    /**
     * @return the periodoLiquidado
     */
    public Date getPeriodoLiquidado() {
        return periodoLiquidado;
    }

    /**
     * @param periodoLiquidado
     *        the periodoLiquidado to set
     */
    public void setPeriodoLiquidado(Date periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    /**
     * @return the fechaLiquidacion
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     *        the fechaLiquidacion to set
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return the fechaGeneracion
     */
    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    /**
     * @param fechaGeneracion
     *        the fechaGeneracion to set
     */
    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    /**
     * @return the montoTotalDescontado
     */
    public BigDecimal getMontoTotalDescontado() {
        return montoTotalDescontado;
    }

    /**
     * @param montoTotalDescontado
     *        the montoTotalDescontado to set
     */
    public void setMontoTotalDescontado(BigDecimal montoTotalDescontado) {
        this.montoTotalDescontado = montoTotalDescontado;
    }

    /**
     * @return the lstDescuentos
     */
    public List<DetalleResultadoEntidadDescuentoDTO> getLstDescuentos() {
        return lstDescuentos;
    }

    /**
     * @param lstDescuentos
     *        the lstDescuentos to set
     */
    public void setLstDescuentos(List<DetalleResultadoEntidadDescuentoDTO> lstDescuentos) {
        this.lstDescuentos = lstDescuentos;
    }

    /**
     * @return the identificadoresPrioridades
     */
    public List<Long> getIdentificadoresPrioridades() {
        return identificadoresPrioridades;
    }

    /**
     * @param identificadoresPrioridades
     *        the identificadoresPrioridades to set
     */
    public void setIdentificadoresPrioridades(List<Long> identificadoresPrioridades) {
        this.identificadoresPrioridades = identificadoresPrioridades;
    }

    /**
     * @return the identificadoresCondiciones
     */
    public List<Long> getIdentificadoresCondiciones() {
        return identificadoresCondiciones;
    }

    /**
     * @param identificadoresCondiciones
     *        the identificadoresCondiciones to set
     */
    public void setIdentificadoresCondiciones(List<Long> identificadoresCondiciones) {
        this.identificadoresCondiciones = identificadoresCondiciones;
    }


    @Override
    public String toString() {
        return "{" +
            " periodoLiquidado='" + getPeriodoLiquidado() + "'" +
            ", fechaLiquidacion='" + getFechaLiquidacion() + "'" +
            ", fechaGeneracion='" + getFechaGeneracion() + "'" +
            ", montoTotalDescontado='" + getMontoTotalDescontado() + "'" +
            ", lstDescuentos='" + getLstDescuentos() + "'" +
            ", identificadoresPrioridades='" + getIdentificadoresPrioridades() + "'" +
            ", identificadoresCondiciones='" + getIdentificadoresCondiciones() + "'" +
            "}";
    }


}
