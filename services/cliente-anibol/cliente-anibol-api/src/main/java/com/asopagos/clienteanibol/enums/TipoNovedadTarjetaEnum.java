package com.asopagos.clienteanibol.enums;

public enum TipoNovedadTarjetaEnum {

	EXPEDICION("01"),
	REEXPEDICION("02"),
	BLOQUEO("03"),
	ACTUALIZACION_DATOS("06"),
	TRASLADO_SALDOS("10");
	
	private final String tipoNovedadTarjeta;
	
	TipoNovedadTarjetaEnum(String tipoNovedadTarjeta){
		this.tipoNovedadTarjeta = tipoNovedadTarjeta;
	}
	
	public String getTipoNovedadTarjeta() { return tipoNovedadTarjeta; }
	
}
