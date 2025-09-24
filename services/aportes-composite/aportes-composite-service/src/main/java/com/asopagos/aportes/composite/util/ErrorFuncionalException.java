package com.asopagos.aportes.composite.util;

import static com.asopagos.util.Interpolator.interpolate;

/**
 * <b>Descripcion:</b> Clase que representa excepciones funcionales presentadas en el funcionamiento del módulo <br/>
 * <b>Módulo:</b> Asopagos - HU-211-397, HU-211-403, HU-211-404, HU-211-405, HU-211-399, HU-211-392<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ErrorFuncionalException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     * @param params
     */
    public ErrorFuncionalException(String message, Throwable cause, Object... params){
        super(interpolate(message, params), cause);
    }
    
    /**
     * @param message
     * @param params
     */
    public ErrorFuncionalException(String message, Object... params) {
        super(interpolate(message, params));
    }
    
    /**
     * @param cause
     */
    public ErrorFuncionalException(Throwable cause) {
        super(cause);
    }

}
