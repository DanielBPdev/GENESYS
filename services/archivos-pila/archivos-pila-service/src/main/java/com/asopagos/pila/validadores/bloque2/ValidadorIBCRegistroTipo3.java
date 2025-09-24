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
 * <b>Descripción:</b> Clase que contiene la validación de IBC<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorIBCRegistroTipo3 extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorIBCRegistroTipo3.class);

    /** Constantes para mensajes */
    private static final String SUMATORIA_IBC = "sumatoria de IBC de Registro Tipo 2";
    private static final String TIPO_ARCHIVO = "tipo archivo";

    /**
     * Metodo se encarga de validar IBC
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            String tipoArchivo = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            BigDecimal valorCampo = (BigDecimal) arg0.getFieldValue();

            TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

            if (tipoArchivoEnum != null) {

                switch (tipoArchivoEnum) {
                    case ARCHIVO_OI_A:
                    case ARCHIVO_OI_AR:
                        break;
                    case ARCHIVO_OI_I:
                    case ARCHIVO_OI_IR:
                        // dependiendo del tipo de planilla 
                        String tipoPlanilla = (String) arg0.getContext()
                                .get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_PLANILLA));

                        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

                        BigDecimal sumatoriaIBC = null;

                        // defino la sumatoria a comparar
                        if (tipoPlanillaEnum != null && !TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)) { // planillas diferente a N
                            sumatoriaIBC = (BigDecimal) arg0.getContext().get(ConstantesContexto.SUMATORIA_IBC_GENERAL);
                        }
                        else if (TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)) {
                            // Planillas tipo N: sumatoria de correcciones C menos sumatoria de correciones A
                            sumatoriaIBC = ((BigDecimal) arg0.getContext().get(ConstantesContexto.SUMATORIA_IBC_C))
                                    .subtract((BigDecimal) arg0.getContext().get(ConstantesContexto.SUMATORIA_IBC_A));
                        }

                        // comparo los valores
                        if (sumatoriaIBC != null) {
                            if (valorCampo.compareTo(sumatoriaIBC) != 0) {
                                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_COINCIDE_CON_SUMATORIA_IBC_REGISTROS_TIPO_2
                                        .getReadableMessage(idCampo, valorCampo.toPlainString(), tipoError, nombreCampo,
                                                valorCampo.toPlainString(), "" + sumatoriaIBC.intValue());

                                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                                throw new FileProcessingException(mensaje);
                            }
                        }
                        else {
                            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, SUMATORIA_IBC);

                            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                            throw new FileProcessingException(mensaje);
                        }

                        break;
                    default:
                        break;
                }
            }
            else {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, TIPO_ARCHIVO);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            logger.debug("Finaliza validate(FieldArgumentDTO)");
        }
    }

}
