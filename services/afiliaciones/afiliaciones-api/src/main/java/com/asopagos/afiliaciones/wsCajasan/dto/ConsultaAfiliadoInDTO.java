package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class ConsultaAfiliadoInDTO {
    private TipoIdentificacionEnum tipoDto;
    private String numeroIdentificacion;

    // Getters y setters
    public TipoIdentificacionEnum getTipoDto() {
        return tipoDto;
    }

    public void setTipoDto(TipoIdentificacionEnum tipoDto) {
        this.tipoDto = tipoDto;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}
