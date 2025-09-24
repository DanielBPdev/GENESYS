package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que Compone los datos para consultar la información
 * complementaria de las inconsistencias para la bandeja 392<br/>
 * <b>Módulo:</b> Asopagos - HU-211-392 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConsultaDatosExtraInconsistenciaDTO implements Serializable {
    private static final long serialVersionUID = -5951930224037409057L;

    /** Número de la planilla */
    private Long numPlanilla;
    
    /** Clave de la planilla para búsqueda en OF */
    private String clave;

    /**
     * @param numPlanilla
     * @param clave
     */
    public ConsultaDatosExtraInconsistenciaDTO(Long numPlanilla, String clave) {
        super();
        this.numPlanilla = numPlanilla;
        this.clave = clave;
    }

    /**
     * @return the numPlanilla
     */
    public Long getNumPlanilla() {
        return numPlanilla;
    }

    /**
     * @param numPlanilla the numPlanilla to set
     */
    public void setNumPlanilla(Long numPlanilla) {
        this.numPlanilla = numPlanilla;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
}
