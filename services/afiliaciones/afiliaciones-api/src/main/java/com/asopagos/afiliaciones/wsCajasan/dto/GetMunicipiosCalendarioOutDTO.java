package com.asopagos.afiliaciones.wsCajasan.dto;


public class GetMunicipiosCalendarioOutDTO {
    
    private String codigoMunicipio;
    private String nombreMunicipio;

    public GetMunicipiosCalendarioOutDTO(){}

    public GetMunicipiosCalendarioOutDTO(String codigoMunicipio, String nombreMunicipio){
        this.codigoMunicipio = codigoMunicipio;
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    
    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }
}
