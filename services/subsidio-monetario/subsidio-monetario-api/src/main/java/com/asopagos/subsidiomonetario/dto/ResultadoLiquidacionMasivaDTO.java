package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de la liquidacion<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-436 y 438<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoLiquidacionMasivaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Lista de los item mostrados en la sección de monto */
    private List<ResultadoMontoLiquidadoDTO> resultadoMonto;

    /** Lista de los item mostrados en la sección de cantidades empresa/trabajdor */
    private List<CantidadEmpresaTrabajadorDTO> cantidadEmpresaTrabajador;

    /** Atributo que indica el estado de finalización del proceso de liquidación específica */
    private Boolean estadoProceso;
    
    /** Atributo que indica el estado de finalización del proceso de liquidación específica */
    private Boolean falloLiquidacion;
    
    /**
     * Método que permite obtener la lista de items para la sección de monto
     * @return lista de items
     */
    public List<ResultadoMontoLiquidadoDTO> getResultadoMonto() {
        return resultadoMonto;
    }

    /**
     * Método que permite establecer la lista de items para la sección de monto
     * @param resultadoMonto
     *        lista de items
     */
    public void setResultadoMonto(List<ResultadoMontoLiquidadoDTO> resultadoMonto) {
        this.resultadoMonto = resultadoMonto;
    }

    /**
     * Método que permite obtener la lista de items para la sección de cantidad trabajador/empresa
     * @return lista de items
     */
    public List<CantidadEmpresaTrabajadorDTO> getCantidadEmpresaTrabajador() {
        return cantidadEmpresaTrabajador;
    }

    /**
     * Método que permite establecer la lista de items para la sección de cantidad trabajador/empresa
     * @param cantidadEmpresaTrabajador
     *        lista de items
     */
    public void setCantidadEmpresaTrabajador(List<CantidadEmpresaTrabajadorDTO> cantidadEmpresaTrabajador) {
        this.cantidadEmpresaTrabajador = cantidadEmpresaTrabajador;
    }
    
    /**
     * @return the estadoProceso
     */
    public Boolean getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * @param estadoProceso
     *        the estadoProceso to set
     */
    public void setEstadoProceso(Boolean estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public Boolean getFalloLiquidacion() {
        return falloLiquidacion;
    }

    public void setFalloLiquidacion(Boolean falloLiquidacion) {
        this.falloLiquidacion = falloLiquidacion;
    }
    
    

}
