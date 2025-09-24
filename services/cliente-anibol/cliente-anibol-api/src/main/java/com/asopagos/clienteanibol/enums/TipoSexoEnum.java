package com.asopagos.clienteanibol.enums;

public enum TipoSexoEnum {

	MASCULINO("M"),
	FEMENINO("F");
	
	private final String tipoSexo;
	
	TipoSexoEnum(String tipoSexo){
		this.tipoSexo = tipoSexo;
	}
	
	public String getTipoSexo() { return tipoSexo; }
}
