package com.asopagos.listas.exception;

/**
 * <b>Descripción:</b> Excepción lanzada por el servicio de listas para errores
 * de configuración inválida en listas y enumeraciones
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class ConfiguracionListaInvalidaException extends RuntimeException {
    

	/**
     * Constructor de la excepción que recibe mensaje de error
     * 
     * @param message El mensaje de error
     */
    public ConfiguracionListaInvalidaException(String message) {
        super(message);
    }    
    
}
