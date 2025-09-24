package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.clients.ValidacionesRegistroArchConfirmAbonosBancario;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.validaciones.clients.ValidarPersonas;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

import com.asopagos.locator.ResourceLocator;
import com.asopagos.novedades.business.interfaces.EntityManagerProceduresPersistenceLocal;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.rest.exception.TechnicalException;
/** 
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de Confirmacion abonos bancarios.<br/>
 * <b>Módulo:</b> Asopagos - HU-13-XXX<br/>
 *
 * @author <a href="mailto:maria.cuellar@eprocess.com.co">Maria Cuellar</a>
 */

public class ConfirmacionAbonosBancariosLineValidator extends LineValidator {

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
    
    /**
     * Método constructor de la clase
     */
    public ConfirmacionAbonosBancariosLineValidator() {
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
            
            


            // Val 1: Se valida el campo No 1 - Tipo identificacion admin sub sea un valor permitido por la plataforma
            if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO) != null) {
                try {
                    tipoIdentificacionEmpleador = GetValueUtil
                            .getTipoIdentificacionByPila(((String) line.get(ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO)));
                    if (tipoIdentificacionEmpleador == null) {
                         throw new Exception("Invalido");
                    }
                } catch (Exception e) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO,ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_ADMIN_SUB_MSG));
                    tipoIdentificacionEmpleador = null;
               }
            }

         /*   lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO,
                    ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_ADMIN_SUB_MSG + 
                            "ESTE VALOR CORRESPONDE AL TIPO ID: " + (String) line.get(ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO)));
          */  
                
            // Val 2: Se valida el campo No 2 - Número de identificación del admin sub sea un valor permitido por la plataforma
            verificarNumeroDocumento(tipoIdentificacionEmpleador, ArchivoCampoNovedadConstante.NUM_ID_ADMIN_SUBSIDIO, 
            ArchivoCampoNovedadConstante.NUM_IDENTIFICACION_ADMIN_SUB_MSG,arguments);
            // Val 8: Se valida que el campo No Tipo cuenta no contenga un valor diferente a 1 ,2 o 3 
            verificarTipoCuenta(arguments);
            // Val 12: Se valida que el campo No Motivo este diligenciado únicamente si en el campo ‘resultado’ esta como ‘ABONO NO EXITOSO’
            //verificarMotivo(arguments);
            // Val 13: Se valida que el campo No fecha y hora confirmacion este diligenciado y cumpla con la estructura definida
            validarFechaFormato(arguments);
            // Val 11: Se valida que el campo No Resultado no contenga un valor diferente a “ABONO EXITOSO” O “ABONO NO EXITOSO”
            verificarResultado(arguments);



            
            // Se crea un pool de peticiones las cuales realizen la ejecucion de las validaciones concurrentemente
            ExecutorService executor = Executors.newFixedThreadPool(8);
            Collection<Callable<ResultadoHallazgosValidacionArchivoDTO>> callables = new ArrayList();

            String casId = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.ID_TRANSACCION_CUENTA_ADMIN_SUBSIDIO);
            String tipoIdenAdminSubsidio = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO) == null ? "" : (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO) ;

            EntityManager em = obtenerEntityCore();

            String tipoIdentificacionFinal = "";
            if (tipoIdentificacionFinal.equals("") && tipoIdenAdminSubsidio.equals("")) {
                tipoIdentificacionFinal = obtenerTitularCuentaAdministradorSubsidioByCasId(casId, em)[0].toString();
            } else {
                TipoIdentificacionEnum tipoIdentificacionAdminEnum = GetValueUtil.getTipoIdentificacionByPila(tipoIdenAdminSubsidio);
                if (tipoIdentificacionAdminEnum == null) {
                    tipoIdentificacionFinal = tipoIdenAdminSubsidio;
                } else {
                    tipoIdentificacionFinal = tipoIdentificacionAdminEnum.name();
                }
            }

            final String tipoIdentificacionFinalf = tipoIdentificacionFinal.toString();

            
            // Val 3: Se valida el campo No 1 Tipo Iden - No 2 Num iden del admin sub corresponda a una persona que es o fue administrador de subsidio en Genesys
            callables.add(() -> {validacionConfirmacionAbonos(arguments,"3", tipoIdentificacionFinalf, em); return null;});
            // Val 4: Se valida que el campo No 3 - casId corresponda a una persona que es o fue administrador de subsidio en Genesys. 
            callables.add(() -> {validacionConfirmacionAbonos(arguments,"4", tipoIdentificacionFinalf, em); return null;});
            // Val 5: Se valida que el campo No 3 - casId este relacionado a un “Medio de pago” igual a “TRANSFERENCIA” con estado de la transacción igual a “ENVIADO”
            callables.add(() -> {validacionConfirmacionAbonos(arguments,"5", tipoIdentificacionFinalf, em); return null;});
            // Val 6: Se valida que el campo No 1 tipo Iden - No 2 Num iden este asociado al administrador de subsidio
            callables.add(() -> {validacionConfirmacionAbonos(arguments,"6", tipoIdentificacionFinalf, em); return null;});
            // Val 7 : Se valida que el campo No 1 Tipo iden - No 2 Num iden sea el mismo que el del administrador de subsidio 
            callables.add(() -> {validacionConfirmacionAbonos(arguments,"7", tipoIdentificacionFinalf, em); return null;});
            // Val 8.1: Se valida que el campo No tipo cuenta este asociado al titular de la cuenta
            callables.add(() -> {validacionConfirmacionAbonos(arguments,"8", tipoIdentificacionFinalf, em); return null;});
            // Val 9: Se valida que el campo No Numero cuenta exista en Genesys y este asociado al titular de la cuenta
            callables.add(() -> {validacionConfirmacionAbonos(arguments,"9", tipoIdentificacionFinalf, em); return null;});
            // Val 10: Se valida que el campo No Valor transferencia sea igual al “valor real de la transacción” del abono que existe en Genesys
            callables.add(() -> {validacionConfirmacionAbonos(arguments,"10", tipoIdentificacionFinalf, em); return null;});

            /*
             * Prueba
            for (Future<ResultadoHallazgosValidacionArchivoDTO> res : executor.invokeAll(callables)) {
                if (res.get() != null) {
                    lstHallazgos.add(res.get());
                }
            }

            executor.shutdown();



        } catch(InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error en ejecucion" + e.getMessage());
        } catch(ExecutionException ex) {
            ex.printStackTrace();
            System.out.println("Error en ejecucion" + ex.getMessage());
        }
        */
        }
        finally {
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
        
        lstHallazgos.add(
                            crearHallazgo(arguments.getLineNumber(), "",
                                    "errorrrr: " + " : " + line.get(campoFecha)));
        
        
        try {
            if (line.get(campoFecha) != null) {
                strFInicioLabores = line.get(campoFecha).toString();

                fInicioLabores = CalendarUtils.darFormatoYYYYMMDDGuionDate(strFInicioLabores);
                
                   lstHallazgos.add(
                            crearHallazgo(arguments.getLineNumber(), "strFInicioLabores: "+ strFInicioLabores + " : " + fInicioLabores,
                                    ""));
                
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
    
    private List<EmpleadorRelacionadoAfiliadoDTO> consultarEmpleadoresRelacionadosAfiliado(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado){
        ObtenerEmpleadoresRelacionadosAfiliado obtenerEmpleadoresRelacionadosSrv = new ObtenerEmpleadoresRelacionadosAfiliado(
                tipoIdEmpleador, tipoIdAfiliado, numeroIdEmpleador, null, numeroIdAfiliado);
        obtenerEmpleadoresRelacionadosSrv.execute();
        return obtenerEmpleadoresRelacionadosSrv.getResult();
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
    
    //Metodos nuevos 
    
    /**
     * Metodo encargado de verifiar que el Tipo cuenta no contenga un valor diferente a 1 ,2 o 3 
     * 
     * @param arguments
     * @param line
    */
    private void verificarTipoCuenta(LineArgumentDTO arguments) {
        try {
            if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TIPO_CUENTA) != null) {
                
                String TipoCuenta = (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TIPO_CUENTA).toString();
                
                if (!TipoCuenta.equals("1") && !TipoCuenta.equals("2") && !TipoCuenta.equals("3")) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_CUENTA,ArchivoCampoNovedadConstante.TIPO_CUENTA_INCORRECTO_MSG));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_CUENTA,ArchivoCampoNovedadConstante.TIPO_CUENTA_INCORRECTO_MSG));
        }
    }
    
    /**
     * Metodo encargado de verifiar que el resultado no contenga un valor diferente a “ABONO EXITOSO” O “ABONO NO EXITOSO”
     * 
     * @param arguments
     * @param line
    */
    private void verificarResultado(LineArgumentDTO arguments) {
        try {
            if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.RESULTADO_ABONO) != null) {
                
                String resultado = (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.RESULTADO_ABONO).toString();
                
                if (!resultado.equals("ABONO EXITOSO") && !resultado.equals("ABONO NO EXITOSO")) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.RESULTADO_ABONO,ArchivoCampoNovedadConstante.RESULTADO_INCORRECTO_MSG));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.RESULTADO_ABONO,ArchivoCampoNovedadConstante.RESULTADO_INCORRECTO_MSG));
        }
    }
    
    /**
     * Metodo encargado de verifiar que el Motivo este diligenciado únicamente si en el campo ‘resultado’ esta como ‘ABONO NO EXITOSO’
     * 
     * @param arguments
     * @param line
    */
    private void verificarMotivo(LineArgumentDTO arguments) {
        try {
            if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.MOTIVO) != null && (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.RESULTADO_ABONO) != null) {
                
                String resultado = (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.RESULTADO_ABONO).toString();
                String motivo = (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.MOTIVO).toString();
                
               // lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.MOTIVO,"VALORES: " + resultado + " : " + motivo ));
                
                    
                if ((!motivo.equals("") && !motivo.isEmpty()) && resultado.equals("ABONO EXITOSO")) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.MOTIVO,ArchivoCampoNovedadConstante.MOTIVO_RESULTADO_EXITOSO_ERRONEO_MSG));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.MOTIVO,"El motivo se encuentra vacio"));
        }
    }
    
 
    
    
       /**
     * Metodo encargado de verifiar el estado del afiliado respecto al empleador
     * 
     * @param arguments
     * @param line
     */
    private ResultadoHallazgosValidacionArchivoDTO  validacionConfirmacionAbonos(LineArgumentDTO arguments, String idValidacion, String tipoIdAdminSubsidio, EntityManager em) {
        
        
        
        RespuestaValidacionArchivoDTO aplicarValidacion = new RespuestaValidacionArchivoDTO();        
        String tipoIdenAdminSubsidio = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO) == null ? "" : (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO) ;
        String numeroIdenAdminSubsidio = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUM_ID_ADMIN_SUBSIDIO);
        String casId = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.ID_TRANSACCION_CUENTA_ADMIN_SUBSIDIO);
        String tipoCuenta = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.TIPO_CUENTA);
        String numeroCuenta = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_CUENTA);
        String valorTransferencia = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.VALOR_TRANSFERENCIA);
        String resultadoAbono = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.RESULTADO_ABONO);
        String motivoRechazoAbono = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.MOTIVO) == null ? "" : (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.MOTIVO);
        String fechaConfirmacionAbono = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.FECHA_HORA_CONFIRMACION);
                    
        
        aplicarValidacion = validacionesRegistroArchConfirmAbonosBancario(tipoIdAdminSubsidio,  numeroIdenAdminSubsidio,  casId,  tipoCuenta,  numeroCuenta,  valorTransferencia, resultadoAbono,  motivoRechazoAbono,  fechaConfirmacionAbono,  idValidacion, em);

        
        if(aplicarValidacion.getStatus().equals("KO")){
            if(idValidacion.equals("3")){
                return crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUM_ID_ADMIN_SUBSIDIO,ArchivoCampoNovedadConstante.ADMIN_SUB_NO_EXISTE_MSG);
            }else if(idValidacion.equals("4")){
                return crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.ID_TRANSACCION_CUENTA_ADMIN_SUBSIDIO,ArchivoCampoNovedadConstante.IDEN_TRANSACCION_MSG);
            }else if(idValidacion.equals("5")){
                return crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.ID_TRANSACCION_CUENTA_ADMIN_SUBSIDIO,ArchivoCampoNovedadConstante.MEDIO_PAGO_NO_RELACIONADO_MSG);
            }else if(idValidacion.equals("6")){
                return crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUM_ID_ADMIN_SUBSIDIO,ArchivoCampoNovedadConstante.TITULAR_CUENTA_NO_RELACIONADO_MSG);
            }else if(idValidacion.equals("7")){
                return crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUM_ID_ADMIN_SUBSIDIO,ArchivoCampoNovedadConstante.TITULAR_CUENTA_DIFERENTE_MSG);
            }else if(idValidacion.equals("8")){
                return crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_CUENTA,ArchivoCampoNovedadConstante.TIPO_CUENTA_INCORRECTO_MSG);
            }else if(idValidacion.equals("9")){
                return crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_CUENTA,ArchivoCampoNovedadConstante.NUMERO_CUENTA_NO_EXISTE_MSG);
            }else if(idValidacion.equals("10")){
                return crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.VALOR_TRANSFERENCIA,ArchivoCampoNovedadConstante.VALOR_TRANSFERENCIA_INCORRECTO_MSG);
            }
        }
        return null;
    
    }
    
    private void validarFechaFormato(LineArgumentDTO arguments) {
        try {
            if ((arguments.getLineValues()).get(ArchivoCampoNovedadConstante.FECHA_HORA_CONFIRMACION) != null) {
                
                String fecha = (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.FECHA_HORA_CONFIRMACION).toString();
                
              // Define el patrón a utilizar para validar la fecha
                String pattern = "yyyy-MM-dd HH:mm:ss";
                
                // se crea un objeto SimpleDateFormat usando el patrón especificado
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                
                // se establece la opción de lenient a false para que el método parse sea más estricto al validar la fecha
                simpleDateFormat.setLenient(false);
                
                // Intenta parsear la fecha con el patrón especificado
                    try {
                        // Intenta parsear la fecha con el patrón especificado
                        Date date = simpleDateFormat.parse(fecha);

                     } catch (Exception e) {
                        // Si ocurre una excepción al parsear la fecha, entonces es porque no tiene el formato correcto
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.FECHA_HORA_CONFIRMACION,ArchivoCampoNovedadConstante.FECHA_CONFIRMACION_ERRONERO_MSG));
                     }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.FECHA_HORA_CONFIRMACION,"El campo fecha esta vacio."));
        }
    }

    private EntityManager obtenerEntityCore() {
        EntityManagerProceduresPersistenceLocal em = ResourceLocator.lookupEJBReference(EntityManagerProceduresPersistenceLocal.class);
        return em.getEntityManager();
    }


    private RespuestaValidacionArchivoDTO validacionesRegistroArchConfirmAbonosBancario(String tipoIdentificacionFinal,
                                                                                       String numeroIdenAdminSubsidio, String casId, String tipoCuenta, String numeroCuenta,
                                                                                       String valorTransferencia, String resultadoAbono, String motivoRechazoAbono, String fechaConfirmacionAbono,
                                                                                       String idValidacion, EntityManager entityManager) {
        RespuestaValidacionArchivoDTO solNovedadDTO = new RespuestaValidacionArchivoDTO();
        TipoIdentificacionEnum tipoIdentificacionAdminEnum = null;
        
        

        // TipoIdentificacionEnum tipoIdentificacionAdmin = TipoIdentificacionEnum
        // .obtenerTiposIdentificacionPILAEnum(tipoIdenAdminSubsidio);

        // logger.info("1574: tipoIdentificacionAdmin " + tipoIdentificacionFinal);

        if (tipoCuenta != null && !(tipoCuenta.equals(""))) {
            switch (tipoCuenta) {
                case "1":
                    tipoCuenta = "AHORROS";
                    break;
                case "2":
                    tipoCuenta = "CORRIENTE";
                    break;
                case "3":
                    tipoCuenta = "DAVIPLATA";
                    break;
                default:
                    break;
            }
        }

        // logger.info("Inicia tipoCuenta " +tipoCuenta);
        // Val 3: Se valida el campo No 1 Tipo Iden - No 2 Num iden del admin sub
        // corresponda a una persona que es o fue administrador de subsidio en Genesys
        if (idValidacion.equals("3")) {
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_3_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .getSingleResult();


            if (validacion != null) {
                if (validacion == 0) {
                    solNovedadDTO.setMensaje("no cumple con la val 3");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 3");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("4")) {
            // Val 4: Se valida que el campo No 3 - casId corresponda a una persona que es o
            // fue administrador de subsidio en Genesys.
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_4_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .getSingleResult();


            if (validacion != null) {
                if (validacion == 0) {
                    solNovedadDTO.setMensaje("no cumple con la val 4");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 4");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("5")) {
            // Val 5: Se valida que el campo No 3 - casId este relacionado a un “Medio de
            // pago” igual a “TRANSFERENCIA” con estado de la transacción igual a “ENVIADO”
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_5_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    solNovedadDTO.setMensaje("no cumple con la val 5");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 5");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("6")) {
            // Val 6: Se valida que el campo No 1 tipo Iden - No 2 Num iden este asociado al
            // administrador de subsidio
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_6_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    solNovedadDTO.setMensaje("no cumple con la val 6");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 6");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("7")) {
            // Val 7 : Se valida que el campo No 1 Tipo iden - No 2 Num iden sea el mismo
            // que el del administrador de subsidio
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_7_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    solNovedadDTO.setMensaje("no cumple con la val 7");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 7");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("8")) {
            // Val 8.1: Se valida que el campo No tipo cuenta este asociado al titular de la
            // cuenta
            if (tipoCuenta != null && !(tipoCuenta.equals(""))) {
                Integer validacion = (Integer) entityManager
                        .createNamedQuery(NamedQueriesConstants.VALIDACION_8_REGISTRO_CONF_ABONO)
                        .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                        .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                        .setParameter("casId", casId)
                        .setParameter("tipoCuenta", tipoCuenta)
                        .getSingleResult();

                if (validacion != null) {
                    if (validacion == 0) {
                        solNovedadDTO.setMensaje("no cumple con la val 8");
                        solNovedadDTO.setStatus("KO");
                    } else {
                        solNovedadDTO.setMensaje("cumple con la val 8");
                        solNovedadDTO.setStatus("OK");
                    }
                }
            } else {
                solNovedadDTO.setMensaje("cumple con la val 8");
                solNovedadDTO.setStatus("OK");
            }
        } else if (idValidacion.equals("9")) {
            // Val 9: Se valida que el campo No Numero cuenta exista en Genesys y este
            // asociado al titular de la cuenta
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_9_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .setParameter("numeroCuenta", numeroCuenta)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    solNovedadDTO.setMensaje("no cumple con la val 9");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 9");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("10")) {
            // Val 10: Se valida que el campo No Valor transferencia sea igual al “valor
            // real de la transacción” del abono que existe en Genesys
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_10_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .setParameter("valorTransferencia", valorTransferencia)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    solNovedadDTO.setMensaje("no cumple con la val 10");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 10");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else {
            solNovedadDTO.setMensaje("Envie la validacion que se quiere realizar (del 3 al 10)");
            solNovedadDTO.setStatus("KO");
        }
        return solNovedadDTO;
    }

    public Object[] obtenerTitularCuentaAdministradorSubsidioByCasId(String casId, EntityManager entityManager) {
        try {

            Object[] cuentaAdministrador = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TITULAR_CUENTA_ADMINISTTRADOR_SUBSIDIO_CASID)
                    .setParameter("casId", casId)
                    .getSingleResult();
            return cuentaAdministrador;

        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
}