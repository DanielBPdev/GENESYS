package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase se encarga de validar la cantidad final<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorFinalCantidadTipo2 extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFinalCantidadTipo2.class);

    /**
     * Metodo se encarga de validar la cantidad final
     * @param arg0
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @Override
    public void validate(LineArgumentDTO arg0) throws FileProcessingException {
        String firmaMetodo = "validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        /*
         * en el caso de presentar inconsistencia, se debe modificar el número de linea del DTO del argumento para evitar que se reporte
         * de manera incorrecta. El error de cantidad de registros tipo 2 siempre se presenta en la primera línea
         */
        //arg0.setLineNumber(ConstantesComunesProcesamientoPILA.PRIMERA_LINEA);
        Map<String, Object> contexto = arg0.getContext();

        String mensaje = null;

        Integer cantidadRegistrosTipo2 = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        try {
            cantidadRegistrosTipo2 = ((BigDecimal) contexto
                    .get(getParams().get(ConstantesParametroValidador.LLAVE_CATIDAD_REGISTROS_TIPO_2))).intValue();
        } catch (NullPointerException | NumberFormatException e) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    MessagesConstants.CANTIDAD_REGISTROS_2);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        Integer contadorRegistrosTipo2 = (Integer) contexto.get(ConstantesContexto.CONTADOR_REGISTROS_2);
        Integer ultimaSecuenciaTipo2 = (Integer) contexto.get(ConstantesContexto.ULTIMA_SECUENCIA_REGISTRO_2);

        if (contadorRegistrosTipo2 == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    MessagesConstants.CONTADOR_REGISTROS_2);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (ultimaSecuenciaTipo2 == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.ULTIMA_SECUENCIA_2);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (!cantidadRegistrosTipo2.equals(contadorRegistrosTipo2)) {
            mensaje = MensajesValidacionEnum.ERROR_CUENTA_REGISTROS_TIPO_2.getReadableMessage(idCampo, cantidadRegistrosTipo2.toString(),
                    tipoError, "1", nombreCampo, contadorRegistrosTipo2.toString());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (!cantidadRegistrosTipo2.equals(ultimaSecuenciaTipo2)) {
            mensaje = MensajesValidacionEnum.ERROR_ULTIMA_SECUENCIA_2.getReadableMessage(idCampo, contadorRegistrosTipo2.toString(),
                    tipoError, nombreCampo, ultimaSecuenciaTipo2.toString());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
