package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase se encarga de realizar las validaciones para el tipo de archivo<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorAORegistroTipo3 extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorAORegistroTipo3.class);

    /** Constantes para mensajes */
    private static final String TIPO_ARCHIVO = "tipo de archivo";
    private static final String TIPO_PLANILLA = "tipo de planilla";
    private static final String SUMATORIA_AO = "sumatoria AO";

    /**
     * Metodo encargado de realizar la respectiva validacion
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay error al procesar el archivo
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            String tipoArchivo = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));
            String tipoPlanilla = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_PLANILLA));

            TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);
            TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

            BigDecimal valorCampo = (BigDecimal) arg0.getFieldValue();

            if (tipoArchivoEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, TIPO_ARCHIVO);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            switch (tipoArchivoEnum) {
                case ARCHIVO_OI_I:
                case ARCHIVO_OI_IR:
                    if (tipoPlanillaEnum == null) {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_PLANILLA);

                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    // dependiendo del tipo de planilla 
                    BigDecimal sumatoriaAO = null;

                    // defino la sumatoria a comparar
                    if (!TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)) { // planillas diferente a N
                        sumatoriaAO = (BigDecimal) arg0.getContext().get(ConstantesContexto.SUMATORIA_AO_GENERAL);
                    }
                    else if (TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)) {
                        // Planillas tipo N: sumatoria de correcciones C menos sumatoria de correciones A
                        sumatoriaAO = ((BigDecimal) arg0.getContext().get(ConstantesContexto.SUMATORIA_AO_C))
                                .subtract((BigDecimal) arg0.getContext().get(ConstantesContexto.SUMATORIA_AO_A));
                    }

                    if (sumatoriaAO != null) {
                        // comparo los valores
                        if (valorCampo.compareTo(sumatoriaAO) != 0) {
                            mensaje = MensajesValidacionEnum.ERROR_SUMATORIA_APORTES.getReadableMessage(idCampo, valorCampo.toString(),
                                    tipoError, nombreCampo, valorCampo.toString(), sumatoriaAO.toString());

                            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                            throw new FileProcessingException(mensaje);
                        }
                    }
                    else {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, SUMATORIA_AO);

                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    // se agrega el valor del campo al contexto para su comparación final
                    arg0.getContext().put(ConstantesContexto.VALOR_APORTES, valorCampo);

                    break;
                case ARCHIVO_OI_IP:
                case ARCHIVO_OI_IPR:
                    sumatoriaAO = (BigDecimal) arg0.getContext().get(ConstantesContexto.SUMATORIA_AO_GENERAL);

                    if (sumatoriaAO != null) {
                        // comparo los valores
                        if (valorCampo.compareTo(sumatoriaAO) != 0) {
                            mensaje = MensajesValidacionEnum.ERROR_SUMATORIA_APORTES.getReadableMessage(idCampo, valorCampo.toString(),
                                    tipoError, nombreCampo, valorCampo.toString(), sumatoriaAO.toString());

                            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                            throw new FileProcessingException(mensaje);
                        }
                    }
                    else {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, SUMATORIA_AO);

                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    break;
                default:
                    break;
            }
            logger.debug("Finaliza validate(FieldArgumentDTO)");
        }
    }

}
