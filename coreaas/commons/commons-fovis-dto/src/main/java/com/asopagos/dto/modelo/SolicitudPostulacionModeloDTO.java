package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.entidades.ccf.fovis.SolicitudPostulacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;

/**
 * DTO que contiene los datos relacionados a la entidad SolicitudPostulacion
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class SolicitudPostulacionModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4599092569265866146L;

    /**
     * Identificador único, llave primaria
     */
    private Long idSolicitudPostulacion;

    /**
     * Asociación a la Postulación FOVIS
     */
    private Long idPostulacionFOVIS;

    /**
     * Estado de la solicitud de postulación.
     */
    private EstadoSolicitudPostulacionEnum estadoSolicitud;

    /**
     * Contiene los datos de la postulación FOVIS
     */
    private PostulacionFOVISModeloDTO postulacionFOVISModeloDTO;

    /**
     * Observaciones de la solicitud de postulación para control interno.
     */
    private String observaciones;

    /**
     * Observaciones de la solicitud de postulación para el usuario web.
     */
    private String observacionesWeb;

    /**
     * Constructor por defecto
     */
    public SolicitudPostulacionModeloDTO() {
        super();
    }

    /**
     * Constructor para manejo de consultas
     * @param solicitudPostulacion Entidad solicitud postulacion
     * @param solicitud Entidad Solicitud
     */
    public SolicitudPostulacionModeloDTO(SolicitudPostulacion solicitudPostulacion, Solicitud solicitud) {
        super.convertToDTO(solicitud);
        this.setIdSolicitudPostulacion(solicitudPostulacion.getIdSolicitudPostulacion());
        this.setIdPostulacionFOVIS(solicitudPostulacion.getIdPostulacionFOVIS());
        this.setEstadoSolicitud(solicitudPostulacion.getEstadoSolicitud());
        this.setObservaciones(solicitudPostulacion.getObservaciones());
    }

    public SolicitudPostulacionModeloDTO(SolicitudPostulacion solicitudPostulacion) {
        this.convertToDTO(solicitudPostulacion);
    }

    public SolicitudPostulacionModeloDTO(SolicitudPostulacion solicitudPostulacion, PostulacionFOVISModeloDTO postulacionFOVISModeloDTO) {
        this.convertToDTO(solicitudPostulacion);
        this.setPostulacionFOVISModeloDTO(postulacionFOVISModeloDTO);
    }

    public SolicitudPostulacionModeloDTO(SolicitudPostulacion solicitudPostulacion, PostulacionFOVIS postulacionFOVIS) {
        this.convertToDTO(solicitudPostulacion);
        this.setPostulacionFOVISModeloDTO(new PostulacionFOVISModeloDTO(postulacionFOVIS));
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * @return SolicitudPostulacion
     */
    public SolicitudPostulacion convertToEntity() {
        SolicitudPostulacion solicitudPostulacion = new SolicitudPostulacion();
        solicitudPostulacion.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudPostulacion.setIdPostulacionFOVIS(this.getIdPostulacionFOVIS());
        solicitudPostulacion.setIdSolicitudPostulacion(this.getIdSolicitudPostulacion());
        solicitudPostulacion.setObservaciones(this.getObservaciones());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudPostulacion.setSolicitudGlobal(solicitudGlobal);
        return solicitudPostulacion;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * @param solicitudPostulacion
     */
    public void convertToDTO(SolicitudPostulacion solicitudPostulacion) {
        if (solicitudPostulacion.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudPostulacion.getSolicitudGlobal());
        }
        this.setIdSolicitudPostulacion(solicitudPostulacion.getIdSolicitudPostulacion());
        this.setIdPostulacionFOVIS(solicitudPostulacion.getIdPostulacionFOVIS());
        this.setEstadoSolicitud(solicitudPostulacion.getEstadoSolicitud());
        this.setObservaciones(solicitudPostulacion.getObservaciones());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * @param solicitudPostulacion
     * @return
     */
    public SolicitudPostulacion copyDTOToEntity(SolicitudPostulacion solicitudPostulacion) {
        if (this.getEstadoSolicitud() != null) {
            solicitudPostulacion.setEstadoSolicitud(this.getEstadoSolicitud());
        }
        if (this.getIdPostulacionFOVIS() != null) {
            solicitudPostulacion.setIdPostulacionFOVIS(this.getIdPostulacionFOVIS());
        }
        if (this.getIdSolicitudPostulacion() != null) {
            solicitudPostulacion.setIdSolicitudPostulacion(this.getIdSolicitudPostulacion());
        }
        if (this.getObservaciones() != null) {
            solicitudPostulacion.setObservaciones(this.getObservaciones());
        }
        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudPostulacion.getSolicitudGlobal());
        if (solicitudGlobal.getIdSolicitud() != null) {
            solicitudPostulacion.setSolicitudGlobal(solicitudGlobal);
        }
        return solicitudPostulacion;
    }

    /**
     * @return the idSolicitudPostulacion
     */
    public Long getIdSolicitudPostulacion() {
        return idSolicitudPostulacion;
    }

    /**
     * @param idSolicitudPostulacion
     *        the idSolicitudPostulacion to set
     */
    public void setIdSolicitudPostulacion(Long idSolicitudPostulacion) {
        this.idSolicitudPostulacion = idSolicitudPostulacion;
    }

    /**
     * @return the idPostulacionFOVIS
     */
    public Long getIdPostulacionFOVIS() {
        return idPostulacionFOVIS;
    }

    /**
     * @param idPostulacionFOVIS
     *        the idPostulacionFOVIS to set
     */
    public void setIdPostulacionFOVIS(Long idPostulacionFOVIS) {
        this.idPostulacionFOVIS = idPostulacionFOVIS;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudPostulacionEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudPostulacionEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the postulacionFOVISModeloDTO
     */
    public PostulacionFOVISModeloDTO getPostulacionFOVISModeloDTO() {
        return postulacionFOVISModeloDTO;
    }

    /**
     * @param postulacionFOVISModeloDTO
     *        the postulacionFOVISModeloDTO to set
     */
    public void setPostulacionFOVISModeloDTO(PostulacionFOVISModeloDTO postulacionFOVISModeloDTO) {
        this.postulacionFOVISModeloDTO = postulacionFOVISModeloDTO;
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
     * @return the observacionesWeb
     */
    public String getObservacionesWeb() {
        return observacionesWeb;
    }

    /**
     * @param observacionesWeb
     *        the observacionesWeb to set
     */
    public void setObservacionesWeb(String observacionesWeb) {
        this.observacionesWeb = observacionesWeb;
    }


    @Override
    public String toString() {
        return "{" +
            " idSolicitudPostulacion='" + getIdSolicitudPostulacion() + "'" +
            ", idPostulacionFOVIS='" + getIdPostulacionFOVIS() + "'" +
            ", estadoSolicitud='" + getEstadoSolicitud() + "'" +
            ", postulacionFOVISModeloDTO='" + getPostulacionFOVISModeloDTO() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", observacionesWeb='" + getObservacionesWeb() + "'" +
            "}";
    }

}
