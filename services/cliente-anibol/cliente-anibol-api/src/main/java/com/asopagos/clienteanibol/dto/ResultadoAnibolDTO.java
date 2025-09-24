package com.asopagos.clienteanibol.dto;

public class ResultadoAnibolDTO {
    
    private boolean exitoso;
    private String idProceso;

    public boolean isExitoso() {
        return exitoso;
    }
    
    public boolean getExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

}
