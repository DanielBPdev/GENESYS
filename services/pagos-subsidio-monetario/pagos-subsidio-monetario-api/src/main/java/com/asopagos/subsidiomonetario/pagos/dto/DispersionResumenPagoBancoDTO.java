package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.subsidiomonetario.dispersion.TipoTransaccionPagoBancoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información resumida del pago por medio del banco de la dispersión del monto liquidado.
 * <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionResumenPagoBancoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1711516236635083602L;

    /** Identificador del banco */
    private BigDecimal idBanco;

    /** Nombre del banco */
    private String nombre;

    /** Total a consignar en el banco */
    private BigDecimal montoTotal;

    /** Tipo de transacción que se realizo en el banco */
    private TipoTransaccionPagoBancoEnum tipoTransaccionBanco;

    /**
     * Metodo que permite obtener el valor del nombre del banco.
     * @return valor del nombre del banco
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que cambia el valor del nombre del banco
     * @param nombre
     *        valor del nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que permite obtener el valor del monto total
     * @return valor del monto total
     */
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    /**
     * Metodo que cambia el valor del monto total.
     * @param montoTotal
     *        valor del nuevo monto total.
     */
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * Metodo que permite obtener el valor del tipo de transacción que se realizó en el banco
     * @return valor del tipo de transacción realizada.
     */
    public TipoTransaccionPagoBancoEnum getTipoTransaccionBanco() {
        return tipoTransaccionBanco;
    }

    /**
     * Metodo que cambia el valor del tipo de transacción que se realizó en el banco.
     * @param tipoTransaccionBanco
     *        valor del nuevo tipo de transacción.
     */
    public void setTipoTransaccionBanco(TipoTransaccionPagoBancoEnum tipoTransaccionBanco) {
        this.tipoTransaccionBanco = tipoTransaccionBanco;
    }

    /**
     * @return the idBanco
     */
    public BigDecimal getIdBanco() {
        return idBanco;
    }

    /**
     * @param idBanco
     *        the idBanco to set
     */
    public void setIdBanco(BigDecimal idBanco) {
        this.idBanco = idBanco;
    }

}
