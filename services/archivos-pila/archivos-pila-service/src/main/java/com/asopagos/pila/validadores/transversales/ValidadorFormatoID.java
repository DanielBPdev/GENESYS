package com.asopagos.pila.validadores.transversales;

import java.util.Map;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Esta clase maneja la validacion del formato<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorFormatoID extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFormatoID.class);

    /** Constantes mensajes */
    private static final String NUMERO_IDENTIFICACION = "número de identificación";
    private static final String TIPO_IDENTIFICACION = "tipo de documento de identidad";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param FieldArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lazado cuando hay error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String id = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NUMERO_ID));
        String tipoId = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_DOCUMENTO));

        if (id == null || tipoId == null) {
            // se buscan los valores en contexto
            id = (String) args.getContext().get(ConstantesContexto.NOMBRE_ID_APORTANTE);
            tipoId = (String) args.getContext().get(ConstantesContexto.NOMBRE_TIPO_DOCUMENTO);
        }

        TipoIdentificacionEnum tipoIdentificacionEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoId);

        if (id == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, NUMERO_IDENTIFICACION);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (tipoIdentificacionEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, TIPO_IDENTIFICACION);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (tipoId != null) {
            String mensajeFormato = FuncionesValidador.validarNumeroId(id,
                    TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoId));

            if (mensajeFormato != null) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_PRESENTA_INCONSISTENCIAS.getReadableMessage(idCampo, id, tipoError,
                        nombreCampo, id, mensajeFormato);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
