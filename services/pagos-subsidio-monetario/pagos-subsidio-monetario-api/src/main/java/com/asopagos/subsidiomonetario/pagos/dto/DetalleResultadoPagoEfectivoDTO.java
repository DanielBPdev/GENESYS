package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene el detalle de los pago hechos por efectivo en la dispersion del monto liquidado <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DetalleResultadoPagoEfectivoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8248195000553239459L;

    /** Número de operación relacionado al pago */
    private BigDecimal numeroOperacion;

    /** Tipo de identificacion del administrador del subsidio */
    private TipoIdentificacionEnum tipoIdentificacionAdministradorSubsidio;

    /** Numero identificación del administrador del subsidio */
    private String numeroIdentificacionAdministradorSubsidio;

    /** Nombre del administrador del subsidio */
    private String nombreAdministradorSubsidio;

    /** Monto pendiente por ser dispersado */
    private BigDecimal montoPendientePorDispersar;

    /** Identificador de la condición de administrador */
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
     * @return the montoPendientePorDispersar
     */
    public BigDecimal getMontoPendientePorDispersar() {
        return montoPendientePorDispersar;
    }

    /**
     * @param montoPendientePorDispersar
     *        the montoPendientePorDispersar to set
     */
    public void setMontoPendientePorDispersar(BigDecimal montoPendientePorDispersar) {
        this.montoPendientePorDispersar = montoPendientePorDispersar;
    }

    /**
     * @return the numeroOperacion
     */
    public BigDecimal getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * @param numeroOperacion
     *        the numeroOperacion to set
     */
    public void setNumeroOperacion(BigDecimal numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
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
