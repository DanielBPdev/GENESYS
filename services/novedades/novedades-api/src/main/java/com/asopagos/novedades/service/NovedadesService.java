package com.asopagos.novedades.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.asopagos.dto.FiltroConsultaSolicitudesEnProcesoDTO;
import com.asopagos.dto.AfiliadoNovedadRetiroNoAplicadaDTO;
import com.asopagos.dto.SolicitudNovedadGeneralDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadEmpleador;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.dto.DatosAfiliadoRetiroDTO;
import com.asopagos.novedades.dto.DatosExcepcionNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorPaginadoDTO;
import com.asopagos.novedades.dto.DatosNovedadRegistradaPersonaDTO;
import com.asopagos.novedades.dto.DatosNovedadVista360DTO;
import com.asopagos.novedades.dto.FiltrosDatosNovedadDTO;
import com.asopagos.novedades.dto.GenerarReporteSupervivenciaDTO;
import com.asopagos.novedades.dto.IntentoNovedadDTO;
import com.asopagos.novedades.dto.RegistroPersonaInconsistenteDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
/**nuevo */
import com.asopagos.novedades.dto.DatosReporteAfiliadosSupervivenciaDTO;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import com.asopagos.enumeraciones.core.EstadoDesafiliacionMasivaEnum;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de novedades de una persoona o empleador <b>Historia de
 * Usuario:</b> Proceso 1.3
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@Path("novedades")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NovedadesService {

    /**
     * Método encargado de consultar una novedad por el nombre.
     *
     * @param nombre de la novedad a consultar.
     * @return novedad encontrada.
     */
    @GET
    @Path("consultarNovedad")
    public ParametrizacionNovedadModeloDTO consultarNovedadPorNombre(
            @QueryParam("tipoTransaccion") @NotNull TipoTransaccionEnum tipoTransaccion);

    /**
     * Método encargado de crear una solicitud de novedad.
     *
     * @param novedad datos de la novedad.
     * @return solicitud de novedad creada.
     */
    @POST
    @Path("crearSolicitudNovedad")
    public SolicitudNovedadModeloDTO crearSolicitudNovedad(SolicitudNovedadModeloDTO novedad);

    /**
     * Método encargado de actualizar la solicitud de novedad.
     *
     * @param solicitudNovedad solicitud de novedad a actualizar.
     */
    @PUT
    @Path("actualizarSolicitudNovedad")
    public void actualizarSolicitudNovedad(@QueryParam("idSolicitudNovedad") @NotNull Long idSolicitudNovedad,
                                           SolicitudNovedadModeloDTO solicitudNovedad);

    /**
     * Método que actualiza el estado de la solicitud de novedad.
     *
     * @param idSolicitudNovedad id de la solicitud de novedad.
     * @param estado             nuevo para la solicitud de novedad.
     */
    @POST
    @Path("/{idSolicitudGlobal}/estadoSolicitud")
    public void actualizarEstadoSolicitudNovedad(@PathParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal,
                                                 @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudNovedadEnum estadoSolicitud);

    /**
     * Método encargado de consultar una solicitud de novedad por el id.
     *
     * @param idSolicitudNovedad id de la solicitud de noevedad.
     * @return solicitud de la novedad encontrada.
     */
    @GET
    @Path("consultarSolicitudNovedad")
    public SolicitudNovedadModeloDTO consultarSolicitudNovedad(@QueryParam("idSolicitud") @NotNull Long idSolicitud);

    /**
     * Método que consulta las solicitudes de novedad asociadas a una persona.
     *
     * @param tipoIdentificacion   el tipo de identificacion de la persona.
     * @param numeroIdentificacion el número de identificacion de la persona.
     * @param canalRecepcion
     * @return List con las solicitudes encontradas.
     */
    @POST
    @Path("consultarSolicitudesNovedadEnProceso")
    public List<SolicitudNovedadEnProcesoDTO> consultarSolicitudesNovedadEnProceso(FiltroConsultaSolicitudesEnProcesoDTO filtro);

    /**
     * Método que crea un intento de novedad
     *
     * @param intentoNovedadDTO Es el intento a crear
     * @return Id del intento creado
     */
    @POST
    @Path("/intentoNovedad")
    public Long crearIntentoNovedad(@NotNull IntentoNovedadDTO intentoNovedadDTO);

    /**
     * Método encargado de crear una solicitud de novedad empleador.
     *
     * @param solicitud de novedad del empleador.
     * @return solicitud de novedad creada.
     */
    @POST
    @Path("crearSolicitudNovedadEmpleador")
    public SolicitudNovedadEmpleador crearSolicitudNovedadEmpleador(@NotNull SolicitudNovedadEmpleador solicitudNovedadEmpleador);

    /**
     * Método encargado de crear una solicitud de novedad afiliado.
     *
     * @param solicitud de novedad del empleador.
     * @return solicitud de novedad creada.
     */
    @POST
    @Path("crearSolicitudNovedadPersona")
    public SolicitudNovedadPersona crearSolicitudNovedadPersona(@NotNull SolicitudNovedadPersona solicitudNovedadPersona);

    /**
     * Método que consulta las solicitudes de novedad asociadas a una persona.
     *
     * @param tipoIdentificacion   el tipo de identificacion de la persona.
     * @param numeroIdentificacion el número de identificacion de la persona.
     * @param canalRecepcion
     * @return List con las solicitudes encontradas.
     */
    @POST
    @Path("consultarSolicitudesNovedad")
    public List<SolicitudNovedadModeloDTO> consultarSolicitudesNovedad(
            @QueryParam("tipoIdentificacion") @NotNull TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") @NotNull Long numeroIdentificacion,
            @QueryParam("canalRecepcion") CanalRecepcionEnum canalRecepcion,
            @QueryParam("tipoSolicitante") TipoTipoSolicitanteEnum tipoSolicitante);

    /**
     * Metodo encargar de generar el reporte de afiliados para consultar supervivencia
     *
     * @param gerarReporte
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/generarReporteAfiSupervivencia")
    public Response generarReporteAfiliadosSupervivencia(@Valid @NotNull GenerarReporteSupervivenciaDTO gerarReporte);
/**
 * Metodo encargar de generar el reporte de afiliados para consultar supervivencia Json
 * @param gerarReporte
 * @return
 */
    /**
     * <b>Descripción:</b> Método que se encarga de consultar una persona por el
     * id.
     *
     * @param estado filtro estado para generar reporte
     * @return listado cedulas segun estado
     */
    @POST
    @Path("/generarReporteAfiSupervivenciaJson")
    public List<DatosReporteAfiliadosSupervivenciaDTO> generarReporteAfiliadosSupervivenciaJson(@Valid @NotNull GenerarReporteSupervivenciaDTO gerarReporte);


    /**
     * Método encargado de consultar las novedades asociadas a un proceso
     *
     * @param procesoEnum el proceso por el cual se van a filtrar las novedades
     * @return
     */
    @GET
    @Path("/{procesoEnum}/consultarNovedades")
    public List<ParametrizacionNovedadModeloDTO> consultarNovedades(@PathParam("procesoEnum") @NotNull ProcesoEnum procesoEnum);

    @GET
    @Path("buscarPersonasPendientesActulizar")
    public List<RegistroPersonaInconsistenteDTO> buscarPersonasPendientesActulizar(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") Long numeroIdentificacion, @QueryParam("fechaIngresoBandeja") Long fechaIngresoBandeja);

    @POST
    @Path("guardarRegistroPersonaInconsistencia")
    public void guardarRegistroPersonaInconsistencia(List<RegistroPersonaInconsistenteDTO> dto);

    /**
     * Método encargado de almacenar una solicitud de novedad masiva para Personas.
     *
     * @param idSolicitudNovedad        id de la solicitudNovedad.
     * @param datosNovedadAutomaticaDTO Datos de la novedad automatica Personas
     */
    @POST
    @Path("{idSolicitudNovedad}/almacenarSolicitudNovedadPersonaMasivo")
    public void almacenarSolicitudNovedadPersonaMasivo(@NotNull @PathParam("idSolicitudNovedad") Long idSolicitudNovedad,
                                                       @NotNull DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO);

    /**
     * Método encargado de cerrar solicitudes masivo por Desistimiento.
     *
     * @param idSolicitudNovedad id de la solicitudNovedad.
     * @param idPersonas         Personas asociados a la solicitud.
     */
    @POST
    @Path("/actualizarSolicitudesNovedadDesistimiento")
    public void actualizarSolicitudesNovedadDesistimiento();

    /**
     * Servicio encargado de crear una novedad futura.
     *
     * @param novedadFuturaDTO novedad futura dto.
     */
    @POST
    @Path("/crearNovedadFutura")
    public void crearNovedadFutura(RegistroNovedadFuturaModeloDTO novedadFuturaDTO);

    /**
     * Método encargado de crear una solicitud de novedad Pila
     *
     * @param solicitud de novedad Pila.
     */
    @POST
    @Path("/crearSolicitudNovedadPila")
    public void crearSolicitudNovedadPila(@NotNull SolicitudNovedadPilaModeloDTO solicitudNovedadPila);

    /**
     *
     */
    @POST
    @Path("/consultarRegistroPersonaInconsistente")
    public boolean consultarRegistroPersonaInconsistente(@NotNull PersonaModeloDTO persona);

    /**
     * Método encargado de actualizar una solicitud de novedad de persona agregandole el beneficiario
     *
     * @param solicitudNovedadPersona solicitud de novedad de persona
     */
    @POST
    @Path("/actualizarSolicitudNovedadPersona")
    public void actualizarSolicitudNovedadPersona(@NotNull SolicitudNovedadPersona solicitudNovedadPersona);

    /**
     * Servicio encargado de la consulta de las novedades futuras que se deben registrar por el cumplimiento de la novedad automatica
     *
     * @return Lista de novedades futuras a registrar
     */
    @GET
    @Path("/consultarNovedadesFuturas")
    public List<RegistroNovedadFuturaModeloDTO> consultarNovedadesFuturas();

    /**
     * Actualiza los registros de novedad futura indicando que se procesaron
     *
     * @param registroNovedadFuturaModeloDTO
     */
    @POST
    @Path("/actualizarRegistroNovedadFuturaMasivo")
    public void actualizarRegistroNovedadFuturaMasivo(List<RegistroNovedadFuturaModeloDTO> listRegistros);

    /**
     * Servicio encargado de la consulta de las novedades futuras que se deben registrar por el cumplimiento de la novedad automatica
     *
     * @return Lista de novedades futuras a registrar
     */
    @POST
    @Path("/consultarNovedadesEmpleador")
    public DatosNovedadEmpleadorPaginadoDTO consultarNovedadesEmpleador(@NotNull FiltrosDatosNovedadDTO filtrosDatosNovedad,
                                                                        @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Servicio para la consulta de los datos de la pestaña Novedades en la Vista 360 de personas.
     *
     * @param tipoIdentificacion   Tipo de Identificación de la persona
     * @param numeroIdentificacion Número de Identificación de la persona
     * @param esBeneficiario       Indica si la persona es beneficiario
     * @return Datos asociados a las Novedades de la Persona.
     */
    @GET
    @Path("/consultarNovedadesPersonaVista360")
    public DatosNovedadVista360DTO consultarNovedadesPersonaVista360(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("esBeneficiario") Boolean esBeneficiario);

    /**
     * Servicio para la consulta de las novedades registradas a la persona desde el ambito de persona
     *
     * @param uriInfo              Informacion para la paginacion de la consulta
     * @param response             Informaicon del formato de respuesta
     * @param tipoIdentificacion   Tipo de idnetificacion de la persona a buscar
     * @param numeroIdentificacion Numero de identificacion de la persona a buscar
     * @return Lista de novedades asociadas a la persona a buscar
     */
    @GET
    @Path("/consultarNovedadesRegistradasPersona")
    public List<DatosNovedadRegistradaPersonaDTO> consultarNovedadesRegistradasPersona(@Context UriInfo uriInfo,
                                                                                       @Context HttpServletResponse response, @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                                                       @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio para la consulta de las novedades registradas a la persona como beneficiario
     *
     * @param uriInfo              Informacion para la paginacion de la consulta
     * @param response             Informaicon del formato de respuesta
     * @param tipoIdentificacion   Tipo de idnetificacion de la persona a buscar
     * @param numeroIdentificacion Numero de identificacion de la persona a buscar
     * @return Lista de novedades asociadas a la persona a buscar
     */
    @GET
    @Path("/consultarNovedadesRegistradasPersonaBeneficiario")
    public List<DatosNovedadRegistradaPersonaDTO> consultarNovedadesRegistradasPersonaBeneficiario(@Context UriInfo uriInfo,
                                                                                                   @Context HttpServletResponse response, @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                                                                   @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Consulta el número consecutivo de la novedad de cascada ([numeroRadicado]_[consecutivo])
     *
     * @param numeroRadicado
     * @return
     */
    @GET
    @Path("/obtenerConsecutivoNovedad")
    public Integer obtenerConsecutivoNovedad(@QueryParam("numeroRadicado") String numeroRadicado);

    /**
     * Guarda los datos asociados a una exepcion.
     *
     * @param numeroRadicado
     * @return
     * @throws Exception
     */
    @POST
    @Path("/guardarExcepcionNovedad")
    public void guardarExcepcionNovedad(DatosExcepcionNovedadDTO solicitudNovedadDTO, @QueryParam("excepcion") String excepcion, @Context UserDTO userDTO) throws Exception;

    /**
     * Consulta la data asociada a los afiliados que se encuentran y activos
     * y registran beneficiarios activos
     *
     * @return
     */
    @GET
    @Path("/consultarAfiliadosInactivosBeneficiariosActivos")
    public List<DatosAfiliadoRetiroDTO> consultarAfiliadosInactivosBeneficiariosActivos();

    /**
     * Servicio encargado de la consulta de las novedades registrada para ser mostradas en la vista 360
     *
     * @return Lista de novedades registradas aprobadas
     */
    @POST
    @Path("/consultarNovedadesEmpleadorVista360")
    public DatosNovedadEmpleadorPaginadoDTO consultarNovedadesEmpVista360(@NotNull FiltrosDatosNovedadDTO filtrosDatosNovedad,
                                                                          @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Servicio encargado de la consulta de las novedades sin instancia de proceso
     *
     * @return Lista de novedades registradas aprobadas
     */
    @GET
    @Path("/obtenerNovedadesSinInstanciaProceso")
    public List<SolicitudNovedadGeneralDTO> obtenerNovedadesSinInstanciaProceso();

    /**
     * Servicio encargado de la consulta de las novedades sin instancia de proceso
     *
     * @param tempId
     * @return Lista de novedades registradas aprobadas
     */
    @GET
    @Path("/TransaccionNovedadPilaCompleta")
    public Long TransaccionNovedadPilaCompleta(@QueryParam("tempId") Long tempId);


    /**
     * @param tempNovedadId
     * @param excepcion
     */
    @PUT
    @Path("/GuardarExcepcionNovedadPila")
    public void GuardarExcepcionNovedadPila(@QueryParam("tempNovedadId") Long tempNovedadId, @QueryParam("excepcion") String excepcion);


    /**
     * Método encargado de insertar una persona inconsistente
     */
    @POST
    @Path("/InsertarRegistroPersonaInconsistente")
    public void InsertarRegistroPersonaInconsistente(@NotNull RegistroPersonaInconsistenteDTO inconsistenteDTO);

    @GET
    @Path("/ConsultarTipoNumeroIdentificacion")
    public Long ConsultarTipoNumeroIdentificacion(@QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Método que se encarga de consultar si la novedad es de periodo retroactivo
     */

    @GET
    @Path("/consultarRetroactividadNovedad/{idRegistroDetallado}")
    public Boolean consultarRetroactividadNovedad(@NotNull @PathParam("idRegistroDetallado") Long idRegistroDetallado);

    @POST
    @Path("/validacionesRegistroArchConfirmAbonosBancario")
    public RespuestaValidacionArchivoDTO validacionesRegistroArchConfirmAbonosBancario(
            @NotNull @QueryParam("tipoIdenAdminSubsidio") String tipoIdenAdminSubsidio,
            @NotNull @QueryParam("numeroIdenAdminSubsidio") String numeroIdenAdminSubsidio,
            @NotNull @QueryParam("casId") String casId,
            @NotNull @QueryParam("tipoCuenta") String tipoCuenta,
            @NotNull @QueryParam("numeroCuenta") String numeroCuenta,
            @NotNull @QueryParam("valorTransferencia") String valorTransferencia,
            @NotNull @QueryParam("resultadoAbono") String resultadoAbono,
            @NotNull @QueryParam("motivoRechazoAbono") String motivoRechazoAbono,
            @NotNull @QueryParam("fechaConfirmacionAbono") String fechaConfirmacionAbono,
            @NotNull @QueryParam("idValidacion") String idValidacion
    );

    @GET
    @Path("/ejecutarDesafiliacionTrabajadoresEmpledorMasivo")
    public void ejecutarDesafiliacionTrabajadoresEmpledorMasivo(@QueryParam("numerRadicacionEmpresa")
                                                                String numerRadicacionEmpresa, @QueryParam("idEmpledor") Long idEmpledor);


    @GET
    @Path("/obtenerTitularCuentaAdministradorSubsidioByCasId")
    public Object[] obtenerTitularCuentaAdministradorSubsidioByCasId(@QueryParam("casId")  String casId);


    @GET
    @Path("/consultarTipoNumeroIdentificacionYTipo")
    public Boolean consultarTipoNumeroIdentificacionYTipo(@QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                          @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion);

    /**
     * @param Método encargado de consultar todos los juzgados
     * @return Lista de todos los juzgados
     */
    @GET
    @Path("consultarJuzgados")
    public List<OficinaJuzgadoDTO> consultarJuzgados();

    @PUT
    @Path("/actualizarPersonaDetalleFallecido")
    void actualizarPersonaDetalleFallecido(@QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                           @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, PersonaDetalleModeloDTO personaDetalleModeloDTO);

        @POST
        @Path("/EmpleadoresProcesar")
        public void empleadoresProcesar(@NotNull List<Long> idEmpleadores,@NotNull @QueryParam("numeroRadicado") String numeroRadicado,@NotNull @QueryParam("estado") EstadoDesafiliacionMasivaEnum estado,@NotNull @QueryParam("intentosDiarios") int intentosDiarios);


        @GET
        @Path("/ObtenerEmpleadoresProcesar")
        public List<Object[]> obtenerEmpleadoresProcesar();

    @POST 
    @Path("/consultarSolicitudesYNovedadCerradasPorEmpleador")
    public List<SolicitudNovedadEnProcesoDTO> consultarSolicitudesYNovedadCerradasPorEmpleador(FiltroConsultaSolicitudesEnProcesoDTO filtro);
    
    }
