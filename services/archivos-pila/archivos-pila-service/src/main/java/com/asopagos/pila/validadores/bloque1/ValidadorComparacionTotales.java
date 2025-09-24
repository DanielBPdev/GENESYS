package com.asopagos.pila.validadores.bloque1;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.dto.ControlLoteOFDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que maneja la validacion del banco autorizador<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 407 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorComparacionTotales extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(ValidadorComparacionTotales.class);

    /**
     * Este metodo se encarga de realizar la validadcion
     * @param FieldArgumentDTO
     *        objeto con la informacion
     * @exception FileProcessingException
     *            lanzada al procesar el archivo
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        String firmaMetodo = "validate(FieldArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> contexto = arg0.getContext();

        // se consulta el número de lote en el contexto
        Integer numeroLote = null;

        // se consulta el mapa de control de sumatorias por lote del contexto
        Map<Integer, ControlLoteOFDTO> mapaControlSumatorias = (Map<Integer, ControlLoteOFDTO>) contexto
                .get(ConstantesContexto.MAPA_CONTROL_SUMATORIAS_LOTES_OF);

        String mensaje = null;

        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        Object valorCampo = arg0.getFieldValue();

        if (valorCampo != null && !valorCampo.toString().isEmpty()) {
            Boolean error = Boolean.FALSE;
            String llaveContadorParametro = getParams().get(ConstantesParametroValidador.LLAVE_CONTADOR_SUMATORIA);
            String valorSumatoria = null;

            // La operación a realizar, depende de la llave específicada en el parámetro
            switch (llaveContadorParametro) {
                case ConstantesContexto.CONTADOR_REGISTROS_6:
                    // se obtiene el DTO con los datos de control relacionados con el lote de la línea
                    numeroLote = FuncionesValidador.buscarLoteLinea(arg0.getLineNumber(), mapaControlSumatorias);
                    if (numeroLote == null) {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                MessagesConstants.NUMERO_LOTE);

                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    ControlLoteOFDTO control = mapaControlSumatorias.get(numeroLote);
                    if (control.getContadorPlanillasLote().compareTo((Integer) valorCampo) != 0) {
                        error = Boolean.TRUE;
                        valorSumatoria = control.getContadorPlanillasLote().toString();
                    }
                    break;
                case ConstantesContexto.SUMATORIA_NUMERO_REGISTROS:
                    // se obtiene el DTO con los datos de control relacionados con el lote de la línea
                    numeroLote = FuncionesValidador.buscarLoteLinea(arg0.getLineNumber(), mapaControlSumatorias);
                    if (numeroLote == null) {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                MessagesConstants.NUMERO_LOTE);

                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    control = mapaControlSumatorias.get(numeroLote);
                    if (control.getSumatoriaCantidadRegistrosPlanillasLote().compareTo((Integer) valorCampo) != 0) {
                        error = Boolean.TRUE;
                        valorSumatoria = control.getSumatoriaCantidadRegistrosPlanillasLote().toString();
                    }
                    break;
                case ConstantesContexto.SUMATORIA_VALOR_PLANILLA:
                    // se obtiene el DTO con los datos de control relacionados con el lote de la línea
                    numeroLote = FuncionesValidador.buscarLoteLinea(arg0.getLineNumber(), mapaControlSumatorias);
                    if (numeroLote == null) {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                MessagesConstants.NUMERO_LOTE);

                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    control = mapaControlSumatorias.get(numeroLote);
                    if (control.getSumatoriaValorRecaudoPlanillasLote().compareTo((BigDecimal) valorCampo) != 0) {
                        error = Boolean.TRUE;
                        valorSumatoria = control.getSumatoriaValorRecaudoPlanillasLote().toPlainString();
                    }
                    break;
                case ConstantesContexto.SUMATORIA_CANTIDAD_PLANILLAS:
                    Integer sumaCantidadPlanillas = 0;
                    Iterator<Entry<Integer, ControlLoteOFDTO>> it = mapaControlSumatorias.entrySet().iterator();

                    while (it.hasNext()) {
                        Entry<Integer, ControlLoteOFDTO> par = it.next();
                        sumaCantidadPlanillas += par.getValue().getContadorPlanillasLote();
                    }

                    if (sumaCantidadPlanillas.compareTo((Integer) valorCampo) != 0) {
                        error = Boolean.TRUE;
                        valorSumatoria = sumaCantidadPlanillas.toString();
                    }
                    break;
                case ConstantesContexto.SUMATORIA_TOTAL_NUMERO_REGISTROS:
                    Integer sumaCantidadRegistrosPlanillas = 0;
                    it = mapaControlSumatorias.entrySet().iterator();

                    while (it.hasNext()) {
                        Entry<Integer, ControlLoteOFDTO> par = it.next();
                        sumaCantidadRegistrosPlanillas += par.getValue().getSumatoriaCantidadRegistrosPlanillasLote();
                    }

                    if (sumaCantidadRegistrosPlanillas.compareTo((Integer) valorCampo) != 0) {
                        error = Boolean.TRUE;
                        valorSumatoria = sumaCantidadRegistrosPlanillas.toString();
                    }
                    break;
                case ConstantesContexto.SUMATORIA_TOTAL_VALOR_PLANILLA:
                    BigDecimal sumaValorPlanillas = BigDecimal.ZERO;
                    it = mapaControlSumatorias.entrySet().iterator();

                    while (it.hasNext()) {
                        Entry<Integer, ControlLoteOFDTO> par = it.next();
                        sumaValorPlanillas = sumaValorPlanillas.add(par.getValue().getSumatoriaValorRecaudoPlanillasLote());
                    }

                    if (sumaValorPlanillas.compareTo((BigDecimal) valorCampo) != 0) {
                        error = Boolean.TRUE;
                        valorSumatoria = sumaValorPlanillas.toPlainString();
                    }
                    break;
                default:
                    break;
            }

            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_SUMATORIA_CONTEO_CAMPOS.getReadableMessage(idCampo, valorCampo.toString(), tipoError,
                        nombreCampo, valorCampo.toString(), valorSumatoria);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
}
