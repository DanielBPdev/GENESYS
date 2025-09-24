package com.asopagos.pila.validadores.bloque2;

import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de horas laboradas<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorHorasLaboradas extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorHorasLaboradas.class);

    /** Constantes para mensajes */
    private static final String TIPO_COTIZANTE_PARAMETRO = "tipo de cotizante parametrizado no válido";
    private static final String TIPO_COTIZANTE = "tipo de cotizante";
    private static final String TIPOS_COTIZANTES_CON_HORAS = "tipos de cotizante que exigen una cantidad de horas laboradas";

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorHorasLaboradas.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE));
        
      

        Integer tipoCotizante = null;
        if (valor != null && !valor.toString().isEmpty()) {
            tipoCotizante = (Integer) valor;
        }
        TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

        valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_HORAS_LABORADAS));

        Integer horasLaboradas = null;
        Boolean valorEsNumerico = false;

        //Se hace validacion expresion regular solo numeros entre 0 y 999
        if (valor != null && !valor.toString().isEmpty() && valor.toString().matches("[0-9]{0,3}")) {
        		horasLaboradas = Integer.parseInt ((String) valor);
    	    	valorEsNumerico = true;
        }

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        //Se hace esta condicion debido a valores Ej: +40  MANTIS 255370
        if (valor != null && !valor.toString().isEmpty() && !valorEsNumerico) {
       	 	String valorCadena = valor.toString();

       	 	mensaje = MensajesValidacionEnum.ERROR_CAMPO_DEBE_SER_NUMERICO.getReadableMessage(idCampo, valorCadena,
                 tipoError, nombreCampo, valorCadena);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FileProcessingException(mensaje);
        }else if (horasLaboradas == null) {
            // cuando no se cuenta con las horas laboradas, se valida que el tipo de cotizante permita que este campo sea nulo
            String tipoCotizanteHoras = (String) getParams().get(ConstantesParametroValidador.TIPO_COTIZANTE_HORAS);

            if (tipoCotizanteHoras != null && !tipoCotizanteHoras.isEmpty()) {
                try {
                    if (tipoCotizanteEnum != null) {
                        String[] tipos = tipoCotizanteHoras.split(",");
                        for (String tipo : tipos) {
                            // en este caso, por parámetro se reciben los tipos de cotizante que requieren una cantidad de horas laboradas
                            // se emplea la enumeración a partir de la parametrización
                            TipoCotizanteEnum tipoCotizanteHorasEnum = TipoCotizanteEnum.obtenerTipoCotizante(Integer.parseInt(tipo));

                            // se verifica que se tenga parametrizado un tipo de cotizante válido
                            if (tipoCotizanteHorasEnum == null) {
                                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                        TIPO_COTIZANTE_PARAMETRO);

                                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                                throw new FileProcessingException(mensaje);
                            }

                            // se compara el tipo de cotizante en el archivo para determinar sí requiere de un valor de horas laboradas

                            if (tipoCotizanteHorasEnum.equals(tipoCotizanteEnum)) {
                                mensaje = MensajesValidacionEnum.ERROR_CAMPO_TIPO_COTIZANTE_EXIGE_CANTIDAD_HORAS_LABORADAS
                                        .getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError,
                                                nombreCampo, tipoCotizanteEnum.getDescripcion());

                                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                                throw new FileProcessingException(mensaje);
                            }
                        }
                    }
                    else {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_COTIZANTE);

                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                        throw new FileProcessingException(mensaje);
                    }
                } catch (NumberFormatException e) {
                    mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                            ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPOS_COTIZANTES_CON_HORAS);

                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                    throw new FileProcessingException(mensaje);
                }
            }
            else {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPOS_COTIZANTES_CON_HORAS);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new FileProcessingException(mensaje);
            }

            logger.debug("Finaliza validate(LineArgumentDTO args)");
        }
        // se valida que no se presenten horas negativas
        else if (horasLaboradas != null && horasLaboradas < 0 ) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_HORAS_COTIZADAS_NEGATIVAS.getReadableMessage(idCampo, horasLaboradas.toString(),
                    tipoError, nombreCampo);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FileProcessingException(mensaje);
        }
        
        // validación campo numérico
        String valorCadena = valor.toString();
        logger.debug("valorCadena: " + valorCadena);
        String regexNumerico = "[0-9]+";
        if(!valorCadena.matches(regexNumerico)){
        	 mensaje = MensajesValidacionEnum.ERROR_CAMPO_DEBE_SER_NUMERICO.getReadableMessage(idCampo, valorCadena,
                     tipoError, nombreCampo);

             logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
             throw new FileProcessingException(mensaje);
        	
        	
        }
    }

}
