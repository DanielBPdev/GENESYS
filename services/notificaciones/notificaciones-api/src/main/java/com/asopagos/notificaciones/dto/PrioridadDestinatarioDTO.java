package com.asopagos.notificaciones.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;


/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class PrioridadDestinatarioDTO implements Serializable{
    
    /**
     * Id que representa la entidad PrioridadDestinatario
     */
    private Long idPrioridadDestinatario;
    
    /**
     * Id que representa la entidad DestinatarioComunicado
     */
    private Long idDestinatarioComunicado;

    /**
     * Id que representa la entidad Grupo Destinatario
     */
    private Long idGrupoPrioridad;
    
    /**
     * Representa la prioridad del destinatario
     */
    private Short prioridad;
    
    /**
     * Representa la etiqueta del comunicado asociada al grupo
     */
    private EtiquetaPlantillaComunicadoEnum etiqueta;

    /**
     * Constructor de la clase
     */
    public PrioridadDestinatarioDTO() {
    }
    
    /**
     * Constructor de la clase
     * 
     * @param idPrioridadDestinatario
     * @param idDestinatarioComunicado
     * @param idGrupoPrioridad
     * @param prioridad
     * @param etiqueta
     */
    public PrioridadDestinatarioDTO(Long idPrioridadDestinatario, Long idDestinatarioComunicado, Long idGrupoPrioridad, Short prioridad,
            EtiquetaPlantillaComunicadoEnum etiqueta) {
        super();
        this.idPrioridadDestinatario = idPrioridadDestinatario;
        this.idDestinatarioComunicado = idDestinatarioComunicado;
        this.idGrupoPrioridad = idGrupoPrioridad;
        this.prioridad = prioridad;
        this.etiqueta = etiqueta;
    }
    
    /**
     * @return the idPrioridadDestinatario
     */
    public Long getIdPrioridadDestinatario() {
        return idPrioridadDestinatario;
    }

    /**
     * @param idPrioridadDestinatario the idPrioridadDestinatario to set
     */
    public void setIdPrioridadDestinatario(Long idPrioridadDestinatario) {
        this.idPrioridadDestinatario = idPrioridadDestinatario;
    }

    /**
     * @return the idDestinatarioComunicado
     */
    public Long getIdDestinatarioComunicado() {
        return idDestinatarioComunicado;
    }

    /**
     * @param idDestinatarioComunicado the idDestinatarioComunicado to set
     */
    public void setIdDestinatarioComunicado(Long idDestinatarioComunicado) {
        this.idDestinatarioComunicado = idDestinatarioComunicado;
    }

    /**
     * @return the idGrupoPrioridad
     */
    public Long getIdGrupoPrioridad() {
        return idGrupoPrioridad;
    }

    /**
     * @param idGrupoPrioridad the idGrupoPrioridad to set
     */
    public void setIdGrupoPrioridad(Long idGrupoPrioridad) {
        this.idGrupoPrioridad = idGrupoPrioridad;
    }

    /**
     * @return the prioridad
     */
    public Short getPrioridad() {
        return prioridad;
    }

    /**
     * @param prioridad the prioridad to set
     */
    public void setPrioridad(Short prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * @return the etiqueta
     */
    public EtiquetaPlantillaComunicadoEnum getEtiqueta() {
        return etiqueta;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(EtiquetaPlantillaComunicadoEnum etiqueta) {
        this.etiqueta = etiqueta;
    }
}
