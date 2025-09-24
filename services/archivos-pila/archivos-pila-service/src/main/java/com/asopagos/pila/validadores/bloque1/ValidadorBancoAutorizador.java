package com.asopagos.pila.validadores.bloque1;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que maneja la validacion del banco autorizador<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 407 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorBancoAutorizador extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(ValidadorBancoAutorizador.class);

    /** Constantes para el validador */
    private static final Object CODIGO_BANCO_PARTE_1 = "00001";
    private static final Integer CODIGO_BANCO_PARTE_2_MAX = 999;

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator#validate(co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO)
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");
        String mensaje = null;

        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            String campoBanco = (String) arg0.getFieldValue();

            String parte1 = campoBanco.substring(0, 5);
            String parte2 = campoBanco.substring(5, campoBanco.length());

            // la primera parte debe ser igual a 00001
            if (!parte1.equals(CODIGO_BANCO_PARTE_1)) {
                mensaje = MensajesValidacionEnum.ERROR_BANCO_AUTORIZADOR.getReadableMessage(idCampo, campoBanco, tipoError, nombreCampo,
                        campoBanco, "primeros 5");

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // la segunda parte, debe ser numérica y debe estar comprendida entre 001 y 999
            try {
                Integer p2 = Integer.parseInt(parte2);
                if (p2.compareTo(0) <= 0 || p2.compareTo(CODIGO_BANCO_PARTE_2_MAX) > 0) {
                    mensaje = MensajesValidacionEnum.ERROR_BANCO_AUTORIZADOR.getReadableMessage(idCampo, campoBanco, tipoError, nombreCampo,
                            campoBanco, "últimos 3");

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            } catch (Exception e) {
                mensaje = MensajesValidacionEnum.ERROR_BANCO_AUTORIZADOR.getReadableMessage(idCampo, campoBanco, tipoError, nombreCampo,
                        campoBanco, "últimos 3");

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
