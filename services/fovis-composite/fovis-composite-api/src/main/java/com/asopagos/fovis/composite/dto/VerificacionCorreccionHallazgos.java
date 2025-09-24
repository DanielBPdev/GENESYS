package com.asopagos.fovis.composite.dto;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos necesarios para el registro de la verificación de control interno.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-031 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class VerificacionCorreccionHallazgos {

    /**
     * Identificador global de la solicitud de postulación.
     */
    private Long idSolicitud;
    /**
     * Identificador de la tarea.
     */
    private Long idTarea;

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *        the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea
     *        the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

}
