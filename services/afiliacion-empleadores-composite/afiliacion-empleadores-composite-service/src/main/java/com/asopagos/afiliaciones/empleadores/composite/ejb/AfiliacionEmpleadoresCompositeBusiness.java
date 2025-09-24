package com.asopagos.afiliaciones.empleadores.composite.ejb;

import static com.asopagos.util.Interpolator.interpolate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import com.asopagos.afiliaciones.clients.ActualizarEstadoDocumentacionAfiliacion;
import com.asopagos.afiliaciones.clients.CambiarEstadoRegistroLista;
import com.asopagos.afiliaciones.clients.ConsultarListaEspecialRevision;
import com.asopagos.afiliaciones.clients.ConsultarRegistroListaEspecialRevision;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliaciones.clients.RegistrarIntentoAfliliacion;
import com.asopagos.afiliaciones.clients.RegistrarPersonaEnListaEspecialRevision;
import com.asopagos.afiliaciones.dto.ActualizacionEstadoListaEspecialDTO;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.dto.ListaEspecialRevisionDTO;
import com.asopagos.afiliaciones.empleadores.clients.ActualizarEstadoSolicitudAfiliacion;
import com.asopagos.afiliaciones.empleadores.clients.ActualizarFechaDesafiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.clients.ActualizarSolicitudAfiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarSolicitudAfiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.clients.CrearSolicitudAfiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.composite.constants.NamedQueriesConstants;
import com.asopagos.afiliaciones.empleadores.composite.constants.ResultadosProductoNoConformeConstants;
import com.asopagos.afiliaciones.empleadores.composite.dto.AsignarSolicitudAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.GestionarProductoNoConformeSubsanableDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.GuardarDataTemporal;
import com.asopagos.afiliaciones.empleadores.composite.dto.ProcesoAfiliacionEmpleadoresPresencialDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.RadicarSolicitudAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.VerificarInformacionSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.VerificarResultadosProductoNoConformeDTO;
import com.asopagos.afiliaciones.empleadores.composite.service.AfiliacionEmpleadoresCompositeService;
import com.asopagos.afiliados.clients.DesafiliarBeneficiarioEmpleador;
import com.asopagos.aportes.clients.ActualizarReconocimientoAportes;
import com.asopagos.aportes.clients.ConsultarAporteDetalladoPorIdsGeneral;
import com.asopagos.aportes.clients.ConsultarAporteGeneralEmpleador;
import com.asopagos.aportes.clients.CrearActualizarAporteGeneral;
import com.asopagos.aportes.clients.CrearAporteDetallado;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.comunicados.clients.GenerarYGuardarDatoTemporalComunicado;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.AnalizarSolicitudAfiliacionDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.InformacionAdicionalDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.JsonPayloadDatoTemporalComunicadoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.PlantillaComunicadoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.DetalleAportesFuturosDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.EncabezadoAportesFuturosDTO;
import com.asopagos.empleadores.clients.ActualizarEmpleador;
import com.asopagos.empleadores.clients.ActualizarEstadoEmpleador;
import com.asopagos.empleadores.clients.BuscarEmpleador;
import com.asopagos.empleadores.clients.ConsultarDatosTemporalesEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.empleadores.clients.CrearEmpleador;
import com.asopagos.empleadores.clients.CrearRepresentanteLegal;
import com.asopagos.empleadores.clients.CrearRolContactoEmpleador;
import com.asopagos.empleadores.clients.EstablecerResponsablesCajaCompensacion;
import com.asopagos.empleadores.clients.GestionarSociosEmpleador;
import com.asopagos.empresas.clients.CrearEmpresa;
import com.asopagos.empresas.clients.CrearSucursalEmpresa;
//modificacion 12/08/2021
 import com.asopagos.empresas.clients.ActualizarUbicacionesEmpresa;
//fin modificacion 12/08/2021
import com.asopagos.empresas.clients.CrearUbicacionesEmpresa;
import com.asopagos.entidades.ccf.afiliaciones.EscalamientoSolicitud;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoAnalisisAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralProductoNoConformeEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.comunicados.PlantillaProcesoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.EstadoListaEspecialRevisionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.listaschequeo.clients.GuardarListaChequeo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadMasiva;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.parametros.publicador.clients.ReplicarCambioEstadoListaEspecialRevision;
import com.asopagos.parametros.publicador.clients.ReplicarInsercionListaEspecialRevision;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ActualizarSolicitudEscalada;
import com.asopagos.solicitudes.clients.EscalarSolicitud;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.CrearUsuarioAdminEmpleador;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioEmpleadorDTO;
import com.asopagos.validaciones.clients.ValidarEmpleadores;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import com.asopagos.util.CalendarUtils;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import com.asopagos.usuarios.clients.CrearUsuarioAdminEmpleadorMasivo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.asopagos.constants.ParametrosGapConstants;
import java.time.LocalDate;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarSolicitud;
import com.asopagos.afiliaciones.empleadores.dto.RespuestaConsultaSolicitudDTO;

import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarSolicitudAfiliacionEmpleadorAnteriores;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;

/**
 * <b>Descripcion:</b> EJB que implementa los servicios de composición del
 * proceso de afilñiación de empleadores
 *
 * @author Sergio Briñez
 *         <a href="mailto:sbrinez@heinsohn.com.co"> sbrinez@heinsohn.com.co</a>
 */
