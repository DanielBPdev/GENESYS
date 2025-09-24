package com.asopagos.clienteanibol.enums;

public enum TipoPersonaEnum {

	JURIDICA("J"),
	NATURAL("N");

	private final String tipoPersona;
	
	TipoPersonaEnum(String tipoPersona){
		this.tipoPersona = tipoPersona;
	}
	
	public String getTipoPersona() { return tipoPersona; }
}
