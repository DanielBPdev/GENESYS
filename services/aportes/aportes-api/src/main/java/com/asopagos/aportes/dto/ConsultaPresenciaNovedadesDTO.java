package com.asopagos.aportes.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que contiene los datos de la consulta de presencia de novedades
 * y tamaño de la planilla de un registro general<br/>
 * <b>Módulo:</b> Asopagos - HU-211 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConsultaPresenciaNovedadesDTO implements Serializable {
    private static final long serialVersionUID = 7419509840624076210L;

    /** Id de registro general */
    private Long idRegGen;
    
    /** Tiene novedades */
    private Boolean tieneNovedades;
    
    /** Tamaño de la planilla */
    private Integer tamanioPlanilla;

    /**
     * @return the idRegGen
     */
    public Long getIdRegGen() {
        return idRegGen;
    }

    /**
     * @param idRegGen the idRegGen to set
     */
    public void setIdRegGen(Long idRegGen) {
        this.idRegGen = idRegGen;
    }

    /**
     * @return the tieneNovedades
     */
    public Boolean getTieneNovedades() {
        return tieneNovedades;
    }

    /**
     * @param tieneNovedades the tieneNovedades to set
     */
    public void setTieneNovedades(Boolean tieneNovedades) {
        this.tieneNovedades = tieneNovedades;
    }

    /**
     * @return the tamanioPlanilla
     */
    public Integer getTamanioPlanilla() {
        return tamanioPlanilla;
    }

    /**
     * @param tamanioPlanilla the tamanioPlanilla to set
     */
    public void setTamanioPlanilla(Integer tamanioPlanilla) {
        this.tamanioPlanilla = tamanioPlanilla;
    }
}
