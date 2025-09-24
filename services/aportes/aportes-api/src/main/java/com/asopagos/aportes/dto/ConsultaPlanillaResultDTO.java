package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.aportes.AportePilaDTO;

/**
 * <b>Descripcion:</b> DTO que contiene los resultados de la consulta de planillas para el 
 * envío de comunicados de PILA <br/>
 * <b>Módulo:</b> Asopagos - HU-211-400 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConsultaPlanillaResultDTO implements Serializable {
    private static final long serialVersionUID = 625467065854215914L;

    /** Cantidad de aportes persistidos en core */
    private Integer cantidadAportes;
    
    /** Listado de los aportes para la generación de las notificaciones */
    private List<AportePilaDTO> listadoAportes;
    
    /** Indicador de aporte PILA manual */
    private Boolean esPilaManual;

    /**
     * @return the cantidadAportes
     */
    public Integer getCantidadAportes() {
        return cantidadAportes;
    }

    /**
     * @param cantidadAportes the cantidadAportes to set
     */
    public void setCantidadAportes(Integer cantidadAportes) {
        this.cantidadAportes = cantidadAportes;
    }

    /**
     * @return the listadoAportes
     */
    public List<AportePilaDTO> getListadoAportes() {
        return listadoAportes;
    }

    /**
     * @param listadoAportes the listadoAportes to set
     */
    public void setListadoAportes(List<AportePilaDTO> listadoAportes) {
        this.listadoAportes = listadoAportes;
    }

    /**
     * @return the esPilaManual
     */
    public Boolean getEsPilaManual() {
        return esPilaManual;
    }

    /**
     * @param esPilaManual the esPilaManual to set
     */
    public void setEsPilaManual(Boolean esPilaManual) {
        this.esPilaManual = esPilaManual;
    }
}
