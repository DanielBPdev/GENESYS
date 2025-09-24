package com.asopagos.subsidiomonetario.dto;

import java.util.ArrayList;

public class RespuestaCargaArchivoBloqueoDTO {
    
    private ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion;
    
    private ArrayList<String[]> lineasError;

    /**
     * @return the resultadoValidacion
     */
    public ResultadoValidacionArchivoBloqueoCMDTO getResultadoValidacion() {
        return resultadoValidacion;
    }

    /**
     * @param resultadoValidacion the resultadoValidacion to set
     */
    public void setResultadoValidacion(ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    /**
     * @return the lineasError
     */
    public ArrayList<String[]> getLineasError() {
        return lineasError;
    }

    /**
     * @param lineasError the lineasError to set
     */
    public void setLineasError(ArrayList<String[]> lineasError) {
        this.lineasError = lineasError;
    }
}
