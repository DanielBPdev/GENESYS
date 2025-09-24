package com.asopagos.pila.validadores.bloque1;

import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripcion:</b> Clase que representa al validador de componente encargado de tomar la referencia 
 * a un registro tipo 5 de OF<br/>
 * <b>Módulo:</b> Asopagos - HU-211-407 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ValidadorAddLoteRegistro5OF extends FieldValidator {

    /**
     * @param arguments
     * @throws FileProcessingException
     */
//    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO args) throws FileProcessingException {
        
//        // se consulta el número de lote en el contexto
//        Integer numeroLote = (Integer) args.getContext().get(ConstantesContexto.NUMERO_LOTE_REGISTRO_5_OF);
//        
//        // se consulta el mapa de control de lotes del contexto
//        Map<Long, ControlLoteOFDTO> mapaControl = (Map<Long, ControlLoteOFDTO>) args.getContext().get(ConstantesContexto.MAPA_CONTROL_SUMATORIAS_LOTES_OF);
//        
//        if(numeroLote != null){
//            mapaControl.get(numeroLote).getLineasEnLote().add(args.getLineNumber());
//        }
    }

}
