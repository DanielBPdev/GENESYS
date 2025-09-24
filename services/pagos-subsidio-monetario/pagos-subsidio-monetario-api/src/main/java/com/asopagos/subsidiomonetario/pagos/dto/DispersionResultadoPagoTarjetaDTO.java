package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de resultante de los paogs en efectivo en la dispersión del monto liquidado
 * <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona.</a>
 */
public class DispersionResultadoPagoTarjetaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Fecha del periodo liquidado */
    private Date periodoLiquidado;

    /** Fecha de liquidación */
    private Date fechaLiquidacion;

    /** Fecha de generaciíon del archivo */
    private Date fechaGeneracion;

    /** Lista que contien eel date de cada abono de tarjeta */
    private List<DetalleResultadoPagoTarjetaDTO> listaDetalleAbonoTarjeta;

    /** Lista de identificadores de las personas asociadas a la dispersión al medio tarjeta */
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
     * @return the listaDetalleAbonoTarjeta
     */
    public List<DetalleResultadoPagoTarjetaDTO> getListaDetalleAbonoTarjeta() {
        return listaDetalleAbonoTarjeta;
    }

    /**
     * @param listaDetalleAbonoTarjeta
     *        the listaDetalleAbonoTarjeta to set
     */
    public void setListaDetalleAbonoTarjeta(List<DetalleResultadoPagoTarjetaDTO> listaDetalleAbonoTarjeta) {
        this.listaDetalleAbonoTarjeta = listaDetalleAbonoTarjeta;
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

}
