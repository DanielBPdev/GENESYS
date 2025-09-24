package com.asopagos.pila.validadores.bloque4;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de fecha maxima minima de pago<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class ValidadorFechaMaxMinPago extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFechaMaxMinPago.class);

    /**
     * Metodo se encarga de validar fecha maxima minima de pago
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // leo los campos indicados en los parámetros
        Object campoEvaluado = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_EVALUADO));

        // sí el campo evaluado no es nulo, se lleva a cabo la validación
        if (campoEvaluado != null) {
            Object campoReferencia = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_FECHA_REFERENCIA));

            String fechaPagoString = null;
            Object fechaPago = args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_FECHA_PAGO));
            
            if(fechaPago != null && fechaPago instanceof String){
                fechaPagoString = (String) fechaPago;
            }else if(fechaPago != null && fechaPago instanceof Calendar){
                fechaPagoString = FuncionesValidador.formatoFecha(fechaPago);
            }

            // fecha mínima por defecto
            Calendar fechaMinima = new GregorianCalendar(1900, 1, 1);

            // si se cuenta con una fecha de referencia, se emplea como fecha mínima
            if (campoReferencia != null) {
                if (campoReferencia instanceof Date) {
                    fechaMinima = CalendarUtil.toCalendar((Date) campoReferencia);
                }
                else if (campoReferencia instanceof String) {
                    fechaMinima = CalendarUtil.convertirCadenaAFecha((String) campoReferencia);
                }
                else {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_EVALUADO_NO_ES_TIPO_FECHA.getReadableMessage(idCampo,
                            campoReferencia.toString(), tipoError, nombreCampo);

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }

            if (campoEvaluado instanceof Date) {
                // se compara la fecha en el campo validado respecto a la fecha mínima y la fecha de pago

                if (CalendarUtil.esFechaMayor(CalendarUtil.toCalendar((Date) campoEvaluado),
                        CalendarUtil.convertirCadenaAFecha(fechaPagoString))) {

                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_POSTERIOR_A_FECHA_PAGO_PLANILLA.getReadableMessage(idCampo,
                            campoEvaluado.toString(), tipoError, nombreCampo, ((Date) campoEvaluado).toString(), fechaPagoString);

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);

                }
                else if (CalendarUtil.esFechaMenor(CalendarUtil.toCalendar((Date) campoEvaluado), fechaMinima)) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_ES_ANTERIOR_A_FECHA_MINIMA_DEFINIDA.getReadableMessage(idCampo,
                            campoEvaluado.toString(), tipoError, nombreCampo, ((Date) campoEvaluado).toString(), fechaMinima.toString());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
            else {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ES_TIPO_FECHA.getReadableMessage(idCampo, campoEvaluado.toString(),
                        tipoError, nombreCampo);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }
}
