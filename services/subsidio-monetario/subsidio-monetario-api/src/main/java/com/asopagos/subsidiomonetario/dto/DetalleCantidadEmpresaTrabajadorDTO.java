package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de cada una de las consultas de la segunda sección de la liquidación masiva<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-436<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DetalleCantidadEmpresaTrabajadorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Cantidad de trabajadores asociados al detalle */
    private Integer cantidadTrabajadores;

    /** Periodo de liquidación asociado al detalle */
    private Date periodoLiquidado;

    /** Umbral parametrizado para la selección de los detalles en las tablas de resultado */
    private BigDecimal umbral;

    /** Información asociada al detalle de cada item */
    private List<DetalleItemEmpresaTrabajadorDTO> detalleItems;

    /**
     * @return the cantidadTrabajadores
     */
    public Integer getCantidadTrabajadores() {
        return cantidadTrabajadores;
    }

    /**
     * @param cantidadTrabajadores
     *        the cantidadTrabajadores to set
     */
    public void setCantidadTrabajadores(Integer cantidadTrabajadores) {
        this.cantidadTrabajadores = cantidadTrabajadores;
    }

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
     * @return the detalleItems
     */
    public List<DetalleItemEmpresaTrabajadorDTO> getDetalleItems() {
        return detalleItems;
    }

    /**
     * @param detalleItems
     *        the detalleItems to set
     */
    public void setDetalleItems(List<DetalleItemEmpresaTrabajadorDTO> detalleItems) {
        this.detalleItems = detalleItems;
    }

    /**
     * @return the umbral
     */
    public BigDecimal getUmbral() {
        return umbral;
    }

    /**
     * @param umbral
     *        the umbral to set
     */
    public void setUmbral(BigDecimal umbral) {
        this.umbral = umbral;
    }

}
