package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class RedireccionarInDTO {

    private TipoIdentificacionEnum tipoDto;
    private String numeroIdentificacion;

    public RedireccionarInDTO() {
        // Constructor por defecto
    }

    public RedireccionarInDTO(TipoIdentificacionEnum tipoDto, String numeroIdentificacion) {
        this.tipoDto = tipoDto;
        this.numeroIdentificacion = numeroIdentificacion;
    }

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
