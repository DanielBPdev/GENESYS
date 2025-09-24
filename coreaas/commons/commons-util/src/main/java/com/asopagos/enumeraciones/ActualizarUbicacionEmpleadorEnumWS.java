package com.asopagos.enumeraciones;

public enum ActualizarUbicacionEmpleadorEnumWS {
    DATOS_OFICINA_PRINCIPAL("Actualizar datos de la oficina principal de la empresa."),
    DATOS_ENVIO_CORRESPONDENCIA("Trabajador dependiente"),
    DATOS_NOTIFICACION_JUDICIAL("Trabajador dependiente");

    private String descripcion;

    private ActualizarUbicacionEmpleadorEnumWS(String descripcion) {
        this.descripcion = descripcion;
    }

	public String getDescripcion() {
		return descripcion;
	}
}
