package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene los descuentos en la sección pagos descuento por entidad
 * en la dispersión masiva de liquidación <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DetalleResultadoEntidadDescuentoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1660611886227312327L;

    /** Identificador de la entidad de descuento */
    private BigDecimal identificadorEntidad;

    /** Nombre de la entidad de descuento */
    private String nombreEntidad;

    /** Numero de identificación de la entidad de descuento */
    private String NIT;

    /** Total de descuentos por entidad */
    private BigDecimal totalEntidad;

    /** Lista que contiene los detalles del descuento de cada entidad */
    private List<ItemResultadoEntidadDescuentoDTO> lstItemsDescuentos;

    /** Identificador de la prioridad de la entidad de descuento en la liquidación */
    private Long idPrioridad;

    /**
     * @return the identificadorEntidad
     */
    public BigDecimal getIdentificadorEntidad() {
        return identificadorEntidad;
    }

    /**
     * @param identificadorEntidad
     *        the identificadorEntidad to set
     */
    public void setIdentificadorEntidad(BigDecimal identificadorEntidad) {
        this.identificadorEntidad = identificadorEntidad;
    }

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
     * @return the nIT
     */
    public String getNIT() {
        return NIT;
    }

    /**
     * @param nIT
     *        the nIT to set
     */
    public void setNIT(String nIT) {
        NIT = nIT;
    }

    /**
     * @return the lstItemsDescuentos
     */
    public List<ItemResultadoEntidadDescuentoDTO> getLstItemsDescuentos() {
        return lstItemsDescuentos;
    }

    /**
     * @param lstItemsDescuentos
     *        the lstItemsDescuentos to set
     */
    public void setLstItemsDescuentos(List<ItemResultadoEntidadDescuentoDTO> lstItemsDescuentos) {
        this.lstItemsDescuentos = lstItemsDescuentos;
    }

    /**
     * @return the totalEntidad
     */
    public BigDecimal getTotalEntidad() {
        return totalEntidad;
    }

    /**
     * @param totalEntidad
     *        the totalEntidad to set
     */
    public void setTotalEntidad(BigDecimal totalEntidad) {
        this.totalEntidad = totalEntidad;
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
