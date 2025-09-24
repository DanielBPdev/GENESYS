package com.asopagos.clienteanibol.enums;

public enum TipoIdentificacionEnum {

	NIT("1"),
	CEDULA_CIUDADANIA("2"),
	CEDULA_EXTRANJERIA("3"),
	TARJETA_IDENTIDAD("4"),
	REGISTRO_CIVIL("5"),
	FALTA_COMPLETAR_INFORMACION("6"),
	PASAPORTE("7"),
	NUIP("8"),
	MENOR_SIN_IDENTIFICACION("9"),
	CARNE_DIPLOMATICO("19"),
	SALVOCONDUCTO("1020"),
	PERM_ESP_PERMANENCIA("1019"),
	PERM_PROT_TEMPORAL("17");

	private final String tipoIdentificacion;
	
	TipoIdentificacionEnum(String tipoIdentificacion){
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	public String getTipoIdentificacion() { return tipoIdentificacion;}
}
