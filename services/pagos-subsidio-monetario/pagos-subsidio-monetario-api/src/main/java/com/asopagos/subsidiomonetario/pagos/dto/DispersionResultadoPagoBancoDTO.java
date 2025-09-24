package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de resultante de los pagos realizados por banco en la dispersión del monto
 * liquidado <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionResultadoPagoBancoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 924572119215314378L;

    /** Fecha del periodo liquidado */
    private Date periodoLiquidado;

    /** Fecha de liquidación */
    private Date fechaLiquidacion;

    /** Fecha de generaciíon del archivo */
    private Date fechaGeneracion;

    /** Lista de consignaciones realizadas */
    private List<DispersionResultadoPagoBancoConsignacionesDTO> lstConsignaciones;

    /** Lista de pagos judiciales */
    private List<DispersionResultadoPagoBancoPagoJuducialDTO> lstPagosJudiciales;

    /** Lista de identificadores de condiciones */
    private List<Long> identificadoresCondiciones;

    /**
     * @return the lstPagosJudiciales
     */
    public List<DispersionResultadoPagoBancoPagoJuducialDTO> getLstPagosJudiciales() {
        return lstPagosJudiciales;
    }

    /**
     * @param lstPagosJudiciales
     *        the lstPagosJudiciales to set
     */
    public void setLstPagosJudiciales(List<DispersionResultadoPagoBancoPagoJuducialDTO> lstPagosJudiciales) {
        this.lstPagosJudiciales = lstPagosJudiciales;
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
     * @return the lstConsignaciones
     */
    public List<DispersionResultadoPagoBancoConsignacionesDTO> getLstConsignaciones() {
        return lstConsignaciones;
    }

    /**
     * @param lstConsignaciones
     *        the lstConsignaciones to set
     */
    public void setLstConsignaciones(List<DispersionResultadoPagoBancoConsignacionesDTO> lstConsignaciones) {
        this.lstConsignaciones = lstConsignaciones;
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
