package com.asopagos.cartera.composite.service;

import com.asopagos.cartera.composite.dto.ResultadoGestionPreventivaDTO;
import com.asopagos.dto.EmpleadorDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.cartera.AportanteCarteraDTO;
import com.asopagos.dto.cartera.AportanteGestionManualDTO;
import com.asopagos.dto.cartera.AportanteRemisionComunicadoDTO;
import com.asopagos.dto.cartera.CriteriosParametrizacionTemporalDTO;
import com.asopagos.dto.cartera.DetalleDeudaDTO;
import com.asopagos.dto.cartera.ExclusionCarteraDTO;
import com.asopagos.dto.cartera.FiltrosGestionCobroManualDTO;
import com.asopagos.dto.cartera.GestionCicloManualDTO;
import com.asopagos.dto.cartera.GestionDeudaDTO;
import com.asopagos.dto.cartera.LiquidacionAporteCarteraDTO;
import com.asopagos.dto.cartera.RegistroRemisionAportantesDTO;
import com.asopagos.dto.cartera.RespuestaCargueMasivoAportanteDTO;
import com.asopagos.dto.cartera.SimulacionDTO;
import com.asopagos.dto.modelo.CarteraDependienteModeloDTO;
import com.asopagos.dto.modelo.CarteraModeloDTO;
import com.asopagos.dto.modelo.CicloCarteraModeloDTO;
import com.asopagos.dto.modelo.DesafiliacionAportanteDTO;
import com.asopagos.dto.modelo.DesafiliacionDTO;
import com.asopagos.dto.modelo.NotificacionPersonalModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionFiscalizacionModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionPreventivaModeloDTO;
import com.asopagos.dto.modelo.ProgramacionFiscalizacionDTO;
import com.asopagos.dto.modelo.SolicitudGestionCobroFisicoModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum;
import com.asopagos.enumeraciones.cartera.ResultadoBitacoraCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoActividadBitacoraEnum;
import com.asopagos.enumeraciones.cartera.TipoGestionCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la cartera de la Caja de compensación.<b>Módulo:</b> Asopagos - Cartera<br/>
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */

