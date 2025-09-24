package com.asopagos.aportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos de las personas para las cuales se va a 
 * consultar el pago de un subsidio<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class DatosConsultaSubsidioPagadoDTO implements Serializable {
    private static final long serialVersionUID = 9021667917032279658L;

    /** Tipo de identificación de la persona */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /** Número de identificación de la persona */
    private String numeroIdentificacion;
    
    /** Período para la consulta */
    private String periodo;
    
    /** Indicador de pago de subsidio para la persona */
    private Boolean tieneSubsidio;
    
    /**
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param perido
     */
    public DatosConsultaSubsidioPagadoDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String perido) {
        super();
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.periodo = perido;
        this.tieneSubsidio = Boolean.FALSE;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
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
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the tieneSubsidio
     */
    public Boolean getTieneSubsidio() {
        return tieneSubsidio;
    }

    /**
     * @param tieneSubsidio the tieneSubsidio to set
     */
    public void setTieneSubsidio(Boolean tieneSubsidio) {
        this.tieneSubsidio = tieneSubsidio;
    }
}
