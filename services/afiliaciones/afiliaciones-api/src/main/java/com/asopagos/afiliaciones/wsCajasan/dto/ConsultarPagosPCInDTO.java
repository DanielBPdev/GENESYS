package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class ConsultarPagosPCInDTO {

    private TipoIdentificacionEnum tipoDto;
    private String numeroIdentificacion;

    public ConsultarPagosPCInDTO() {
        // Constructor por defecto
    }

    public ConsultarPagosPCInDTO(TipoIdentificacionEnum tipoDto, String numeroIdentificacion) {
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

    