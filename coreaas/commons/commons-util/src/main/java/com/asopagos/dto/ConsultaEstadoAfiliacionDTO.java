package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos de una consulta de estado de afiliación<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConsultaEstadoAfiliacionDTO implements Serializable {
    private static final long serialVersionUID = -6073518204628918636L;
    
    /** Estado de la afiliación */
    private EstadoAfiliadoEnum estadoAfiliacion;
    
    /** Fecha del último retiro (respecto a la CCF) */
    private Long fechaRetiro;
    
    /** ID de registro en tabla Persona */
    private Long idPersona;
    
    /** Tipo de identificación de la persona */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /** Número de identificación de la persona */
    private String numeroIdentificacion;

    /**
     * @return the estadoAfiliacion
     */
    public EstadoAfiliadoEnum getEstadoAfiliacion() {
        return estadoAfiliacion;
    }

    /**
     * @param estadoAfiliacion the estadoAfiliacion to set
     */
    public void setEstadoAfiliacion(EstadoAfiliadoEnum estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }

    /**
     * @return the fechaRetiro
     */
    public Long getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(Long fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
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
}
