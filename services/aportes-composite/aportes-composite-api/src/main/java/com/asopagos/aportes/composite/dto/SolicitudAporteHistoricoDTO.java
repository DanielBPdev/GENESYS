package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO con los datos de consulta y resultados del historico de aportes.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudAporteHistoricoDTO implements Serializable{

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
    private TipoIdentificacionEnum tipoIdAportante;
    
    /**
     * Número de numeroRadicacion del aportante
     */
    private String numeroIdAportante;
    
    /**
     * Número de numeroRadicacion
     */
    private String numeroRadicacion;
    
    /**
     * Fecha radicación
     */
    private Long fechaRadicacion;
    
    /**
     * Estado de solicitud.
     */
    private EstadoSolicitudAporteEnum estadoSolicitud;
    
    /**
     * Id de la solicitud.
     */
    private Long idSolicitud;

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
     * Método que retorna el valor de tipoIdAportante.
     * @return valor de tipoIdAportante.
     */
    public TipoIdentificacionEnum getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdAportante.
     * @param valor para modificar tipoIdAportante.
     */
    public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * Método que retorna el valor de numeroIdAportante.
     * @return valor de numeroIdAportante.
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }

    /**
     * Método encargado de modificar el valor de numeroIdAportante.
     * @param valor para modificar numeroIdAportante.
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    /**
     * Método que retorna el valor de numeroRadicacion.
     * @return valor de numeroRadicacion.
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * Método encargado de modificar el valor de numeroRadicacion.
     * @param valor para modificar numeroRadicacion.
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
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
}
