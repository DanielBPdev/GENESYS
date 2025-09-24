package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa la información de la condición de una persona en un proceso de liquidación
 * <b>Módulo:</b> Asopagos - Transversal liquidación <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class CondicionPersonaLiquidacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la condición persona
     */
    private Long idCondicionPersona;

    /**
     * Tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificación de la persona
     */
    private String numeroIdentificacion;

    /**
     * Razon social de la persona
     */
    private String razonSocial;

    /**
     * Primer nombre de la persona en el proceso de liquidación
     */
    private String primerNombre;

    /**
     * Pirmer apellido de la persona en el proceso de liquidación
     */
    private String primerApellido;

    /**
     * Segundo nombre de la persona en el proceso de liquidación
     */
    private String segundoNombre;

    /**
     * Segundo apellido de la persona en el proceso de liquidación
     */
    private String segundoApellido;

    /**
     * @return the idCondicionPersona
     */
    public Long getIdCondicionPersona() {
        return idCondicionPersona;
    }

    /**
     * @param idCondicionPersona
     *        the idCondicionPersona to set
     */
    public void setIdCondicionPersona(Long idCondicionPersona) {
        this.idCondicionPersona = idCondicionPersona;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     *        the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre
     *        the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido
     *        the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre
     *        the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido
     *        the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

}
