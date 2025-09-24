package com.asopagos.dto.modelo;

import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import java.io.Serializable;

public class DatosArchivoCargueExclusionMoralParcialDTO implements Serializable {

    private static final long serialVersionUID = 5165142646354284L;

    private List<String> cabeceras;
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;


    public List<String> getCabeceras() {
        return this.cabeceras;
    }

    public void setCabeceras(List<String> cabeceras) {
        this.cabeceras = cabeceras;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    @Override
    public String toString() {
        return "{" +
            " cabeceras='" + getCabeceras() + "'" +
            ", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            "}";
    }

}