package com.asopagos.solicitud.composite.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Arrays;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.asopagos.afiliaciones.clients.BuscarSolicitud;
import com.asopagos.afiliaciones.empleadores.clients.ActualizarEstadoSolicitudAfiliacion;
import com.asopagos.afiliaciones.personas.clients.ActualizarEstadoSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ObtenerAfiliacionesSinInstanciaProceso;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.correspondencia.clients.AsociarSolicitudesACajaCorrespondencias;
import com.asopagos.correspondencia.clients.GenerarListadoSolicitudesRemisionBack;
import com.asopagos.dto.FiltroSolicitudDTO;
import com.asopagos.dto.ResultadoConsultaSolicitudDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.SolicitudNovedadGeneralDTO;
import com.asopagos.dto.afiliaciones.RemisionBackDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ReultadoValidacionCampoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.ConsultarSolicitudesPostulacionEnProceso;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.clients.ActualizarEstadoSolicitudNovedad;
import com.asopagos.novedades.clients.ObtenerNovedadesSinInstanciaProceso;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.solicitud.composite.dto.DatosAbortarSolicitudDTO;
import com.asopagos.solicitud.composite.dto.DatosSeguimientoSolicitudesDTO;
import com.asopagos.solicitud.composite.factory.Solicitud;
import com.asopagos.solicitud.composite.factory.SolicitudFactory;
import com.asopagos.solicitud.composite.service.SolicitudCompositeService;
import com.asopagos.solicitudes.clients.ConsultarDatosTempPorPersona;
import com.asopagos.solicitudes.clients.ConsultarSolicitudesFiltroSolicitud;
import com.asopagos.solicitudes.clients.GuardarDocumentosAdminSolicitudes;
import com.asopagos.solicitudes.clients.PersistirResultadoUtiliarioBPM;
import com.asopagos.solicitudes.clients.ReasignarSolicitud;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.ActivarTarea;
import com.asopagos.tareashumanas.clients.ObtenerDetalleTarea;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.ObtenerTareaActivaInstancia;
import com.asopagos.tareashumanas.clients.ObtenerTareasCreadasSinPropietario;
import com.asopagos.tareashumanas.clients.ReasignarTarea;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.usuarios.clients.CerrarSesionesUsuario;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.solicitud.composite.constants.NamedQueriesConstants;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedad;
import com.asopagos.novedades.composite.clients.ResolverNovedades;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.novedades.composite.dto.ResolverNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliado;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.core.PuntoResolucionEnum;
import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;


/**
 * <b>Descripci�n:</b> EJB que implementa los m�todos de negocio relacionados
 * con la composic�n de Solicitudes <b>Historia de Usuario:</b> TRA-114
 * 
 * @author Jerson Zambrano <jzambrano@heinsohn.com.co>
 */

@Stateless
public class SolicitudCompositeBusiness implements SolicitudCompositeService {

    
    /** Referencia al logger */
    private ILogger logger = LogManager.getLogger(SolicitudCompositeBusiness.class);
    
    /**
     * Indica el resultado de ejecucion EN_BANDEJA
     */
    private final String EN_BANDEJA = "EN_BANDEJA";
    
    /**
     * Indica el resultado de ejecucion ERROR
     */
    private final String ERROR = "ERROR";
    
    /**
     * Indica el resultado de ejecucion SOLICITUD_RECHAZADA
     */
    private final String SOLICITUD_RECHAZADA = "SOLICITUD_RECHAZADA";

    @PersistenceContext(unitName = "solicitudcomposite_PU")
    private EntityManager entityManager;

    

