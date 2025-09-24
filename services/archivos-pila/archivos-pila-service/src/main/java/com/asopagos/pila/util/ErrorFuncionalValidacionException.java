package com.asopagos.pila.util;

import static com.asopagos.util.Interpolator.interpolate;

/**
 * <b>Descripcion:</b> Excepción presentada por los validadores de orquestador y que se relacionan con inconsistencias funcionales <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ErrorFuncionalValidacionException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     * @param params
     */
    public ErrorFuncionalValidacionException(String message, Throwable cause, Object... params){
        super(interpolate(message, params), cause);
    }
    
    /**
     * @param message
     * @param params
     */
    public ErrorFuncionalValidacionException(String message, Object... params) {
        super(interpolate(message, params));
    }
    
    /**
     * @param cause
     */
    public ErrorFuncionalValidacionException(Throwable cause) {
        super(cause);
    }
}
