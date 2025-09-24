package com.asopagos.personas.constants;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum TipoMovimientoHistoricoAccesoEnum {

	ACTIVACION("Activo"),

	INACTIVACION("Inactivo"),
	
	ACTUALIZACION_DATOS("Actualización de datos de cuenta portal privado");


	/**
	 * Mensaje en lenguaje natural del valor del enum
	 */
	private String descripcion;

	/**
     * Constructor del enum
     */
    private TipoMovimientoHistoricoAccesoEnum(String descripcion) {    
        this.descripcion = descripcion;
    }

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
