package com.asopagos.dto.cartera;

import java.util.List;

public class RespuestaCargueMasivoAportanteDTO {

    private String resultado;
    private List<CargueManualCotizanteAportante> cotizanteAportanteList;

    public RespuestaCargueMasivoAportanteDTO() {
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public List<CargueManualCotizanteAportante> getCotizanteAportanteList() {
        return cotizanteAportanteList;
    }

    public void setCotizanteAportanteList(List<CargueManualCotizanteAportante> cotizanteAportanteList) {
        this.cotizanteAportanteList = cotizanteAportanteList;
    }
}
