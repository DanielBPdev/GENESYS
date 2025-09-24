package com.asopagos.parametros;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public enum GrupoValidacionEnum {

    
    GRUPO_CREACION("Grupo de creación"),
    GRUPO_ACTUALIZACION("Grupo de actualización");
    
    /**
     * Mensaje en lenguaje natural del valor del enum
     */
    private String descripcion;

    /**
     * @param descripcion
     */
    private GrupoValidacionEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
    
}
