package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

import java.util.*;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
import com.asopagos.afiliados.clients.ConsultarBeneficiarios;
import com.asopagos.afiliados.clients.ConsultarBeneficiariosAfiliacion;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.personas.*;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.pila.clients.ConsultarDatosEmpleadorByRegistroDetallado;
import com.asopagos.pila.clients.ConsultarDatosAfiliacionByRegistroDetallado;
import com.asopagos.afiliados.clients.ActualizarBeneficiarioSimple;
import com.asopagos.afiliados.clients.CrearRolAfiliado;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.composite.service.util.NovedadesCompositeUtils;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.dto.SucursalEmpresaDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.cache.CacheManager;
import com.asopagos.util.CalendarUtils;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import javax.persistence.EntityManager;
import com.asopagos.rutine.listaschequeorutines.guardarlistachequeo.GuardarListaChequeoRutine;

/**
 * Clase que contiene la lógica para actualizar por retiro un trabajador.
 * @author Maria Cuellar <maria.cuellar@eprocess.com.co>
 *
 */
public class ActualizarReintegroNovedadPersona implements NovedadCore {
    
    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ActualizarReintegroNovedadPersona.class);

    private List<TipoTransaccionEnum> retiro;
    
    private final String CANAL_PILA = "PILA";
    
    private final String CANAL_PRESENCIAL = "PRESENCIAL";

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        logger.info("[ActualizarReintegroNovedadPersona:transformarServicio]******");
        logger.info(solicitudNovedadDTO.toString());
       return null;
    }

    /**
     * Asocia las novedades por cada tipo
     * 
     */
    private void agregarListaNovedades() {
        /* Novedad 240 - 248 back */
        retiro = new ArrayList<>();
        retiro.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);
        retiro.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS);
    }

    /**
     * Realiza el llamado al servicio que consulta los beneficiarios asociados a la afiliación objeto del retiro
     * @param idAfiliado
     *        Identificador afiliado
     * @param idRolAfiliado
     *        Identificador del rol afiliado
     * @return
     */
    private List<BeneficiarioModeloDTO> consultarBeneficiariosAfiliacion(Long idAfiliado, Long idRolAfiliado) {
        ConsultarBeneficiariosAfiliacion consultarBeneficiariosAfiliacionService = new ConsultarBeneficiariosAfiliacion(idAfiliado,
                idRolAfiliado);
        consultarBeneficiariosAfiliacionService.execute();
        return consultarBeneficiariosAfiliacionService.getResult();
    }

    /**
     * Obtiene la lista de personas desafiliadas en la novedad
     * @param listaBe
     * @param listaBeneficiarios
     *        Lista de Beneficiarios retirados
     * @param rolAfiliado
     *        Afiliado retirado
     * @return Lista de personas asociadas a la novedad
     */
    private List<PersonaDTO> obtenerPersonasNovedad(RolAfiliadoModeloDTO rolAfiliado) {
        List<PersonaDTO> listaPersonaConsulta = new ArrayList<>();
        // Se itera los roles y beneficiarios inactivados
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNumeroIdentificacion(rolAfiliado.getAfiliado().getNumeroIdentificacion());
        personaDTO.setTipoIdentificacion(rolAfiliado.getAfiliado().getTipoIdentificacion());
        listaPersonaConsulta.add(personaDTO);
        return listaPersonaConsulta;
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO) {
        logger.info("[transformarEjecutarRutinaNovedad:transformarServicio]******");
        logger.info(solicitudNovedadDTO.toString());
        logger.info(solicitudNovedadDTO.getDatosPersona().toString());

        Date fechaIngresoAfiliado = new Date();
        logger.info(solicitudNovedadDTO.getDatosPersona());

        NovedadesCompositeUtils novedadesCompositeUtils = new NovedadesCompositeUtils(entityManager);

        TipoIdentificacionEnum tipoIdentificacionEmpleado = solicitudNovedadDTO.getDatosPersona().getTipoIdentificacion();
        String numeroidentificacionEmpleado = solicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion();
        TipoIdentificacionEnum tipoIdentificacionEmpleador = solicitudNovedadDTO.getDatosPersona().getTipoIdentificacionEmpleador();
        String numeroidentificacionEmpleador = solicitudNovedadDTO.getDatosPersona().getNumeroIdentificacionEmpleador();
        logger.info("transformarEjecutarRutinaNovedad: tipoIdentificacionEmpleado:" + tipoIdentificacionEmpleado);
        logger.info("transformarEjecutarRutinaNovedad: numeroidentificacionEmpleado:" + numeroidentificacionEmpleado);

        logger.info("transformarEjecutarRutinaNovedad: tipoIdentificacionEmpleador:" + tipoIdentificacionEmpleador);
        logger.info("transformarEjecutarRutinaNovedad: numeroidentificacionEmpleador:" + numeroidentificacionEmpleador);

        logger.info("transformarEjecutarRutinaNovedad: tipoSolicitud:" + tipoIdentificacionEmpleador);
        logger.info("transformarEjecutarRutinaNovedad: solicitud:" + solicitudNovedadDTO.getIdSolicitud());
        DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
        Date fechaRetiroAfiliado = null;
        List<Date> fechaRetiroAfiliadoList = new ArrayList<Date>();


        if (datosPersona.getFechaInicioNovedad() != null) {
            fechaIngresoAfiliado = new Date(datosPersona.getFechaInicioNovedad());
        }


        // Busca los datos del empleador con la SolicitudNovedad
        if (solicitudNovedadDTO.getIdRegistroDetallado() != null) {
            ConsultarDatosEmpleadorByRegistroDetallado consultarDatosEmpleadorByRegistroDetallado
                    = new ConsultarDatosEmpleadorByRegistroDetallado(solicitudNovedadDTO.getIdRegistroDetallado());
            consultarDatosEmpleadorByRegistroDetallado.execute();
            Object[] datosEmpleador = consultarDatosEmpleadorByRegistroDetallado.getResult();
            tipoIdentificacionEmpleador = TipoIdentificacionEnum.valueOf(datosEmpleador[0].toString());
            numeroidentificacionEmpleador = datosEmpleador[1].toString();
        }

        logger.info("transformarEjecutarRutinaNovedad: numero empleador:" + numeroidentificacionEmpleador);
        ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(solicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion(),
                solicitudNovedadDTO.getDatosPersona().getTipoIdentificacion());
        consultarDatosAfiliado.execute();
        AfiliadoModeloDTO afiliado = consultarDatosAfiliado.getResult();

        logger.info("**_** ActualizarReintegroNovedadPersona Afiliado:" + afiliado);
        if (afiliado != null) {
            // se consultan los beneficiarios asociados al afiliado
            logger.info("id Afiliado:" + afiliado.getIdAfiliado());
            Long cantidadBeneficiarios = novedadesCompositeUtils.getCantidadBenenficiariosAfiliado(afiliado.getIdAfiliado(), entityManager);
//comentado, priorizacion velocidad planillas
           /* try {
                System.out.println("Inicia tiempo espera");      
               //Ponemos a "Dormir" el programa durante los ms que queremos
               if(cantidadBeneficiarios == 1L){
                     Thread.sleep(2500); //dos segundos y medio
                }else if(cantidadBeneficiarios > 1L && cantidadBeneficiarios <= 3L){
                     Thread.sleep(5*1500);
                 }else if(cantidadBeneficiarios> 3L && cantidadBeneficiarios <= 5L){
                     Thread.sleep(7*2500);
               }else if(cantidadBeneficiarios> 5L){
                Thread.sleep(7*3500);
          }
              
                   System.out.println("Termina tiempo espera ");      
            } catch (Exception e) {
               System.out.println(e);
            }*/

            List<BeneficiarioDTO> beneficiarios = null;

            ConsultarBeneficiarios consultaBeneficiarios = new ConsultarBeneficiarios(afiliado.getIdAfiliado(), false);
            consultaBeneficiarios.execute();
            beneficiarios = consultaBeneficiarios.getResult();


            if (beneficiarios != null && !beneficiarios.isEmpty()) {
                logger.info("**_**Cantidad beneficiarios:" + beneficiarios.size());
                logger.info("solicitudNovedadDTO.getIsIngresoRetiro()" + solicitudNovedadDTO.getIsIngresoRetiro());
                // se compara la fecha de retiro del beneficiario con la fecha
                // de retiro del afiliado principal
                if (afiliado.getFechaRetiroAfiliado() != null) {
                    fechaRetiroAfiliadoList.add(new Date(afiliado.getFechaRetiroAfiliado()));
                    fechaRetiroAfiliado = new Date(afiliado.getFechaRetiroAfiliado());
                } else {
                    fechaRetiroAfiliadoList = novedadesCompositeUtils.getFechaRolAfiliado(solicitudNovedadDTO.getIdSolicitudNovedad(), solicitudNovedadDTO.getDatosPersona().getIdRolAfiliado(), afiliado.getIdAfiliado(), entityManager);
                }
                logger.info("Fecha de Retiro:" + fechaRetiroAfiliado);

                List<Long> benIdsList = new ArrayList<>();

                logger.info("solicitudNovedadDTO getBeneficiariosCadena" + solicitudNovedadDTO.getBeneficiariosCadena());

                if(solicitudNovedadDTO.getBeneficiariosCadena() != null){
                    benIdsList = Arrays.stream(solicitudNovedadDTO.getBeneficiariosCadena().split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                }



                for (BeneficiarioDTO beneficiario : beneficiarios) {

                    solicitudNovedadDTO.setIsIngresoRetiro(Boolean.FALSE);

                    if (benIdsList != null && benIdsList.contains(beneficiario.getIdBeneficiario())){
                        solicitudNovedadDTO.setIsIngresoRetiro(Boolean.TRUE);
                    }

                    logger.info("beneficiario.getEstadoBeneficiarioAfiliado() " + beneficiario.getEstadoBeneficiarioAfiliado());
                    if (EstadoAfiliadoEnum.INACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado()) || (solicitudNovedadDTO.getIsIngresoRetiro() != null && solicitudNovedadDTO.getIsIngresoRetiro())) {
                        LocalDate retiroBeneficiario = beneficiario.getFechaRetiro() != null
                                ? beneficiario.getFechaRetiro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                                : null;
                        boolean concideFechaRetiro = false;
                        for (Date fecha : fechaRetiroAfiliadoList) {
                            LocalDate retiroAfiliado = fecha != null ? fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                            if (retiroBeneficiario != null && retiroBeneficiario.isEqual(retiroAfiliado)) {
                                logger.info("**_** ActualizarReintegroNovedadPersona Fecha retiro concideFechaRetiro TRUE  " + retiroAfiliado);
                                concideFechaRetiro = true;
                            }
                        }


                        logger.info("**_** ActualizarReintegroNovedadPersona Id Solicitud:  " + solicitudNovedadDTO.getIdSolicitud());
                        logger.info("**_** ActualizarReintegroNovedadPersona Fecha retiro concideFechaRetiro " + concideFechaRetiro);
                        logger.info("**_** ActualizarReintegroNovedadPersona Fecha retiro Afiliado " + fechaRetiroAfiliado);
                        logger.info("**_** ActualizarReintegroNovedadPersona Fecha retiro beneficiario " + retiroBeneficiario);
                        logger.info("**_** ActualizarReintegroNovedadPersona tipo transaccion beneficiario " + beneficiario.getTipoBeneficiario());

                        logger.info("**_** ActualizarReintegroNovedadPersona retiroBeneficiario " + retiroBeneficiario);
                        logger.info("**_** ActualizarReintegroNovedadPersona concideFechaRetiro " + concideFechaRetiro);
                        logger.info("**_** ActualizarReintegroNovedadPersona solicitudNovedadDTO.getIsIngresoRetiro() " + solicitudNovedadDTO.getIsIngresoRetiro());
                        logger.info("**_** ActualizarReintegroNovedadPersona ejecutarMallaValidacionBeneficiario " + ejecutarMallaValidacionBeneficiario(beneficiario, tipoIdentificacionEmpleado, numeroidentificacionEmpleado,
                                tipoIdentificacionEmpleador, numeroidentificacionEmpleador, solicitudNovedadDTO.getCanalRecepcion(), solicitudNovedadDTO.getIsIngresoRetiro()));
                        logger.info("**_** ActualizarReintegroNovedadPersona validacionParametroActivacionBeneficiario " + validacionParametroActivacionBeneficiario(beneficiario, fechaIngresoAfiliado, solicitudNovedadDTO.getIsIngresoRetiro()));

                        // Si no existe un rol afiliado es necesario crearlo ademas de activas sus beneficiarios
                        if (((retiroBeneficiario != null && concideFechaRetiro) || (solicitudNovedadDTO.getIsIngresoRetiro() != null && solicitudNovedadDTO.getIsIngresoRetiro()))
                                && ejecutarMallaValidacionBeneficiario(beneficiario, tipoIdentificacionEmpleado, numeroidentificacionEmpleado,
                                tipoIdentificacionEmpleador, numeroidentificacionEmpleador, solicitudNovedadDTO.getCanalRecepcion(),solicitudNovedadDTO.getIsIngresoRetiro() )
                                && validacionParametroActivacionBeneficiario(beneficiario, fechaIngresoAfiliado, solicitudNovedadDTO.getIsIngresoRetiro())) {
                            logger.info("Entro a Activar beneficiario: " + beneficiario.getIdBeneficiario());
                            beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.ACTIVO);
                            beneficiario.setFechaRetiro(null);
                            beneficiario.setMotivoDesafiliacion(null);
                            beneficiario.setFechaAfiliacion(fechaIngresoAfiliado);

                            ActualizarBeneficiarioSimple actualizarBeneficiarioSimple = new ActualizarBeneficiarioSimple(beneficiario);
                            actualizarBeneficiarioSimple.execute();

                        }
                    }else{
                        logger.info("El beneficiario no cumple con la misma fecha de retiro del afiliado " + beneficiario.getIdBeneficiario());
                    }
                }
            }

            RolAfiliadoModeloDTO rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO();
            rolAfiliadoModeloDTO.setIdRolAfiliado(solicitudNovedadDTO.getDatosPersona().getIdRolAfiliado());
            rolAfiliadoModeloDTO.setFechaAfiliacion(fechaIngresoAfiliado.getTime());
            rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
            rolAfiliadoModeloDTO.setCanalReingreso(solicitudNovedadDTO.getCanalRecepcion());
            if (solicitudNovedadDTO.getDatosPersona().getFechaInicioLaboresConEmpleador() != null) {
                rolAfiliadoModeloDTO.setFechaIngreso(solicitudNovedadDTO.getDatosPersona().getFechaInicioLaboresConEmpleador());
            }
            if (solicitudNovedadDTO.getDatosPersona() != null && solicitudNovedadDTO.getDatosPersona().getSucursalEmpleadorTrabajador() != null) {
                rolAfiliadoModeloDTO.setIdSucursalEmpleador(solicitudNovedadDTO.getDatosPersona().getSucursalEmpleadorTrabajador().getIdSucursalEmpresa());
            }
            rolAfiliadoModeloDTO.setValorSalarioMesadaIngresos(solicitudNovedadDTO.getDatosPersona().getValorSalarioMensualTrabajador());
            actualizarRolAfiliado(
                    solicitudNovedadDTO,
                    rolAfiliadoModeloDTO,
                    numeroidentificacionEmpleador, tipoIdentificacionEmpleador,
                    numeroidentificacionEmpleado, tipoIdentificacionEmpleado,
                    solicitudNovedadDTO.getIdRegistroDetallado(),
                    entityManager);
        }
    }
    
    private Boolean ejecutarMallaValidacionBeneficiario(BeneficiarioDTO beneficiario,
            TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,CanalRecepcionEnum canalRecepcion,
                                                        Boolean ingresoRetiro) {
        String firmaMetodo = "AporteNovedadBussines.ejecutarMallaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        TipoTransaccionEnum bloque = calcularTipoTransaccion(beneficiario);

        if (bloque == null) {
            return Boolean.FALSE;
        }

        //

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
        datosValidacion.put("numeroIdentificacionEmpleador", numeroIdEmpleador);
        datosValidacion.put("canalRecepcion", canalRecepcion.name());
        datosValidacion.put("isIngresoRetiro", String.valueOf(ingresoRetiro));

        logger.info("datosValidacion " +datosValidacion);
        logger.info("bloque.name().replace(CANAL_PRESENCIAL, CANAL_PILA) " +bloque.name().replace(CANAL_PRESENCIAL, CANAL_PILA));
        logger.info("bloque.getProceso() " +bloque.getProceso());
        logger.info("beneficiario.getTipoBeneficiario().name() " +beneficiario.getTipoBeneficiario().name());

        ValidarReglasNegocio validador = new ValidarReglasNegocio(bloque.name().replace(CANAL_PRESENCIAL, CANAL_PILA), bloque.getProceso(),
                beneficiario.getTipoBeneficiario().name(), datosValidacion);
        validador.execute();
        List<ValidacionDTO> resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {

                logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: El beneficiario "
                        + beneficiario.getPersona().getTipoIdentificacion().getValorEnPILA()
                        + beneficiario.getPersona().getNumeroIdentificacion() + ", no aprobó la maya de validación - "
                        + validacion.getDetalle());
                return Boolean.FALSE;
            }
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.TRUE;
    }

    
    private Boolean validacionParametroActivacionBeneficiario(BeneficiarioDTO beneficiario,Date fechaIngresoAfiliado, Boolean isIngresoRetiro ) {

        String firmaMetodo = "Reintegro.validacionParametroActivacionBeneficiario(BeneficiarioDTO)";
        // validacion de reintegro beneficiario por parametro TIEMPO_REINTEGRO_GF
        String tiempoReintegroGF = (String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_REINTEGRO_GF);
        Long tiempoReintegro = CalendarUtils.toMilis(tiempoReintegroGF);

        LocalDate retiroBeneficiario = beneficiario.getFechaRetiro() != null ? 
        Instant.ofEpochMilli(beneficiario.getFechaRetiro().getTime() + tiempoReintegro).atZone(ZoneId.systemDefault()).toLocalDate()
        : null;

        if(isIngresoRetiro){
            return Boolean.TRUE;
        }

        if (retiroBeneficiario == null) return Boolean.FALSE;
        LocalDate fechaActualOPlanilla = LocalDate.now();
            if (fechaIngresoAfiliado != null){
                fechaActualOPlanilla = Instant.ofEpochMilli(fechaIngresoAfiliado.getTime()).atZone(ZoneId.systemDefault()).toLocalDate() ;
            }


        logger.info("Fecha retiro beneficiario + tiempo reintegro:  " + retiroBeneficiario.toString());
        logger.info("Fecha actual: " + fechaActualOPlanilla.toString());
        

        if (fechaActualOPlanilla.isBefore(retiroBeneficiario)) {
            logger.info("El beneficiario fue activado");
            return Boolean.TRUE;
        }
        logger.warn(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: El beneficiario "
                + beneficiario.getPersona().getTipoIdentificacion().getValorEnPILA()
                + beneficiario.getPersona().getNumeroIdentificacion() + ", no cumple con el tiempo de reintegro - ");
        return Boolean.FALSE;


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
     * Realiza la petición REST al servicio que actualiza el rol afiliado
     * @param rolAfiliadoModeloDTO
     *        Información del rolAfiliado a actualizar
     */
    private void actualizarRolAfiliado(SolicitudNovedadDTO solicitudNovedadDTO,
        RolAfiliadoModeloDTO rolAfiliadoModeloDTO,
        String numeroIdentificacionCotizante, TipoIdentificacionEnum tipoIdentificacionCotizante,
        String numeroIdentificacionAportante, TipoIdentificacionEnum tipoIdentificacionAportante,
        Long idRegistroDetallado, EntityManager entityManager) {
        String firma = "actualizarRolAfiliado";
        logger.info("Inicio de metodo: " +  firma);
        logger.info("Datos rolAfiliado: " + rolAfiliadoModeloDTO.toString());
        logger.info("Datos empleador, cedula: " + numeroIdentificacionCotizante +  " tipoIdentificaicon: " +  tipoIdentificacionCotizante.name());
        logger.info("Datos empleado, cedula: " + numeroIdentificacionAportante +  " tipoIdentificaicon: " +  tipoIdentificacionAportante.name());
        NovedadesCompositeUtils novedadesCompositeUtils = new NovedadesCompositeUtils();
        if (rolAfiliadoModeloDTO != null && rolAfiliadoModeloDTO.getIdRolAfiliado() != null) {

            // validacion solicitud afiliacion persona por novedades masivas
            logger.info("Canal recepcion: " + solicitudNovedadDTO.getCanalRecepcion().name());
            if (solicitudNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL) ) {
                // Consulta datos necesarios para la solicitud afiliacion
    
                Solicitud sol = entityManager.find(Solicitud.class, solicitudNovedadDTO.getIdSolicitud());
                RolAfiliado roa = entityManager.find(RolAfiliado.class, solicitudNovedadDTO.getDatosPersona().getIdRolAfiliado());
    
    
                SolicitudAfiliacionPersona solAfi = new SolicitudAfiliacionPersona();
    
                solAfi.setSolicitudGlobal(sol);
                solAfi.setRolAfiliado(roa);
                logger.info(solAfi.getRolAfiliado().getSucursalEmpleador().getNombre());
                logger.info(solAfi.getRolAfiliado().getSucursalEmpleador().getIdSucursalEmpresa());
                solAfi.setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
    
                entityManager.persist(solAfi);
                entityManager.flush();    

            }else{
                logger.info("Condicion fecha ingreso: " + rolAfiliadoModeloDTO.getFechaIngreso() == null);
                if(rolAfiliadoModeloDTO.getFechaIngreso() == null){
                    rolAfiliadoModeloDTO.setFechaIngreso(rolAfiliadoModeloDTO.getFechaAfiliacion());
                }
                //Alexander GLPI 72768 --74412 -- cuando existen solicitud WEB Y PRESENCIl sin terminar su afiliacion, y tienen el mismo empleador, actualizara ese rol afiliado incompleto
                    if (solicitudNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA) ) {
                    ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(numeroIdentificacionAportante, tipoIdentificacionAportante);
                    consultarDatosAfiliado.execute();
                    AfiliadoModeloDTO afiliado = consultarDatosAfiliado.getResult();
                    logger.info("Datos afiliado, id previo: " + afiliado.getIdAfiliado());
                    // Ya que se consulta al empleador, 
                    SucursalEmpresaDTO datosSucursal =
                    novedadesCompositeUtils.getDatosAportanteYSucursalByCedula(numeroIdentificacionCotizante, tipoIdentificacionCotizante.name(), entityManager);
                    DatosEmpleadorNovedadDTO dtoEmpleador = new DatosEmpleadorNovedadDTO();
                    dtoEmpleador.setIdEmpleador(datosSucursal.getIdEmpleador());
                    solicitudNovedadDTO.setDatosEmpleador(dtoEmpleador);
                    //Datos de pila
                    ConsultarDatosAfiliacionByRegistroDetallado consultarDatosAfiliacionByRegistroDetallado = new ConsultarDatosAfiliacionByRegistroDetallado(idRegistroDetallado);
                    consultarDatosAfiliacionByRegistroDetallado.execute();
                    Object[] datosAfiliacion = consultarDatosAfiliacionByRegistroDetallado.getResult();
                    //Setear datos
                    rolAfiliadoModeloDTO.setAfiliado(afiliado);
                    rolAfiliadoModeloDTO.setValorSalarioMesadaIngresos(new BigDecimal(datosAfiliacion[0].toString()));
                    rolAfiliadoModeloDTO.setIdEmpleador(datosSucursal.getIdEmpleador()); 
                    rolAfiliadoModeloDTO.setIdSucursalEmpleador(datosSucursal.getIdSucursalEmpresa());
                    rolAfiliadoModeloDTO.setFechaIngreso(Long.valueOf(datosAfiliacion[1].toString()));// date
                    rolAfiliadoModeloDTO.setTipoAfiliado(TipoAfiliadoEnum.valueOf(datosAfiliacion[2].toString()));
                    rolAfiliadoModeloDTO.setHorasLaboradasMes(Short.valueOf(datosAfiliacion[3].toString()));
                    rolAfiliadoModeloDTO.setCanalReingreso(solicitudNovedadDTO.getCanalRecepcion());
                    switch (datosAfiliacion[4].toString()) {
                        case "4":
                        rolAfiliadoModeloDTO.setClaseTrabajador(ClaseTrabajadorEnum.MADRE_COMUNITARIA); 
                        break;
                        case "2":
                        rolAfiliadoModeloDTO.setClaseTrabajador(ClaseTrabajadorEnum.SERVICIO_DOMESTICO);
                        break;
                        case "51":
                        rolAfiliadoModeloDTO.setClaseTrabajador(ClaseTrabajadorEnum.TRABAJADOR_POR_DIAS); 
                        break;
                        default:
                        rolAfiliadoModeloDTO.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
                        break;
                    }
                    guradarListasChequeoReintegro(datosAfiliacion[5].toString(),solicitudNovedadDTO,entityManager);
                }
            }
            logger.info("datos depues de if "+rolAfiliadoModeloDTO.toString());
            
            ActualizarRolAfiliado actualizarRolAfiliado = new ActualizarRolAfiliado(rolAfiliadoModeloDTO);
            actualizarRolAfiliado.execute();
            return;
        } 
        
        // Creacion de rol afiliado. Consulta a datos necesario de pila para la creacion

        // Dato idAfiliado
        ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(numeroIdentificacionAportante, tipoIdentificacionAportante);
        consultarDatosAfiliado.execute();
        AfiliadoModeloDTO afiliado = consultarDatosAfiliado.getResult();
        logger.info("Datos afiliado, id: " + afiliado.getIdAfiliado());
        
        // Ya que se consulta al empleador, 
        
        SucursalEmpresaDTO datosSucursal =
        novedadesCompositeUtils.getDatosAportanteYSucursalByCedula(numeroIdentificacionCotizante, tipoIdentificacionCotizante.name(), entityManager);
        
        DatosEmpleadorNovedadDTO dtoEmpleador = new DatosEmpleadorNovedadDTO();
        dtoEmpleador.setIdEmpleador(datosSucursal.getIdEmpleador());
        solicitudNovedadDTO.setDatosEmpleador(dtoEmpleador);

        //Datos de pila

        ConsultarDatosAfiliacionByRegistroDetallado consultarDatosAfiliacionByRegistroDetallado = new ConsultarDatosAfiliacionByRegistroDetallado(idRegistroDetallado);
        consultarDatosAfiliacionByRegistroDetallado.execute();
        Object[] datosAfiliacion = consultarDatosAfiliacionByRegistroDetallado.getResult();

        //Setear datos
        guradarListasChequeoReintegro(datosAfiliacion[5].toString(),solicitudNovedadDTO,entityManager);
        rolAfiliadoModeloDTO.setAfiliado(afiliado);
        rolAfiliadoModeloDTO.setValorSalarioMesadaIngresos(new BigDecimal(datosAfiliacion[0].toString()));

        rolAfiliadoModeloDTO.setIdEmpleador(datosSucursal.getIdEmpleador()); 
        rolAfiliadoModeloDTO.setIdSucursalEmpleador(datosSucursal.getIdSucursalEmpresa());
        rolAfiliadoModeloDTO.setFechaIngreso(Long.valueOf(datosAfiliacion[1].toString()));// date
        rolAfiliadoModeloDTO.setTipoAfiliado(TipoAfiliadoEnum.valueOf(datosAfiliacion[2].toString()));
        rolAfiliadoModeloDTO.setHorasLaboradasMes(Short.valueOf(datosAfiliacion[3].toString()));
        switch (datosAfiliacion[4].toString()) {
            case "4":
            rolAfiliadoModeloDTO.setClaseTrabajador(ClaseTrabajadorEnum.MADRE_COMUNITARIA); 
            break;
            case "2":
            rolAfiliadoModeloDTO.setClaseTrabajador(ClaseTrabajadorEnum.SERVICIO_DOMESTICO);
            break;
            case "51":
            rolAfiliadoModeloDTO.setClaseTrabajador(ClaseTrabajadorEnum.TRABAJADOR_POR_DIAS); 
            break;
            default:
            rolAfiliadoModeloDTO.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
            break;
        }
        rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
        rolAfiliadoModeloDTO.setCanalReingreso(solicitudNovedadDTO.getCanalRecepcion());
        rolAfiliadoModeloDTO.setIdentificadorAnteEntidadPagadora(null); 
        rolAfiliadoModeloDTO.setPorcentajePagoAportes(null);
        rolAfiliadoModeloDTO.setEstadoEnEntidadPagadora(null);
        rolAfiliadoModeloDTO.setTipoSalario(null);
        rolAfiliadoModeloDTO.setSustitucionPatronal(false);
        rolAfiliadoModeloDTO.setFechaRetiro(null);
        rolAfiliadoModeloDTO.setFechaFinPagadorAportes(null);
        rolAfiliadoModeloDTO.setFechaFinPagadorPension(null);
        rolAfiliadoModeloDTO.setCargo(null);
        rolAfiliadoModeloDTO.setClaseIndependiente(null);
        rolAfiliadoModeloDTO.setDiaHabilVencimientoAporte(null);
        rolAfiliadoModeloDTO.setMarcaExpulsion(null);
        rolAfiliadoModeloDTO.setEnviadoAFiscalizacion(null);
        rolAfiliadoModeloDTO.setMotivoFiscalizacion(null);
        rolAfiliadoModeloDTO.setFechaFiscalizacion(null);
        rolAfiliadoModeloDTO.setOportunidadPago(null);
        rolAfiliadoModeloDTO.setReferenciaAporteReingreso(null);
        rolAfiliadoModeloDTO.setReferenciaSolicitudReingreso(null);
        rolAfiliadoModeloDTO.setFechaFinContrato(null);

        CrearRolAfiliado crearRolAfiliado = new CrearRolAfiliado(rolAfiliadoModeloDTO);
        crearRolAfiliado.execute();
        solicitudNovedadDTO.getDatosPersona().setIdRolAfiliado(crearRolAfiliado.getResult());
        logger.info("Rol afiliado creado, id: " + solicitudNovedadDTO.getDatosPersona().getIdRolAfiliado());

    }

     public void guradarListasChequeoReintegro(String fecha_recepcion,SolicitudNovedadDTO solNovedadDTO,EntityManager entityManager) {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         Date fechaRecepcionDocumento = new Date();
         try {
             fechaRecepcionDocumento = new Date(Long.parseLong(fecha_recepcion.toString()));
         } catch (Exception e) {
             e.printStackTrace();
         }
         ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
         ItemChequeoDTO itemChequeoDto1 = new ItemChequeoDTO();
         itemChequeoDto1.setIdSolicitudGlobal(solNovedadDTO.getIdSolicitud());
         itemChequeoDto1.setIdRequisito(91L);
         itemChequeoDto1.setNombreRequisito("");
         itemChequeoDto1.setTextoAyuda("");
         itemChequeoDto1.setTipoRequisito(TipoRequisitoEnum.ESTANDAR);
         itemChequeoDto1.setFechaRecepcionDocumentos(fechaRecepcionDocumento.getTime());
         itemChequeoDto1.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OPCIONAL);
         itemChequeoDto1.setIdentificadorDocumento("");
         itemChequeoDto1.setVersionDocumento(Short.MIN_VALUE);
         itemChequeoDto1.setCumpleRequisito(Boolean.TRUE);
         logger.info("itemChequeoDto1.getIdentificadorDocumento" + itemChequeoDto1.getIdentificadorDocumento());
         List<ItemChequeoDTO> itemChequeoDto = new ArrayList<ItemChequeoDTO>();
         itemChequeoDto.add(itemChequeoDto1);
         listaChequeo.setFechaRecepcionDocumentos(fechaRecepcionDocumento.getTime());
         listaChequeo.setIdSolicitudGlobal(solNovedadDTO.getIdSolicitud());
         listaChequeo.setNumeroIdentificacion(solNovedadDTO.getDatosPersona().getNumeroIdentificacion());
         listaChequeo.setTipoIdentificacion(solNovedadDTO.getDatosPersona().getTipoIdentificacion());
         listaChequeo.setListaChequeo(itemChequeoDto);
         GuardarListaChequeoRutine g = new GuardarListaChequeoRutine();
         g.guardarListaChequeo(listaChequeo, entityManager);
     }

}