    @Override
    public void cambiarEstadoSolicitudFinalizarGestion(CambiarEstadoSolicitudFinGestionDTO inDTO, UserDTO userDTO) {
        logger.debug("Inicia cambiarEstadoSolicitudFinalizarGestion(CambiarEstadoSolicitudFinGestionDTO, UserDTO)");
        List<TipoTransaccionEnum> validTransactionTypes = Arrays.asList(
        TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION,
        TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_WEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_WEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL,
        TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB,
        TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL,
        TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB,
        TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_WEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_DEPWEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_WEB,
        TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_DEPWEB
    );

    if (validTransactionTypes.contains(inDTO.getTipoTx())) {
        RolAfiliado rolAfiliado = null;
        List<Beneficiario> beneficiarios = new ArrayList<>();

        try {
            rolAfiliado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ROLAFILIADO_POR_SOLICITUD, RolAfiliado.class)
                    .setParameter("idSolicitud", inDTO.getIdSolicitudGlobal())
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("No se encontró rolAfiliado para la solicitud: " + inDTO.getIdSolicitudGlobal());
        }
   
        SolicitudNovedadDTO solicitudNovedad = new SolicitudNovedadDTO();

        if (rolAfiliado != null && (inDTO.getTipoTx() == TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION || inDTO.getTipoTx() == TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION)) {
           
            List<RolAfiliadoEmpleadorDTO> listaRolAfiliadoEmpleador = new ArrayList<>(); //importar
            ConsultarRolesAfiliado consultarAfiliado = new ConsultarRolesAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,
                rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion(),
                rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion());
            consultarAfiliado.execute();
            listaRolAfiliadoEmpleador = consultarAfiliado.getResult();
            Boolean multiAfiliacion = false;
            int contadorAfiliaciones = 0;
            if(listaRolAfiliadoEmpleador != null && listaRolAfiliadoEmpleador.size() > 1) {
                for (RolAfiliadoEmpleadorDTO rolAfiliadoEmpleador : listaRolAfiliadoEmpleador) {
                    if(rolAfiliadoEmpleador.getRolAfiliado().getEstadoAfiliado() != null) {
                        if (rolAfiliadoEmpleador.getRolAfiliado().getEstadoAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)) {
                            contadorAfiliaciones++;
                            if (contadorAfiliaciones > 1) {
                                multiAfiliacion = true;
                                break;
                            }
                        }
                    }
                        
                }
            }
                
            DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
            Persona persona = rolAfiliado.getAfiliado().getPersona();

