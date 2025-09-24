package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.List;
import com.asopagos.entidades.ccf.cartera.SolicitudDesafiliacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudDesafiliacionEnum;

/**
 * <b>Descripcion:</b> Clase que representa en modo de DTO a la
 * entidad SolicitudDesafiliacion<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class SolicitudDesafiliacionModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * Serial id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de la Solicitud de Desafiliacion
     */
    private Long idSolicitudDesafiliacion;

    /**
     * Estado de la solicitud
     */
    private EstadoSolicitudDesafiliacionEnum estadoSolicitud;

    /**
     * Comentario del analista
     */
    private String comentarioCoordinador;
    
    /**
     * Lista con los aportantes que entran al proceso de desafiliacion
     */
    private List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs;
    
    /**
     * Lista con los aportantes que entran al proceso de desafiliacion
     */
    private List<DocumentoDesafiliacionModeloDTO> documentoDesafiliacionModeloDTOs;

    /**
     * Constructor de la clase
     */
    public SolicitudDesafiliacionModeloDTO() {
    }

    /**
     * Metodo que se encarga de convertir de entidad a DTO
     * @param solicitudDesafiliacion
     *        recibe la entidad SolicitudDesafiliacion
     * @return un DTO de tipo SolicitudDesafiliacionModeloDTO
     */
    public SolicitudDesafiliacionModeloDTO convertToDTO(SolicitudDesafiliacion solicitudDesafiliacion) {
        /* Se construye objeto */
        this.setIdSolicitudDesafiliacion(
                solicitudDesafiliacion.getIdSolicitudDesafiliacion() != null ? solicitudDesafiliacion.getIdSolicitudDesafiliacion() : null);
        if (solicitudDesafiliacion.getSolicitudGlobal() != null) {
            /* Se setea la información para SolicitudModeloDTO */
            super.convertToDTO(solicitudDesafiliacion.getSolicitudGlobal());
        }
        this.setComentarioCoordinador(
                solicitudDesafiliacion.getComentarioCoordinador() != null ? solicitudDesafiliacion.getComentarioCoordinador() : null);
        this.setEstadoSolicitud(solicitudDesafiliacion.getEstadoSolicitud() != null ? solicitudDesafiliacion.getEstadoSolicitud() : null);
        return this;
    }

    /**
     * Metodo que se encarga de convertir DTO a Entity
     * @return una entity
     */
    public SolicitudDesafiliacion convertToEntity() {
        /* Se construye el objeto */
        SolicitudDesafiliacion solicitudDesafiliacion = new SolicitudDesafiliacion();
        solicitudDesafiliacion
                .setIdSolicitudDesafiliacion(this.getIdSolicitudDesafiliacion() != null ? this.getIdSolicitudDesafiliacion() : null);
        Solicitud solicitud = super.convertToSolicitudEntity();
        solicitudDesafiliacion.setSolicitudGlobal(solicitud);
        solicitudDesafiliacion.setComentarioCoordinador(this.getComentarioCoordinador() != null ? this.getComentarioCoordinador() : null);
        solicitudDesafiliacion.setEstadoSolicitud(this.getEstadoSolicitud() != null ? this.getEstadoSolicitud() : null);
        return solicitudDesafiliacion;
    }

    /**
     * Método que retorna el valor de estadoSolicitud.
     * @return valor de estadoSolicitud.
     */
    public EstadoSolicitudDesafiliacionEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * Método encargado de modificar el valor de estadoSolicitud.
     * @param valor
     *        para modificar estadoSolicitud.
     */
    public void setEstadoSolicitud(EstadoSolicitudDesafiliacionEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * Método que retorna el valor de comentarioAnalista.
     * @return valor de comentarioAnalista.
     */
    public String getComentarioCoordinador() {
        return comentarioCoordinador;
    }

    /**
     * Método encargado de modificar el valor de comentarioAnalista.
     * @param valor
     *        para modificar comentarioAnalista.
     */
    public void setComentarioCoordinador(String comentarioAnalista) {
        this.comentarioCoordinador = comentarioAnalista;
    }

    /**
     * Método que retorna el valor de idSolicitudDesafiliacion.
     * @return valor de idSolicitudDesafiliacion.
     */
    public Long getIdSolicitudDesafiliacion() {
        return idSolicitudDesafiliacion;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudDesafiliacion.
     * @param valor
     *        para modificar idSolicitudDesafiliacion.
     */
    public void setIdSolicitudDesafiliacion(Long idSolicitudDesafiliacion) {
        this.idSolicitudDesafiliacion = idSolicitudDesafiliacion;
    }

    /**
     * Método que retorna el valor de desafiliacionAportanteDTOs.
     * @return valor de desafiliacionAportanteDTOs.
     */
    public List<DesafiliacionAportanteDTO> getAportanteDesafiliacionDTOs() {
        return desafiliacionAportanteDTOs;
    }

    /**
     * Método encargado de modificar el valor de desafiliacionAportanteDTOs.
     * @param valor para modificar desafiliacionAportanteDTOs.
     */
    public void setAportanteDesafiliacionDTOs(List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs) {
        this.desafiliacionAportanteDTOs = desafiliacionAportanteDTOs;
    }

    /**
     * Método que retorna el valor de desafiliacionModeloDTOs.
     * @return valor de desafiliacionModeloDTOs.
     */
    public List<DocumentoDesafiliacionModeloDTO> getDocumentoDesafiliacionModeloDTOs() {
        return documentoDesafiliacionModeloDTOs;
    }

    /**
     * Método encargado de modificar el valor de desafiliacionModeloDTOs.
     * @param valor para modificar desafiliacionModeloDTOs.
     */
    public void setDocumentoDesafiliacionModeloDTOs(List<DocumentoDesafiliacionModeloDTO> documentoDesafiliacionModeloDTOs) {
        this.documentoDesafiliacionModeloDTOs = documentoDesafiliacionModeloDTOs;
    }

}
