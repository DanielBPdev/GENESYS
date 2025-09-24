package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class BuscarTarjetaInDTO {

    private TipoIdentificacionEnum tipoDto;
    private String numeroIdentificacion;

    public BuscarTarjetaInDTO() {
        // Constructor por defecto
    }

    public BuscarTarjetaInDTO(TipoIdentificacionEnum tipoDto, String numeroIdentificacion) {
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
