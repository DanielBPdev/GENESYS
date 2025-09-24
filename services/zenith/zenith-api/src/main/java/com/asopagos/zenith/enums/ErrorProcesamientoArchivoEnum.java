package com.asopagos.zenith.enums;


/**
 * Enumeración que representa los posibles errores al momento de 
 * procesar los archivos de entrada para la consulta de trabajadores activos
 * 
 * @author squintero
 *
 */
public enum ErrorProcesamientoArchivoEnum {

	ERROR_MASCARA_ARCHIVO("error en mascara del archivo", "_NombreArchivoNoValido"),
	FORMATO_ARCHIVO_NO_PERMITIDO("formato archivo no permitido", "_ArchivoNoValido"),
	ERROR_ESTRUCTURA_REGISTRO_CONTROL("error de estructura registro de control", "_RegistroControlErrado"),
	ERROR_ESTRUCTURA_REGISTRO_DETALLE("error estructura registro detalle", "_ErrorEstructura"),
	ERROR_CONTENIDO_REGISTRO_DETALLE("error contenido registro detalle", "_ErrorContenido");
	
	private final String descripcion;
	private final String sufijo;
	
	/**
	 * @param descripcion
	 * @param sufijo
	 */
	private ErrorProcesamientoArchivoEnum(String descripcion, String sufijo) {
		this.descripcion = descripcion;
		this.sufijo = sufijo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return the sufijo
	 */
	public String getSufijo() {
		return sufijo;
	}
	
	
}