@Stateless
public class AfiliacionEmpleadoresCompositeBusiness implements AfiliacionEmpleadoresCompositeService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "afiliacion_empleadores_composite_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(AfiliacionEmpleadoresCompositeBusiness.class);

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.empleadores.composite.service.AfiliacionEmpleadoresCompositeService#verificarInformacionSolicitud(java.lang.String,
     *      com.asopagos.afiliaciones.empleadores.composite.dto.VerificarInformacionSolicitudDTO)
     */
    @Override
    public void verificarInformacionSolicitud(VerificarInformacionSolicitudDTO inDTO, UserDTO userDTO) {

        Integer resultadoBack = null;
        switch (inDTO.getResultadoGeneral()) {
            case SUBSANABLE: {
                resultadoBack = 1;
                break;
            }
            case NO_SUBSANABLE: {
                resultadoBack = 2;
                break;
            }
            case APROBADA: {
                resultadoBack = 3;
                break;
            }
        }

        // Se actualiza el estado de la solicitud
        actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(),
                inDTO.getSolicitudAfiliacion().getEstadoSolicitud());

        if (inDTO.getResultadoGeneral().equals(ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE)) {
            /* Si la solicitud es rechazada se invoca la novedad automática de desafiliación */
            this.ejecutarNovedadDesafiliacion(inDTO.getSolicitudAfiliacion().getIdEmpleador());
        }

        // Si se aprueba o rechaza la solicitud automaticamente se cambia el estado de la solicitud a "CERRADA"
        if (inDTO.getSolicitudAfiliacion().getEstadoSolicitud().equals(EstadoSolicitudAfiliacionEmpleadorEnum.APROBADA)
                || inDTO.getSolicitudAfiliacion().getEstadoSolicitud().equals(EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA)) {
            //if(inDTO.getSolicitudAfiliacion().getEstadoSolicitud().equals(EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA)){
            //    desafiliarBeneficiarioEmpleador(inDTO.getSolicitudAfiliacion().getIdEmpleador(),
            //            MotivoDesafiliacionBeneficiarioEnum.AFILIACION_ANULADA);
            //}           
            actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(),
                    EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("datosTarea", resultadoBack);
        TerminarTarea terminarTareaService = new TerminarTarea(inDTO.getIdTarea(), params);
        terminarTareaService.execute();

    }

    /**
     * Ejecuta la novedad de Desafiliación cuando la solicitud es rechazada.
     * @param idEmpleador
     */
    private void ejecutarNovedadDesafiliacion(Long idEmpleador) {
        /* Se invoca la novedad de Desafiliación por solicitud Rechazada. */
        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
        /* Se asigna el tipo de transaccion de Novedad de Desafiliación. */
        solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_SOLICITUD_RECHAZADA);
        DatosNovedadAutomaticaDTO datosNovedad = new DatosNovedadAutomaticaDTO();
        // Se crea la lista de empleadores
        List<Long> idEmpleadores = new ArrayList<>();
        idEmpleadores.add(idEmpleador);
        datosNovedad.setIdEmpleadores(idEmpleadores);
        /* Se asigna el empleador a desafiliar. */
        solNovedadDTO.setDatosNovedadMasiva(datosNovedad);
        /* Radica la solicitud de Novedad. */
        RadicarSolicitudNovedadMasiva radicarSolicitudNovedadMasivaService =  new RadicarSolicitudNovedadMasiva(solNovedadDTO);
        radicarSolicitudNovedadMasivaService.execute();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.empleadores.composite.service.AfiliacionEmpleadoresCompositeService#analizarSolicitudAfiliacion(java.lang.String,
     *      com.asopagos.afiliaciones.empleadores.composite.dto.AnalizarSolicitudAfiliacionDTO)
     */
    @Override
    public void analizarSolicitudAfiliacion(AnalizarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) {

        // Se consulta el estado actual de la entidad
        EscalamientoSolicitud escalamientoActual = consultarEscalamientoSolicitudAfiliacion(
                inDTO.getRegistroResultado().getIdEscalamientoSolicitud());

        Integer resultadoAnalisis;
        Long idSolicitudAfiliacionEmpleador = consultarIdSolicitudAfiliacionEmpleador(inDTO.getRegistroResultado().getIdSolicitud());
        // El servicio es invocado desde el analista porque aun no se ha
        // registrado el resultado
        if (escalamientoActual.getResultadoAnalista() == null) {
            // Se llama el servicio de
            // AfiliacionEmpleadoresService.actualizarEstadoSolicitudAfiliacion
            actualizarEstadoSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador,
                    EstadoSolicitudAfiliacionEmpleadorEnum.GESTIONADA_POR_ESPECIALISTA);
            // se actualiza la solicitud de escalamiento
            ActualizarSolicitudEscalada actualizarSolicitudAfiliacionEscalada = new ActualizarSolicitudEscalada(
                    inDTO.getRegistroResultado().getIdSolicitud(), inDTO.getRegistroResultado());
            actualizarSolicitudAfiliacionEscalada.execute();
            resultadoAnalisis = inDTO.getRegistroResultado().getResultadoAnalista().getCodigo();

        }
        else {
            // El servicio es invocado desde el front
            // Se usa el resultado del escalamiento actual y no el recibido por
            // parámetro
            // para evitar que el front envié una petición con otro el resultado
            // modificado
        	 //se pasa la data temporal a real
            Long idEmpleador = consultarSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador).getIdEmpleador();
            Empleador empleador = entityManager.find(Empleador.class, idEmpleador);
            
            ConsultarDatosTemporalesEmpleador consultTemporal = new ConsultarDatosTemporalesEmpleador(
                    inDTO.getRegistroResultado().getIdSolicitud());
            consultTemporal.execute();
            String dataTemporal = consultTemporal.getResult();
            GuardarDataTemporal guardarTemporal = new GuardarDataTemporal();
            if (escalamientoActual.getResultadoAnalista() == ResultadoAnalisisAfiliacionEnum.SOLICITUD_PROCEDENTE) {
                
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    guardarTemporal = mapper.readValue(dataTemporal, GuardarDataTemporal.class);
                    //return null;

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getEmpleador() != null) {
                        ActualizarEmpleador updateEmpleador = new ActualizarEmpleador(empleador.getIdEmpleador(),
                                guardarTemporal.getEmpleador());
                        if(empleador.getFechaRetiroTotalTrabajadores()==null){
                            guardarTemporal.getEmpleador().setFechaRetiroTotalTrabajadores(new Date());
                        }
                        updateEmpleador.execute();
                    }

                    //se actualiza las ubicaciones con la información diligenciada
                    if (guardarTemporal.getUbicaciones() != null && !guardarTemporal.getUbicaciones().isEmpty()) {
                        CrearUbicacionesEmpresa crearUbicacion = new CrearUbicacionesEmpresa(empleador.getEmpresa().getIdEmpresa(),
                                guardarTemporal.getUbicaciones());
                        crearUbicacion.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getRepresentante1() != null
                        && guardarTemporal.getRepresentante1().getNumeroIdentificacion() != null
                        && guardarTemporal.getRepresentante1().getTipoIdentificacion() != null
                        && guardarTemporal.getRepresentante1().getPrimerNombre() != null
                        && guardarTemporal.getRepresentante1().getPrimerApellido() != null) {
                        CrearRepresentanteLegal crearRepreLegal = new CrearRepresentanteLegal(empleador.getIdEmpleador(), true,
                                guardarTemporal.getRepresentante1());
                        crearRepreLegal.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getRepresentante2() != null
                        && guardarTemporal.getRepresentante2().getNumeroIdentificacion() != null
                        && guardarTemporal.getRepresentante2().getTipoIdentificacion() != null
                        && guardarTemporal.getRepresentante2().getPrimerNombre() != null
                        && guardarTemporal.getRepresentante2().getPrimerApellido() != null) {
                        CrearRepresentanteLegal crearRepreLegalSuple = new CrearRepresentanteLegal(empleador.getIdEmpleador(), false,
                                guardarTemporal.getRepresentante2());
                        crearRepreLegalSuple.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getSocios() != null && !guardarTemporal.getSocios().isEmpty()) {
                        GestionarSociosEmpleador crearSocios = new GestionarSociosEmpleador(empleador.getIdEmpleador(),
                                guardarTemporal.getSocios());
                        crearSocios.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getSucursales() != null && !guardarTemporal.getSucursales().isEmpty()) {
                        CrearSucursalEmpresa crearSucursal = new CrearSucursalEmpresa(empleador.getEmpresa().getIdEmpresa(),
                                guardarTemporal.getSucursales());
                        crearSucursal.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getRolafiliaciones() != null && !guardarTemporal.getRolafiliaciones().isEmpty()) {
                        CrearRolContactoEmpleador creaRolesContactos = new CrearRolContactoEmpleador(empleador.getIdEmpleador(),
                                guardarTemporal.getRolafiliaciones());
                        creaRolesContactos.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getResponsables() != null && !guardarTemporal.getResponsables().isEmpty()) {
                        EstablecerResponsablesCajaCompensacion crearResponsables = new EstablecerResponsablesCajaCompensacion(
                                empleador.getIdEmpleador(), guardarTemporal.getResponsables());
                        crearResponsables.execute();
                    }

                } catch (Exception e) {
                    logger.debug(
                            "AfiliacionEmpleadoresCompositeBusiness.iniciarProcesoAfliliacionEmpleadoresPresencial :: No se logro crear la solcitud de afiliacion de empresas",e);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_ACTUALIZAR_RECURSO);
                    // TODO: handle exception
                }
                
                // Se actualiza el estado de la solicitud
                actualizarEstadoSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador,
                        EstadoSolicitudAfiliacionEmpleadorEnum.PENDIENTE_ENVIO_AL_BACK);

                // se actualiza el estado del empleador a activo
                actualizarEstadoEmpleador(idEmpleador, EstadoEmpleadorEnum.ACTIVO);

                SolicitudAfiliacionEmpleador sae = new SolicitudAfiliacionEmpleador();
                ConsultarSolicitudAfiliacionEmpleador csae = new ConsultarSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador);
                csae.execute();
                sae = (SolicitudAfiliacionEmpleador) csae.getResult();

                // Se actualiza el estado de la documentación
                actualizarEstadoDocumentacionAfiliacion(sae.getSolicitudGlobal().getIdSolicitud(),
                        EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
                if(empleador.getEmpresa().getIdPersonaRepresentanteLegal()==null)
                	empleador = consultarEmpleador(idEmpleador);
                Long idRepresentaLegal = empleador.getEmpresa().getIdPersonaRepresentanteLegal();
                if (idRepresentaLegal != null) {
                    Persona persona = entityManager.find(Persona.class, idRepresentaLegal);
                    UsuarioEmpleadorDTO usuarioEmpleadorDTO = new UsuarioEmpleadorDTO();
                    if (empleador.getEmpresa().getIdUbicacionRepresentanteLegal() != null) {
                        Ubicacion ubicacion = entityManager.find(Ubicacion.class,
                                empleador.getEmpresa().getIdUbicacionRepresentanteLegal());

                        if (ubicacion.getEmail() != null) {
                            usuarioEmpleadorDTO.setEmail(ubicacion.getEmail());
                            usuarioEmpleadorDTO.setPrimerApellido(persona.getPrimerApellido());
                            usuarioEmpleadorDTO.setPrimerNombre(persona.getPrimerNombre());
                            usuarioEmpleadorDTO
                                    .setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion().toString());
                            usuarioEmpleadorDTO.setNumIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
                            usuarioEmpleadorDTO.setIdSolicitudGlobal(sae.getSolicitudGlobal().getIdSolicitud());
                            CrearUsuarioAdminEmpleador crearUsuarioAdminEmpleador = new CrearUsuarioAdminEmpleador(usuarioEmpleadorDTO);
                            crearUsuarioAdminEmpleador.execute();
                        }
                        else {
                            logger.warn("No se ingreso el email del representate legal, no se crea cuenta para empleador");
                        }
                    }
                }
            }
            else {
            	// Se persiste información de la empresa para envió de comunicado mantis 257885
            	try {
            		ObjectMapper mapper = new ObjectMapper();
            		guardarTemporal = mapper.readValue(dataTemporal, GuardarDataTemporal.class);
            		
            		 //se actualiza las ubicaciones con la información diligenciada
                    if (guardarTemporal.getUbicaciones() != null && !guardarTemporal.getUbicaciones().isEmpty()) {
                        CrearUbicacionesEmpresa crearUbicacion = new CrearUbicacionesEmpresa(empleador.getEmpresa().getIdEmpresa(),
                                guardarTemporal.getUbicaciones());
                        crearUbicacion.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getRepresentante1() != null) {
                        CrearRepresentanteLegal crearRepreLegal = new CrearRepresentanteLegal(empleador.getIdEmpleador(), true,
                                guardarTemporal.getRepresentante1());
                        crearRepreLegal.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getRepresentante2() != null) {
                        CrearRepresentanteLegal crearRepreLegalSuple = new CrearRepresentanteLegal(empleador.getIdEmpleador(), false,
                                guardarTemporal.getRepresentante2());
                        crearRepreLegalSuple.execute();
                    }
                    
                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getSucursales() != null && !guardarTemporal.getSucursales().isEmpty()) {
                        CrearSucursalEmpresa crearSucursal = new CrearSucursalEmpresa(empleador.getEmpresa().getIdEmpresa(),
                                guardarTemporal.getSucursales());
                        crearSucursal.execute();
                    }
            		
            	}catch(Exception e) {
            		  logger.debug(
                              "AfiliacionEmpleadoresCompositeBusiness.iniciarProcesoAfliliacionEmpleadoresPresencial :: No se logro persistir la información de la empresa",e);
                      throw new TechnicalException(MensajesGeneralConstants.ERROR_ACTUALIZAR_RECURSO);
            	}
            	
                // Se llama al servicio de actualiza el estado de la solicitud a "RECHAZADA"
                actualizarEstadoSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador,
                        EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA);

                // Se llama al servicio de actualiza el estado de la solicitud a "CERRADA" despues de rechazarse
                actualizarEstadoSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador,
                        EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);

            }
            resultadoAnalisis = escalamientoActual.getResultadoAnalista().getCodigo();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("datosTarea", resultadoAnalisis);
        TerminarTarea terminarTareaService = new TerminarTarea(inDTO.getIdTarea(), params);
        terminarTareaService.execute();
    }

    /**
     * Método que se encarga de recuperar el id de la solicitud afiliación
     * Empleador a partir de la solicitud global
     * 
     * @param idSolicitudGlobal
     *        id de la solicitud genérica
     * 
     * @return id de la solicitud específica la de la afilicación empleador
     */
    private Long consultarIdSolicitudAfiliacionEmpleador(Long idSolicitudGlobal) {
        Long id = (Long) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_ESPECIFICA)
                .setParameter("idSolicitudGlobal", idSolicitudGlobal).getSingleResult();

        return id;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliaciones.empleadores.composite.service.AfiliacionEmpleadoresCompositeService#radicarSolicitudAfiliacionYActivarEmpleador(com.asopagos.afiliaciones.empleadores.composite.dto.RadicarSolicitudAfiliacionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, Object> radicarSolicitudAfiliacionYActivarEmpleador(RadicarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) throws IOException{
        logger.debug(interpolate(
                "Inicia radicarSolicitudAfiliacionYActivarEmpleador(RadicarSolicitudAfiliacionDTO, UserDTO)  idEmpleador:{0}, resultadoRadicacion: {1}",
                inDTO.getSolicitudAfiliacion().getIdEmpleador(), inDTO.getResultadoRadicacion()));
        if (inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador() == null) {
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

        Map<String, Object> variablesRetorno = new HashMap<>();
        String numeroRadicado = "";
        int estado = 0;
        Map<String, Object> params = new HashMap<>();
        Empleador empleador = consultarEmpleador(inDTO.getSolicitudAfiliacion().getIdEmpleador());
        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoIdentificacion", empleador.getEmpresa().getPersona().getTipoIdentificacion().toString());
        datosValidacion.put("numeroIdentificacion", empleador.getEmpresa().getPersona().getNumeroIdentificacion());
        Short dv = empleador.getEmpresa().getPersona().getDigitoVerificacion();
        datosValidacion.put("digitoVerificacion", dv != null ? String.valueOf(dv) : null);
        // Mantis 0252654 - Se envia el valor de la solicitud global de afiliación
        datosValidacion.put("idSolicitud", inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud().toString());

        switch (inDTO.getResultadoRadicacion()) {

            case EXITOSA:

                ValidarEmpleadores validar = new ValidarEmpleadores("111-066-1", ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL,
                        datosValidacion);
                validar.execute();
                List<ValidacionDTO> list = validar.getResult();

                ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR, list);

                //Se valida de que el empleador no tenga mas solicitudes inicializadas
                if (validacionExistenciaSolicitud !=null && validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                    variablesRetorno.put(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR.name(),
                            ResultadoValidacionEnum.NO_APROBADA.name());
                    registrarIntentoSolicitudSimultanea(inDTO, datosValidacion);
                    return variablesRetorno;
                }

                ConsultarDatosTemporalesEmpleador consultTemporal = new ConsultarDatosTemporalesEmpleador(
                        inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud());
                consultTemporal.execute();
                String dataTemporal = consultTemporal.getResult();
                
                Gson gson = new Gson();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                JsonElement element = gson.fromJson (dataTemporal, JsonElement.class);
                JsonObject jsonObj = element.getAsJsonObject();
                JsonObject objEmpleador = jsonObj.getAsJsonObject("empleador");
                JsonObject objEmpresa = objEmpleador.getAsJsonObject("empresa");
                
                if(objEmpleador.get("periodoUltimaNomina") != null){
                    Float sunriseFloat = objEmpleador.get("periodoUltimaNomina").getAsFloat();
                    Date periodoUltimaNomina = new Date(sunriseFloat.longValue());
                    objEmpleador.addProperty("periodoUltimaNomina",formatter.format(periodoUltimaNomina));
                }
                if(objEmpresa.get("fechaConstitucion") != null){
                    Float sunriseFloat2 = objEmpresa.get("fechaConstitucion").getAsFloat();
                    Date fechaConstitucion = new Date(sunriseFloat2.longValue());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fechaConstitucion);
                    logger.info("Prueba fecha" + cal.getTime());
                    cal.add(Calendar.HOUR_OF_DAY, 19);
                    logger.info("Prueba fecha 1" + cal.getTime());
                    objEmpresa.addProperty("fechaConstitucion",formatter.format(cal.getTime()));
                }

                dataTemporal = gson.toJson(jsonObj);
                
                GuardarDataTemporal guardarTemporal = new GuardarDataTemporal();
                ObjectMapper mapper = new ObjectMapper();
                guardarTemporal = mapper.readValue(dataTemporal, GuardarDataTemporal.class);

                try {
                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getEmpleador() != null) {
                        logger.info("Prueba 1 "+ guardarTemporal.getEmpleador().getPeriodoUltimaNomina());
                        if(empleador.getFechaRetiroTotalTrabajadores()==null){
                            guardarTemporal.getEmpleador().setFechaRetiroTotalTrabajadores(new Date());
                        }
                        if(guardarTemporal.getEmpleador().getPeriodoUltimaNomina()!=null) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(guardarTemporal.getEmpleador().getPeriodoUltimaNomina());
                            calendar.add(Calendar.DATE, 5);
                            guardarTemporal.getEmpleador().setPeriodoUltimaNomina(calendar.getTime());
                        }  
                         logger.info("Prueba 2 "+ guardarTemporal.getEmpleador().getPeriodoUltimaNomina());
                        ActualizarEmpleador updateEmpleador = new ActualizarEmpleador(empleador.getIdEmpleador(),
                         guardarTemporal.getEmpleador());
                        updateEmpleador.execute();
                    }       
                try {
                    //se actualiza las ubicaciones con la información diligenciada
                    if (guardarTemporal.getUbicaciones() != null && !guardarTemporal.getUbicaciones().isEmpty()) {
                        CrearUbicacionesEmpresa crearUbicacion = new CrearUbicacionesEmpresa(empleador.getEmpresa().getIdEmpresa(),
                                guardarTemporal.getUbicaciones());
                        crearUbicacion.execute();
                    }
            
                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getRepresentante1() != null) {
                        CrearRepresentanteLegal crearRepreLegal = new CrearRepresentanteLegal(empleador.getIdEmpleador(), true,
                                guardarTemporal.getRepresentante1());
                        crearRepreLegal.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getRepresentante2() != null) {
                        CrearRepresentanteLegal crearRepreLegalSuple = new CrearRepresentanteLegal(empleador.getIdEmpleador(), false,
                                guardarTemporal.getRepresentante2());
                        crearRepreLegalSuple.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getSocios() != null && !guardarTemporal.getSocios().isEmpty()) {
                        GestionarSociosEmpleador crearSocios = new GestionarSociosEmpleador(empleador.getIdEmpleador(),
                                guardarTemporal.getSocios());
                        crearSocios.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getSucursales() != null && !guardarTemporal.getSucursales().isEmpty()) {
                        CrearSucursalEmpresa crearSucursal = new CrearSucursalEmpresa(empleador.getEmpresa().getIdEmpresa(),
                                guardarTemporal.getSucursales());
                        crearSucursal.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getRolafiliaciones() != null && !guardarTemporal.getRolafiliaciones().isEmpty()) {
                        CrearRolContactoEmpleador creaRolesContactos = new CrearRolContactoEmpleador(empleador.getIdEmpleador(),
                                guardarTemporal.getRolafiliaciones());
                        creaRolesContactos.execute();
                    }

                    //Se actualiza el empleador con la información diligenciada 
                    if (guardarTemporal.getResponsables() != null && !guardarTemporal.getResponsables().isEmpty()) {
                        EstablecerResponsablesCajaCompensacion crearResponsables = new EstablecerResponsablesCajaCompensacion(
                                empleador.getIdEmpleador(), guardarTemporal.getResponsables());
                        crearResponsables.execute();
                    }
                    
                    } catch (FunctionalConstraintException | PersistenceException e) {
                    logger.error(interpolate(
                            "Error de persistencia en radicarSolicitudAfiliacionYActivarEmpleador(RadicarSolicitudAfiliacionDTO, UserDTO)  idEmpleador:{0}, resultadoRadicacion: {1}",
                            inDTO.getSolicitudAfiliacion().getIdEmpleador(), inDTO.getResultadoRadicacion()), e);
                    // Se realiza manejo de solicitud simultanea
                    variablesRetorno.put(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR.name(),
                            ResultadoValidacionEnum.NO_APROBADA.name());
                    registrarIntentoSolicitudSimultanea(inDTO, datosValidacion);
                    return variablesRetorno;
                }

                } catch (FunctionalConstraintException | PersistenceException e) {
                    logger.error(interpolate(
                            "Error de persistencia en radicarSolicitudAfiliacionYActivarEmpleador(RadicarSolicitudAfiliacionDTO, UserDTO)  idEmpleador:{0}, resultadoRadicacion: {1}",
                            inDTO.getSolicitudAfiliacion().getIdEmpleador(), inDTO.getResultadoRadicacion()), e);
                    // Se realiza manejo de solicitud simultanea
                    variablesRetorno.put(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR.name(),
                            ResultadoValidacionEnum.NO_APROBADA.name());
                    registrarIntentoSolicitudSimultanea(inDTO, datosValidacion);
                    return variablesRetorno;
                }
                
                // Se actualiza el estado del empleador
                actualizarEstadoEmpleador(inDTO.getSolicitudAfiliacion().getIdEmpleador(), EstadoEmpleadorEnum.ACTIVO);

                // Se actualiza la fecha de desafiliacion del empleador
                actualizarFechaDesafiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdEmpleador());
                
                // Se realiza el reconocimiento de aportes "Retroactivo Automático" cuando se activa el empleador -> HU-262
                ejecutarRetroactivoAutomaticoEmpleador(inDTO.getSolicitudAfiliacion().getIdEmpleador());

                // Se actualiza el estado de la solicitud de afiliacion
                actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.PENDIENTE_ENVIO_AL_BACK);

                // se actualiza la solicitud global con el número y la fecha del
                // radicado

                // se validada de que la solicitud aun no tenga numero de radicado
                // se produce cuando hay un posible error en el servicio de iniciar
                // proceso
                if (inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getNumeroRadicacion() == null) {
                    if(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud() == null){
                        logger.error("****** Error de parametro idSolicitud **********");
                    } else {
                        logger.info("****** Parametro idSolicitud **" + inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud() + "**");
                    }
                    if(userDTO.getSedeCajaCompensacion() == null){
                        logger.error("****** Error de parametro SedeCaja **********");
                    }else {
                        logger.info("****** Parametro SedeCaja **" + userDTO.getSedeCajaCompensacion() + "**");
                    }
                    numeroRadicado = radicarSolicitudAfiliacion(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud(),
                            userDTO.getSedeCajaCompensacion());
                    variablesRetorno.put("numeroRadicado", numeroRadicado);
                }

                // Se actualiza el estado de la documentación
                actualizarEstadoDocumentacionAfiliacion(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud(),
                        EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
                estado = 1;

                // se comenta ya que se consulta el empleador desde el inicio del
                // metodo y a traves de un cliente
                // Long idEmpleador =
                // inDTO.getSolicitudAfiliacion().getIdEmpleador();
                 //empleador = entityManager.find(Empleador.class,
                         //inDTO.getSolicitudAfiliacion().getIdEmpleador());
                
                empleador = consultarEmpleador(inDTO.getSolicitudAfiliacion().getIdEmpleador());
                Long idRepresentaLegal = empleador.getEmpresa().getIdPersonaRepresentanteLegal();
                if (idRepresentaLegal != null) {
                    Persona persona = entityManager.find(Persona.class, idRepresentaLegal);
                    UsuarioEmpleadorDTO usuarioEmpleadorDTO = new UsuarioEmpleadorDTO();
                    if (empleador.getEmpresa().getIdUbicacionRepresentanteLegal() != null) {

                        Ubicacion ubicacion = entityManager.find(Ubicacion.class,
                                empleador.getEmpresa().getIdUbicacionRepresentanteLegal());
                        logger.info("**Correo ubicacion.getEmail(): "+ubicacion.getEmail());
                        logger.info("**Correo empleador.getEmpresa().getIdUbicacionRepresentanteLegal(): "+empleador.getEmpresa().getIdUbicacionRepresentanteLegal());
                        if (ubicacion.getEmail() != null) {
                            usuarioEmpleadorDTO.setEmail(ubicacion.getEmail());
                            usuarioEmpleadorDTO.setPrimerApellido(persona.getPrimerApellido());
                            usuarioEmpleadorDTO.setPrimerNombre(persona.getPrimerNombre());
                            usuarioEmpleadorDTO
                                    .setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion().toString());
                            usuarioEmpleadorDTO.setNumIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
                            usuarioEmpleadorDTO.setIdSolicitudGlobal(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud());

                            CrearUsuarioAdminEmpleador crearUsuarioAdminEmpleador = new CrearUsuarioAdminEmpleador(usuarioEmpleadorDTO);
                            crearUsuarioAdminEmpleador.execute();
                        }
                        else {
                            logger.warn("No se ingreso el email del representate legal, no se crea cuenta para empleador");
                        }
                    }
                }

                break;

            case FALLIDA:
                // Se crea el registro de intento de afiliacion
                IntentoAfiliacionInDTO intentoAfiliacion = new IntentoAfiliacionInDTO();
                intentoAfiliacion.setCausaIntentoFallido(inDTO.getCausaIntentoFallido());
                intentoAfiliacion.setIdSolicitud(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud());
                intentoAfiliacion.setFechaInicioProceso(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getFechaCreacion());
                intentoAfiliacion.setTipoTransaccion(inDTO.getTipoTransaccion());
                intentoAfiliacion.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(datosValidacion.get("tipoIdentificacion")));
                intentoAfiliacion.setNumeroIdentificacion(datosValidacion.get("numeroIdentificacion"));

                registrarIntentoAfiliacion(intentoAfiliacion);

