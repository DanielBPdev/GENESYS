package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.PorcentajeTarifaEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo tarifa<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorTarifa extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTarifa.class);
    private String mensaje = null;
    private String idCampo = null;
    private String tipoError = null;
    private String nombreCampo = null;

    private DecimalFormat df = new DecimalFormat();

    /**
     * Metodo se encarga de validar el campo tarifa
     *
     * @param LineArgumentDTO objeto con la informacion a validar
     * @exception FileProcessingException lanzada cuando hay un error en la
     * validacion
     */
    @Override
    public void validate(LineArgumentDTO arg0) throws FileProcessingException {
        String firmaMetodo = "ValidadorTarifa.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        df.setMaximumFractionDigits(3);
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(false);

        Boolean tieneAusentismo;
        Boolean esIndependiente;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = arg0.getLineValues();

        nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // se lee la tarifa
        Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TARIFA));
        BigDecimal tarifa = null;
        if (valor != null && !valor.toString().isEmpty()) {
            tarifa = (BigDecimal) valor;
        } else {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, df.format(tarifa),
                    MessagesConstants.TARIFA);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        Map<String, Object> contexto = arg0.getContext();

        // se identifica el tipo de archivo
        String tipoArchivo = contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO)).toString();
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        if (tipoArchivoEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, df.format(tarifa),
                    MessagesConstants.TIPO_ARCHIVO);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // Se obtiene el tipo de cotizante
        valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE));
        Integer tipoCotizante = null;
        if (valor != null && !valor.toString().isEmpty()) {
            tipoCotizante = (Integer) valor;
        }
        TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

        String novedadSLN = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NOVEDAD_SLN));
        String novedadIGE = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NOVEDAD_IGE));
        String novedadIRL = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NOVEDAD_IRL));
        String novedadLMA = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NOVEDAD_LMA));
        String novedadVAC = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NOVEDAD_VAC));

        String claseAportante = null;

        claseAportante = (String) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_CLASE_APORTANTE));

        ClaseAportanteEnum claseAportanteEnum = ClaseAportanteEnum.obtenerClaseAportanteEnum(claseAportante);

        claseAportanteEnum = GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo()) ? ClaseAportanteEnum.CLASE_I
                : claseAportanteEnum;

        if (claseAportanteEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, df.format(tarifa),
                    MessagesConstants.CLASE_APORTANTE);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        //Si existe novedad LMA la tarifa puede ser cualquiera.
        if (null == novedadLMA) {
            // se valida la presencia de novedades de ausentismo o sí el cotizante es independiente
            Map<String, Boolean> ausentismoEInd = validarAusentismoEIndependiente(tarifa, novedadSLN, novedadIGE, novedadIRL, novedadLMA, novedadVAC,
                    tipoCotizanteEnum);

            tieneAusentismo = ausentismoEInd.get("tieneAusentismo");
            esIndependiente = ausentismoEInd.get("esIndependiente");

            Date fecha = (Date) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_FECHA_MATRICULA));
            String periodoPago = (String) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_PERIODO_PAGO));

            // sí no se ha presentado ausentismo ni es un independiente, se validan las condiciones por beneficio de ley
            if (!tieneAusentismo && !esIndependiente
                    && (ClaseAportanteEnum.CLASE_C.equals(claseAportanteEnum) || ClaseAportanteEnum.CLASE_D.equals(claseAportanteEnum))) {
                if (fecha != null) {
                    validarCasoBeneficioConFechaMatricula(fecha, claseAportanteEnum, periodoPago, tarifa);
                } else {
                    validarCasoBeneficioSinFechaMatricula(claseAportanteEnum, tarifa, tipoCotizanteEnum);
                }
            }

            // caso pensionados y caso general
            if (!tieneAusentismo && !esIndependiente
                    && (!ClaseAportanteEnum.CLASE_C.equals(claseAportanteEnum) && !ClaseAportanteEnum.CLASE_D.equals(claseAportanteEnum))) {

                valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SALARIO_MESADA));
                BigDecimal mesadaPensional = null;
                if (valor != null && !valor.toString().isEmpty()) {
                    mesadaPensional = (BigDecimal) valor;
                }

                BigDecimal salarioMinimo = (BigDecimal) contexto.get(ConstantesContexto.SALARIO_MINIMO);

                if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())) {
                    validarCasoPensionados(tarifa, tipoArchivoEnum, salarioMinimo, mesadaPensional);
                } else {
                    validarCasoGeneral(tarifa, claseAportanteEnum, tipoCotizanteEnum);
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Función encargada de validar la tarifa de pensionados
     *
     * @param tarifa Tarifa registrada en la planilla PILA
     * @param tipoArchivoEnum Tipos de archivo de planilla PILA
     * @param salarioMinimo Valor de SMLMV
     * @param mesadaPensional Valor de la mesada pensional diligenciada en la
     * planilla PILA
     * @throws FileProcessingException
     */
    private void validarCasoPensionados(BigDecimal tarifa, TipoArchivoPilaEnum tipoArchivoEnum, BigDecimal salarioMinimo,
            BigDecimal mesadaPensional) throws FileProcessingException {

        String firmaMetodo = "ValidadorTarifa.validarCasoPensionados(BigDecimal, TipoArchivoPilaEnum, BigDecimal, BigDecimal)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (mesadaPensional == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, df.format(tarifa),
                    MessagesConstants.MESADA_PENSIONAL);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0
                && PorcentajeTarifaEnum.TARIFA_PUNTO_SEIS.getValor().compareTo(tarifa) != 0
                && PorcentajeTarifaEnum.TARIFA_DOS.getValor().compareTo(tarifa) != 0) {

            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ES_VALIDO.getReadableMessage(idCampo, df.format(tarifa), tipoError, nombreCampo,
                    df.format(tarifa));

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        } else if (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) == 0
                && !TipoArchivoPilaEnum.ARCHIVO_OI_IPR.equals(tipoArchivoEnum)
                && (salarioMinimo != null && mesadaPensional.compareTo(salarioMinimo.multiply(BigDecimal.valueOf(1.5))) > 0)) {
            // si se presenta una tarifa 0% en un archivo diferente a IPR, el ingreso del pensionado debe ser mayor a 1.5 SMLMV
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_ARCHIVO_SUPERA_MESADA_1_5_SMLMV.getReadableMessage(idCampo,
                    df.format(tarifa), tipoError, nombreCampo, df.format(tarifa), tipoArchivoEnum.getCodigo(),
                    mesadaPensional.toPlainString());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Función encargada para validar los casos que no dependen de la clase de
     * aportante
     *
     * @param tarifa Tarifa registrada en la planilla PILA
     * @param claseAportanteEnum Clase de aportante
     * @param tipoCotizanteEnum Tipo de cotizante
     * @throws FileProcessingException
     */
    private void validarCasoGeneral(BigDecimal tarifa, ClaseAportanteEnum claseAportanteEnum, TipoCotizanteEnum tipoCotizanteEnum)
            throws FileProcessingException {
        String firmaMetodo = "ValidadorTarifa.validarCasoGeneral(String, BigDecimal, ClaseAportanteEnum, TipoCotizanteEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tipoCotizanteEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, df.format(tarifa),
                    MessagesConstants.TIPO_COTIZANTE);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        /*
         * -Archivos con valor diferente a "D" o "C" en el campo 35 "Clase de aportante" del Registro tipo 1 y
         * -en el Campo 5 "Tipo de cotizante" del Registro tipo 2, el valor "20" , "32"
         */
        switch (tipoCotizanteEnum) {
            case TIPO_COTIZANTE_ESTUDIANTES_REG_ESP:
            case TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL:
                if (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0
                        && PorcentajeTarifaEnum.TARIFA_CUATRO.getValor().compareTo(tarifa) != 0) {

                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE.getReadableMessage(idCampo,
                            df.format(tarifa), tipoError, nombreCampo, claseAportanteEnum.getDescripcion(),
                            tipoCotizanteEnum.getDescripcion());

                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
                break;
            case TIPO_COTIZANTE_DEPENDIENTE:
            case TIPO_COTIZANTE_SERVICIO_DOMESTICO:
            case TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC:
            case TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR:
            case TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI:
            case TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI:
            case TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL:
            case TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE:
                /*
                 * Archivos con valor diferente a "D" o "C" en el campo 35 "Clase de aportante" del Registro tipo 1
                 * y en el Campo 5 "Tipo de cotizante" del Registro tipo 2, el valor "01" , "02", "18", "22", "30",
                 * "47", "51", "55" - sólo pueden tener tarifa 4%
                 */
                if (PorcentajeTarifaEnum.TARIFA_CUATRO.getValor().compareTo(tarifa) != 0) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE.getReadableMessage(idCampo,
                            df.format(tarifa), tipoError, nombreCampo, claseAportanteEnum.getDescripcion(),
                            tipoCotizanteEnum.getDescripcion());

                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
                break;
            default:
                break;
        }

        /*
         * -Archivos con valor "A" ó "B" en el campo 35 "Clase de aportante" del Registro tipo 1 y
         * -El valor "31" en el Campo 5 "Tipo de cotizante" del Registro tipo 2
         */
        if (!ClaseAportanteEnum.CLASE_I.equals(claseAportanteEnum)
                && TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO.equals(tipoCotizanteEnum)
                && (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0
                && PorcentajeTarifaEnum.TARIFA_CUATRO.getValor().compareTo(tarifa) != 0)) {

            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE.getReadableMessage(idCampo, df.format(tarifa),
                    tipoError, nombreCampo, claseAportanteEnum.getDescripcion(), tipoCotizanteEnum.getDescripcion());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
    }

    /**
     * Función para evaluar los casos de clases de aportante con beneficio de
     * ley, sin contar con fecha de matrícula
     *
     * @param claseAportanteEnum Clase de aportante
     * @param tarifa Tarifa registrada en la planilla PILA
     * @param tipoCotizanteEnum Tipo de cotizante
     * @throws FileProcessingException
     */
    private void validarCasoBeneficioSinFechaMatricula(ClaseAportanteEnum claseAportanteEnum, BigDecimal tarifa,
            TipoCotizanteEnum tipoCotizanteEnum) throws FileProcessingException {
        String firmaMetodo = "ValidadorTarifa.validarCasoBeneficioSinFechaMatricula(ClaseAportanteEnum, BigDecimal, TipoCotizanteEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (PorcentajeTarifaEnum.TARIFA_PUNTO_SEIS.getValor().compareTo(tarifa) == 0) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE.getReadableMessage(idCampo, df.format(tarifa),
                    tipoError, nombreCampo, claseAportanteEnum.getDescripcion(),
                    tipoCotizanteEnum != null ? tipoCotizanteEnum.getDescripcion() : "Indeterminado");

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Función para evaluar los casos de clases de aportante con beneficio de
     * ley contando con fecha de matrícula
     *
     * @param fecha Fecha de matrícula del aportante
     * @param claseAportanteEnum Clase de aportante
     * @param periodoPago Período de pago de la planilla
     * @param tarifa Tarifa registrada en la planilla PILA
     * @param tipoCotizanteEnum Tipo de cotizante
     * @throws FileProcessingException
     */
    private void validarCasoBeneficioConFechaMatricula(Date fecha, ClaseAportanteEnum claseAportanteEnum, String periodoPago,
            BigDecimal tarifa) throws FileProcessingException {
        String firmaMetodo = "ValidadorTarifa.validarCasoBeneficioConFechaMatricula(Date, ClaseAportanteEnum, String, BigDecimal)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Calendar fechaMatricula = new GregorianCalendar();
        fechaMatricula.setTimeInMillis(fecha.getTime());

        Integer anioBeneficio = 10;
        Boolean error = false;

        // para los aportantes clase C o D (leyes 590 del 2000 y 1429 del 2010) se calcula el año de beneficio
        if (ClaseAportanteEnum.CLASE_C.equals(claseAportanteEnum)) {
            Integer anioMatricula = fechaMatricula.get(Calendar.YEAR);
            Integer mesMatricula = fechaMatricula.get(Calendar.MONTH) + 1;

            Integer anioPeriodo = Integer.parseInt(periodoPago.split("-")[0]);
            Integer mesPeriodo = Integer.parseInt(periodoPago.split("-")[1]);

            // se comparan los años, sí son iguales entonces año beneficio = 1
            if (anioMatricula.compareTo(anioPeriodo) == 0) {
                anioBeneficio = 1;
            } // sí el año de período es menor al año de la matrícula lanzar error
            else if (anioPeriodo.compareTo(anioMatricula) < 0) {
                mensaje = MensajesValidacionEnum.ERROR_PERIODO_ANIO_MENOR_MATRICULA.getReadableMessage(idCampo, df.format(tarifa),
                        tipoError, nombreCampo, anioPeriodo.toString(), anioMatricula.toString());

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            } // sí el año de período es mayor al año de la matrícula
            else {
                // sí el mes de periodo es menor o igual al mes de la matrícula el año de beneficio es año período - año matricula
                if (mesPeriodo.compareTo(mesMatricula) < 0) {
                    anioBeneficio = anioPeriodo - anioMatricula;
                } // sí el mes de periodo es mayor al mes de la matrícula el año de beneficio es año período - año matricula + 1
                else if (mesPeriodo.compareTo(mesMatricula) >= 0) {
                    anioBeneficio = anioPeriodo - anioMatricula + 1;
                }
            }

            // una vez se cuenta con el año de beneficio, se establece que tarifas son válidas
            // 1% para el año 1
            if ((anioBeneficio.equals(1) && PorcentajeTarifaEnum.TARIFA_UNO.getValor().compareTo(tarifa) != 0)
                    // 2% para el año 2
                    || (anioBeneficio.equals(2) && PorcentajeTarifaEnum.TARIFA_DOS.getValor().compareTo(tarifa) != 0)
                    // 3% para el año 3
                    || (anioBeneficio.equals(3) && PorcentajeTarifaEnum.TARIFA_TRES.getValor().compareTo(tarifa) != 0)
                    // 4% para el año 4 en adelante
                    || (anioBeneficio >= 4 && PorcentajeTarifaEnum.TARIFA_CUATRO.getValor().compareTo(tarifa) != 0)) {
                error = true;
            }
        } else if (ClaseAportanteEnum.CLASE_D.equals(claseAportanteEnum)) {
            Integer anioMatricula = fechaMatricula.get(Calendar.YEAR);
            Integer anioPeriodo = Integer.parseInt(periodoPago.split("-")[0]);

            anioBeneficio = anioPeriodo - anioMatricula + 1;

            // una vez se cuenta con el año de beneficio, se establece que tarifas son válidas
            // 0% para los años 1 y 2
            if ((anioBeneficio <= 2 && PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0)
                    // 0% y 1% para el año 3  
                    || (anioBeneficio.equals(3) && (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0
                    && PorcentajeTarifaEnum.TARIFA_UNO.getValor().compareTo(tarifa) != 0))
                    // 0% y 2% para el año 4
                    || (anioBeneficio.equals(4) && (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0
                    && PorcentajeTarifaEnum.TARIFA_DOS.getValor().compareTo(tarifa) != 0))
                    // 0% y 3% para el año 5
                    || (anioBeneficio.equals(5) && (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0
                    && PorcentajeTarifaEnum.TARIFA_TRES.getValor().compareTo(tarifa) != 0))
                    // 0% y 4% para los años 6, 7 y 8
                    || ((anioBeneficio.equals(6) || anioBeneficio.equals(7) || anioBeneficio.equals(8))
                    && (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0
                    && PorcentajeTarifaEnum.TARIFA_CUATRO.getValor().compareTo(tarifa) != 0))
                    // 2% y 4% para el año 9
                    || (anioBeneficio.equals(9) && (PorcentajeTarifaEnum.TARIFA_DOS.getValor().compareTo(tarifa) != 0
                    && PorcentajeTarifaEnum.TARIFA_CUATRO.getValor().compareTo(tarifa) != 0))
                    // 3% y 4% para el año 10
                    || (anioBeneficio.equals(10) && (PorcentajeTarifaEnum.TARIFA_TRES.getValor().compareTo(tarifa) != 0
                    && PorcentajeTarifaEnum.TARIFA_CUATRO.getValor().compareTo(tarifa) != 0))) {
                error = true;
            }
        }

        if (error) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE_EN_SU_ANIO_BENEFICIO.getReadableMessage(idCampo,
                    df.format(tarifa), tipoError, nombreCampo, claseAportanteEnum.getDescripcion(), anioBeneficio.toString());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Validaciones respecto al ausentismo y cotizante independiente
     *
     * @param tarifa
     * @param novedadSLN
     * @param novedadIGE
     * @param novedadIRL
     * @param novedadLMA
     * @param tipoCotizanteEnum
     * @param claseAportante
     * @throws FileProcessingException
     */
    private Map<String, Boolean> validarAusentismoEIndependiente(BigDecimal tarifa, String novedadSLN, String novedadIGE, String novedadIRL,
            String novedadLMA, String novedadVAC, TipoCotizanteEnum tipoCotizanteEnum) throws FileProcessingException {
        String firmaMetodo = "ValidadorTarifa.validarAusentismoEIndependiente(BigDecimal, String, String, String, String)";

        Boolean tieneAusentismo = Boolean.TRUE;
        Boolean esIndependiente = Boolean.FALSE;

        // cualquier clase de aportante y tarifa 0% con novedad de suspención
        if (novedadSLN != null && (novedadSLN.equals("X"))
                && (PorcentajeTarifaEnum.TARIFA_CERO.getValor().compareTo(tarifa) != 0)) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE_NOV_SLN.getReadableMessage(idCampo,
                    df.format(tarifa), tipoError, nombreCampo);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // las novedades de ausentismo pueden presentar cualquier tarifa 
        if (novedadSLN == null && novedadIGE == null && (novedadIRL == null || Integer.parseInt(novedadIRL) == 0) && novedadLMA == null) {
            tieneAusentismo = Boolean.FALSE;
        }

        // los cotizantes de tipo independiente pueden presentar cualquier tarifa
        if (tipoCotizanteEnum != null && TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoCotizanteEnum.getTipoAfiliado())) {
            esIndependiente = Boolean.TRUE;
        }

        Map<String, Boolean> resultado = new HashMap<>();
        resultado.put("tieneAusentismo", tieneAusentismo);
        resultado.put("esIndependiente", esIndependiente);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

}
