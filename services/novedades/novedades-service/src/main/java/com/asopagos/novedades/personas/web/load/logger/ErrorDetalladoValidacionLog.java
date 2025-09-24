package com.asopagos.novedades.personas.web.load.logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.FileCommonLog;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileCommon.exception.FileCommonException;
import co.com.heinsohn.lion.fileCommon.log.DetailedErrorLog;

/**
 * <b>Descripcion:</b> Clase que implementa la configuración custom de lion para el manejo de errores <br/>
 * <b>Módulo:</b> Asopagos - HU 13 <br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> jocorrea</a>
 */
public class ErrorDetalladoValidacionLog extends DetailedErrorLog {

    /**
     * Refrencia al Logger
     */
    private static ILogger logger = LogManager.getLogger(ErrorDetalladoValidacionLog.class);

    /**
     * 
     */
    public ErrorDetalladoValidacionLog(String fileName, Date date) {
        super();
    }

    /**
     * 
     */
    public ErrorDetalladoValidacionLog(String fileName, Date date, String fileLogName) {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void log(LineArgumentDTO lineArgument, FieldArgumentDTO fieldArgument, String message, EntityManager em)
            throws FileCommonException {
        logger.debug("Inicio log(LineArgumentDTO, FieldArgumentDTO, "+message+", EntityManager)");
        Map<String, Object> contexto = null;
        Long lineNumber = null;
        String fieldName = null;
        if (lineArgument != null && lineArgument.getContext() != null) {
            contexto = lineArgument.getContext();
            lineNumber = lineArgument.getLineNumber();
        }
        else if (fieldArgument != null && fieldArgument.getContext() != null) {
            contexto = fieldArgument.getContext();
            lineNumber = fieldArgument.getLineNumber();
            fieldName = (String) fieldArgument.getFieldValue();
        }
        else if (lineArgument == null && fieldArgument == null) {
            // en este punto no se cuenta con argumentos ni de línea ni de campo, se lanza excepción técnica
            String mensajeExcepcion = MensajesFTPErrorComunesEnum.ERROR_ARGUMENTO_COMPONENTE.getReadableMessage(message);

            throw new TechnicalException(mensajeExcepcion, new Throwable());
        }
        else if (contexto == null) {
            // los errores de estructura generan un segundo error que presenta DTO, pero no contexto.  No se deben hacer más acciones
            // en este método para que el flujo de la aplicación continue correctamente
            return;
        }
        ((List<ResultadoHallazgosValidacionArchivoDTO>) contexto.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                .add(crearHallazgo(lineNumber, fieldName, message));
        logger.debug("Linea:"+ lineNumber+" Campo:"+fieldName+" Error:"+message);
        logger.debug("Fin log(LineArgumentDTO, FieldArgumentDTO, "+message+", EntityManager)");
    }

    @Override
    public List<? extends FileCommonLog> getFileCommonLog() {
        return null;
    }

    /**
     * Método que crea un hallazgo según la información ingresada
     * 
     * @param lineNumber,
     *        numero de linea
     * @param campo,
     *        campo al que pertenece el hallazgo
     * @param errorMessage,
     *        mensaje de error
     * @return retorna el resultado de hallazgo DTO
     */
    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        String error;
        if (campo == null) {
            error = errorMessage;
        } else {
            error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        }
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
        return hallazgo;
    }
}
