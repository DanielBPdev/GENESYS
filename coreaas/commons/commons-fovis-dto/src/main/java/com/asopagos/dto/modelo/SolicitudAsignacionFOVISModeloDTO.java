package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.asopagos.entidades.ccf.fovis.SolicitudAsignacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAsignacionEnum;

/**
 * DTO que representa los datos de la entidad ResultadosAsignacionFOVIS
 * 
 * @author Edward Castano <ecastano@heinsohn.com.co>
 *
 */
public class SolicitudAsignacionFOVISModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 4159967592223549957L;

    /**
     * Identificador de la solicitud de asignacion creada
     */
    private Long idSolicitudAsignacion;

    /**
     * Fecha de aceptacion de resultados para el ciclo de asignación seleccionado
     */
    private Long fechaAceptacion;

    /**
     * Usuario que aprueba
     */
    private String usuario;

    /**
     * Valor asignado subsidio
     */
    private BigDecimal valorSFVAsignado;

    /**
     * Estado de la solicitud de asignacion
     */
    private EstadoSolicitudAsignacionEnum estadoSolicitudAsignacion;

    /**
     * Identificador del ciclo de asignacion al cual esta asociada la solicitud de asignacion
     */
    private Long idCicloAsignacion;

    /**
     * Comentarios del coordinador FOVIS
     */
    private String comentarios;

    /**
     * Comentarios del Contol interno
     */
    private String comentarioControlInterno;

    /**
     * Lista de Hogares asociados a la solictud de asignacion
     */
    private List<PostulacionFOVISModeloDTO> listadoPostulacionesHabiles = new ArrayList<PostulacionFOVISModeloDTO>();

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return ResultadosAsignacionFOVIS
     */
    public SolicitudAsignacion convertToEntity() {
        SolicitudAsignacion solicitudAsignacionFOVIS = new SolicitudAsignacion();
        solicitudAsignacionFOVIS.setFechaAceptacion(this.getFechaAceptacion() != null ? new Date(this.getFechaAceptacion()) : null);
        solicitudAsignacionFOVIS.setUsuario(this.getUsuario());
        solicitudAsignacionFOVIS.setComentarios(this.getComentarios());
        solicitudAsignacionFOVIS.setValorSFVAsignado(this.getValorSFVAsignado());
        solicitudAsignacionFOVIS.setEstadoSolicitudAsignacion(this.getEstadoSolicitudAsignacion());
        solicitudAsignacionFOVIS.setIdCicloAsignacion(this.getIdCicloAsignacion());
        solicitudAsignacionFOVIS.setComentarioControlInterno(this.getComentarioControlInterno());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudAsignacionFOVIS.setSolicitudGlobal(solicitudGlobal);
        solicitudAsignacionFOVIS.setIdSolicitudAsignacion(this.getIdSolicitudAsignacion());
        return solicitudAsignacionFOVIS;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * 
     * @param ResultadosAsignacionFOVIS
     */
    public void convertToDTO(SolicitudAsignacion solicitudAsignacionFOVIS) {
        if(solicitudAsignacionFOVIS.getSolicitudGlobal()!=null){
            super.convertToDTO(solicitudAsignacionFOVIS.getSolicitudGlobal());
        }
        this.setComentarios(solicitudAsignacionFOVIS.getComentarios());
        this.setFechaAceptacion(
                solicitudAsignacionFOVIS.getFechaAceptacion() != null ? solicitudAsignacionFOVIS.getFechaAceptacion().getTime() : null);
        this.setUsuario(solicitudAsignacionFOVIS.getUsuario());
        this.setValorSFVAsignado(solicitudAsignacionFOVIS.getValorSFVAsignado());
        this.setEstadoSolicitudAsignacion(solicitudAsignacionFOVIS.getEstadoSolicitudAsignacion());
        this.setIdCicloAsignacion(solicitudAsignacionFOVIS.getIdCicloAsignacion());
        this.setComentarioControlInterno(solicitudAsignacionFOVIS.getComentarioControlInterno());
        this.setIdSolicitudAsignacion(solicitudAsignacionFOVIS.getIdSolicitudAsignacion());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * 
     * @param persona
     *        resultadosAsignacionFOVIS consultada.
     */
    public SolicitudAsignacion copyDTOToEntity(SolicitudAsignacion solicitudAsignacionFOVIS) {
        if (this.getComentarios() != null) {
            solicitudAsignacionFOVIS.setComentarios(this.getComentarios());
        }

        if (this.getFechaAceptacion() != null) {
            solicitudAsignacionFOVIS.setFechaAceptacion(this.getFechaAceptacion() != null ? new Date(this.getFechaAceptacion()) : null);
        }

        if (this.getUsuario() != null) {
            solicitudAsignacionFOVIS.setUsuario(this.getUsuario());
        }
        if (this.getValorSFVAsignado() != null) {
            solicitudAsignacionFOVIS.setValorSFVAsignado(this.getValorSFVAsignado());
        }

        if (this.getEstadoSolicitudAsignacion() != null) {
            solicitudAsignacionFOVIS.setEstadoSolicitudAsignacion(this.getEstadoSolicitudAsignacion());
        }

        if (this.getIdCicloAsignacion() != null) {
            solicitudAsignacionFOVIS.setIdCicloAsignacion(this.getIdCicloAsignacion());
        }

        if (this.getComentarioControlInterno() != null) {
            solicitudAsignacionFOVIS.setComentarioControlInterno(this.getComentarioControlInterno());
        }

        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudAsignacionFOVIS.getSolicitudGlobal());
        if(solicitudGlobal.getIdSolicitud()!=null){
            solicitudAsignacionFOVIS.setSolicitudGlobal(solicitudGlobal);
        }
        return solicitudAsignacionFOVIS;
    }

    /**
     * @return the fechaAceptacion
     */
    public Long getFechaAceptacion() {
        return fechaAceptacion;
    }

    /**
     * @param fechaAceptacion
     *        the fechaAceptacion to set
     */
    public void setFechaAceptacion(Long fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *        the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the valorSFVAsignado
     */
    public BigDecimal getValorSFVAsignado() {
        return valorSFVAsignado;
    }

    /**
     * @param valorSFVAsignado
     *        the valorSFVAsignado to set
     */
    public void setValorSFVAsignado(BigDecimal valorSFVAsignado) {
        this.valorSFVAsignado = valorSFVAsignado;
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
     * @return the idSolicitudAsignacion
     */
    public Long getIdSolicitudAsignacion() {
        return idSolicitudAsignacion;
    }

    /**
     * @param idSolicitudAsignacion
     *        the idSolicitudAsignacion to set
     */
    public void setIdSolicitudAsignacion(Long idSolicitudAsignacion) {
        this.idSolicitudAsignacion = idSolicitudAsignacion;
    }

    /**
     * @return the listadoPostulacionesHabiles
     */
    public List<PostulacionFOVISModeloDTO> getListadoPostulacionesHabiles() {
        return listadoPostulacionesHabiles;
    }

    /**
     * @param listadoPostulacionesHabiles
     *        the listadoPostulacionesHabiles to set
     */
    public void setListadoPostulacionesHabiles(List<PostulacionFOVISModeloDTO> listadoPostulacionesHabiles) {
        this.listadoPostulacionesHabiles = listadoPostulacionesHabiles;
    }

    /**
     * @return the estadoSolicitudAsignacion
     */
    public EstadoSolicitudAsignacionEnum getEstadoSolicitudAsignacion() {
        return estadoSolicitudAsignacion;
    }

    /**
     * @param estadoSolicitudAsignacion
     *        the estadoSolicitudAsignacion to set
     */
    public void setEstadoSolicitudAsignacion(EstadoSolicitudAsignacionEnum estadoSolicitudAsignacion) {
        this.estadoSolicitudAsignacion = estadoSolicitudAsignacion;
    }

    /**
     * @return the idCicloAsignacion
     */
    public Long getIdCicloAsignacion() {
        return idCicloAsignacion;
    }

    /**
     * @param idCicloAsignacion
     *        the idCicloAsignacion to set
     */
    public void setIdCicloAsignacion(Long idCicloAsignacion) {
        this.idCicloAsignacion = idCicloAsignacion;
    }

    /**
     * @return the comentariosControlInterno
     */
    public String getComentarioControlInterno() {
        return comentarioControlInterno;
    }

    /**
     * @param comentariosControlInterno
     *        the comentariosControlInterno to set
     */
    public void setComentarioControlInterno(String comentarioControlInterno) {
        this.comentarioControlInterno = comentarioControlInterno;
    }

}
