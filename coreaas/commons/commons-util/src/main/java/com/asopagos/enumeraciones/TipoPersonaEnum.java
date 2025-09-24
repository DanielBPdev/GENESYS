package com.asopagos.enumeraciones;

/**
 * <b>Descripcion:</b> Enumeración en la que se listan los tipos de persona definidos en el 
 * Decreto 2388 de 2016 <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public enum TipoPersonaEnum {
    JURIDICA ("J", "Jurídica"),
    NATURAL ("N", "Natural");

    
    /** Código */
    private String codigo;
    
    /** Descripción del tipo */
    private String descripcion;
    
    /** Constructor de la enumeración */
    private TipoPersonaEnum (String codigo, String descripcion){
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    /**
     * Método para obtener una entrada de enumeración a partir de un código de tipo de persona
     * 
     * @param cogigo
     *        Código de tipo de persona consultado
     * @return <b>TipoPersonaEnum</b>
     *         Valor de enumeración correspondiente al código ingresado
     * */
    public static TipoPersonaEnum obtenerTipoPersona(String codigo){
        for (TipoPersonaEnum tipo : TipoPersonaEnum.values()) {
            if(tipo.getCodigo().equals(codigo)){
                return tipo;
            }
        }
        return null;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
