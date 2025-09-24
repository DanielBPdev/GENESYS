package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;

public class ExportarInconsistenciasDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<String[]> data;
    
    private String nombreArchivo;

    /**
     * @return the data
     */
    public List<String[]> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<String[]> data) {
        this.data = data;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

}
