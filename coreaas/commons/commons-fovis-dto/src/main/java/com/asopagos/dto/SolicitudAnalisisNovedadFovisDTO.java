package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.SolicitudAnalisisNovedadFovis;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Entidad que registra las solicitudes de analisis de novedades de personas asociadas a una postulacion fovis <br/>
 * <b>Historia de Usuario: </b> 325-78, 325-81
 *
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class SolicitudAnalisisNovedadFovisDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8635207177712961022L;

    /**
     * Código identificador de llave primaria de la solicitud de novedad
     */
    private Long idSolicitudAnalisisNovedadFovis;

    /**
     * Referencia a la solicitud
     */
    private Long idSolicitudGlobal;

    /**
     * Referencia a la solicitudNovedad
     */
    private Long idSolicitudNovedad;

    /**
     * Descripción del estado de la solicitud
     */
    private EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud;

    /**
     * Referencia la postulacion fovis asociada a la solicitud
     */
    private Long idPostulacionFovis;

    /**
     * Descripción de las observaciones del Analisis
     */
    private String observaciones;

    /**
     * Identificador persona que hace parte de la novedad
     */
    private Long idPersonaNovedad;

    /**
     * Tipo de identificación persona asociada novedad
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Numero de identificación persona asociada novedad
     */
    private String numeroIdentificacion;

    /**
     * Convierte el DTO a la entidad
     * @return Entidad
     */
    public SolicitudAnalisisNovedadFovis convertToEntity() {
        SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis = new SolicitudAnalisisNovedadFovis();
        solicitudAnalisisNovedadFovis.setIdSolicitudAnalisisNovedadFovis(this.getIdSolicitudAnalisisNovedadFovis());
        // Solicitud global
        Solicitud solicitudGlobal = new Solicitud();
        solicitudGlobal.setIdSolicitud(this.getIdSolicitudGlobal());
        solicitudAnalisisNovedadFovis.setSolicitudGlobal(solicitudGlobal);
        solicitudAnalisisNovedadFovis.setSolicitudNovedad(this.getIdSolicitudNovedad());
        solicitudAnalisisNovedadFovis.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudAnalisisNovedadFovis.setIdPostulacionFovis(this.getIdPostulacionFovis());
        solicitudAnalisisNovedadFovis.setObservaciones(this.getObservaciones());
        solicitudAnalisisNovedadFovis.setIdPersonaNovedad(this.getIdPersonaNovedad());
        return solicitudAnalisisNovedadFovis;
    }

    /**
     * @return the idSolicitudAnalisisNovedadFovis
     */
    public Long getIdSolicitudAnalisisNovedadFovis() {
        return idSolicitudAnalisisNovedadFovis;
    }

    /**
     * @param idSolicitudAnalisisNovedadFovis
     *        the idSolicitudAnalisisNovedadFovis to set
     */
    public void setIdSolicitudAnalisisNovedadFovis(Long idSolicitudAnalisisNovedadFovis) {
        this.idSolicitudAnalisisNovedadFovis = idSolicitudAnalisisNovedadFovis;
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
     * @return the idSolicitudNovedad
     */
    public Long getIdSolicitudNovedad() {
        return idSolicitudNovedad;
    }

    /**
     * @param idSolicitudNovedad
     *        the idSolicitudNovedad to set
     */
    public void setIdSolicitudNovedad(Long idSolicitudNovedad) {
        this.idSolicitudNovedad = idSolicitudNovedad;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudAnalisisNovedadFovisEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the idPostulacionFovis
     */
    public Long getIdPostulacionFovis() {
        return idPostulacionFovis;
    }

    /**
     * @param idPostulacionFovis
     *        the idPostulacionFovis to set
     */
    public void setIdPostulacionFovis(Long idPostulacionFovis) {
        this.idPostulacionFovis = idPostulacionFovis;
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
     * @return the idPersonaNovedad
     */
    public Long getIdPersonaNovedad() {
        return idPersonaNovedad;
    }

    /**
     * @param idPersonaNovedad
     *        the idPersonaNovedad to set
     */
    public void setIdPersonaNovedad(Long idPersonaNovedad) {
        this.idPersonaNovedad = idPersonaNovedad;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

}
