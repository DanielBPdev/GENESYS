package com.asopagos.aportes.dto;

import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO con los datos de consulta y resultados del historico de aportes.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class SolicitudAporteHistoricoDTO {

    /**
     * Fecha de la consulta dada en formato MM/YYYY
     */
    private Long periodoConsulta;
   
    /**
     * Fecha de inicio de la consulta a realizar
     */
    private Long fechaInicioConsulta;
   
    /**
     * Fecha de inicio de la consulta a realizar
     */
    private Long fechaFinConsulta;
   
    /**
     * Tipo de identifiacion del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacion;
   
    /**
     * Número de numeroRadicacion del aportante
     */
    private String numeroIdentificacion;
   
    /**
     * Número de numeroRadicacion
     */
    private String numeroSolicitud;
    
    /**
     * Id de la solicitud.
     */
    private Long idSolicitud;
    
    /**
     * Estado de la solicitud.
     */
    private EstadoSolicitudAporteEnum estadoSolicitud;
    /**
     * Fecha de radicacion.
     */
    private Long fechaRadicacion;
    /**
     * Nombre del aportante.
     */
    private String nombreAportante;
    /**
     * Método que retorna el valor de nombreAportante.
     * @return valor de nombreAportante.
     */
    public String getNombreAportante() {
        return nombreAportante;
    }
    /**
     * Método encargado de modificar el valor de nombreAportante.
     * @param valor para modificar nombreAportante.
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }
    /**
     * Método que retorna el valor de periodoConsulta.
     * @return valor de periodoConsulta.
     */
    public Long getPeriodoConsulta() {
        return periodoConsulta;
    }
    /**
     * Método encargado de modificar el valor de periodoConsulta.
     * @param valor para modificar periodoConsulta.
     */
    public void setPeriodoConsulta(Long periodoConsulta) {
        this.periodoConsulta = periodoConsulta;
    }
    /**
     * Método que retorna el valor de fechaInicioConsulta.
     * @return valor de fechaInicioConsulta.
     */
    public Long getFechaInicioConsulta() {
        return fechaInicioConsulta;
    }
    /**
     * Método encargado de modificar el valor de fechaInicioConsulta.
     * @param valor para modificar fechaInicioConsulta.
     */
    public void setFechaInicioConsulta(Long fechaInicioConsulta) {
        this.fechaInicioConsulta = fechaInicioConsulta;
    }
    /**
     * Método que retorna el valor de fechaFinConsulta.
     * @return valor de fechaFinConsulta.
     */
    public Long getFechaFinConsulta() {
        return fechaFinConsulta;
    }
    /**
     * Método encargado de modificar el valor de fechaFinConsulta.
     * @param valor para modificar fechaFinConsulta.
     */
    public void setFechaFinConsulta(Long fechaFinConsulta) {
        this.fechaFinConsulta = fechaFinConsulta;
    }
    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }
    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
    /**
     * Método que retorna el valor de numeroSolicitud.
     * @return valor de numeroSolicitud.
     */
    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }
    /**
     * Método encargado de modificar el valor de numeroSolicitud.
     * @param valor para modificar numeroSolicitud.
     */
    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }
    /**
     * Método que retorna el valor de idSolicitud.
     * @return valor de idSolicitud.
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }
    /**
     * Método encargado de modificar el valor de idSolicitud.
     * @param valor para modificar idSolicitud.
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    /**
     * Método que retorna el valor de estadoSolicitud.
     * @return valor de estadoSolicitud.
     */
    public EstadoSolicitudAporteEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }
    /**
     * Método encargado de modificar el valor de estadoSolicitud.
     * @param valor para modificar estadoSolicitud.
     */
    public void setEstadoSolicitud(EstadoSolicitudAporteEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
    /**
     * Método que retorna el valor de fechaRadicacion.
     * @return valor de fechaRadicacion.
     */
    public Long getFechaRadicacion() {
        return fechaRadicacion;
    }
    /**
     * Método encargado de modificar el valor de fechaRadicacion.
     * @param valor para modificar fechaRadicacion.
     */
    public void setFechaRadicacion(Long fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }
    
  }
