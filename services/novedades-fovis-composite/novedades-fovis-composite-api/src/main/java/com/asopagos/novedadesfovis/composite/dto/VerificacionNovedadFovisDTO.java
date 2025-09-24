package com.asopagos.novedadesfovis.composite.dto;

import java.io.Serializable;
import com.asopagos.dto.EscalamientoSolicitudDTO;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos necesarios para finalizar la verificación de la postulación.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-026,027,031-3.2.2-041 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class VerificacionNovedadFovisDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador solicitud global de novedad
     */
    private Long idSolicitud;

    /**
     * Identificador de la tarea.
     */
    private Long idTarea;

    /**
     * Indicador del resultado de la verificacion que determina las acciones a realizar
     */
    private Integer resultado;

    /**
     * Escalamiento a miembros del hogar
     */
    private EscalamientoSolicitudDTO escalamientoMiembrosHogar;

    /**
     * Escalamiento jurídico
     */
    private EscalamientoSolicitudDTO escalamientoJuridico;

    /**
     * Escalamiento al técnico en construcción
     */
    private EscalamientoSolicitudDTO escalamientoTecnicoConstruccion;

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
     * @return the resultado
     */
    public Integer getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *        the resultado to set
     */
    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the escalamientoMiembrosHogar
     */
    public EscalamientoSolicitudDTO getEscalamientoMiembrosHogar() {
        return escalamientoMiembrosHogar;
    }

    /**
     * @param escalamientoMiembrosHogar
     *        the escalamientoMiembrosHogar to set
     */
    public void setEscalamientoMiembrosHogar(EscalamientoSolicitudDTO escalamientoMiembrosHogar) {
        this.escalamientoMiembrosHogar = escalamientoMiembrosHogar;
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
     * @return the escalamientoTecnicoConstruccion
     */
    public EscalamientoSolicitudDTO getEscalamientoTecnicoConstruccion() {
        return escalamientoTecnicoConstruccion;
    }

    /**
     * @param escalamientoTecnicoConstruccion
     *        the escalamientoTecnicoConstruccion to set
     */
    public void setEscalamientoTecnicoConstruccion(EscalamientoSolicitudDTO escalamientoTecnicoConstruccion) {
        this.escalamientoTecnicoConstruccion = escalamientoTecnicoConstruccion;
    }

}
