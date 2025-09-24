package com.asopagos.pila.validadores.transversales;

import java.math.BigDecimal;
import org.apache.commons.lang3.math.NumberUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase maneja la validacion el valor del campo sea diferente de cero<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorNumeroMinMax extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorNumeroMinMax.class);

    /** Constantes para mensajes */
    private static final String VALOR_MINIMO = "valor mínimo de comparación";
    private static final String VALOR_MAXIMO = "valor máximo de comparación";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param LineArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lazado cuando hay error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            Object valorCampo = arg0.getFieldValue();
            BigDecimal campo = null;

            if (valorCampo instanceof Integer) {
                campo = BigDecimal.valueOf((Integer) valorCampo);
            }
            else if (valorCampo instanceof BigDecimal) {
                campo = (BigDecimal) valorCampo;
            }
            else if (valorCampo instanceof String && NumberUtils.isParsable((String) valorCampo)) {
                campo = BigDecimal.valueOf(Long.valueOf((String) valorCampo));
            }
            else {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_VALOR_DEBE_SER_NUMERICO.getReadableMessage(idCampo, valorCampo.toString(),
                        tipoError, nombreCampo, valorCampo.toString());

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            boolean error = false;

            String valorMinimo = getParams().get(ConstantesParametroValidador.VALOR_MINIMO);
            BigDecimal minimo = null;
            String valorMaximo = getParams().get(ConstantesParametroValidador.VALOR_MAXIMO);
            BigDecimal maximo = null;

            if (valorMinimo == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, VALOR_MINIMO);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (valorMaximo == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, VALOR_MAXIMO);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            try {
                minimo = BigDecimal.valueOf(Long.valueOf(valorMinimo));
            } catch (Exception e) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, VALOR_MINIMO);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            try {
                maximo = BigDecimal.valueOf(Long.valueOf(valorMaximo));
            } catch (Exception e) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, VALOR_MAXIMO);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (campo.compareTo(minimo) < 0 || campo.compareTo(maximo) > 0) {
                error = true;
            }

            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_VALOR_NO_ESTA_EN_RANGO_DE_VALORES_ADMITIDOS.getReadableMessage(idCampo,
                        valorCampo.toString(), tipoError, nombreCampo, valorCampo.toString(), valorMinimo.toString(),
                        valorMaximo.toString());

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            logger.debug("Finaliza validate(FieldArgumentDTO)");
        }
    }

}
