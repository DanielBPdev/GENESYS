package com.asopagos.fovis.composite.dto;

import com.asopagos.dto.EscalamientoSolicitudDTO;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos necesarios para finalizar la verificación de la postulación.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-026,027,031-3.2.2-041 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class VerificacionGestionPNCPostulacionDTO {

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
    private Integer resultadoVerifPNC;
    /**
     * Información del escalamiento de la solicitud de postulación para los miembros del hogar postulante.
     */
    private EscalamientoSolicitudDTO escalamientoMiembroHogar;
    /**
     * Información del escalamiento de la solicitud de postulación para el jurídico.
     */
    private EscalamientoSolicitudDTO escalamientoJuridico;
    /**
     * Información del escalamiento de la solicitud de postulación para el técnico de construcción.
     */
    private EscalamientoSolicitudDTO escalamientoTecnico;
    /**
     * Indicador del resultado de la verificacion por parte del back, que determina las acciones a realizar
     */
    private Integer resultadoVerificacionBack;

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
     * @return the resultadoVerifPNC
     */
    public Integer getResultadoVerifPNC() {
        return resultadoVerifPNC;
    }

    /**
     * @param resultadoVerifPNC
     *        the resultadoVerifPNC to set
     */
    public void setResultadoVerifPNC(Integer resultadoVerifPNC) {
        this.resultadoVerifPNC = resultadoVerifPNC;
    }

    /**
     * @return the escalamientoMiembroHogar
     */
    public EscalamientoSolicitudDTO getEscalamientoMiembroHogar() {
        return escalamientoMiembroHogar;
    }

    /**
     * @param escalamientoMiembroHogar
     *        the escalamientoMiembroHogar to set
     */
    public void setEscalamientoMiembroHogar(EscalamientoSolicitudDTO escalamientoMiembroHogar) {
        this.escalamientoMiembroHogar = escalamientoMiembroHogar;
    }

    /**
     * @return the escalamientoJuridico
     */
    public EscalamientoSolicitudDTO getEscalamientoJuridico() {
        return escalamientoJuridico;
    }

    /**
     * @param escalamientoJuridico
     *        the escalamientoJuridico to set
     */
    public void setEscalamientoJuridico(EscalamientoSolicitudDTO escalamientoJuridico) {
        this.escalamientoJuridico = escalamientoJuridico;
    }

    /**
     * @return the escalamientoTecnico
     */
    public EscalamientoSolicitudDTO getEscalamientoTecnico() {
        return escalamientoTecnico;
    }

    /**
     * @param escalamientoTecnico
     *        the escalamientoTecnico to set
     */
    public void setEscalamientoTecnico(EscalamientoSolicitudDTO escalamientoTecnico) {
        this.escalamientoTecnico = escalamientoTecnico;
    }

    /**
     * @return the resultadoVerificacionBack
     */
    public Integer getResultadoVerificacionBack() {
        return resultadoVerificacionBack;
    }

    /**
     * @param resultadoVerificacionBack
     *        the resultadoVerificacionBack to set
     */
    public void setResultadoVerificacionBack(Integer resultadoVerificacionBack) {
        this.resultadoVerificacionBack = resultadoVerificacionBack;
    }

}
