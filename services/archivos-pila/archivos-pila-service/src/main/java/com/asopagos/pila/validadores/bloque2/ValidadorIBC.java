package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
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
 * <b>Descripción:</b> Clase que contiene la validación de Ingreso Base de Cotizacion<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorIBC extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorIBC.class);

    /** Constantes para mensajes */
    private static final String VALOR_BASE_REDONDEO = "valor base de redondeo";
    private static final String TIPO_COTIZANTE = "(Tipo de Cotizante)";

    /**
     * Metodo se encarga de validar Ingreso Base de Cotizacion
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_IBC));

        BigDecimal ibc = null;
        if (valor != null && !valor.toString().isEmpty()) {
            ibc = (BigDecimal) valor;
        }

        if (ibc != null) {
            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            // en primer lugar, se verifica que el valor del IBC no sea cero
            if (!ibc.equals(0)) {
                // se aplica el redondeo al valor de IBC registrado
                BigDecimal redondeo = null;

                try {
                    redondeo = new BigDecimal(getParams().get(ConstantesParametroValidador.REDONDEO));
                } catch (Exception e) {
                    mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                            ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, VALOR_BASE_REDONDEO);

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }

                BigDecimal ibcRedondeado = FuncionesValidador.redondearValor(ibc, redondeo);

                if (ibc.compareTo(ibcRedondeado) != 0) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_CUMPLE_REGLA_REDONDEO.getReadableMessage(idCampo, ibc.toString(),
                            tipoError, nombreCampo, ibc.toString(), "" + redondeo);

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
            else {
                // en el caso de ser cero, se valida el tipo de cotizante
                valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE));

                Integer tipoCotizante = null;
                if (valor != null && !valor.toString().isEmpty()) {
                    tipoCotizante = (Integer) valor;
                }
                TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);
                List<TipoCotizanteEnum> tiposCotizanteIBCNoCero = (List<TipoCotizanteEnum>) args.getContext()
                        .get(ConstantesContexto.COTIZANTES_IBC_NO_CERO);

                if (tipoCotizanteEnum != null) {
                    // se notifica el error sí da a lugar
                    if (tiposCotizanteIBCNoCero.contains(tipoCotizanteEnum)) {
                        mensaje = MensajesValidacionEnum.ERROR_CAMPO_TIPO_COTIZANTE_NO_ADMITE_VALOR_CERO.getReadableMessage(idCampo,
                                ibc.toString(), tipoError, nombreCampo, ibc.toString(), tipoCotizanteEnum.getDescripcion());

                        logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                }
                else {
                    // si no se cuenta con el tipo de cotizante
                    mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                            ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_COTIZANTE);

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }

            logger.debug("Finaliza validate(LineArgumentDTO)");
        }
    }

}
