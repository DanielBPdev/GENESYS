package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene el detalle de los abono hechos por tarjeta en la dispersion del monto liquidado <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DetalleResultadoPagoTarjetaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2477876996567789941L;

    /** Tipo de identificacion del administrador del subsidio */
    private TipoIdentificacionEnum tipoIdentificacionAdministradorSubsidio;

    /** Numero de indentificacion del administrador del subsidio */
    private String numeroIdentificacionAdministradorSubsidio;

    /** Nombre del administrador del subsidio */
    private String nombreAdministradorSubsidio;

    /** Numero de tarjeta */
    private String numeroTarjeta;

    /** Tipo de indentificacion del trabajador */
    private TipoIdentificacionEnum tipoIndentificacionTrabajador;

    /** Numero de indentificacion del trabajador */
    private String numeroIdentificacionTrabajador;

    /** Nombre del trabajador */
    private String nombreTrabajador;

    /** Monto del abono */
    private BigDecimal monto;

    /** Identificador de la condición del administrador en el proceso de liquidación */
    private Long idCondicionAdministrador;

    /** Identificador de la condición del trabajador en el proceso de liquidación */
    private Long idCondicionTrabajador;

    /**
     * @return valor del tipo de identificacion del administrador del subsidio
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
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta
     *        the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @return the tipoIndentificacionTrabajador
     */
    public TipoIdentificacionEnum getTipoIndentificacionTrabajador() {
        return tipoIndentificacionTrabajador;
    }

    /**
     * @param tipoIndentificacionTrabajador
     *        the tipoIndentificacionTrabajador to set
     */
    public void setTipoIndentificacionTrabajador(TipoIdentificacionEnum tipoIndentificacionTrabajador) {
        this.tipoIndentificacionTrabajador = tipoIndentificacionTrabajador;
    }

    /**
     * @return the nombreTrabajador
     */
    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    /**
     * @param nombreTrabajador
     *        the nombreTrabajador to set
     */
    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
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
     * @return the numeroIdentificacionTrabajador
     */
    public String getNumeroIdentificacionTrabajador() {
        return numeroIdentificacionTrabajador;
    }

    /**
     * @param numeroIdentificacionTrabajador
     *        the numeroIdentificacionTrabajador to set
     */
    public void setNumeroIdentificacionTrabajador(String numeroIdentificacionTrabajador) {
        this.numeroIdentificacionTrabajador = numeroIdentificacionTrabajador;
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

    /**
     * @return the idCondicionTrabajador
     */
    public Long getIdCondicionTrabajador() {
        return idCondicionTrabajador;
    }

    /**
     * @param idCondicionTrabajador
     *        the idCondicionTrabajador to set
     */
    public void setIdCondicionTrabajador(Long idCondicionTrabajador) {
        this.idCondicionTrabajador = idCondicionTrabajador;
    }

}
