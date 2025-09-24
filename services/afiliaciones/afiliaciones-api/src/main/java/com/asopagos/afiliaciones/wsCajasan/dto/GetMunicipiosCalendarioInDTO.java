package com.asopagos.afiliaciones.wsCajasan.dto;

public class GetMunicipiosCalendarioInDTO {
    
    private String codigoDepartamento;
    
    public GetMunicipiosCalendarioInDTO() {
    }

    public GetMunicipiosCalendarioInDTO(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }
    
    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }
}

    