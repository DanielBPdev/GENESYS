package com.asopagos.afiliaciones.wsCajasan.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;



public class ConsultarInformacionDinamicoInDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private String tipoPersona;

    // Constructor vacío
    public ConsultarInformacionDinamicoInDTO() {
    }

    // Constructor con todos los campos
    public ConsultarInformacionDinamicoInDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String tipoPersona) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoPersona = tipoPersona;
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

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    @Override
    public String toString() {
        return "ConsultarInformacionDinamicoOutDTO{" +
                "tipoIdentificacion=" + tipoIdentificacion +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", tipoPersona='" + tipoPersona + '\'' +
                '}';
    }
}