package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.historicos.clients.ObtenerGruposFamiliaresAfiPrincipal;
import com.asopagos.historicos.dto.BeneficiarioGrupoFamiliarDTO;
import com.asopagos.historicos.dto.PersonaComoAfiPpalGrupoFamiliarDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import java.text.SimpleDateFormat;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de empleadores<br/>
 * <b>Módulo:</b> Asopagos - HU-498<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Jose Arley Correa Salamanca</a>
 */

public class ActualizacionRetiroBeneficiarioLineValidator extends LineValidator {

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
    public ActualizacionRetiroBeneficiarioLineValidator() {
        super();
        cInicioLabores.set(Calendar.YEAR, 1965);
        cInicioLabores.set(Calendar.MONTH, 1);
        cInicioLabores.set(Calendar.DAY_OF_YEAR, 1);
    }
    

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        System.out.println("inicia ActualizacionRetiroBeneficiarioLineValidator");
        Map<String, Object> line = arguments.getLineValues();
        try {
            Long lineNumber = arguments.getLineNumber();
            Calendar sistema = Calendar.getInstance();
            TipoIdentificacionEnum tipoIdentificacionBeneficiario=null;
            TipoIdentificacionEnum tipoIdentificacionAfiliado=null;
            
            // Se valida el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia
            try {
                System.out.println("tipo identificacion trabajador");
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

            System.out.println("numero identificacion trabajador");
            // Se valida el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
            verificarNumeroDocumento(tipoIdentificacionAfiliado, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO, 
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,arguments);

              
            // Se valida el campo No 3 - Tipo identificacion beneficiario
            try {
                System.out.println("tipo identificacion beneficiario");
                tipoIdentificacionBeneficiario = GetValueUtil
                        .getTipoIdentificacionByPila(((String) line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFICIARIO)));
                if (tipoIdentificacionBeneficiario == null) {
                     throw new Exception("Invalido");
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFICIARIO_MSG,
                        "Tipo de identificación invalido"));
                tipoIdentificacionBeneficiario = null;
            }
            
            System.out.println("numero identificacion beneficiario");
            // Se valida el campo No 4 - Número de identificación del beneficiario
            verificarNumeroDocumento(tipoIdentificacionBeneficiario, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO, 
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO_MSG,arguments);
          
            System.out.println("fecha retiro beneficiario");
            // Se valida el campo No 5 - Fecha de retiro
            verificacionCampoFecha(arguments, line, sistema, ArchivoCampoNovedadConstante.FECHA_RETIRO_BENEFICIARIO, 
                    ArchivoCampoNovedadConstante.FECHA_RETIRO_AFILIADO_MSG);
            
