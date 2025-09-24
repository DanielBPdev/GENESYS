/**
 * 
 */
package com.asopagos.pila.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que contiene los datos del resultado del proceso de finalización de una planilla asistida<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoFinalizacionPlanillaAsistidaDTO implements Serializable {
    private static final long serialVersionUID = -9098265920995847045L;

    /** Cantidad de aportes procesados */
    private Integer cantidadAportesAfectados;
    
    /** Indicador de aportes pendientes por procesar por el ESB */
    private Boolean aportesPendientes;
    
    /** Indicador de novedades pendientes por procesar por el ESB */
    private Boolean novedadesPendientes;
    
    /** Indicador que marca al archivo como corrección o adición */
    private Boolean esAdicion;

    /**
     * @return the cantidadAportesAfectados
     */
    public Integer getCantidadAportesAfectados() {
        return cantidadAportesAfectados;
    }

    /**
     * @param cantidadAportesAfectados the cantidadAportesAfectados to set
     */
    public void setCantidadAportesAfectados(Integer cantidadAportesAfectados) {
        this.cantidadAportesAfectados = cantidadAportesAfectados;
    }

    /**
     * @return the aportesPendientes
     */
    public Boolean getAportesPendientes() {
        return aportesPendientes;
    }

    /**
     * @param aportesPendientes the aportesPendientes to set
     */
    public void setAportesPendientes(Boolean aportesPendientes) {
        this.aportesPendientes = aportesPendientes;
    }

    /**
     * @return the novedadesPendientes
     */
    public Boolean getNovedadesPendientes() {
        return novedadesPendientes;
    }

    /**
     * @param novedadesPendientes the novedadesPendientes to set
     */
    public void setNovedadesPendientes(Boolean novedadesPendientes) {
        this.novedadesPendientes = novedadesPendientes;
    }

    /**
     * @return the esAdicion
     */
    public Boolean getEsAdicion() {
        return esAdicion;
    }

    /**
     * @param esAdicion the esAdicion to set
     */
    public void setEsAdicion(Boolean esAdicion) {
        this.esAdicion = esAdicion;
    }
}
