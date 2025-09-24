/**
 * 
 */
package com.asopagos.subsidiomonetario.pagos.enums;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Enumeración que representa los posibles estados de recepción 
 * de los resultados de reexpedición por bloqueo.
 * 
 * @author squintero
 */
@XmlEnum
public enum EstadoRecepcionResultadoEnum {

	OK("OK - RECEPCION EXITOSA"),
	NO_OK("NO_OK - RECEPCION NO EXITOSA");
	
	private String descripcion;

	private EstadoRecepcionResultadoEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
