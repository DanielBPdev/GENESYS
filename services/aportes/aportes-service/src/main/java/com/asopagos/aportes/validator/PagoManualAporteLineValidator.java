package com.asopagos.aportes.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.aportes.constants.ConstanteCampoArchivo;
import com.asopagos.aportes.util.FuncionesUtilitarias;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que se encarga de validar la estructura de un
 * archivo txt para pago manual de aportes<br/>
 * <b>Módulo:</b> Asopagos - 2.1.2 -HU - 482<br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */
public class PagoManualAporteLineValidator extends LineValidator {

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(PagoManualAporteLineValidator.class);

    /**
     * Tipo de identificacion
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Tipo de cotizante
     */
    private TipoCotizanteEnum tipoCotizante;
    /**
     * Tipo de registro
     */
    private Integer tipoRegistro;
    /**
     * Subtipo de cotizante
     */
    private Integer subtipoCotizante;
    /**
     * Departamento
     */
    private Departamento departamento;
    /**
     * Número de secuencia
     */
    private Integer secuencia;
    /**
     * Lista de Hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase PagoManualAporteLineValidator
     */
    public PagoManualAporteLineValidator() {
        tipoIdentificacion = null;
        tipoCotizante = null;
        tipoRegistro = null;
        subtipoCotizante = null;
        departamento = null;
        secuencia = null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        logger.debug("Inicia PagoManualAporteLineValidator.validate(LineArgumentDTO)");
        try {
            lstHallazgos = new ArrayList<>();
            Map<String, Object> line = arguments.getLineValues();
            // se valida la secuencia
            try {
                secuencia = ((Integer) line.get(ConstanteCampoArchivo.SECUENCIA));
                if (!(secuencia >= ConstanteCampoArchivo.INICIO_SECUENCIA && secuencia <= ConstanteCampoArchivo.FIN_SECUENCIA)) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.SECUENCIA_MSG,
                            "Debe ser una valor entre " + ConstanteCampoArchivo.INICIO_SECUENCIA + " y "
                                    + ConstanteCampoArchivo.FIN_SECUENCIA));
                }
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.SECUENCIA_MSG,
                        ConstanteCampoArchivo.SECUENCIA_MSG + ConstanteCampoArchivo.INVALIDO_MSG));
            }
            // se valida el tipo de registro
            try {
                tipoRegistro = ((Integer) line.get(ConstanteCampoArchivo.TIPO_REGISTRO));
                if (tipoRegistro != ConstanteCampoArchivo.TIPO_REGISTRO_ESTABLECIDO) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.TIPO_REGISTRO_MSG,
                            ConstanteCampoArchivo.TIPO_REGISTRO_MSG + ".El valor debe de ser 2"));
                }
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.TIPO_REGISTRO_MSG,
                        ConstanteCampoArchivo.TIPO_REGISTRO_MSG + ConstanteCampoArchivo.INVALIDO_MSG));
            }
            // se valida el tipo de identificación
            try {
                tipoIdentificacion = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(
                        ((String) line.get(ConstanteCampoArchivo.TIPO_IDENTIFICACION_COTIZANTE)).toUpperCase());
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(),
                        ConstanteCampoArchivo.TIPO_IDENTIFICACION_COTIZANTE_MSG,
                        ConstanteCampoArchivo.TIPO_IDENTIFICACION_COTIZANTE_MSG + ConstanteCampoArchivo.INVALIDO_MSG));
                tipoIdentificacion = null;
            }
            // Se valida el numero de documento
            verificarNumeroDocumento(tipoIdentificacion, arguments);
            // Se valida el tipo de cotizante
            try {
                tipoCotizante = TipoCotizanteEnum.obtenerTipoCotizante(((Integer) line.get(ConstanteCampoArchivo.TIPO_COTIZANTE)));
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.TIPO_COTIZANTE_MSG,
                        ConstanteCampoArchivo.TIPO_COTIZANTE_MSG + ConstanteCampoArchivo.INVALIDO_MSG));
                tipoCotizante = null;
            }
            // Se valida el subtipo de cotizante
            try {
                subtipoCotizante = ((Integer) line.get(ConstanteCampoArchivo.SUBTIPO_COTIZANTE));
                SubTipoCotizanteEnum subTipoCotizanteEnum = SubTipoCotizanteEnum.obtenerSubTipoCotizante(subtipoCotizante);
                if (subTipoCotizanteEnum == null) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(),
                            ConstanteCampoArchivo.SUBTIPO_COTIZANTE_MSG, "Debe ser un subtipo cotizante valido"));
                }
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.SUBTIPO_COTIZANTE_MSG,
                        ConstanteCampoArchivo.SUBTIPO_COTIZANTE_MSG + ConstanteCampoArchivo.INVALIDO_MSG));
                subtipoCotizante = null;
            }
            // Se valida Extranjero no obligado a cotizar a pensiones
            validarExtranjeroNoObligadoACotizar(arguments, tipoIdentificacion);
            // Se valida Colombiano en el exterior
            validarColombianoExterior(arguments, tipoIdentificacion);
            // Se valida el Departamento
            departamento = validarDepartamento(arguments, line);
            // Se valida el Municipio
            validarMunicipio(arguments, line, departamento);
            // Se valida el primer apellido
            validarRegex(arguments, ConstanteCampoArchivo.PRIMER_APELLIDO, ExpresionesRegularesConstants.PRIMER_APELLIDO,
                    "Solo se permiten 19 caracteres del alfabeto", ConstanteCampoArchivo.PRIMER_APELLIDO_MSG);
            // Se valida el segundo apellido
            validarRegex(arguments, ConstanteCampoArchivo.SEGUNDO_APELLIDO, ExpresionesRegularesConstants.SEGUNDO_APELLIDO,
                    "Solo se permiten 30 caracteres del alfabeto", ConstanteCampoArchivo.SEGUNDO_APELLIDO_MSG);
            // Se valida el primer nombre
            validarRegex(arguments, ConstanteCampoArchivo.PRIMER_NOMBRE, ExpresionesRegularesConstants.PRIMER_NOMBRE,
                    "Solo se permiten 20 caracteres del alfabeto", ConstanteCampoArchivo.PRIMER_NOMBRE_MSG);
            // Se valida el segundo nombre
            validarRegex(arguments, ConstanteCampoArchivo.SEGUNDO_NOMBRE, ExpresionesRegularesConstants.SEGUNDO_NOMBRE,
                    "Solo se permiten 30 caracteres del alfabeto", ConstanteCampoArchivo.SEGUNDO_NOMBRE_MSG);
            // Validacion Fecha ING: Ingreso
            validarFechaUnica(arguments, ConstanteCampoArchivo.ING, ConstanteCampoArchivo.ING_MSG, ConstanteCampoArchivo.FECHA_INGRESO,
                    ConstanteCampoArchivo.FECHA_INGRESO_MSG);
            // Validacion Fecha RET formato (AAAA-MM-DD)
            validarFechaUnica(arguments, ConstanteCampoArchivo.RET, ConstanteCampoArchivo.RET_MSG, ConstanteCampoArchivo.FECHA_RETIRO,
                    ConstanteCampoArchivo.FECHA_RETIRO_MSG);
            // Validacion Fecha VSP formato (AAAA-MM-DD)
            validarFechaUnica(arguments, ConstanteCampoArchivo.VSP, ConstanteCampoArchivo.VSP_MSG, ConstanteCampoArchivo.FECHA_INICIO_VSP,
                    ConstanteCampoArchivo.FECHA_INICIO_VSP_MSG);
            // Validacion Fecha VST: Variación transitoria del salario.
            validarNovedadFechaInicioFin(arguments, ConstanteCampoArchivo.VST, ConstanteCampoArchivo.FECHA_INICIO_VST,
                    ConstanteCampoArchivo.FECHA_INICIO_VST_MSG, ConstanteCampoArchivo.FECHA_FIN_VST,
                    ConstanteCampoArchivo.FECHA_FIN_VST_MSG);
            // Validacion Fecha SLN formato (AAAA-MM-DD)
            validarNovedadFechaInicioFin(arguments, ConstanteCampoArchivo.SLN, ConstanteCampoArchivo.FECHA_INICIO_SLN,
                    ConstanteCampoArchivo.FECHA_INICIO_SLN_MSG, ConstanteCampoArchivo.FECHA_FIN_SLN,
                    ConstanteCampoArchivo.FECHA_FIN_SLN_MSG);
            // Validacion Fecha IGE formato (AAAA-MM-DD)
            validarNovedadFechaInicioFin(arguments, ConstanteCampoArchivo.IGE, ConstanteCampoArchivo.FECHA_INICIO_IGE,
                    ConstanteCampoArchivo.FECHA_INICIO_IGE_MSG, ConstanteCampoArchivo.FECHA_FIN_IGE,
                    ConstanteCampoArchivo.FECHA_FIN_IGE_MSG);
            // Validacion Fecha LMA formato (AAAA-MM-DD)
            validarNovedadFechaInicioFin(arguments, ConstanteCampoArchivo.LMA, ConstanteCampoArchivo.FECHA_INICIO_LMA,
                    ConstanteCampoArchivo.FECHA_INICIO_LMA_MSG, ConstanteCampoArchivo.FECHA_FIN_LMA,
                    ConstanteCampoArchivo.FECHA_FIN_LMA_MSG);
            // Validacion Fecha VAC - LR: Vacaciones, Licencia remunerada
            validarNovedadFechaInicioFin(arguments, ConstanteCampoArchivo.VAC, ConstanteCampoArchivo.FECHA_INICIO_VAC,
                    ConstanteCampoArchivo.FECHA_INICIO_VAC_MSG, ConstanteCampoArchivo.FECHA_FIN_VAC,
                    ConstanteCampoArchivo.FECHA_FIN_VAC_MSG);
            /*
             * Validacion IRL: días de incapacidad por accidente de trabajo o
             * enfermedad laboral
             */
            validarNovedadFechaInicioFin(arguments, ConstanteCampoArchivo.IRL, ConstanteCampoArchivo.FECHA_INICIO_IRL,
                    ConstanteCampoArchivo.FECHA_INICIO_IRL_MSG, ConstanteCampoArchivo.FECHA_FIN_IRL,
                    ConstanteCampoArchivo.FECHA_FIN_IRL_MSG);
            // Se validan los Días cotizados.
            validarDiasCotizados(arguments);
            // se valida el salario basico
            validarSalarioBasico(arguments);
            // se valida Ingreso Base Cotización (IBC)
            validarIBC(arguments, tipoCotizante);
            // se valida la tarifa
            validarTarifa(arguments);
            // se valida el Aporte obligatorio.
            validarAporteObligatorio(arguments);
            // se validan las correciones
            validarCorreciones(arguments);
            // se valida Salario Integral
            validarXoVacio(arguments, ConstanteCampoArchivo.SALARIO_INTEGRAL, ExpresionesRegularesConstants.X_O_VACIO,
                    ConstanteCampoArchivo.SALARIO_INTEGRAL_MSG);
            // se valida el Número de horas laboradas
            validarNumeroHoraLaboral(arguments);
        } finally {
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
        }
        logger.debug("Finaliza PagoManualAporteLineValidator.validate(LineArgumentDTO)");
    }

    /**
     * Método encargado de validar la fecha de vsp
     * 
     * @param arguments,
     *        argumentos a validar
     */
    private void validarFechaUnica(LineArgumentDTO arguments, String campoValidar, String mensajeCampoValidar, String campoFecha,
            String campoMsg) {
        boolean valorValidacion = validarXoVacio(arguments, campoValidar, ExpresionesRegularesConstants.X_O_VACIO, mensajeCampoValidar);
        if (valorValidacion && (arguments.getLineValues().get(campoFecha) != null)) {
            try {
                String strFechaInicio = arguments.getLineValues().get(campoFecha).toString();
                CalendarUtils.convertirFechaAnoMesDia(strFechaInicio);
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), campoMsg,
                        ConstanteCampoArchivo.DEBE_SER_UN_FORMATO_AAAA_MM_DD));
            }
        }
    }

    /**
     * Método encargado de valdiar las novedades por fecha inicio y fecha fin
     * 
     * @param arguments,
     *        argumentos que contiene la informacion
     * @param campoArchivo,
     *        campo archivo a validar
     * @param campoFechaInicio,
     *        mensaje de fecha inicio a mostrar
     * @param campoFechaInicioMsg,
     *        mensaje de fecha inicio a mostrar
     * @param campoFechaFin,
     *        fecha fin a validar
     * @param campoFechaFinMsg,
     *        mensaje de fecha fin a mostrar
     */
    private void validarNovedadFechaInicioFin(LineArgumentDTO arguments, String campoArchivo, String campoFechaInicio,
            String campoFechaInicioMsg, String campoFechaFin, String campoFechaFinMsg) {
    	boolean valorIng ;
    	if(campoArchivo.equals(ConstanteCampoArchivo.IRL)){
    		valorIng = validarDias(arguments, campoArchivo, ConstanteCampoArchivo.ING_MSG);	
    	} else {
    		valorIng = validarXoVacio(arguments, campoArchivo, ExpresionesRegularesConstants.X_O_VACIO, ConstanteCampoArchivo.ING_MSG);	
    	}
    	
        Date dateFechaInicio = null;
        Date dateFechaFin = null;
        if (valorIng) {
            try {
                if (arguments.getLineValues().get(campoFechaInicio) != null) {
                    String strFechaInicio = arguments.getLineValues().get(campoFechaInicio).toString();
                    dateFechaInicio = CalendarUtils.convertirFechaAnoMesDia(strFechaInicio);
                }
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), campoFechaInicioMsg,
                        "Debe ser en formato (AAAA-MM-DD)"));
            }
            try {
                if (arguments.getLineValues().get(campoFechaFin) != null) {
                    String strFechaFin = arguments.getLineValues().get(campoFechaFin).toString();
                    dateFechaFin = CalendarUtils.convertirFechaAnoMesDia(strFechaFin);
                }
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), campoFechaFinMsg,
                        ConstanteCampoArchivo.DEBE_SER_UN_FORMATO_AAAA_MM_DD));
            }
            if ((dateFechaInicio != null && dateFechaFin != null) && (dateFechaInicio.getTime() > dateFechaFin.getTime())) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), campoFechaFinMsg,
                        ConstanteCampoArchivo.DEBE_SER_UN_FORMATO_AAAA_MM_DD));
            }
        }
    }

    /**
     * Método encargado de validar el número de horas laborales
     * 
     * @param arguments,
     *        dto que contiene la información a validar
     */
    private void validarNumeroHoraLaboral(LineArgumentDTO arguments) {
        try {
            if (arguments.getContext().get(ConstanteCampoArchivo.NUMERO_HORA_LABORAL) != null) {
                new Integer((arguments.getContext().get(ConstanteCampoArchivo.NUMERO_HORA_LABORAL).toString()));
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.NUMERO_HORA_LABORAL_MSG,
                    "Valor de horas laborales invalido"));
        }
    }

    /**
     * Método encargado de validar el aporte obligatorio
     * 
     * @param arguments,
     *        argumento a validar
     */
    private void validarAporteObligatorio(LineArgumentDTO arguments) {
        try {
            if (arguments.getContext().get(ConstanteCampoArchivo.APORTE_OBLIGATORIO) != null) {
                String aporteObligatorio = arguments.getContext().get(ConstanteCampoArchivo.APORTE_OBLIGATORIO).toString();
                new BigDecimal(aporteObligatorio);
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.APORTE_OBLIGATORIO_MSG,
                    "Debe ser un valor valido"));
        }
    }

    /**
     * Método encargado de validar un municipio
     * 
     * @param arguments,
     *        argumento a validar
     * @param line,
     *        linea a validar
     * @param departamento,
     *        departamento al que debe de pertenecer el municipio
     */
    @SuppressWarnings("unchecked")
    private void validarMunicipio(LineArgumentDTO arguments, Map<String, Object> line, Departamento departamento) {
        Municipio municipio;
        List<Municipio> lstMunicipio = new ArrayList<>();
        String codigoMunicipio;
        try {
            Object codigo = line.get(ConstanteCampoArchivo.CODIGO_MUNICIPIO);
            if (codigo != null) {
                lstMunicipio = ((List<Municipio>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO));
                codigoMunicipio = departamento.getCodigo() + "" + ((String) line.get(ConstanteCampoArchivo.CODIGO_MUNICIPIO));
                municipio = GetValueUtil.getMunicipioCodigo(lstMunicipio, codigoMunicipio);
                if (departamento.getIdDepartamento() != municipio.getIdDepartamento()) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(),
                            ConstanteCampoArchivo.CODIGO_MUNICIPIO_MSG, "El municipio debe de pertenecer al departamento ingresado"));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.CODIGO_MUNICIPIO_MSG,
                    "Debe ser un departamento valido"));
        }
    }

    /**
     * Método encargado de validar un deparmento
     * 
     * @param arguments,
     *        argumentos a validar
     * @param line,
     *        linea a validar
     * @return retorna el departamento
     */
    @SuppressWarnings("unchecked")
    private Departamento validarDepartamento(LineArgumentDTO arguments, Map<String, Object> line) {
        departamento = null;
        List<Departamento> lstDepartamento = new ArrayList<>();
        try {
            lstDepartamento = ((List<Departamento>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
            Object codigo = line.get(ConstanteCampoArchivo.CODIGO_DEPARTAMENTO);
            if (codigo != null) {
                Long codigoDepartamento = new Long(((String) line.get(ConstanteCampoArchivo.CODIGO_DEPARTAMENTO)));
                departamento = GetValueUtil.getDepartamento(lstDepartamento, codigoDepartamento);
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.CODIGO_DEPARTAMENTO_MSG,
                    "Debe ser un departamento valido"));
            departamento = null;
        }
        return departamento;
    }

    /**
     * Método encargado de validar las correciones
     * 
     * @param arguments
     */
    private void validarCorreciones(LineArgumentDTO arguments) {
        try {
            if (arguments.getContext().get(ConstanteCampoArchivo.CORRECCIONES) != null) {
                String correcion = ((String) arguments.getContext().get(ConstanteCampoArchivo.CORRECCIONES));
                if ((correcion.equals(ConstanteCampoArchivo.VALOR_CORRECION_A)
                        || correcion.equals(ConstanteCampoArchivo.VALOR_CORRECION_C))) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.CORRECCIONES_MSG,
                            "Puede ser blanco, A ó C."));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.CORRECCIONES_MSG,
                    "Puede ser blanco, A ó C."));
        }
    }

    /**
     * Método encargado de validar la tarifa
     * 
     * @param arguments,
     *        argumento a validar
     */
    private void validarTarifa(LineArgumentDTO arguments) {
        try {
            if (arguments.getLineValues().get(ConstanteCampoArchivo.TARIFA) != null) {
                String tarifa = arguments.getLineValues().get(ConstanteCampoArchivo.TARIFA).toString();
                new BigDecimal(tarifa);
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.TARIFA_MSG,
                    "Debe ser un valor valido"));
        }
    }

    /**
     * Método encargado de validar el Ingreso Base Cotización (IBC)
     * 
     * @param arguments,
     *        argumento a validar
     * @param tipoCotizante,
     *        tipo de cotizante a comprar
     */
    private void validarIBC(LineArgumentDTO arguments, TipoCotizanteEnum tipoCotizante) {
        BigDecimal ibc = null;
        try {
            if (arguments.getLineValues().get(ConstanteCampoArchivo.IBC) != null) {
                String valorIBC = arguments.getLineValues().get(ConstanteCampoArchivo.IBC).toString();
                ibc = new BigDecimal(valorIBC);
                if (ibc.longValue() < ConstanteCampoArchivo.VALOR_MINIMO_SALARIO.longValue()) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.IBC_MSG,
                            "Debe ser un valor mayor o igual 0"));
                }
            }

        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.IBC_MSG,
                    "Debe ser un valor valido. Sin comas ni puntos.No puede ser menor cero."));
            ibc = null;
        }
        if (ibc == null && (tipoCotizante.equals(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE)
                || tipoCotizante.equals(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO)
                || tipoCotizante.equals(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC)
                || tipoCotizante.equals(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR)
                || tipoCotizante.equals(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI)
                || tipoCotizante.equals(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL)
                || tipoCotizante.equals(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE))) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.IBC_MSG,
                    "Campo obligatorio para los tipos de cotizante 1,2,18,22, 30,51 y 55."));
        }
    }

    /**
     * Método encargado de validar el salario basico
     * 
     * @param arguments,
     *        argumentos a validar
     */
    private void validarSalarioBasico(LineArgumentDTO arguments) {
        try {
            if (arguments.getLineValues().get(ConstanteCampoArchivo.SALARIO_BASICO) != null) {
                String salario = arguments.getLineValues().get(ConstanteCampoArchivo.SALARIO_BASICO).toString();
                BigDecimal salarioBasico = new BigDecimal(salario);
                if (salarioBasico.longValue() <= ConstanteCampoArchivo.VALOR_MINIMO_SALARIO.longValue()) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.SALARIO_BASICO_MSG,
                            "Debe ser un valor mayor a 0"));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.SALARIO_BASICO_MSG,
                    "Debe ser un valor valido. Sin comas ni puntos.No puede ser menor cero."));
        }
    }

    /**
     * Método encargado de validar los días cotizados
     * 
     * @param arguments,
     *        argumentos a validar
     */
    private void validarDiasCotizados(LineArgumentDTO arguments) {
        Long diaCotizado;
        try {
            if (arguments.getLineValues().get(ConstanteCampoArchivo.DIAS_COTIZADOS) != null) {
                diaCotizado = new Long(((Integer) arguments.getLineValues().get(ConstanteCampoArchivo.DIAS_COTIZADOS)).toString());
                if (!(diaCotizado >= ConstanteCampoArchivo.DIA_COTIZADO_INICIO
                        && diaCotizado <= ConstanteCampoArchivo.DIA_COTIZADO_FINAL)) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.DIAS_COTIZADOS_MSG,
                            "Debe ser un valor entre" + ConstanteCampoArchivo.DIA_COTIZADO_INICIO + " y "
                                    + ConstanteCampoArchivo.DIA_COTIZADO_FINAL));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.DIAS_COTIZADOS_MSG,
                    ConstanteCampoArchivo.DIAS_COTIZADOS_MSG + ConstanteCampoArchivo.INVALIDO_MSG));
        }
    }

    /**
     * Método encargado de validar si es un colombiano en el exterior
     * 
     * @param arguments,
     *        argumentos a validar
     * @param tipoIdentificacion,
     *        tipo de identificacion a validar
     */
    private void validarColombianoExterior(LineArgumentDTO arguments, TipoIdentificacionEnum tipoIdentificacion) {
        boolean colombianoExterior;
        colombianoExterior = validarXoVacio(arguments, ConstanteCampoArchivo.COLOMBIANO_EXTERIOR, ExpresionesRegularesConstants.X_O_VACIO,
                ConstanteCampoArchivo.COLOMBIANO_EXTERIOR_MSG);
        if (colombianoExterior && !(tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)
                || tipoIdentificacion.equals(TipoIdentificacionEnum.TARJETA_IDENTIDAD))) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.COLOMBIANO_EXTERIOR_MSG,
                    "El tipo de identificacion debe ser " + "Cédula de ciudadanía  (CC) o  Tarjeta de identidad  (TI)"
                            + "Si Colombiano en el exterior tiene seleccionado X"));
        }
    }

    /**
     * Método encargado de validar si un extranjero es no obligado a cotizar y
     * el tipo de identificacion valido para este
     * 
     * @param arguments,
     *        argumentos a validar
     * @param tipoIdentificacion,
     *        tipo de identificacion
     */
    private void validarExtranjeroNoObligadoACotizar(LineArgumentDTO arguments, TipoIdentificacionEnum tipoIdentificacion) {
        boolean extranjeroNoObligadoCotizar = validarXoVacio(arguments, ConstanteCampoArchivo.EXTRANJERO_NO_ABLIGADO_A_COTIZAR,
                ExpresionesRegularesConstants.X_O_VACIO, ConstanteCampoArchivo.EXTRANJERO_NO_ABLIGADO_A_COTIZAR_MSG);
        if (extranjeroNoObligadoCotizar && !(tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)
                || tipoIdentificacion.equals(TipoIdentificacionEnum.PASAPORTE)
                || tipoIdentificacion.equals(TipoIdentificacionEnum.CARNE_DIPLOMATICO))) {
            lstHallazgos
                    .add(FuncionesUtilitarias
                            .crearHallazgo(arguments.getLineNumber(), ConstanteCampoArchivo.EXTRANJERO_NO_ABLIGADO_A_COTIZAR_MSG,
                                    "El tipo de identificacion debe ser "
                                            + "Cédula de extranjería (CE) o Pasaporte (PA) o Carné diplomático(CD)."
                                            + "Si Extranjero no obligado a cotizar a pensiones tiene seleccionado X"));
        }
    }

    /**
     * Validador de campo aplicando una expresión regular.
     * 
     * @param arguments,
     *        dto que contiene los argumentos a validar
     * @param campoVal,
     *        compo a validar
     * @param regex,
     *        expresion regular
     * @param mensaje,
     *        mensaje de validacion del campo
     */
    private void validarRegex(LineArgumentDTO arguments, String campoVal, String regex, String mensaje, String campoMSG) {
        try {
            ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
            String valorCampo = "";
            if ((String) (arguments.getLineValues()).get(campoVal) != null
                    && (campoVal.equals(ConstanteCampoArchivo.SEGUNDO_NOMBRE) || campoVal.equals(ConstanteCampoArchivo.SEGUNDO_APELLIDO))) {
                String campoValidar = ((String) (arguments.getLineValues()).get(campoVal)).trim();
                if (campoVal.equals(ConstanteCampoArchivo.PRIMER_NOMBRE) || campoVal.equals(ConstanteCampoArchivo.SEGUNDO_NOMBRE)
                        || campoVal.equals(ConstanteCampoArchivo.PRIMER_APELLIDO)
                        || campoVal.equals(ConstanteCampoArchivo.SEGUNDO_APELLIDO)) {
                    valorCampo = campoValidar.toLowerCase();
                }
                if (valorCampo != null && !(valorCampo.matches(regex))) {
                    hallazgo = FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje);
                }
                if (hallazgo != null) {
                    lstHallazgos.add(hallazgo);
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), campoMSG,
                    " Valor perteneciente " + campoMSG + " invalido"));
        }
    }

    /**
     * Método encargado de validar si un campo tiene valor x o es vacio
     * 
     * @param arguments
     * @param campoVal,
     *        campo a validar
     * @param regex,
     *        expresion regular
     * @param campoMSG,
     *        campo a mostrar el mensaje
     * @return retorna true si no tiene errores
     */
    private boolean validarXoVacio(LineArgumentDTO arguments, String campoVal, String regex, String campoMSG) {
        try {
            if (arguments.getLineValues().get(campoVal) != null) {
                String valorCampo = ((String) arguments.getLineValues().get(campoVal)).trim().toUpperCase();
                if (valorCampo != null && !(valorCampo.matches(regex))) {
                    return false;
                }
            }
            else {
                return false;
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), campoMSG,
                    " Valor perteneciente " + campoMSG + " invalido"));
            return false;
        }
        return true;
    }

    /**
     * Método encargado verificar el número de documento de identificación
     * 
     * @param tipoIdentificacion,
     *        tipo de identificación al que pertenece el número
     * @param arguments,
     *        argumento a seleccionar el número
     */
    public void verificarNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, LineArgumentDTO arguments) {
        switch (tipoIdentificacion) {
            case NIT:
                // se valida el número de identificación respecto a NIT
                validarRegex(arguments, ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE, ExpresionesRegularesConstants.NIT,
                        "El Nit debe terner 9 o 10 dígitos.", ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE_MSG);
                break;
            case CEDULA_CIUDADANIA:
                // se valida el número de identificación respecto a
                // CEDULA_CIUDADANIA
                validarRegex(arguments, ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE,
                        ExpresionesRegularesConstants.CEDULA_CIUDADANIA, "La cédula de ciudadanía deber tener 3, 4, 5, 8 o 10 dígitos.",
                        ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE_MSG);
                break;
            case PASAPORTE:
                // se valida el número de identificación respecto a PASAPORTE
                validarRegex(arguments, ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE, ExpresionesRegularesConstants.PASAPORTE,
                        "El pasaporte no puede tener más de 16 caracteres.", ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE_MSG);
                break;
            case REGISTRO_CIVIL:
                // se valida el número de identificación respecto a REGISTRO_CIVIL
                validarRegex(arguments, ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE, ExpresionesRegularesConstants.REGISTRO_CIVIL,
                        "El registro civil debe tener 8, 10 u 11 caracteres.", ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE_MSG);
                break;
            case TARJETA_IDENTIDAD:
                // se valida el número de identificación respecto a
                // TARJETA_IDENTIDAD
                validarRegex(arguments, ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE,
                        ExpresionesRegularesConstants.TARJETA_IDENTIDAD, "La tarjeta de identidad solo puede ser de 10 u 11 dígitos.",
                        ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE_MSG);
                break;
            case CARNE_DIPLOMATICO:
                // se valida el número de identificación respecto a
                // CARNE_DIPLOMATICO
                validarRegex(arguments, ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE,
                        ExpresionesRegularesConstants.CARNE_DIPLOMATICO,
                        "El carné diplomático debe tener máximo 15 caracteres.",
                        ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE_MSG);
                break;
            default:
                validarRegex(arguments, ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE,
                        ExpresionesRegularesConstants.CEDULA_CIUDADANIA, "El número de identificación debe de tener un valor valido",
                        ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE_MSG);
                break;
        }
    }
    
    /**
     * Método encargado de validar el salario basico
     * 
     * @param arguments,
     *        argumentos a validar
     */
    private boolean validarDias(LineArgumentDTO arguments, String campoArchivo, String campoMSG) {
    	boolean resultadoValidacion=false;
    	Long diasCalendario;
        try {
            if (arguments.getLineValues().get(campoArchivo) != null) {
                diasCalendario = new Long( arguments.getLineValues().get(campoArchivo).toString());
                if ((diasCalendario >= ConstanteCampoArchivo.DIA_COTIZADO_INICIO
                        && diasCalendario <= ConstanteCampoArchivo.DIA_COTIZADO_FINAL)) {
                    resultadoValidacion=true;
                }
            } 
            return resultadoValidacion;
        } catch (Exception e) {
        	lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), campoMSG,
                    " Valor perteneciente " + campoMSG + " invalido"));
            return false;
        }
	}

}