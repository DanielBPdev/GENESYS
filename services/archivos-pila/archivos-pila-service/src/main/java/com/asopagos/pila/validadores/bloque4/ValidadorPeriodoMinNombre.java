package com.asopagos.pila.validadores.bloque4;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del período del aporte en el nombre del archivo <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @version 1.0.0
 */
public class ValidadorPeriodoMinNombre extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorPeriodoMinNombre.class);

    /** Constantes para mensajes */
    private static final String CAMPO_FECHA_NOMBRE = "Fecha en el nombre del archivo";
    private static final String PERIODO_MINIMO = "período de pago mínimo parametrizado";
    private static final String PERIODO_PAGO = "período de pago de la planilla";

    /**
     * Metodo se encarga de validar nombre del periodo
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_PERIODO_APORTE;

        String periodoMinimo = getParams().get(ConstantesParametroValidador.PERIODO_MINIMO);
        String periodoPago = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_PERIODO_PAGO));

        if (periodoMinimo == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, CAMPO_FECHA_NOMBRE, PERIODO_MINIMO);

            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (periodoPago == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, CAMPO_FECHA_NOMBRE, PERIODO_PAGO);

            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // año y mes del período
        Integer anioPeriodo = Integer.parseInt(periodoPago.split("-")[0]);
        Integer mesPeriodo = Integer.parseInt(periodoPago.split("-")[1]);

        // año y mes del periodo mínimo
        Integer anioPeriodoMinimo = Integer.parseInt(periodoMinimo.split("-")[0]);
        Integer mesPeriodoMinimo = Integer.parseInt(periodoMinimo.split("-")[1]);

        boolean error = false;

        if (anioPeriodo.compareTo(anioPeriodoMinimo) < 0) {
            error = true;
        }
        else if (anioPeriodo.compareTo(anioPeriodoMinimo) == 0 && mesPeriodo.compareTo(mesPeriodoMinimo) < 0) {
            error = true;
        }

        if (error) {
            mensaje = MensajesValidacionEnum.ERROR_PERIODO_PAGO_APORTE_ANTERIOR_A_PERIODO_MINIMO.getReadableMessage(idCampo, periodoPago,
                    tipoError, periodoPago, periodoMinimo);

            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
