package com.asopagos.pila.validadores.controlerrores;

import java.util.Date;
import co.com.heinsohn.lion.fileCommon.exception.FileCommonException;
import co.com.heinsohn.lion.fileCommon.log.SummarizedErrorLog;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ErroresValidacionGeneralLog extends SummarizedErrorLog {
    
    public ErroresValidacionGeneralLog(String fileName, Date date, String fileLogName) { 
        // implementación vacía
    }
    
    public ErroresValidacionGeneralLog(String fileName, Date date) { 
        // implementación vacía
    }

    /** (non-Javadoc)
     * @see co.com.heinsohn.lion.fileCommon.log.SummarizedErrorLog#log(java.lang.String)
     */
    @Override
    public void log(String message) throws FileCommonException {
        // implementación vacía
    }

    /** (non-Javadoc)
     * @see co.com.heinsohn.lion.fileCommon.log.SummarizedErrorLog#close()
     */
    @Override
    public void close() throws FileCommonException{
        // implementación vacía
    }
}
