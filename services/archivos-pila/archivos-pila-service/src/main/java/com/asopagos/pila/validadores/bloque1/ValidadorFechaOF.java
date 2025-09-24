package com.asopagos.pila.validadores.bloque1;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
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
public class ValidadorFechaOF extends FieldValidator {
    /** Constante para mensaje de error por falta de parámetros */
    private static final String EXPRESION_REGULAR = "expresión regular de referencia";
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(ValidadorFechaOF.class);

    /**
     * Este metodo se encarga de realizar la validadcion
     * @param FieldArgumentDTO
     *        objeto con la informacion
     * @param FileProcessingException
     *        lanzada al procesar el archivo
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");
        String mensaje = null;

        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        Object valorCampo = arg0.getFieldValue();

        if (valorCampo != null && !valorCampo.toString().isEmpty()) {
            String expReg = getParams().get(ConstantesParametroValidador.EXPRESION_REGULAR);

            if (expReg != null) {
                String fecha = (String) valorCampo;

                if (!Pattern.matches(expReg, fecha)) {
                    mensaje = MensajesValidacionEnum.ERROR_FORMATO_INCORRECTO.getReadableMessage(idCampo, valorCampo.toString(), tipoError,
                            nombreCampo, fecha);

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }

                // coherencia de la fecha
                int anio = Integer.parseInt(fecha.substring(0, 4));
                int mes = Integer.parseInt(fecha.substring(4, 6));
                int dia = Integer.parseInt(fecha.substring(6, 8));

                try {
                    LocalDate.of(anio, mes, dia);
                } catch (DateTimeException e) {
                    mensaje = MensajesValidacionEnum.ERROR_FORMATO_INCORRECTO.getReadableMessage(idCampo, valorCampo.toString(), tipoError,
                            nombreCampo, fecha);

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);

                }
            }
            else {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, EXPRESION_REGULAR);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
