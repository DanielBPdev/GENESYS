package com.asopagos.fovis.composite.dto;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos necesarios para el registro de la verificación de control interno.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-031 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class VerificacionGestionControlInterno {

    /**
     * Identificador global de la solicitud de postulación.
     */
    private Long idSolicitud;
    /**
     * Identificador de la tarea.
     */
    private Long idTarea;
    /**
     * Indicador del resultado de la verificacion que determina las acciones a realizar
     */
    private Integer resultadoControlInterno;
    /**
     * Identificador del documento adjunto.
     */
    private String idDocumentoAdjunto;
    /**
     * Observaciones de la solicitud de postulación.
     */
    private String observaciones;
    /**
     * Registra el resultado de la verificación de las correcciones por parte de control interno.
     */
    private Integer resultadoCorreccionCruces;

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

    /**
     * @return the resultadoControlInterno
     */
    public Integer getResultadoControlInterno() {
        return resultadoControlInterno;
    }

    /**
     * @param resultadoControlInterno
     *        the resultadoControlInterno to set
     */
    public void setResultadoControlInterno(Integer resultadoControlInterno) {
        this.resultadoControlInterno = resultadoControlInterno;
    }

    /**
     * @return the idDocumentoAdjunto
     */
    public String getIdDocumentoAdjunto() {
        return idDocumentoAdjunto;
    }

    /**
     * @param idDocumentoAdjunto
     *        the idDocumentoAdjunto to set
     */
    public void setIdDocumentoAdjunto(String idDocumentoAdjunto) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones
     *        the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the resultadoCorreccionCruces
     */
    public Integer getResultadoCorreccionCruces() {
        return resultadoCorreccionCruces;
    }

    /**
     * @param resultadoCorreccionCruces
     *        the resultadoCorreccionCruces to set
     */
    public void setResultadoCorreccionCruces(Integer resultadoCorreccionCruces) {
        this.resultadoCorreccionCruces = resultadoCorreccionCruces;
    }

}
