package com.asopagos.enumeraciones;

public enum TiposUsuarioWebServiceEnum {

    USUARIO_PERSONA("Usuario persona"),
    USUARIO_EMPRESA("Usuario empresa"),
    USUARIO_TERCEROS("Usuario  terceros");

    private String descripcion;

    private TiposUsuarioWebServiceEnum(String descripcion) {
        this.descripcion = descripcion;
    }

	public String getDescripcion() {
		return descripcion;
	}
}
