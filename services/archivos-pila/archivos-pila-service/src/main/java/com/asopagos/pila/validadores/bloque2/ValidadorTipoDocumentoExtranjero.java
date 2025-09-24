package com.asopagos.pila.validadores.bloque2;

import java.util.Map;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo tipo documento extranjero<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorTipoDocumentoExtranjero extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoDocumentoExtranjero.class);

    /** Constantes para mensajes */
    private static final String TIPO_DOCUMENTO = "tipo documento del cotizante";

    /**
     * Metodo se encarga de validar el tipo documento extranjero
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtienen los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String tipoDocumento = null;
        String extranjero = null;
        String valoresValidos = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        try {
            extranjero = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_EXTRANJERO));

            // primero se valida que el la marca de extranjero esté o no diligenciada
            if (extranjero != null && !extranjero.equals("")) {
                // luego se valida que no sea diferente a "X"
                if (!extranjero.equals("X")) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_UNICO_VALOR_VALIDO_X_O_VACIO.getReadableMessage(idCampo, extranjero,
                            tipoError, nombreCampo, extranjero);

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }

                // en el caso de que cumpla el formato, se valida la información de los campos asociados al cruce
                valoresValidos = getParams().get(ConstantesParametroValidador.VALORES_VALIDOS).toString();
                tipoDocumento = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_DOCUMENTO)).toString();

                if (!valoresValidos.contains(tipoDocumento)) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_TIPO_DOCUMENTO_NO_COMPATIBLE_CON_MARCACION.getReadableMessage(idCampo,
                            extranjero, tipoError, nombreCampo, extranjero, tipoDocumento);

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
        } catch (NullPointerException e) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, TIPO_DOCUMENTO);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