@Path("carteraComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface CarteraCompositeService {

    /**
     * Servicio encargado de registrar los resultados de una gestión preventiva.
     *
     * @param resultadoGestionPreventivaDTO datos con el resultado de la gestión preventiva.
     */
    @POST
    @Path("/registrarGestionPreventiva")
    public void registrarGestionPreventiva(@NotNull ResultadoGestionPreventivaDTO resultadoGestionPreventivaDTO, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de consultar los aportantes para una gestión preventiva.
     *
     * @param parametrizacionDTO parametrización de la cartera preventiva.
     * @param automatico         indica true si se esta ejecutando por medio del proceso automático, para evaluar unicamente los aportantes que tienen vencida
     *                           la fecha. False o null si es manual, no se tiene en cuenta la fecha.
     * @return lista que contiene los datos de aportantes para una gestión preventiva.
     */
    @POST
    @Path("/consultarAportantesGestionPreventiva")
    public List<SimulacionDTO> consultarAportantesGestionPreventiva(@QueryParam("automatico") Boolean automatico,
                                                                    ParametrizacionPreventivaModeloDTO parametrizacionDTO, @Context UserDTO userDTO, @Context UriInfo uri,
                                                                    @Context HttpServletResponse response);

    /**
     * Servicio que se encarga de asignar la gestión preventiva para los aportantes.
     *
     * @param simulaciones que fueron consultadas previamente.
     * @param userDTO      usuario que se encuentra autenticado.
     */
    @POST
    @Path("/asignarAccionesPreventivas")
    public void asignarAccionesPreventivas(@Context UserDTO userDTO, @QueryParam("automatico") Boolean automatico);

    /**
     * Servicio que se encarga de actualizar la gestion preventiva.
     *
     * @param numeroRadicacion      número de radicación de la solicitud.
     * @param idTarea               id de la tarea a cerrar.
     * @param actualizacionEfectiva indica si la actualización fue efectiva o no.
     */
    @POST
    @Path("/{numeroRadicacion}/actualizarGestionPreventiva")
    public void actualizarGestionPreventiva(@PathParam("numeroRadicacion") String numeroRadicacion,
                                            @NotNull @QueryParam("idTarea") Long idTarea, @NotNull @QueryParam("actualizacionEfectiva") Boolean actualizacionEfectiva,
                                            @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de guardar una parametrización preventiva, y si se trata de una parametrización automática genera la
     * pametrización del proceso.
     *
     * @param parametrizacionPreventivaModeloDTO datos de la parametrización.
     */
    @POST
    @Path("/guardarParametrizacionPreventiva")
    public void guardarParametrizacionPreventiva(ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO);

    /**
     * Servicio que se encarga de consultar los aportantes para una fiscalización de cartera, según la parametrización.
     *
     * @return simulaciones consultadas previamente.
     */
    @POST
    @Path("/consultarAportantesFiscalizacion")
    public List<SimulacionDTO> consultarAportantesFiscalizacion(ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO,
                                                                @Context UserDTO userDTO, @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Servicio que se encarga de crear un nuevo ciclo de fiscalización con los aportantes asociados.
     *
     * @param fechaInicio    fecha inicio del ciclo.
     * @param fechaFin       fecha fin del ciclo.
     * @param simulacionDTOs recibe una lista de SimulacionDTO
     * @param user           ingresa por el contexto de la página
     * @return CicloFiscalizacionModeloDTO donde esta la informacion relacioana al cilo de fiscalización
     */
    @POST
    @Path("/crearNuevoCicloFiscalizacion")
    public CicloCarteraModeloDTO crearNuevoCicloFiscalizacion(@NotNull @QueryParam("fechaInicio") Long fechaInicio,
                                                              @NotNull @QueryParam("fechaFin") Long fechaFin, List<SimulacionDTO> simulacionDTOs, @Context UserDTO user);

    /**
     * Servicio que se encarga de excluir ó finalizar el ciclo de fiscalización fiscalización.
     *
     * @param numeroRadicacion    es el numero de radicacion.
     * @param idTarea             id de la tarea.
     * @param estadoFiscalizacion representa el estado de la solicitud
     */
    @POST
    @Path("/{idTarea}/terminarProcesoFiscalizacion")
    public void terminarProcesoFiscalizacion(@NotNull @QueryParam("numeroRadicacion") String numeroRadicacion,
                                             @PathParam("idTarea") Long idTarea, @NotNull @QueryParam("estadoFiscalizacion") EstadoFiscalizacionEnum estadoFiscalizacion);

    /**
     * Servicio que sirve para guardar temporalmente las actividades y las agendas registradas por el funcionario back
     * gestion preventiva de mora
     *
     * @param programacionFiscalizacionDTO contiene toda la informacion de las agendas y actividades
     * @param idSolicitud                  representa el id de la solcitud
     */
    @POST
    @Path("/guardarFiscalizacionAportes")
    public void guardarFiscalizacionAportes(@NotNull @QueryParam("numeroRadicacion") String numeroRadicacion,
                                            ProgramacionFiscalizacionDTO programacionFiscalizacionDTO);

    /**
     * Servicio que sirve para consutlar la Fiscalizacion del Aportante que se encuentra temporal
     *
     * @param idSolicitud parametro de la id de la solicitud para que pueda devolver los datos que estan temporales
     * @return un objeto ProgramacionFiscalizacionDTO con la información temporal
     */
    @GET
    @Path("/consultarFiscalizacionAportesTemporal")
    public ProgramacionFiscalizacionDTO consultarFiscalizacionAportesTemporal(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio encargado de consultar los aportantes de un ciclo de fiscalización actual.
     *
     * @param tipoIdentificacion   representa el tipo de indentificacion de la persona
     * @param numeroIdentificacion representa el número de indentificacion de la persona
     * @param analista             representa el analista
     * @param estado               representa el estado de la solicitud
     * @return lista de los aportantes del ciclo de fiscalización actual.
     */
    @GET
    @Path("/consultarAportanteCicloActual")
    public List<SimulacionDTO> consultarAportantesCicloActual(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                              @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                              @QueryParam("analista") String analista,
                                                              @QueryParam("estado") EstadoFiscalizacionEnum estado,
                                                              @Context UserDTO user);

    /**
     * Servicio que sirve para cancelar un ciclo de fiscalizacion.
     */
    @POST
    @Path("/cancelarCicloFiscalizacion")
    public void cancelarCicloFiscalizacion();

    /**
     * Servicio que sirve para finalizar un ciclo de fiscalización.
     */
    @POST
    @Path("/finalizarCiclosVencidos")
    public void finalizarCiclosVencidos();

    /**
     * Servicio que se encarga de asignar los analistas para la simulacion
     *
     * @param simulacionDTOs representa una lista de SimulacionDTO
     * @param user           ingresa por el contexto de la página
     * @return una lista de los analistas asignados para la simulacion
     */
    @POST
    @Path("/asignarUsuarioCiclo")
    public List<SimulacionDTO> asignarUsuarioCiclo(List<SimulacionDTO> simulacionDTOs, @QueryParam("proceso") ProcesoEnum proceso,
                                                   @Context UserDTO user);

    /**
     * Servicio encargado de consultas los detalles pertenecientes a un ciclo de Fiscalización
     *
     * @param idCiclo,      identificador del ciclo de Fiscalización a consultar los detalles
     * @param esSupervisor, booleano que identifica si se realiza la consulta de los detalles de fiscalizacion para un supervisor
     * @return retorna el listado de detalles de Fiscalización
     */
    @GET
    @Path("/buscarDetallesCicloFiscalizacion/{idCiclo}")
    public List<SimulacionDTO> buscarDetallesCicloFiscalizacion(@PathParam("idCiclo") Long idCiclo,
                                                                @NotNull @QueryParam("esSupervisor") boolean esSupervisor, @QueryParam("gestionManual") boolean gestionManual,
                                                                @Context UserDTO user);

    /**
     * Servicio encargado de buscar el detalle de un ciclo Aportante
     *
     * @param numeroRadicado, número de radicado por el cual se va a consultar
     * @return retorna el detalle del ciclo del aportante
     */
    @GET
    @Path("/buscarDetalleCicloAportante/{numeroRadicado}")
    public SimulacionDTO buscarDetalleCicloAportante(@PathParam("numeroRadicado") String numeroRadicado);

    /**
     * Servicio encargado de obtener los parametros de un comunicado para gestión preventiva
     *
     * @param tipoSolicitante,      tipo del solicitante que realiza el movimiento de aporte
     * @param numeroIdentificacion, número de identificación
     * @return retorna un mapa con los parametros pertenecientes al comunicado de gestión preventiva
     */
    @GET
    @Path("/obtenerParametrosComunicadoPreventiva")
    public Map<String, String> obtenerParametrosComunicadoPreventiva(
            @NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio que hace las respectivas validaciones antes de permitir crear el convenio de pago
     *
     * @param tipoIdentificacion   tipo de identificacion del aportante relacionado al convenio de pago
     * @param numeroIdentificacion número de identificacion del aportante relacionado al convenio de pago
     * @param tipoSolicitante      tipo de solicitante si es empleador, independiente o pensionado
     * @return retorna un boolean que indica si es permitido o no crear el convenio de pago
     */
    @GET
    @Path("/validarCrearConvenioPago")
    public Boolean validarCrearConvenioPago(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio encargado de consultar fechas limites de pago
     *
     * @param numeroCuotas,         número de cuotas
     * @param tipoSolicitante,      tipo de solicitante
     * @param numeroIdentificacion, número de identificación a consultar la fecha de vencimiento
     * @return retorna el listado de fechas de vencimiento
     */
    @GET
    @Path("/consultarFechasLimitePago")
    public List<Long> consultarFechasLimitePago(@QueryParam("numeroCuotas") Short numeroCuotas,
                                                @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio encatgado de consultar las exclusiones de cartera que se encuentran activas
     *
     * @param exclusionCarteraDTO, exclusion de cartera dto que contiene los dos a consultar
     * @return retorna las exclusiones de cartera dto
     */
    @POST
    @Path("/consultarExclusionCarteraActiva")
    public List<ExclusionCarteraDTO> consultarExclusionCarteraActiva(@NotNull ExclusionCarteraDTO exclusionCarteraDTO);

    /**
     * Servicio que se encarga de realizar la ejecución del proceso automático de convenios de pago.
     */
    @POST
    @Path("/ejecutarProcesoAutomatico")
    public void ejecutarProcesoAutomaticoConvenioPago();

    /**
     * Servicio encargado de buscar las exclusiones por aportante
     *
     * @param tipoIdentificacion,   tipo de identificación de la persona
     * @param numeroIdentificacion, número de identificación de la persona
     * @param tipoSolicitante,      tipo de solicitante
     * @return retorna el listado de exclusiones asociados a la persona
     */
    @GET
    @Path("/buscarExclusionPorAportante")
    public List<ExclusionCarteraDTO> buscarExclusionPorAportante(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /* Inicio proceso 22 */

    /**
     * Servicio que se encarga de crear un nuevo ciclo de gestión manual con los aportantes asociados.
     *
     * @param fechaInicio fecha inicio del ciclo.
     * @param fechaFin    fecha fin del ciclo.
     * @param lineaCobro  linea cobro.
     * @param aportantes  lista de los aportantes para un nuevo ciclo de gestión manual.
     * @param userDTO     ingresa por el contexto de la página
     * @return CicloCarteraModeloDTO donde esta la informacion relaciona al cilo de gestion manual
     */
    @POST
    @Path("/crearNuevoCicloGestionManual")
    public CicloCarteraModeloDTO crearNuevoCicloGestionManual(@NotNull @QueryParam("fechaInicio") Long fechaInicio,
                                                              @NotNull @QueryParam("fechaFin") Long fechaFin, @NotNull @QueryParam("lineaCobro") TipoLineaCobroEnum lineaCobro,
                                                              List<SimulacionDTO> aportantes, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de consultar la información temporal almacenada y pasarla a las tablas oficiales.
     */
    @POST
    @Path("/finalizarParametrizacionCriteriosGestionCobro")
    public Integer finalizarParametrizacionCriteriosGestionCobro();

    /**
     * Servicio que se encarga de consultar los criterios de parametrizacion del encabezado.
     *
     * @return criterios consultados.
     */
    @GET
    @Path("/consultarCriteriosParametrizacion")
    public CriteriosParametrizacionTemporalDTO consultarCriteriosParametrizacion();

    /**
     * Servicio qu ese encarga de consultar una parametrización del criterio.
     *
     * @return parametrización encontrada.
     */
    @GET
    @Path("/{lineaCobro}/consultarParametrizacionCriterioTemporal")
    public ParametrizacionCriteriosGestionCobroModeloDTO consultarParametrizacionCriterioTemporal(
            @PathParam("lineaCobro") TipoLineaCobroEnum lineaCobro, @NotNull @QueryParam("accion") TipoGestionCarteraEnum accion);

    /**
     * Servicio que se encarga de guardar la parametrización temporal.
     *
     * @param parametrizacionDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarParametrizacionCriterioTemporal")
    public void guardarParametrizacionCriterioTemporal(@NotNull ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionDTO);

    /**
     * Traspasar Cartera
     *
     * @param tipoSolicitante
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     */
    @POST
    @Path("/traspasarCartera")
    public void traspasarCartera(@NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                 @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                 @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion, @Context UserDTO userDTO);

    /**
     * Consultar Aportantes Deuda Antigua
     *
     * @param filtros
     * @param uri
     * @param response
     * @return
     */
    @POST
    @Path("/consultarAportantesDeudaAntigua")
    public List<AportanteGestionManualDTO> consultarAportantesDeudaAntigua(FiltrosGestionCobroManualDTO filtros, @Context UriInfo uri,
                                                                           @Context HttpServletResponse response);

    /* Fin proceso 22 */

    /* Inicio proceso 223 */

    /**
     * Servicio encargado de ejecutar un proceso automatico para cartera
     *
     * @param userDTO Información del usuario, tomada del contexto
     */
    @POST
    @Path("/ejecutarProcesoAutomaticoCartera")
    public void ejecutarProcesoAutomaticoCartera(@Context UserDTO userDTO);

    /**
     * Servicio que se encarga de confirmar la remisión de un aportante.
     *
     * @param registroRemision registro de la primera remisión.
     */
    @POST
    @Path("/confirmarPrimeraRemision")
    public void confirmarPrimeraRemision(@NotNull RegistroRemisionAportantesDTO registroRemision, @Context HttpHeaders header, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de actualizar el estado de la solicitud de acuerdo a si la actualización de datos fue efectiva o no.
     *
     * @param numeroRadicacion      número de radicación de la solicitud de gestión de cobro.
     * @param idTarea               id de la tarea a cerrar.
     * @param actualizacionEfectiva true si fue efectiva, false si no lo fue.
     */
    @POST
    @Path("/{numeroRadicacion}/actualizarGestionCobro")
    public void actualizarGestionCobro(@PathParam("numeroRadicacion") String numeroRadicacion, @NotNull @QueryParam("idTarea") Long idTarea,
                                       @NotNull @QueryParam("actualizacionEfectiva") Boolean actualizacionEfectiva);

    /**
     * Servicio que se encarga de realizar un segundo intento para el envío del comunicado, inciando un nuevo proceso para el aportante.
     *
     * @param numeroRadicacion     número de radicación de la solicitud actual.
     * @param accionCobro          Tipo de acción de cobro
     * @param aportanteRemisionDTO aportante al que se le inciara el proceso con un segundo intento.
     * @return estado de la solicitud cuando se gsetiona un segundo intento.
     */
    @POST
    @Path("/{numeroRadicacion}/gestionarSegundoIntento")
    public EstadoSolicitudGestionCobroEnum gestionarSegundoIntento(@PathParam("numeroRadicacion") String numeroRadicacion,
                                                                   @QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @NotNull AportanteRemisionComunicadoDTO aportanteRemisionDTO,
                                                                   @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de registrar los resultados de la primera remisión.
     *
     * @param numeroRadicacion     número de radicación de la solicitud.
     * @param aportanteRemisionDTO lista de los aportantes con los resultados de la remisión.
     * @param idTarea              id de la tarea asociada.
     * @param userDTO              usuario autenticado.
     */
    @POST
    @Path("/{numeroRadicacion}/{idTarea}/registrarResultadosPrimeraRemision")
    public void registrarResultadosPrimeraRemision(@NotNull @PathParam("numeroRadicacion") String numeroRadicacion,
                                                   @PathParam("idTarea") Long idTarea, @NotNull List<AportanteRemisionComunicadoDTO> aportanteRemisionDTO,
                                                   @QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @Context UserDTO userDTO, @Context HttpHeaders header);

    /**
     * Servicio que se encarga de guardar el criterio de parametrización temporal.
     *
     * @param parametrizacionDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarParametrizacionGestionCobroTemporal")
    public void guardarParametrizacionGestionCobroTemporal(@NotNull CriteriosParametrizacionTemporalDTO criterioParametrizacionDTO);

    /**
     * Servicio que se encarga de guardar la segunda remisión del comunicado
     *
     * @param registroRemision Datos a guardar.
     */
    @POST
    @Path("/guardarSegundaRemision")
    public void guardarSegundaRemision(RegistroRemisionAportantesDTO registroRemision);

    /**
     * Servicio que se encarga de registrar la segunda remision del comunicado
     *
     * @param registroRemision Datos a guardar
     */
    @POST
    @Path("/registrarResultadoSegundaRemision")
    public void registrarResultadoSegundaRemision(RegistroRemisionAportantesDTO registroRemision);
    /* Fin proceso 223 */

    /**
     * Servicio que asigna una acción de cobro, de acuerdo a la línea de cobro y acción de cobro enviados como parámetros
     *
     * @param accionCobro Tipo de acción de cobro
     * @param userDTO     Información del usuario, tomada del contexto
     */
    @GET
    @Path("/asignarAccionesCobro")
    void asignarAccionesCobro(@QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de modificar el estado de la primera entrega de remisión de un aportante.
     *
     * @param aportanteRemision aportante para remisión.
     * @return estado de la solicitud cuando se guarda y cierra la entrega.
     */
    @POST
    @Path("/{numeroRadicacion}/guardarEntrega")
    public EstadoSolicitudGestionCobroEnum guardarEntrega(@PathParam("numeroRadicacion") String numeroRadicacion,
                                                          @NotNull AportanteRemisionComunicadoDTO aportanteRemision);

    /**
     * Servicio que realiza la asignación de acciones de cobro, así como las solicitudes asociadas a éstas
     *
     * @param accionCobro Tipo de acción de cobro
     * @param userDTO     Información del usuario, tomada del contexto
     */
    @GET
    @Path("/ejecutarProcesoAutomaticoGestionCobro")
    void ejecutarProcesoAutomaticoGestionCobro(@QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de aprobar o rechazar la desafiliacion
     *
     * @param numeroRadicacion       ingresa como parametro el numero de radicacion de la solicitud
     * @param observacionCoordinador ingresa como parametro la observacion del coordinador
     * @param idTarea                ingresa como parametro el id de la tarea en cuestion
     * @param aprobado               ingresa como parametro resultado de si aprobo o rechazo la desafiliacion
     */
    @POST
    @Path("/{numeroRadicacion}/tramitarDesafiliacion")
    public void tramitarDesafiliacion(@PathParam("numeroRadicacion") String numeroRadicacion, @NotNull @QueryParam("idTarea") Long idTarea,
                                      @QueryParam("observacionCoordinador") String observacionCoordinador, @NotNull @QueryParam("aprobado") boolean aprobado);

    /**
     * Servicio que se encarga de finalizar el proceso de desafiliacion
     *
     * @param desafiliacionDTO DTO con la informacion para desafiliar a los aportantes
     */
    @POST
    @Path("/finalizarSolicitudDesafiliacion")
    public void finalizarSolicitudDesafiliacion(DesafiliacionDTO desafiliacionDTO, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de gestionar la desafiliacion de los aportantes
     * que fueron seleccionador para ser expulsados
     *
     * @param desafiliacionAportanteDTOs lista de aportante para ser expulsados
     * @return numero de radicacion luego de aprobar la desafiliacion
     */
    @POST
    @Path("/gestionarDesafiliacion")
    public String gestionarDesafiliacion(@Context UserDTO userDTO, List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs);

    /**
     * Servicio que realiza el cierre masivo de solciitudes preventivas
     *
     * @param userDTO Información del usuario, tomado del contexto
     */
    @POST
    @Path("/cierreMasivoSolicitudPreventivaAgrupadora")
    public void cierreMasivoSolicitudPreventivaAgrupadora(@Context UserDTO userDTO);

    /**
     * Servicio encargado de guardar la gestión de un ciclo manual de forma temporal
     */
    @POST
    @Path("/guardarGestionCicloManualTemporal/{numeroOperacion}")
    public void guardarGestionCicloManualTemporal(@NotNull @PathParam("numeroOperacion") Long numeroOperacion,
                                                  @NotNull @QueryParam("tieneSolicitudManual") Boolean tieneSolicitudManual, @NotNull GestionCicloManualDTO gestionCicloDTO);

    /**
     * Servicio encargado de consultar la gestión de un ciclo manual temporal por el id de solicitud
     *
     * @param idCartera Número de operación de cartera
     * @return retorna GestionCicloManualDTO
     */
    @GET
    @Path("/consultarDatosTemporalesCartera/{numeroOperacion}")
    public GestionCicloManualDTO consultarDatosTemporalesCartera(@NotNull @PathParam("numeroOperacion") Long numeroOperacion);

    /**
     * Servicio encargado de finalizar la solicitud de gestión Manual
     */
    @POST
    @Path("/finalizarSolicitudGestionManual")
    public void finalizarSolicitudGestionManual(@NotNull @QueryParam("numeroOperacion") Long numeroOperacion,
                                                @NotNull @QueryParam("tieneSolicitudManual") Boolean tieneSolicitudManual, @NotNull @QueryParam("finalizar") Boolean finalizar,
                                                @Context UserDTO userDTO);

    /**
     * Servicio que actualiza los resultados de aportantes edictos en la acción de cobro 2E, y finaliza la tarea
     *
     * @param registroDTO datos de los registros de los aportantes
     */
    @POST
    @Path("/finalizarResultadosEdictos")
    public void finalizarResultadosEdictos(@NotNull RegistroRemisionAportantesDTO registroDTO, @Context HttpHeaders header);

    /**
     * Servicio encargado de guardar una notificación persona
     *
     * @param notificacionPersonalDTO, dto que contiene la información de la notificación judicial
     */
    @POST
    @Path("/guardarNotificacionPersonal")
    public void guardarNotificacionPersonal(@NotNull NotificacionPersonalModeloDTO notificacionPersonalDTO);

    /**
     * Servicio encargado de consultar los aportantes por gestión de cobro manual.
     *
     * @param filtros filtros de la consulta.
     * @return lista de los aportantes.
     */
    @POST
    @Path("/consultarAportantesGestionCobroManual")
    public List<AportanteGestionManualDTO> consultarAportantesGestionCobroManual(@NotNull FiltrosGestionCobroManualDTO filtros);

    /**
     * Servicio que consulta el total de deuda de un aportante registrado en cartera, agrupada por línea de cobro
     *
     * @param aportanteDTO Información del aportante
     * @return Listado de deuda por líena de cobro
     */
    @POST
    @Path("/obtenerCarteraAportante")
    public List<CarteraModeloDTO> obtenerCarteraAportante(@NotNull AportanteCarteraDTO aportanteDTO);

    /**
     * Servicio que almacena la gestión de deuda manual para los cotizantes en Cartera. HU-239
     *
     * @param userDTO
     * @param listaCotizantesDTO Lista de cotizantes (incluye novedades a aplicar)
     * @param periodo            Periodo de evaluación
     */
    @POST
    @Path("/registrarDeudaPersonaCartera")
    public void registrarDeudaPersonaCartera(@NotNull List<GestionDeudaDTO> listaCotizantesDTO, @QueryParam("periodo") String periodo,@Context UserDTO userDTO);

    /**
     * Servicio que genera el documento de liquidación de aportes
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoAportante        Tipo de aportante: EMPLEADOR, INDEPENDIENTE ó PENSIONADO
     * @param tipoLineaCobro       Tipo de línea de cobro
     * @return La información de la liquidación generada
     */
    @GET
    @Path("/generarLiquidacionAportes")
    public LiquidacionAporteCarteraDTO generarLiquidacionAportes(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante,
            @QueryParam("lineaCobro") TipoLineaCobroEnum tipoLineaCobro);

    /**
     * Servicio que consulta el detalle de la deuda de un aportante registrado en cartera
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de aportante
     * @param periodo              Periodo de deuda
     * @return La lista de trabajadores asociados a la deuda
     */
    @GET
    @Path("/consultarDetalleDeuda")
    public DetalleDeudaDTO consultarDetalleDeuda(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                 @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                 @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante, @QueryParam("periodo") Long periodo);

    /**
     * Servicio que consulta el detalle de la deuda de un aportante registrado en cartera
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de aportante
     * @param periodo              Periodo de deuda
     * @param lineaCobro           Línea de cobro consultada
     * @return La lista de trabajadores asociados a la deuda
     */
    @GET
    @Path("/consultarDetalleDeudaLC")
    public DetalleDeudaDTO consultarDetalleDeudaLC(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                   @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                   @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante, @QueryParam("periodo") Long periodo,
                                                   @QueryParam("lineaCobro") TipoLineaCobroEnum lineaCobro);

    /**
     * Servicio que consulta el estado de gestión manual para un aportante registrado en cartera
     */
    @GET
    @Path("/consultarEstadoGestionManualCartera")
    public EstadoFiscalizacionEnum consultarEstadoGestionManualCartera(@QueryParam("numeroRadicacion") String numeroRadicacion,
                                                                       @QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Servicio que almacena la bitácora de una liquidación de aportes generada manualmente
     */
    @GET
    @Path("/guardarBitacoraLiquidacion")
    public void guardarBitacoraLiquidacion(@QueryParam("numeroOperacion") Long numeroOperacion,
                                           @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                           @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                           @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante,
                                           @QueryParam("tipoActividad") TipoActividadBitacoraEnum tipoActividad,
                                           @QueryParam("resultado") ResultadoBitacoraCarteraEnum resultado, @QueryParam("idECM") String idECM, @Context UserDTO userDTO);

    /**
     * Servicio que consulta la existencia de una solicitud de gestión de cobro manual que se encuentre en estado asignada o en proceso
     *
     * @param numeroRadicado  Numero de radicación de la solicitud global
     * @param numeroOperacion Numero de operacion asociado a la cartera agrupadora del aportante
     * @return
     */
    @GET
    @Path("/consultarExistenciaGestionCobroManual")
    public Boolean consultarExistenciaGestionCobroManual(@QueryParam("numeroRadicado") String numeroRadicado,
                                                         @QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Consulta los cotizantes morosos relacionados a un empleador respecto a un periodo
     *
     * @param tipoIdentificacion   Tipo de identificacion dle aportante
     * @param numeroIdentificacion Número de Identificacion del aportante
     * @param tipoSolicitante      Tipo de solicitante del aportante
     * @param periodo,periodo      a consultar la morosidad
     * @return Retorna una lista de dependientes con el detalle de su deuda y estado respecto al empleador
     */
    @GET
    @Path("/consultarCotizantesMorosos")
    public List<CarteraDependienteModeloDTO> consultarCotizantesMorosos(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion, @NotNull @QueryParam("periodo") Long periodo);

    /**
     * Servicio que guarda los resultados de aportantes edictos en la acción de cobro 2E
     *
     * @param registroDTO datos de los registros de los aportantes
     * @return Dto con los datos de la solicitud de gestión de cobro físico
     */
    @POST
    @Path("/guardarResultadosEdictos")
    public SolicitudGestionCobroFisicoModeloDTO guardarResultadosEdictos(@NotNull RegistroRemisionAportantesDTO registroDTO);

    /**
     * <b>Descripción:</b>Metodo que se encarga de consultar las personas por
     * los filtros enviados retornando también el estado de cartera
     *
     * @param valorTI,         valor del tipo de documento
     * @param valorNI,         valor del numero de identificacion
     * @param primerNombre,    valor del primer nombre
     * @param primerApellido,  valor del primer apellido
     * @param fechaNacimiento, fecha de nacimiento
     * @return listado de personas que cumplen con los filtros de búsqueda
     */
    @GET
    @Path("/buscarPersonasCartera")
    public List<PersonaDTO> buscarPersonasCartera(@QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                  @QueryParam("tipoIdentificacion") TipoIdentificacionEnum valorTI, @QueryParam("numeroIdentificacion") String valorNI,
                                                  @QueryParam("primerNombre") String primerNombre, @QueryParam("primerApellido") String primerApellido,
                                                  @QueryParam("fechaNacimiento") Long fechaNacimiento, @QueryParam("idEmpleador") Long idEmpleador,
                                                  @QueryParam("segundoNombre") String segundoNombre, @QueryParam("segundoApellido") String segundoApellido,
                                                  @QueryParam("esVista360Web") Boolean esVista360Web);

    /**
     * <b>Descripción</b>Método que se encarga de consultar listado de
     * empleadores con su estado actual de cartera
     * <code>tipoIdentificacion: es el tipo de identificación del empleadro,
     * numeroIdentificacion: es el numero de identificación del empleador,
     * digitoVerificacion: es el numero de verificacion del tipo NIT y
     * razonSocial: es la razon social del empleador</code><br/>
     *
     * @param tipoIdentificacion,  tipo de identificación del empleador, de tipo enumeración
     * @param numeroIdentificacion Numero de identificación del empleador
     * @param digitoVerificacion   Digito de verificación del documento
     * @param razonSocial          Razon social del empleador
     * @param aplicaPre            Indica si debe consultar empeladores que se encuentren en
     *                             estado no formalizado con información
     * @return lista de empleadores
     */
    @GET
    @Path("/buscarEmpleadorCartera")
    public List<EmpleadorDTO> buscarEmpleadorCartera(@QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                     @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                     @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                     @QueryParam("razonSocial") String razonSocial, @QueryParam("aplicaPre") Boolean aplicaPre);


    /**
     * Se encarga de incluir un aportante al ciclo de fiscalización activo posterior de validar que no se encuentre en el mismo
     *
     * @param simulacionDTOs Datos del aportante para incluir a la fiscalización
     * @param proceso        Proceso de fiscalización
     * @param user           contexto del usuario
     */
    @POST
    @Path("/incluirAportante")
    public void incluirAportanteCicloFiscalizacion(SimulacionDTO simulacionDTO, @QueryParam("proceso") ProcesoEnum proceso,
                                                   @Context UserDTO user);

    /**
     * Servicio que retoma las acciones de cobro para personas que tuvieron un convenio de pago o una exclusión activa
     * Se realiza por el control de cambios #95-#96 Mantis 268883
     *
     * @param idPersona
     * @param tipoSolicitante
     */
    @POST
    @Path("/retomarAccionesCobro")
    public void retomarAccionesCobro(@QueryParam("idPersona") Long idPersona,
                                     @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante, @Context UserDTO user);

    /**
     * Servicio encargado de actualizar o guardar una exclusion de cartera
     *
     * @param exclusionCarteraDTO, DTO que contiene la información de la exclusion
     */
    @POST
    @Path("/actualizarExclusionCartera")
    public void actualizarExclusionCartera(@NotNull ExclusionCarteraDTO exclusionCarteraDTO, @Context UserDTO user);

    /**
     * Servicio encargado de actualizar o guardar una exclusion de cartera
     *
     * @param exclusionCarteraDTOS, DTO que contiene la información de la exclusion
     */
    @POST
    @Path("/actualizarExclusionCarteraMora")
    public void actualizarExclusionCarteraMora(@NotNull List<ExclusionCarteraDTO> exclusionCarteraDTOS, @Context UserDTO user);

    /**
     * Servicio que se encarga de realizar la ejecución del proceso automático de exclusion de cartera.
     */
    @POST
    @Path("/ejecutarProcesoAutomaticoExclusion")
    public void ejecutarProcesoAutomaticoExclusion();

    /**
     * Servicio cargueMasivo guardar cartera
     *
     * @param aportanteCarteraDTO, DTO que contiene la información del aportante
     * @param idArchivo
     * @return
     */
    @POST
    @Path("/insertarCargueMasivo")
    public RespuestaCargueMasivoAportanteDTO insertarCargueMasivo(@NotNull @QueryParam("idArchivo") String idArchivo);

    @GET
    @Path(value = "/ejecutarProcesoAutomaticoFirmezaTitulo")
    public void ejecutarProcesoAutomaticoFirmezaTitulo();

    @GET
    @Path(value = "/EjecutarProcesoAutomaticoActaLiquidacion")
    public void EjecutarProcesoAutomaticoActaLiquidacion();

    @GET
    @Path(value = "/DocumentoFiscalizacion")
    public List<Object[]> DocumentoFiscalizacion(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @GET
    @Path(value = "/ejecutarProcesoAutomaticoPrescribirCartera")
    public void ejecutarProcesoAutomaticoPrescribirCartera();

}
