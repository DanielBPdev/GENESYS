package com.asopagos.enumeraciones;

/**
 * <b>Descripción: </b> Enumeración que representa los diferentes tipos de
 * validación para un registro de oferente <br/>
 * <b>Historia de Usuario: </b> HU-055
 *
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public enum NombreValidacionEnum {
	VALIDACION_MOTIVO_DESAFILIACION_EMPLEADOR_INACTIVO("Validación de motivo de desafiliación del empleador (estado \"Inactivo\")"),
	VALIDACION_MOTIVO_DESAFILIACION_PERSONA_INACTIVO("Validación de motivo de desafiliación de persona (estado \"Inactivo\")"),
	VALIDACION_MOTIVO_DESAFILIACION_INACTIVO("Validación de motivo de desafiliación \"Inactivo\""),
	VALIDACION_ESTADO_MOROSIDAD("Validación de estado de morosidad (diferente a estado \"al día\")"),
	VALIDACION_REGISTRADO_BENEFICIARIO("Validación cuando persona registrada sea un beneficiario");

	/**
	 * Descripción del tipo
	 */
	private String descripcion;

	/**
	 * Constructor
	 */
	private NombreValidacionEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método para obtener una entrada de la enumeración a partir de su
	 * descripción
	 * 
	 * @param descripcion
	 *            Campo <code>descripcion</code> en la enumeración
	 * @return La enumeración correspondiente
	 */
	public static NombreValidacionEnum obtenerPorDescripcion(String descripcion) {
		for (NombreValidacionEnum tipo : NombreValidacionEnum.values()) {
			if (tipo.getDescripcion().equals(descripcion)) {
				return tipo;
			}
		}

		return null;
	}

	/**
	 * Obtiene el valor de descripcion
	 * 
	 * @return El valor de descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
}
