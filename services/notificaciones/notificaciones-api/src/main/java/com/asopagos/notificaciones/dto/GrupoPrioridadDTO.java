package com.asopagos.notificaciones.dto;

import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class GrupoPrioridadDTO {
    
    /**
     * Id que representa la entidad Grupo Destinatario
     */
    private Long idGrupoPrioridad;
   
    /**
     * Representa el nombre del grupo del destinatario
     */
    private String nombre;

    /**
     * Codigo que representa la prioridad del destinatario
     */
    private Short prioridad;
    
    /**
     * Representa la etiqueta del comunicado asociada al grupo
     */
    private EtiquetaPlantillaComunicadoEnum etiqueta;
    
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
