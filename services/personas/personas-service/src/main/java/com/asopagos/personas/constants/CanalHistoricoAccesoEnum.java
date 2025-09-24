package com.asopagos.personas.constants;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum CanalHistoricoAccesoEnum {

	AFILIACION_EMPLEADOR("Afiliación de empleador"),

	EXPIRACION_AFILIACION("Expiracion de tiempo de desafiliación"), 
	
	MANUAL("Se realizo desde vista 360"),

	AUTOGESTION("El empleador lo realizo desde el portal web");

	/**
	 * Mensaje en lenguaje natural del valor del enum
	 */
	private String descripcion;

	/**
	 * Constructor del enum
	 */
	private CanalHistoricoAccesoEnum(String descripcion) {
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
