package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.enumeraciones.pila.TipoNovedadPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.CasoRevisionCorrecionNovDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.dto.ResumenRegistro2DTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripcion:</b> CONTROL DE CAMBIOS 219141 - Clase que implementa la primare parte de la validación de
 * correciones en la presentación de novedades de ingreso y retiro en planillas tipo N.
 * 
 * Esta segunda parte, se encarga de llevar a cabo la revisión y comparación de los casos recopilados <br/>
 * <b>Módulo:</b> Asopagos - HU-211-391<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ValidadorCorreccionNovedadComparacion extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorCorreccionNovedadComparacion.class);

    /** Constantes mensajes error */
    private static final String FECHAS_PAGO_VENCIMIENTO = "Fecha de pago o vencimiento de la planilla";
    private static final String FECHA_PAGO_PERIODO = "Fecha de pago o Período de Aporte de la planilla";

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator#validate(
     *      co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorCorreccionNovedadComparacion.validate(FieldArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se consulta el listado de casos del contexto
        List<CasoRevisionCorrecionNovDTO> listaCasos = (List<CasoRevisionCorrecionNovDTO>) args.getContext()
                .get(ConstantesContexto.LISTA_CASOS_CORRECCION);

        // se consulta el listado de errores para agregar las inconsistencias que se encuentren
        RespuestaValidacionDTO erroresResultado = (RespuestaValidacionDTO) args.getContext()
                .get(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO);

        // se carga el período de pago junto a las fechas de vencimiento y pago desde el contexto
        String periodo = (String) args.getContext().get(ConstantesContexto.NOMBRE_PERIODO_PAGO);
        Date fechaVencimiento = (Date) args.getContext().get(ConstantesContexto.FECHA_VENCIMIENTO);
        Date fechaPago = FuncionesValidador.convertirDate((String) args.getContext().get(ConstantesContexto.NOMBRE_FECHA_PAGO));

        // se cargan los días festivos del contexto
        List<DiasFestivos> festivos = (List<DiasFestivos>) args.getContext().get(ConstantesContexto.FESTIVOS);

        if (listaCasos != null && periodo != null && fechaVencimiento != null && fechaPago != null) {

            // se recorren los casos
            for (CasoRevisionCorrecionNovDTO casoCorreccion : listaCasos) {
                /*
                 * en primer lugar, se revisan las novedades ING del caso. Sí se presenta en la corrección C y no en la A
                 * se debe comparar la fecha de pago con la fecha de vencimiento (CASO 1, Anexo 2.1.1 - Pestaña 1B celda F7)
                 * 
                 * Adicionalmente, se lleva sumatoria de valores de IBC y días cotizados para registros A y C
                 */
                Boolean presentaINGenA = false;
                Boolean presentaRETenA = false;

                Boolean presentaINGenC = false;
                Boolean presentaRETenC = false;

                Integer sumatoriaDiasA = 0;
                Integer sumatoriaDiasC = 0;

                BigDecimal sumatoriaIbcA = BigDecimal.valueOf(0);
                BigDecimal sumatoriaIbcC = BigDecimal.valueOf(0);

                // se determina la existencia de novedades en líneas A
                for (ResumenRegistro2DTO lineaA : casoCorreccion.getListaLineasA()) {
                    if (lineaA.getPresentaING()) {
                        presentaINGenA = true;
                    }

                    if (lineaA.getPresentaRET()) {
                        presentaRETenA = true;
                    }

                    sumatoriaDiasA += lineaA.getDiasCotizados();
                    sumatoriaIbcA = sumatoriaIbcA.add(lineaA.getValorIBC());
                }

                // se toman sumatorias de IBC y días cotizados en líneas C
                for (ResumenRegistro2DTO lineaC : casoCorreccion.getListaLineasC()) {
                    if (lineaC.getPresentaING()) {
                        presentaINGenC = true;
                    }

                    if (lineaC.getPresentaRET()) {
                        presentaRETenC = true;
                    }

                    sumatoriaDiasC += lineaC.getDiasCotizados();
                    sumatoriaIbcC = sumatoriaIbcC.add(lineaC.getValorIBC());
                }

                // cuando no se han presentado novedades ING en líneas A
                if (!presentaINGenA) {
                    // se recorren las líneas C para validar que no tengan novedad ING o que sea valida en caso de presentarla
                    for (ResumenRegistro2DTO lineaC : casoCorreccion.getListaLineasC()) {
                        if (lineaC.getPresentaING()) {
                            // en este caso, la fecha de pago debe ser menor o igual al último día hábil del mes de la fecha de 
                            // vencimiento (CASO 1)
                            erroresResultado = verificarFechasCaso1(festivos, fechaPago, fechaVencimiento, erroresResultado,
                                    TipoNovedadPilaEnum.NOVEDAD_ING, casoCorreccion, lineaC.getNumeroLinea(),
                                    TipoNovedadPilaEnum.NOVEDAD_ING.getCodigo());
                        }
                    }
                }

                // cuando no se han presentado novedades RET en líneas A
                if (!presentaRETenA) {
                    // se recorren las líneas C para validar que no tengan novedad RET o que sea valida en caso de presentarla
                    for (ResumenRegistro2DTO lineaC : casoCorreccion.getListaLineasC()) {
                        if (lineaC.getPresentaRET()) {
                            // cuando se prsenta un caso para revisar con novedad RET, se debe validar el tipo de cotizante
                            switch (casoCorreccion.getTipoCotizante()) {
                                case TIPO_COTIZANTE_INDEPENDIENTE:
                                case TIPO_COTIZANTE_MADRE_SUSTITUTA:
                                case TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO:
                                case TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN:
                                case TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD:
                                case TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD:
                                case TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP:
                                case TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD:
                                case TIPO_COTIZANTE_PAGO_POR_TERCERO:
                                case TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE:
                                case TIPO_COTIZANTE_AFILIADO_PARTICIPE:
                                case TIPO_COTIZANTE_PREPENSIONADO_AVS:
                                case TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES:
                                case TIPO_COTIZANTE_EDIL_JAL:
                                case TIPO_COTIZANTE_PROGRAMA_REINCORPORACION:
                                    /*
                                     * un caso para evaluar de novedad de retiro para estos tipos de cotizante, la fecha de pago debe ser
                                     * menor
                                     * o igual al 5to día hábil del mes siguientes del mes del período en le que se presenta la novedad
                                     * (CASO 2, Anexo 2.1.1 - Pestaña 1B celda F7)
                                     */
                                    erroresResultado = verificarFechasCaso2(festivos, fechaPago, periodo, erroresResultado, casoCorreccion,
                                            lineaC.getNumeroLinea(), TipoNovedadPilaEnum.NOVEDAD_RET.getCodigo());
                                    break;
                                default:
                                    // los demás tipos de cotizante se evaluan con el CASO 1
                                    erroresResultado = verificarFechasCaso1(festivos, fechaPago, fechaVencimiento, erroresResultado,
                                            TipoNovedadPilaEnum.NOVEDAD_RET, casoCorreccion, lineaC.getNumeroLinea(),
                                            TipoNovedadPilaEnum.NOVEDAD_RET.getCodigo());
                                    break;
                            }
                        }
                    }
                }

                casoCorreccion.setSumatoriaDiasA(sumatoriaDiasA);
                casoCorreccion.setSumatoriaDiasC(sumatoriaDiasC);
                casoCorreccion.setSumatoriaIbcA(sumatoriaIbcA);
                casoCorreccion.setSumatoriaIbcC(sumatoriaIbcC);

                // se validan las sumatorias de IBC y días cotizados
                erroresResultado = verificarSumatorias(casoCorreccion, erroresResultado, presentaINGenC, presentaRETenC);
            }

            // se actualiza el listado de errores en el contexto
            args.getContext().replace(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO, erroresResultado);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para evaluar las fechas relacionadas con el CASO 1, Anexo 2.1.1 - Pestaña 1B celda F7
     * 
     * En este caso, la fecha de pago debe ser menor o igual al último día hábil del mes al que corresponde
     * la fecha de vencimiento de la planilla
     * @param festivos
     *        Listado de los dísa festivos parametrizados, empleado para calcular fechas hábiles
     * @param fechaPago
     *        Fecha de pago de la planilla
     * @param fechaVencimiento
     *        Fecha de vencimiento de la planilla
     * @param erroresResultado
     *        Resultado de validaror que contiene el listado con las inconsistencias que se presenten
     * @param novedad
     *        Tipo de novedad presentada (ING o RET)
     * @param casoCorreccion
     *        Caso de validación evaluado
     * @param numeroLinea
     *        Línea del archivo en la que se presenta el caso
     * @param tipoNovedad
     * @return <b>RespuestaValidacionDTO</b>
     *         Resultado de validaror que contiene el listado con las inconsistencias que se presenten actualizado
     * @throws FileProcessingException
     */
    private RespuestaValidacionDTO verificarFechasCaso1(List<DiasFestivos> festivos, Date fechaPago, Date fechaVencimiento,
            RespuestaValidacionDTO erroresResultado, TipoNovedadPilaEnum novedad, CasoRevisionCorrecionNovDTO casoCorreccion,
            Long numeroLinea, String tipoNovedad) throws FileProcessingException {
        String firmaMetodo = "ValidadorCorreccionNovedadComparacion.verificarFechasCaso1(List<DiasFestivos>, Date, Date, "
                + "RespuestaValidacionDTO, TipoNovedadPilaEnum, CasoRevisionCorrecionNovDTO)";

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // variables para los mensajes de inconsistencia
        String tipoError = TipoErrorValidacionEnum.TIPO_1.toString();
        String idCampo = EtiquetaArchivoIEnum.I231.toString();
        String valorCampo = ConstantesComunesProcesamientoPILA.CORRECCIONES_C;
        String nombreCampo = ConstantesComunesProcesamientoPILA.NOMBRE_CAMPO_MENSAJE_ERROR_CORRECCION_NOVEDAD;

        String mensaje = null;

        if (fechaPago != null && fechaVencimiento != null) {
            Date ultimoHabilVencimiento = FuncionesValidador.obtenerUltimoDiaHabilMes(festivos, fechaVencimiento);

            if (CalendarUtil.compararFechas(fechaPago, ultimoHabilVencimiento) > 0) {

                mensaje = MensajesValidacionEnum.ERROR_CORRECCION_NOVEDAD_CASO1.getReadableMessage(idCampo, FuncionesValidador.formatoFecha(fechaPago), tipoError,
                        casoCorreccion.getTipoIdCotizante().getDescripcion(), casoCorreccion.getNumIdCotizante(), tipoNovedad,
                        FuncionesValidador.formatoFecha(fechaPago), FuncionesValidador.formatoFecha(ultimoHabilVencimiento));

                // sí le listado de errores aún no existe, se le crea
                if (erroresResultado == null) {
                    erroresResultado = new RespuestaValidacionDTO();
                }

                // se añade la inconsistencia
                erroresResultado.addErrorDetalladoValidadorDTO(
                        FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, numeroLinea));
            }
        }
        else {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, valorCampo, tipoError, nombreCampo,
                    FECHAS_PAGO_VENCIMIENTO);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return erroresResultado;
    }

    /**
     * Método para evaluar las fechas relacionadas con el CASO 2, Anexo 2.1.1 - Pestaña 1B celda F7
     * 
     * En este caso, la fecha de pago debe ser menor o igual al quinto día hábil del mes siguiente al
     * período en el que se presenta la novedad
     * @param festivos
     *        Listado de los dísa festivos parametrizados, empleado para calcular fechas hábiles
     * @param fechaPago
     *        Fecha de pago de la planilla
     * @param periodo
     *        Período en el que se presenta la novedad
     * @param erroresResultado
     *        Resultado de validaror que contiene el listado con las inconsistencias que se presenten
     * @param casoCorreccion
     *        Caso de validación evaluado
     * @param numeroLinea
     *        Número de la línea del caso
     * @param tipoNovedad
     *        Tipo de novedad presentada
     * @return <b>RespuestaValidacionDTO</b>
     *         Resultado de validaror que contiene el listado con las inconsistencias que se presenten actualizado
     * @throws FileProcessingException
     *         Error de parámetros incompletos
     */
    private RespuestaValidacionDTO verificarFechasCaso2(List<DiasFestivos> festivos, Date fechaPago, String periodo,
            RespuestaValidacionDTO erroresResultado, CasoRevisionCorrecionNovDTO casoCorreccion, Long numeroLinea, String tipoNovedad)
            throws FileProcessingException {
        String firmaMetodo = "ValidadorCorreccionNovedadComparacion.verificarFechasCaso2(List<DiasFestivos>, Date, String, RespuestaValidacionDTO, CasoRevisionCorrecionNovDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // variables para los mensajes de inconsistencia
        String tipoError = TipoErrorValidacionEnum.TIPO_1.toString();
        String idCampo = EtiquetaArchivoIEnum.I232.toString();
        String valorCampo = ConstantesComunesProcesamientoPILA.CORRECCIONES_C;
        String nombreCampo = ConstantesComunesProcesamientoPILA.NOMBRE_CAMPO_MENSAJE_ERROR_CORRECCION_NOVEDAD;

        String mensaje = null;

        if (fechaPago != null && periodo != null) {
            // a partir del período de aporte, se toman el año y mes
            int anioPeriodo = Integer.parseInt(periodo.split("-")[0]);
            int mesPeriodo = Integer.parseInt(periodo.split("-")[1]);

            // se crea un Calendar con la fecha base a partir del período del aporte
            Calendar fechaBaseCal = Calendar.getInstance();
            fechaBaseCal.set(Calendar.YEAR, anioPeriodo);
            fechaBaseCal.set(Calendar.MONTH, mesPeriodo);
            fechaBaseCal.set(Calendar.DAY_OF_MONTH, 1);

            int diasMod = ConstantesComunesProcesamientoPILA.DIAS_HABILES_CASO2_CORRECCION_NOVEDADES;

            if (fechaBaseCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fechaBaseCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                    && !FuncionesValidador.isFestivo(fechaBaseCal, festivos)) {
                diasMod -= 1;
            }

            Date fechaBaseDate = FuncionesValidador.modificarFecha(fechaBaseCal, diasMod, festivos).getTime();

            if (CalendarUtil.compararFechas(fechaPago, fechaBaseDate) > 0) {

                mensaje = MensajesValidacionEnum.ERROR_CORRECCION_NOVEDAD_CASO2.getReadableMessage(idCampo, valorCampo, tipoError,
                        casoCorreccion.getTipoCotizante().getDescripcion(), casoCorreccion.getTipoIdCotizante().getDescripcion(),
                        casoCorreccion.getNumIdCotizante(), tipoNovedad, FuncionesValidador.formatoFecha(fechaPago),
                        ConstantesComunesProcesamientoPILA.DIAS_HABILES_CASO2_CORRECCION_NOVEDADES.toString(),
                        FuncionesValidador.formatoFecha(fechaBaseDate));

                // sí le listado de errores aún no existe, se le crea
                if (erroresResultado == null) {
                    erroresResultado = new RespuestaValidacionDTO();
                }

                // se añade la inconsistencia
                erroresResultado.addErrorDetalladoValidadorDTO(
                        FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, numeroLinea));
            }
        }
        else {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, FECHA_PAGO_PERIODO);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return erroresResultado;
    }

    /**
     * Función para realizar la validación a las sumatorias de dias cotizados y de IBC
     * @param casoCorreccion
     *        Caso de validación evaluado
     * @param erroresResultado
     *        Resultado de validaror que contiene el listado con las inconsistencias que se presenten
     * @param presentaRETenC
     *        Indicador de presencia de novedad de retiro en registro C
     * @param presentaINGenC
     *        Indicador de presencia de novedad de ingreso en registro C
     * @return <b>RespuestaValidacionDTO</b>
     *         Resultado de validaror que contiene el listado con las inconsistencias que se presenten actualizado
     */
    private RespuestaValidacionDTO verificarSumatorias(CasoRevisionCorrecionNovDTO casoCorreccion, RespuestaValidacionDTO erroresResultado,
            Boolean presentaINGenC, Boolean presentaRETenC) {
        String firmaMetodo = "ValidadorCorreccionNovedadComparacion.verificarSumatorias(CasoRevisionCorrecionNovDTO, "
                + "RespuestaValidacionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        RespuestaValidacionDTO erroresResultadoTemp = erroresResultado;

        // variables para los mensajes de inconsistencia
        String tipoError = TipoErrorValidacionEnum.TIPO_2.toString();
        String idCampo = EtiquetaArchivoIEnum.I229.toString();

        String mensaje = null;

        Integer sumatoriaDiasA = casoCorreccion.getSumatoriaDiasA();
        Integer sumatoriaDiasC = casoCorreccion.getSumatoriaDiasC();
        // se comparan los días cotizados
        if (sumatoriaDiasC.compareTo(sumatoriaDiasA) < 0 && !presentaINGenC && !presentaRETenC) {
            mensaje = MensajesValidacionEnum.ERROR_DIFERENCIA_SUMATORIA_DIAS_CORRECCION.getReadableMessage(idCampo,
                    sumatoriaDiasC.toString(), tipoError, casoCorreccion.getTipoIdCotizante().getDescripcion(),
                    casoCorreccion.getNumIdCotizante(), sumatoriaDiasA.toString());

            // sí le listado de errores aún no existe, se le crea
            if (erroresResultadoTemp == null) {
                erroresResultadoTemp = new RespuestaValidacionDTO();
            }

            // se añade la inconsistencia
            erroresResultadoTemp.addErrorDetalladoValidadorDTO(
                    FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, null));
        }

        BigDecimal sumatoriaIbcA = casoCorreccion.getSumatoriaIbcA();
        BigDecimal sumatoriaIbcC = casoCorreccion.getSumatoriaIbcC();
        // se comparan los IBC cotizados
        if (sumatoriaIbcC.compareTo(sumatoriaIbcA) < 0) {
            mensaje = MensajesValidacionEnum.ERROR_DIFERENCIA_SUMATORIA_IBC_CORRECCION.getReadableMessage(idCampo,
                    sumatoriaIbcC.toPlainString(), tipoError, casoCorreccion.getTipoIdCotizante().getDescripcion(),
                    casoCorreccion.getNumIdCotizante(), sumatoriaIbcA.toPlainString());

            // sí le listado de errores aún no existe, se le crea
            if (erroresResultadoTemp == null) {
                erroresResultadoTemp = new RespuestaValidacionDTO();
            }

            // se añade la inconsistencia
            erroresResultadoTemp.addErrorDetalladoValidadorDTO(
                    FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, null));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return erroresResultadoTemp;
    }
}
