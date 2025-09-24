package com.asopagos.rest.exception;

/**
 * <b>Descripción:</b> Excepción que identifica los errores producidos en el
 * lado del BPM durante la invocación a su API REST <b>Historia de Usuario:</b>
 * Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class BPMSExecutionException extends AsopagosException {
	/**
	 * Default Serial Version
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
	public BPMSExecutionException(String message, Object... params) {
		super(message, params);
	}
}
