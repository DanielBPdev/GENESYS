package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene los items que se muestran en los descuentos dentro
 * de la sección entidades de descuento en la dispersión masiva de liquidación.<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class ItemResultadoEntidadDescuentoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7354728684598242554L;

    /** Tipo de identificación del administrador del subsidio */
    private TipoIdentificacionEnum tipoIdentificacionAdministradorSubsidio;

    /** Numero de identificación del administrador del subsidio */
    private String numeroIdentificacionAdministradorSubsidio;

    /** Nombre del administrador del subsidio */
    private String nombreAdministradorSubsidio;

    /** Numero de operación de la entidad de descuento */
    private Long numeroOperacion;

    /** Monto descontado de la entidad */
    private BigDecimal montoDescontado;

    /** Identificador de la condición del administrador en el proceso de liquidación */
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
     * @return the numeroOperacion
     */
    public Long getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * @param numeroOperacion
     *        the numeroOperacion to set
     */
    public void setNumeroOperacion(Long numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * @return the montoDescontado
     */
    public BigDecimal getMontoDescontado() {
        return montoDescontado;
    }

    /**
     * @param montoDescontado
     *        the montoDescontado to set
     */
    public void setMontoDescontado(BigDecimal montoDescontado) {
        this.montoDescontado = montoDescontado;
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
