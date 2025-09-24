package com.asopagos.pila.validadores.bloque1;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase se encarga de validar el campo hora en el archivo<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 407 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorHora extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(ValidadorHora.class);

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
            boolean error = false;
            try {
                Integer hora = Integer.parseInt(((String) valorCampo).substring(0, 2));
                Integer minuto = Integer.parseInt(((String) valorCampo).substring(2, 4));

                if (hora.compareTo(23) > 0)
                    error = true;

                if (minuto.compareTo(59) > 0)
                    error = true;
            } catch (Exception e) {
                error = true;
            }

            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_FORMATO_INCORRECTO.getReadableMessage(
                        idCampo, valorCampo.toString(), tipoError, nombreCampo, valorCampo.toString());
                
                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);

            }
        }
        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
