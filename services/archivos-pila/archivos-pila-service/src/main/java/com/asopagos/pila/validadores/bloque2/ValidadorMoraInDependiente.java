package com.asopagos.pila.validadores.bloque2;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que se encarga de ejecutar el conjunto de validadores que dependen
 * del cálculo previo de la fecha de vencimiento del aporte en planillas de dependientes e
 * independientes <br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ValidadorMoraInDependiente extends LineValidator {
    private static final ILogger logger = LogManager.getLogger(ValidadorMoraInDependiente.class);

    /**
     * Método encargado de ejecutar el conjunto de validadores que dependen
     * del cálculo previo de la fecha de vencimiento del aporte en planillas de dependientes e
     * independientes
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @Override
    public void validate(LineArgumentDTO arg0) throws FileProcessingException {
        String firmaMetodo = "validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String etiquetaCampoDiasMora = getParams().get(ConstantesParametroValidador.CAMPO_DIAS_MORA);

        FieldArgumentDTO fieldArgumentDTO = new FieldArgumentDTO();
        Object valor = arg0.getLineValues().get(etiquetaCampoDiasMora);
        Integer valorDiasMora = null;
        if(valor != null && !valor.toString().isEmpty()){
            valorDiasMora = (Integer) valor;
        }
        fieldArgumentDTO.setContext(arg0.getContext());
        fieldArgumentDTO.setFieldValue(valorDiasMora);

        ValidadorDiasMora validadorDiasMora = new ValidadorDiasMora();
        ValidadorCorreccionNovedadComparacion validadorCorreccionNovedadComparacion = new ValidadorCorreccionNovedadComparacion();
        ValidadorNovedadesMultiplesComparacion validadorNovedadesMultiplesComparacion = new ValidadorNovedadesMultiplesComparacion();

        try {
            validadorDiasMora.setParams(getParams());
            validadorDiasMora.validate(fieldArgumentDTO);

            validadorCorreccionNovedadComparacion.setParams(getParams());
            validadorCorreccionNovedadComparacion.validate(fieldArgumentDTO);

            validadorNovedadesMultiplesComparacion.setParams(getParams());
            validadorNovedadesMultiplesComparacion.validate(fieldArgumentDTO);
        } catch (FileProcessingException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
