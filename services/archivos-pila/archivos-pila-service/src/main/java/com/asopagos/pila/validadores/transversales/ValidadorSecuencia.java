package com.asopagos.pila.validadores.transversales;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase valida el valor de la secuencia<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorSecuencia extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorSecuencia.class);

    /** Constantes mensaje */
    private static final String SEGUIMIENTO_SECUENCIA = "variable de seguimiento de secuencia";
    private static final String VALOR_SECUENCIA = "valor de la secuencia para la línea";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param LineArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lanzado cuando hay error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicio validate(FieldArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            Integer valorActualSecuencia = (Integer) arg0.getContext()
                    .get(getParams().get(ConstantesParametroValidador.LLAVE_SECUENCIA_CONTEXTO));
            String valorCampo = arg0.getFieldValue().toString();

            if (valorActualSecuencia != null) {
                // se comprueba que el valor del campo sea igual al valor actual de la secuencia
                Integer campoSecuencia = (Integer) arg0.getFieldValue();

                if (campoSecuencia != null) {
                    if (campoSecuencia.equals(valorActualSecuencia)) {
                        // en el caso en el que concuerde, se debe incrementar la secuencia
                        arg0.getContext().replace(getParams().get(ConstantesParametroValidador.LLAVE_SECUENCIA_CONTEXTO),
                                valorActualSecuencia + 1);
                    }
                    else {
                        mensaje = MensajesValidacionEnum.ERROR_VALOR_NO_CONCUERDA_CON_ESPERADO_EN_SECUENCIA.getReadableMessage(idCampo,
                                valorCampo, tipoError, nombreCampo, valorCampo, valorActualSecuencia.toString());
                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                }
                else {
                    mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                            ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, VALOR_SECUENCIA);

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
            else {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, SEGUIMIENTO_SECUENCIA);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            logger.debug("Finaliza validate(FieldArgumentDTO)");
        }
    }

}
