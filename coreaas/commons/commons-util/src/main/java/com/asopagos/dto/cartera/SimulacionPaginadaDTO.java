package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO que contiene los datos después de ejecutada una simulación.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @updated 29-sept.-2017 2:35:50 p.m.
 */
@XmlRootElement
public class SimulacionPaginadaDTO implements Serializable {

    /**
     * Atributo que contiene la lista de simulaciones.
     */
    private List<SimulacionDTO> simulaciones;
    
    /**
     * Atributo que contiene el total de registros para enviar en el response.
     */
    private Integer totalRecords;

    /**
     * Método que retorna el valor de simulaciones.
     * @return valor de simulaciones.
     */
    public List<SimulacionDTO> getSimulaciones() {
        return simulaciones;
    }

    /**
     * Método encargado de modificar el valor de simulaciones.
     * @param valor para modificar simulaciones.
     */
    public void setSimulaciones(List<SimulacionDTO> simulaciones) {
        this.simulaciones = simulaciones;
    }

    /**
     * Método que retorna el valor de totalRecords.
     * @return valor de totalRecords.
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     * Método encargado de modificar el valor de totalRecords.
     * @param valor para modificar totalRecords.
     */
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
    
    
}