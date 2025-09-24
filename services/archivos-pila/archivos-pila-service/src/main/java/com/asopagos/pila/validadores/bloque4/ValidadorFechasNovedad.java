package com.asopagos.pila.validadores.bloque4;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de fecha de pago maxima minima nombre <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @version 1.0.0
 */
public class ValidadorFechasNovedad extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFechasNovedad.class);

    /**
     * Metodo se encarga de validar fecha novedad
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorFechasNovedad.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String campoNovedad = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NOVEDAD));
        Date campoFechaInicio = null;
        Date campoFechaFin = null;

        String tieneFechaFin = (String) getParams().get(ConstantesParametroValidador.TIENE_FECHA_FIN);
        
        Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_FECHA_INICIO));
        if(valor != null){
            campoFechaInicio = (Date) valor;
        }

        valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_FECHA_FIN));
        if(valor != null){
            campoFechaFin = (Date) valor;
        }

        // se presenta una fecha de inicio o fin de novedad sin haber marcado el campo correspondiente
        if ((campoFechaInicio != null || campoFechaFin != null)
                && (campoNovedad == null || (!campoNovedad.equals("X") && !campoNovedad.equals("S")))) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_FECHA_NOVEDAD_SIN_HABERSE_MARCADO.getReadableMessage(idCampo,
                    CalendarUtil.retornarFechaSinHorasMinutosSegundos(campoFechaInicio), tipoError, nombreCampo);

            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // no se presenta una fecha de inicio o fin de novedad habiendo marcado el campo correspondiente
        if ((campoFechaInicio == null || (campoFechaFin == null && tieneFechaFin == null))
                && (campoNovedad != null && (campoNovedad.equals("X") || campoNovedad.equals("S")))) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_PRESENTA_FECHA_NOVEDAD_HABIENDOSE_MARCADO.getReadableMessage(idCampo,
                    CalendarUtil.retornarFechaSinHorasMinutosSegundos(campoFechaInicio), tipoError, nombreCampo);

            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // cuando se presenta una fecha de finalización de novedad sin una fecha de inicio
        if (campoFechaFin != null && campoFechaInicio == null) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_PRESENTA_FECHA_FIN_NOVEDAD_SIN_FECHA_INICIO.getReadableMessage(idCampo,
                    CalendarUtil.retornarFechaSinHorasMinutosSegundos(campoFechaInicio), tipoError, nombreCampo);

            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // cuando se tienen ambas fechas, pero la fecha de inicio es posterior a la de fin
        if (campoFechaInicio != null && campoFechaFin != null) {
            Calendar fechaInicio = null;
            Calendar fechaFin = null;

            try {
                fechaInicio = CalendarUtil.toCalendar(campoFechaInicio);
                fechaFin = CalendarUtil.toCalendar(campoFechaFin);
            } catch (Exception e) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_SIN_VERIFICAR_DEBIDO_A_CONVERSION_FECHAS.getReadableMessage(idCampo,
                        CalendarUtil.retornarFechaSinHorasMinutosSegundos(campoFechaInicio), tipoError, nombreCampo, e.getMessage());

                logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (fechaInicio.compareTo(fechaFin) > 0) {
                mensaje = MensajesValidacionEnum.ERROR_FECHA_INICIO_NOVEDAD_POSTERIOR_A_FECHA_FINALIZACION_NOVEDAD.getReadableMessage(
                        idCampo, CalendarUtil.retornarFechaSinHorasMinutosSegundos(campoFechaInicio), tipoError, nombreCampo,
                        CalendarUtil.retornarFechaSinHorasMinutosSegundos(campoFechaInicio),
                        CalendarUtil.retornarFechaSinHorasMinutosSegundos(campoFechaFin));

                logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
