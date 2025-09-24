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
import com.asopagos.afiliados.clients.ConsultarBeneficiarios;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.afiliados.clients.ObtenerEmpleadoresRelacionadosAfiliado;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.validaciones.clients.ValidarPersonas;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import com.asopagos.afiliados.clients.ConsultarRolAfiliadoEspecifico;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import java.text.SimpleDateFormat;
import java.lang.NullPointerException;


/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de Reintegro Trabajadores<br/>
 * <b>Módulo:</b> Asopagos - HU-13-XXX<br/>
 *
 * @author <a href="mailto:maria.cuellar@eprocess.com.co">Maria Cuellar</a>
 */

public class ActualizacionReintegroAfiliadoLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
    
    /**
     * Calendar
     */
    private Calendar cInicioLabores = Calendar.getInstance();

    private final String CANAL_PILA = "PILA";
    
    private final String CANAL_PRESENCIAL = "PRESENCIAL";

    private final ILogger logger = LogManager.getLogger(ActualizacionReintegroAfiliadoLineValidator.class);
    
    /**
     * Método constructor de la clase
     */
    public ActualizacionReintegroAfiliadoLineValidator() {
        super();
        cInicioLabores.set(Calendar.YEAR, 1965);
        cInicioLabores.set(Calendar.MONTH, 1);
        cInicioLabores.set(Calendar.DAY_OF_YEAR, 1);
    }

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     * @param arguments
     * @throws FileProcessingException 
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        Map<String, Object> line = arguments.getLineValues();
        try {
            Long lineNumber = arguments.getLineNumber();
            Calendar sistema = Calendar.getInstance();
            TipoIdentificacionEnum tipoIdentificacionEmpleador=null;
            TipoIdentificacionEnum tipoIdentificacionAfiliado=null;
            
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
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_MSG,arguments);
            //No se valida el campo 3 - serial empleador
            
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
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,arguments);

            // Se valida el campo No 6 - Sucursal del empleador
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR,
                    ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_200_CARACTERES, null,
                    null, true, false);
            
            //Se valida el campo No 7 - Fecha de recepcion de documentos del trabajador 
            //  verificacionCampoFecha(arguments, line, sistema, ArchivoCampoNovedadConstante.FECHA_RECEPCION_DOCUMENTOS, 
            //          ArchivoCampoNovedadConstante.FECHA_INICIO_AFILIACION_MSG);

            // Se valida el campo No 8 Valor salario mensual
            verificarSalarioMensual(arguments);
            
            //Validaciones de negocio
            if(tipoIdentificacionEmpleador != null && tipoIdentificacionAfiliado != null){
                validarPersona(arguments,tipoIdentificacionEmpleador,tipoIdentificacionAfiliado);
                verificarEmpleadoresRelacionadosAfiliado(arguments,tipoIdentificacionEmpleador,tipoIdentificacionAfiliado);
//                validarBeneficiariosPersona(arguments, tipoIdentificacionEmpleador, tipoIdentificacionAfiliado);
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
                }
                else {
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
     * @param tipoIdentificacion
     *        tipo de identificación
     * @param arguments,
     *        argumentos
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
        }
        else {
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
    private void validarPersona(LineArgumentDTO arguments,
        TipoIdentificacionEnum tipoIdentificacionEmpleador,TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        
        String numeroIdEmpleador = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR);
        String numeroIdAfiliado = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);

        Map<String, String> datosValidacion = new HashMap<String, String>();
         ConsultarEmpleadorTipoNumero empleador = new ConsultarEmpleadorTipoNumero(numeroIdEmpleador, tipoIdentificacionEmpleador);
        empleador.execute();

        if(empleador.getResult() == null){
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "El empleador no se encuentra registrado en el sistema."));
            return;
        }



        datosValidacion.put("tipoIdentificacion", tipoIdentificacionAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numeroIdAfiliado);
        datosValidacion.put("tipoIdentificacionEmpleador",tipoIdentificacionEmpleador.toString());
        datosValidacion.put("numeroIdentificacionEmpleador", numeroIdEmpleador);
        datosValidacion.put("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        datosValidacion.put("tipoBeneficiario", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        datosValidacion.put("idEmpleador", Long.toString(empleador.getResult().getIdEmpleador()));

    

              

        try {
            
            ValidarPersonas validarPersona = new ValidarPersonas("121-104-1", ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
                        TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.toString(), datosValidacion);
            validarPersona.execute();
            List<ValidacionDTO> list = validarPersona.getResult();
            
            if(CollectionUtils.isNotEmpty(list)){
                List<ValidacionDTO> resultadoFilterValidacionList = list.stream().filter(iteValidacion -> iteValidacion.getResultado().
                        equals(ResultadoValidacionEnum.NO_APROBADA)).
                        collect(Collectors.toList());
                
                if(CollectionUtils.isNotEmpty(resultadoFilterValidacionList)){
                    for(ValidacionDTO iteValidacion : resultadoFilterValidacionList){
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        iteValidacion.getDetalle()));
                    }
                }
            }else{
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "El afiliado no cumple las reglas de validación para el reintegro."));
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "El afiliado no cumple las reglas de validación para el reintegro: "
                                + "tipoIdentificacionEmpleador: "+tipoIdentificacionEmpleador+", numeroIdEmpleador: "+numeroIdEmpleador
                                +" tipoIdentificacionAfiliado: "+tipoIdentificacionAfiliado+", numeroIdAfiliado: "+numeroIdAfiliado
                                +e.getMessage()+" Cause: "+e.getCause()));
        }
    }
    
    /**
     * Metodo encargado de verifiar si tiene beneficiarios para reintegrar
     * 
     * @param arguments
     * @param line
     */
    private void validarBeneficiariosPersona(LineArgumentDTO arguments,
        TipoIdentificacionEnum tipoIdentificacionEmpleador,TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        
        
        String numeroIdAfiliado = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
        String numeroIdEmpleador = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR);
        
        // se obtiene el ID de afiliado
        ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(numeroIdAfiliado, tipoIdentificacionAfiliado);
        consultarDatosAfiliado.execute();
        AfiliadoModeloDTO afiliado = consultarDatosAfiliado.getResult();
        
        if (afiliado != null) {
            // se consultan los beneficiarios asociados al afiliado
            ConsultarBeneficiarios consultaBeneficiarios = new ConsultarBeneficiarios(afiliado.getIdAfiliado(), false);
            consultaBeneficiarios.execute();
            List<BeneficiarioDTO> beneficiarios = consultaBeneficiarios.getResult();
            
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "afiliado.getIdAfiliado(): "+afiliado.getIdAfiliado()+
                        " afiliado.getFechaRetiroAfiliado(): " + afiliado.getFechaRetiroAfiliado()));
            
            if (beneficiarios != null && !beneficiarios.isEmpty()) {
                
                // se compara la fecha de retiro del beneficiario con la fecha
                // de retiro del afiliado principal
                LocalDate retiroAfiliado = afiliado.getFechaRetiroAfiliado() != null
                        ? new Date(afiliado.getFechaRetiroAfiliado()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        : null;
                
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "retiroAfiliado: "+retiroAfiliado));
                
                for (BeneficiarioDTO beneficiario : beneficiarios) {
                
                    LocalDate retiroBeneficiario = beneficiario.getFechaRetiro() != null
                            ? beneficiario.getFechaRetiro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            : null;
                    
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "beneficiario.getFechaRetiro(): "+beneficiario.getFechaRetiro()+
                                "retiroBeneficiario: "+retiroBeneficiario));
                    
                    // sí las fechas de retiro coinciden y pasa la malla de
                    // validación, se sólicita la actualización del beneficiario
                    ejecutarMallaValidacionBeneficiario(arguments, beneficiario, tipoIdentificacionAfiliado, numeroIdAfiliado,
                                    tipoIdentificacionEmpleador, numeroIdEmpleador);
                    
                    //Esta validacion se debe aplicar para activar a los beneficiarios (Reintegro afiliado)
