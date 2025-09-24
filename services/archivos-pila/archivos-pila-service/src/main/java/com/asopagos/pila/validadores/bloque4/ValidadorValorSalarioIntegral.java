package com.asopagos.pila.validadores.bloque4;

import java.math.BigDecimal;
import java.util.Map;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del valor del salario integral <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @version 1.0.0
 */
public class ValidadorValorSalarioIntegral extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorValorSalarioIntegral.class);

    /**
     * Metodo se encarga de validar el valor del salario integral
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");
        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        BigDecimal salario = (BigDecimal) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SALARIO));
        String esSalarioIntegral = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SALARIO_INTEGRAL));
        if (esSalarioIntegral == null)
            esSalarioIntegral = "";

        BigDecimal salarioMinimo = (BigDecimal) args.getContext().get(ConstantesContexto.SALARIO_MINIMO);
        BigDecimal modificadorSalarioIntegral = (BigDecimal) args.getContext().get(ConstantesContexto.MODIFICADOR_SALARIO_INTEGRAL);

        if (salarioMinimo != null && salario != null && esSalarioIntegral != null) {
            BigDecimal salarioIntegralMinimo = salarioMinimo.multiply(modificadorSalarioIntegral);

            // se comprueba la marca de salario integral y el valor mínimo del salario base correspondiente
            if (esSalarioIntegral.equals("X") && salario.compareTo(salarioIntegralMinimo) < 0) {
                String mensaje = MensajesValidacionEnum.ERROR_CAMPO_MARCA_SALARIO_INTEGRAL_NO_CUMPLE_VALOR_MIN_ESTABLECIDO
                        .getReadableMessage(idCampo, esSalarioIntegral, tipoError, nombreCampo, salario.toPlainString(),
                                salarioIntegralMinimo.toPlainString());

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
