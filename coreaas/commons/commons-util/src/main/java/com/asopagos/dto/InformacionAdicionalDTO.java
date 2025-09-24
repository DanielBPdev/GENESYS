package com.asopagos.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> Clase DTO que representa información adicional del contexto o de las plantillas del dato temporal del comunicado
 *
 * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
 */
public class InformacionAdicionalDTO implements Serializable{
    
    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = 5035472324194528181L;

    /*
     * Atributo que representa la llave que representa el objeto adicional
     */
    private String llave;
    
    /**
     * Atributo que representa el valor del objeto adicional
     */
    private  Object valor;
    
    /**
     * Constructor
     */
    public InformacionAdicionalDTO() {
        
    }

    /**
     * Constructor 
     * @param llave
     *        Atributo que representa la llave que representa el objeto adicional
     * @param valor
     *        Atributo que representa el valor del objeto adicional
     */
    public InformacionAdicionalDTO(String llave, Object valor) {
        super();
        this.llave = llave;
        this.valor = valor;
    }

    /**
     * @return the llave
     */
    public String getLlave() {
        return llave;
    }

    /**
     * @param llave the llave to set
     */
    public void setLlave(String llave) {
        this.llave = llave;
    }

    /**
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }   
}
