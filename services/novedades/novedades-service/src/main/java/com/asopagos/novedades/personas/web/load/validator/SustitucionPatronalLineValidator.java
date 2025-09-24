package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.afiliados.clients.ObtenerEmpleadoresRelacionadosAfiliado;
import com.asopagos.afiliados.clients.ObtenerInfoAfiliadoRespectoEmpleador;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.afiliados.dto.InfoAfiliadoRespectoEmpleadorDTO;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.empresas.clients.ObtenerSucursalEmpresaByCodigoAndEmpleador;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de sustitucion
 * patronal<br/>
 * <b>Módulo:</b> Asopagos - HU-13-XXX<br/>
 *
 * @author <a href="mailto:maria.cuellar@eprocess.com.co">Maria Cuellar</a>
 */
public class SustitucionPatronalLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Calendar
     */
    private Calendar cInicioLabores = Calendar.getInstance();

    /**
     * Método constructor de la clase
     */
    public SustitucionPatronalLineValidator() {
        super();
        cInicioLabores.set(Calendar.YEAR, 1965);
        cInicioLabores.set(Calendar.MONTH, 1);
        cInicioLabores.set(Calendar.DAY_OF_YEAR, 1);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        Map<String, Object> line = arguments.getLineValues();
        try {
            Long lineNumber = arguments.getLineNumber();
            Calendar sistema = Calendar.getInstance();
            TipoIdentificacionEnum tipoIdentificacionEmpleadorOrigen = null;
            TipoIdentificacionEnum tipoIdentificacionEmpleadorDestino = null;
            TipoIdentificacionEnum tipoIdentificacionAfiliado = null;

//            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_ORIGEN_MSG,
//                        "iNGRESO A SustitucionPatronalLineValidator !!!!"));
            // Se valida el campo No 1 - Tipo identificacion empleador origen
            try {
                tipoIdentificacionEmpleadorOrigen = GetValueUtil
                        .getTipoIdentificacionByPila(((String) line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_ORIGEN)));
                if (tipoIdentificacionEmpleadorOrigen == null) {
                    throw new Exception("Invalido");
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_ORIGEN_MSG,
                        "Tipo de identificación invalido"));
                tipoIdentificacionEmpleadorOrigen = null;
            }

            // Se valida el campo No 2 - Número de identificación del empleador origen
            verificarNumeroDocumento(tipoIdentificacionEmpleadorOrigen, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_ORIGEN,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_ORIGEN_MSG, arguments);

            // Se valida el campo No 4 - Tipo identificacion empleador destino
            try {
                tipoIdentificacionEmpleadorDestino = GetValueUtil
                        .getTipoIdentificacionByPila(((String) line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_DESTINO)));
                if (tipoIdentificacionEmpleadorOrigen == null) {
                    throw new Exception("Invalido");
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_DESTINO_MSG,
                        "Tipo de identificación invalido"));
                tipoIdentificacionEmpleadorOrigen = null;
            }

            // Se valida el campo No 5 - Número de identificación del empleador destino
            verificarNumeroDocumento(tipoIdentificacionEmpleadorDestino, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_DESTINO,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_DESTINO_MSG, arguments);

            // Se valida el campo No 6 - Sucursal del empleador
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR,
                    ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES, null,
                    null, true, false);

            // Se valida el campo No 7 - Tipo de identificación del trabajador o cotizante o cabeza de familia
            try {
                tipoIdentificacionAfiliado = GetValueUtil
                        .getTipoIdentificacionByPila(((String) line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)));
                if (tipoIdentificacionAfiliado == null) {
                    throw new Exception("Invalido");
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO_MSG,
                        "Tipo de identificación invalido"));
                tipoIdentificacionAfiliado = null;
            }

            // Se valida el campo No 8 - Número de identificación del trabajador o cotizante o cabeza de familia 
            verificarNumeroDocumento(tipoIdentificacionAfiliado, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG, arguments);

            // Se valida el campo No 9 - Empleador origen-Fecha fin labores con el empleador origen 
            verificacionCampoFecha(arguments, line, sistema, ArchivoCampoNovedadConstante.FECHA_FIN_LABORES_EMPLEADOR_ORIGEN,
                    ArchivoCampoNovedadConstante.FECHA_FIN_LABORES_EMPLEADOR_ORIGEN_MSG);

            // Se valida el campo No 10 - Empleador destino-Fecha inicio labores con el empleadore destino 
            verificacionCampoFecha(arguments, line, sistema, ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_EMPLEADOR_DESTINO,
                    ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_EMPLEADOR_DESTINO_MSG);

            String fechaFinLaboresEmpleadorOrigen = line.get(ArchivoCampoNovedadConstante.FECHA_FIN_LABORES_EMPLEADOR_ORIGEN).toString();
            String fechaInicioLaboresEmpleadorDestino = line.get(ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_EMPLEADOR_DESTINO).toString();

            verificacionFechas(arguments, fechaFinLaboresEmpleadorOrigen, fechaInicioLaboresEmpleadorDestino);

            //Validacion relacion empleador afiliado
            if (tipoIdentificacionEmpleadorOrigen != null && tipoIdentificacionEmpleadorDestino != null && tipoIdentificacionAfiliado != null) {
                try {
                    verificarEmpleadoresRelacionadosAfiliado(arguments, tipoIdentificacionEmpleadorOrigen, tipoIdentificacionAfiliado);
                } catch (Exception e) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO_MSG,
                            "No se encontró relación entre el trabajador y el empleador. e: " + e.getMessage()));
                    tipoIdentificacionAfiliado = null;
                }

                try {
                    validarNovedad(arguments, tipoIdentificacionEmpleadorOrigen, tipoIdentificacionEmpleadorDestino, tipoIdentificacionAfiliado);
                } catch (Exception e) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO_MSG,
                            "Excepcion validando la novedad e: " + e.getMessage()));
                    tipoIdentificacionAfiliado = null;
                }
            }

        } finally {
            ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
            if (!lstHallazgos.isEmpty()) {
                ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
            }
        }
    }

    /**
     * Metodo encargado de ejecutar las validaciones del afiliado
     *
     * @param arguments
     * @param tipoIdentificacionEmpleadorOrigen
     * @param tipoIdentificacionEmpleadorDestino
     * @param tipoIdentificacionAfiliado
     * @param empleadorOrigenRelacionadoAfiliadoDTO
     */
    private void validarNovedad(LineArgumentDTO arguments,
            TipoIdentificacionEnum tipoIdentificacionEmpleadorOrigen,
            TipoIdentificacionEnum tipoIdentificacionEmpleadorDestino,
            TipoIdentificacionEnum tipoIdentificacionAfiliado) {

        String numeroIdEmpleadorOrigen = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_ORIGEN);
        String numeroIdEmpleadorDestino = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_DESTINO);
        String numeroIdAfiliado = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
        String sucursal = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR);

        InfoAfiliadoRespectoEmpleadorDTO afiliadoRespectoEmpleadorOrigenDTO = obtenerInfoAfiliadoRespectoEmpleador(
                tipoIdentificacionEmpleadorOrigen, numeroIdEmpleadorOrigen,
                tipoIdentificacionAfiliado, numeroIdAfiliado);

        //Confirmar si vamos a recibir el codigo o el nombre de la sucursal!!!
        //Consultamos los datos de la sucursal del empleador destino.
        SucursalEmpresaModeloDTO sucursalDestinoDTO = consultarSucursalPorCodigo(tipoIdentificacionEmpleadorDestino,
                numeroIdEmpleadorDestino,
                sucursal);

        Map<String, String> datosValidacion = new HashMap<String, String>();
        if (afiliadoRespectoEmpleadorOrigenDTO != null && afiliadoRespectoEmpleadorOrigenDTO.getInfoEstadoAfiliado() != null
                && afiliadoRespectoEmpleadorOrigenDTO.getInfoEstadoAfiliado().getIdPersona() != null) {

            if (afiliadoRespectoEmpleadorOrigenDTO.getInfoEstadoAfiliado() != null
                    && afiliadoRespectoEmpleadorOrigenDTO.getInfoEstadoAfiliado().getUltimaFechaIngreso() != null) {

                String fechaInicioLaboresEmpleadorDestino = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.FECHA_FIN_LABORES_EMPLEADOR_ORIGEN);
                Date fechaInicioLaboresEmpleadorDestinoDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaInicioLaboresEmpleadorDestino);

                if (afiliadoRespectoEmpleadorOrigenDTO.getInfoEstadoAfiliado().getUltimaFechaIngreso().compareTo(fechaInicioLaboresEmpleadorDestinoDate) > 0) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.FECHA_FIN_LABORES_EMPLEADOR_ORIGEN_MSG,
                            "La fecha fin labores empleador origen es mayor a la fecha de afiliación"));
                }
            }

            datosValidacion.put("tipoIdentificacion", tipoIdentificacionEmpleadorOrigen.name());
            datosValidacion.put("numeroIdentificacion", numeroIdEmpleadorOrigen);
            datosValidacion.put("idsPersonas", afiliadoRespectoEmpleadorOrigenDTO.getInfoEstadoAfiliado().getIdPersona().toString());
            datosValidacion.put("tipoIdentificacionEmpleadorDestino", tipoIdentificacionEmpleadorDestino.name());
            datosValidacion.put("numeroIdentificacionEmpleadorDestino", numeroIdEmpleadorDestino);