            datosPersona.setIdPersona(persona.getIdPersona());
            datosPersona.setTipoIdentificacion(persona.getTipoIdentificacion());
            datosPersona.setNumeroIdentificacion(persona.getNumeroIdentificacion());
            datosPersona.setTipoIdentificacionTrabajador(persona.getTipoIdentificacion());
            datosPersona.setNumeroIdentificacionTrabajador(persona.getNumeroIdentificacion());
            datosPersona.setPrimerApellidoTrabajador(persona.getPrimerApellido());
            datosPersona.setSegundoApellidoTrabajador(persona.getSegundoApellido());
            datosPersona.setPrimerNombreTrabajador(persona.getPrimerNombre());
            datosPersona.setSegundoNombreTrabajador(persona.getSegundoNombre());
            datosPersona.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
            datosPersona.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            datosPersona.setFechaRetiro(new Date().getTime());
            datosPersona.setTipoSolicitanteTrabajador(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
            datosPersona.setMotivoDesafiliacionTrabajador(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);

            solicitudNovedad.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
            solicitudNovedad.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            solicitudNovedad.setTipoTransaccion(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);
            solicitudNovedad.setDatosPersona(datosPersona);
            solicitudNovedad.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
            solicitudNovedad.setObservaciones(inDTO.getObservacion());
            ParametrizacionNovedadModeloDTO novedadDTO = new ParametrizacionNovedadModeloDTO();
            novedadDTO.setPuntoResolucion(PuntoResolucionEnum.FRONT);
            solicitudNovedad.setNovedadDTO(novedadDTO);

            RadicarSolicitudNovedad radicar = new RadicarSolicitudNovedad(solicitudNovedad);
            radicar.execute();
            solicitudNovedad = radicar.getResult();

            SolicitudNovedadModeloDTO solicitudModelo = solicitudNovedad.covertToSolicitudNovedadModeloDTO();
            solicitudModelo.setIdSolicitud(solicitudNovedad.getIdSolicitud());
            solicitudModelo.setIdInstanciaProceso(String.valueOf(solicitudNovedad.getIdInstancia()));
            solicitudModelo.setNumeroRadicacion(solicitudNovedad.getNumeroRadicacion());

            ResolverNovedadDTO resolverNovedad = new ResolverNovedadDTO();
            resolverNovedad.setSolicitudNovedad(solicitudNovedad);
            resolverNovedad.setSolicitudNovedadModelo(solicitudModelo);
            ResolverNovedades resolver = new ResolverNovedades(resolverNovedad);
            resolver.execute();
            
            if(multiAfiliacion) {
                try {
                    beneficiarios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_POR_SOLICITUD, Beneficiario.class)
                                .setParameter("idSolicitud", inDTO.getIdSolicitudGlobal())
                                .getResultList();
                } catch (NoResultException e) {
                    logger.warn("No se encontraron beneficiarios para la solicitud: " + inDTO.getIdSolicitudGlobal());
                }
                List<BeneficiarioModeloDTO> listaBeneficiarios = new ArrayList<>();
                for (Beneficiario beneficiario : beneficiarios) {
                    BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
                    beneficiarioModeloDTO.convertToDTO(beneficiario, null, null, null);
                    listaBeneficiarios.add(beneficiarioModeloDTO);
                }
                DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
                datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
                datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
                datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(solicitudNovedad.getNumeroRadicacion());
                datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(solicitudNovedad.getTipoTransaccion());
                datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
                RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
                novedadCascada.execute();
            }
        }else{
            try {
                beneficiarios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_POR_SOLICITUD_NOVEDAD, Beneficiario.class)
                    .setParameter("idSolicitud", inDTO.getIdSolicitudGlobal())
                    .getResultList();
                } catch (NoResultException e) {
                    logger.warn("No se encontraron beneficiarios para la solicitud: " + inDTO.getIdSolicitudGlobal());
                }
            if(beneficiarios != null && !beneficiarios.isEmpty()){
                List<BeneficiarioModeloDTO> listaBeneficiarios = new ArrayList<>();
                Beneficiario beneficiario = beneficiarios.get(0);
                BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
                beneficiarioModeloDTO.convertToDTO(beneficiario, null, null, null);
                listaBeneficiarios.add(beneficiarioModeloDTO);
                DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
                datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
                datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
                datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(String.valueOf(inDTO.getIdSolicitudGlobal()));                
                datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
                RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
                novedadCascada.execute();   
            }       
        }
    } 
        Solicitud solicitudAfiliacion = SolicitudFactory.getInstance(inDTO.getTipoTx());
        solicitudAfiliacion.actualizarEstadoSolicitud(inDTO);

    if (inDTO.getAdministracionEstadoSolicituds() != null && !inDTO.getAdministracionEstadoSolicituds().isEmpty()) {
        GuardarDocumentosAdminSolicitudes guardar = new GuardarDocumentosAdminSolicitudes(
                inDTO.getNumeroRadicado(),
                inDTO.getAdministracionEstadoSolicituds());
        guardar.execute();
    }

    logger.debug("Finaliza cambiarEstadoSolicitudFinalizarGestion(CambiarEstadoSolicitudFinGestionDTO, UserDTO)");
    }

    @Override
    public void cambiarEstadoSolicitudAbortarProceso(DatosAbortarSolicitudDTO inDTO, UserDTO userDTO) {
        logger.debug("Inicia cambiarEstadoSolicitudAbortarProceso(DatosAbortarSolicitudDTO inDTO, UserDTO userDTO)");
        ConsultarDatosTempPorPersona instanciaSolTemp = new ConsultarDatosTempPorPersona(inDTO.getNumeroIdentificacion(),
                inDTO.getTipoIdentificacion());
        instanciaSolTemp.execute();
        List<SolicitudAfiliacionEmpleador> solicitudesWeb = instanciaSolTemp.getResult();
        if (solicitudesWeb != null) {
            for (SolicitudAfiliacionEmpleador soli : solicitudesWeb) {
                try{
                    AbortarProceso abortarProceso = new AbortarProceso(ProcesoEnum.AFILIACION_EMPRESAS_WEB,
                        new Long(soli.getSolicitudGlobal().getIdInstanciaProceso()));
                    abortarProceso.execute();
                }catch(Exception e){
                    e.printStackTrace();
                    logger.error("Error al abortar proceso de afiliacion de empresas web", e);
                }

                ActualizarEstadoSolicitudAfiliacion actualizar = new ActualizarEstadoSolicitudAfiliacion(
                        soli.getIdSolicitudAfiliacionEmpleador(), EstadoSolicitudAfiliacionEmpleadorEnum.CANCELADA);
                actualizar.execute();
                actualizar = new ActualizarEstadoSolicitudAfiliacion(soli.getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
                actualizar.execute();
            }
        }
        logger.debug("Finaliza cambiarEstadoSolicitudAbortarProceso(DatosAbortarSolicitudDTO inDTO, UserDTO userDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.solicitud.composite.service.SolicitudCompositeService#
     *      consultarSolicitudesEnProceso(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public DatosSeguimientoSolicitudesDTO consultarSolicitudesGeneralesEnProceso(UserDTO userDTO) {
        logger.debug("Inicia servicio consultarSolicitudesGeneralesEnProceso(UserDTO)");
        try {
            ObtenerDatosUsuarioCajaCompensacion obtener = new ObtenerDatosUsuarioCajaCompensacion(userDTO.getNombreUsuario(), null, null,
                    null);
            obtener.execute();

            UsuarioCCF datosUsuario = obtener.getResult();
            Long identificacion = Long.parseLong(datosUsuario.getNumIdentificacion());
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosUsuario.getTipoIdentificacion());

            DatosSeguimientoSolicitudesDTO datosSeguimientoSolicitudesDTO = new DatosSeguimientoSolicitudesDTO();

            // TODO falta Consultar las demas solicitudes y adicionarlas al dto

            //            // consulta todos los estados
            //            ConsultarSolicitudesNovedad consultarSolicitudesNovedad = new ConsultarSolicitudesNovedad(identificacion, null,
            //                    tipoIdentificacion);
            //
            //            ConsultarSolicitudAfiliacionPersonaAfiliada consultarSolicitudAfiliacionPersonaAfiliada = new ConsultarSolicitudAfiliacionPersonaAfiliada(
            //                    null, null, null, datosUsuario.getNumIdentificacion(), tipoIdentificacion);
            //
            //            BuscarSolicitudesAfiliacionPersonaPorEmpleador buscarSolicitudesAfiliacionPersonaPorEmpleador = new BuscarSolicitudesAfiliacionPersonaPorEmpleador(
            //                    null, null, null);
            //
            //            // busca las solicitudes de afiliacion de empleador por id del empleador si se envia, de lo contario por persona
            //            ConsultarSolicitudesEnProceso consultarSolicitudesEnProceso = new ConsultarSolicitudesEnProceso(null,
            //                    datosUsuario.getNumIdentificacion(), tipoIdentificacion);

            /**
             * m�todos por validar
             * ValidadorExistenciaSolicitudWeb -> consultarSolicitudesAfilPersona
             * ValidadorExistenciaSolicitud -> consultarSolicitudesAfilPersona
             */
            System.out.println("GLPI 79807 datosUsuario.getNumIdentificacion():"+datosUsuario.getNumIdentificacion()+",  tipoIdentificacion:"+tipoIdentificacion);
            ConsultarSolicitudesPostulacionEnProceso consultarSolicitudesPostulacionEnProceso = new ConsultarSolicitudesPostulacionEnProceso(
                    datosUsuario.getNumIdentificacion(), tipoIdentificacion);
            consultarSolicitudesPostulacionEnProceso.execute();
            datosSeguimientoSolicitudesDTO.setSolicitudesPostulacion(consultarSolicitudesPostulacionEnProceso.getResult());

            logger.debug("Finaliza servicio consultarSolicitudesGeneralesEnProceso(UserDTO)");
            return datosSeguimientoSolicitudesDTO;
        } catch (Exception e) {
            logger.error("Error inesperado en consultarSolicitudesGeneralesEnProceso(UserDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    @Override
    public List<SolicitudDTO> consultarSolicitudeAdmin(String numeroRadicado) {
        //BuscarSolicitud 
        BuscarSolicitud consultarSolicitudes = new BuscarSolicitud(numeroRadicado);
        consultarSolicitudes.execute();
        List<SolicitudDTO> listaSolicitudes = consultarSolicitudes.getResult();
        if (listaSolicitudes != null){
            for (SolicitudDTO solicitudDTO : listaSolicitudes) {
                if(solicitudDTO.getIdInstanciaProceso()!=null){
                    Map<String, Object> mapResult = consultarDetalleTarea(solicitudDTO.getIdInstanciaProceso());
                    if(mapResult!=null){
                        Long fechaActivacion = new Long(mapResult.get("fechaActivacion").toString());
                        solicitudDTO.setFechaActivacion(fechaActivacion);                    
                    }
                }
            }
        }
        return listaSolicitudes;
    }
    
    /**
     * M�todo que hace la peticion REST al servicio de obtener tarea activa para
     * posteriomente finalizar el proceso de Afiliaci�n 
     * 
     * @param idInstanciaProceso
     *        <code>String</code> El identificador de la Instancia Proceso
     *        Afiliacion de la Persona
     * 
     * @return <code>Long</code> El identificador de la tarea Activa
     */
    private Long consultarTarea(String idInstanciaProceso) {
        logger.debug("Inicia consultarTareaAfiliacionPersonas( idSolicitudGlobal )");
        String idTarea;
        ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(Long.parseLong(idInstanciaProceso));
        obtenerTareaActivaService.execute();
        Map<String, Object> mapResult = (Map<String, Object>) obtenerTareaActivaService.getResult();
        if (mapResult != null) {
            logger.debug("Finaliza consultarTareaAfiliacionPersonas( idSolicitudGlobal )");
            idTarea = ((Integer) mapResult.get("idTarea")).toString();
            return new Long(idTarea);            
        }
        return null;
    }
    
    
    /**
     * M�todo que hace la peticion REST al servicio de obtener tarea activa para
     * posteriomente finalizar el proceso de Afiliaci�n 
     * 
     * @param idInstanciaProceso
     *        <code>String</code> El identificador de la Instancia Proceso
     *        Afiliacion de la Persona
     * 
     * @return <code>Long</code> El identificador de la tarea Activa
     */
    private Map<String, Object> consultarDetalleTarea(String idInstanciaProceso) {
        
        Long idTarea= consultarTarea(idInstanciaProceso);
        if(idTarea!=null){
            logger.debug("Inicia consultarDetalleTarea(String idInstanciaProceso)");
            ObtenerDetalleTarea detalle = new ObtenerDetalleTarea(idTarea);
            detalle.execute();
            Map<String, Object> mapResult = (Map<String, Object>) detalle.getResult();
            return mapResult;            
        }
        return null;
    }
    
    @Override
    public void reasignarTareaSolicitud(Long idProceso, Long idTarea, String usuarioActual, String usuarioNuevo, UserDTO userDTO) {
        //Se reasigna la tarea en el BPMS
        ReasignarTarea reasignarTarea = new ReasignarTarea(idTarea, usuarioNuevo);
        reasignarTarea.execute();
        // Se cierran la sesi�n del due�o actual de la solicitud para evitar problemas de concurrencia
        CerrarSesionesUsuario cerrarSesiones = new CerrarSesionesUsuario(usuarioActual);
        cerrarSesiones.execute();
        //Se registra la reasignaci�n en la tabla SOlicitud de core
        ReasignarSolicitud reasignarSolicitud = new ReasignarSolicitud(idProceso, usuarioNuevo, usuarioActual);
        reasignarSolicitud.execute();
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.service.SolicitudesService#asociarSolicitudesACajaCorrespondencia(java.lang.String,
     *      java.lang.String, java.util.List)
     */
    @Override
    public void asociarSolicitudesACajaCorrespondencia(String codigoSede, List<String> listaRadicados,
            UserDTO userDTO) {
        
        //Logger temporal - Revisión mantis 259482
        for (String radicado : listaRadicados) {
            logger.info("Radicado recibido desde la pantalla " + radicado);
        }

        AsociarSolicitudesACajaCorrespondencias asociar = new AsociarSolicitudesACajaCorrespondencias(codigoSede, listaRadicados);
        asociar.execute();
        
        List<String> idsInstanciaProceso = new ArrayList<>();
        idsInstanciaProceso.addAll(asociar.getResult());
        
        try {
            for (String idInstancia : idsInstanciaProceso) {
                logger.debug("Identificador IN de instancia de proceso: "+idInstancia);
                ObtenerTareaActivaInstancia obtener = new ObtenerTareaActivaInstancia(Long.valueOf(idInstancia));
                obtener.execute();
                
                logger.debug("Identificador  OUT de instancia de proceso: "+idInstancia);
                TareaDTO tarea = obtener.getResult();
                if(tarea != null){
                    logger.debug("Identificador de tarea a terminar: "+tarea.getId());
                    Map<String, Object> params = new HashMap<>();
                    TerminarTarea terminarTarea = new TerminarTarea(tarea.getId(), params);
                    terminarTarea.execute();
                }else
                    logger.info("No hay tarea activa asociada al proceso " + idInstancia);
            }
            
        } catch (Exception e) {
            logger.error("Error inesperado en asociarSolicitudesACajaCorrespondencia", e);
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }
    }

    @Override
	public List<ResultadoConsultaSolicitudDTO> consultarSolicitudesFiltro(FiltroSolicitudDTO filtroSolicitud, UriInfo uri, HttpServletResponse response) {

    	/*
    	 * Parametros que se utilizan para la paginacion de los resultados de la consulta
    	 */
		Map<String,List<String>> params = new HashMap<String,List<String>>();
		
		 if (uri != null && uri.getQueryParameters() != null) {
            for (Entry<String, List<String>> e : uri.getQueryParameters().entrySet()) {
           	 params.put(e.getKey(), e.getValue()); 
            }
        }
		 
		 Integer offSet = params.get("offset") != null ? Integer.parseInt(params.get("offset").get(0)): null;
		 Integer limit = params.get("limit") != null  ? Integer.parseInt(params.get("limit").get(0)) : null ;
		 
		 filtroSolicitud.setOffSet(offSet);
		 filtroSolicitud.setLimit(limit);
		 
		/*
		 * Se llama al servicio de solicitudes que filtra informaci�n que se puede
		 * obtener del modelo relacional
		 */
		ConsultarSolicitudesFiltroSolicitud consultarSolicitudesFiltroSolicitud = new ConsultarSolicitudesFiltroSolicitud(
				filtroSolicitud);
		consultarSolicitudesFiltroSolicitud.execute();
		List<ResultadoConsultaSolicitudDTO> listaSolicitudesResultado = consultarSolicitudesFiltroSolicitud.getResult();
		
		Response responseConsultaSolicitudes = consultarSolicitudesFiltroSolicitud.getResponse();
		response.addHeader("totalRecords", responseConsultaSolicitudes!= null ? responseConsultaSolicitudes.getHeaderString("totalRecords") : null);
		
		Iterator<ResultadoConsultaSolicitudDTO> listaSolicitudesResultadoIterator = listaSolicitudesResultado
				.iterator();
		while (listaSolicitudesResultadoIterator.hasNext()) {
			ResultadoConsultaSolicitudDTO resultadoConsultaSolicitudDTO = listaSolicitudesResultadoIterator.next();

			/*
			 * Se obtiene informaci�n del usuario que radic� la solicitud
			 */
			UsuarioCCF usuarioRadiacionDatos = null;
            logger.info(resultadoConsultaSolicitudDTO.getUsuarioRadicacion());
			if(resultadoConsultaSolicitudDTO.getUsuarioRadicacion() != null){
				ObtenerDatosUsuarioCajaCompensacion obtener = new ObtenerDatosUsuarioCajaCompensacion(
						resultadoConsultaSolicitudDTO.getUsuarioRadicacion(), null, null, null);
				obtener.execute();
				usuarioRadiacionDatos = obtener.getResult();
                logger.info(usuarioRadiacionDatos);
			}
			
			resultadoConsultaSolicitudDTO.setSucursalRadicacion(usuarioRadiacionDatos != null ? usuarioRadiacionDatos.getCodigoSede(): null);

			/*
			 * Filtro de sucursal de radicaci�n
			 */
			if (filtroSolicitud.getSucursalRadicacion() != null) {
				if (usuarioRadiacionDatos != null && usuarioRadiacionDatos.getCodigoSede().equals(filtroSolicitud.getSucursalRadicacion())) {
					resultadoConsultaSolicitudDTO.setSucursalRadicacion(usuarioRadiacionDatos.getCodigoSede());
				} else {
					listaSolicitudesResultadoIterator.remove();
					continue;
				}
			} else {
				resultadoConsultaSolicitudDTO.setSucursalRadicacion(usuarioRadiacionDatos != null ? usuarioRadiacionDatos.getCodigoSede(): null);
			}

			/*
			 * Se obtiene la informaci�n de la tarea realcionada con la solicitud
			 */
			
			resultadoConsultaSolicitudDTO.setTarea(null);
			resultadoConsultaSolicitudDTO.setUsuarioAsignado(null);
			
			if (resultadoConsultaSolicitudDTO.getIdInstanciaProceso() != null) {
				ObtenerTareaActivaInstancia obtenerTareaActiva = new ObtenerTareaActivaInstancia(
						Long.parseLong(resultadoConsultaSolicitudDTO.getIdInstanciaProceso()));
                logger.info("Id Instancia: "+resultadoConsultaSolicitudDTO.getIdInstanciaProceso());
				obtenerTareaActiva.execute();
				TareaDTO informacionTareaActiva = obtenerTareaActiva.getResult();
				if(informacionTareaActiva != null){
				    resultadoConsultaSolicitudDTO.setTarea(informacionTareaActiva.getEstado());
				    resultadoConsultaSolicitudDTO.setNombreTarea(informacionTareaActiva.getNombre());
                    logger.info("Tarea: "+informacionTareaActiva.getPropietario());
	                resultadoConsultaSolicitudDTO.setUsuarioAsignado(informacionTareaActiva.getPropietario());    
				}
			}
			
			/*
			 * Filtro de usuario asignado
			 */
			if (filtroSolicitud.getUsuarioAsignado() != null) {
				if( (resultadoConsultaSolicitudDTO.getUsuarioAsignado() == null) || 
						(!(resultadoConsultaSolicitudDTO.getUsuarioAsignado().equals(filtroSolicitud.getUsuarioAsignado())))) {
					listaSolicitudesResultadoIterator.remove();
					continue;
				}
			}
		}
		return listaSolicitudesResultado;
	}

    /** (non-Javadoc)
     * @see com.asopagos.solicitud.composite.service.SolicitudCompositeService#generarListadoSolicitudesRemisionBackAsignado(java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.core.ProcesoEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public RemisionBackDTO generarListadoSolicitudesRemisionBackAsignado(Long fechaInicial, Long fechaFinal, ProcesoEnum proceso,
            UserDTO userDTO) {
        try {
            GenerarListadoSolicitudesRemisionBack remisionesBackService = new GenerarListadoSolicitudesRemisionBack(fechaInicial, proceso,
                    fechaFinal, userDTO.getNombreUsuario());
            remisionesBackService.execute();
            return remisionesBackService.getResult();

        } catch (Exception e) {
            logger.error("Error inesperado en generarListadoSolicitudesRemisionBackAsignado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    @Override
    public String retomarTareasErrorBPM(UserDTO user) {
        logger.debug("Inicia retomarTareasErrorBPM()");
        
        String mensajeExitoso = "Procesado sin errores";
        String mensajeErrores = "Procesado con errores";
        Boolean hayError = false;
        
        ActivarTarea activarSrv =  null;
        PersistirResultadoUtiliarioBPM persistirSrv = null;
        int count = 0;
        
        ObtenerTareasCreadasSinPropietario tareasSrv = new ObtenerTareasCreadasSinPropietario();
        tareasSrv.execute();
        
        List<TareaDTO> tareasList = tareasSrv.getResult();
        int total = tareasList.size();
        
        logger.info("Solicitudes a procesar " + total);
        
        for (TareaDTO tarea : tareasList) {
            try {
                activarSrv = new ActivarTarea(tarea.getId(), new HashMap<String, Object>());
                activarSrv.execute();
                persistirSrv = new PersistirResultadoUtiliarioBPM(EN_BANDEJA, user.getNombreUsuario(), tarea.getDescripcion());
                persistirSrv.execute();
            } catch (Exception e) {
                hayError = true;
                persistirSrv = new PersistirResultadoUtiliarioBPM(ERROR, user.getNombreUsuario(), tarea.getDescripcion());
                persistirSrv.execute();
                e.printStackTrace();
            }
            logger.info("Solicitudes procesadas " + ++count + "/" + total);
        }
        logger.debug("Finaliza retomarTareasErrorBPM()");
        return hayError ? mensajeErrores:mensajeExitoso;
    }
    
    public String rechazarSolicitudesErrorBPM(UserDTO usuario) {
        
        PersistirResultadoUtiliarioBPM persistirSrv = null;
        Boolean hayErrorAfiliacion = false;
        Boolean hayErrorNovedad = false;
        String mensajeExitoso = "procesadas sin errores";
        String mensajeErrores = "procesadas con errores";
        String resultado = null;
        int countNov = 0;
        int countAfi = 0;
        
        //Se rechazan solicitudes de afiliación
        EstadoSolicitudAfiliacionPersonaEnum estadoAfiliacion = null;
        Long idSolicitudGlobal = null;
        
        List<SolicitudAfiliacionPersonaDTO> solicitudesAfiliacion =  this.obtenerAfiliacionesSinInstanciaProceso();
        int totalAfiliaciones = solicitudesAfiliacion.size();
        logger.info("AFILIACIONES A PROCESAR " + totalAfiliaciones);
        for (SolicitudAfiliacionPersonaDTO solicitud : solicitudesAfiliacion) {
            try {
                estadoAfiliacion = solicitud.getEstadoSolicitud();
                idSolicitudGlobal = solicitud.getIdSolicitudGlobal();
                switch (estadoAfiliacion) {
                    case PRE_RADICADA:
                        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
                        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
                        persistirSrv = new PersistirResultadoUtiliarioBPM(SOLICITUD_RECHAZADA, usuario.getNombreUsuario(), solicitud.getNumeroRadicacion());
                        persistirSrv.execute();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                hayErrorAfiliacion = true;
                persistirSrv = new PersistirResultadoUtiliarioBPM(ERROR, usuario.getNombreUsuario(), solicitud.getNumeroRadicacion());
                persistirSrv.execute();
                e.printStackTrace();
            }
            logger.info("Afiliaciones procesadas " + ++countAfi + "/" + totalAfiliaciones);
        }
        
        //Se rechazan las solicitudes de novedad
        EstadoSolicitudNovedadEnum estadoNovedad = null;
        
        List<SolicitudNovedadGeneralDTO> solicitudesNovedad = this.obtenerNovedadesSinInstanciaProceso();
        int totalNovedades = solicitudesNovedad.size();
        System.out.println("NOVEDADES A PROCESAR " + totalNovedades);
        
        List<ProcesoEnum> procesosNovedades = new ArrayList<ProcesoEnum>();
        procesosNovedades.add(ProcesoEnum.NOVEDADES_PERSONAS_PRESENCIAL);
        procesosNovedades.add(ProcesoEnum.NOVEDADES_EMPRESAS_PRESENCIAL);
        
        for (SolicitudNovedadGeneralDTO solicitud : solicitudesNovedad) {
            
            try {
                estadoNovedad = solicitud.getEstado();
                idSolicitudGlobal = solicitud.getIdSolicitudGlobal();
                
                switch (estadoNovedad) {
                    case RADICADA:
                    case PENDIENTE_ENVIO_AL_BACK:
                    case ASIGNADA_AL_BACK:
                        if (procesosNovedades.contains(solicitud.getTipoTransaccion().getProceso())) {
                            actualizarEstadoSolicitudNovedad(idSolicitudGlobal, EstadoSolicitudNovedadEnum.RECHAZADA);
                            actualizarEstadoSolicitudNovedad(idSolicitudGlobal, EstadoSolicitudNovedadEnum.CERRADA);
                            persistirSrv = new PersistirResultadoUtiliarioBPM(SOLICITUD_RECHAZADA, usuario.getNombreUsuario(), solicitud.getNumeroRadicado());
                            persistirSrv.execute();
                        }
                        break;

                    default:
                        break;
                }
            } catch (Exception e) {
                hayErrorNovedad = true;
                persistirSrv = new PersistirResultadoUtiliarioBPM(ERROR, usuario.getNombreUsuario(), solicitud.getNumeroRadicado());
                persistirSrv.execute();
                e.printStackTrace();
            }
            logger.info("Novedades procesadas " + ++countNov + "/" + totalNovedades);
        }
        
        if (!hayErrorAfiliacion) {
            resultado = "Afiliaciones " + mensajeExitoso;
        }else
            resultado = "Afiliaciones " + mensajeErrores;
        
        if (!hayErrorNovedad) {
            resultado += " Novedades " + mensajeExitoso;
        }else
            resultado += " Novedades " + mensajeErrores;
        
        
        return resultado;
    }
    
    /**
     * Consulta las solicitudes de afiliacion de persona sin instancia de proceso
     * @return
     */
    private List<SolicitudAfiliacionPersonaDTO> obtenerAfiliacionesSinInstanciaProceso(){
        logger.debug("Inicia obtenerAfiliacionesSinInstanciaProceso()");
        ObtenerAfiliacionesSinInstanciaProceso afiliacionesSrv = new ObtenerAfiliacionesSinInstanciaProceso();
        afiliacionesSrv.execute();
        logger.debug("Finaliza obtenerAfiliacionesSinInstanciaProceso()");
        return afiliacionesSrv.getResult();
    }
    
    /**
     * Método que hace la peticion REST al servicio de actualizar el estado de
     * una Solicitud de Afiliacion de Persona indicado en
     * <code>EstadoSolicitudAfiliacionPersonaEnum</code>
     * 
     * @param idSolicitudGlobal
     *        <code>Long</code> El identificador de la solicitud global de
     *        afiliacion de persona
     * @param estadoSolcitudAfiliacionPersona
     *        <code>EstadoSolicitudAfiliacionPersonaEnum</code> Enumeración
     *        que representa los estados de la solicitud de afiliación de
     *        una persona
     */
    private void actualizarEstadoSolicitudPersona(Long idSolicitudGlobal,
            EstadoSolicitudAfiliacionPersonaEnum estadoSolcitudAfiliacionPersona) {
        logger.debug("Inicia actualizarEstadoSolicitudPersona( idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum )");
        ActualizarEstadoSolicitudAfiliacionPersona actualizarSoliticutdAfilPersonaService = new ActualizarEstadoSolicitudAfiliacionPersona(
                idSolicitudGlobal, estadoSolcitudAfiliacionPersona);
        actualizarSoliticutdAfilPersonaService.execute();
        logger.debug("Finaliza actualizarEstadoSolicitudPersona( idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum )");
    }
    
    /**
     * Consulta las solicitudes de novedad sin instancia de proceso
     * @return
     */
    private List<SolicitudNovedadGeneralDTO> obtenerNovedadesSinInstanciaProceso(){
        logger.debug("Inicia obtenerNovedadesSinInstanciaProceso()");
        ObtenerNovedadesSinInstanciaProceso novedadesSrv = new ObtenerNovedadesSinInstanciaProceso();
        novedadesSrv.execute();
        logger.debug("Finaliza obtenerNovedadesSinInstanciaProceso()");
        return novedadesSrv.getResult();
    }
    
    /**
     * Método que hace la peticion REST al servicio que actualiza el estado de
     * una novedad
     */
    private void actualizarEstadoSolicitudNovedad(Long idSolicitud, EstadoSolicitudNovedadEnum estadoSolicitud) {
        logger.debug("Inicia actualizarEstadoSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
        ActualizarEstadoSolicitudNovedad actualizarEstadoSolNovedadService = new ActualizarEstadoSolicitudNovedad(
                idSolicitud, estadoSolicitud);
        actualizarEstadoSolNovedadService.execute();
        logger.debug("Finaliza actualizarEstadoSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
    }
}
