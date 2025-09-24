package com.asopagos.pila.dto;
import java.util.List;
import com.asopagos.pila.dto.CriteriosBusquedaArchivosProcesados;

import java.io.Serializable;

public class DatosArchvioReporteDTO implements Serializable {

    private static final long serialVersionUID = 5165142646354284L;

    private List<String> cabeceras;
    private CriteriosBusquedaArchivosProcesados datos;


    public List<String> getCabeceras() {
        return this.cabeceras;
    }

    public void setCabeceras(List<String> cabeceras) {
        this.cabeceras = cabeceras;
    }

    public CriteriosBusquedaArchivosProcesados getDatos() {
        return this.datos; 
    }

    public void setDatos(CriteriosBusquedaArchivosProcesados datos) {
        this.datos = datos; 
    }


}