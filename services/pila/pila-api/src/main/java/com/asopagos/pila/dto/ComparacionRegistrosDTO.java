package com.asopagos.pila.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que agrupa los valores de la búsqueda de un registro de corrección A
 * en una planilla original<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ComparacionRegistrosDTO implements Serializable {
    private static final long serialVersionUID = 5165142642207038283L;

    /**
     * Id de registro detallado en planilla original que coincide con el registro A en la corrección
     * (último encontrado)
     * */
    private Long idRegDetOriginal;
    
    /**
     * Cantidad de ocurrencias del registro A de corrección dentro de la planilla original
     * */
    private Integer cantidadOcurrencias;

    /**
     * @return the idRegDetOriginal
     */
    public Long getIdRegDetOriginal() {
        return idRegDetOriginal;
    }

    /**
     * @param idRegDetOriginal the idRegDetOriginal to set
     */
    public void setIdRegDetOriginal(Long idRegDetOriginal) {
        this.idRegDetOriginal = idRegDetOriginal;
    }

    /**
     * @return the cantidadOcurrencias
     */
    public Integer getCantidadOcurrencias() {
        return cantidadOcurrencias;
    }

    /**
     * @param cantidadOcurrencias the cantidadOcurrencias to set
     */
    public void setCantidadOcurrencias(Integer cantidadOcurrencias) {
        this.cantidadOcurrencias = cantidadOcurrencias;
    }
}
