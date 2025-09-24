package com.asopagos.dto.modelo;
import java.io.Serializable;

public class ResultadoEnvioComunicadoCarteraDTO implements Serializable {
    private Long idComunicado;
    private Boolean envioComunicadosResultado;

    public Long getIdComunicado() {
        return this.idComunicado;
    }

    public void setIdComunicado(Long idComunicado) {
        this.idComunicado = idComunicado;
    }

    public Boolean isEnvioComunicadosResultado() {
        return this.envioComunicadosResultado;
    }

    public Boolean getEnvioComunicadosResultado() {
        return this.envioComunicadosResultado;
    }

    public void setEnvioComunicadosResultado(Boolean envioComunicadosResultado) {
        this.envioComunicadosResultado = envioComunicadosResultado;
    }

    @Override
    public String toString() {
        return "{" +
            " idComunicado='" + getIdComunicado() + "'" +
            ", envioComunicadosResultado='" + isEnvioComunicadosResultado() + "'" +
            "}";
    }
    
}