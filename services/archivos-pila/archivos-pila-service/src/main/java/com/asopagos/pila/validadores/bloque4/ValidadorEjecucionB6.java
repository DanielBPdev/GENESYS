package com.asopagos.pila.validadores.bloque4;

import java.math.BigDecimal;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de ejecucion<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class ValidadorEjecucionB6 extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(ValidadorEjecucionB6.class);
    
	 /**
	  * Metodo se encarga de validar la ejecucion
	  * @param LineArgumentDTO objeto con la informacion a validar
	  * @exception FileProcessingException lanzada cuando hay un error en la validacion
	 * */
	@Override
	public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
	    logger.debug("Inicia validate(FieldArgumentDTO)");
		if(arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()){
			if(((BigDecimal) arg0.getFieldValue()).compareTo(new BigDecimal(0)) == 0)
				arg0.getContext().put(ConstantesContexto.EJECUTAR_BLOQUE_6, false);
			else
				arg0.getContext().put(ConstantesContexto.EJECUTAR_BLOQUE_6, true);
		}
		logger.debug("Finaliza validate(FieldArgumentDTO)");
	}

}
