package com.asopagos.dto;

public class empleadorDatosDTO {
    private String tipoDoc;
    private String numeroId;
    
    public empleadorDatosDTO(String tipoDoc, String numeroId) {
        this.tipoDoc = tipoDoc;
        this.numeroId = numeroId;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
    }

    @Override
    public String toString() {
        return "empleadorDatosDTO [tipoDoc=" + tipoDoc + ", numeroId=" + numeroId + "]";
    }

    

}
