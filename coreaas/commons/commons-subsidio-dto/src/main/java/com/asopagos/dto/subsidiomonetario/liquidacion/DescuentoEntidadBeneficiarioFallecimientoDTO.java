package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información detallada de los descuentos aplicados a un beneficiario por entidad de
 * descuento
 * <b>Módulo:</b> Asopagos - HU-317-517 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DescuentoEntidadBeneficiarioFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nombre de la entidad de descuento
     */
    private String nombreEntidad;

    /**
     * Lista de descuentos aplicados para la entidad
     */
    private List<BigDecimal> descuentos;

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
     * @return the descuentos
     */
    public List<BigDecimal> getDescuentos() {
        return descuentos;
    }

    /**
     * @param descuentos
     *        the descuentos to set
     */
    public void setDescuentos(List<BigDecimal> descuentos) {
        this.descuentos = descuentos;
    }

}
