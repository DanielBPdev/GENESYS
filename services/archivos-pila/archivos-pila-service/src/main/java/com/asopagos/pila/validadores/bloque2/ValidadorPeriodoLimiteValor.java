package com.asopagos.pila.validadores.bloque2;

import java.util.Date;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del período límite que es válido para que un campo
 * tengo un valor determinado<br>
 * <b>Módulo:</b> ArchivosPILAService - HU-211-391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class ValidadorPeriodoLimiteValor extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorPeriodoLimiteValor.class);

    /**
     * Metodo se encarga de validar de la validación del período límite que es válido para que un campo
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorPeriodoLimiteValor.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // se leen desde los parámetros, los nombres de los campos a validar y el período límite a comparar
        String periodoLimite = getParams().get(ConstantesParametroValidador.PERIODO_LIMITE);
        Date periodoLimiteDate = FuncionesValidador.convertirDate(periodoLimite + "-01");

        String campoValorValidado = getParams().get(ConstantesParametroValidador.CAMPO_VALIDADO);
        String valorReferencia = getParams().get(ConstantesParametroValidador.VALOR_REFERENCIA);

        // se lee el período de la planilla desde el contexto
        String periodoAporte = (String) args.getContext().get(ConstantesContexto.NOMBRE_PERIODO_PAGO);
        Date periodoAporteDate = FuncionesValidador.convertirDate(periodoAporte + "-01");

        if (periodoAporte == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.PERIODO_APORTE);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        Object valorValidado = valoresDeLinea.get(campoValorValidado);
        String valorValidadoString = null;

        // se hace la conversión a String del valor del campo
        if (valorValidado != null && valorValidado instanceof Number) {
            valorValidadoString = ((Long) valorValidado).toString();
        }
        else if (valorValidado != null && valorValidado instanceof Date) {
            valorValidadoString = FuncionesValidador.formatoFecha(valorValidado);
        }
        else if (valorValidado != null && valorValidado instanceof String) {
            valorValidadoString = (String) valorValidado;
        }

        // se compara el valor de referencia respecto al valor validado y el período límite
        if (valorReferencia.equalsIgnoreCase(valorValidadoString) && periodoAporteDate.getTime() > periodoLimiteDate.getTime()) {
            mensaje = MensajesValidacionEnum.ERROR_PERIODO_LIMITE.getReadableMessage(idCampo, valorValidadoString, tipoError, nombreCampo,
                    periodoLimite, periodoAporte);
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
