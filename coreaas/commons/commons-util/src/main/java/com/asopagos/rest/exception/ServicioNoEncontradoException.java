package com.asopagos.rest.exception;

import com.asopagos.constants.MensajesGeneralConstants;

/**
 * <b>Descripción:</b> Excepción generica lanzada en el momento que un 
 * cliente REST no pude encontrar el servicio
 * <br/>
 * <b>Historia de Usuario:</b> Transversal
 * <br/>
 * Genera una respuesta HTTP
 * <code>Status.NOT_FOUND(404, "Not Found")</code>
 * 
 * @author Juan Diego Ocampo Q <jocampo@heinsohn.com.co>
 */
public class ServicioNoEncontradoException extends AsopagosException {
    /**
     * Default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepción que recibe mensaje de error
     *
     * @param message
     *        the String that is the entity of the 404 response.
     * @param params
     *        Parámetros para interpolar en el mensaje de la excepción
     */
    public ServicioNoEncontradoException(String message, Object... params) {
        super(message);
    }

    /**
     * Constructor de la excepción que utiliza el mensaje por defecto para esta
     * excepción </br>
     * Siempre debe indicarse un mesaje sobre el recurso que no se encuetra
     */
    @Deprecated
    public ServicioNoEncontradoException() {
        super(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
    }

    /**
     * Constructor de la excepción que recibe causa
     * 
     * @param causa
     *        Recibe la causa del error
     */
    public ServicioNoEncontradoException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor de la excepción que recibe mensaje de error
     * 
     * @param message
     *        El mensaje de error
     * @param causa
     *        Causa de la excepción
     * @param params
     *        Parámetros para interpolar en el mensaje de la excepción
     */
    public ServicioNoEncontradoException(String message, Throwable causa, Object... params) {
        super(message, causa, params);
    }
}