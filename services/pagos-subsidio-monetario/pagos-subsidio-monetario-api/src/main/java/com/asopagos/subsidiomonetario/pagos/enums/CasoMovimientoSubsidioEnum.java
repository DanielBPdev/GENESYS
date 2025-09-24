package com.asopagos.subsidiomonetario.pagos.enums;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum CasoMovimientoSubsidioEnum {

	CASO_1("El saldo de la tarjeta (Objeto bloqueo) es igual al saldo de la tarjeta nueva (expedida)"),
	
	CASO_2("El saldo de la tarjeta bloqueada es mayor al saldo de la tarjeta nueva");
	
	private String descripcion;

	private CasoMovimientoSubsidioEnum(String descripcion) {
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