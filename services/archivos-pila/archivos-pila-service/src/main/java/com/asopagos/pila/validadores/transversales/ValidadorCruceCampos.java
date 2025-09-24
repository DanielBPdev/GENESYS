package com.asopagos.pila.validadores.transversales;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 2 del archivo I<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorCruceCampos extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorCruceCampos.class);

    /** Constante mensaje */
    private static final String TIPO_COMPARACION = "tipo de comparación deseada";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param LineArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lazado cuando hay error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicio validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtiene los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        Object valorCampoValidado = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_VALIDADO));
        Object valorCampoReferencia = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_REFERENCIA));

        // sí el valor del campo de referencia es texto vacío o cero, se pasa a null 

        if (valorCampoReferencia != null && valorCampoReferencia instanceof String) {
            if (((String) valorCampoReferencia).isEmpty()
                    || (NumberUtils.isParsable((String) valorCampoReferencia) && Integer.valueOf((String) valorCampoReferencia) == 0)) {
                valorCampoReferencia = null;
            }
        }
        else if (valorCampoReferencia != null && valorCampoReferencia instanceof Integer
                && ((Integer) valorCampoReferencia).compareTo(0) == 0) {
            valorCampoReferencia = null;
        }

        // el valor del campo validado se convierte a null en caso de ser un cero o un texto vacío
        if (valorCampoValidado != null && (valorCampoValidado instanceof Number && (((Number) valorCampoValidado).longValue() == 0)
                || (valorCampoValidado instanceof BigDecimal && ((BigDecimal) valorCampoValidado).compareTo(BigDecimal.ZERO) == 0)
                || (valorCampoValidado instanceof String && ((String) valorCampoValidado).equals("")))) {
            valorCampoValidado = null;
        }

        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO_VALIDADO);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        String nombreCampoReferencia = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO_REFERENCIA);

        Integer casoValoracion = null;
        String casoString = getParams().get(ConstantesParametroValidador.CASO_VALORACION);
        if (casoString != null && StringUtils.isNumeric(casoString)) {
            casoValoracion = Integer.valueOf(casoString);
        }

        String valorReferencia = getParams().get(ConstantesParametroValidador.VALOR_REFERENCIA);
        // sí llega una cadena vacía, se convierte a null, el valor cero también se iguala a null
        if ((valorReferencia != null && valorReferencia.isEmpty())
                || (valorReferencia != null && StringUtils.isNumeric(valorReferencia) && Integer.parseInt(valorReferencia) == 0)) {
            valorReferencia = null;
        }

        boolean error = false;

        if (casoValoracion == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_COMPARACION);
            error = true;
        }
        else {

            /**
             * Se contemplan 6 escenarios
             * caso 1: cuando el campo referencia sea null, el campo a validar también debe ser null
             * caso 2: cuando el campo referencia sea null, el campo a validar no debe ser null
             * caso 3: cuando el campo referencia no sea null, el campo a validar debe ser null
             * caso 4: cuando el campo referencia no sea null, el campo a validar tampoco debe ser null
             * caso 5: cuando el campo referencia es igual al valor de referencia, el campo a validar debe ser null
             * caso 6: cuando el campo referencia es igual al valor de referencia, el campo a validar no debe ser null
             * caso 7: cuando el campo referencia sea null, el campo a validar puede ser diferente de null (opcional)
             * caso 8: cuando el campo referencia esté en lista de referencia, el campo a validar no debe ser null
             * caso 9: cuando el campo referencia no sea null, el campo a validar se hace obligatorio
             */
            switch (casoValoracion) {
                case 1:
                    if (valorCampoReferencia == null) {
                        if (valorCampoValidado != null) {
                            error = true;

                            mensaje = MensajesValidacionEnum.ERROR_CRUCE_VALOR_VS_VACIO.getReadableMessage(idCampo,
                                    valorCampoValidado.toString(), tipoError, nombreCampo, valorCampoValidado.toString(),
                                    nombreCampoReferencia);
                        }
                    }
                    else if (valorCampoValidado == null) {
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_NO_VACIO.getReadableMessage(idCampo, "", tipoError,
                                nombreCampo, nombreCampoReferencia, valorCampoReferencia.toString());
                    }
                    break;
                case 2:
                    if (valorCampoReferencia == null) {
                        if (valorCampoValidado == null) {
                            error = true;

                            mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_VACIO.getReadableMessage(idCampo, "", tipoError,
                                    nombreCampo, nombreCampoReferencia);
                        }
                    }
                    else if (valorCampoValidado != null) {
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_VALOR_VS_NO_VACIO.getReadableMessage(idCampo,
                                valorCampoValidado.toString(), tipoError, nombreCampo, valorCampoValidado.toString(), nombreCampoReferencia,
                                valorCampoReferencia.toString());
                    }
                    break;
                case 3:
                    if (valorCampoReferencia != null) {
                        if (valorCampoValidado != null) {
                            error = true;

                            mensaje = MensajesValidacionEnum.ERROR_CRUCE_VALOR_VS_NO_VACIO.getReadableMessage(idCampo,
                                    valorCampoValidado.toString(), tipoError, nombreCampo, valorCampoValidado.toString(),
                                    nombreCampoReferencia, valorCampoReferencia.toString());
                        }
                    }
                    else if (valorCampoValidado == null) {
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_VACIO.getReadableMessage(idCampo, "", tipoError,
                                nombreCampo, nombreCampoReferencia);
                    }
                    break;
                case 4:
                    if (valorCampoReferencia != null) {
                        if (valorCampoValidado == null) {
                            error = true;

                            mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_NO_VACIO.getReadableMessage(idCampo, "", tipoError,
                                    nombreCampo, nombreCampoReferencia, valorCampoReferencia.toString());
                        }
                    }
                    else if (valorCampoValidado != null) {
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_VALOR_VS_VACIO.getReadableMessage(idCampo,
                                valorCampoValidado.toString(), tipoError, nombreCampo, valorCampoValidado.toString(),
                                nombreCampoReferencia);
                    }
                    break;
                case 5:
                    // el valor del campo de referencia concuerda con el valor de referencia
                    if (valorReferencia != null && valorCampoReferencia != null && valorCampoReferencia.equals(valorReferencia)) {
                        if (valorCampoValidado != null) {
                            error = true;
                            mensaje = MensajesValidacionEnum.ERROR_CRUCE_VALOR_VS_REFERENCIA.getReadableMessage(idCampo,
                                    valorCampoValidado.toString(), tipoError, nombreCampo, valorCampoValidado.toString(),
                                    nombreCampoReferencia, valorReferencia);
                        }
                    }
                    else if ((valorReferencia == null && valorCampoReferencia == null) && valorCampoValidado == null) {
                        // falta el valor de referencia o el valor de campo de referencia
                        error = true;
                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_VACIO_NO_REFERENCIA.getReadableMessage(idCampo, "",
                                tipoError, nombreCampo, nombreCampoReferencia, valorReferencia);
                    }
                    else if ((valorReferencia != null && valorCampoReferencia != null && !valorCampoReferencia.equals(valorReferencia))
                            && valorCampoValidado == null) {
                        // los valores de referencia y campo de referencia no coinciden
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_NO_REFERENCIA.getReadableMessage(idCampo, "", tipoError,
                                nombreCampo, nombreCampoReferencia, valorReferencia, valorCampoReferencia.toString());

                    }
                    break;
                case 6:
                    // el valor del campo de referencia concuerda con el valor de referencia
                    if (valorReferencia != null && valorCampoReferencia != null && valorCampoReferencia.equals(valorReferencia)) {
                        if (valorCampoValidado == null) {
                            error = true;

                            mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_REFERENCIA.getReadableMessage(idCampo, "", tipoError,
                                    nombreCampo, nombreCampoReferencia, valorReferencia);
                        }
                    }
                    else if ((valorReferencia == null || valorCampoReferencia == null) && valorCampoValidado == null) {
                        // falta el valor de referencia o el valor de campo de referencia
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_VACIO_NO_REFERENCIA.getReadableMessage(idCampo, "",
                                tipoError, nombreCampo, nombreCampoReferencia, valorReferencia);

                    }
                    else if ((valorReferencia != null && valorCampoReferencia != null && !valorCampoReferencia.equals(valorReferencia))
                            && valorCampoValidado != null) {
                        // los valores de referencia y campo de referencia no coinciden
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_VALOR_VS_REFERENCIA.getReadableMessage(idCampo,
                                valorCampoValidado.toString(), tipoError, nombreCampo, valorCampoValidado.toString(), nombreCampoReferencia,
                                valorReferencia, valorCampoReferencia.toString());
                    }
                    break;
                case 7:
                    if (valorReferencia != null && valorCampoReferencia != null && valorCampoReferencia.equals(valorReferencia)
                            && valorCampoValidado == null) {
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_REFERENCIA.getReadableMessage(idCampo, "", tipoError,
                                nombreCampo, nombreCampoReferencia, valorReferencia);
                    }
                    break;
                case 8:
                    // el valor de referencia se convierte a lista serparando por comas
                    List<String> listaReferencia = null;
                    if (valorReferencia != null) {
                        listaReferencia = Arrays.asList(valorReferencia.split(","));

                        String valorCampoReferenciaString = null;
                        if (valorCampoReferencia instanceof Number) {
                            valorCampoReferenciaString = Long.toString(((Number) valorCampoReferencia).longValue());
                        }
                        else if (valorCampoReferencia instanceof Date) {
                            valorCampoReferenciaString = FuncionesValidador.formatoFecha(valorCampoReferencia);
                        }
                        else {
                            valorCampoReferenciaString = (String) valorCampoReferencia;
                        }

                        // el valor del campo de referencia se busca en el listado como un String
                        if (valorCampoReferenciaString != null && !listaReferencia.contains(valorCampoReferenciaString)
                                && valorCampoValidado != null) {
                            error = true;
                            mensaje = MensajesValidacionEnum.ERROR_CRUCE_VALOR_VS_NO_REFERENCIA.getReadableMessage(idCampo,
                                    valorCampoValidado != null && valorCampoValidado instanceof Date
                                            ? FuncionesValidador.formatoFecha(valorCampoValidado) : valorCampoValidado.toString(),
                                    tipoError, nombreCampo, nombreCampoReferencia, valorReferencia, valorCampoReferenciaString);
                        }
                        else if (valorCampoReferencia != null && listaReferencia.contains(valorCampoReferenciaString)
                                && valorCampoValidado == null) {
                            error = true;

                            mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_REFERENCIA.getReadableMessage(idCampo, "", tipoError,
                                    nombreCampo, nombreCampoReferencia, valorReferencia);

                        }
                    }
                    break;
                case 9:
                    if (valorCampoReferencia != null && valorCampoValidado == null) {
                        error = true;

                        mensaje = MensajesValidacionEnum.ERROR_CRUCE_SIN_VALOR_VS_NO_VACIO.getReadableMessage(idCampo, "", tipoError,
                                nombreCampo, nombreCampoReferencia, valorCampoReferencia.toString());
                    }
                    break;
                default:
                    break;
            }
        }

        if (error) {
            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}