            // Se valida el campo No 6 - Motivo de retiro
            try {
                System.out.println("motivo retiro beneficiario");
                MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiario = GetValueUtil
                        .getMotivoDesafiliacionBeneficiario(((String) line.get(ArchivoCampoNovedadConstante.MOTIVO_RETIRO_BENEFICIARIO)));
                if (motivoDesafiliacionBeneficiario == null) {
                     throw new Exception("Invalido");
                }
                if(motivoDesafiliacionBeneficiario == MotivoDesafiliacionBeneficiarioEnum.FALLECIMIENTO){
                    throw new Exception("Invalido");
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.MOTIVO_RETIRO_BENEFICIARIO,
                        "Motivo retiro beneficiario invalido"));
                tipoIdentificacionAfiliado = null;
            }
            
            //Validacion relacion trabajador beneficiario
            if(tipoIdentificacionAfiliado != null && tipoIdentificacionBeneficiario != null){
                System.out.println("trabajador beneficiario");
                verificarTrabajadorRelacionadosBeneficiario(arguments,tipoIdentificacionAfiliado,tipoIdentificacionBeneficiario);
                System.out.println("validar novedad");
                validarNovedad(arguments, tipoIdentificacionAfiliado, tipoIdentificacionBeneficiario);
            }

        } finally {
            ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
                    System.out.println("**__** finally  ActualizacionRetiroBeneficiarioLineValidator ");

            if (!lstHallazgos.isEmpty()) {
                System.out.println("**__** finally  !ActualizacionRetiroBeneficiarioLineValidator ");
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
            TipoIdentificacionEnum tipoIdentificacionAfiliado,
            TipoIdentificacionEnum tipoIdentificacionBeneficiario) {//Revisar este llamado

        String numeroIdAfiliado = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
        String numeroIdBeneficiario = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO);
        String fechaRetiro = (String) (arguments.getLineValues()).get(ArchivoCampoNovedadConstante.FECHA_RETIRO_BENEFICIARIO);




        System.out.println("datos validacion");
        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoIdentificacion", tipoIdentificacionAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numeroIdAfiliado);
        datosValidacion.put("tipoIdentificacionBeneficiario", tipoIdentificacionBeneficiario.toString());
        datosValidacion.put("numeroIdentificacionBeneficiario", numeroIdBeneficiario);
        datosValidacion.put("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        datosValidacion.put("tipoBeneficiario", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
        datosValidacion.put("tipoIdentificacionTrabajador",tipoIdentificacionAfiliado.name());
        datosValidacion.put("numeroIdentificacionTrabajador",numeroIdAfiliado);

        System.out.println("datos validacion 2");

        
        BeneficiarioGrupoFamiliarDTO beneficiarioGrupoFamiliarDTO = consultarBeneficiariosTrabajador(tipoIdentificacionAfiliado, 
                numeroIdAfiliado, 
                tipoIdentificacionBeneficiario, 
                numeroIdBeneficiario);

                System.out.println("consultarBeneficiariosTrabajador");
        Date date1 = null;
        Date date2 = null;

        System.out.println("fecha retiro "+fechaRetiro);
        try {
            date1=new SimpleDateFormat("yyyy-MM-dd").parse(fechaRetiro);  
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            date2=new SimpleDateFormat("yyyy-MM-dd").parse(beneficiarioGrupoFamiliarDTO.getFechaIngresoBenef());  
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(date1 != null && date2 != null){
            if(date1.getTime() < date2.getTime()){
             lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.FECHA_RETIRO_AFILIADO_MSG,
                        "El beneficiario diligencio una fecha de retiro menor a la fecha de ingreso."));
            }

        }


        try {

        datosValidacion.put("idBeneficiario", beneficiarioGrupoFamiliarDTO.getIdBeneficiario().toString());
        System.out.println("toString");

        

            TipoTransaccionEnum tipoTransaccionBeneficiarioEnum = obtenerTipoTransaccionBeneficiario(beneficiarioGrupoFamiliarDTO.getParentezco());
            
            ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                    tipoTransaccionBeneficiarioEnum.name(), tipoTransaccionBeneficiarioEnum.getProceso(),
                    beneficiarioGrupoFamiliarDTO.getParentezco().name(), datosValidacion);
            validarReglasService.execute();
            List<ValidacionDTO> list = (List<ValidacionDTO>) validarReglasService.getResult();
            
//            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
//                        "Validacion reglas beneficiario: - tipoTransaccionBeneficiarioEnum.name(): "+tipoTransaccionBeneficiarioEnum.name()+
//                                " tipoTransaccionBeneficiarioEnum.getProceso(): " + tipoTransaccionBeneficiarioEnum.getProceso()+
//                                " beneficiarioGrupoFamiliarDTO.getParentezco().name(): " + beneficiarioGrupoFamiliarDTO.getParentezco().name()+
//                                " datosValidacion: " + datosValidacion));

            if (CollectionUtils.isNotEmpty(list)) {
                
//                list.forEach((iteValidation) -> {
//                    if(iteValidation.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)){
//                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
//                        "Validacion reglas beneficiario: - iteValidation.getBloque(): "+iteValidation.getBloque()+
//                                " iteValidation.getDetalle(): " + iteValidation.getDetalle()+
//                                " iteValidation.getResultado(): " + iteValidation.getResultado()+
//                                " iteValidation.getValidacion(): " + iteValidation.getValidacion()));
//                    }                    
//                });
                
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
                    + " tipoIdentificacionAfiliado: " + tipoIdentificacionAfiliado + ", numeroIdAfiliado: " + numeroIdAfiliado
                    + "tipoIdentificacionBeneficiario: " + tipoIdentificacionBeneficiario + ", numeroIdBeneficiario: " + numeroIdBeneficiario
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
     * Metodo encargado de verificar el numero de documento respecto a tipo de de documento
     * @param tipoIdentificacion
     * @param constanteNumeroIdentificacion
     * @param msgNumIdentificacion
     * @param arguments 
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
     * Metodo encargado de verifiar el estado del afiliado respecto al empleador
     * 
     * @param arguments
     * @param line
     */
    private void verificarTrabajadorRelacionadosBeneficiario(LineArgumentDTO arguments,
        TipoIdentificacionEnum tipoIdentificacionAfiliado, TipoIdentificacionEnum tipoIdentificacionBeneficiario) {
        System.out.println((String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO));
        System.out.println((String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO));

        String numeroIdAfiliado = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
        String numeroIdBeneficiario = (String)(arguments.getLineValues()).get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO);
        try {
            System.out.println(numeroIdAfiliado);
            System.out.println(numeroIdBeneficiario);
            
            BeneficiarioGrupoFamiliarDTO beneficiarioGrupoFamiliarDTO = consultarBeneficiariosTrabajador(tipoIdentificacionAfiliado, numeroIdAfiliado, 
                    tipoIdentificacionBeneficiario, numeroIdBeneficiario);
            
            if(beneficiarioGrupoFamiliarDTO == null){
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO_MSG,
                        "No se encontró relación entre el trabajador y el beneficiario"));
            }else if(!beneficiarioGrupoFamiliarDTO.getEstado().equals(EstadoAfiliadoEnum.ACTIVO)){
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO_MSG,
                        "El estado del beneficiario no es ACTIVO"));
            }
 
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG,
                        "No se encontró relación entre el trabajador y el beneficiario: "
                                +" tipoIdentificacionAfiliado: "+tipoIdentificacionAfiliado+", numeroIdAfiliado: "+numeroIdAfiliado
                                + "tipoIdentificacionBeneficiario: "+tipoIdentificacionBeneficiario+", numeroIdBeneficiario: "+numeroIdBeneficiario
                                +e.getMessage()+" Cause: "+e.getCause()));
        }
    }
    
    /**
     * Metodo que se encarga de llamar al cliente que consulta los beneficiarios de los empleadores.
     * @param tipoIdentificacionTrabajador
     * @param numeroIdentificacionTrabajador
     * @param tipoIdentificacionBeneficiario
     * @param numeroIdentificacionBeneficiario
     * @return Beneficiario que coincide con los filtros
     */
    private BeneficiarioGrupoFamiliarDTO consultarBeneficiariosTrabajador(TipoIdentificacionEnum tipoIdentificacionTrabajador, String numeroIdentificacionTrabajador,
            TipoIdentificacionEnum tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario) {
        
        BeneficiarioGrupoFamiliarDTO beneficiarioGrupoFamiliarDTO = null;
        
        ObtenerGruposFamiliaresAfiPrincipal obtenerGruposFamiliaresAfiPrincipal = 
                new ObtenerGruposFamiliaresAfiPrincipal(tipoIdentificacionTrabajador.toString(), numeroIdentificacionTrabajador);
        
        obtenerGruposFamiliaresAfiPrincipal.execute();
        List<PersonaComoAfiPpalGrupoFamiliarDTO> personasAfiliadas =  obtenerGruposFamiliaresAfiPrincipal.getResult();
        
        if (CollectionUtils.isNotEmpty(personasAfiliadas)) {
            ITEGRUPO_FAMILIAR : for (PersonaComoAfiPpalGrupoFamiliarDTO iteGrupoFamiliar : personasAfiliadas) {
                if (CollectionUtils.isNotEmpty(iteGrupoFamiliar.getBeneficiarios())) {

                    beneficiarioGrupoFamiliarDTO = iteGrupoFamiliar.getBeneficiarios().stream().filter(
                            iteBeneficiario -> iteBeneficiario.getTipoIdentificacion().equals(tipoIdentificacionBeneficiario)
                            && iteBeneficiario.getNumeroIdentificacion().equals(numeroIdentificacionBeneficiario)
                    ).findFirst().orElse(null);
                    if(beneficiarioGrupoFamiliarDTO != null){
                        break ITEGRUPO_FAMILIAR;
                    }
                }
            }
        }
        
        return beneficiarioGrupoFamiliarDTO;
    }
    
    /**
     * Entrega el tipo de transaccion para inactivacion del beneficario
     * @param clasificacion
     * @return
     */
    private TipoTransaccionEnum obtenerTipoTransaccionBeneficiario(ClasificacionEnum clasificacion) {
        TipoTransaccionEnum tipoTransaccionResult = null;
        switch (clasificacion) {
        case CONYUGE:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL;
            break;
        case PADRE:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL;
            break;
        case MADRE:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL;
            break;
        case HIJO_BIOLOGICO:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL;
            break;
        case HIJASTRO:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL;
            break;
        case HERMANO_HUERFANO_DE_PADRES:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL;
            break;
        case HIJO_ADOPTIVO:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL;
            break;
        case BENEFICIARIO_EN_CUSTODIA:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL;
            break;
        default:
            break;
        }
        return tipoTransaccionResult;
    }
}