package com.asopagos.pila.dto;

import java.io.Serializable;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;

/**
 * <b>Descripcion:</b> DTO que representa un elemento en la lista de combinatoria de archivos para el B0 de OI <br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ElementoCombinatoriaArchivosDTO implements Serializable {
    private static final long serialVersionUID = 2011134384314768055L;
    
    /**
     * Índice de planilla a validar
     * */
    private IndicePlanilla indice;
    
    /**
     * Indicador que determina sí el índice es anulable
     * */
    private Boolean esAnulable;
    
    /**
     * Constructor desde consulta JPA
     * */
    public ElementoCombinatoriaArchivosDTO(IndicePlanilla indice, Boolean esAnulable){
    	this.indice = indice;
    	this.esAnulable = esAnulable;
    }

    /**
     * @return the indice
     */
    public IndicePlanilla getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(IndicePlanilla indice) {
        this.indice = indice;
    }

    /**
     * @return the esAnulable
     */
    public Boolean getEsAnulable() {
        return esAnulable;
    }

    /**
     * @param esAnulable the esAnulable to set
     */
    public void setEsAnulable(Boolean esAnulable) {
        this.esAnulable = esAnulable;
    }
}
