package com.asopagos.pila.validadores.bloque2;

import java.util.List;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.dto.CasoRevisionNovMultipleDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.dto.ResumenRegistro2DTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripcion:</b> CONTROL DE CAMBIOS 224118 - Clase que implementa la validación de la presentación de novedades en
 * resgistros separados para un mismo cotizante<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ValidadorNovedadesMultiplesComparacion extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorNovedadesMultiplesComparacion.class);

    /**
     * Tipo de inconsistencia presentada
     */
    private String tipoError = null;

    /**
     * DTO de resultado de bloque de validación en el cual se agregan los errores detectados
     */
    private RespuestaValidacionDTO erroresResultado = null;

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator#validate(co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO args) throws FileProcessingException {
        logger.debug(ConstantesComunes.INICIO_LOGGER + "validate(FieldArgumentDTO)");
        
        this.tipoError = null;

        Map<String, Object> contexto = args.getContext();

        // se lee el tipo de inconsistencia desde parámetro
        tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);

        // se carga el listado de los registros tipo 2 resumidos para la validación
        List<CasoRevisionNovMultipleDTO> listaCasos = (List<CasoRevisionNovMultipleDTO>) contexto
                .get(ConstantesContexto.LISTA_CASOS_NOVEDAD_MULTIPLE);

        // se consulta el listado de errores para agregar las inconsistencias que se encuentren
        erroresResultado = (RespuestaValidacionDTO) contexto.get(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO);

        // sí el resultado de errores no se encuentra instanciado, se crea
        if (erroresResultado == null) {
            erroresResultado = new RespuestaValidacionDTO();
        }

        if (listaCasos != null) {
            // se recorre el listado para establecer establecer los cotizantes que presentaron más de un registro tipo2
            for (CasoRevisionNovMultipleDTO casoRevision : listaCasos) {
                // sólo se consideran para revisión los cotizantes que presentan más de un registro tipo 2
                if (casoRevision.getRegistrosTipo2().size() > 1) {
                    Integer sumatoriaDias = 0;
                    Boolean tipoCotizanteIgual = true;
                    TipoCotizanteEnum tipoAnterior = null;

                    // sumatoria de los días cotizados de todos los registros 2 y comprobación de mismo tipo
                    for (ResumenRegistro2DTO registro2 : casoRevision.getRegistrosTipo2()) {
                        sumatoriaDias += registro2.getDiasCotizados();

                        if (tipoAnterior != null && !registro2.getTipoCotizante().equals(tipoAnterior)) {
                            tipoCotizanteIgual = false;
                        }
                        else {
                            tipoAnterior = registro2.getTipoCotizante();
                        }
                    }

                    if (sumatoriaDias.compareTo(30) > 0) {

                        // se crea el error
                        String mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_DIAS_COTIZADOS.getReadableMessage(tipoError,
                                casoRevision.getTipoIdCotizante().getDescripcion(), casoRevision.getNumIdCotizante(),
                                sumatoriaDias.toString());

                        erroresResultado.addErrorDetalladoValidadorDTO(FuncionesValidador.prepararError(mensaje,
                                BloqueValidacionEnum.BLOQUE_2_OI, casoRevision.getRegistrosTipo2().get(0).getNumeroLinea()));

                    }
                    
                    if(!tipoCotizanteIgual){

                        // se crea el error
                        String mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_TIPO_COTIZANTE.getReadableMessage(tipoError,
                                casoRevision.getTipoIdCotizante().getDescripcion(), casoRevision.getNumIdCotizante());

                        erroresResultado.addErrorDetalladoValidadorDTO(FuncionesValidador.prepararError(mensaje,
                                BloqueValidacionEnum.BLOQUE_2_OI, casoRevision.getRegistrosTipo2().get(0).getNumeroLinea()));
                    }

                    // para cotizante que presente múltiples registros tipo 2
                    //                    List<ResumenRegistro2DTO> registrosTipo2 = casoRevision.getRegistrosTipo2();
                    //
                    //                    // se compara que los IBC de todas las líneas sean diferentes
                    //                    Boolean todosIBCDiferentes = compararIBCRegistros(registrosTipo2); desactivado por CC 0226581
                    //
                    //                    // las novedades ING y RET deben estar en la misma línea
                    //                    Integer diasCotizadosIngreso = validarINGyRET(registrosTipo2); desactivado por CC 0226581
                    //
                    //                    // las novedades diferentes a ING y RET deben estar en registros separados, siempre que se cumpla que los IBC 
                    //                    // sean diferentes
                    //                    validarOtrasNovedades(registrosTipo2, todosIBCDiferentes); desactivado por CC 0226581
                    //
                    //                    // la sumatoria de días cotizados de las novedades debe ser 30 sí no hay ING o RET
                    //                    validarSumatoriaDiasCotizados(registrosTipo2, diasCotizadosIngreso, casoRevision.getTipoIdCotizante(),
                    //                            casoRevision.getNumIdCotizante());
                    //
                    //                    // el valor del IBC se calcula con base en la cantidad de días cotizados
                    //                    validarValoresIBC(registrosTipo2, contexto); desactivado por CC 0226581
                }
            }
        }

        // se actualiza el listado de errores en el contexto
        if (erroresResultado != null) {
            if (contexto.get(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO) != null) {
                contexto.replace(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO, erroresResultado);
            }
            else {
                contexto.put(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO, erroresResultado);
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + "validate(FieldArgumentDTO)");
    }

    /**
     * Método encargado de determinar si todos los IBC en de registro tipo 2 son diferentes
     * 
     * @param registrosTipo2
     *        Listado de los registros tipo 2 del cotizante
     * @return <b>Boolean</b>
     *         Indicador de diferencia en IBC
     */
    //    private Boolean compararIBCRegistros(List<ResumenRegistro2DTO> registrosTipo2) { desactivado por CC 0226581
    //        String firmaMetodo = "compararIBCRegistros(List<ResumenRegistro2DTO>)";
    //        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    //
    //        Boolean result = true;
    //
    //        // se toman los IBC de los registros tipo 2 en un set para determinar que un valor ya se encuentra en lista
    //        Set<BigDecimal> ibcSet = new HashSet<>();
    //        for (ResumenRegistro2DTO resumenRegistro2 : registrosTipo2) {
    //            if (!ibcSet.add(resumenRegistro2.getValorIBC())) {
    //                // el Set indica que el valor ya existe, se cambia la respuesta y se prepara el error
    //                result = false;
    //
    //                // sí el resultado de errores no se encuentra instanciado, se crea
    //                if (erroresResultado == null) {
    //                    erroresResultado = new RespuestaValidacionDTO();
    //                }
    //
    //                // se crea el error
    //                String mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_IGUAL_IBC.getReadableMessage(tipoError,
    //                        resumenRegistro2.getValorIBC().toPlainString());
    //
    //                erroresResultado.addErrorDetalladoValidadorDTO(
    //                        FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, resumenRegistro2.getNumeroLinea()));
    //            }
    //        }
    //
    //        logger.debug(ConstantesComunesProcesamientoPILA.FIN_LOGGER + firmaMetodo);
    //        return result;
    //    }

    /**
     * Método encargado de validar que las novedades de tipo ING y RET se encuentren en el mismo registro tipo 2
     * 
     * @param registrosTipo2
     *        Listado de los registros tipo 2 del cotizante
     * @return <b>Integer</b>
     *         Cantidad de días cotizados en novedad ING/RET en caso de presentarse
     */
    //    private Integer validarINGyRET(List<ResumenRegistro2DTO> registrosTipo2) { desactivado por CC 0226581
    //        String firmaMetodo = "validarINGyRET(List<ResumenRegistro2DTO>)";
    //        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    //
    //        String mensaje = null;
    //        Integer result = null;
    //
    //        Long numLineaING = null;
    //        Long numLineaRET = null;
    //        Integer diasING = null;
    //        Integer diasRET = null;
    //
    //        Boolean ingresoRetiroMultiple = false;
    //        Set<Long> numerosLineaIngRetMultiple = new HashSet<>();
    //
    //        // se recorren los registros tipo 2 del cotizante para determinar el número de línea de las novedades ING y RET
    //        for (ResumenRegistro2DTO resumenRegistro2 : registrosTipo2) {
    //            // se verifica en cada caso sí se encuentran 2 ingresos y/o 2 retiros para determinar error
    //            if (resumenRegistro2.getPresentaING()) {
    //                if (numLineaING != null) {
    //                    ingresoRetiroMultiple = true;
    //                }
    //                numLineaING = resumenRegistro2.getNumeroLinea();
    //                diasING = resumenRegistro2.getDiasCotizados();
    //                numerosLineaIngRetMultiple.add(resumenRegistro2.getNumeroLinea());
    //            }
    //
    //            if (resumenRegistro2.getPresentaRET()) {
    //                if (numLineaRET != null) {
    //                    ingresoRetiroMultiple = true;
    //                }
    //                numLineaRET = resumenRegistro2.getNumeroLinea();
    //                diasRET = resumenRegistro2.getDiasCotizados();
    //                numerosLineaIngRetMultiple.add(resumenRegistro2.getNumeroLinea());
    //            }
    //        }
    //
    //        if (ingresoRetiroMultiple) {
    //            // se agrega un error por cada línea en la que se encuentra un ingreso o retiro múltiple
    //            for (Long numeroLinea : numerosLineaIngRetMultiple) {
    //                mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_ING_RET_MULTIPLE.getReadableMessage(tipoError);
    //
    //                // sí el resultado de errores no se encuentra instanciado, se crea
    //                if (erroresResultado == null) {
    //                    erroresResultado = new RespuestaValidacionDTO();
    //                }
    //
    //                erroresResultado.addErrorDetalladoValidadorDTO(
    //                        FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, numeroLinea));
    //
    //            }
    //        }
    //
    //        // se comparan los números de línea en las que se encuentran las novedades ING y RET
    //        if (numLineaING != null && numLineaRET != null && numLineaING != numLineaRET) {
    //            mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_ING_RET_SEPARADOS.getReadableMessage(tipoError, numLineaING.toString(),
    //                    numLineaRET.toString());
    //
    //            // sí el resultado de errores no se encuentra instanciado, se crea
    //            if (erroresResultado == null) {
    //                erroresResultado = new RespuestaValidacionDTO();
    //            }
    //
    //            erroresResultado.addErrorDetalladoValidadorDTO(
    //                    FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, numLineaING));
    //        }
    //        // sí solo se presenta ING, se toman los días de ING
    //        else if (numLineaING != null && numLineaRET == null) {
    //            result = diasING;
    //        }
    //        // sí solo se presenta RET, se toman los días de RET
    //        else if (numLineaING == null && numLineaRET != null) {
    //            result = diasRET;
    //        }
    //
    //        logger.debug(ConstantesComunesProcesamientoPILA.FIN_LOGGER + firmaMetodo);
    //        return result;
    //    }

    /**
     * Método encargado de determinar que las novedades diferentes a ING y RET se encuentran en registros tipo 2
     * separados cuando presentan IBC diferente
     * 
     * @param registrosTipo2
     *        Listado de los registros tipo 2 del cotizante
     * @param todosIBCDiferentes
     *        Indicador de diferencia en IBC
     */
    //    private void validarOtrasNovedades(List<ResumenRegistro2DTO> registrosTipo2, Boolean todosIBCDiferentes) { desactivado por CC 0226581
    //        String firmaMetodo = "validarOtrasNovedades(List<ResumenRegistro2DTO>, Boolean)";
    //        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    //
    //        // sí se ha determinado que todos los registros tipo 2 del cotizante tienen diferente IBC
    //        if (todosIBCDiferentes) {
    //            // Variables para indicar el número de línea en la que se presenta la novedad
    //            Long numLineaSLN = null;
    //            Long numLineaIGE = null;
    //            Long numLineaLMA = null;
    //            Long numLineaVAC_LR = null;
    //            Long numLineaVSP = null;
    //            Long numLineaVST = null;
    //            Long numLineaIRL = null;
    //
    //            // se recorren los registros tipo 2 del cotizante para determinar sí una novedad se presenta más de una vez
    //            // teniendo diferentes valores de IBC 
    //
    //            for (ResumenRegistro2DTO resumenRegistro2 : registrosTipo2) {
    //                // se valida cada tipo de novedad
    //                numLineaSLN = validarFechasNovedad(numLineaSLN, resumenRegistro2.getPresentaSLN(), resumenRegistro2.getNumeroLinea(),
    //                        TipoNovedadPilaEnum.NOVEDAD_SLN.getCodigo());
    //
    //                numLineaIGE = validarFechasNovedad(numLineaIGE, resumenRegistro2.getPresentaIGE(), resumenRegistro2.getNumeroLinea(),
    //                        TipoNovedadPilaEnum.NOVEDAD_IGE.getCodigo());
    //
    //                numLineaLMA = validarFechasNovedad(numLineaLMA, resumenRegistro2.getPresentaLMA(), resumenRegistro2.getNumeroLinea(),
    //                        TipoNovedadPilaEnum.NOVEDAD_LMA.getCodigo());
    //
    //                numLineaVAC_LR = validarFechasNovedad(numLineaVAC_LR, resumenRegistro2.getPresentaVAC_LR(),
    //                        resumenRegistro2.getNumeroLinea(), TipoNovedadPilaEnum.NOVEDAD_VAC_LR.getCodigo());
    //
    //                numLineaVSP = validarFechasNovedad(numLineaVSP, resumenRegistro2.getPresentaVSP(), resumenRegistro2.getNumeroLinea(),
    //                        TipoNovedadPilaEnum.NOVEDAD_VSP.getCodigo());
    //
    //                numLineaVST = validarFechasNovedad(numLineaVST, resumenRegistro2.getPresentaVST(), resumenRegistro2.getNumeroLinea(),
    //                        TipoNovedadPilaEnum.NOVEDAD_VST.getCodigo());
    //
    //                numLineaIRL = validarFechasNovedad(numLineaIRL, resumenRegistro2.getPresentaIRL(), resumenRegistro2.getNumeroLinea(),
    //                        TipoNovedadPilaEnum.NOVEDAD_IRP.getCodigo());
    //            }
    //        }
    //
    //        logger.debug(ConstantesComunesProcesamientoPILA.FIN_LOGGER + firmaMetodo);
    //    }

    /**
     * Método encargado de comparar las fechas de una novedad en registros tipo 2 separados
     * 
     * @param numLineaNovAnt
     *        Número de línea de la novedad para el cotizante encontrado previamente
     * @param presentaNovedad
     *        Indicador de marca de presentación de la novedad
     * @param numLineaNovAct
     *        Número de línea en la que se evalua la novedad
     * @param tipoNovedad
     *        Código PILA de la novedad evaluada
     * @return <b>Long</b>
     *         Número de la línea que se lee, en caso de que la novedad se esté evaluando por primera vez
     */
    //    private Long validarFechasNovedad(Long numLineaNovAnt, Boolean presentaNovedad, Long numLineaNovAct, String tipoNovedad) {
    //        String firmaMetodo = "validarFechasNovedad(Long, Boolean, Long, String)"; desactivado por CC 0226581
    //        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    //
    //        Long result = null;
    //
    //        // sí el resultado de errores no se encuentra instanciado, se crea
    //        if (erroresResultado == null) {
    //            erroresResultado = new RespuestaValidacionDTO();
    //        }
    //
    //        String mensaje = null;
    //
    //        if (presentaNovedad && numLineaNovAnt == null) {
    //            result = numLineaNovAct;
    //        }
    //        else if (presentaNovedad && numLineaNovAnt != numLineaNovAct) {
    //            // sí el resultado de errores no se encuentra instanciado, se crea
    //            erroresResultado = new RespuestaValidacionDTO();
    //
    //            mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_NOVEDAD_REPETIDA.getReadableMessage(tipoError, tipoNovedad,
    //                    numLineaNovAnt.toString());
    //
    //            erroresResultado.addErrorDetalladoValidadorDTO(
    //                    FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, numLineaNovAnt));
    //        }
    //
    //        logger.debug(ConstantesComunesProcesamientoPILA.FIN_LOGGER + firmaMetodo);
    //        return result;
    //    }

    /**
     * Método encargado de verificar la sumatoria de días cotizados de novedades diferentes a ING y RET
     * 
     * @param registrosTipo2
     *        Listado de los registros tipo 2 del cotizante
     * @param diasCotizadosING_RET
     *        Cantidad de días cotizados de una novedad ING/RET
     * @param tipoIdCotizante
     *        Tipo de ID del cotizante
     * @param numIdCotizante
     *        Número de ID del cotizante
     */
    //    private void validarSumatoriaDiasCotizados(List<ResumenRegistro2DTO> registrosTipo2, Integer diasCotizadosING_RET,
    //            TipoIdentificacionEnum tipoIdCotizante, String numIdCotizante) {
    //        String firmaMetodo = "validarFechasNovedad(Long, Boolean, Long, String)";
    //        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    //
    //        // se determina la cantidad de días contra los cuales se compara
    //        // sí no se cuenta con días de una novedad ING/RET, se compara con 30 días
    //        Integer diasCriterio = 30;
    //        Integer contadorDias = 0;
    //        
    //        if (diasCotizadosING_RET != null) {
    //            diasCriterio = 0;
    //        }
    //
    //        // siempre que los dias de criterio sean diferentes a cero (se ha presentado ING / RET)
    //        if (diasCriterio != 0) {
    //            // se recorren los registros tipo 2 del cotizante
    //            for (ResumenRegistro2DTO resumenRegistro2 : registrosTipo2) {
    //                // se omiten los casos que incluyan novedades ING/RET
    //                if (!resumenRegistro2.getPresentaING() && !resumenRegistro2.getPresentaRET()) {
    //                    contadorDias += resumenRegistro2.getDiasCotizados();
    //                }
    //            }
    //
    //            // se compara la sumatoria con los días objetivo
    //            if (contadorDias.compareTo(diasCriterio) != 0) {
    //                // se prepara el mensaje de error
    //                String mensaje = null;
    //
    //                // sí el resultado de errores no se encuentra instanciado, se crea
    //                if (erroresResultado == null) {
    //                    erroresResultado = new RespuestaValidacionDTO();
    //                }
    //
    //                if (diasCriterio != 30) {
    //                    // presenta ING/RET
    //                    mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_DIAS_COTIZADOS_ING_RET.getReadableMessage(tipoError,
    //                            tipoIdCotizante.getDescripcion(), numIdCotizante, diasCriterio.toString());
    //                }
    //                else {
    //                    mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_DIAS_COTIZADOS.getReadableMessage(tipoError,
    //                            tipoIdCotizante.getDescripcion(), numIdCotizante, diasCriterio.toString());
    //                }
    //
    //                erroresResultado
    //                        .addErrorDetalladoValidadorDTO(FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, null));
    //
    //            }
    //        }
    //
    //        logger.debug(ConstantesComunesProcesamientoPILA.FIN_LOGGER + firmaMetodo);
    //    }

    /**
     * Método encargado de validar el valor de IBC frente a los días cotizados de novedad
     * 
     * @param registrosTipo2
     *        Listado de los registros tipo 2 del cotizante
     * @param contexto
     */
    //    private void validarValoresIBC(List<ResumenRegistro2DTO> registrosTipo2, Map<String, Object> contexto) {
    //        String firmaMetodo = "validarValoresIBC(List<ResumenRegistro2DTO>)"; desactivado por CC 0226581
    //        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    //
    //        String mensaje = null;
    //        BigDecimal salarioMinimo = (BigDecimal) contexto.get(ConstantesContexto.SALARIO_MINIMO);
    //        BigDecimal ibcCalculado = null;
    //
    //        // se recorren los registros tipo 2
    //        for (ResumenRegistro2DTO registroTipo2 : registrosTipo2) {
    //            // se calcula el IBC con base en el salario
    //            ibcCalculado = registroTipo2.getValorSalario().divide(new BigDecimal(30), 2, RoundingMode.HALF_EVEN);
    //            ibcCalculado = ibcCalculado.multiply(new BigDecimal(registroTipo2.getDiasCotizados()));
    //
    //            // se aplica redondeo al IBC (peso superior más cercano)
    //            ibcCalculado = FuncionesValidador.redondearValor(ibcCalculado, new BigDecimal(1));
    //
    //            // indicador de comparación contra base mínima de 1 SMLMV
    //            Boolean comparaMinimoSMLMV = false;
    //
    //            // sí presenta novedad ING y el IBC calculado es menor a 1 SMLMV, el IBC calculado se cambia por este último valor
    //            if (registroTipo2.getPresentaING() && ibcCalculado.compareTo(salarioMinimo) < 0) {
    //                ibcCalculado = salarioMinimo;
    //                comparaMinimoSMLMV = true;
    //            }
    //
    //            // se comparan los valores de IBC (calculado y registro)
    //            if (ibcCalculado.compareTo(registroTipo2.getValorIBC()) != 0) {
    //                // sí el resultado de errores no se encuentra instanciado, se crea
    //                if (erroresResultado == null) {
    //                    erroresResultado = new RespuestaValidacionDTO();
    //                }
    //
    //                if (!comparaMinimoSMLMV) {
    //                    mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_CALCULO_IBC.getReadableMessage(tipoError,
    //                            registroTipo2.getValorIBC().toPlainString(), registroTipo2.getDiasCotizados().toString(),
    //                            ibcCalculado.toPlainString());
    //                }
    //                else {
    //                    mensaje = MensajesValidacionEnum.ERROR_NOVEDAD_MULTIPLE_CALCULO_IBC_ING.getReadableMessage(tipoError,
    //                            registroTipo2.getValorIBC().toPlainString(), registroTipo2.getDiasCotizados().toString(),
    //                            ibcCalculado.toPlainString());
    //                }
    //
    //                erroresResultado.addErrorDetalladoValidadorDTO(
    //                        FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, registroTipo2.getNumeroLinea()));
    //            }
    //
    //        }
    //
    //        logger.debug(ConstantesComunesProcesamientoPILA.FIN_LOGGER + firmaMetodo);
    //    }
}
