package com.asopagos.zenith.enums;

public enum RespuestaConfirmacionEnum {

	EXITOSO("EXITOSO"),
	PROCESADO_CON_ERRORES("PROCESADO CON ERRORES"),
	NO_PROCESADO("NO PROCESADO");

	private final String respuesta;
	
	RespuestaConfirmacionEnum(String respuesta){
		this.respuesta = respuesta;
	}
	
	public String getRespuesta() { return respuesta; }
}
