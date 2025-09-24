package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> Clase DTO que representa la información de la condición de una entidad de descuento en un proceso de liquidación
 * <b>Módulo:</b> Asopagos - Transversal liquidación <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class CondicionEntidadDescuentoLiquidacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la condición de la entidad en la liquidación
     */
    private Long idPrioridad;

    /**
     * Nombre de la entidad de descuento en la liquidación
     */
    private String nombreEntidad;

    /**
     * Nit de la entidad de descuento en la liquidación
     */
    private String nitEntidad;

    /**
     * @return the nombreEntidad
     */
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    /**
     * @param nombreEntidad
     *        the nombreEntidad to set
     */
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    /**
     * @return the nitEntidad
     */
    public String getNitEntidad() {
        return nitEntidad;
    }

    /**
     * @param nitEntidad
     *        the nitEntidad to set
     */
    public void setNitEntidad(String nitEntidad) {
        this.nitEntidad = nitEntidad;
    }

    /**
     * @return the idPrioridad
     */
    public Long getIdPrioridad() {
        return idPrioridad;
    }

    /**
     * @param idPrioridad
     *        the idPrioridad to set
     */
    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

}
