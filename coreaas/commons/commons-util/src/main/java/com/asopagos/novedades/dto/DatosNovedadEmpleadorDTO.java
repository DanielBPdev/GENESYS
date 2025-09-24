package com.asopagos.novedades.dto;

import java.math.BigInteger;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;

/**
 * <b>Descripcion:</b> Clase que contiene los datos resumidos que se muestran en la vista 360 para las novedades de un empleador<br/>
 * <b>Módulo:</b> Asopagos - HU TRA 488<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */

public class DatosNovedadEmpleadorDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Tipo de transaccion de la novedad (para indicar el nombre).
     */
    private TipoTransaccionEnum novedad;
    /**
     * Fecha de radicación de la novedad (para indicar la fecha de registro)
     */
    private Long fechaRadicacion;
    /**
     * Estado de la solicitud de novedad
     */
    private EstadoSolicitudNovedadEnum estadoSolicitud;
    /**
     * Estado del empleador antes de la novedad
     */
    private EstadoEmpleadorEnum estadoEmpleadorAntes;
    /**
     * Estado del empleador después de la novedad
     */
    private EstadoEmpleadorEnum estadoEmpleadorDespues;
    /**
     * Fecha inicio de la vigencia de la novedad.
     */
    private Long fechaInicio;
    /**
     * Fecha fin de la vigencia de la novedad.
     */
    private Long fechaFin;
    /**
     * Número de radicación de la solicitud de novedad
     */
    private String numeroRadicacion;
    /**
     * Descripción del canal de recepción de las solicitudes
     */
    private CanalRecepcionEnum canalRecepcion;
    /**
     * Identificador de la solicitud global
     */
    private Long idSolicitudGlobal;

    public DatosNovedadEmpleadorDTO() {
    }

    /**
     * Constructor que recibe los datos desde una consulta nativa de base de datos.
     * @param novedad
     * @param fechaRadicacion
     * @param estadoSolicitud
     * @param estadoEmpleadorAntes
     * @param estadoEmpleadorDespues
     * @param fechaInicio
     * @param fechaFin
     * @param numeroRadicacion
     * @param canalRecepcion
     */
    public DatosNovedadEmpleadorDTO(String novedad, Date fechaRadicacion, String estadoSolicitud, String estadoEmpleadorAntes,
            String estadoEmpleadorDespues, Date fechaInicio, Date fechaFin, String numeroRadicacion, String canalRecepcion,
            BigInteger idSolicitudGlobal) {
        this.setNovedad(novedad != null ? TipoTransaccionEnum.valueOf(novedad) : null);
        this.setFechaRadicacion(fechaRadicacion != null ? fechaRadicacion.getTime() : null);
        this.setEstadoSolicitud(estadoSolicitud != null ? EstadoSolicitudNovedadEnum.valueOf(estadoSolicitud) : null);
        this.setEstadoEmpleadorAntes(estadoEmpleadorAntes != null ? EstadoEmpleadorEnum.valueOf(estadoEmpleadorAntes) : null);
        this.setEstadoEmpleadorDespues(estadoEmpleadorDespues != null ? EstadoEmpleadorEnum.valueOf(estadoEmpleadorDespues) : null);
        this.setFechaInicio(fechaInicio != null ? fechaInicio.getTime() : null);
        this.setFechaFin(fechaFin != null ? fechaFin.getTime() : null);
        this.setNumeroRadicacion(numeroRadicacion);
        this.setCanalRecepcion(canalRecepcion != null ? CanalRecepcionEnum.valueOf(canalRecepcion) : null);
        this.setIdSolicitudGlobal(idSolicitudGlobal.longValue());
    }

    /**
     * @return the novedad
     */
    public TipoTransaccionEnum getNovedad() {
        return novedad;
    }

    /**
     * @param novedad
     *        the novedad to set
     */
    public void setNovedad(TipoTransaccionEnum novedad) {
        this.novedad = novedad;
    }

    /**
     * @return the fechaRadicacion
     */
    public Long getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion
     *        the fechaRadicacion to set
     */
    public void setFechaRadicacion(Long fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudNovedadEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudNovedadEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the estadoEmpleadorAntes
     */
    public EstadoEmpleadorEnum getEstadoEmpleadorAntes() {
        return estadoEmpleadorAntes;
    }

    /**
     * @param estadoEmpleadorAntes
     *        the estadoEmpleadorAntes to set
     */
    public void setEstadoEmpleadorAntes(EstadoEmpleadorEnum estadoEmpleadorAntes) {
        this.estadoEmpleadorAntes = estadoEmpleadorAntes;
    }

    /**
     * @return the estadoEmpleadorDespues
     */
    public EstadoEmpleadorEnum getEstadoEmpleadorDespues() {
        return estadoEmpleadorDespues;
    }

    /**
     * @param estadoEmpleadorDespues
     *        the estadoEmpleadorDespues to set
     */
    public void setEstadoEmpleadorDespues(EstadoEmpleadorEnum estadoEmpleadorDespues) {
        this.estadoEmpleadorDespues = estadoEmpleadorDespues;
    }

    /**
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the canalRecepcion
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * @param canalRecepcion
     *        the canalRecepcion to set
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal
     *        the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

}
