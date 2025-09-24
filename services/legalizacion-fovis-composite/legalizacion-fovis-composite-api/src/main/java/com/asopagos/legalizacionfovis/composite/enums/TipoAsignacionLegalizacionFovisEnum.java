package com.asopagos.legalizacionfovis.composite.enums;

/**
 * <b>Descripción:</b> Enumeración que representa los tipos de asignación que 
 * se realizan en el proceso 324 Legalizacion y Desembolso FOVIS.</b>
 *
 * @author Fabian López <flopez@heinsohn.com.co>
 */
public enum TipoAsignacionLegalizacionFovisEnum {
    
	
    BACK("Back Legalizacion FOVIS"),
    INSPECTOR("Inspector FOVIS"),
    COORDINADOR("Coordinador FOVIS");

    /**
     * Mensaje en lenguaje natural del valor del enum
     */
    private String descripcion;
    

    /**
     * Constructor del enum
     */
    private TipoAsignacionLegalizacionFovisEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

}
