package com.asopagos.rest.exception;

/**
 * <b>Descripción:</b> Excepción genérica lanzada por los servicios para los
 * errores funcionales o de flujo de datos y/o negocio 
 * <br>
 * <b>Historia de Usuario:</b> Transversal
 * <br>
 * Genera una respuesta HTTP
 * <code>Status.CONFLICT(409, "Conflict")</code>
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class FunctionalConstraintException extends AsopagosException {

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
    public FunctionalConstraintException(String message, Object... params) {
        super(message, params);
    }

    /**
     * Constructor de la excepción que recibe causa
     * 
     * @param causa
     *            Recibe la causa del error
     */
    public FunctionalConstraintException(Throwable cause) {
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
    public FunctionalConstraintException(String message, Throwable causa, Object... params) {
        super(message, causa, params);
    }
    
}
