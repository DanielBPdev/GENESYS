package com.asopagos.rest.exception;

/**
 * Indica que el acceso a un recurso no es permitido.
 * <br/>
 * Genera una respuesta HTTP
 * <code>Status.UNAUTHORIZED(401, "Unauthorized")</code>
 * 
 * @author alopez
 */
public class RecursoNoAutorizadoException extends AsopagosException {

	/**
	 * Constructor de la excepción que recibe el mensaje de error junto con sus parametros
	 * @param message Mensaje de error
	 * @param params Parametros del mensaje de error
	 */
	public RecursoNoAutorizadoException(String message, Object... params) {
		super(message, params);
	}

	/**
	 * Constructor de la excepción que recibe mensaje de error
	 * 
	 * @param message
	 *            El mensaje de error
	 * @param causa
	 *            Causa de la excepción
	 * @param params
	 *            Parámetros para interpolar en el mensaje de la excepción
	 */
	public RecursoNoAutorizadoException(String message, Throwable cause, Object... params) {
		super(message, cause, params);
		
	}

	/**
	 * Constructor de la excepción que recibe causa
	 * 
	 * @param causa
	 *            Recibe la causa del error
	 */
	public RecursoNoAutorizadoException(Throwable cause) {
		super(cause);
	}

	/**
	 * Número serial de la versión
	 */
	private static final long serialVersionUID = 1L;

}
