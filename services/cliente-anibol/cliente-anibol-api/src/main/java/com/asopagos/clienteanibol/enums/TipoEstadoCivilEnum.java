package com.asopagos.clienteanibol.enums;

public enum TipoEstadoCivilEnum {

	SOLTERO("1"),
	SEPARADO("2"),
	VIUDO("3"),
	UNION_LIBRE("4"),
	CASADO("5"),
	FALTA_COMPLETAR_INFORMACION("9");
	
	private final String tipoEstadoCivil;
	
	TipoEstadoCivilEnum(String tipoEstadoCivil){
		this.tipoEstadoCivil = tipoEstadoCivil;
	}
	
	public String getTipoSexo() { return tipoEstadoCivil; }
	
}