/*                if(TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION.equals(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getTipoTransaccion()) ||
                        TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION.equals(inDTO.getTipoTransaccion())){
                    Long idSolicitudAfiliacionEmpleador = inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador();
                    Long idEmpleador = inDTO.getSolicitudAfiliacion().getIdEmpleador();
                    
                    SolicitudAfiliacionEmpleador sae = entityManager.find(SolicitudAfiliacionEmpleador.class, idSolicitudAfiliacionEmpleador);
                    Empleador empl = entityManager.find(Empleador.class, idEmpleador);
                    Empresa emp = null;
                    //Persona per = null;
                    
                    /*if(sae != null){
                        entityManager.remove(sae);
                        entityManager.flush();
                    }
                    if(empl != null){
                        emp = entityManager.find(Empresa.class, empl.getEmpresa().getIdEmpresa());
                        entityManager.remove(empl);
                        entityManager.flush();                
                    }
                    /*if(emp != null){
                        //per = entityManager.find(Persona.class, emp.getPersona().getIdPersona());
                        entityManager.remove(emp);
                        entityManager.flush();
                    }*/
//                    if(per != null){
//                        entityManager.remove(per);
//                        entityManager.flush();
//                    }
//                }
//                else{
                 // Se actualiza el estado de la solicitud de afiliacion
                actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA);

                actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
