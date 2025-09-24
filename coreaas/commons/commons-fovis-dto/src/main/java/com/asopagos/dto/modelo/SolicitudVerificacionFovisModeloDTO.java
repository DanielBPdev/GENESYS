package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.fovis.SolicitudVerificacionFovis;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum;
import com.asopagos.enumeraciones.fovis.TipoSolicitudVerificacionFovisEnum;

/**
 * DTO que contiene los datos relacionados a la entidad SolicitudVerificacionFovis
 * @author Alexander Quintero <alquintero@heinsohn.com.co>
 *
 */
public class SolicitudVerificacionFovisModeloDTO extends SolicitudModeloDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identificador único, llave primaria
     */
    private Long idSolicitudVerificacionFovis;
    /**
     * Asociación a la Postulación FOVIS
     */
    private Long idPostulacionFOVIS;
    /**
     * Estado de la solicitud de verificación fovis.
     */
    private EstadoSolicitudVerificacionFovisEnum estadoSolicitud;
    /**
     * Propiedad para determinar si es la verificación inicial o una re-verificación.
     */
    private TipoSolicitudVerificacionFovisEnum tipoVerificacion;
    /**
     * Observaciones de la solicitud de postulación en la verificación para control interno.
     */
    private String observaciones;

    /**
     * Resultado de la  postulación en la verificación para control interno.
     */
    private String resultado;

    /**
     * Constructor por defecto
     */
    public SolicitudVerificacionFovisModeloDTO() {
        super();
    }

    public SolicitudVerificacionFovisModeloDTO(SolicitudVerificacionFovis solicitudVerificacionFovis) {
        this.convertToDTO(solicitudVerificacionFovis);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * @return SolicitudVerificacionFovis
     */
    public SolicitudVerificacionFovis convertToEntity() {
        SolicitudVerificacionFovis solicitudVerificacionFovis = new SolicitudVerificacionFovis();
        solicitudVerificacionFovis.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudVerificacionFovis.setIdPostulacionFOVIS(this.getIdPostulacionFOVIS());
        solicitudVerificacionFovis.setIdSolicitudVerificacionFovis(this.getIdSolicitudVerificacionFovis());
        solicitudVerificacionFovis.setTipoVerificacion(this.getTipoVerificacion());
        solicitudVerificacionFovis.setObservaciones(this.getObservaciones());
        solicitudVerificacionFovis.setResultado(this.getResultado());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudVerificacionFovis.setSolicitudGlobal(solicitudGlobal);
        return solicitudVerificacionFovis;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * @param solicitudVerificacionFovis
     */
    public void convertToDTO(SolicitudVerificacionFovis solicitudVerificacionFovis) {
        if (solicitudVerificacionFovis.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudVerificacionFovis.getSolicitudGlobal());
        }
        this.setIdSolicitudVerificacionFovis(solicitudVerificacionFovis.getIdSolicitudVerificacionFovis());
        this.setIdPostulacionFOVIS(solicitudVerificacionFovis.getIdPostulacionFOVIS());
        this.setEstadoSolicitud(solicitudVerificacionFovis.getEstadoSolicitud());
        this.setTipoVerificacion(solicitudVerificacionFovis.getTipoVerificacion());
        this.setObservaciones(solicitudVerificacionFovis.getObservaciones());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * @param solicitudVerificacionFovis
     * @return
     */
    public SolicitudVerificacionFovis copyDTOToEntity(SolicitudVerificacionFovis solicitudVerificacionFovis) {
        if (this.getEstadoSolicitud() != null) {
            solicitudVerificacionFovis.setEstadoSolicitud(this.getEstadoSolicitud());
        }
        if (this.getIdPostulacionFOVIS() != null) {
            solicitudVerificacionFovis.setIdPostulacionFOVIS(this.getIdPostulacionFOVIS());
        }
        if (this.getIdSolicitudVerificacionFovis() != null) {
            solicitudVerificacionFovis.setIdSolicitudVerificacionFovis(this.getIdSolicitudVerificacionFovis());
        }
        if (this.getTipoVerificacion() != null) {
            solicitudVerificacionFovis.setTipoVerificacion(this.getTipoVerificacion());
        }
        if (this.getObservaciones() != null) {
            solicitudVerificacionFovis.setObservaciones(this.getObservaciones());
        }
        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudVerificacionFovis.getSolicitudGlobal());
        if (solicitudGlobal.getIdSolicitud() != null) {
            solicitudVerificacionFovis.setSolicitudGlobal(solicitudGlobal);
        }
        return solicitudVerificacionFovis;
    }

    /**
     * @return the idSolicitudVerificacionFovis
     */
    public Long getIdSolicitudVerificacionFovis() {
        return idSolicitudVerificacionFovis;
    }

    /**
     * @param idSolicitudVerificacionFovis
     *        the idSolicitudVerificacionFovis to set
     */
    public void setIdSolicitudVerificacionFovis(Long idSolicitudVerificacionFovis) {
        this.idSolicitudVerificacionFovis = idSolicitudVerificacionFovis;
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
    public EstadoSolicitudVerificacionFovisEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudVerificacionFovisEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the tipoVerificacion
     */
    public TipoSolicitudVerificacionFovisEnum getTipoVerificacion() {
        return tipoVerificacion;
    }

    /**
     * @param tipoVerificacion
     *        the tipoVerificacion to set
     */
    public void setTipoVerificacion(TipoSolicitudVerificacionFovisEnum tipoVerificacion) {
        this.tipoVerificacion = tipoVerificacion;
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

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
