package com.asopagos.legalizacionfovis.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * <b>Descripcion:</b> Clase que define los datos de entrada para el resultado definitivo del analisis de una solicitud de postulación.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-025_y_029 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
@XmlRootElement
public class ResultadoAnalisisLegalizacionDesembolsoDTO implements Serializable {

    /**
     * Variable idSolicitud
     */
    private Long idSolicitud;
    /**
     * Variable idTarea
     */
    private Long idTarea;
    /**
     * Variable resultadoAnalisisEsp
     */
    private Integer resultadoAnalisisEsp;

    /**
     * Método encargado de retornar el valor del campo idSolicitud
     * 
     * @return el campo idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Método encargado de asignar el valor al campo idSolicitud
     * 
     * @param idSolicitud
     *        idSolicitud a asignar
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Método encargado de retornar el valor del campo idTarea
     * 
     * @return el campo idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * Método encargado de asignar el valor al campo idTarea
     * 
     * @param idTarea
     *        idTarea a asignar
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * Método encargado de retornar el valor del campo resultadoAnalisisEsp
     * 
     * @return el campo resultadoAnalisisEsp
     */
    public Integer getResultadoAnalisisEsp() {
        return resultadoAnalisisEsp;
    }

    /**
     * Método encargado de asignar el valor al campo resultadoAnalisisEsp
     * 
     * @param resultadoAnalisisEsp
     *        resultadoAnalisisEsp a asignar
     */
    public void setResultadoAnalisisEsp(Integer resultadoAnalisisEsp) {
        this.resultadoAnalisisEsp = resultadoAnalisisEsp;
    }

}
