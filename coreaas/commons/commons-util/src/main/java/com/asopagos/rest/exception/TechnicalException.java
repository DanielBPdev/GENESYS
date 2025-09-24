package com.asopagos.rest.exception;

/**
 * <b>Descripción:</b> Excepción genérica lanzada por los servicios para los
 * errores técnicos en general
 * <br/> 
 * <b>Historia de Usuario:</b> Transversal
 * <br/>
 * Genera una respuesta HTTP
 * <code>Status.INTERNAL_SERVER_ERROR(500, "Internal Server Error")</code>
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class TechnicalException extends AsopagosException {
	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción que recibe mensaje de error
	 * 
	 * @param message
	 *            Mensaje que indica la causa de la excepción
	 */
	public TechnicalException(String message, Object... params) {
		super(message, params);
	}

	/**
	 * Constructor de la excepción que recibe causa
	 * 
	 * @param causa
	 *            Recibe la causa del error
	 */
	public TechnicalException(Throwable cause) {
		super(cause);
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
	public TechnicalException(String message, Throwable causa, Object... params) {
		super(message, causa, params);
	}
	
	/**
	 * Constructor que recibe mensaje y causa del error
	 * @param message
	 * @param cause
	 */
	public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}