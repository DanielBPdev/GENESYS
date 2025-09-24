package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.modelo.DescuentoInteresMoraModeloDTO;
import com.asopagos.entidades.ccf.aportes.TasasInteresMora;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.dto.ErrorValidacionValorMoraDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo valor mora<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorValorMora extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorValorMora.class);

    /**
     * Metodo se encarga de validar del campo valor mora
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorValorMora.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();
        Map<String, Object> contexto = args.getContext();

        Object valor = null;

        // se obtienen los períodos de tasa de interes y casos de descuento en mora del contexto
        List<TasasInteresMora> periodosInteres = (List<TasasInteresMora>) contexto.get(ConstantesContexto.TASAS_INTERES);
        List<DescuentoInteresMoraModeloDTO> casosDescuento = (List<DescuentoInteresMoraModeloDTO>) contexto
                .get(ConstantesContexto.CASOS_DESCUENTO_INTERES);

        // se lee el tipo del archivo
        String tipoArchivo = (String) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);
        String tipoErrorParam = "TIPO_2";

        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        if (periodosInteres == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoErrorParam, nombreCampo, MessagesConstants.LISTADO_PERIODOS);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (tipoArchivoEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoErrorParam, nombreCampo, MessagesConstants.TIPO_ARCHIVO);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        
        if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())) {
            /*
             * los archivos de dependientes / independientes, se deben calcular por fuera de FileProcessing,
             * ante la posibilidad de que se presente un registro tipo 4 que afecte el cálculo de descuento en mora.
             * En este caso, se almacena la información en el contexto para su posterior validación
             */
            contexto.put(ConstantesContexto.ID_CAMPO, idCampo);
            contexto.put(ConstantesContexto.NOMBRE_CAMPO, nombreCampo);
            contexto.put(ConstantesContexto.TIPO_ERROR, tipoError);
            contexto.put(ConstantesContexto.LINEA_VALOR_MORA, args.getLineNumber());
        }

        // se leen las fechas de vencimiento y pago del contexto
        Date fechaVencimiento = (Date) contexto.get(ConstantesContexto.FECHA_VENCIMIENTO);
        Date fechaPago = null;

        if (contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_FECHA_PAGO)) instanceof String) {
            fechaPago = FuncionesValidador
                    .convertirDate((String) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_FECHA_PAGO)));
        }
        else if (contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_FECHA_PAGO)) instanceof Date) {
            fechaPago = (Date) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_FECHA_PAGO));
        }

        if (fechaPago == null) {
            // Error por falta de fecha de pago y/o vencimiento
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoErrorParam, nombreCampo, MessagesConstants.FECHA_APORTE);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (fechaVencimiento == null) {
            fechaVencimiento = fechaPago;
        }

        // se lee el campo de valor de mora
        valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_VALOR_MORA));
        BigDecimal valorMora = null;
        if (valor != null && !valor.toString().isEmpty()) {
            valorMora = (BigDecimal) valor;
        }

        if (valorMora == null) {
            // Error por falta de fecha de pago y/o vencimiento
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoErrorParam, nombreCampo,
                    MessagesConstants.VALOR_MORA_PLANILLA);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // este dato es requerido para el cálculo del valor total del aporte
        // por tal motivo se agrega al contexto sí aún no existe
        if (contexto.get(ConstantesContexto.VALOR_MORA) == null) {
            contexto.put(ConstantesContexto.VALOR_MORA, valorMora);
        }

        // se consulta el rango de tolerancia en el valor de la mora
        BigDecimal toleranciaValorMora = (BigDecimal) contexto.get(ConstantesContexto.TOLERANCIA_VALOR_MORA);

        // se inicializa el valor de mora calculado
        BigDecimal valorTotalMoraCalculado = new BigDecimal(0);
        valorTotalMoraCalculado.setScale(16, BigDecimal.ROUND_HALF_UP);

        /*
         * sí la fecha de pago es posterior a la fecha de vencimiento, se requiere cálculo de los días de mora para
         * comparar con el dato de la planilla
         */
        if (CalendarUtil.compararFechas(fechaPago, fechaVencimiento) > 0) {
            BigDecimal valorAporte = null;

            // se lee el valor del aporte del contexto para el caso de archivo de archivo I
            if (!GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())) {
                valorAporte = (BigDecimal) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_VALOR_APORTE));
            }
            else {
                // en el caso de un archivo IP, se lee el registro 3, campo 2
                valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.LLAVE_VALOR_APORTE));
                if (valor != null && !valor.toString().isEmpty()) {
                    valorAporte = (BigDecimal) valor;
                }
            }
            
            if (valorAporte == null) {
                // Error por falta de fecha de pago y/o vencimiento
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoErrorParam, nombreCampo,
                        MessagesConstants.VALOR_APORTE_PLANILLA);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (toleranciaValorMora == null)
                toleranciaValorMora = new BigDecimal(0);

            toleranciaValorMora.setScale(2, BigDecimal.ROUND_HALF_UP);

            ErrorValidacionValorMoraDTO resultado = FuncionesValidador.calcularValorMora(periodosInteres, fechaVencimiento, fechaPago,
                    valorAporte);
            
            //Se busca la naturaleza juridica traida desde el archivo I y Tipo Planilla
            Integer naturalezaJuridica = null;
            TipoPlanillaEnum tipoPlanillaEnum = null;
            
            if (contexto.get(ConstantesContexto.INFORMACION_BLOQUE_3) != null) {
            	Map<String, Object>  informacionBloque3Map=(Map<String, Object>) contexto.get(ConstantesContexto.INFORMACION_BLOQUE_3);
            	
            	if( informacionBloque3Map!=null) {
            		Object objetoNaturaleza = informacionBloque3Map.get("naturaleza");
            		
            		if(objetoNaturaleza!= null && objetoNaturaleza instanceof String[]) {
            				String [] naturalezaArray = (String[]) objetoNaturaleza;
                			
            				try {
            					if(naturalezaArray != null && naturalezaArray.length>=2) {
                    				naturalezaJuridica = Integer.valueOf(naturalezaArray[1]);
                    			}
							} catch (Exception e) {
								naturalezaJuridica = null;
							}                        			
            		}
            	}
            }   
            
            // se toma el tipo de planilla del contexto
            if (contexto.get(ConstantesContexto.TIPO_PLANILLA) != null) {
            	String valorCampoTipoPlanilla = null;
            	try {
            		valorCampoTipoPlanilla = (String) contexto.get(ConstantesContexto.TIPO_PLANILLA);
                    String tipoPlanilla = null;
                    if (valorCampoTipoPlanilla instanceof String) {
                        tipoPlanilla = (String) valorCampoTipoPlanilla;
                    }
                    else if (valorCampoTipoPlanilla != null) {
                        tipoPlanilla = valorCampoTipoPlanilla.toString();
                    }

                    tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);
				} catch (Exception e) {
					valorCampoTipoPlanilla = null;
					tipoPlanillaEnum = null;
				}
                
            }


            if (resultado.getIndicioMensaje() == null) {
            	if(!(naturalezaJuridica!= null && naturalezaJuridica== 1 && TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum))) {
            		valorTotalMoraCalculado = resultado.getValorMoraCalculado();
            	}else{
            		valorTotalMoraCalculado = new BigDecimal(0);
            	}
            }
            else {
            	//Solo se revisan estos tipos de errores para las planillas diferentes a N y naturalezaJuridica != 1
            	if(!(naturalezaJuridica!= null && naturalezaJuridica== 1 && TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum))) {
            		switch (resultado.getIndicioMensaje()) {
	                    case MessagesConstants.CALCULO_DIAS_PERIODO:
	                        // Error en cálculo de los días en el período de interés
	                        mensaje = MensajesValidacionEnum.ERROR_CALCULO.getReadableMessage(idCampo, valorMora.toPlainString(), tipoError,
	                                nombreCampo, MessagesConstants.CALCULO_DIAS_PERIODO, resultado.getMensajeErrorDetallado());
	                        break;
	                    case MessagesConstants.ERROR_TECNICO:
	                        // Error en la consulta del rango de períodos de tasa de interés, se debe registrar como error técnico de validador
	                        mensaje = MensajesValidacionEnum.ERROR_CAMPO_SIN_VALIDAR_FALLO_EN_RANGO_PERIODOS.getReadableMessage(idCampo,
	                                valorMora.toPlainString(), TipoErrorValidacionEnum.ERROR_TECNICO.toString(), nombreCampo);
	                        break;
	                    case MessagesConstants.PERIODOS_INTERES:
	                        // Error por falta de primer y/o último período de tasa de interés
	                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
	                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoErrorParam, nombreCampo,
	                                MessagesConstants.PERIODOS_INTERES);
	                        break;
	                    default:
	                        break;
	                }
            	}
                

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        String periodoPago = (String) contexto.get(ConstantesContexto.NOMBRE_PERIODO_PAGO);
        Set<String> tiposCotizantes = (HashSet<String>) contexto.get(ConstantesContexto.TIPOS_COTIZANTES_ENCONTRADOS);

        if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())) {
            /*
             * los archivos de dependientes / independientes, se deben calcular por fuera de FileProcessing,
             * ante la posibilidad de que se presente un registro tipo 4 que afecte el cálculo de descuento en mora.
             * En este caso, se almacena la información en el contexto para su posterior validación
             */
            contexto.put(ConstantesContexto.VALOR_MORA_CALCULADO, valorTotalMoraCalculado);
        }
        else {
            // se aplica el descuento de mora que sea procedente
            valorTotalMoraCalculado = FuncionesValidador.aplicarDescuentoMora(valorTotalMoraCalculado, casosDescuento,
                    tipoArchivoEnum.getPerfilArchivo(), null, fechaPago.getTime(), periodoPago, tiposCotizantes);

            if (!FuncionesValidador.validarValorMora(valorMora, valorTotalMoraCalculado, toleranciaValorMora)) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_PRESENTA_DIFERENCIA_RESPECTO_A_VALOR_CALCULADO_PLANILLA.getReadableMessage(
                        idCampo, valorMora.toPlainString(), tipoError, nombreCampo, valorTotalMoraCalculado.toPlainString());

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
}
