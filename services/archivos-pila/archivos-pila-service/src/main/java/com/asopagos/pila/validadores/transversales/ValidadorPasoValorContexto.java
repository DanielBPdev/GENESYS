package com.asopagos.pila.validadores.transversales;

import java.util.Map;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase almacena un valor en el contexto<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorPasoValorContexto extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorPasoValorContexto.class);

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
        
        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            Map<String, Object> contexto = arg0.getContext(); 
            String llaveVariable = getParams().get(ConstantesParametroValidador.LLAVE_VARIABLE);
            
            if(contexto != null){
                // sí la variable ya se encuentra en el contexto, su valor se reemplaza
                if(contexto.get(llaveVariable) != null){
                    contexto.replace(llaveVariable, arg0.getFieldValue());
                }else{
                    // sino, se agrega
                    contexto.put(llaveVariable, arg0.getFieldValue());
                }
            }
        }
        
        logger.debug("Finaliza validate(FieldArgumentDTO )");
    }

}
