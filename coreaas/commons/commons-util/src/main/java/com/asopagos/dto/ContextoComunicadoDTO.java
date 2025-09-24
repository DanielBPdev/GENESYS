package com.asopagos.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * <b>Descripción:</b> Clase DTO que representa el contexto del JSON del dato temporal del comunicado
 * 
 * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
 *
 */
public class ContextoComunicadoDTO implements Serializable{

    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = -4018246281626961773L;

    /**
     * Atributo que representa el id de la instancia del proceso se usa en el contexto del JSON
     */
    private Long idInstanciaProceso;
    
    /**
     * Atributo que representa el id de la solicitud de la tabla Solicitud se usa en el contexto del JSON
     */
    private Long idSolicitud;
    
    /**
     * Atributo que representa la información adicional a agregar al contexto del JSON
     */
    private Map<String,Object> informacionAdicional;
    
    
    public ContextoComunicadoDTO() {
        
    }

    /**
     * @return the idInstanciaProceso
     */
    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

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
     * @return the informacionAdicional
     */
    public Map<String, Object> getInformacionAdicional() {
        return informacionAdicional;
    }

    /**
     * @param informacionAdicional the informacionAdicional to set
     */
    public void setInformacionAdicional(Map<String, Object> informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }
}
