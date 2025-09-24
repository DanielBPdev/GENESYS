package com.asopagos.enumeraciones;

import javax.xml.bind.annotation.XmlEnum;

/**
 * <b>Descripción:</b> Enumeración con los tipos de postulación FOVIS a realizar
 * <b>Historia de Usuario: </b>321-020
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@XmlEnum
public enum TipoPostulacionFOVISEnum {

	ABREVIADA("Abreviada"),
	COMPLETA("Completa"),
	ESCALADA("Escalada");

	/**
	 * Variable para la descripción de la enumeración.
	 */
    private String descripcion;

    /**
     * Constructor del la enumeración.
     * @param descripcion de la enumeración.
     */
    private TipoPostulacionFOVISEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
	 * Método que retorna el valor de descripcion.
	 * @return valor de descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}
}