package com.asopagos.afiliados.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que representa una Solicitud global asociada a una persona <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */
public class SolicitudGlobalPersonaDTO implements Serializable {

    private static final long serialVersionUID = -3628797423741280484L;
    
    /**
     * Id de la solcitud global
     */
    private Long idSolicitud;
    
    /**
     * Instancia del proceso asociada
     */
    private String idInstanciaProceso;

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }
    
    public SolicitudGlobalPersonaDTO(Long idSolicitud, String idInstanciaProceso) {
        this.idSolicitud = idSolicitud;
        this.idInstanciaProceso = idInstanciaProceso;
    }
}
