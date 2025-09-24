package com.asopagos.subsidiomonetario.pagos.enums;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum ComparacionSaldoTarjetaEnum {

	MAYOR("saldo mayor en genesys"),
	MENOR("saldo menor en genesys"),
	IGUAL("saldos iguales"),
	IGUALES_CERO("saldos iguales con valor cero");
	
	private String descripcion;

	private ComparacionSaldoTarjetaEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	public ComparacionSaldoTarjetaEnum validarResultadoComparacion(int valor){
		return valor == 0 ? this.IGUAL : (valor > 0 ? this.MAYOR : this.MENOR);
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
