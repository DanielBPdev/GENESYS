package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.SolicitudFiscalizacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;

/**
 * DTO que representa el modelo de solicitud de fiscalización
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class SolicitudFiscalizacionModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 7747108986130371901L;
    /**
     * Representa el identificador de la solicitud de fiscalización
     */
    private Long idSolicitudFiscalizacion;
    /**
     * Representa el estado de fiscalización para la presente solicitud
     */
    private EstadoFiscalizacionEnum estadoFiscalizacion;
    /**
     * Número identificador que hace relación a la persona
     */
    private Long idCicloAportante;

    /**
     * Método constructor
     */
    public SolicitudFiscalizacionModeloDTO() {

    }

    /**
     * @return the idSolicitudFiscalizacion
     */
    public Long getIdSolicitudFiscalizacion() {
        return idSolicitudFiscalizacion;
    }

    /**
     * @param idSolicitudFiscalizacion
     *        the idSolicitudFiscalizacion to set
     */
    public void setIdSolicitudFiscalizacion(Long idSolicitudFiscalizacion) {
        this.idSolicitudFiscalizacion = idSolicitudFiscalizacion;
    }

    /**
     * @return the estadoFiscalizacion
     */
    public EstadoFiscalizacionEnum getEstadoFiscalizacion() {
        return estadoFiscalizacion;
    }

    /**
     * @param estadoFiscalizacion
     *        the estadoFiscalizacion to set
     */
    public void setEstadoFiscalizacion(EstadoFiscalizacionEnum estadoFiscalizacion) {
        this.estadoFiscalizacion = estadoFiscalizacion;
    }

    /**
     * @return the idPersona
     */
    public Long getIdCicloAportante() {
        return idCicloAportante;
    }

    /**
     * @param idPersona
     *        the idPersona to set
     */
    public void setIdCicloAportante(Long idCicloAportante) {
        this.idCicloAportante = idCicloAportante;
    }

    /**
     * Método que sirve para convertir de DTO a Entity para este caso SolicitudFiscalizacion
     * @return
     */
    public SolicitudFiscalizacion convertToSolicitudFiscalizacionEntity() {
        SolicitudFiscalizacion solicitudFiscalizacion = new SolicitudFiscalizacion();
        solicitudFiscalizacion.setIdSolicitudFiscalizacion(this.getIdSolicitudFiscalizacion());
        solicitudFiscalizacion.setEstadoFiscalizacion(this.getEstadoFiscalizacion());
        solicitudFiscalizacion.setIdCicloAportante(this.getIdCicloAportante());
        Solicitud solicitud = super.convertToSolicitudEntity();
        solicitudFiscalizacion.setSolicitudGlobal(solicitud);
        return solicitudFiscalizacion;
    }

    /**
     * Método que sirve para convertir de Entity a DTO para este caso SolicitudFiscalizacionModeloDTO
     * @param solicitudFiscalizacion
     *        recibe como parametro objeto SolicitudFiscalizacionModeloDTO
     */
    public void convertToDTO(SolicitudFiscalizacion solicitudFiscalizacion) {
        if (solicitudFiscalizacion.getSolicitudGlobal() != null) {
            /* Se setea la información para SolicitudModeloDTO */
            super.convertToDTO(solicitudFiscalizacion.getSolicitudGlobal());
        }
        /* Se setean los valores para el objeto SolicitudFiscalizacionModeloDTO */
        this.setIdSolicitudFiscalizacion(solicitudFiscalizacion.getIdSolicitudFiscalizacion());
        this.setEstadoFiscalizacion(solicitudFiscalizacion.getEstadoFiscalizacion());
        this.setIdCicloAportante(solicitudFiscalizacion.getIdCicloAportante());
    }
}
