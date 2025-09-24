package com.asopagos.pila.validadores.transversales;

import java.math.BigDecimal;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase valida el valor del rango<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorRangoValor extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorRangoValor.class);

    /** Constante mensaje */
    private static final String RANGO_VALORES = "rango de valores";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param LineArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lazado cuando hay error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicio validate(FieldArgumentDTO)");

        String mensaje = null;

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            Object valorCampo = arg0.getFieldValue().toString();
            String rangoValor = getParams().get(ConstantesParametroValidador.RANGO_VALOR);

            try {
                String[] valores = rangoValor.split(",");
                boolean error = true;

                EXTERNO: for (String valor : valores) {

                    // se evalua el campo dependiendo de su tipo
                    if (valorCampo instanceof String) {
                        try {
                            // se convierten en número para evitar discrepancias del tipo 1 != 01
                            if (Integer.parseInt(valor) == Integer.parseInt((String) valorCampo)) {
                                error = false;
                                break EXTERNO;
                            }
                        } catch (NumberFormatException e) {
                            // para los campos de texto
                            if (valor.equals((String) valorCampo)) {
                                error = false;
                                break EXTERNO;
                            }
                        }
                    }
                    else if (valorCampo instanceof BigDecimal) {
                        try {
                            if (((BigDecimal) valorCampo).compareTo(new BigDecimal(valor)) == 0) {
                                error = false;
                                break EXTERNO;
                            }
                        } catch (Exception e) {
                        }
                    }
                    else if (valorCampo instanceof Integer) {
                        try {
                            if (Integer.parseInt(valor) == (Integer) valorCampo) {
                                error = false;
                                break EXTERNO;
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                }

                if (error) {
                    mensaje = MensajesValidacionEnum.ERROR_VALOR_NO_ESTA_EN_RANGO_VALIDO.getReadableMessage(idCampo, valorCampo.toString(),
                            tipoError, nombreCampo, valorCampo.toString(), rangoValor);

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            } catch (NullPointerException e) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, RANGO_VALORES);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            logger.debug("Finaliza validate(FieldArgumentDTO)");

        }
    }

}
