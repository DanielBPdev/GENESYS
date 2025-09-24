package com.asopagos.aportes.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.aportes.constants.ConstanteCampoArchivo;
import com.asopagos.aportes.util.FuncionesUtilitarias;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIPEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que implementa la validación del archivo de cotizantes pensionados
 * de un aporte manual<br/>
 * <b>Módulo:</b> Asopagos - HU-212-482 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class PagoManualAportePensionadoLineValidator extends LineValidator {

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(PagoManualAportePensionadoLineValidator.class);

    /**
     * Lista de Hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        String firmaMetodo = "validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            lstHallazgos = new ArrayList<>();
            Map<String, Object> line = arguments.getLineValues();

            // se valida el tipo de identificación
            TipoIdentificacionEnum tipoIdentificacion = null;
            try {
                tipoIdentificacion = TipoIdentificacionEnum
                        .obtenerTiposIdentificacionPILAEnum(((String) line.get(EtiquetaArchivoIPEnum.IP23.getNombreCampo())).toUpperCase());
            } catch (Exception e) {
                lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), EtiquetaArchivoIPEnum.IP23.getDescripcion(),
                        EtiquetaArchivoIPEnum.IP23.getDescripcion() + ConstanteCampoArchivo.INVALIDO_MSG));
            }

            // Se valida el numero de documento
            verificarNumeroDocumento(tipoIdentificacion, arguments);

            // Se valida el primer apellido
            validarRegex(arguments, EtiquetaArchivoIPEnum.IP25.getNombreCampo(), ExpresionesRegularesConstants.PRIMER_APELLIDO,
                    "Solo se permiten 19 caracteres del alfabeto", EtiquetaArchivoIPEnum.IP25.getDescripcion());

            // Se valida el segundo apellido
            validarRegex(arguments, EtiquetaArchivoIPEnum.IP26.getNombreCampo(), ExpresionesRegularesConstants.SEGUNDO_APELLIDO,
                    "Solo se permiten 30 caracteres del alfabeto", EtiquetaArchivoIPEnum.IP26.getDescripcion());

            // Se valida el primer nombre
            validarRegex(arguments, EtiquetaArchivoIPEnum.IP27.getNombreCampo(), ExpresionesRegularesConstants.PRIMER_NOMBRE,
                    "Solo se permiten 20 caracteres del alfabeto", EtiquetaArchivoIPEnum.IP27.getDescripcion());

            // Se valida el segundo nombre
            validarRegex(arguments, EtiquetaArchivoIPEnum.IP28.getNombreCampo(), ExpresionesRegularesConstants.SEGUNDO_NOMBRE,
                    "Solo se permiten 30 caracteres del alfabeto", EtiquetaArchivoIPEnum.IP28.getDescripcion());

            // Validacion Fecha ING: Ingreso
            validarFechaUnica(arguments, EtiquetaArchivoIPEnum.IP215.getNombreCampo(), EtiquetaArchivoIPEnum.IP215.getDescripcion(),
                    EtiquetaArchivoIPEnum.IP219.getNombreCampo(), EtiquetaArchivoIPEnum.IP219.getDescripcion());

            // Validacion Fecha RET formato (AAAA-MM-DD)
            validarFechaUnica(arguments, EtiquetaArchivoIPEnum.IP216.getNombreCampo(), EtiquetaArchivoIPEnum.IP216.getDescripcion(),
                    EtiquetaArchivoIPEnum.IP220.getNombreCampo(), EtiquetaArchivoIPEnum.IP220.getDescripcion());

            // Validacion Fecha VSP formato (AAAA-MM-DD)
            validarFechaUnica(arguments, EtiquetaArchivoIPEnum.IP217.getNombreCampo(), EtiquetaArchivoIPEnum.IP217.getDescripcion(),
                    EtiquetaArchivoIPEnum.IP221.getNombreCampo(), EtiquetaArchivoIPEnum.IP221.getDescripcion());

            // Validacion Fecha SUS formato (AAAA-MM-DD)
            validarNovedadFechaInicioFin(arguments, EtiquetaArchivoIPEnum.IP218.getNombreCampo(),
                    EtiquetaArchivoIPEnum.IP222.getNombreCampo(), EtiquetaArchivoIPEnum.IP222.getDescripcion(),
                    EtiquetaArchivoIPEnum.IP223.getNombreCampo(), EtiquetaArchivoIPEnum.IP223.getDescripcion());

            // se valida la mesada pensional
            validarMesadaPensional(arguments);
            
            // se valida la tarifa
            validarTarifa(arguments);
            
            // se valida el Aporte obligatorio.
            validarAporteObligatorio(arguments);
        } finally {
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
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
        String nombreCampo = EtiquetaArchivoIPEnum.IP24.getNombreCampo();
        String descripcion = EtiquetaArchivoIPEnum.IP24.getDescripcion();
        switch (tipoIdentificacion) {
            case NIT:
                // se valida el número de identificación respecto a NIT
                validarRegex(arguments, nombreCampo, ExpresionesRegularesConstants.NIT, "El Nit debe terner 9 o 10 dígitos.", descripcion);
                break;
            case CEDULA_CIUDADANIA:
                // se valida el número de identificación respecto a
                // CEDULA_CIUDADANIA
                validarRegex(arguments, nombreCampo, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                        "La cédula de ciudadanía deber tener 3, 4, 5, 8 o 10 dígitos.", descripcion);
                break;
            case PASAPORTE:
                // se valida el número de identificación respecto a PASAPORTE
                validarRegex(arguments, nombreCampo, ExpresionesRegularesConstants.PASAPORTE,
                        "El pasaporte no puede tener más de 16 caracteres.", descripcion);
                break;
            case REGISTRO_CIVIL:
                // se valida el número de identificación respecto a REGISTRO_CIVIL
                validarRegex(arguments, nombreCampo, ExpresionesRegularesConstants.REGISTRO_CIVIL,
                        "El registro civil debe tener 8, 10 u 11 caracteres.", descripcion);
                break;
            case TARJETA_IDENTIDAD:
                // se valida el número de identificación respecto a
                // TARJETA_IDENTIDAD
                validarRegex(arguments, nombreCampo, ExpresionesRegularesConstants.TARJETA_IDENTIDAD,
                        "La tarjeta de identidad solo puede ser de 10 u 11 dígitos.", descripcion);
                break;
            case CARNE_DIPLOMATICO:
                // se valida el número de identificación respecto a
                // CARNE_DIPLOMATICO
                validarRegex(arguments, nombreCampo, ExpresionesRegularesConstants.CARNE_DIPLOMATICO,
                        "El carné diplomático debe tener máximo 15 caracteres.",
                        descripcion);
                break;
            default:
                validarRegex(arguments, nombreCampo, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                        "El número de identificación debe de tener un valor valido", descripcion);
                break;
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
        boolean valorIng = validarXoVacio(arguments, campoArchivo, ExpresionesRegularesConstants.X_O_VACIO, ConstanteCampoArchivo.ING_MSG);
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
     * Método encargado de validar la mesada pensional
     * 
     * @param arguments,
     *        argumentos a validar
     */
    private void validarMesadaPensional(LineArgumentDTO arguments) {
        try {
            if (arguments.getLineValues().get(EtiquetaArchivoIPEnum.IP213.getNombreCampo()) != null) {
                String salario = arguments.getLineValues().get(EtiquetaArchivoIPEnum.IP213.getNombreCampo()).toString();
                BigDecimal salarioBasico = new BigDecimal(salario);
                if (salarioBasico.longValue() <= ConstanteCampoArchivo.VALOR_MINIMO_SALARIO.longValue()) {
                    lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(),
                            EtiquetaArchivoIPEnum.IP213.getDescripcion(), "Debe ser un valor mayor a 0"));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), EtiquetaArchivoIPEnum.IP213.getDescripcion(),
                    "Debe ser un valor valido. Sin comas ni puntos.No puede ser menor cero."));
        }
    }

    /**
     * Método encargado de validar la tarifa
     * 
     * @param arguments,
     *            argumento a validar
     */
    private void validarTarifa(LineArgumentDTO arguments) {
        try {
            if (arguments.getLineValues().get(EtiquetaArchivoIPEnum.IP211.getNombreCampo()) != null) {
                String tarifa= arguments.getLineValues().get(EtiquetaArchivoIPEnum.IP211.getNombreCampo()).toString();
                new BigDecimal(tarifa);
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), EtiquetaArchivoIPEnum.IP211.getDescripcion(),
                    "Debe ser un valor valido"));
        }
    }

    /**
     * Método encargado de validar el aporte obligatorio
     * 
     * @param arguments,
     *            argumento a validar
     */
    private void validarAporteObligatorio(LineArgumentDTO arguments) {
        try {
            if (arguments.getLineValues().get(EtiquetaArchivoIPEnum.IP212.getNombreCampo()) != null) {
                String tarifa= arguments.getLineValues().get(EtiquetaArchivoIPEnum.IP212.getNombreCampo()).toString();
                new BigDecimal(tarifa);
            }
        } catch (Exception e) {
            lstHallazgos.add(FuncionesUtilitarias.crearHallazgo(arguments.getLineNumber(), EtiquetaArchivoIPEnum.IP212.getDescripcion(),
                    "Debe ser un valor valido"));
        }
    }
}
