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
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.empresas.clients.ObtenerSucursalEmpresaByCodigoAndEmpleador;
import com.asopagos.empresas.clients.ObtenerSucursalEmpresaByNombreAndEmpleador;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import java.math.BigDecimal;
import java.util.Arrays;
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
public class ActualizacionSucursalesTrabajadorLineValidator extends LineValidator {

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
    public ActualizacionSucursalesTrabajadorLineValidator() {
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
                    ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_100_CARACTERES, null,
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

            //Validacion relacion empleador afiliado
            if (tipoIdentificacionEmpleador != null && tipoIdentificacionAfiliado != null) {
                verificarEmpleadoresRelacionadosAfiliado(arguments, tipoIdentificacionEmpleador, tipoIdentificacionAfiliado);
                verificarEstadoSucursal(arguments, tipoIdentificacionEmpleador);
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

        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoIdentificacion", tipoIdentificacionAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numeroIdAfiliado);
        datosValidacion.put("tipoIdentificacionEmpleador", tipoIdentificacionEmpleador.toString());
        datosValidacion.put("numeroIdentificacionEmpleador", numeroIdEmpleador);
        datosValidacion.put("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        datosValidacion.put("tipoBeneficiario", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());

        try {
            ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                    TipoTransaccionEnum.TRASLADO_TRABAJADORES_ENTRE_SUCURSALES.name(), TipoTransaccionEnum.TRASLADO_TRABAJADORES_ENTRE_SUCURSALES.getProceso(),
                    ClasificacionEnum.PERSONA_JURIDICA.name(), 
                    datosValidacion);
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
    
    /**
     * Metodo encargado de verifiar el estado de la sucursal
     *
     * @param arguments
     * @param line
     */
    private void verificarEstadoSucursal(LineArgumentDTO arguments,
            TipoIdentificacionEnum tipoIdentificacionEmpleador) {

        String numeroIdEmpleador = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR);
        String codigoSucursal = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR);
        try {
            
//            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), 
//                        ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR_MSG,
//                        "No se encontró relación entre la sucursal y el empleador - prueba !!!"
//                                + "tipoIdentificacionEmpleador: "+tipoIdentificacionEmpleador+
//            "numeroIdEmpleador: " + numeroIdEmpleador+
//                    "codigoSucursal: "+codigoSucursal
//            ));
            
            SucursalEmpresaModeloDTO resultadosSucursalEmpleador = consultarEstadoSucursal(
                    tipoIdentificacionEmpleador, numeroIdEmpleador, codigoSucursal);

            if (resultadosSucursalEmpleador == null) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), 
                        ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR_MSG,
                        "No se encontró relación entre la sucursal y el empleador."));
            } else {
                if (!resultadosSucursalEmpleador.getEstadoSucursal().equals(EstadoActivoInactivoEnum.ACTIVO)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_MSG,
                            "El estado de la sucursal no es ACTIVO."));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                    "No se encontró relación entre la sucursal y el empleador: "
                    + "tipoIdentificacionEmpleador: " + tipoIdentificacionEmpleador + ", numeroIdEmpleador: " + numeroIdEmpleador
                    + " codigo sucursal: " + codigoSucursal
                    + e.getMessage() + " Cause: " + e.getCause()));
        }
    }

    private SucursalEmpresaModeloDTO consultarEstadoSucursal(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            String codigoSucursal) {
        
        ObtenerSucursalEmpresaByNombreAndEmpleador sucursalEmpresaByCodigoAndEmpleadorSrv =
                new ObtenerSucursalEmpresaByNombreAndEmpleador(tipoIdEmpleador, codigoSucursal, numeroIdEmpleador);
        sucursalEmpresaByCodigoAndEmpleadorSrv.execute();
        return sucursalEmpresaByCodigoAndEmpleadorSrv.getResult();
    }
}
