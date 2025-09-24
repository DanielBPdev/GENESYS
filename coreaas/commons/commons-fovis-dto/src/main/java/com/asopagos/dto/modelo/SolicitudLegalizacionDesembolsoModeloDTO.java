package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.fovis.LegalizacionDesembolso;
import com.asopagos.entidades.ccf.fovis.SolicitudLegalizacionDesembolso;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;

/**
 * DTO que contiene los datos relacionados a la entidad SolicitudLegalizacionDesembolso
 * @author Alexander Quintero <alquintero@heinsohn.com.co>
 *
 */
public class SolicitudLegalizacionDesembolsoModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4599092569265866146L;

    /**
     * Identificador único, llave primaria
     */
    private Long idSolicitudLegalizacionDesembolso;
    /**
     * Asociación a la Postulación FOVIS
     */
    private Long idPostulacionFOVIS;
    /**
     * Asociación a la Legalización y Desembolso
     */
    private Long idLegalizacionDesembolso;
    /**
     * Contiene los datos de la Legalización y Desembolso FOVIS
     */
    private LegalizacionDesembolsoModeloDTO legalizacionDesembolso;
    /**
     * Estado de la solicitud de legalización y desembolso.
     */
    private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud;
    /**
     * Observaciones de la solicitud de legalización y desembolso para control interno.
     */
    private String observaciones;
    /**
     * Fecha de operacion del desembolso
     */
    private Long fechaOperacion;
    /**
     * Objeto Json con los datos de la postulación antes del inicio de la Legalización y Desembolso.
     */
    private String jsonPostulacion;

    /**
     * Variable que indica si es un reintento de transaccion desembolso
     */
    private Boolean esReintentoTransaccionDesembolso;

    /**
     * Indica la cantidad de reintento de transaccion desembolso maximo se permiten 10
     */
    private Short cantidadReintentos;

    public SolicitudLegalizacionDesembolsoModeloDTO() {
    }

    public SolicitudLegalizacionDesembolsoModeloDTO(SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso) {
        this.convertToDTO(solicitudLegalizacionDesembolso);
    }

    public SolicitudLegalizacionDesembolsoModeloDTO(SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso,
            LegalizacionDesembolsoModeloDTO legalizacion) {
        this.convertToDTO(solicitudLegalizacionDesembolso);
        this.setLegalizacionDesembolso(legalizacion);
    }

    public SolicitudLegalizacionDesembolsoModeloDTO(SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso,
            LegalizacionDesembolso legalizacionDesembolso) {
        this.convertToDTO(solicitudLegalizacionDesembolso);
        this.setLegalizacionDesembolso(new LegalizacionDesembolsoModeloDTO(legalizacionDesembolso));
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * @return SolicitudLegalizacionDesembolso
     */
    public SolicitudLegalizacionDesembolso convertToEntity() {
        SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso = new SolicitudLegalizacionDesembolso();
        solicitudLegalizacionDesembolso.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudLegalizacionDesembolso.setIdPostulacionFOVIS(this.getIdPostulacionFOVIS());
        solicitudLegalizacionDesembolso.setIdLegalizacionDesembolso(this.getIdLegalizacionDesembolso());
        solicitudLegalizacionDesembolso.setIdSolicitudLegalizacionDesembolso(this.getIdSolicitudLegalizacionDesembolso());
        solicitudLegalizacionDesembolso.setObservaciones(this.getObservaciones());
        solicitudLegalizacionDesembolso.setFechaOperacion(this.fechaOperacion != null ? new Date(this.fechaOperacion) : null);
        solicitudLegalizacionDesembolso.setJsonPostulacion(this.getJsonPostulacion());
        solicitudLegalizacionDesembolso.setCantidadReintentos(this.cantidadReintentos);
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudLegalizacionDesembolso.setSolicitudGlobal(solicitudGlobal);
        return solicitudLegalizacionDesembolso;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * @param solicitudLegalizacionDesembolso
     */
    public void convertToDTO(SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso) {
        if (solicitudLegalizacionDesembolso.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudLegalizacionDesembolso.getSolicitudGlobal());
        }
        this.setIdSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitudLegalizacionDesembolso());
        this.setIdPostulacionFOVIS(solicitudLegalizacionDesembolso.getIdPostulacionFOVIS());
        this.setIdLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdLegalizacionDesembolso());
        this.setEstadoSolicitud(solicitudLegalizacionDesembolso.getEstadoSolicitud());
        this.setObservaciones(solicitudLegalizacionDesembolso.getObservaciones());
        this.setFechaOperacion(solicitudLegalizacionDesembolso.getFechaOperacion() != null
                ? solicitudLegalizacionDesembolso.getFechaOperacion().getTime() : null);
        this.setJsonPostulacion(solicitudLegalizacionDesembolso.getJsonPostulacion());
        this.setCantidadReintentos(solicitudLegalizacionDesembolso.getCantidadReintentos());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * @param solicitudLegalizacionDesembolso
     * @return
     */
    public SolicitudLegalizacionDesembolso copyDTOToEntity(SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso) {
        if (this.getEstadoSolicitud() != null) {
            solicitudLegalizacionDesembolso.setEstadoSolicitud(this.getEstadoSolicitud());
        }
        if (this.getIdPostulacionFOVIS() != null) {
            solicitudLegalizacionDesembolso.setIdPostulacionFOVIS(this.getIdPostulacionFOVIS());
        }
        if (this.getIdLegalizacionDesembolso() != null) {
            solicitudLegalizacionDesembolso.setIdLegalizacionDesembolso(this.getIdLegalizacionDesembolso());
        }
        if (this.getIdSolicitudLegalizacionDesembolso() != null) {
            solicitudLegalizacionDesembolso.setIdSolicitudLegalizacionDesembolso(this.getIdSolicitudLegalizacionDesembolso());
        }
        if (this.getObservaciones() != null) {
            solicitudLegalizacionDesembolso.setObservaciones(this.getObservaciones());
        }
        if (this.getJsonPostulacion() != null) {
            solicitudLegalizacionDesembolso.setJsonPostulacion(this.getJsonPostulacion());
        }
        if (this.getFechaOperacion() != null) {
            solicitudLegalizacionDesembolso.setFechaOperacion(new Date(this.getFechaOperacion()));
        }
        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudLegalizacionDesembolso.getSolicitudGlobal());
        if (solicitudGlobal.getIdSolicitud() != null) {
            solicitudLegalizacionDesembolso.setSolicitudGlobal(solicitudGlobal);
        }
        if (this.getCantidadReintentos() != null) {
            solicitudLegalizacionDesembolso.setCantidadReintentos(this.getCantidadReintentos());
        }
        return solicitudLegalizacionDesembolso;
    }

    /**
     * @return the idSolicitudLegalizacionDesembolso
     */
    public Long getIdSolicitudLegalizacionDesembolso() {
        return idSolicitudLegalizacionDesembolso;
    }

    /**
     * @param idSolicitudLegalizacionDesembolso
     *        the idSolicitudLegalizacionDesembolso to set
     */
    public void setIdSolicitudLegalizacionDesembolso(Long idSolicitudLegalizacionDesembolso) {
        this.idSolicitudLegalizacionDesembolso = idSolicitudLegalizacionDesembolso;
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
     * @return the idLegalizacionDesembolso
     */
    public Long getIdLegalizacionDesembolso() {
        return idLegalizacionDesembolso;
    }

    /**
     * @param idLegalizacionDesembolso
     *        the idLegalizacionDesembolso to set
     */
    public void setIdLegalizacionDesembolso(Long idLegalizacionDesembolso) {
        this.idLegalizacionDesembolso = idLegalizacionDesembolso;
    }

    /**
     * @return the legalizacionDesembolso
     */
    public LegalizacionDesembolsoModeloDTO getLegalizacionDesembolso() {
        return legalizacionDesembolso;
    }

    /**
     * @param legalizacionDesembolso
     *        the legalizacionDesembolso to set
     */
    public void setLegalizacionDesembolso(LegalizacionDesembolsoModeloDTO legalizacionDesembolso) {
        this.legalizacionDesembolso = legalizacionDesembolso;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the observaciones
     */
    public Long getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param fechaOperacion
     *        the fechaOperacion to set
     */
    public void setFechaOperacion(Long fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
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
     * @return the jsonPostulacion
     */
    public String getJsonPostulacion() {
        return jsonPostulacion;
    }

    /**
     * @param jsonPostulacion
     *        the jsonPostulacion to set
     */
    public void setJsonPostulacion(String jsonPostulacion) {
        this.jsonPostulacion = jsonPostulacion;
    }

    /**
     * @return the esReintentoTransaccionDesembolso
     */
    public Boolean getEsReintentoTransaccionDesembolso() {
        return esReintentoTransaccionDesembolso;
    }

    /**
     * @param esReintentoTransaccionDesembolso
     *        the esReintentoTransaccionDesembolso to set
     */
    public void setEsReintentoTransaccionDesembolso(Boolean esReintentoTransaccionDesembolso) {
        this.esReintentoTransaccionDesembolso = esReintentoTransaccionDesembolso;
    }

    /**
     * @return the cantidadReintentos
     */
    public Short getCantidadReintentos() {
        return cantidadReintentos;
    }

    /**
     * @param cantidadReintentos
     *        the cantidadReintentos to set
     */
    public void setCantidadReintentos(Short cantidadReintentos) {
        this.cantidadReintentos = cantidadReintentos;
    }

}
