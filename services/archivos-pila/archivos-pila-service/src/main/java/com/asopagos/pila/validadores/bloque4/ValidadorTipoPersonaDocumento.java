package com.asopagos.pila.validadores.bloque4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del tipo persona documento <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class ValidadorTipoPersonaDocumento extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoPersonaDocumento.class);

    /** Constantes mensajes */
    private static final String TIPO_DOCUMENTO = "tipo de documento de identidad";
    private static final String TIPO_PERSONA = "tipo de persona";

    /**
     * Metodo se encarga de validar tipo persona documento
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String tipoDocumento = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_ID));
        TipoIdentificacionEnum tipoIdentificacionEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoDocumento);
 logger.info("**__** TipoPersonaEnum.tipoIdentificacionEnum "+tipoIdentificacionEnum);
        String tipoPersona = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_PERSONA));
        TipoPersonaEnum tipoPersonaEnum = TipoPersonaEnum.obtenerTipoPersona(tipoPersona);
         logger.info("**__** TipoPersonaEnum.obtenerTipoPersona "+tipoPersonaEnum);

        if (tipoIdentificacionEnum == null) {
            mensaje = " - " + MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_DOCUMENTO);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (tipoPersonaEnum == null) {
            mensaje = " - " + MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_PERSONA);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
            throw new FileProcessingException(mensaje);
        }

        List<TipoIdentificacionEnum> tiposIdentificacionValidos = definirTiposIdentificacionValidos(tipoPersonaEnum);

        if (TipoPersonaEnum.NATURAL.equals(tipoPersonaEnum) && !tiposIdentificacionValidos.contains(tipoIdentificacionEnum)) {
            mensaje = " - " + MensajesValidacionEnum.ERROR_CAMPO_TIPO_DOCUMENTO_NO_VALIDO_PARA_TIPO_PERSONA.getReadableMessage(idCampo,
                    tipoDocumento, tipoError, nombreCampo, tipoIdentificacionEnum.getDescripcion(), tipoPersonaEnum.getDescripcion());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para establecer los tipos de identificación a los que puede acceder un tipo de persona
     * 
     * @param tipoPersonaEnum
     *        Tipo de persona para la consulta
     * @return <b>List<TipoIdentificacionEnum></b>
     *         Listado de los tipos de documento que pueden ser usados por un tipo de persona
     */
    private List<TipoIdentificacionEnum> definirTiposIdentificacionValidos(TipoPersonaEnum tipoPersonaEnum) {
        String firmaMetodo = "definirTiposIdentificacionValidos(TipoPersonaEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TipoIdentificacionEnum> result = null;

        if (tipoPersonaEnum != null) {
            result = new ArrayList<>();

            if (TipoPersonaEnum.NATURAL.equals(tipoPersonaEnum)) {
                result.add(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
                result.add(TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
                result.add(TipoIdentificacionEnum.TARJETA_IDENTIDAD);
                result.add(TipoIdentificacionEnum.PASAPORTE);
                result.add(TipoIdentificacionEnum.CARNE_DIPLOMATICO);
                result.add(TipoIdentificacionEnum.SALVOCONDUCTO);
                result.add(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
                result.add(TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
}
