package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene la informacion consultada de las
 * solicitudes FOVIS<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Edward Castano</a>
 */
public class ResultadoHistoricoSolicitudesFovisDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 6516256299881192950L;

    /**
     * Identificador de la solicitud a consultar
     */
    private String numeroSolicitud;

    /**
     * Fecha de radicacion
     */
    private Date fechaRadicacion;

    /**
     * Identificador de la postulacion
     */
    private TipoSolicitudEnum tipoSolicitud;

    /**
     * Estado solicitud -- Depende del tipo de solicitud
     */
    private String estadoSolicitud;

    /**
     * Refernecia al identificador del documento comunicado
     */
    private String comunicado;

    /**
     * Identificador solicitud global
     */
    private Long idSolicitudGlobal;

    /**
     * Estado de la solicitud de novedad.
     */
    private EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad;

    /**
     * Estado de la solicitud legalizacion.
     */
    private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudLegalizacion;

    /**
     * Estado de la solicitud postulación.
     */
    private EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion;

    /**
     * @return the numeroSolicitud
     */
    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    /**
     * @param numeroSolicitud
     *        the numeroSolicitud to set
     */
    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    /**
     * @return the fechaRadicacion
     */
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion
     *        the fechaRadicacion to set
     */
    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the tipoSolicitud
     */
    public TipoSolicitudEnum getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * @param tipoSolicitud
     *        the tipoSolicitud to set
     */
    public void setTipoSolicitud(TipoSolicitudEnum tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * @return the estadoSolicitud
     */
    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the comunicado
     */
    public String getComunicado() {
        return comunicado;
    }

    /**
     * @param comunicado
     *        the comunicado to set
     */
    public void setComunicado(String comunicado) {
        this.comunicado = comunicado;
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
     * @return the estadoSolicitudNovedad
     */
    public EstadoSolicitudNovedadFovisEnum getEstadoSolicitudNovedad() {
        return estadoSolicitudNovedad;
    }

    /**
     * @param estadoSolicitudNovedad
     *        the estadoSolicitudNovedad to set
     */
    public void setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad) {
        this.estadoSolicitudNovedad = estadoSolicitudNovedad;
    }

    /**
     * @return the estadoSolicitudlegalizacion
     */
    public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitudLegalizacion() {
        return estadoSolicitudLegalizacion;
    }

    /**
     * @param estadoSolicitudlegalizacion
     *        the estadoSolicitudlegalizacion to set
     */
    public void setEstadoSolicitudLegalizacion(EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudLegalizacion) {
        this.estadoSolicitudLegalizacion = estadoSolicitudLegalizacion;
    }

    /**
     * @return the estadoSolicitudPostulacion
     */
    public EstadoSolicitudPostulacionEnum getEstadoSolicitudPostulacion() {
        return estadoSolicitudPostulacion;
    }

    /**
     * @param estadoSolicitudPostulacion
     *        the estadoSolicitudPostulacion to set
     */
    public void setEstadoSolicitudPostulacion(EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion) {
        this.estadoSolicitudPostulacion = estadoSolicitudPostulacion;
    }

}
