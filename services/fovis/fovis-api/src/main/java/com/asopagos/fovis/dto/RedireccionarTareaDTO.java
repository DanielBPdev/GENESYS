package com.asopagos.fovis.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción</b> DTO que representa los datos que debe llevar la consulta de un postulante a FOVIS.
 * <b>HU-032</b>
 * @author <a href="mailto:criparra@heinsohn.com.co">Cristian David Parra Zuluaga</a>
 */
@XmlRootElement
public class RedireccionarTareaDTO implements Serializable {

    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = -7671976893350969838L;

    /**
     * idTarea.
     */
    private Integer idTarea;

    /**
     * ruleNav.
     */
    private String ruleNav;

    /**
     * idInstanciaProceso.
     */
    private Long idInstanciaProceso;

    /**
     * fechaActivacion.
     */
    private Date fechaActivacion;

    /**
     * idSolicitud.
     */
    private Long idSolicitud;

    /**
     * numeroRadicado.
     */
    private String numeroRadicado;

    /**
     * numeroRadicado.
     */
    private String tipoTransaccion;

    public RedireccionarTareaDTO(Integer idTarea, String ruleNav, Long idInstanciaProceso, Date fechaActivacion, Long idSolicitud, String numeroRadicado, String tipoTransaccion) {
        this.idTarea = idTarea;
        this.ruleNav = ruleNav;
        this.idInstanciaProceso = idInstanciaProceso;
        this.fechaActivacion = fechaActivacion;
        this.idSolicitud = idSolicitud;
        this.numeroRadicado = numeroRadicado;
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * Método constructor para devolver los datos consultados relacionados a la lista de postulantes a FOVIS.
     */

    public RedireccionarTareaDTO() {

    }

    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public String getRuleNav() {
        return ruleNav;
    }

    public void setRuleNav(String ruleNav) {
        this.ruleNav = ruleNav;
    }

    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    public Date getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(Date fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
}
