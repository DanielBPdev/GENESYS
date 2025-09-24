package com.asopagos.enumeraciones;

public enum TipoConsultanteAportesWS {
    APORTANTE("Empleador, trabajador independiente"),
    COTIZANTE("Trabajador dependiente");

    private String descripcion;

    private TipoConsultanteAportesWS(String descripcion) {
        this.descripcion = descripcion;
    }

	public String getDescripcion() {
		return descripcion;
	}
}
