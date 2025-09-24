package com.asopagos.dto.webservices;

import java.io.Serializable;

import javax.validation.constraints.*;

public class ConsultaCargueDTO implements Serializable{
    
    private String numeroRadicado;

    @NotNull(message = "El campo tipoConsulta es obligatorio.")
    private int tipoConsulta;

    private Long idCargue;

    public ConsultaCargueDTO() {
    }

    public ConsultaCargueDTO(String numeroRadicado, int tipoConsulta, Long idCargue) {
        this.numeroRadicado = numeroRadicado;
        this.tipoConsulta = tipoConsulta;
        this.idCargue = idCargue;
    }

    public String getNumeroRadicado() {
        return this.numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public int getTipoConsulta() {
        return this.tipoConsulta;
    }

    public void setTipoConsulta(int tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Long getIdCargue() {
        return this.idCargue;
    }

    public void setIdCargue(Long idCargue) {
        this.idCargue = idCargue;
    }

    public ConsultaCargueDTO numeroRadicado(String numeroRadicado) {
        setNumeroRadicado(numeroRadicado);
        return this;
    }

    public ConsultaCargueDTO tipoConsulta(int tipoConsulta) {
        setTipoConsulta(tipoConsulta);
        return this;
    }

    public ConsultaCargueDTO idCargue(Long idCargue) {
        setIdCargue(idCargue);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " numeroRadicado='" + getNumeroRadicado() + "'" +
            ", tipoConsulta='" + getTipoConsulta() + "'" +
            ", idCargue='" + getIdCargue() + "'" +
            "}";
    }
}