//            datosValidacion.put("idEmpleadorDestino", numeroIdAfiliado);

            if (sucursalDestinoDTO != null) {
                datosValidacion.put("idSucursal", sucursalDestinoDTO.getIdSucursalEmpresa().toString());
            } else {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_DESTINO_MSG,
                        "No se encuentra relación entre la empresa y la sucursal destino."));
            }
            datosValidacion.put("tipoTransaccion", TipoTransaccionEnum.SUSTITUCION_PATRONAL.name());

            try {

                ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                        TipoTransaccionEnum.SUSTITUCION_PATRONAL.name(), TipoTransaccionEnum.SUSTITUCION_PATRONAL.getProceso(),
                        ClasificacionEnum.PERSONA_JURIDICA.name(), datosValidacion);
                validarReglasService.execute();
                List<ValidacionDTO> list = (List<ValidacionDTO>) validarReglasService.getResult();

//                String resultJson = printJsonMessage(list,"validarReglasService");
//                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
//                                    resultJson));
                if (CollectionUtils.isNotEmpty(list)) {
                    List<ValidacionDTO> resultadoFilterValidacionList = list.stream().filter(iteValidacion -> iteValidacion.getResultado().
                            equals(ResultadoValidacionEnum.NO_APROBADA)).
                            collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(resultadoFilterValidacionList)) {
                        for (ValidacionDTO iteValidacion : resultadoFilterValidacionList) {
                            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                                    iteValidacion.getDetalle()));
                        }
                    }
                } else {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                            "El afiliado no cumple las reglas de validación."));
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "El afiliado no cumple las reglas de validación: "
                        + "tipoIdentificacionEmpleadorOrigen: " + tipoIdentificacionEmpleadorOrigen + ", numeroIdEmpleadorOrigen: " + numeroIdEmpleadorOrigen
                        + "tipoIdentificacionEmpleadorDestino: " + tipoIdentificacionEmpleadorDestino + ", numeroIdEmpleadorDestino: " + numeroIdEmpleadorDestino
                        + " tipoIdentificacionAfiliado: " + tipoIdentificacionAfiliado + ", numeroIdAfiliado: " + numeroIdAfiliado
                        + e.getMessage() + " Cause: " + e.getCause()));
            }

        } else {
            //Esta novedad se está reportando previamente
//            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
//                        "El afiliado no cumple las reglas de validación: "
//                        + "No encuentra relacion entre el afiliado y la empresa"));
        }
    }

    /**
     * Método que crea un hallazgo según la información ingresada
     *
     * @param lineNumber
     * @param campo
     * @param errorMessage
     * @return retorna el resultado hallazgo dto
     */
    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
        return hallazgo;
    }

    private void verificacionCampoFecha(LineArgumentDTO arguments, Map<String, Object> line, Calendar sistema,
            String campoFecha, String campoFechaMsg) {
        String strFInicioLabores = null;
        Date fInicioLabores = null;
        try {
            if (line.get(campoFecha) != null) {
                strFInicioLabores = line.get(campoFecha).toString();

                fInicioLabores = CalendarUtils.darFormatoYYYYMMDDGuionDate(strFInicioLabores);
                if (!CalendarUtils.esFechaMayor(fInicioLabores, cInicioLabores.getTime())) {
                    lstHallazgos.add(
                            crearHallazgo(arguments.getLineNumber(), campoFechaMsg,
                                    "La fecha no es mayor a 1965"));
                }
//                if (CalendarUtils.esFechaMayor(fInicioLabores, sistema.getTime())) {
//                    lstHallazgos.add(
//                            crearHallazgo(arguments.getLineNumber(), campoFechaMsg,
//                                    "La fecha es mayor a la fecha actual"));
//                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoFechaMsg,
                    " Digite la " + campoFechaMsg
                    + " en formato año - mes - dia. Ejemplo: 1990-10-02"));
        }
    }

    /**
     * Valor mínimo: primer día del mes actual (el mes de la fecha actual del
     * sistema) Valor máximo: el primer día del mes siguiente al actual (el mes
     * siguiente a la fecha actual del sistema)
     *
     * @param dateToCheck
     * @return
     */
    public static Map<String, String> isDateInRangeFechaInicioLabores(Date dateToCheck) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToCheck);

        Map<String, String> respuesta = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Obtener el primer día del mes actual
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);

        // Obtener el primer día del mes siguiente al actual
        Calendar firstDayOfNextMonth = Calendar.getInstance();
        firstDayOfNextMonth.add(Calendar.MONTH, 1);
        firstDayOfNextMonth.set(Calendar.DAY_OF_MONTH, 1);

        boolean resultado = (calendar.after(firstDayOfMonth) || sdf.format(calendar.getTime()).equals(sdf.format(firstDayOfMonth.getTime())))
                && calendar.before(firstDayOfNextMonth);

        respuesta.put("firstDayOfMonth", sdf.format(firstDayOfMonth.getTime()));
        respuesta.put("firstDayOfNextMonth", sdf.format(firstDayOfNextMonth.getTime()));
        respuesta.put("dateToCheck", sdf.format(calendar.getTime()));
        respuesta.put("result", resultado ? "OK" : "KO");

        return respuesta;
    }

    /**
     * -Valor mínimo: primer día del mes inmediatamente anterior (el mes de la
     * fecha actual del sistema (2023-09-01)) -> 2023-08-01 -Valor máximo: el
     * primer día del mes siguiente al actual (el mes siguiente a la fecha
     * actual del sistema) -> 2023-10-01
     *
     * @param dateToCheck
     * @return
     */
    public static Map<String, String> isDateInRangeFechaFinLabores(Date dateToCheck) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToCheck);

        Map<String, String> respuesta = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Obtener el primer día del mes actual
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.add(Calendar.MONTH, -1);
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);

        // Obtener el primer día del mes siguiente al actual
        Calendar firstDayOfNextMonth = Calendar.getInstance();
        firstDayOfNextMonth.add(Calendar.MONTH, 1);
        firstDayOfNextMonth.set(Calendar.DAY_OF_MONTH, 1);

        boolean resultado = (calendar.after(firstDayOfMonth) || sdf.format(calendar.getTime()).equals(sdf.format(firstDayOfMonth.getTime())))
                && calendar.before(firstDayOfNextMonth);

        respuesta.put("firstDayOfMonth", sdf.format(firstDayOfMonth.getTime()));
        respuesta.put("firstDayOfNextMonth", sdf.format(firstDayOfNextMonth.getTime()));
        respuesta.put("dateToCheck", sdf.format(calendar.getTime()));
        respuesta.put("result", resultado ? "OK" : "KO");

        return respuesta;
    }

    private void verificacionFechas(LineArgumentDTO arguments,
            String fechaFinLaboresEmpleadorOrigen, String fechaInicioLaboresEmpleadorDestino) {

        try {

            Date fechaFinLaboresEmpleadorOrigenDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaFinLaboresEmpleadorOrigen);
            Date fechaInicioLaboresEmpleadorDestinoDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaInicioLaboresEmpleadorDestino);

            if (CalendarUtils.esFechaMayor(fechaFinLaboresEmpleadorOrigenDate, fechaInicioLaboresEmpleadorDestinoDate)) {
                lstHallazgos.add(
                        crearHallazgo(arguments.getLineNumber(), "Fecha inicio labores empleador destino",
                                "La fecha Inicio labores Empleador Destino debe ser mayor a la fecha fin labores empleador origen"));

            }

            Map<String, String> dateInRangeFechaInicioLabores = isDateInRangeFechaInicioLabores(fechaInicioLaboresEmpleadorDestinoDate);
            if (dateInRangeFechaInicioLabores.get("result").equals("KO")) {
                lstHallazgos.add(
                        crearHallazgo(arguments.getLineNumber(), "Fecha inicio labores empleador destino",
                                "La fecha Inicio labores empleador destino: "+dateInRangeFechaInicioLabores.get("dateToCheck")+" no cumple las reglas de validacion. "
        			+ "Valor mínimo: primer día del mes actual (el mes de la fecha actual del sistema): "+dateInRangeFechaInicioLabores.get("firstDayOfMonth")+". "
        			+ "Valor máximo: el primer día del mes siguiente al actual (el mes siguiente a la fecha actual del sistema): "+dateInRangeFechaInicioLabores.get("firstDayOfNextMonth")));

            }

            Map<String, String> dateInRangeFechaFinLabores = isDateInRangeFechaFinLabores(fechaFinLaboresEmpleadorOrigenDate);
            if (dateInRangeFechaFinLabores.get("result").equals("KO")) {
                lstHallazgos.add(
                        crearHallazgo(arguments.getLineNumber(), "Fecha fin labores empleador origen",
                                "La fecha fin labores empleador origen: "+dateInRangeFechaFinLabores.get("dateToCheck")+" no cumple las reglas de validacion. "
        			+ "Valor mínimo: primer día del mes inmediatamente anterior(el mes de la fecha actual del sistema) : "+dateInRangeFechaFinLabores.get("firstDayOfMonth")+". "
        					+ "Valor máximo: el primer día del mes siguiente al actual (el mes siguiente a la fecha actual del sistema): "+dateInRangeFechaFinLabores.get("firstDayOfNextMonth")));

            }

        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), "",
                    "Las fechas no cumplen el formato año - mes - dia. Ejemplo: 1990-10-02"));
        }
    }

    /**
     * Metodo encargado de verifiar el salario mensual
     *
     * @param arguments
     * @param line
     */
    private void verificarSalarioMensual(LineArgumentDTO arguments) {
        try {
            if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL) != null) {
                BigDecimal salarioMensual = new BigDecimal(
                        (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL).toString());
                if ((salarioMensual.toString().length() > ArchivoMultipleCampoConstants.LONGITUD_9_CARACTERES)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL_MSG,
                            "Valor salario mensual debe de tener una longitud menor igual a "
                            + ArchivoMultipleCampoConstants.LONGITUD_9_CARACTERES + " caracteres"));
                } else {
                    if (!(salarioMensual.longValue() >= ArchivoMultipleCampoConstants.VALOR_MINIMO_SALARIO
                            && salarioMensual.longValue() <= ArchivoMultipleCampoConstants.VALOR_MAXIMO_SALARIO)) {
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL_MSG,
                                "El valor del salario no esta entre " + ArchivoMultipleCampoConstants.VALOR_MINIMO_SALARIO + " y "
                                + ArchivoMultipleCampoConstants.VALOR_MAXIMO_SALARIO));
                    }
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL_MSG,
                    " Valor perteneciente " + ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL_MSG
                    + " invalido. Únicamente debe contener caracteres numéricos"));
        }
    }

    /**
     * Metodo encargado de verificar el numero de documento respecto a tipo de
     * de documento
     *
     * @param tipoIdentificacion tipo de identificación
     * @param arguments, argumentos
     */
    public void verificarNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, String constanteNumeroIdentificacion, String msgNumIdentificacion, LineArgumentDTO arguments) {

        if (tipoIdentificacion != null) {
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)) {
                validarRegex(arguments, constanteNumeroIdentificacion, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                        "La cédula de ciudadanía deber tener 3, 4, 5, 8 o 10 dígitos.",
                        msgNumIdentificacion);
                return;
            }
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)) {
                validarRegex(arguments, constanteNumeroIdentificacion,
                        ExpresionesRegularesConstants.CEDULA_EXTRANJERIA, "La cédula de extranjería debe tener máximo 16 caracteres.",
                        msgNumIdentificacion);
                return;
            }
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.TARJETA_IDENTIDAD)) {
                validarRegex(arguments, constanteNumeroIdentificacion, ExpresionesRegularesConstants.TARJETA_IDENTIDAD,
                        "La tarjeta de identidad solo puede ser de 10 u 11 dígitos.",
                        msgNumIdentificacion);
                return;
            }
        } else {
            validarRegex(arguments, constanteNumeroIdentificacion, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                    "El número de identificación debe de tener un valor valido", msgNumIdentificacion);

        }
    }

    /**
     * Validador de campo aplicando una expresión regular.
     *
     * @param arguments
     * @param campoVal
     * @param regex
     * @param mensaje
     */
    private void validarRegex(LineArgumentDTO arguments, String campoVal, String regex, String mensaje, String campoMSG) {
        try {
            ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
            String valorCampo = ((String) (arguments.getLineValues()).get(campoVal)).trim();

            if (valorCampo != null && !(valorCampo.matches(regex))) {
                hallazgo = crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje);

            }
            if (hallazgo != null) {
                lstHallazgos.add(hallazgo);
                hallazgo = null;
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, " Valor perteneciente " + campoMSG + " invalido"));
        }
    }

    /**
     * Metodo encargado de verifiar el estado del afiliado respecto al empleador
     *
     * @param arguments
     * @param line
     */
    private void verificarEmpleadoresRelacionadosAfiliado(LineArgumentDTO arguments,
            TipoIdentificacionEnum tipoIdentificacionEmpleador, TipoIdentificacionEnum tipoIdentificacionAfiliado) {

        String numeroIdEmpleador = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_ORIGEN);
        String numeroIdAfiliado = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);

        try {
            List<EmpleadorRelacionadoAfiliadoDTO> resultadosEmplAfil = consultarEmpleadoresRelacionadosAfiliado(
                    tipoIdentificacionEmpleador, numeroIdEmpleador, tipoIdentificacionAfiliado, numeroIdAfiliado);

            if (CollectionUtils.isEmpty(resultadosEmplAfil)) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "No se encontró relación entre el trabajador y el empleador."));
            } else {
                EmpleadorRelacionadoAfiliadoDTO afiliadoDTO = resultadosEmplAfil.get(0);

                if (!afiliadoDTO.getEstadoEmpleador().equals(EstadoEmpleadorEnum.ACTIVO)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_MSG,
                            "El estado del empleador no es ACTIVO."));
                }
                if (!afiliadoDTO.getEstadoAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                            "El estado del afiliado no es ACTIVO."));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                    "No se encontró relación entre el trabajador y el empleador: "
                    + "tipoIdentificacionEmpleador: " + tipoIdentificacionEmpleador + ", numeroIdEmpleador: " + numeroIdEmpleador
                    + " tipoIdentificacionAfiliado: " + tipoIdentificacionAfiliado + ", numeroIdAfiliado: " + numeroIdAfiliado
                    + e.getMessage() + " Cause: " + e.getCause()));
        }
    }

    private List<EmpleadorRelacionadoAfiliadoDTO> consultarEmpleadoresRelacionadosAfiliado(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {
        ObtenerEmpleadoresRelacionadosAfiliado obtenerEmpleadoresRelacionadosSrv = new ObtenerEmpleadoresRelacionadosAfiliado(
                tipoIdEmpleador, tipoIdAfiliado, numeroIdEmpleador, null, numeroIdAfiliado);
        obtenerEmpleadoresRelacionadosSrv.execute();
        return obtenerEmpleadoresRelacionadosSrv.getResult();
    }

    private InfoAfiliadoRespectoEmpleadorDTO obtenerInfoAfiliadoRespectoEmpleador(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {

        ObtenerInfoAfiliadoRespectoEmpleador afiliadoRespectoEmpleadorSrv
                = new ObtenerInfoAfiliadoRespectoEmpleador(tipoIdEmpleador, tipoIdAfiliado, numeroIdEmpleador, numeroIdAfiliado);
        afiliadoRespectoEmpleadorSrv.execute();
        return afiliadoRespectoEmpleadorSrv.getResult();
    }

    private SucursalEmpresaModeloDTO consultarSucursalPorCodigo(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            String codigoSucursal) {

        ObtenerSucursalEmpresaByCodigoAndEmpleador sucursalEmpresaByCodigoAndEmpleadorSrv
                = new ObtenerSucursalEmpresaByCodigoAndEmpleador(tipoIdEmpleador, codigoSucursal, numeroIdEmpleador);
        sucursalEmpresaByCodigoAndEmpleadorSrv.execute();
        return sucursalEmpresaByCodigoAndEmpleadorSrv.getResult();
    }

    private String printJsonMessage(Object object, String message) {
        String jsonString = "";
        try {
            //Creating the ObjectMapper object
            ObjectMapper mapper = new ObjectMapper();
            //Converting the Object to JSONString
            jsonString = mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
