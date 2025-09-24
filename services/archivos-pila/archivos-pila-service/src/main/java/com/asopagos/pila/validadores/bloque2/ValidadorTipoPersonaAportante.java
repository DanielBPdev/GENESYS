package com.asopagos.pila.validadores.bloque2;

import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.enumeraciones.aportes.TipoAportanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo tipo persona aportante<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorTipoPersonaAportante extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoPersonaAportante.class);

    /** Constantes para mensajes */
    private static final String TIPO_APORTANTE = "tipo de aportante válido";
    private static final String TIPO_PERSONA = "tipo de persona válida";

    /**
     * Metodo se encarga de validar del campo tipo persona aportante
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorTipoPersonaAportante.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        // Se obtienen los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        Object valorCampoTipoAportante = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_APORTANTE));

        if (valorCampoTipoAportante != null) {
            Integer tipoAportante = null;
            if (valorCampoTipoAportante instanceof String && NumberUtils.isParsable((String) valorCampoTipoAportante)) {
                tipoAportante = Integer.parseInt((String) valorCampoTipoAportante);
            }
            else if (valorCampoTipoAportante instanceof Integer) {
                tipoAportante = (Integer) valorCampoTipoAportante;
            }

            if (tipoAportante == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, TIPO_APORTANTE);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            TipoAportanteEnum tipoAportanteEnum = TipoAportanteEnum.obtenerTipoAportante(tipoAportante);

            String tipoPersona = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_PERSONA));
            TipoPersonaEnum tipoPersonaEnum = TipoPersonaEnum.obtenerTipoPersona(tipoPersona);

            if (tipoAportanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, TIPO_APORTANTE);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (tipoPersonaEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, TIPO_PERSONA);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // los aportantes tipo 02, sólo pueden ser de personas de tipo N
            if ((TipoAportanteEnum.INDEPENDIENTE.equals(tipoAportanteEnum)||(tipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE.equals(tipoAportanteEnum))) && !TipoPersonaEnum.NATURAL.equals(tipoPersonaEnum)) {

                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_TIPO_APORTANTE.getReadableMessage(idCampo, tipoPersona,
                        tipoError, nombreCampo, tipoPersonaEnum.getDescripcion(), tipoAportanteEnum.getDescripcion());

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // los aportantes diferentes a tipo 01 y 02, sólo pueden presentar tipo de persona J
            if (!TipoAportanteEnum.EMPLEADOR.equals(tipoAportanteEnum) && !TipoAportanteEnum.INDEPENDIENTE.equals(tipoAportanteEnum)
                    && !TipoPersonaEnum.JURIDICA.equals(tipoPersonaEnum)) {

                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_TIPO_APORTANTE.getReadableMessage(idCampo, tipoPersona,
                        tipoError, nombreCampo, tipoPersonaEnum.getDescripcion(), tipoAportanteEnum.getDescripcion());

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER+ firmaMetodo);
    }

}
