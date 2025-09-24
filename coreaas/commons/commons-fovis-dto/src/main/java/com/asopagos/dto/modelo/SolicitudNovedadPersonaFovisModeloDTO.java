package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.fovis.SolicitudNovedadFovis;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;

/**
 * DTO que contiene los datos relacionados a la entidad SolicitudNovedadFovis
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 *
 */
public class SolicitudNovedadPersonaFovisModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -5563170124806236674L;

    /**
     * Identificador único, llave primaria
     */
    private Long idSolicitudNovedadFovis;

    /**
     * Asociación a la Postulación FOVIS
     */
    private Long idSolicitudGlobal;

    /**
     * Estado de la solicitud de postulación.
     */
    private EstadoSolicitudNovedadFovisEnum estadoSolicitud;

    /**
     * Id de la novedad.
     */
    private Long idParametrizacionNovedad;

    /**
     * Observaciones de la solicitud de postulación para control interno.
     */
    private String observaciones;

    public SolicitudNovedadPersonaFovisModeloDTO() {
    }

    public SolicitudNovedadPersonaFovisModeloDTO(SolicitudNovedadFovis SolicitudNovedadFovis) {
        this.convertToDTO(SolicitudNovedadFovis);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * @return solicitudNovedadFovis
     */
    public SolicitudNovedadFovis convertToEntity() {
        SolicitudNovedadFovis solicitudNovedadFovis = new SolicitudNovedadFovis();
        solicitudNovedadFovis.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudNovedadFovis.setIdSolicitudNovedadFovis(this.getIdSolicitudNovedadFovis());
        solicitudNovedadFovis.setIdParametrizacionNovedad(this.getIdParametrizacionNovedad());
        solicitudNovedadFovis.setObservaciones(this.getObservaciones());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudNovedadFovis.setSolicitudGlobal(solicitudGlobal);
        return solicitudNovedadFovis;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * @param solicitudNovedadFovis
     */
    public void convertToDTO(SolicitudNovedadFovis solicitudNovedadFovis) {
        if (solicitudNovedadFovis.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudNovedadFovis.getSolicitudGlobal());
        }

        this.setIdSolicitudNovedadFovis(solicitudNovedadFovis.getIdSolicitudNovedadFovis());
        this.setIdParametrizacionNovedad(solicitudNovedadFovis.getIdParametrizacionNovedad());
        this.setEstadoSolicitud(solicitudNovedadFovis.getEstadoSolicitud());
        this.setObservaciones(solicitudNovedadFovis.getObservaciones());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * @param solicitudNovedadFovis
     * @return
     */
    public SolicitudNovedadFovis copyDTOToEntity(SolicitudNovedadFovis solicitudNovedadFovis) {
        if (this.getEstadoSolicitud() != null) {
            solicitudNovedadFovis.setEstadoSolicitud(this.getEstadoSolicitud());
        }
        if (this.getIdParametrizacionNovedad() != null) {
            solicitudNovedadFovis.setIdParametrizacionNovedad(this.getIdParametrizacionNovedad());
        }
        if (this.getIdSolicitudNovedadFovis() != null) {
            solicitudNovedadFovis.setIdSolicitudNovedadFovis(this.getIdSolicitudNovedadFovis());
        }
        if (this.getObservaciones() != null) {
            solicitudNovedadFovis.setObservaciones(this.getObservaciones());
        }
        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudNovedadFovis.getSolicitudGlobal());
        if (solicitudGlobal.getIdSolicitud() != null) {
            solicitudNovedadFovis.setSolicitudGlobal(solicitudGlobal);
        }
        return solicitudNovedadFovis;
    }

    /**
     * @return the idSolicitudNovedadFovis
     */
    public Long getIdSolicitudNovedadFovis() {
        return idSolicitudNovedadFovis;
    }

    /**
     * @param idSolicitudNovedadFovis
     *        the idSolicitudNovedadFovis to set
     */
    public void setIdSolicitudNovedadFovis(Long idSolicitudNovedadFovis) {
        this.idSolicitudNovedadFovis = idSolicitudNovedadFovis;
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

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudNovedadFovisEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
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
     * @return the estadoSolicitud
     */
    public EstadoSolicitudNovedadFovisEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @return the idParametrizacionNovedad
     */
    public Long getIdParametrizacionNovedad() {
        return idParametrizacionNovedad;
    }

    /**
     * @param idParametrizacionNovedad
     *        the idParametrizacionNovedad to set
     */
    public void setIdParametrizacionNovedad(Long idParametrizacionNovedad) {
        this.idParametrizacionNovedad = idParametrizacionNovedad;
    }

}
