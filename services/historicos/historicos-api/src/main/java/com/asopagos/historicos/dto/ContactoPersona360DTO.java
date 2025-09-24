package com.asopagos.historicos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactoPersona360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<String> historialTelFijo;
    private List<String> historialCelular;
    private List<String> historialCorreo;
    
    /**
     * 
     */
    public ContactoPersona360DTO() {
    }
    
    /**
     * @param historialTelFijo
     * @param historialCelular
     * @param historialCorreo
     */
    public ContactoPersona360DTO(List<String> historialTelFijo, List<String> historialCelular, List<String> historialCorreo) {
        this.historialTelFijo = historialTelFijo;
        this.historialCelular = historialCelular;
        this.historialCorreo = historialCorreo;
    }
    /**
     * @return the historialTelFijo
     */
    public List<String> getHistorialTelFijo() {
        return historialTelFijo;
    }
    /**
     * @param historialTelFijo the historialTelFijo to set
     */
    public void setHistorialTelFijo(List<String> historialTelFijo) {
        this.historialTelFijo = historialTelFijo;
    }
    /**
     * @return the historialCelular
     */
    public List<String> getHistorialCelular() {
        return historialCelular;
    }
    /**
     * @param historialCelular the historialCelular to set
     */
    public void setHistorialCelular(List<String> historialCelular) {
        this.historialCelular = historialCelular;
    }
    /**
     * @return the historialCorreo
     */
    public List<String> getHistorialCorreo() {
        return historialCorreo;
    }
    /**
     * @param historialCorreo the historialCorreo to set
     */
    public void setHistorialCorreo(List<String> historialCorreo) {
        this.historialCorreo = historialCorreo;
    }
    
}
