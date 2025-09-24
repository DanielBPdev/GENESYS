package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;

public class CategoriaSTDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
    * Fecha Para Cada Cambio
    */
    private String fecha;
    
    /**
    * Categoría del afiliado
    */
    private CategoriaPersonaEnum categoria;
    
    /**
     * 
     */
    public CategoriaSTDTO() {
    }

    /**
     * @param fecha
     * @param categoria
     */
    public CategoriaSTDTO(String fecha, CategoriaPersonaEnum categoria) {
        this.fecha = fecha;
        this.categoria = categoria;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the categoria
     */
    public CategoriaPersonaEnum getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaPersonaEnum categoria) {
        this.categoria = categoria;
    }
}
