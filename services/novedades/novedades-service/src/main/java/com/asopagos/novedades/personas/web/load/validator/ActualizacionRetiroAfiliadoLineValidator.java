package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.afiliados.clients.ObtenerEmpleadoresRelacionadosAfiliado;
import com.asopagos.afiliados.clients.ConsultarRolAfiliadoEmpresa;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.validaciones.clients.ValidarPersonas;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de Retiro
 * Trabajadores<br/>
 * <b>Módulo:</b> Asopagos - HU-13-XXX<br/>
 *
 * @author <a href="mailto:maria.cuellar@eprocess.com.co">Maria Cuellar</a>
 */
public class ActualizacionRetiroAfiliadoLineValidator extends LineValidator {

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
    public ActualizacionRetiroAfiliadoLineValidator() {
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
            TipoIdentificacionEnum tipoIdentificacionEmpleador = null;
            TipoIdentificacionEnum tipoIdentificacionAfiliado = null;

            System.out.println("Iniciando la validación de la línea #: " + arguments.getLineNumber() + " - " + "Contenidos: " +  line.toString());
            // Se valida el campo No 1 - Tipo identificacion empleador
            try {
                tipoIdentificacionEmpleador = GetValueUtil
                        .getTipoIdentificacionByPila(((String) line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR)));
                if (tipoIdentificacionEmpleador == null) {
                    throw new Exception("Invalido");
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_MSG,
                        "Tipo de identificación invalido"));
                tipoIdentificacionEmpleador = null;
            }

            // Se valida el campo No 2 - Número de identificación del empleador
            verificarNumeroDocumento(tipoIdentificacionEmpleador, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_MSG, arguments);
            // Se valida el campo No 3 - Sucursal del empleador
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR,
                    ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES, null,
                    null, true, false);
            // Se valida el campo No 4 - Tipo de identificación del trabajador o cotizante o cabeza de familia
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

            // Se valida el campo No 5 - Número de identificación del trabajador o cotizante o cabeza de familia 
            verificarNumeroDocumento(tipoIdentificacionAfiliado, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG, arguments);

            // Se valida el campo No 6 - Fecha inicio de labores
            verificacionCampoFecha(arguments, line, sistema, ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR,
                    ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG);
            // Se valida el campo No 7 - Fecha de afiliación del trabajador 
            verificacionCampoFecha(arguments, line, sistema, ArchivoCampoNovedadConstante.FECHA_INICIO_AFILIACION,
                    ArchivoCampoNovedadConstante.FECHA_INICIO_AFILIACION_MSG);
            // Se valida el campo No 8 Valor salario mensual
            verificarSalarioMensual(arguments);
            // Se valida el campo No 9 - Fecha de retiro
            verificacionCampoFecha(arguments, line, sistema, ArchivoCampoNovedadConstante.FECHA_RETIRO_AFILIADO,
                    ArchivoCampoNovedadConstante.FECHA_RETIRO_AFILIADO_MSG);
            // Se valida el campo No 10 - Motivo de retiro
            try {
                MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionTrabajador = GetValueUtil
                        .getTipoMotivoDesafiliacionAfiliado(((String) line.get(ArchivoCampoNovedadConstante.MOTIVO_RETIRO_AFILIADO)));
                if (motivoDesafiliacionTrabajador == null) {
                    throw new Exception("Invalido");
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.MOTIVO_RETIRO_AFILIADO_MSG,
                        "Motivo retiro beneficiario invalido"));
                tipoIdentificacionAfiliado = null;
            }
//            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.MOTIVO_RETIRO_AFILIADO,
//                    ArchivoCampoNovedadConstante.MOTIVO_RETIRO_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES, null,
//                    null, true, false);
            //Validacion relacion empleador afiliado
            if (tipoIdentificacionEmpleador != null && tipoIdentificacionAfiliado != null) {
                verificarEmpleadoresRelacionadosAfiliado(arguments, tipoIdentificacionEmpleador, tipoIdentificacionAfiliado);
                validarNovedad(arguments, tipoIdentificacionEmpleador, tipoIdentificacionAfiliado);
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
     * @param line
     */
    private void validarNovedad(LineArgumentDTO arguments,
            TipoIdentificacionEnum tipoIdentificacionEmpleador, TipoIdentificacionEnum tipoIdentificacionAfiliado) {

        String numeroIdEmpleador = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR);
        String numeroIdAfiliado = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
        String fechaRetiro = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.FECHA_RETIRO_AFILIADO);

        System.out.println(fechaRetiro+ " "+numeroIdAfiliado+ " "+numeroIdEmpleador);
        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd");
        try {
        System.out.println(formatter2.parse(fechaRetiro));
        System.out.println(formatter2.parse(fechaRetiro).getTime());
        System.out.println(Long.toString(formatter2.parse(fechaRetiro).getTime()));
    } catch (Exception e) {
        System.out.println(e);

    }


        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoIdentificacion", tipoIdentificacionAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numeroIdAfiliado);
        datosValidacion.put("tipoIdentificacionEmpleador", tipoIdentificacionEmpleador.toString());
        datosValidacion.put("numeroIdentificacionEmpleador", numeroIdEmpleador);
        datosValidacion.put("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        datosValidacion.put("tipoBeneficiario", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        try {
        datosValidacion.put("fechaRetiro", Long.toString(formatter2.parse(fechaRetiro).getTime()));
    } catch (Exception e) {
        e.printStackTrace();
    }

        System.out.println(tipoIdentificacionAfiliado+ " "+numeroIdAfiliado+ " "+tipoIdentificacionEmpleador+ " "+numeroIdEmpleador);

        ConsultarRolAfiliadoEmpresa consultarRolAfiliadoEmpresa = new ConsultarRolAfiliadoEmpresa(tipoIdentificacionAfiliado,
                numeroIdAfiliado, tipoIdentificacionEmpleador, numeroIdEmpleador);

        consultarRolAfiliadoEmpresa.execute();

        System.out.println(consultarRolAfiliadoEmpresa.getResult());
        datosValidacion.put("idRolAfiliado", String.valueOf(consultarRolAfiliadoEmpresa.getResult()));




        try {
           System.out.println("Validando reglas de negocio");
           System.out.println("=====================================================");
            ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                    TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.name(), TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.getProceso(),
                    ClasificacionEnum.TRABAJADOR_DEPENDIENTE.name(), datosValidacion);
            validarReglasService.execute();
            List<ValidacionDTO> list = (List<ValidacionDTO>) validarReglasService.getResult();

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
                    + "tipoIdentificacionEmpleador: " + tipoIdentificacionEmpleador + ", numeroIdEmpleador: " + numeroIdEmpleador
                    + " tipoIdentificacionAfiliado: " + tipoIdentificacionAfiliado + ", numeroIdAfiliado: " + numeroIdAfiliado
                    + e.getMessage() + " Cause: " + e.getCause()));
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
                if (CalendarUtils.esFechaMayor(fInicioLabores, sistema.getTime())) {
                    lstHallazgos.add(
                            crearHallazgo(arguments.getLineNumber(), campoFechaMsg,
                                    "La fecha es mayor a la fecha actual"));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoFechaMsg,
                    " Digite la " + campoFechaMsg
                    + " en formato año - mes - dia. Ejemplo: 1990-10-02"));
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

        String numeroIdEmpleador = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR);
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
}
