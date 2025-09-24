package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoNovedadPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de novedad del cotizante<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorNovedadCotizante extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorNovedadCotizante.class);

    /** Constantes para mensajes */
    private static final String TIPO_ARCHIVO = "tipo de archivo";
    private static final String TIPO_COTIZANTE = "tipo de cotizante";
    private static final String TIPO_NOVEDAD = "tipo de novedad";
    private static final String FECHA_NOVEDAD = "fecha de la novedad";

    /**
     * Metodo se encarga de validar novedad del cotizante
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String mensaje = null;

        String tipoNovedad = null;
        Integer tipoCotizante;
        Object valorCampo = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        String tipoArchivo = (String) args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        if (tipoArchivoEnum != null) {

            boolean error = false;

            valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_LEIDO));

            if (valorCampo != null && !valorCampo.toString().isEmpty()) {

                if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())) {

                    tipoNovedad = getParams().get(ConstantesParametroValidador.TIPO_NOVEDAD);
                    TipoNovedadPilaEnum tipoNovedadPilaEnum = TipoNovedadPilaEnum.obtenerTipoNovedadPila(tipoNovedad);

                    try {
                        tipoCotizante = (Integer) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE));
                    } catch (Exception e) {
                        tipoCotizante = null;
                    }

                    TipoCotizanteEnum tipoCotizanteEnum = null;
                    try {
                        tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);
                    } catch (Exception e) {
                    }

                    if (tipoCotizanteEnum == null) {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_COTIZANTE);

                        logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    if (tipoNovedadPilaEnum == null) {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_NOVEDAD);

                        logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    // para los casos en los que se reciba una fecha, se valida que el año y el mes sean iguales a los del periodo de pago
                    if (valorCampo instanceof Date) {
                        //CC Relajar validaciones Pila quito esta comparacion.
                        /*String periodoPago = args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_PERIODO_PAGO))
                                .toString();

                        Calendar fechaNovedad = new GregorianCalendar();
                        fechaNovedad.setTimeInMillis(((Date) valorCampo).getTime());

                        try {
                            Integer anioPeriodo = Integer.parseInt(periodoPago.split("-")[0]);
                            Integer mesPeriodo = Integer.parseInt(periodoPago.split("-")[1]);

                            if (!anioPeriodo.equals(fechaNovedad.get(Calendar.YEAR))
                                    || !mesPeriodo.equals(fechaNovedad.get(Calendar.MONTH) + 1)) {

                                mensaje = MensajesValidacionEnum.ERROR_CAMPO_FECHA_NOVEDAD_NO_CORRESPONDE_CON_PERIODO_PAGO_PLANILLA
                                        .getReadableMessage(idCampo, FuncionesValidador.formatoFecha(fechaNovedad), tipoError, nombreCampo,
                                                periodoPago);

                                logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                                throw new FileProcessingException(mensaje);
                            }

                        } catch (NumberFormatException e) {
                            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, FECHA_NOVEDAD);

                            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                            throw new FileProcessingException(mensaje);
                        }*/
                    }
                    else {

                        // para los casos en los que se reciba una marca de novedad, se valida el tipo de cotizante que la presenta
                        List<TipoCotizanteEnum> tiposCotizanteNoValidos = definirTiposCotizanteNoValidos(tipoNovedadPilaEnum,
                                (String) valorCampo);


                        error = true;

                        // se valida el tipo de cotizante del registro frente a las opciones diponibles para la novedad
                        if (!tiposCotizanteNoValidos.contains(tipoCotizanteEnum)) {


                            // para la novedad tipo IRP, se debe agregar una validación adicional de valor mínimo de días
                            if (TipoNovedadPilaEnum.NOVEDAD_IRP.equals(tipoNovedadPilaEnum)) {                 
                                           


                                // se comprueba formato numérico
                                if (StringUtils.isNumeric((String) valorCampo)) {
                                    error = false;

                                }
                                else {
                                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_VALOR_DEBE_SER_NUMERICO.getReadableMessage(idCampo,
                                            valorCampo.toString(), tipoError, nombreCampo, valorCampo.toString());

                                    logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                                    throw new FileProcessingException(mensaje);
                                }

                            }
                            else {
                                error = false;
                            }
                        }
                        else {
                            /*
                             * CONTROL DE CAMBIO 224118
                             * sí el tipo de cotizante no es válido para el tipo de novedad, se verifica que el valor del campo sea
                             * diferente a cero, caso contrario, no hay error
                             */
                            try {
                                if (Integer.parseInt((String) valorCampo) == 0) {
                                    error = false;
                                }
                            } catch (NumberFormatException nfe) {
                            }
                        }

                    }

                    if (error) {
                        mensaje = MensajesValidacionEnum.ERROR_CAMPO_NOVEDAD_NO_VALIDA_PARA_TIPO_COTIZANTE.getReadableMessage(idCampo,
                                valorCampo.toString(), tipoError, nombreCampo, valorCampo.toString(), tipoNovedad,
                                tipoCotizante.toString());

                        logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                }
                else {
                    // en el caso de los archivos de tipo IP e IPR, sólo se verifica una fecha respecto al período de pago
                    if (valorCampo instanceof Date) {
                        //CC Relajar validaciones Pila quito esta comparacion.
                        /*String periodoPago = args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_PERIODO_PAGO))
                                .toString();

                        Calendar fechaNovedad = new GregorianCalendar();
                        fechaNovedad.setTimeInMillis(((Date) valorCampo).getTime());

                        try {
                            Integer anioPeriodo = Integer.parseInt(periodoPago.split("-")[0]);
                            Integer mesPeriodo = Integer.parseInt(periodoPago.split("-")[1]);

                            if (!anioPeriodo.equals(fechaNovedad.get(Calendar.YEAR))
                                    || !mesPeriodo.equals(fechaNovedad.get(Calendar.MONTH) + 1)) {

                                mensaje = MensajesValidacionEnum.ERROR_CAMPO_FECHA_NOVEDAD_NO_CORRESPONDE_CON_PERIODO_PAGO_PLANILLA
                                        .getReadableMessage(idCampo,
                                                CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaNovedad.getTime()), tipoError,
                                                nombreCampo, CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaNovedad.getTime()),
                                                periodoPago);

                                logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                                throw new FileProcessingException(mensaje);
                            }

                        } catch (NumberFormatException e) {
                            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, FECHA_NOVEDAD);

                            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                            throw new FileProcessingException(mensaje);
                        }*/
                    }
                    else {
                        mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ES_VALIDO.getReadableMessage(idCampo, valorCampo.toString(),
                                tipoError, nombreCampo, valorCampo.toString());

                        logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                }
            }
        }
        else {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(tipoError, nombreCampo, TIPO_ARCHIVO);

            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para determinar el listado de tipos de cotizante que son válidos para presentar un tipo de novedad específica
     * 
     * @param tipoNovedadPilaEnum
     *        Tipo de novedad consultada
     * @param valorCampo
     *        Valor del campo en que se marca la novedad, puede cambiar el listado de respuesta
     * @return <b>List<TipoCotizanteEnum></b>
     *         Listado de tipos de cotizante que NO pueden presentar la novedad
     */
    private List<TipoCotizanteEnum> definirTiposCotizanteNoValidos(TipoNovedadPilaEnum tipoNovedadPilaEnum, String valorCampo) {
        logger.debug("Inicia definirTiposCotizanteValidos(TipoNovedadPilaEnum, String)");

        List<TipoCotizanteEnum> result = null;

        if (tipoNovedadPilaEnum != null) {
            result = new ArrayList<>();

            switch (tipoNovedadPilaEnum) {
                case NOVEDAD_ING:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    break;
                case NOVEDAD_RET:
                    if(valorCampo.equals("X")){
                    // result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    //result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                    //result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    //result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    // result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    //result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    //result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    //result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    // result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ);
                    }
                 else if (valorCampo.equals("R")) {
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MUJER_CON_APORTE);
                    }
                    else if(valorCampo.equals("C")){
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE); 
                  // result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO); 
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                   result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MUJER_CON_APORTE);
                    } 
                  else if( valorCampo.equals("P")){
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MUJER_CON_APORTE);
                    }
                  else if (valorCampo.equals("T")) {
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO); 
                   // result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MUJER_CON_APORTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);  
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    }
                    break; 
                case NOVEDAD_SLN:
                    if (valorCampo.equals("X")) {
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    }
                    else if (valorCampo.equals("C")) {
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL); 
                    }
                    break;
                case NOVEDAD_IGE:
                   if(valorCampo.equals("X")){
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MUJER_CON_APORTE); 
                       //agregar el tipo de cotizante 71
                    }else if(valorCampo.equals("L")){
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);    
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL); 
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO); 
                    }
                    break;
                case NOVEDAD_LMA:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    break;
                case NOVEDAD_VAC_LR:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    break;
                case NOVEDAD_TAE:
                case NOVEDAD_TDE:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    break;
                case NOVEDAD_VSP:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    break;
                case NOVEDAD_VST:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    break;
                case NOVEDAD_IRP:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    break;
                case NOVEDAD_AVP:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    break;
                case NOVEDAD_VCT:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    break;
                case NOVEDAD_TAP:
                case NOVEDAD_TDP:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    break;
            }
        }

        logger.debug("Finaliza definirTiposCotizanteValidos(TipoNovedadPilaEnum, String)");
        return result;
    }

}
