package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import java.io.Serializable;

public class FiltroIdPersonaDTO implements Serializable {
    private static final long serialVersionUID = 8799656478674716638L;

    /**
     * Descripción del tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación de la persona
     */
    private String numeroIdentificacion;

    public FiltroIdPersonaDTO() {
    }

    public FiltroIdPersonaDTO(Persona persona) {
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}
