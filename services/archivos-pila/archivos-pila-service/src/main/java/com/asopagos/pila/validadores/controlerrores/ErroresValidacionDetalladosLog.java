package com.asopagos.pila.validadores.controlerrores;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.TipoRegistroArchivoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.ComposicionMensajeEstructuraDTO;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.rest.exception.TechnicalException;
import co.com.heinsohn.lion.fileCommon.FileCommonLog;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileCommon.exception.FileCommonException;
import co.com.heinsohn.lion.fileCommon.log.DetailedErrorLog;

/**
 * <b>Descripcion:</b> Clase que implementa los métodos abstractos empleados por el complemento FileProccessor
 * para la gestión de errores en modalidad CUSTOM <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ErroresValidacionDetalladosLog extends DetailedErrorLog {
    /**
     * Refrencia al Logger
     */
    private static ILogger logger = LogManager.getLogger(ErroresValidacionDetalladosLog.class);

    /** FileLogName empleado para distinguir las excepciones por error de estructura de las inconsistencias de negocio */
    private String fileLogName;

    /** Constructor para casos de errores de negocio que incluyen fileLogName */
    public ErroresValidacionDetalladosLog(String fileName, Date date, String fileLogName) {
        this.fileLogName = fileLogName;
    }

    /** Constructor para casos de errores de estructura que no incluyen fileLogName */
    public ErroresValidacionDetalladosLog(String fileName, Date date) {
    }

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileCommon.log.DetailedErrorLog#log(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO,
     *      co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO, java.lang.String, javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void log(LineArgumentDTO lineArgument, FieldArgumentDTO fieldArgument, String message, EntityManager em)
            throws FileCommonException {
        String firmaMetodo = "ErroresValidacionDetalladosLog.log(LineArgumentDTO, FieldArgumentDTO, String, EntityManager)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String messageTemp = message;
        List<ComposicionMensajeEstructuraDTO> mensajesSeparados = new ArrayList<>();

        Map<String, Object> contexto = null;

        // se toma el listado de errores del contexto
        List<ErrorDetalladoValidadorDTO> listadoErrores = null;
        
        // listado de líneas vacías
        Set<Long> lineasVacias = null;

        // listado de tipos de registros faltantes
        Map<TipoRegistroArchivoEnum, Set<Long>> listadoRegistrosFaltantes = null;

        Long lineNumber = null;
        BloqueValidacionEnum bloque = null;

        // se toma el número de línea y el bloque del argumento de línea o campo
        if (lineArgument != null && lineArgument.getContext() != null) {
            contexto = lineArgument.getContext();
            lineNumber = lineArgument.getLineNumber();
        }
        else if (fieldArgument != null && fieldArgument.getContext() != null) {
            contexto = fieldArgument.getContext();
            lineNumber = fieldArgument.getLineNumber();
        }
        else if (lineArgument == null && fieldArgument == null) {
            // en este punto no se cuenta con argumentos ni de línea ni de campo, se lanza excepción técnica
            String mensajeExcepcion = MensajesFTPErrorComunesEnum.ERROR_ARGUMENTO_COMPONENTE.getReadableMessage(messageTemp);

            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensajeExcepcion);
            throw new TechnicalException(mensajeExcepcion, new Throwable());
        }
        else if (contexto == null) {
            // los errores de estructura generan un segundo error que presenta DTO, pero no contexto.  No se deben hacer más acciones
            // en este método para que el flujo de la aplicación continue correctamente
            return;
        }

        // sí la línea que presenta el error, está en la lista de líneas vacías, se ignora el error
        lineasVacias = (Set<Long>) contexto.get(ConstantesContexto.LISTADO_LINEAS_VACIAS);
        if(lineasVacias != null && lineasVacias.contains(lineNumber)){
            return;
        }
        
        
        listadoErrores = (List<ErrorDetalladoValidadorDTO>) contexto.get(ConstantesContexto.ERRORES_DETALLADOS);
        listadoRegistrosFaltantes = (Map<TipoRegistroArchivoEnum, Set<Long>>) contexto.get(ConstantesContexto.LISTA_REGISTROS_FALTANTES);
        bloque = (BloqueValidacionEnum) contexto.get(ConstantesContexto.BLOQUE);

        boolean listadoNuevo = Boolean.FALSE;
        boolean listadoRegistrosNuevo = Boolean.FALSE;

        if (listadoErrores == null) {
            // cuando no se puede obtener un listado de errores desde los argumentos, se le agrega uno nuevo
            listadoErrores = new ArrayList<>();
            listadoNuevo = Boolean.TRUE;
        }

        // sí el mensaje no contiene la expresión para la separación del mensaje de error, se trata como error de estructura
        if (!messageTemp.contains(ConstantesComunesProcesamientoPILA.SEPARADOR_MENSAJE_ERROR)) {
            mensajesSeparados = FuncionesValidador.componerErrorEstructura(messageTemp);
        }
        else {
            ComposicionMensajeEstructuraDTO mensaje = new ComposicionMensajeEstructuraDTO();
            mensaje.setMensaje(messageTemp);
            mensajesSeparados.add(mensaje);
        }

        for (ComposicionMensajeEstructuraDTO mensajeDTO : mensajesSeparados) {
            String mensajeIndividual = mensajeDTO.getMensaje();
            TipoRegistroArchivoEnum tipoRegistro = mensajeDTO.getTipoRegistro();
            if (mensajeDTO.getErrorGeneralEstructura() && TipoRegistroArchivoEnum.REGISTRO_2_OI.equals(tipoRegistro)) {
                Long lineaEstructuraAnterior = (Long) contexto.get(ConstantesContexto.LINEA_ESTRUCTURA_ANTERIOR);
                contexto.replace(ConstantesContexto.CONTADOR_REGISTROS_2,
                        ((int) contexto.get(ConstantesContexto.CONTADOR_REGISTROS_2)) + 1);

                // se actualiza el número de línea de error de estructura
                if (lineaEstructuraAnterior == null) {
                    contexto.put(ConstantesContexto.LINEA_ESTRUCTURA_ANTERIOR, lineNumber);
                }
                else {
                    contexto.replace(ConstantesContexto.LINEA_ESTRUCTURA_ANTERIOR, lineNumber);
                }
            }

            if (mensajeIndividual == null) {
                String mensajeError = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_NO_RECONOCIDO.getReadableMessage(message);
                logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensajeError);
            }
            else {
                ErrorDetalladoValidadorDTO error = null;

                // a partir del mensaje, se identifica sí se trata de un error único o si involucra múltiples campos
                if (!mensajeIndividual.contains(ConstantesComunesProcesamientoPILA.SEPARADOR_CAMPOS_ERROR)) {
                    error = FuncionesValidador.prepararError(mensajeIndividual, bloque, lineNumber);
                    agregarError(listadoErrores, listadoRegistrosFaltantes, tipoRegistro, error, mensajeDTO);
                }
                else {
                    String[] mensajeSeparado = mensajeIndividual.split(ConstantesComunesProcesamientoPILA.SEPARADOR_MENSAJE_ERROR);
                    String[] codigosCampos = null;
                    String[] valoresCampos = null;

                    // el mensaje debe estar dividido en 5 partes, la segunda posición tiene los códigos de campo y la tercera sus valores
                    if (mensajeSeparado.length == 5) {
                        codigosCampos = mensajeSeparado[1].split(ConstantesComunesProcesamientoPILA.SEPARADOR_CAMPOS_ERROR);
                        valoresCampos = mensajeSeparado[2].split(ConstantesComunesProcesamientoPILA.SEPARADOR_CAMPOS_ERROR);

                        if (codigosCampos.length == valoresCampos.length) {

                            for (int i = 0; i < codigosCampos.length; i++) {
                                // por cada juego de campo/valor, se reconstruye un mensaje de error independiente para ser agregado al listado
                                String nuevoMensaje = mensajeSeparado[0] + ConstantesComunesProcesamientoPILA.SEPARADOR_MENSAJE_ERROR
                                        + codigosCampos[i] + ConstantesComunesProcesamientoPILA.SEPARADOR_MENSAJE_ERROR + valoresCampos[i]
                                        + ConstantesComunesProcesamientoPILA.SEPARADOR_MENSAJE_ERROR + mensajeSeparado[3]
                                        + ConstantesComunesProcesamientoPILA.SEPARADOR_MENSAJE_ERROR + mensajeSeparado[4];

                                error = FuncionesValidador.prepararError(nuevoMensaje, bloque, lineNumber);

                                agregarError(listadoErrores, listadoRegistrosFaltantes, tipoRegistro, error, mensajeDTO);
                            }
                        }
                        else {
                            // sí la cantidad de campos no concuerda con la cantidad de valores, significa un error en la configuración
                            String mensajeExcepcion = MensajesFTPErrorComunesEnum.ERROR_PARAMETRIZACION_COMPONENTE
                                    .getReadableMessage(mensajeIndividual);

                            logger.debug("Finaliza log(LineArgumentDTO, FieldArgumentDTO, String, EntityManager) - " + mensajeExcepcion);
                            throw new TechnicalException(mensajeExcepcion, new Throwable());
                        }
                    }
                    else {
                        // sí no tiene 5 partes, se le trata como error único
                        error = FuncionesValidador.prepararError(mensajeIndividual, bloque, lineNumber);

                        agregarError(listadoErrores, listadoRegistrosFaltantes, tipoRegistro, error, mensajeDTO);
                    }
                }

                // se actualiza el contexto
                if (contexto != null && listadoNuevo) {
                    contexto.put(ConstantesContexto.ERRORES_DETALLADOS, listadoErrores);
                }
                else if (contexto != null && !listadoNuevo) {
                    contexto.replace(ConstantesContexto.ERRORES_DETALLADOS, listadoErrores);
                }

                if (contexto != null && listadoRegistrosNuevo) {
                    contexto.put(ConstantesContexto.LISTA_REGISTROS_FALTANTES, listadoRegistrosFaltantes);
                }
                else if (contexto != null && !listadoRegistrosNuevo) {
                    contexto.replace(ConstantesContexto.LISTA_REGISTROS_FALTANTES, listadoRegistrosFaltantes);
                }
            }

        }

        logger.debug("Finaliza log(LineArgumentDTO, FieldArgumentDTO, String, EntityManager)");
    }

    private void agregarError(List<ErrorDetalladoValidadorDTO> listadoErrores,
            Map<TipoRegistroArchivoEnum, Set<Long>> listadoRegistrosFaltantes, TipoRegistroArchivoEnum tipoRegistroError,
            ErrorDetalladoValidadorDTO error, ComposicionMensajeEstructuraDTO mensajeDTO) {

        Set<Long> listaLineasRegistro = null;
        
        if(listadoRegistrosFaltantes.containsKey(tipoRegistroError)) {
        	listaLineasRegistro = listadoRegistrosFaltantes.get(tipoRegistroError);
        }
        if (listaLineasRegistro == null || !listaLineasRegistro.contains(error.getLineNumber())) {
            /*
             * sí el código de error corresponde a la falta de una línea requerida, se elimina el # de línea asignado por el componente para
             * evitar confusiones al usuario
             */
            if (error.getCodigoError() != null
                    && error.getCodigoError().equals(ConstantesComunesProcesamientoPILA.CODIGO_ERROR_LINEA_FALTANTE)) {
                error.setLineNumber(null);
            }

            listadoErrores.add(error);
        }

        if (mensajeDTO.getErrorExcluyente() != null && mensajeDTO.getErrorExcluyente() && listaLineasRegistro != null) {
            listaLineasRegistro.add(error.getLineNumber());
        }
        else if (mensajeDTO.getErrorExcluyente() != null && mensajeDTO.getErrorExcluyente() && listaLineasRegistro == null) {
            listaLineasRegistro = new HashSet<>();
            listaLineasRegistro.add(error.getLineNumber());

            listadoRegistrosFaltantes.put(tipoRegistroError, listaLineasRegistro);
        }
    }

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileCommon.log.DetailedErrorLog#getFileCommonLog()
     */
    @Override
    public List<? extends FileCommonLog> getFileCommonLog() {
        // implementación vacía de método abstracto
        return null;
    }
}
