package com.asopagos.rest.exception;

/**
 * <b>Descripción:</b> Excepción generica lanzada por los servicios para los
 * errores de verificaciones en general <b>Historia de Usuario:</b> Transversal
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 * 
 * @deprecated se reemplaza por FunctionalConstraintException
 * @see com.asopagos.rest.exception.FunctionalConstraintException
 */
@Deprecated
public class ValidacionFallidaException extends AsopagosException {
	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción que recibe mensaje de error
	 * 
	 * @param message
	 *            El mensaje de error
	 * @param params
	 *            Parámetros para interpolar en el mensaje de la excepción
	 */
	public ValidacionFallidaException(String message, Object... params) {
		super(message, params);
	}
}
