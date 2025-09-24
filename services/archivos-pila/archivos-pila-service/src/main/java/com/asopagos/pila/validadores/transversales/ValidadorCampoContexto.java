package com.asopagos.pila.validadores.transversales;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;

import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase en la que se implementa la validación de un campo contra una variable de contexto<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorCampoContexto extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(ValidadorCampoContexto.class);

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator#validate(co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO)
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        String comparacion = getParams().get(ConstantesParametroValidador.COMPARACION);

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {

            Object valorVariableBase = arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_VARIABLE));
            String valorVariable = null;
            Object campo = arg0.getFieldValue();

            String valorMensaje = null;

            boolean error = false;

            try {
                // se convierte el valor leído en el contexto a String
                if (valorVariableBase != null) {
                    if (valorVariableBase instanceof String) {
                        valorVariable = (String) valorVariableBase;
                    }
                    else if (valorVariableBase instanceof Integer) {
                        valorVariable = ((Integer) valorVariableBase).toString();
                    }
                    else if (valorVariableBase instanceof BigDecimal) {
                        valorVariable = ((BigDecimal) valorVariableBase).toPlainString();
                    }
                    else if (valorVariableBase instanceof Date) {
                        valorVariable = FuncionesValidador.formatoFechaMilis(((Date) valorVariableBase).getTime());
                    }
                }

                if (campo instanceof String) {
                    valorMensaje = (String) campo;
                    if (valorVariable == null || !((String) campo).equals(valorVariable)) {
                        error = true;
                    }
                }
                else if (campo instanceof Date) {
                    valorMensaje = FuncionesValidador.formatoFechaMilis(((Date) campo).getTime());

                    if (valorVariable != null) {
                        Integer comparacionFechas = FuncionesValidador.compararFechasString(valorVariable, valorMensaje);

                        if (comparacionFechas == null || comparacionFechas != 0) {
                            error = true;
                        }
                    }
                    else {
                        error = true;
                    }
                }
                else if (campo instanceof Integer) {
                    valorMensaje = Integer.toString((Integer) campo);

                    if (valorVariable == null || !((Integer) campo).equals(Integer.parseInt(valorVariable))) {
                        error = true;
                    }
                }
                else if (campo instanceof BigDecimal) {
                    valorMensaje = ((BigDecimal) campo).toPlainString();

                    if (valorVariable == null || !((BigDecimal) campo).equals(new BigDecimal(Integer.parseInt(valorVariable)))) {
                        error = true;
                    }
                }

                if (error) {
                    mensaje = MensajesValidacionEnum.ERROR_COMPARACION_CONTEXTO.getReadableMessage(idCampo, valorMensaje, tipoError,
                            nombreCampo, valorMensaje, comparacion, valorVariable);

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            } catch (NullPointerException e) {
                mensaje = MensajesValidacionEnum.ERROR_COMPARACION_CONTEXTO_REFERENCIA.getReadableMessage(idCampo, valorMensaje,
                        tipoError, nombreCampo);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
