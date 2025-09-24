package com.asopagos.fovis.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.EscalamientoSolicitudDTO;

/**
 * 
 * <b>Descripcion:</b> Clase que define los datos de entrada para el analisis de una solicitud de postulación.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-024_y_028 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
@XmlRootElement
public class AnalisisSolicitudPostulacionDTO implements Serializable {

    /**
     * Variable idSolicitud
     */
    private Long idSolicitud;
    /**
     * Variable idTarea
     */
    private Long idTarea;
    /**
     * Variable escalamientoSolicitud
     */
    private EscalamientoSolicitudDTO escalamientoSolicitud;

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
     * Método encargado de retornar el valor del campo escalamientoSolicitud
     * @return el campo escalamientoSolicitud
     */
    public EscalamientoSolicitudDTO getEscalamientoSolicitud() {
        return escalamientoSolicitud;
    }

    /**
     * Método encargado de asignar el valor al campo escalamientoSolicitud
     * @param escalamientoSolicitud
     *        escalamientoSolicitud a asignar
     */
    public void setEscalamientoSolicitud(EscalamientoSolicitudDTO escalamientoSolicitud) {
        this.escalamientoSolicitud = escalamientoSolicitud;
    }

}
