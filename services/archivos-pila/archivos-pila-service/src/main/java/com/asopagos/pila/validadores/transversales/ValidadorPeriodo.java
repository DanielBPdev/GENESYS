package com.asopagos.pila.validadores.transversales;

import java.util.regex.Pattern;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
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
public class ValidadorPeriodo extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorPeriodo.class);

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
            
            String periodo = (String) arg0.getFieldValue();

            boolean error = true;
            String regx = getParams().get(ConstantesParametroValidador.EXPRESION_REGULAR);
            if (regx != null && Pattern.matches(regx, periodo)) {
                try {
                    Integer mes = null;

                    // cuando el período contiene guión
                    if (periodo.contains("-")) {
                        mes = Integer.parseInt(periodo.split("-")[1]);
                    }
                    else {
                        mes = Integer.parseInt(periodo.substring(4, periodo.length()));
                    }

                    if (mes <= 12)
                        error = false;
                } catch (NumberFormatException e) {
                }
            }

            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ES_VALIDO
                        .getReadableMessage(idCampo, periodo, tipoError, nombreCampo, periodo);
                
                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
