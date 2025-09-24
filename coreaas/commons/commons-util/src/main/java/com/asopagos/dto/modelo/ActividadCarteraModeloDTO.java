package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.asopagos.entidades.ccf.cartera.ActividadCartera;
import com.asopagos.enumeraciones.cartera.ActividadCicloCarteraEnum;
import com.asopagos.enumeraciones.cartera.ResultadoCicloCarteraEnum;

/**
 * Representa la actividad que se realizara durante la visita al aportante
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ActividadCarteraModeloDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 2624119232501333501L;
    /**
     * Identificador de la actividad de fiscalización
     */
    private Long idActividadFiscalizacion;
    /**
     * Representa el valor de la actividad que se realizara durante la visita al
     * aportante
     */
    @NotNull
    private ActividadCicloCarteraEnum actividadFiscalizacion;
    /**
     * Representa los resultados de la fiscalización durante la vista al
     * aportante
     */
    private ResultadoCicloCarteraEnum resutadoFiscalizacion;
    /**
     * Comentarios de la actividad
     */
    private String comentarios;
    /**
     * lista de la tabla intermedia actividad documento
     */
    private List<ActividadDocumentoModeloDTO> actividadDocumentoModeloDTOs;

    /**
     * Identificador único del ciclo del aportante
     */
    private Long idCicloAportante;

    /**
     * Fecha de creacion de la actividad
     */
    private Long fecha;
    /**
     * Fecha de compromiso.
     */
    private Long fechaCompromiso;

    /**
     * Número de operación de cartera
     */
    private Long idCartera;

    /**
     * Bandera para registrar nuevas actividades en bitácora
     */
    private Boolean esNuevaActividad;

    /**
     * Observaciones de la actividad
     */
    private String observaciones;

    /**
     * Valor que permite identificar cuando la agenda de la cartera debe estar visible en la pestaña gestión para solicitudes manuales o del
     * aportante
     */
    private Boolean esVisible;

    /**
     * Método constructor
     */
    public ActividadCarteraModeloDTO() {
    }

    /**
     * 
     * @param actividad
     */
    public ActividadCarteraModeloDTO(ActividadCartera actividad) {
        convertToDTO(actividad);
    }

    /**
     * Metodo que sirve para convertir de entidad a DTO
     * 
     * @param actividadFiscalizacion
     *        recibe como parametro la entidad ActividadFiscalizacion
     */
    public void convertToDTO(ActividadCartera actividadFiscalizacion) {
        /*
         * Se setean los datos para el DTO ActividadDocumentoModeloDTO
         */
        this.setActividadFiscalizacion(actividadFiscalizacion.getActividadCicloCartera());
        this.setResutadoFiscalizacion(actividadFiscalizacion.getResutadoCartera());
        this.setComentarios(actividadFiscalizacion.getComentarios());
        this.setIdCicloAportante(actividadFiscalizacion.getIdCicloAportante());
        this.setFecha(actividadFiscalizacion.getFecha() != null ? actividadFiscalizacion.getFecha().getTime(): null);
        this.setFechaCompromiso(
                actividadFiscalizacion.getFechaCompromiso() != null ? actividadFiscalizacion.getFechaCompromiso().getTime() : null);
        this.setIdCartera(actividadFiscalizacion.getIdCartera());
        this.setObservaciones(actividadFiscalizacion.getObservaciones());
        this.setEsVisible(actividadFiscalizacion.getEsVisible());
        this.setIdActividadFiscalizacion(actividadFiscalizacion.getIdActividadFiscalizacion());
    }

    /**
     * Metodo que sirve para convertir de DTO a entidad
     * 
     * @return un objeto ActividadFiscalizacion
     */
    public ActividadCartera convertToEntity() {
        /*
         * Se setean los datos para la entididad actividadFiscalizacion
         */
        ActividadCartera actividadCartera = new ActividadCartera();
        actividadCartera.setActividadCicloCartera(this.getActividadFiscalizacion());
        actividadCartera.setResultadoCartera(this.getResutadoFiscalizacion());
        actividadCartera.setComentarios(this.getComentarios());
        actividadCartera.setIdCicloAportante(this.getIdCicloAportante());
        actividadCartera.setFecha(this.getFecha() != null ? new Date(this.getFecha()) : null);
        actividadCartera.setFechaCompromiso(this.getFechaCompromiso() != null ? new Date(this.getFechaCompromiso()) : null);
        actividadCartera.setIdCartera(this.getIdCartera());
        actividadCartera.setObservaciones(this.getObservaciones());
        actividadCartera.setEsVisible(this.getEsVisible());
        actividadCartera.setIdActividadFiscalizacion(this.getIdActividadFiscalizacion());
        return actividadCartera;
    }

    /**
     * @return the actividadFiscalizacion
     */
    public ActividadCicloCarteraEnum getActividadFiscalizacion() {
        return actividadFiscalizacion;
    }

    /**
     * @param actividadFiscalizacion
     *        the actividadFiscalizacion to set
     */
    public void setActividadFiscalizacion(ActividadCicloCarteraEnum actividadFiscalizacion) {
        this.actividadFiscalizacion = actividadFiscalizacion;
    }

    /**
     * @return the resutadoFiscalizacion
     */
    public ResultadoCicloCarteraEnum getResutadoFiscalizacion() {
        return resutadoFiscalizacion;
    }

    /**
     * @param resutadoFiscalizacion
     *        the resutadoFiscalizacion to set
     */
    public void setResutadoFiscalizacion(ResultadoCicloCarteraEnum resutadoFiscalizacion) {
        this.resutadoFiscalizacion = resutadoFiscalizacion;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios
     *        the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Método que retorna el valor de actividadDocumentoModeloDTOs.
     * 
     * @return valor de actividadDocumentoModeloDTOs.
     */
    public List<ActividadDocumentoModeloDTO> getActividadDocumentoModeloDTOs() {
        return actividadDocumentoModeloDTOs;
    }

    /**
     * Método encargado de modificar el valor de actividadDocumentoModeloDTOs.
     * 
     * @param valor
     *        para modificar actividadDocumentoModeloDTOs.
     */
    public void setActividadDocumentoModeloDTOs(List<ActividadDocumentoModeloDTO> actividadDocumentoModeloDTOs) {
        this.actividadDocumentoModeloDTOs = actividadDocumentoModeloDTOs;
    }

    /**
     * Método que retorna el valor de idCicloAportante.
     * 
     * @return valor de idCicloAportante.
     */
    public Long getIdCicloAportante() {
        return idCicloAportante;
    }

    /**
     * Método encargado de modificar el valor de idCicloAportante.
     * 
     * @param valor
     *        para modificar idCicloAportante.
     */
    public void setIdCicloAportante(Long idCicloAportante) {
        this.idCicloAportante = idCicloAportante;
    }

    /**
     * Método que retorna el valor de fecha.
     * 
     * @return valor de fecha.
     */
    public Long getFecha() {
        return fecha;
    }

    /**
     * Método encargado de modificar el valor de fecha.
     * 
     * @param valor
     *        para modificar fecha.
     */
    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    /**
     * Método que retorna el valor de idActividadFiscalizacion.
     * 
     * @return valor de idActividadFiscalizacion.
     */
    public Long getIdActividadFiscalizacion() {
        return idActividadFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de idActividadFiscalizacion.
     * 
     * @param valor
     *        para modificar idActividadFiscalizacion.
     */
    public void setIdActividadFiscalizacion(Long idActividadFiscalizacion) {
        this.idActividadFiscalizacion = idActividadFiscalizacion;
    }

    /**
     * Método que retorna el valor de fechaCompromiso.
     * 
     * @return valor de fechaCompromiso.
     */
    public Long getFechaCompromiso() {
        return fechaCompromiso;
    }

    /**
     * Método encargado de modificar el valor de fechaCompromiso.
     * 
     * @param valor
     *        para modificar fechaCompromiso.
     */
    public void setFechaCompromiso(Long fechaCompromiso) {
        this.fechaCompromiso = fechaCompromiso;
    }

    /**
     * Obtiene el valor de idCartera
     * 
     * @return El valor de idCartera
     */
    public Long getIdCartera() {
        return idCartera;
    }

    /**
     * Establece el valor de idCartera
     * 
     * @param idCartera
     *        El valor de idCartera por asignar
     */
    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    /**
     * Obtiene el valor de esNuevaActividad
     * @return El valor de esNuevaActividad
     */
    public Boolean getEsNuevaActividad() {
        return esNuevaActividad;
    }

    /**
     * Establece el valor de esNuevaActividad
     * @param esNuevaActividad
     *        El valor de esNuevaActividad por asignar
     */
    public void setEsNuevaActividad(Boolean esNuevaActividad) {
        this.esNuevaActividad = esNuevaActividad;
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
     * @return the esVisible
     */
    public Boolean getEsVisible() {
        return esVisible;
    }

    /**
     * @param esVisible
     *        the esVisible to set
     */
    public void setEsVisible(Boolean esVisible) {
        this.esVisible = esVisible;
    }

}