//                }
                estado = 2;
                break;

            case PENDIENTE_ANALISIS:
                // Escalamiento de la solicitud
                if (inDTO.getSolicitudEscalamiento() == null) {
                    throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                }

                ValidarEmpleadores validarEscalada = new ValidarEmpleadores("111-066-1", ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL,
                        datosValidacion);
                validarEscalada.execute();
                List<ValidacionDTO> listValidaciones = validarEscalada.getResult();

                ValidacionDTO validacionExistenciaSolicitudEscalada = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
                        listValidaciones);
                if (validacionExistenciaSolicitudEscalada != null && validacionExistenciaSolicitudEscalada.getResultado()!=null && validacionExistenciaSolicitudEscalada.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                    variablesRetorno.put(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR.name(),
                            ResultadoValidacionEnum.NO_APROBADA.name());

                    registrarIntentoSolicitudSimultanea(inDTO, datosValidacion);
                    return variablesRetorno;
                }

                // se actualiza la solicitud global con el número y la fecha del
                // radicado
                if (inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getNumeroRadicacion() == null) {
                    numeroRadicado = radicarSolicitudAfiliacion(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud(),
                            userDTO.getSedeCajaCompensacion());
                    variablesRetorno.put("numeroRadicado", numeroRadicado);
                }

                // Se actaliza a estado escalada
                actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.ESCALADA);

                // Se ingresa el registro de escalamiento
                escalarSolicitudAfiliacion(inDTO.getSolicitudEscalamiento());
                estado = 3;
                params.put("destinatarioEscalamiento", inDTO.getSolicitudEscalamiento().getDestinatario());
                break;
        }
        params.put("estadoAfiliacion", estado);
        params.put("numeroRadicado", numeroRadicado);
        TerminarTarea terminarTarea = new TerminarTarea(inDTO.getIdTarea(), params);
        terminarTarea.execute();
        return variablesRetorno;
    }

    /**
     * Registra el intento de solicitud de afiliación simultanea por error de validación
     * @param inDTO
     *        Datos para el registro del intento
     * @param datosValidacion
     *        Datos usados en la validación
     */
    private void registrarIntentoSolicitudSimultanea(RadicarSolicitudAfiliacionDTO inDTO, Map<String, String> datosValidacion) {
        // Se crea el registro de intento de afiliacion
        IntentoAfiliacionInDTO intentoAfiliacion = new IntentoAfiliacionInDTO();
        intentoAfiliacion.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA);
        intentoAfiliacion.setIdSolicitud(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud());
        intentoAfiliacion.setFechaInicioProceso(inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getFechaCreacion());
        intentoAfiliacion.setTipoTransaccion(inDTO.getTipoTransaccion());
        intentoAfiliacion.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(datosValidacion.get("tipoIdentificacion")));
        intentoAfiliacion.setNumeroIdentificacion(datosValidacion.get("numeroIdentificacion"));
        registrarIntentoAfiliacion(intentoAfiliacion);

        //se cierra la solicitud sebido a que tinee mas solicitudes en proceso
        actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(),
                EstadoSolicitudAfiliacionEmpleadorEnum.CANCELADA);

        actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(),
                EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);

        //se finaliza la solicitud debido a que tiene mas solicitudes en proceso
        abortarProceso(inDTO.getIdInstanciaProceso());
    }

    /**
     * Método que hce la peticion REST al servicio de actualizar estado
     * documentación solicitud afiliación
     */
    private void actualizarEstadoDocumentacionAfiliacion(Long idSolicitudAfiliacion, EstadoDocumentacionEnum estadoDocumentacion) {
        logger.debug("Inicia actualizarEstadoEmpleador(Long, EstadoDocumentacionEnum)");
        ActualizarEstadoDocumentacionAfiliacion actualizarEstadoDocumentacionAfiliacion = new ActualizarEstadoDocumentacionAfiliacion(
                idSolicitudAfiliacion, estadoDocumentacion);
        actualizarEstadoDocumentacionAfiliacion.execute();
        logger.debug("Finaliza actualizarEstadoEmpleador(Long, EstadoDocumentacionEnum)");
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar estado del
     * empleador
     */
    private void actualizarEstadoEmpleador(Long idEmpleador, EstadoEmpleadorEnum estadoEmpleador) {
        logger.debug("Inicia actualizarEstadoEmpleador(Long, EstadoEmpleadorEnum)");
        ActualizarEstadoEmpleador actualizarEstadoEmpleador = new ActualizarEstadoEmpleador(idEmpleador, estadoEmpleador);
        actualizarEstadoEmpleador.execute();
        logger.debug("Finaliza actualizarEstadoEmpleador(Long, EstadoEmpleadorEnum)");
    }

    /**
     * Método que hace la peticion REST al servicio de registrar intento de
     * afiliación
     */
    private void registrarIntentoAfiliacion(IntentoAfiliacionInDTO intentoAfiliacionInDTO) {
        logger.debug("Inicia registrarIntentoAfiliacion(String, IntentoAfiliacionInDTO)");
        RegistrarIntentoAfliliacion registrarIntentoAfliliacion = new RegistrarIntentoAfliliacion(intentoAfiliacionInDTO);
        registrarIntentoAfliliacion.execute();
        logger.debug("Finaliza registrarIntentoAfiliacion(String, IntentoAfiliacionInDTO)");
    }

    /**
     * Método que hace la peticion REST al servicio de registrar intento de
     * afiliación
     */
    private void escalarSolicitudAfiliacion(EscalamientoSolicitudDTO escalamiento) {
        logger.debug("Inicia escalarSolicitudAfiliacion(EscalamientoSolicitudAfiliacionEmpleador)");
        EscalarSolicitud escalarSolicitudAfiliacion = new EscalarSolicitud(escalamiento.getIdSolicitud(), escalamiento);
        escalarSolicitudAfiliacion.execute();
        logger.debug("Finaliza escalarSolicitudAfiliacion(EscalamientoSolicitudAfiliacionEmpleador)");
    }

    /**
     * Método que hace la peticion REST al servicio de radicar la solicitud
     * afiliación
     * 
     * @param idSolicitud
     *        identificador de la solicitud global
     * @param sccID
     *        identificador de la sede
     */
    private String radicarSolicitudAfiliacion(Long idSolicitud, String sccID) {
        logger.debug("Inicia radicarSolicitudAfiliacion(Long, String)");
        RadicarSolicitud radicarSolicitud = new RadicarSolicitud(idSolicitud, sccID);
        radicarSolicitud.execute();
        logger.debug("Finaliza radicarSolicitudAfiliacion(Long, String)");
        return (String) radicarSolicitud.getResult();
    }

    /**
     * Método que permite consultar la solicitud de afiliación del empleador
     */
    private SolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleador(Long idSolicitudAfiliacionEmpleador) {
        return entityManager.find(SolicitudAfiliacionEmpleador.class, idSolicitudAfiliacionEmpleador);
    }

    /**
     * Método que permite consultar el registro de escalamiento de la solicitud
     */
    private EscalamientoSolicitud consultarEscalamientoSolicitudAfiliacion(Long idEscalamientoSolicitud) {
        return entityManager.find(EscalamientoSolicitud.class, idEscalamientoSolicitud);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.empleadores.composite.service.AfiliacionEmpleadoresCompositeService#gestionarProductoNoConformeSubsanable(java.lang.String,
     *      com.asopagos.afiliaciones.empleadores.composite.dto.GestionarProductoNoConformeSubsanableDTO)
     */
    @Override
    public void gestionarProductoNoConformeSubsanable(GestionarProductoNoConformeSubsanableDTO inDTO, UserDTO userDTO) {

        // Se actualiza el estado de la solicitud
        actualizarEstadoSolicitudAfiliacionEmpleador(inDTO.getIdSolicitud(), inDTO.getResultado().getEstadoSolicitud());
        // se termina la tarea
        Map<String, Object> maps = new HashMap<>();
        TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), maps);
        service2.execute();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.empleadores.composite.service.AfiliacionEmpleadoresCompositeService#asignarSolicitudAfiliacionEmpleador(java.lang.String,
     *      java.lang.String,
     *      com.asopagos.afiliaciones.empleadores.composite.dto.AsignarSolicitudAfiliacionDTO)
     */
    @Override
    public void asignarSolicitudAfiliacionEmpleador(AsignarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) {

        UsuarioCCF usuarioDTO = new UsuarioCCF();
        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacion = null;
        String destinatario = null;
        if (inDTO.getSolicitudAfiliacionEmpleador() != null && inDTO.getSolicitudAfiliacionEmpleador().getSolicitudGlobal() != null
                && inDTO.getSolicitudAfiliacionEmpleador().getSolicitudGlobal().getDestinatario() != null) {
            destinatario = inDTO.getSolicitudAfiliacionEmpleador().getSolicitudGlobal().getDestinatario();

        }
        else {
            EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL,
                    Long.parseLong(userDTO.getSedeCajaCompensacion()));
            ejecutarAsignacion.execute();
            destinatario = ejecutarAsignacion.getResult();
        }
        obtenerDatosUsuariosCajaCompensacion = new ObtenerDatosUsuarioCajaCompensacion(destinatario, null, null, false);
        obtenerDatosUsuariosCajaCompensacion.execute();
        usuarioDTO = (UsuarioCCF) obtenerDatosUsuariosCajaCompensacion.getResult();

        inDTO.getSolicitudAfiliacionEmpleador().getSolicitudGlobal().setSedeDestinatario(usuarioDTO.getCodigoSede());
        inDTO.getSolicitudAfiliacionEmpleador().getSolicitudGlobal().setDestinatario(destinatario);

        ActualizarSolicitudAfiliacionEmpleador actualizarEstadoService = new ActualizarSolicitudAfiliacionEmpleador(
                inDTO.getSolicitudAfiliacionEmpleador().getIdSolicitudAfiliacionEmpleador(), inDTO.getSolicitudAfiliacionEmpleador());
        actualizarEstadoService.execute();

        Map<String, Object> params = new HashMap<>();
        params.put("destinatario", destinatario);
        params.put("metodoEnvioCodigo", inDTO.getSolicitudAfiliacionEmpleador().getSolicitudGlobal().getMetodoEnvio().getCodigo());
        params.put("estadoAfiliacion", inDTO.getSolicitudAfiliacionEmpleador().getEstadoSolicitud().getCodigo());
        TerminarTarea terminarTarea = new TerminarTarea(inDTO.getIdTarea(), params);
        terminarTarea.execute();
    }

    /**
     * Método que permite terminar la tarea para el usuario recibido
     * 
     * @param idTarea
     * @param inDTO
     * @param userDTO
     */
    @Override
    public void verificarResultadosProductoNoConforme(Long idTarea, VerificarResultadosProductoNoConformeDTO inDTO, UserDTO userDTO) {

        SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = inDTO.getSolicitudAfiliacion();

        if (inDTO.getResultadoVerificacion() == ResultadosProductoNoConformeConstants.NO_SUBSANADOS) {

            //se actuliza el estado de la solicitud a "RECHAZADA"
            actualizarEstadoSolicitudAfiliacionEmpleador(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                    EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA);
            /* Si la solicitud es rechazada se invoca la novedad automática de desafiliación */
            this.ejecutarNovedadDesafiliacion(inDTO.getSolicitudAfiliacion().getIdEmpleador());
        }
        else {
            //se actuliza el estado de la solicitud a "APROBADO"
            actualizarEstadoSolicitudAfiliacionEmpleador(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                    EstadoSolicitudAfiliacionEmpleadorEnum.APROBADA);
        }

        //Se actuliza el estado de la solicitud a Cerrada
        actualizarEstadoSolicitudAfiliacionEmpleador(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("resultadoVerificacion", inDTO.getResultadoVerificacion());
        TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
        terminarTarea.execute();
    }

    /**
     * Método encargado de iniciar una nueva instancia del proceso en el BPM
     * 
     * @see com.asopagos.tareashumanas.service.IProcesosService#iniciarProceso(java.lang.Long,
     *      java.util.Map)
     * @param inDTO
     */
    @Override
    public Long iniciarProcesoAfliliacionEmpleadoresPresencial(ProcesoAfiliacionEmpleadoresPresencialDTO inDTO, UserDTO userDTO) {

        logger.info("**__**iniciarProcesoAfliliacionEmpleadoresPresencial userDTO.getNombreUsuario() "+userDTO.getNombreUsuario());
        Long idEmpleador;
        Long idInstanciaProcesoAfiliacionEmpleador = 0L;
        Long idSolicitudAfiliacion = null;
        boolean validar = true;
        if(! inDTO.getEmpleador().getEmpresa().getPersona().getNumeroIdentificacion().matches("[0-9]+")){
         validar = false ;
        }
        // Nueva afiliacion
        if (inDTO.getTipoTransaccion() == TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION) {
            BuscarEmpleador buscarEmpleador = new BuscarEmpleador(validar,
                    inDTO.getEmpleador().getEmpresa().getPersona().getNumeroIdentificacion(),
                    inDTO.getEmpleador().getEmpresa().getPersona().getTipoIdentificacion(), null);
            buscarEmpleador.execute();
            List<Empleador> lista = buscarEmpleador.getResult();

            if (inDTO.getEmpleador() != null && inDTO.getEmpleador().getIdEmpleador() != null) {
                idEmpleador = inDTO.getEmpleador().getIdEmpleador();
            }
            else if (lista != null && !lista.isEmpty() && lista.get(0).getIdEmpleador() != null) {
                idEmpleador = lista.get(0).getIdEmpleador();
            }
            //si el estado llega false se asume que es un intento de afiliación y por ende no se crea el empleador
            else if(!inDTO.getEstado()){
                idEmpleador = null;
                // Crear empresa y persona
                EmpresaModeloDTO empresa = new EmpresaModeloDTO();
                empresa.convertToDTO(inDTO.getEmpleador().getEmpresa());
                CrearEmpresa crearEmpresa = new CrearEmpresa(empresa);
                crearEmpresa.execute();
            }
            else{
                CrearEmpleador crearEmpleador = new CrearEmpleador(inDTO.getEmpleador());
                crearEmpleador.execute();
                idEmpleador = crearEmpleador.getResult();
            }
        }
        // Reintegro
        else {
            idEmpleador = inDTO.getEmpleador().getIdEmpleador();
        }
        
        Solicitud solicitud = new Solicitud();
        solicitud.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitud.setClasificacion(inDTO.getClasificacion());
        solicitud.setTipoTransaccion(inDTO.getTipoTransaccion());
        solicitud.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitud.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());

        SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = new SolicitudAfiliacionEmpleador();
        solicitudAfiliacionEmpleador.setSolicitudGlobal(solicitud);
        solicitudAfiliacionEmpleador.setIdEmpleador(idEmpleador);
        solicitudAfiliacionEmpleador.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);
        solicitudAfiliacionEmpleador.getSolicitudGlobal().setMetodoEnvio(inDTO.getMetodoEnvio());

        CrearSolicitudAfiliacionEmpleador crearSolicitudAfiliacionEmpleador = new CrearSolicitudAfiliacionEmpleador(
                solicitudAfiliacionEmpleador);
        crearSolicitudAfiliacionEmpleador.execute();
        idSolicitudAfiliacion = crearSolicitudAfiliacionEmpleador.getResult();
        if (idSolicitudAfiliacion != null && idSolicitudAfiliacion != 0) {
            if (!inDTO.getEstado()) {
                solicitudAfiliacionEmpleador = entityManager.find(SolicitudAfiliacionEmpleador.class, idSolicitudAfiliacion);
                IntentoAfiliacionInDTO intento = new IntentoAfiliacionInDTO();
                intento.setIdSolicitud(solicitudAfiliacionEmpleador.getSolicitudGlobal().getIdSolicitud());
                intento.setCausaIntentoFallido(inDTO.getCausaIntentoFallido());
                intento.setTipoTransaccion(inDTO.getTipoTransaccion());
                intento.setTipoIdentificacion(inDTO.getEmpleador().getEmpresa().getPersona().getTipoIdentificacion());
                intento.setNumeroIdentificacion(inDTO.getEmpleador().getEmpresa().getPersona().getNumeroIdentificacion());
                if (inDTO.getListaChequeo() != null && !inDTO.getListaChequeo().isEmpty()) {
                    List<Long> idsRequisitos = new ArrayList<>();
                    for (ItemChequeoDTO it : inDTO.getListaChequeo()) {
                        idsRequisitos.add(it.getIdRequisito());
                    }
                    intento.setIdsRequsitos(idsRequisitos);
                }
                registrarIntentoAfiliacion(intento);

                // Se actualiza el estado de la solicitud de afiliacion
                actualizarEstadoSolicitudAfiliacionEmpleador(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA);

                actualizarEstadoSolicitudAfiliacionEmpleador(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
            }
            else {
                // inicia proceso de afiliacion de empresa
                Map<String, Object> parametros = new HashMap<String, Object>();
                
                String tiempoProcesoSolicitud = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.BPM_AEP_TIEMPO_PROCESO_SOLICITUD);
                String tiempoAsignacionBack = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.BPM_AEP_TIEMPO_ASIGNACION_BACK);
                String tiempoSolicitudPendienteDocumentos = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.BPM_AEP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS);
                
                parametros.put("idEmpleador", idEmpleador);
                parametros.put("estado", inDTO.getEstado());
                parametros.put("usuarioRadicador", inDTO.getUsuarioRadicador());
                parametros.put("idSolicitud", idSolicitudAfiliacion);
                parametros.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
                parametros.put("tiempoAsignacionBack", tiempoAsignacionBack);
                parametros.put("tiempoSolicitudPendienteDocumentos", tiempoSolicitudPendienteDocumentos);
                
                
                IniciarProceso iniciarProceso = new IniciarProceso(ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL, parametros);
                iniciarProceso.execute();
                idInstanciaProcesoAfiliacionEmpleador = (Long) iniciarProceso.getResult();

                // Actualizo la solcicitud
                List<Solicitud> solicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GLOBAL)
                        .setParameter("idSolicitudAfiliacionEmpleador", idSolicitudAfiliacion).getResultList();
                solicitud = solicitudes.iterator().next();
                solicitud.setIdInstanciaProceso(idInstanciaProcesoAfiliacionEmpleador.toString());
                entityManager.merge(solicitud);

                // Persisto la lista de requisitos
                if (inDTO.getListaChequeo() != null && !inDTO.getListaChequeo().isEmpty()) {
                    Empleador empleador = entityManager.find(Empleador.class, idEmpleador);
                    if (empleador != null && empleador.getEmpresa() != null && empleador.getEmpresa().getPersona() != null) {
                        List<ItemChequeoDTO> listadoItems = inDTO.getListaChequeo();
                        ListaChequeoDTO listaChequeoDTO = new ListaChequeoDTO();
                        listaChequeoDTO.setListaChequeo(listadoItems);
                        listaChequeoDTO.setIdSolicitudGlobal(solicitud.getIdSolicitud());
                        listaChequeoDTO.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
                        listaChequeoDTO.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
                        GuardarListaChequeo crearListaChequeo = new GuardarListaChequeo(listaChequeoDTO);
                        crearListaChequeo.execute();
                    }
                }
            }
        }
        else {
            logger.debug(
                    "AfiliacionEmpleadoresCompositeBusiness.iniciarProcesoAfliliacionEmpleadoresPresencial :: No se logro crear la solcitud de afiliacion de empresas");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }

        return idInstanciaProcesoAfiliacionEmpleador;

    }
    @Override
    public Long iniciarProcesoAfliliacionEmpleadoresPresencialSat(ProcesoAfiliacionEmpleadoresPresencialDTO inDTO, UserDTO userDTO) {

        logger.info("**__**iniciarProcesoAfliliacionEmpleadoresPresencial userDTO.getNombreUsuario() "+userDTO.getNombreUsuario());
        Long idEmpleador;
        Long idInstanciaProcesoAfiliacionEmpleador = 0L;
        Long idSolicitudAfiliacion = null;
        boolean validar = true;
        if(! inDTO.getEmpleador().getEmpresa().getPersona().getNumeroIdentificacion().matches("[0-9]+")){
         validar = false ;
        }
        // Nueva afiliacion
        if (inDTO.getTipoTransaccion() == TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION) {
            BuscarEmpleador buscarEmpleador = new BuscarEmpleador(validar,
                    inDTO.getEmpleador().getEmpresa().getPersona().getNumeroIdentificacion(),
                    inDTO.getEmpleador().getEmpresa().getPersona().getTipoIdentificacion(), null);
            buscarEmpleador.execute();
            List<Empleador> lista = buscarEmpleador.getResult();

            if (inDTO.getEmpleador() != null && inDTO.getEmpleador().getIdEmpleador() != null) {
                idEmpleador = inDTO.getEmpleador().getIdEmpleador();
            }
            else if (lista != null && !lista.isEmpty() && lista.get(0).getIdEmpleador() != null) {
                idEmpleador = lista.get(0).getIdEmpleador();
            }
            //si el estado llega false se asume que es un intento de afiliación y por ende no se crea el empleador
            else if(!inDTO.getEstado()){
                idEmpleador = null;
                // Crear empresa y persona
                EmpresaModeloDTO empresa = new EmpresaModeloDTO();
                empresa.convertToDTO(inDTO.getEmpleador().getEmpresa());
                CrearEmpresa crearEmpresa = new CrearEmpresa(empresa);
                crearEmpresa.execute();
            }
            else{
                CrearEmpleador crearEmpleador = new CrearEmpleador(inDTO.getEmpleador());
                crearEmpleador.execute();
                idEmpleador = crearEmpleador.getResult();
            }
        }
        // Reintegro
        else {
            idEmpleador = inDTO.getEmpleador().getIdEmpleador();
        }
        
        Solicitud solicitud = new Solicitud();
        solicitud.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitud.setClasificacion(inDTO.getClasificacion());
        solicitud.setTipoTransaccion(inDTO.getTipoTransaccion());
        solicitud.setUsuarioRadicacion(inDTO.getUsuarioRadicador());
        solicitud.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());

        SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = new SolicitudAfiliacionEmpleador();
        solicitudAfiliacionEmpleador.setSolicitudGlobal(solicitud);
        solicitudAfiliacionEmpleador.setIdEmpleador(idEmpleador);
        solicitudAfiliacionEmpleador.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);
        solicitudAfiliacionEmpleador.getSolicitudGlobal().setMetodoEnvio(inDTO.getMetodoEnvio());

        CrearSolicitudAfiliacionEmpleador crearSolicitudAfiliacionEmpleador = new CrearSolicitudAfiliacionEmpleador(
                solicitudAfiliacionEmpleador);
        crearSolicitudAfiliacionEmpleador.execute();
        idSolicitudAfiliacion = crearSolicitudAfiliacionEmpleador.getResult();
        if (idSolicitudAfiliacion != null && idSolicitudAfiliacion != 0) {
            if (!inDTO.getEstado()) {
                solicitudAfiliacionEmpleador = entityManager.find(SolicitudAfiliacionEmpleador.class, idSolicitudAfiliacion);
                IntentoAfiliacionInDTO intento = new IntentoAfiliacionInDTO();
                intento.setIdSolicitud(solicitudAfiliacionEmpleador.getSolicitudGlobal().getIdSolicitud());
                intento.setCausaIntentoFallido(inDTO.getCausaIntentoFallido());
                intento.setTipoTransaccion(inDTO.getTipoTransaccion());
                intento.setTipoIdentificacion(inDTO.getEmpleador().getEmpresa().getPersona().getTipoIdentificacion());
                intento.setNumeroIdentificacion(inDTO.getEmpleador().getEmpresa().getPersona().getNumeroIdentificacion());
                if (inDTO.getListaChequeo() != null && !inDTO.getListaChequeo().isEmpty()) {
                    List<Long> idsRequisitos = new ArrayList<>();
                    for (ItemChequeoDTO it : inDTO.getListaChequeo()) {
                        idsRequisitos.add(it.getIdRequisito());
                    }
                    intento.setIdsRequsitos(idsRequisitos);
                }
                registrarIntentoAfiliacion(intento);

                // Se actualiza el estado de la solicitud de afiliacion
                actualizarEstadoSolicitudAfiliacionEmpleador(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA);

                actualizarEstadoSolicitudAfiliacionEmpleador(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
            }
            else {
                // inicia proceso de afiliacion de empresa
                Map<String, Object> parametros = new HashMap<String, Object>();
                
                String tiempoProcesoSolicitud = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.BPM_AEP_TIEMPO_PROCESO_SOLICITUD);
                String tiempoAsignacionBack = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.BPM_AEP_TIEMPO_ASIGNACION_BACK);
                String tiempoSolicitudPendienteDocumentos = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.BPM_AEP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS);
                
                parametros.put("idEmpleador", idEmpleador);
                parametros.put("estado", inDTO.getEstado());
                parametros.put("usuarioRadicador", inDTO.getUsuarioRadicador());
                parametros.put("idSolicitud", idSolicitudAfiliacion);
                parametros.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
                parametros.put("tiempoAsignacionBack", tiempoAsignacionBack);
                parametros.put("tiempoSolicitudPendienteDocumentos", tiempoSolicitudPendienteDocumentos);
                
                
                IniciarProceso iniciarProceso = new IniciarProceso(ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL, parametros);
                iniciarProceso.execute();
                idInstanciaProcesoAfiliacionEmpleador = (Long) iniciarProceso.getResult();

                // Actualizo la solcicitud
                List<Solicitud> solicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GLOBAL)
                        .setParameter("idSolicitudAfiliacionEmpleador", idSolicitudAfiliacion).getResultList();
                solicitud = solicitudes.iterator().next();
                solicitud.setIdInstanciaProceso(idInstanciaProcesoAfiliacionEmpleador.toString());
                entityManager.merge(solicitud);

                // Persisto la lista de requisitos
                if (inDTO.getListaChequeo() != null && !inDTO.getListaChequeo().isEmpty()) {
                    Empleador empleador = entityManager.find(Empleador.class, idEmpleador);
                    if (empleador != null && empleador.getEmpresa() != null && empleador.getEmpresa().getPersona() != null) {
                        List<ItemChequeoDTO> listadoItems = inDTO.getListaChequeo();
                        ListaChequeoDTO listaChequeoDTO = new ListaChequeoDTO();
                        listaChequeoDTO.setListaChequeo(listadoItems);
                        listaChequeoDTO.setIdSolicitudGlobal(solicitud.getIdSolicitud());
                        listaChequeoDTO.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
                        listaChequeoDTO.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
                        GuardarListaChequeo crearListaChequeo = new GuardarListaChequeo(listaChequeoDTO);
                        crearListaChequeo.execute();
                    }
                }
            }
        }
        else {
            logger.debug(
                    "AfiliacionEmpleadoresCompositeBusiness.iniciarProcesoAfliliacionEmpleadoresPresencial :: No se logro crear la solcitud de afiliacion de empresas");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }

        return idInstanciaProcesoAfiliacionEmpleador;

    }

    /**
     * Metodo que se encarga de consultar empleador
     * @param idEmpleador
     * @return
     */
    private Empleador consultarEmpleador(Long idEmpleador) {
        logger.debug("Inicia consultarEmpleador(Long idEmpleador)");
        Empleador empleador = null;
        ConsultarEmpleador consulEmpleador = new ConsultarEmpleador(idEmpleador);
        consulEmpleador.execute();
        empleador = consulEmpleador.getResult();
        logger.debug("Finalizar consultarEmpleador(Long idEmpleador)");
        return empleador;
    }

    /**
     * Metodo que se encarga de filtrar las validaciones
     * @param validacion
     * @param lista
     * @return
     */
    private ValidacionDTO getValidacion(ValidacionCoreEnum validacion, List<ValidacionDTO> lista) {
        for (ValidacionDTO validacionAfiliacionDTO : lista) {
            if (validacionAfiliacionDTO.getValidacion().equals(validacion)) {
                return validacionAfiliacionDTO;
            }
        }
        return null;
    }

    /**
     * Metodo que se encarga de abortar un proceso de afiliación en el bpm
     * @param idInstanciaProceso
     */
    private void abortarProceso(Long idInstanciaProceso) {
        logger.debug("Inicia abortarProceso(Long idInstanciaProceso)");
        AbortarProceso aborProceso = new AbortarProceso(ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL, idInstanciaProceso);
        aborProceso.execute();
        logger.debug("Finaliza abortarProceso(Long idInstanciaProceso)");
    }

    /**
     * Método que hce la peticion REST al servicio de actualizar estado
     * solicitud afiliación empleador
     */
    private void actualizarEstadoSolicitudAfiliacionEmpleador(Long idSolicitudAfiliacionEmpleador,
            EstadoSolicitudAfiliacionEmpleadorEnum estado) {
        logger.debug("Inicia actualizarEstadoSolicitudAfiliacionEmpleador(Long, EstadoSolicitudAfiliacionEmpleadorEnum)");
        ActualizarEstadoSolicitudAfiliacion actualizarEstadoSolicitudAfiliacion = new ActualizarEstadoSolicitudAfiliacion(
                idSolicitudAfiliacionEmpleador, estado);
        actualizarEstadoSolicitudAfiliacion.execute();
        logger.debug("Finaliza actualizarEstadoSolicitudAfiliacionEmpleador(Long, EstadoSolicitudAfiliacionEmpleadorEnum)");
    }

    /**
     * Método encargado encargado de llamar el cliente que realiza la desafiliacion de un empleador
     * @param inactivacionBeneficiarioDTO,
     *        inactivacion del beneficiario dto
     */
    private void desafiliarBeneficiarioEmpleador(Long idEmpleador, MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        logger.debug("Inicia método desafiliarBeneficiarioEmpleador(Long, MotivoDesafiliacionBeneficiarioEnum)");
        DesafiliarBeneficiarioEmpleador desEmpleador = new DesafiliarBeneficiarioEmpleador(idEmpleador, motivoDesafiliacion);
        desEmpleador.execute();
        logger.debug("Finaliza método desafiliarBeneficiarioEmpleador(Long, MotivoDesafiliacionBeneficiarioEnum)");
    }
    
    /**
     * Método que realiza la ejecución del retroactivo automático para un empleador 
     * @param idEmpleador Identificador del empleador
     */
    private void ejecutarRetroactivoAutomaticoEmpleador(Long idEmpleador) {
        logger.debug("Inicia método ejecutarRetroactivoAutomaticoEmpleador");

        // Consulta y actualiza "AporteGeneral" del empleador, a la fecha actual
        List<Long> listaIdsAporteGeneral = new ArrayList<>();
        List<AporteGeneralModeloDTO> listaAporteGeneralDTO = consultarAporteGeneralEmpleador(idEmpleador, EstadoAporteEnum.VIGENTE,
                EstadoRegistroAporteEnum.RELACIONADO);

        Object consultaEstadoParametro = CacheManager.getParametroGap(ParametrosGapConstants.APORTES_FUTURO);
        for (AporteGeneralModeloDTO aporteGeneralDTO : listaAporteGeneralDTO) {
            LocalDate periodoFecha = LocalDate.parse(aporteGeneralDTO.getPeriodoAporte()+ "-01");
        	LocalDate periodoActual = LocalDate.now().withDayOfMonth(1);
			periodoActual = periodoActual.minusMonths(1L);
            LocalDate primerDiaMesActual = LocalDate.now().withDayOfMonth(1);
            if(!aporteGeneralDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") || (aporteGeneralDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") && periodoActual.compareTo(periodoFecha) >= 0)
            || (aporteGeneralDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") && primerDiaMesActual.compareTo(periodoFecha) >= 0 && consultaEstadoParametro.toString().equals("ACTIVO"))){            
                listaIdsAporteGeneral.add(aporteGeneralDTO.getId());
            }
        }

    //esta este parametro, de acuerdo al GLPI 64096, algunas cajas lo quieren y lo pagan y otra no
    Object consultaParametroAporte = CacheManager.getParametroGap(ParametrosGapConstants.REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION);

    Empleador empleador = consultarEmpleador(idEmpleador);
    ConsultarSolicitud consultarSolicitud = new ConsultarSolicitud(null,empleador.getEmpresa().getPersona().getNumeroIdentificacion(),null,empleador.getEmpresa().getPersona().getTipoIdentificacion());
    consultarSolicitud.execute();
    List<RespuestaConsultaSolicitudDTO> solicitudesAfiliacionEmpleador = consultarSolicitud.getResult();
    logger.info("tamaño lista  solicitudesAfiliacionEmpleador"+solicitudesAfiliacionEmpleador.size());
        

        if(consultaParametroAporte.toString().equals("INACTIVO") || solicitudesAfiliacionEmpleador.size() == 1){ 
            if (!listaIdsAporteGeneral.isEmpty()) {
                ActualizarReconocimientoAportes actualizarReconocimiento = new ActualizarReconocimientoAportes(
                        EstadoRegistroAporteEnum.REGISTRADO, FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO,
                        listaIdsAporteGeneral);
                actualizarReconocimiento.execute();
            }
        }else{
            logger.info("**__**No REGISTRA aportes porque el parametro REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION esta activo para esta caja  por lo tanto no registra en fecha antes del reingreso");    
        }
        logger.info("**__**Finaliza método ejecutarRetroactivoAutomaticoEmpleador");
    }  
    
    /**
     * Método que consulta la lista de aportes generales por empleador
     * @param idEmpleador Identificador del empleador
     * @param estadoAporteAportante Estado del aporte
     * @param estadoRegistroAporte Estado del registro del aporte
     * @return La lista de aportes nivel 1 del empleador
     */
    private List<AporteGeneralModeloDTO> consultarAporteGeneralEmpleador(Long idEmpleador, EstadoAporteEnum estadoAporteAportante, EstadoRegistroAporteEnum estadoRegistroAporte) {
        logger.debug("Inicia método consultarAporteGeneralEmpleador");
        ConsultarAporteGeneralEmpleador service = new ConsultarAporteGeneralEmpleador(idEmpleador, estadoAporteAportante, estadoRegistroAporte);
        service.execute();
        logger.debug("Inicia método consultarAporteGeneralEmpleadorrrr");
        return service.getResult();
    }
    
    /**
     * Método que consulta la lista de aportes detallados por ids de aportes generales
     * @param listaIdAporteGeneral Lista de ids de <code>AporteGeneral</code>
     * @param estadoAporteAportante Estado del aporte
     * @param estadoRegistroAporte Estado del registro del aporte
     * @return La lista de aportes nivel 2 asociados 
     */
    private List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdsGeneral(List<Long> listaIdAporteGeneral, EstadoAporteEnum estadoAporteAportante, EstadoRegistroAporteEnum estadoRegistroAporte) {
        logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneral");
        ConsultarAporteDetalladoPorIdsGeneral service = new ConsultarAporteDetalladoPorIdsGeneral(estadoAporteAportante, estadoRegistroAporte, listaIdAporteGeneral);
        service.execute();
        logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneral");
        return service.getResult();
    }
    
    /**
     * Método que crea o actualiza un registro en la tabla <code>AporteGeneral</code>
     * @param aporteGeneralDTO
     *        Información del registro a actualizar
     * @return El identificador del registro modificado
     */
    private Long crearActualizarAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO) {
        logger.debug("Inicia método crearActualizarAporteGeneral");
        CrearActualizarAporteGeneral service = new CrearActualizarAporteGeneral(aporteGeneralDTO);
        service.execute();
        Long id = service.getResult();
        logger.debug("Inicia método crearActualizarAporteGeneral");
        return id;
    }
    
    /**
     * Método que invoca el servicio de creación o actualización de un registro en <code>AporteDetallado</code>
     * @param aporteDetalladoDTO
     *        Datos del registro a modificar
     * @return El identificador del registro actualizado
     */
    private Long crearActualizarAporteDetallado(AporteDetalladoModeloDTO aporteDetalladoDTO) {
        logger.debug("Inicia método crearActualizarDevolucionAporte");
        CrearAporteDetallado service = new CrearAporteDetallado(aporteDetalladoDTO);
        service.execute();
        Long id = service.getResult();
        logger.debug("Inicia método crearActualizarDevolucionAporte");
        return id;
    }

    /**
     * @param listaEspecialRevisionDTO
     * @param userDTO
     * @return
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Integer registrarEmpleadorEnListaEspecialRevision(ListaEspecialRevisionDTO listaEspecialRevisionDTO, UserDTO userDTO) {
        String firmaServicio = "cambiarEstadoEmpleadorRegistroLista(ActualizacionEstadoListaEspecialDTO, UserDTO)";
        try {
            logger.debug("Inicia " + firmaServicio);
            RegistrarPersonaEnListaEspecialRevision registro = new RegistrarPersonaEnListaEspecialRevision(listaEspecialRevisionDTO);
            registro.execute();
            ListaEspecialRevision listaEspecialRevision = registro.getResult();
            // 85428
            // ReplicarInsercionListaEspecialRevision replicarInsercion = new ReplicarInsercionListaEspecialRevision(listaEspecialRevision);
            // replicarInsercion.execute();
            logger.debug("Finaliza " + firmaServicio);
            return listaEspecialRevision.getCajaCompensacion();
            
        } catch (Exception e) {
            logger.debug("Finaliza con error " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param digitoVerificacion
     * @param fechaInicio
     * @param fechaFin
     * @param nombreEmpleador
     * @return
     */
    @Override
    public List<ListaEspecialRevisionDTO> consultarEmpleadorListaEspecialRevision(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, Byte digitoVerificacion, Long fechaInicio, Long fechaFin, String nombreEmpleador) {
        
        ConsultarListaEspecialRevision lista = new ConsultarListaEspecialRevision(digitoVerificacion, fechaInicio, fechaFin, numeroIdentificacion, nombreEmpleador, tipoIdentificacion);
        lista.execute();
        return lista.getResult();
    }

    /**
     * @param actualizacionEstadoListaEspecialDTO
     * @param userDTO
     */
    @Override
    public void cambiarEstadoEmpleadorRegistroLista(ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO,
            UserDTO userDTO) {
        String firmaServicio = "cambiarEstadoEmpleadorRegistroLista(ActualizacionEstadoListaEspecialDTO, UserDTO)";
        try {
            logger.debug("Inicia " + firmaServicio);
            ConsultarRegistroListaEspecialRevision consultar = new ConsultarRegistroListaEspecialRevision(actualizacionEstadoListaEspecialDTO);
            consultar.execute();
            ListaEspecialRevision listaEspecialRevision = consultar.getResult();
            //si el registro no lo hizo la caja, no puede modificarlo
            if(listaEspecialRevision.getCajaCompensacion() == Integer.valueOf(CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID).toString())){
                
                mapearInfoRegistroLER(actualizacionEstadoListaEspecialDTO, listaEspecialRevision);
                
                CambiarEstadoRegistroLista actualizacion = new CambiarEstadoRegistroLista(listaEspecialRevision);
                actualizacion.execute();
                
                ReplicarCambioEstadoListaEspecialRevision replicarActualizacion = new ReplicarCambioEstadoListaEspecialRevision(listaEspecialRevision);
                replicarActualizacion.execute();
            }
            
            logger.debug("Finaliza " + firmaServicio);
        } catch (Exception e) {
            logger.debug("Finaliza con error " + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        
        
        
    }

    /**
     * @param actualizacionEstadoListaEspecialDTO
     * @param listaEspecialRevision
     */
    private void mapearInfoRegistroLER(ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO,
            ListaEspecialRevision listaEspecialRevision) {
        if (actualizacionEstadoListaEspecialDTO.getEstado() != null) {
            listaEspecialRevision.setEstado(actualizacionEstadoListaEspecialDTO.getEstado());
        }
        if (actualizacionEstadoListaEspecialDTO.getComentario() != null) {
            listaEspecialRevision.setComentario(actualizacionEstadoListaEspecialDTO.getComentario());
        }
        if (actualizacionEstadoListaEspecialDTO.getEstado().equals(EstadoListaEspecialRevisionEnum.INCLUIDO)) {
            listaEspecialRevision.setFechaInicioInclusion(new Date());
            listaEspecialRevision.setFechaFinInclusion(null);
        } else {
            listaEspecialRevision.setFechaFinInclusion(new Date());
        }
    }
    
    @Override
    public Map<String, Object> radicarSolicitudAfiliacionComunicado(RadicarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) throws IOException {
        String firmaServicio = "radicarSolicitudAfiliacionYActivarEmpleadorComunicado(RadicarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String, Object> resultadosRadicar  = new HashMap<>();
      //  try {
            resultadosRadicar = radicarSolicitudAfiliacionYActivarEmpleador(inDTO, userDTO);
            inDTO.setResultadoValidacion(resultadosRadicar.get(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR.name()) != null ? ResultadoValidacionEnum.valueOf((String) resultadosRadicar.get(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR.name())) : null);

            if (inDTO.getIdInstanciaProceso() != null && inDTO.getResultadoRadicacion() != ResultadoRadicacionSolicitudEnum.PENDIENTE_ANALISIS) {
                // A partir de acá empieza la generación del dato temporal del comunicado  
                Long idSolicitudGlobal;
                Long idSolicitudAfiliacionEmpleador;
                Long idInstanciaProceso;
                Long idTareaComunicado;
                JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicado = new JsonPayloadDatoTemporalComunicadoDTO();

                //Se obtiene el valor de la instancia del proceso
                idInstanciaProceso = inDTO.getIdInstanciaProceso();

                // Se obtiene el id de la tarea activa
                try {
                    ObtenerTareaActiva obtenerTareaActivaComunicado = new ObtenerTareaActiva(idInstanciaProceso);
                    obtenerTareaActivaComunicado.execute();
                    Map<String, Object> tareaActiva = obtenerTareaActivaComunicado.getResult();
                    idTareaComunicado = tareaActiva != null ? ((Integer) obtenerTareaActivaComunicado.getResult().get(ConstantesComunes.ID_TAREA)).longValue() : null;
                } catch (Exception e) {
                    logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
                    idTareaComunicado = null;
                }

                //Se obtiene el valor del id de la solicitud global
                idSolicitudGlobal = inDTO.getSolicitudAfiliacion() != null ? inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador() != null ? inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud() : null : null;
                //Se obtiene el valor del id de la solicitud de afiliación del empleador
                idSolicitudAfiliacionEmpleador = inDTO.getSolicitudAfiliacion() != null ? inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador() : null;
                //Se agrega información información compartida de las plantillas
                jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getInformacionCompartidaPlantillas(), 
                        new InformacionAdicionalDTO(ConstantesComunes.ID_SOLICITUD, idSolicitudGlobal));

                if (inDTO.getResultadoValidacion() != null && inDTO.getResultadoValidacion().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                    //Se agrega información adicional al contexto
                    jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getContexto().getInformacionAdicional(), 
                            new InformacionAdicionalDTO("dtAdicionalIntAfi", inDTO.getEmpleador()),
                            new InformacionAdicionalDTO(ConstantesComunes.ID_TAREA, idTareaComunicado));
                    jsonPayloadDatoTemporalComunicado.asignarValoresJson(inDTO.getIdTarea(), idSolicitudGlobal, idInstanciaProceso, idSolicitudAfiliacionEmpleador,
                            new PlantillaComunicadoDTO(PlantillaProcesoEnum.NOTIFICACION_INTENTO_AFILIACION, ConstantesComunes.URL_PENDIENTES, null, null));
                }
                else if (inDTO.getResultadoRadicacion().equals(ResultadoRadicacionSolicitudEnum.FALLIDA)) {
                    //Se agrega información adicional al contexto
                    jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getContexto().getInformacionAdicional(), 
                            new InformacionAdicionalDTO("dtAdicionalIntAfi", inDTO.getEmpleador()),
                            new InformacionAdicionalDTO(ConstantesComunes.ID_TAREA, idTareaComunicado));
                    jsonPayloadDatoTemporalComunicado.asignarValoresJson(idTareaComunicado, idSolicitudGlobal, idInstanciaProceso, idSolicitudAfiliacionEmpleador, 
                            new PlantillaComunicadoDTO(PlantillaProcesoEnum.NOTIFICACION_INTENTO_AFILIACION, ConstantesComunes.URL_PENDIENTES, Boolean.TRUE, null));
                }
                else {
                    //Se agrega información información compartida de las plantillas
                    jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getInformacionCompartidaPlantillas(), 
                            new InformacionAdicionalDTO(ConstantesComunes.ID_INSTANCIA_PROCESO, idInstanciaProceso));
                    //Se agrega información adicional al contexto
                    jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getContexto().getInformacionAdicional(),
                            new InformacionAdicionalDTO(ConstantesComunes.ID_TAREA, idTareaComunicado));
                    jsonPayloadDatoTemporalComunicado.asignarValoresJson(idTareaComunicado, idSolicitudGlobal, idInstanciaProceso, idSolicitudAfiliacionEmpleador,
                            new PlantillaComunicadoDTO(PlantillaProcesoEnum.CARTA_BIENVENIDA_EMPLEADOR, null, null, null),
                            new PlantillaComunicadoDTO(PlantillaProcesoEnum.CARTA_ACEPTACION_EMPLEADOR, "asigSolicAfiEmpleador", Boolean.TRUE, null));
                }

                GenerarYGuardarDatoTemporalComunicado generarYGuardarDatoTemporalComunicado = new GenerarYGuardarDatoTemporalComunicado(jsonPayloadDatoTemporalComunicado);
                generarYGuardarDatoTemporalComunicado.execute();

 //bloque de codigo para actualizar ubicacion cuando usuario front radique la solicitud modificado 13/08/2021   
         //aqui puede llamar simplemente a idEmpleador
         Long idEmpleador = consultarSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador).getIdEmpleador();
         Empleador empleador = entityManager.find(Empleador.class, idEmpleador);
         ConsultarDatosTemporalesEmpleador consultTemporal = new ConsultarDatosTemporalesEmpleador(idSolicitudGlobal);
         consultTemporal.execute();
         String dataTemporal = consultTemporal.getResult();
         GuardarDataTemporal guardarTemporal = new GuardarDataTemporal();
         try {
             ObjectMapper mapper = new ObjectMapper();
             guardarTemporal = mapper.readValue(dataTemporal, GuardarDataTemporal.class);
         //se actualiza las ubicaciones con la información diligenciada
             if (guardarTemporal.getUbicaciones() != null && !guardarTemporal.getUbicaciones().isEmpty()) {
                 //para actualizarlas
                 ActualizarUbicacionesEmpresa actualizarUbicacionesEmpresa = new ActualizarUbicacionesEmpresa(empleador.getEmpresa().getIdEmpresa(),
                 guardarTemporal.getUbicaciones());
                 actualizarUbicacionesEmpresa.execute();
             // para crear ubicacion empresa
         // CrearUbicacionesEmpresa crearUbicacion = new CrearUbicacionesEmpresa(empleador.getEmpresa().getIdEmpresa(),
         // guardarTemporal.getUbicaciones());
         // crearUbicacion.execute();
             }
         } catch (Exception e) {
         logger.debug(
                 "AfiliacionEmpleadoresCompositeBusiness.radicarSolicitudAfiliacionYActivarEmpleadorComunicado(RadicarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) :: No se logro crear la solcitud de creacion de ubicacion",e);
         throw new TechnicalException(MensajesGeneralConstants.ERROR_ACTUALIZAR_RECURSO);
         // TODO: handle exception
         }
         //fin actualizacion de ubicacion cuando usuario front radica la solicitud

                resultadosRadicar.put(ConstantesComunes.JSON_DATO_TEMPORAL_COMUNICADO, generarYGuardarDatoTemporalComunicado.getResult());
            }
            else {
                logger.debug("No se pudo generarrrrrr el dato temporal del comunicado debido a que no se tiene la instancia del proceso: " + firmaServicio);
            }
      //  } catch (Exception e) {
      //      logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
      //      throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
      //  }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadosRadicar;
    }

    @Override
    public Map<String, Object> analizarSolicitudAfiliacionComunicado(AnalizarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) {
        String firmaServicio = "analizarSolicitudAfiliacionComunicado(AnalizarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String, Object> resultadoAnalizarSolicitudAfiliacion = new HashMap<>();
        try {
            EscalamientoSolicitud escalamientoActual = consultarEscalamientoSolicitudAfiliacion(
                    inDTO.getRegistroResultado().getIdEscalamientoSolicitud());
            
            analizarSolicitudAfiliacion(inDTO, userDTO);

            if (escalamientoActual != null && escalamientoActual.getResultadoAnalista() != null) {
                if (inDTO.getIdInstanciaProceso() != null) {
                    // A partir de acá empieza la generación del dato temporal del comunicado  
                    Long idSolicitudGlobal;
                    Long idSolicitudAfiliacionEmpleador;
                    Long idInstanciaProceso;
                    Long idTareaComunicado;
                    JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicado = new JsonPayloadDatoTemporalComunicadoDTO();
                    
                    //Se obtiene el valor de la instancia del proceso
                    idInstanciaProceso = inDTO.getIdInstanciaProceso();
                    
                    // Se obtiene el id de la tarea activa
                    try {
                        ObtenerTareaActiva obtenerTareaActivaComunicado = new ObtenerTareaActiva(idInstanciaProceso);
                        obtenerTareaActivaComunicado.execute();
                        Map<String, Object> tareaActiva = obtenerTareaActivaComunicado.getResult();
                        idTareaComunicado =  tareaActiva != null ? tareaActiva.get(ConstantesComunes.ID_TAREA) != null ? ((Integer) obtenerTareaActivaComunicado.getResult().get(ConstantesComunes.ID_TAREA)).longValue() : null : null;
                    }catch(Exception e) {
                        logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
                        idTareaComunicado = null;
                    }
                    
                    //Se obtiene el valor del id de la solicitud global
                    idSolicitudGlobal = inDTO.getRegistroResultado().getIdSolicitud();
                    
                    //Se obtiene el valor del id de la solicitud de afiliación del empleador
                    idSolicitudAfiliacionEmpleador = inDTO.getIdSolicitudAfiliacionEmpleador();
                    
                    //Información compartida plantillas
                    jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getInformacionCompartidaPlantillas(), 
                            new InformacionAdicionalDTO(ConstantesComunes.ID_INSTANCIA_PROCESO,idInstanciaProceso), 
                            new InformacionAdicionalDTO(ConstantesComunes.ID_SOLICITUD, idSolicitudGlobal));
                    
                    //Se agrega información adicional al contexto
                    jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getContexto().getInformacionAdicional(),
                            new InformacionAdicionalDTO(ConstantesComunes.ID_TAREA, idTareaComunicado));

                    if (escalamientoActual.getResultadoAnalista().equals(ResultadoAnalisisAfiliacionEnum.SOLICITUD_PROCEDENTE)) {
                        jsonPayloadDatoTemporalComunicado.asignarValoresJson(idTareaComunicado, idSolicitudGlobal, idInstanciaProceso, idSolicitudAfiliacionEmpleador, 
                                new PlantillaComunicadoDTO(PlantillaProcesoEnum.CARTA_BIENVENIDA_EMPLEADOR,null,null,null), 
                                new PlantillaComunicadoDTO(PlantillaProcesoEnum.CARTA_ACEPTACION_EMPLEADOR, "asigSolicAfiEmpleador", Boolean.TRUE, null));       
                    }
                    else {
                        jsonPayloadDatoTemporalComunicado.asignarValoresJson(idTareaComunicado, idSolicitudGlobal, idInstanciaProceso, idSolicitudAfiliacionEmpleador, 
                                new PlantillaComunicadoDTO(PlantillaProcesoEnum.RECHAZO_SOLICITUD_AFILIACION_EMPRESA,ConstantesComunes.URL_PENDIENTES,Boolean.TRUE,null)); 
                    }
                    
                    GenerarYGuardarDatoTemporalComunicado generarYGuardarDatoTemporalComunicado = new GenerarYGuardarDatoTemporalComunicado(jsonPayloadDatoTemporalComunicado);
                    generarYGuardarDatoTemporalComunicado.execute();

                    resultadoAnalizarSolicitudAfiliacion.put(ConstantesComunes.JSON_DATO_TEMPORAL_COMUNICADO, generarYGuardarDatoTemporalComunicado.getResult());
                }
                else {
                    logger.debug("No se pudo generar el dato temporal del comunicado debido a que no se tiene la instancia del proceso: " + firmaServicio);
                }
            }
        }catch(Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoAnalizarSolicitudAfiliacion;
    }

    @Override
    public Map<String, Object> verificarInformacionSolicitudComunicado(VerificarInformacionSolicitudDTO inDTO, UserDTO userDTO) {
        String firmaServicio = "verificarInformacionSolicitudComunicado(VerificarInformacionSolicitudDTO inDTO, UserDTO userDTO) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String, Object> resultadosVerificar = new HashMap<>();
        try {
            verificarInformacionSolicitud(inDTO, userDTO);
            
            if(inDTO.getIdInstanciaProceso() != null && inDTO.getResultadoGeneral() != null && inDTO.getResultadoGeneral().equals(ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE)) {
                // A partir de acá empieza la generación del dato temporal del comunicado  
                Long idSolicitudGlobal;
                Long idSolicitudAfiliacionEmpleador;
                Long idInstanciaProceso;
                Long idTareaComunicado = null;
                JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicado = new JsonPayloadDatoTemporalComunicadoDTO();
                
                //Se obtiene el valor de la instancia del proceso
                idInstanciaProceso = inDTO.getIdInstanciaProceso();
                
                // Se obtiene el id de la tarea activa
                try {
                    ObtenerTareaActiva obtenerTareaActivaComunicado = new ObtenerTareaActiva(idInstanciaProceso);
                    obtenerTareaActivaComunicado.execute();
                    Map<String, Object> tareaActiva = obtenerTareaActivaComunicado.getResult();
                    idTareaComunicado =  tareaActiva != null ? tareaActiva.get(ConstantesComunes.ID_TAREA) != null ? ((Integer) obtenerTareaActivaComunicado.getResult().get(ConstantesComunes.ID_TAREA)).longValue() : null : null;
                }catch(Exception e) {
                    logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
                    idTareaComunicado = null;
                }
               
                //Se obtiene el valor del id de la solicitud global
                idSolicitudGlobal = inDTO.getSolicitudAfiliacion() != null ? inDTO.getSolicitudAfiliacion().getSolicitudGlobal() != null ? inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud() : null : null;
                
                //Se obtiene el valor del id de la solicitud de afiliación del empleador
                idSolicitudAfiliacionEmpleador = inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador() != null ? inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador() : null;

                // Información compartida plantillas
                jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getInformacionCompartidaPlantillas(),
                        new InformacionAdicionalDTO(ConstantesComunes.ID_INSTANCIA_PROCESO,idInstanciaProceso), 
                        new InformacionAdicionalDTO(ConstantesComunes.ID_SOLICITUD, idSolicitudGlobal));
                
                //Se agrega información adicional al contexto
                jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getContexto().getInformacionAdicional(),
                        new InformacionAdicionalDTO(ConstantesComunes.ID_TAREA, idTareaComunicado));
                
                jsonPayloadDatoTemporalComunicado.asignarValoresJson(idTareaComunicado, idSolicitudGlobal, idInstanciaProceso, idSolicitudAfiliacionEmpleador, 
                        new PlantillaComunicadoDTO(PlantillaProcesoEnum.RECHAZO_SOLICITUD_AFILIACION_EMPRESA_PRE,ConstantesComunes.URL_PENDIENTES,Boolean.TRUE,null) );
                
                GenerarYGuardarDatoTemporalComunicado generarYGuardarDatoTemporalComunicado = new GenerarYGuardarDatoTemporalComunicado(jsonPayloadDatoTemporalComunicado);
                generarYGuardarDatoTemporalComunicado.execute();

                resultadosVerificar.put(ConstantesComunes.JSON_DATO_TEMPORAL_COMUNICADO, generarYGuardarDatoTemporalComunicado.getResult());
            }else {
                logger.debug("No se pudo generar el dato temporal del comunicado debido a que no se tiene la instancia del proceso: " + firmaServicio);
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadosVerificar;
    }

    @Override
    public Map<String, Object> verificarResultadosProductoNoConformeComunicado(Long idTarea, VerificarResultadosProductoNoConformeDTO inDTO, UserDTO userDTO) {
        String firmaServicio = "verificarResultadosProductoNoConformeComunicado(Long idTarea, VerificarResultadosProductoNoConformeDTO inDTO, UserDTO userDTO) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String, Object> resultadosVerificar = new HashMap<>();
        try {
            verificarResultadosProductoNoConforme(idTarea,inDTO,userDTO);
            
            if(inDTO.getIdInstanciaProceso() != null && inDTO.getResultadoVerificacion() != null && inDTO.getResultadoVerificacion().equals(ResultadosProductoNoConformeConstants.NO_SUBSANADOS)) {
                // A partir de acá empieza la generación del dato temporal del comunicado  
                Long idSolicitudGlobal;
                Long idSolicitudAfiliacionEmpleador;
                Long idInstanciaProceso;
                Long idTareaComunicado;
                JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicado = new JsonPayloadDatoTemporalComunicadoDTO();
                
                //Se obtiene el valor de la instancia del proceso
                idInstanciaProceso = inDTO.getIdInstanciaProceso();
                
                // Se obtiene el id de la tarea activa
                try {
                    ObtenerTareaActiva obtenerTareaActivaComunicado = new ObtenerTareaActiva(idInstanciaProceso);
                    obtenerTareaActivaComunicado.execute();
                    Map<String, Object> tareaActiva = obtenerTareaActivaComunicado.getResult();
                    idTareaComunicado =  tareaActiva != null ? tareaActiva.get(ConstantesComunes.ID_TAREA) != null ? ((Integer) obtenerTareaActivaComunicado.getResult().get(ConstantesComunes.ID_TAREA)).longValue() : null : null;
                }catch(Exception e) {
                    logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
                    idTareaComunicado = null;
                }
               
                //Se obtiene el valor del id de la solicitud global
                idSolicitudGlobal = inDTO.getSolicitudAfiliacion() != null ? inDTO.getSolicitudAfiliacion().getSolicitudGlobal() != null ? inDTO.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud() : null : null;
                
                //Se obtiene el valor del id de la solicitud de afiliación del empleador
                idSolicitudAfiliacionEmpleador = inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador() != null ? inDTO.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador() : null;

                // Información compartida plantillas
                jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getInformacionCompartidaPlantillas(), 
                        new InformacionAdicionalDTO(ConstantesComunes.ID_INSTANCIA_PROCESO,idInstanciaProceso), 
                        new InformacionAdicionalDTO(ConstantesComunes.ID_SOLICITUD, idSolicitudGlobal));
                
                //Se agrega información adicional al contexto
                jsonPayloadDatoTemporalComunicado.agregarInformacionAdicional(jsonPayloadDatoTemporalComunicado.getContexto().getInformacionAdicional(),
                        new InformacionAdicionalDTO(ConstantesComunes.ID_TAREA, idTareaComunicado));
                
                jsonPayloadDatoTemporalComunicado.asignarValoresJson(idTareaComunicado, idSolicitudGlobal, idInstanciaProceso, idSolicitudAfiliacionEmpleador, 
                        new PlantillaComunicadoDTO(PlantillaProcesoEnum.RECHAZO_SOLICITUD_AFILIACION_EMPRESA_PRE,ConstantesComunes.URL_PENDIENTES,Boolean.TRUE,null) );
                
                GenerarYGuardarDatoTemporalComunicado generarYGuardarDatoTemporalComunicado = new GenerarYGuardarDatoTemporalComunicado(jsonPayloadDatoTemporalComunicado);
                generarYGuardarDatoTemporalComunicado.execute();

                resultadosVerificar.put(ConstantesComunes.JSON_DATO_TEMPORAL_COMUNICADO, generarYGuardarDatoTemporalComunicado.getResult());
            }else {
                logger.debug("No se pudo generar el dato temporal del comunicado debido a que no se tiene la instancia del proceso: " + firmaServicio);
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadosVerificar;        
    }   
    
    
    @Override
    public List<DetalleAportesFuturosDTO> consultarDetalleAportesFuturos(String idEncabezado) throws IOException {
        List<DetalleAportesFuturosDTO> detalleAportesFuturosDTO = new ArrayList<>();
        try {
            
                final int varConsecutivoDetalle = 0;
                final int varOperacionRecaudo = 1;
                final int varFechaRegistroAporte = 2;
                final int varAntiguedadRecaudo = 3;
                final int varFechaPago = 4;
                final int varTipoPersona = 5;
                final int varTipoIdentificacion = 6;
                final int varNumeroIdentificacion = 7;
                final int varNombres = 8;
                final int varMontoAporte  = 9;
                final int varMontoInteres = 10;
                final int varMontoTotal = 11;
                final int varTipoReconocimiento  = 12;
                final int varFormaReconocimiento = 13;

                List<Object[]> resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_APORTES_FUTUROS)
			.setParameter("aporteGeneral", idEncabezado)
                        .getResultList();


                for (Object[] obj : resultado) {
                    DetalleAportesFuturosDTO objLocal = new  DetalleAportesFuturosDTO();
                    
                    objLocal.setConsecutivoDetalle(String.valueOf(String.valueOf(obj[varConsecutivoDetalle] == null ? "0": obj[varConsecutivoDetalle].toString())));
                    objLocal.setOperacionRecaudo(String.valueOf(String.valueOf(obj[varOperacionRecaudo] == null ? "0": obj[varOperacionRecaudo].toString())));
                    objLocal.setFechaRegistroAporte(String.valueOf(String.valueOf(obj[varFechaRegistroAporte] == null ? "0": obj[varFechaRegistroAporte].toString())));
                    objLocal.setAntiguedadRecaudo(String.valueOf(String.valueOf(obj[varAntiguedadRecaudo] == null ? "0": obj[varAntiguedadRecaudo].toString())));
                    objLocal.setFechaPago(String.valueOf(String.valueOf(obj[varFechaPago] == null ? "0": obj[varFechaPago].toString())));
                    objLocal.setTipoPersona(String.valueOf(String.valueOf(obj[varTipoPersona] == null ? "0": obj[varTipoPersona].toString())));
                    objLocal.setTipoIdentificacion(String.valueOf(String.valueOf(obj[varTipoIdentificacion] == null ? "0": obj[varTipoIdentificacion].toString())));
                    objLocal.setNumeroIdentificacion(String.valueOf(String.valueOf(obj[varNumeroIdentificacion] == null ? "0": obj[varNumeroIdentificacion].toString())));
                    objLocal.setNombres(String.valueOf(String.valueOf(obj[varNombres] == null ? "0": obj[varNombres].toString())));
                    objLocal.setMontoAporte(String.valueOf(String.valueOf(obj[varMontoAporte] == null ? "0": obj[varMontoAporte].toString()))); 
                    objLocal.setMontoInteres(String.valueOf(String.valueOf(obj[varMontoInteres] == null ? "0": obj[varMontoInteres].toString()))); 
                    objLocal.setMontoTotal(String.valueOf(String.valueOf(obj[varMontoTotal] == null ? "0": obj[varMontoTotal].toString())));
                    objLocal.setTipoReconocimiento(String.valueOf(String.valueOf(obj[varTipoReconocimiento] == null ? "0": obj[varTipoReconocimiento].toString())));
                    objLocal.setFormaReconocimiento(String.valueOf(String.valueOf(obj[varFormaReconocimiento] == null ? "0": obj[varFormaReconocimiento].toString())));
                    
                    detalleAportesFuturosDTO.add(objLocal);
                }
                
	} catch (NoResultException e) {
				logger.debug("Sin resultados para los criterios de busqueda");
	} catch (NonUniqueResultException e) {
				logger.debug("Finaliza el servicio detalle aportes futuro");
				throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, e);
	}
        
        return detalleAportesFuturosDTO;
    }
    
    @Override
    public List<EncabezadoAportesFuturosDTO> consultarEncabezadoAportesFuturos(Long fechaInicio, Long fechaFin, Long antiguedadRecaudo, String tipoEntidad) throws IOException {
        List<EncabezadoAportesFuturosDTO> encabezadoAportesFuturosDTO = new ArrayList<>();
        try {
                
                Calendar calendar = new GregorianCalendar(1970,0,01);                
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : CalendarUtils.truncarHora(calendar.getTime());
                Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : CalendarUtils.truncarHora(new Date());
                Date dateAntiguedadRecaudo = antiguedadRecaudo != null ? CalendarUtils.truncarHora(new Date(antiguedadRecaudo)) : CalendarUtils.truncarHora(new Date());
                
                String dateFechaInicioString = dateFormat.format(dateFechaInicio);  
                String dateFechaFinString = dateFormat.format(dateFechaFin);  
                String dateAntiguedadRecaudoString = dateFormat.format(dateAntiguedadRecaudo);                 
            
                final int varConsecutivoEncabezado = 0;
                final int varOperacionRecaudo = 1;
                final int varFechaRegistro = 2;
                final int varAntiguedadRecaudo = 3;
                final int varFechaPago = 4;
                final int varTipoEntidad = 5;
                final int varTipoIdentificacionEntidad = 6;
                final int varNumeroIdentificacionEntidad = 7;
                final int varRazonSocial = 8;
                final int varMontoAporte = 9;
                final int varMontoInteres = 10;
                final int varMontoTotal = 11;
                final int varEstadoAporte = 12;
                final int varPagoSiMismo = 13;
                final int varAporteConDetalle = 14;
                final int varTipoReconocimiento = 15;
                final int varFechaMovimiento = 16;
                final int varFormaReconocimiento = 17;
                final int varDetalleEncabezado = 18;
                final int varApgId = 19;
                
                String tipoSolicitantePensionado = "";
                String tipoSolicitanteEmpeador = "";
                String tipoSolicitanteIndependiente = "";

                if(tipoEntidad.contains("PENSIONADO")){
                    tipoSolicitantePensionado = "PENSIONADO";
                }
                if(tipoEntidad.contains("EMPLEADOR")){
                    tipoSolicitanteEmpeador = "EMPLEADOR";
                }
                if(tipoEntidad.contains("INDEPENDIENTE")){
                    tipoSolicitanteIndependiente = "INDEPENDIENTE";
                }
                
                List<Object[]> resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ENCABEZADO_APORTES_FUTUROS)
				.setParameter("fechaInicio", dateFechaInicioString)
                                .setParameter("fechaFin", dateFechaFinString)
                                .setParameter("antiguedadRecaudo", dateAntiguedadRecaudoString)
                                .setParameter("tipoSolicitantePensionado", tipoSolicitantePensionado)
                                .setParameter("tipoSolicitanteEmpeador", tipoSolicitanteEmpeador)
                                .setParameter("tipoSolicitanteIndependiente", tipoSolicitanteIndependiente)
                        .getResultList();


                for (Object[] obj : resultado) {
                    EncabezadoAportesFuturosDTO objLocal = new  EncabezadoAportesFuturosDTO();
                    
                    objLocal.setConsecutivoEncabezado(String.valueOf(String.valueOf(obj[varConsecutivoEncabezado] == null ? "0": obj[varConsecutivoEncabezado].toString())));
                    objLocal.setOperacionRecaudo(String.valueOf(String.valueOf(obj[varOperacionRecaudo] == null ? "0": obj[varOperacionRecaudo].toString())));
                    objLocal.setFechaRegistro(String.valueOf(String.valueOf(obj[varFechaRegistro] == null ? "0": obj[varFechaRegistro].toString())));
                    objLocal.setAntiguedadRecaudo(String.valueOf(String.valueOf(obj[varAntiguedadRecaudo] == null ? "0": obj[varAntiguedadRecaudo].toString())));
                    objLocal.setFechaPago(String.valueOf(String.valueOf(obj[varFechaPago] == null ? "0": obj[varFechaPago].toString())));
                    objLocal.setTipoEntidad(String.valueOf(String.valueOf(obj[varTipoEntidad] == null ? "0": obj[varTipoEntidad].toString())));
                    objLocal.setTipoIdentificacionEntidad(String.valueOf(String.valueOf(obj[varTipoIdentificacionEntidad] == null ? "0": obj[varTipoIdentificacionEntidad].toString())));
                    objLocal.setNumeroIdentificacionEntidad(String.valueOf(String.valueOf(obj[varNumeroIdentificacionEntidad] == null ? "0": obj[varNumeroIdentificacionEntidad].toString())));
                    objLocal.setRazonSocial(String.valueOf(String.valueOf(obj[varRazonSocial] == null ? "0": obj[varRazonSocial].toString())));
                    objLocal.setMontoAporte(String.valueOf(String.valueOf(obj[varMontoAporte] == null ? "0": obj[varMontoAporte].toString())));
                    objLocal.setMontoInteres(String.valueOf(String.valueOf(obj[varMontoInteres] == null ? "0": obj[varMontoInteres].toString())));
                    objLocal.setMontoTotal(String.valueOf(String.valueOf(obj[varMontoTotal] == null ? "0": obj[varMontoTotal].toString())));
                    objLocal.setEstadoAporte(String.valueOf(String.valueOf(obj[varEstadoAporte] == null ? "0": obj[varEstadoAporte].toString())));
                    objLocal.setPagoSiMismo(String.valueOf(String.valueOf(obj[varPagoSiMismo] == null ? "0": obj[varPagoSiMismo].toString())));
                    objLocal.setAporteConDetalle(String.valueOf(String.valueOf(obj[varAporteConDetalle] == null ? "0": obj[varAporteConDetalle].toString())));
                    objLocal.setTipoReconocimiento(String.valueOf(String.valueOf(obj[varTipoReconocimiento] == null ? "0": obj[varTipoReconocimiento].toString())));
                    objLocal.setFechaMovimiento(String.valueOf(String.valueOf(obj[varFechaMovimiento] == null ? "0": obj[varFechaMovimiento].toString())));
                    objLocal.setFormaReconocimiento(String.valueOf(String.valueOf(obj[varFormaReconocimiento] == null ? "0": obj[varFormaReconocimiento].toString())));
                    objLocal.setDetalleEncabezado(String.valueOf(String.valueOf(obj[varDetalleEncabezado] == null ? "0": obj[varDetalleEncabezado].toString())));
                    objLocal.setApgId(String.valueOf(String.valueOf(obj[varApgId] == null ? "0": obj[varApgId].toString())));
                    encabezadoAportesFuturosDTO.add(objLocal);
                }
                
	} catch (NoResultException e) {
				logger.debug("Sin resultados para los criterios de busqueda");
	} catch (NonUniqueResultException e) {
				logger.debug("Finaliza el servicio encabezado aportes futuros");
				throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, e);
	}
        
        return encabezadoAportesFuturosDTO;
    
    }

     @Override
    public List<String> creacionUsuriosEmpresasMasivos() {
         logger.info("**__**INICIA creacionUsuriosEmpresasMasivos keycload");
         String respuesta="";
         	List<String> listaResultados = new ArrayList<>();
			 listaResultados.add("RESPUESTA DE SERVICIO CREACIONUSUARIOSEMPRESASMASIVOS");
        try{
              List<Object[]> resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TABLA_USUARIO_POR_CREAR_EMPRESAS_KEYCLOAD)
				.setParameter("estado", "NO_CREADO")
                        .getResultList();
            for (Object[] obj : resultado) {
     
                UsuarioEmpleadorDTO usuarioEmpleadorDTO = new UsuarioEmpleadorDTO();
                    usuarioEmpleadorDTO.setEmail(obj[0] != null ? obj[0].toString() : null);
                    usuarioEmpleadorDTO.setPrimerApellido(obj[1] != null ? obj[1].toString() : null);
                    usuarioEmpleadorDTO.setPrimerNombre(obj[2] != null ? obj[2].toString() : null);
                    usuarioEmpleadorDTO.setTipoIdentificacion(obj[3] != null ? obj[3].toString() : null);
                    usuarioEmpleadorDTO.setNumIdentificacion(obj[4] != null ? obj[4].toString() : null);
                    usuarioEmpleadorDTO.setIdSolicitudGlobal(obj[5] != null ? Long.valueOf(String.valueOf(obj[5])) : null);
                   CrearUsuarioAdminEmpleadorMasivo crearUsuarioAdminEmpleador = new CrearUsuarioAdminEmpleadorMasivo(usuarioEmpleadorDTO);
                   crearUsuarioAdminEmpleador.execute();
                     respuesta = (String) crearUsuarioAdminEmpleador.getResult();
                   listaResultados.add(respuesta);
            }  
        } catch (NoResultException e) {
            logger.info("Error en la consulta NoResultException "+e);
             listaResultados.add("NO HAY USUARIOS POR CREAR EN LA TABLA CreacionUsuariosEmpresasKeycload");
        }
        return listaResultados;
    }
    
    
    @Override
    public void crearSolicitudAfiliacionEmpleadorAportes(ActivacionEmpleadorDTO datosReintegro) {
        // Se consulta la solicitud de afiliacion empleador anterior
        List<SolicitudAfiliacionEmpleador> listaSolicitudes = new ArrayList();
        ConsultarSolicitudAfiliacionEmpleadorAnteriores consultarSolicitudAfiliacionEmpleadorAnteriores =
            new ConsultarSolicitudAfiliacionEmpleadorAnteriores(
                datosReintegro.getTipoIdEmpleador(),
                datosReintegro.getNumIdEmpleador());
        consultarSolicitudAfiliacionEmpleadorAnteriores.execute();
        listaSolicitudes = consultarSolicitudAfiliacionEmpleadorAnteriores.getResult();

        if (listaSolicitudes == null || listaSolicitudes.isEmpty()) {
            return;
        }

        
        SolicitudAfiliacionEmpleador ultimaSolicitud = listaSolicitudes.get(0);

        // Se crea la nueva solicitud global

        Solicitud solicitud = new Solicitud();
        solicitud.setCanalRecepcion(datosReintegro.getCanalReintegro());
        solicitud.setClasificacion(ultimaSolicitud.getSolicitudGlobal().getClasificacion());
        solicitud.setTipoTransaccion(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
        solicitud.setResultadoProceso(ResultadoProcesoEnum.APROBADA);
        solicitud.setUsuarioRadicacion("service-account-system");
        solicitud.setFechaRadicacion(new Date());
        solicitud.setCiudadSedeRadicacion("1");
        
        // Persistir la solicitud asignar numero de radicacion
        
        SolicitudAfiliacionEmpleador solAfiliacionEmpleador = new SolicitudAfiliacionEmpleador();
        solAfiliacionEmpleador.setSolicitudGlobal(solicitud);
        solAfiliacionEmpleador.setCodigoEtiquetaPreimpresa(ultimaSolicitud.getCodigoEtiquetaPreimpresa());
        solAfiliacionEmpleador.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.APROBADA);
        solAfiliacionEmpleador.setNumeroCustodiaFisica(ultimaSolicitud.getNumeroCustodiaFisica());
        solAfiliacionEmpleador.setNumeroActoAdministrativo(ultimaSolicitud.getNumeroActoAdministrativo());
        solAfiliacionEmpleador.setFechaAprobacionConsejo(ultimaSolicitud.getFechaAprobacionConsejo());
        solAfiliacionEmpleador.setIdEmpleador(ultimaSolicitud.getIdEmpleador());
        
        CrearSolicitudAfiliacionEmpleador crearSolicitudAfiliacionEmpleador = new CrearSolicitudAfiliacionEmpleador(
            solAfiliacionEmpleador);
        crearSolicitudAfiliacionEmpleador.execute();
        Long idSolicitudAfiliacion = crearSolicitudAfiliacionEmpleador.getResult();

        // Se asigna numero de radicado a la solicitud
        SolicitudAfiliacionEmpleador sae = new SolicitudAfiliacionEmpleador();
        ConsultarSolicitudAfiliacionEmpleador csae = new ConsultarSolicitudAfiliacionEmpleador(idSolicitudAfiliacion);
        csae.execute();
        sae = (SolicitudAfiliacionEmpleador) csae.getResult();

        String numeroRadicado = radicarSolicitudAfiliacion(sae.getSolicitudGlobal().getIdSolicitud(), "1");


        sae.getSolicitudGlobal().setNumeroRadicacion(numeroRadicado);

        
        ActualizarSolicitudAfiliacionEmpleador actualizarEstadoService = new ActualizarSolicitudAfiliacionEmpleador(
            idSolicitudAfiliacion,
            sae);
        actualizarEstadoService.execute();

         //Se actuliza el estado de la solicitud a Cerrada
         actualizarEstadoSolicitudAfiliacionEmpleador(idSolicitudAfiliacion, EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);



    }

    public void actualizarFechaDesafiliacionEmpleador(Long idEmpleador){
        ActualizarFechaDesafiliacionEmpleador actualizarFechaDesafiliacionEmpleador = new ActualizarFechaDesafiliacionEmpleador(idEmpleador);
        actualizarFechaDesafiliacionEmpleador.execute();
    }
    
}