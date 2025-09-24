package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene los items que se muestran en los tipos de pago banco consignaciones y pago judicial en la
 * dispersión masiva de liquidación. <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> mosorio</a>
 */

public class ItemResultadoPagoBancoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7605825776835667140L;

    /** Tipo de identificacion del administrador del subsidio */
    private TipoIdentificacionEnum tipoIdentificacionAdministradorSubsidio;

    /** Numero identificación del administrador del subsidio */
    private String numeroIdentificacionAdministradorSubsidio;

    /** Nombre del administrador del subsidio */
    private String nombreAdministradorSubsidio;

    /** Tipo de cuenta del banco */
    private TipoCuentaEnum tipoCuenta;

    /** Numero de cuenta del banco */
    private String numeroCuenta;

    /** Monto de la transaccion */
    private BigDecimal monto;

    /** Identificador de condición de administrador en la liquidación */
    private Long idCondicionAdministrador;

    /**
     * @return the tipoIdentificacionAdministradorSubsidio
     */
    public TipoIdentificacionEnum getTipoIdentificacionAdministradorSubsidio() {
        return tipoIdentificacionAdministradorSubsidio;
    }

    /**
     * @param tipoIdentificacionAdministradorSubsidio
     *        the tipoIdentificacionAdministradorSubsidio to set
     */
    public void setTipoIdentificacionAdministradorSubsidio(TipoIdentificacionEnum tipoIdentificacionAdministradorSubsidio) {
        this.tipoIdentificacionAdministradorSubsidio = tipoIdentificacionAdministradorSubsidio;
    }

    /**
     * @return the numeroIdentificacionAdministradorSubsidio
     */
    public String getNumeroIdentificacionAdministradorSubsidio() {
        return numeroIdentificacionAdministradorSubsidio;
    }

    /**
     * @param numeroIdentificacionAdministradorSubsidio
     *        the numeroIdentificacionAdministradorSubsidio to set
     */
    public void setNumeroIdentificacionAdministradorSubsidio(String numeroIdentificacionAdministradorSubsidio) {
        this.numeroIdentificacionAdministradorSubsidio = numeroIdentificacionAdministradorSubsidio;
    }

    /**
     * @return the nombreAdministradorSubsidio
     */
    public String getNombreAdministradorSubsidio() {
        return nombreAdministradorSubsidio;
    }

    /**
     * @param nombreAdministradorSubsidio
     *        the nombreAdministradorSubsidio to set
     */
    public void setNombreAdministradorSubsidio(String nombreAdministradorSubsidio) {
        this.nombreAdministradorSubsidio = nombreAdministradorSubsidio;
    }

    /**
     * @return the tipoCuenta
     */
    public TipoCuentaEnum getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @param tipoCuenta
     *        the tipoCuenta to set
     */
    public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta
     *        the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @param monto
     *        the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return the idCondicionAdministrador
     */
    public Long getIdCondicionAdministrador() {
        return idCondicionAdministrador;
    }

    /**
     * @param idCondicionAdministrador
     *        the idCondicionAdministrador to set
     */
    public void setIdCondicionAdministrador(Long idCondicionAdministrador) {
        this.idCondicionAdministrador = idCondicionAdministrador;
    }

}
