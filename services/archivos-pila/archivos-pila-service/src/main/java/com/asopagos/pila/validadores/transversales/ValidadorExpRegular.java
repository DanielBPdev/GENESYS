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
 * <b>Descripción:</b> Validador del campo expresion regular<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorExpRegular extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorExpRegular.class);

    /**
     * Metodo que se encargará de realizar la validacion
     * @param FieldArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lazado cuando hay error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String cadenaRevision = (String) arg0.getFieldValue();
        
        if (cadenaRevision != null && !cadenaRevision .isEmpty()) {
            // la cadena de texto se pasa a minúsculas para ser evaluada
            cadenaRevision = cadenaRevision.toLowerCase();
            
            boolean error = false;
            String regx = getParams().get(ConstantesParametroValidador.EXPRESION_REGULAR);
            error = Pattern.matches(regx, cadenaRevision);
            if (!error) {
                String mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ES_VALIDO.getReadableMessage(
                        getParams().get(ConstantesParametroValidador.ID_CAMPO),
                        cadenaRevision, 
                        getParams().get(ConstantesParametroValidador.TIPO_ERROR),
                        getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO), cadenaRevision);
                
                logger.debug("Finaliza validate(FieldArgumentDTO)" + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }
}