//                    if ( retiroAfiliado != null && retiroBeneficiario != null
//                            && retiroBeneficiario.isEqual(retiroAfiliado)
//                            && ejecutarMallaValidacionBeneficiario(arguments, beneficiario, tipoIdentificacionAfiliado, numeroIdAfiliado,
//                                    tipoIdentificacionEmpleador, numeroIdEmpleador)) {
//                        
//                    }
                }
            }
        }
        
        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoIdentificacion", tipoIdentificacionAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numeroIdAfiliado);
        datosValidacion.put("tipoIdentificacionEmpleador",tipoIdentificacionEmpleador.toString());
        datosValidacion.put("numeroIdentificacionEmpleador", numeroIdEmpleador);
        datosValidacion.put("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        datosValidacion.put("tipoBeneficiario", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        
        try {
            
            ValidarPersonas validarPersona = new ValidarPersonas("121-104-1", ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
                        TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.toString(), datosValidacion);
            validarPersona.execute();
            List<ValidacionDTO> list = validarPersona.getResult();
            
            if(CollectionUtils.isNotEmpty(list)){
                List<ValidacionDTO> resultadoFilterValidacionList = list.stream().filter(iteValidacion -> iteValidacion.getResultado().
                        equals(ResultadoValidacionEnum.NO_APROBADA)).
                        collect(Collectors.toList());
                
                if(CollectionUtils.isNotEmpty(resultadoFilterValidacionList)){
                    for(ValidacionDTO iteValidacion : resultadoFilterValidacionList){
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        iteValidacion.getDetalle()));
                    }
                }
            }else{
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "El afiliado no cumple las reglas de validación para el reintegro."));
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "El afiliado no cumple las reglas de validación para el reintegro: "
                                + "tipoIdentificacionEmpleador: "+tipoIdentificacionEmpleador+", numeroIdEmpleador: "+numeroIdEmpleador
                                +" tipoIdentificacionAfiliado: "+tipoIdentificacionAfiliado+", numeroIdAfiliado: "+numeroIdAfiliado
                                +e.getMessage()+" Cause: "+e.getCause()));
        }
    }
    
    private List<EmpleadorRelacionadoAfiliadoDTO> consultarEmpleadoresRelacionadosAfiliado(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado){
        ObtenerEmpleadoresRelacionadosAfiliado obtenerEmpleadoresRelacionadosSrv = new ObtenerEmpleadoresRelacionadosAfiliado(
                tipoIdEmpleador, tipoIdAfiliado, numeroIdEmpleador, null, numeroIdAfiliado);
        obtenerEmpleadoresRelacionadosSrv.execute();
        return obtenerEmpleadoresRelacionadosSrv.getResult();
    }
    
    /**
     * Método encargado de ejecutar la malla de validaciones para el reintegro de
     * un beneficiario
     * 
     * @param beneficiario
     * @param tipoIdAfiliado
     * @param numIdAfiliado
     * @return
     */
    private Boolean ejecutarMallaValidacionBeneficiario(LineArgumentDTO arguments, BeneficiarioDTO beneficiario,
            TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {
        String firmaMetodo = "AportesNovedadesBusiness.ejecutarMayaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";

        TipoTransaccionEnum bloque = calcularTipoTransaccion(beneficiario);

        if (bloque == null) {            
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG, "El beneficiario "
                        + beneficiario.getPersona().getTipoIdentificacion().getValorEnPILA()
                        + beneficiario.getPersona().getNumeroIdentificacion() + ", no aprobó la malla de validación - "));
            
        }

        // se diligencia el mapa de datos
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionBeneficiario",
                beneficiario.getPersona().getTipoIdentificacion().name());
        datosValidacion.put("numeroIdentificacionBeneficiario",
                beneficiario.getPersona().getNumeroIdentificacion());
        datosValidacion.put("primerNombre", beneficiario.getPersona().getPrimerNombre());
        datosValidacion.put("segundoNombre", beneficiario.getPersona().getSegundoNombre());
        datosValidacion.put("primerApellido",
                beneficiario.getPersona().getPrimerApellido());
        datosValidacion.put("segundoApellido",
                beneficiario.getPersona().getSegundoApellido());
        datosValidacion.put("fechaNacimiento", 
                beneficiario.getPersona().getFechaNacimiento().toString());
        datosValidacion.put("tipoIdentificacionAfiliado", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacionAfiliado", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionEmpleador", tipoIdEmpleador.name());
        datosValidacion.put("tipoIdentificacionEmpleador", numeroIdEmpleador);
        
        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG, 
                "datosValidacion " + datosValidacion));
        
        ValidarReglasNegocio validador = new ValidarReglasNegocio(bloque.name().replace(CANAL_PRESENCIAL, CANAL_PILA), bloque.getProceso(),
                beneficiario.getTipoBeneficiario().name(), datosValidacion);
        validador.execute();
        List<ValidacionDTO> resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG, 
                "validacion.getTipoExcepcion(): " + validacion.getTipoExcepcion()+
                        " validacion.getDetalle(): " + validacion.getDetalle() + 
                        " validacion.getResultado()" + validacion.getResultado() + 
                        " validacion.getBloque()" + validacion.getBloque()));
            
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {

                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG, "El beneficiario "
                        + beneficiario.getPersona().getTipoIdentificacion().getValorEnPILA()
                        + beneficiario.getPersona().getNumeroIdentificacion() + ", no aprobó la malla de validación - "
                +validacion.getDetalle()));
                
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }
    
    private TipoTransaccionEnum calcularTipoTransaccion(BeneficiarioDTO beneficiario) {
        TipoTransaccionEnum bloque = null;
        switch (beneficiario.getTipoBeneficiario()) {
        case BENEFICIARIO_EN_CUSTODIA:
            bloque = TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL;
            break;
        case CONYUGE:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL;
            break;
        case HERMANO_HUERFANO_DE_PADRES:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL;
            break;
        case HIJASTRO:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL;
            break;
        case HIJO_ADOPTIVO:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL;
            break;
        case HIJO_BIOLOGICO:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL;
            break;
        case MADRE:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL;
            break;
        case PADRE:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL;
            break;
        default:
            break;

        }
        return bloque;
    }
    
    /**
     * Metodo encargado de verifiar el estado del afiliado respecto al empleador
     * 
     * @param arguments
     * @param line
     */
    private void verificarEmpleadoresRelacionadosAfiliado(LineArgumentDTO arguments,
        TipoIdentificacionEnum tipoIdentificacionEmpleador,TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        
        String numeroIdEmpleador = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR);
        String numeroIdAfiliado = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
        String fechaRecepcionDocumento = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.FECHA_RECEPCION_DOCUMENTOS);

        System.out.println("numeroIdEmpleador--" + numeroIdEmpleador);
        System.out.println("numeroIdAfiliado--" + numeroIdAfiliado);
        System.out.println("fechaRecepcionDocumento--" + fechaRecepcionDocumento);
        System.out.println("tipoIdentificacionEmpleador--" + tipoIdentificacionEmpleador.name());

        try {
            System.out.println("Ya empezo el try **__**");
            List<EmpleadorRelacionadoAfiliadoDTO> resultadosEmplAfil = consultarEmpleadoresRelacionadosAfiliado(                        
                        tipoIdentificacionEmpleador, numeroIdEmpleador, tipoIdentificacionAfiliado, numeroIdAfiliado);
            System.out.println("resultadosEmplAfil **__**" + resultadosEmplAfil.get(0).getNumeroIdentificacion());
            if(CollectionUtils.isEmpty(resultadosEmplAfil)){
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "No se encontró relación entre el trabajador y el empleador."));
            System.out.println("No hay resultados **__**");
            }else{
                EmpleadorRelacionadoAfiliadoDTO afiliadoDTO = resultadosEmplAfil.get(0);
                System.out.println("ELSE **__**");
                
                if(!afiliadoDTO.getEstadoEmpleador().equals(EstadoEmpleadorEnum.ACTIVO)){
                    System.out.println("El estado del empleador no es ACTIVO **__**");
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_MSG,
                        "El estado del empleador no es ACTIVO."));
                }
                if(afiliadoDTO.getEstadoAfiliado().equals(EstadoAfiliadoEnum.ACTIVO) || afiliadoDTO.getEstadoAfiliado().equals(EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION)
                || afiliadoDTO.getEstadoAfiliado().equals(EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES)){
                    System.out.println("El estado del afiliado no es valido. **__**");
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "El estado del afiliado no es valido."));
                }else{
                    System.out.println("ELSE DEL ELSE**__**");
                    EmpleadorModeloDTO empleadorDTO = null;
                    RolAfiliadoModeloDTO rolAfiliadoDTO = null;
                    System.out.println("consultar empleador" + numeroIdEmpleador + tipoIdentificacionEmpleador);
                    ConsultarEmpleadorTipoNumero empleador = new ConsultarEmpleadorTipoNumero(numeroIdEmpleador, tipoIdentificacionEmpleador);
                    empleador.execute();
                    System.out.println("Empleador **__**" + empleador.getResult());

                    ConsultarRolAfiliadoEspecifico rolAfiliado = null;
                    if (empleador.getResult() != null) {
                        System.out.println("consultar empleador" + empleador.getResult());
                        empleadorDTO = empleador.getResult();
                        rolAfiliado = new ConsultarRolAfiliadoEspecifico(empleadorDTO.getIdEmpleador(), TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,numeroIdAfiliado, tipoIdentificacionAfiliado);
                        rolAfiliado.execute();
                        System.out.println("rolAfiliado **__**" + rolAfiliado.getResult());
                    }
                    if(rolAfiliado  != null && rolAfiliado.getResult() != null){
                        rolAfiliadoDTO = rolAfiliado.getResult();
                        Date date1 = new Date();
                        System.out.println(date1.getTime());
                        try {
                            date1=new SimpleDateFormat("yyyy-MM-dd").parse(fechaRecepcionDocumento);  
                        } catch (Exception e) {
                            System.out.println("Este es el catch");
                        }
                        System.out.println(date1.getTime() < (new Date(rolAfiliadoDTO.getFechaRetiro()).getTime()));
                        System.out.println( date1.getTime() > (new Date().getTime()));
                        System.out.println((new Date().getTime()));
                        System.out.println(new Date(rolAfiliadoDTO.getFechaRetiro()).getTime());


                        if(date1.getTime() < (new Date(rolAfiliadoDTO.getFechaRetiro()).getTime()) || date1.getTime() > (new Date().getTime())){
                            System.out.println("La fecha de recepción de documentos no puede ser menor a la retiro del afiliado o mayor a la fecha actual");
                            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.FECHA_RECEPCION_DOCUMENTOS_MSG, "La fecha de recepción de documentos no puede ser menor a la retiro del afiliado o mayor a la fecha actual"));
                        }
                    }  
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                                "No se encontró relación entre el trabajador y el empleador: "
                                + "tipoIdentificacionEmpleador: "+tipoIdentificacionEmpleador+", numeroIdEmpleador: "+numeroIdEmpleador
                                +" tipoIdentificacionAfiliado: "+tipoIdentificacionAfiliado+", numeroIdAfiliado: "+numeroIdAfiliado
                                +e.getMessage()+" Cause: "+e.getCause()));
        }
    }
    
}