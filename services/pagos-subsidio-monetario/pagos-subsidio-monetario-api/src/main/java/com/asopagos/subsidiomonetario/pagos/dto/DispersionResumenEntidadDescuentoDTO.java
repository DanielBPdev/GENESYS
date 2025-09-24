package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.TipoEntidadDescuentoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información resumida de la entidad de descuento de la dispersión del monto liquidado <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> mosorio</a>
 */

public class DispersionResumenEntidadDescuentoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5292596293683592134L;

    /** Código de la entidad de descuento */
    private String codigoEntidad;

    /** Identificador de la entidad de descuento */
    private Long idEntidad;

    /** Nombre de la entidad de descuento */
    private String nombre;

    /** Total descontado para el pago a la entida de descuento */
    private BigDecimal montoTotal;

    /** Tipo de entidad de descuento */
    private TipoEntidadDescuentoEnum tipoEntidad;

    /**
     * Metodo que obtiene el valor del nombre
     * @return valor del nombre de la entidad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que permite cambiar el nombre de la entidad.
     * @param nombre
     *        valor del nuevo nombre de la entidad.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que obtiene el valor del monto total de a descontar de la entidad.
     * @return valor del monto total de la entidad.
     */
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    /**
     * Metodo que permite cambiar el valor del monto total a descontar de la entidad
     * @param montoTotal
     *        nuevo valor del monto total a descontar.
     */
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * Metodo que obtiene el valor del tipo de la entidad
     * @return valor del tipo de la entidad.
     */
    public TipoEntidadDescuentoEnum getTipoEntidad() {
        return tipoEntidad;
    }

    /**
     * @param tipoEntidad
     *        the tipoEntidad to set
     */
    public void setTipoEntidad(TipoEntidadDescuentoEnum tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    /**
     * @return the codigoEntidad
     */
    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    /**
     * @param codigoEntidad
     *        the codigoEntidad to set
     */
    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    /**
     * @return the idEntidad
     */
    public Long getIdEntidad() {
        return idEntidad;
    }

    /**
     * @param idEntidad
     *        the idEntidad to set
     */
    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

}
