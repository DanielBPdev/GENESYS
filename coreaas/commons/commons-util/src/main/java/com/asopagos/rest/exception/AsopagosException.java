package com.asopagos.rest.exception;

import static com.asopagos.util.Interpolator.interpolate;

/**
 * Representa la Excepción base del proyecto ASOPAGOS
 * 
 * @author <a href="mailto:ogiral@heinsohn.com.co">Leonardo Giral</a>
 *
 */
public abstract class AsopagosException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 * @param params
	 */
	public AsopagosException(String message, Throwable cause, Object... params) {
		super(interpolate(message, params), cause);
	}
	/**
	 * @param message
	 * @param params
	 */
	public AsopagosException(String message, Object... params) {
		super(interpolate(message, params));
	}
	/**
	 * @param cause
	 */
	public AsopagosException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public AsopagosException(String message, Throwable cause) {
	    super(message, cause);
	}

}
