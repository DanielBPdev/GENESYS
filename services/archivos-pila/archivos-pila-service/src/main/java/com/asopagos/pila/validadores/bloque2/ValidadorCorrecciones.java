package com.asopagos.pila.validadores.bloque2;

import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase realiza la validacion de correcciones<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorCorrecciones extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorCorrecciones.class);

    /** Constantes de mensajes */
    private static final String TIPO_PLANILLA = "tipo de planilla";

    /**
     * Metodo se encarga de validar la cla tipo aportante
     * @param FieldArgumentDTO
     *        objeto con la informacion a valida
     * @exception FileProcessingException
     *            lanzada cuando hay una inconsistencia
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        String tipoPlanilla = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_PLANILLA));
        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

        if (tipoPlanillaEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_PLANILLA);

            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // cuando el campo no está vacío, el tipo de planilla debe ser N
        if (TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)
                && (arg0.getFieldValue() == null || arg0.getFieldValue().toString().isEmpty())) {
            mensaje = MensajesValidacionEnum.ERROR_VALOR_CORRECCION.getReadableMessage(idCampo, "", tipoError, nombreCampo);

            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");

    }

}
