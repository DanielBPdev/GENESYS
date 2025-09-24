package com.asopagos.usuarios.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public enum LoginEventsEnum {

    UPDATE_PASSWORD("Actualiza contraseña");
        
    /**
     * Descripción del atributo
     */
    private String descripcion;


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

    private LoginEventsEnum(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
