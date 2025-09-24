package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class ValidaEmpresaInDTO {
    private String numeroIdentificacion;
    private TipoIdentificacionEnum tipoIdentificacion;
    private long codigoActividad;

    public ValidaEmpresaInDTO() {
        // Constructor por defecto
    }

    public ValidaEmpresaInDTO(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion, long codigoActividad) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.codigoActividad = codigoActividad;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public long getCodigoActividad() {
        return codigoActividad;
    }
    public void setCodigoActividad(long codigoActividad) {
        this.codigoActividad = codigoActividad;
    }
}