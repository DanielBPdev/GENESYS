package com.asopagos.fovis.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import com.asopagos.dto.CalificacionPostulacionDTO;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.PostulacionAsignacionDTO;
import com.asopagos.dto.fovis.HistoricoAsignacionFOVISDTO;
import com.asopagos.dto.fovis.HistoricoCrucesFOVISDTO;
import com.asopagos.dto.fovis.InformacionDocumentoActaAsignacionDTO;
import com.asopagos.dto.fovis.InformacionSubsidioFOVISDTO;
import com.asopagos.dto.fovis.IntentoPostulacionDTO;
import com.asopagos.dto.fovis.MiembroHogarDTO;
import com.asopagos.dto.fovis.ParametrizacionMedioPagoDTO;
import com.asopagos.dto.fovis.PostulacionDTO;
import com.asopagos.dto.fovis.ResultadoHistoricoSolicitudesFovisDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.fovis.EstadoCicloAsignacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAsignacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.fovis.ResultadoAsignacionEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.dto.CartasAsignacionDTO;
import com.asopagos.fovis.dto.PostulanteDTO;
import com.asopagos.fovis.dto.RedireccionarTareaDTO;
import com.asopagos.fovis.dto.TareasHeredadasDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de del Proceso FOVIS <b>Historia de Usuario:</b> Proceso 3.2
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Path("fovis")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface FovisService {

    /**
     * tope maximo permitido por el Decreto 1467 del 2019 para la adquisicion de vivienda urbana
     * @param identificadorMunicipio
     *        Identificador de el municipio
     * @return Tope Maximo para dicha ubicación
     */
    @GET
    @Path("/consultarTopeDec14672019")
    public Long consultarTopeDec14672019(@QueryParam("identificadorMunicipio") Long munId);
    /**
     * Servicio que consulta los proyectos de vivienda asociados a un oferente
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del oferente
     * @param numeroIdentificacion
     *        Número de identificación del oferente
     * @param proyectoRegistrado
     *        Propiedad para filtrar los proyectos que estén registrados (el valor false o null no filtran)
     * @param proyectosConLicencia
     *        Propiedad para filtrar los proyectos que estén con licencia vigente, tanto de construcción como urbanismo
     *        (el valor false o null no filtran)
     * @return La lista de proyectos asociados
     */
    @GET
    @Path("/consultarProyectosOferente")
    List<ProyectoSolucionViviendaModeloDTO> consultarProyectosOferente(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, 
            @QueryParam("proyectoRegistrado") Boolean proyectoRegistrado,
            @QueryParam("proyectosConLicencia") Boolean proyectosConLicencia);

    /**
     * Servicio que crea o actualiza un proyecto en la tabla
     * <code>ProyectoSolucionVivienda</code>
     * 
     * @param proyectoDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/crearActualizarProyectoSolucionVivienda")
    ProyectoSolucionViviendaModeloDTO crearActualizarProyectoSolucionVivienda(@NotNull ProyectoSolucionViviendaModeloDTO proyectoDTO);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>SolicitudPostulacion</code>
     * 
     * @param solicitudPostulacionDTO
     *        La información del registro a crear/actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarSolicitudPostulacion")
    SolicitudPostulacionModeloDTO crearActualizarSolicitudPostulacion(@NotNull SolicitudPostulacionModeloDTO solicitudPostulacionDTO);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>PostulacionFOVIS</code>
     * 
     * @param postulacionFOVISDTO
     *        La información del registro a crear/actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarPostulacionFOVIS")
    PostulacionFOVISModeloDTO crearActualizarPostulacionFOVIS(@NotNull PostulacionFOVISModeloDTO postulacionFOVISDTO);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>AhorroPrevio</code>
     * 
     * @param ahorroPrevioDTO
     *        La información del registro a actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarAhorroPrevio/{idPostulacion}")
    List<AhorroPrevioModeloDTO> crearActualizarAhorroPrevio(@NotNull List<AhorroPrevioModeloDTO> ahorrosPreviosDTO,
            @NotNull @PathParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>RecursoComplementario</code>
     * 
     * @param recursoComplementarioDTO
     *        La información del registro a actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarRecursoComplementario/{idPostulacion}")
    List<RecursoComplementarioModeloDTO> crearActualizarRecursoComplementario(
            @NotNull List<RecursoComplementarioModeloDTO> recursosComplementariosDTO,
            @NotNull @PathParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>Licencia</code>
     * 
     * @param licenciaDTO
     *        Información del registro a actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarLicencia")
    LicenciaModeloDTO crearActualizarLicencia(@NotNull LicenciaModeloDTO licenciaDTO);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>LicenciaDetalle</code>
     * 
     * @param licenciaDetalleDTO
     *        La información del registro a actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarLicenciaDetalle")
    LicenciaDetalleModeloDTO crearActualizarLicenciaDetalle(@NotNull LicenciaDetalleModeloDTO licenciaDetalleDTO);

    /**
     * Servicio para consultar los parámetros generales de FOVIS
     * @return La lista de los parámetros generales de FOVIS
     */
    @GET
    @Path("/consultarDatosGeneralesFovis")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParametrizacionFOVISModeloDTO> consultarDatosGeneralesFovis();

    /**
     * Servicio para consultar los datos de parametrización para las modalidades.
     * @return La parametrización de las modalidades.
     */
    @GET
    @Path("/consultarParametrizacionModalidades")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParametrizacionModalidadModeloDTO> consultarParametrizacionModalidades();

    /**
     * Servicio para consultar los medios de pago en la CCF para FOVIS.
     * @return Los Medios de Pago.
     */
    @GET
    @Path("/consultarMediosDePago")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParametrizacionMedioPagoDTO> consultarMediosDePago();

    /**
     * Servicio para consultar los ciclos de asignación por el año escogido.
     * @param anio
     *        Año escogido para consultar
     * @return Los ciclos de asignación parametrizados para el año consultado.
     */
    @GET
    @Path("/consultarCiclosAsignacionPorAnio")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacionPorAnio(@NotNull @QueryParam("anio") Short anio);

    /**
     * Servicio que consulta los rangos SVF parametrizados por modalidad.
     * @param modalidad
     *        Identificador de la modalidad.
     * @return La lista de rangos parametrizados.
     */
    @GET
    @Path("/consultarRangosSVFPorModalidad")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RangoTopeValorSFVModeloDTO> consultarRangosSVFPorModalidad(@NotNull @QueryParam("modalidad") ModalidadEnum modalidad);

    /**
     * Servicio que consulta las formas de pago habilitadas para las modalidades parametrizadas de FOVIS.
     * @param idParametrizacionModalidad
     *        identificador de la parametrización de la modalidad para FOVIS.
     * @return La lista de las formas de pago habilitadas para la modalidad.
     */
    @GET
    @Path("/consultarFormasDePagoPorModalidad")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FormaPagoModalidadModeloDTO> consultarFormasDePagoPorModalidad(@NotNull @QueryParam("modalidad") ModalidadEnum modalidad);

    /**
     * Servicio que consulta la parametrizacion de modalidades habilitadas para el ciclo de asignación.
     * @param idCicloAsignacion
     *        identificador del ciclo de asignación.
     * @param habilitadas
     *        Indica si se consultan solo las habilitadas en parametrización.
     * @return La lista de la parametrizacion de modalidades para el ciclo.
     */
    @GET
    @Path("/consultarModalidadesPorCiclo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CicloModalidadModeloDTO> consultarModalidadesPorCiclo(@NotNull @QueryParam("idCicloAsignacion") Long idCicloAsignacion,
            @QueryParam("habilitadas") Boolean habilitadas);

    /**
     * Servicio para consultar los ciclos de asignación por la modalidad seleccionada.
     * @param modalidad
     *        Modalidad seleccionada
     * @return Los ciclos de asignación que tienen parametrizados la modalidad seleccionada.
     */
    @GET
    @Path("/consultarCiclosAsignacionPorModalidad")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacionPorModalidad(@NotNull @QueryParam("modalidad") ModalidadEnum modalidad);
     
    
    /**
     * Servicio para actualizar los parámetros generales de FOVIS.
     * @param parametrizacionFOVIS
     *        Lista con los parametros a registrar.
     */
    @POST
    @Path("/actualizarDatosGeneralesFovis")
    public void actualizarDatosGeneralesFovis(@NotNull List<ParametrizacionFOVISModeloDTO> parametrizacionFOVIS);

    /**
     * Servicio para actualizar la tabla parametrica ParametrizacionModalidad.
     * @param modalidadesFOVIS
     *        Lista con las modalidades a actualizar.
     */
    @POST
    @Path("/actualizarModalidades")
    public void actualizarModalidades(@NotNull List<ParametrizacionModalidadModeloDTO> modalidadesFOVIS);

    /**
     * Servicio para actualizar los medios de pago disponibles en la CCF.
     * @param parametrizacionesMedioPagoDTO
     *        Lista con los medios de pago.
     */
    @POST
    @Path("/actualizarMediosDePago")
    public void actualizarMediosDePago(@NotNull List<ParametrizacionMedioPagoDTO> parametrizacionesMedioPagoDTO);

    /**
     * Servicio para registrar los datos del ciclo de asignación.
     * @param cicloAsignacionModelDTO
     *        Ciclo de asignación a crear o actualizar.
     * @return El ciclo de asignación registrado.
     */
    @POST
    @Path("/registrarCicloAsignacion")
    public CicloAsignacionModeloDTO registrarCicloAsignacion(@NotNull CicloAsignacionModeloDTO cicloAsignacionModelDTO);

    /**
     * Servicio para registrar las modalidades habilitadas para un determinado ciclo de asignación.
     * @param cicloAsignacionModelDTO
     *        Ciclo de asignación con la lista de las modalidaddes a registrar.
     */
    @POST
    @Path("/registrarModalidadesCicloAsignacion")
    public void registrarModalidadesCicloAsignacion(CicloAsignacionModeloDTO cicloAsignacionModelDTO);

    /**
     * Servicio que crea un conjunto de condiciones especiales por persona
     * 
     * @param idPersona
     *        Identificador único de la persona
     * @param listaCondicionEspecial
     *        Lista de condiciones especiales
     * @param idPostulacion
     *        Identificador de la postulacion
     */
    @POST
    @Path("/crearListaCondicionEspecialPersona/{tipoIdentificacion}/{numeroIdentificacion}/{idPostulacion}")
    void crearListaCondicionEspecialPersona(@PathParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @PathParam("numeroIdentificacion") String numeroIdentificacion,            
            @NotNull List<NombreCondicionEspecialEnum> listaCondicionEspecial,
            @PathParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio encargado de consultar una solicitud de postulación por el id.
     * @param idSolicitudGlobal
     *        Identificador global de la solicitud de postulación.
     * @return Solicitud de postulación encontrada.
     */
    @GET
    @Path("/consultarSolicitudPostulacion")
    @Produces(MediaType.APPLICATION_JSON)
    public SolicitudPostulacionModeloDTO consultarSolicitudPostulacion(@QueryParam("idSolicitud") @NotNull Long idSolicitudGlobal);

    /**
     * Servicio que actualiza el estado de la solicitud de postulación.
     * @param idSolicitudGlobal
     *        id de la solicitud.
     * @param estadoSolicitud
     *        Nuevo estado para la solicitud de postulación.
     */
    @POST
    @Path("/{idSolicitudGlobal}/estadoSolicitud")
    public void actualizarEstadoSolicitudPostulacion(@PathParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal,
            @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudPostulacionEnum estadoSolicitud);

    /**
     * Servicio que verifica si existen escalamientos sin resultados de análisis para una solicitud.
     * @param idSolicitudGlobal
     *        Identificador global de la solicitud.
     * @return indicador de si existen o no escalamientos para la solicitud.
     */
    @GET
    @Path("/existenEscalamientosSinResultado")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean existenEscalamientosSinResultado(@QueryParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal);

    /**
     * Servicio encargado de consultar los ciclos de asignación que no son predecesores de ningun otro, pudiendo excluir el ciclo a
     * modificar e incluyendo el ciclo predecesor ya asignado.
     * @param idCicloAsignacion
     *        Identificador del ciclo de asignación al cual se le asignará los no predecesores, puede ser nulo.
     * @param idCicloPredecesor
     *        Identificador del ciclo predecesor del que se modificará, puede ser nulo.
     * @return Lista de ciclos no predecesores.
     */
    @GET
    @Path("/consultarCiclosNoPredecesores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CicloAsignacionModeloDTO> consultarCiclosNoPredecesores(@QueryParam("idCicloAsignacion") Long idCicloAsignacion,
            @QueryParam("idCicloPredecesor") Long idCicloPredecesor);

    /**
     * Servicio encargado de consultar una postulación FOVIS por el id.
     * @param idPostulacion
     *        Identificador de la postulación.
     * @return Postulación FOVIS encontrada.
     */
    @GET
    @Path("/consultarPostulacionFOVIS")
    @Produces(MediaType.APPLICATION_JSON)
    public PostulacionFOVISModeloDTO consultarPostulacionFOVIS(@QueryParam("idPostulacion") @NotNull Long idPostulacion);

    /**
     * Servicio encargado de consultar personas detalle por el identificador de la postulación FOVIS.
     * @param idPostulacion
     *        Identificador de la postulación.
     * @return Lista de PersonaDetalle encontradas.
     */
    @GET
    @Path("/consultarPersonasDetallePorPostulacion")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonaDetalleModeloDTO> consultarPersonasDetallePorPostulacion(@QueryParam("idPostulacion") @NotNull Long idPostulacion);

    /**
     * <b>Descripción:</b> Método que permite registrar un intento de postulacion en la base de datos.
     * @param intentoPostulacionDTO
     *        el objeto de tipo IntentoPostulacionDTO que será persistido
     */
    @POST
    @Path("/registrarIntentoPostulacionFOVIS")
    public Long registrarIntentoPostulacionFOVIS(@NotNull IntentoPostulacionDTO intentoPostulacionDTO);

    /**
     * Servicio que consulta los datos de una solicitud global, por número de radicado
     * @param numeroRadicado
     *        Número de radicado
     * @return Objeto <code>SolicitudModeloDTO</code> con la información de la solicitud
     */
    @POST
    @Path("/consultarSolicitud/{numeroRadicado}")
    SolicitudModeloDTO consultarSolicitudPorRadicado(@PathParam("numeroRadicado") String numeroRadicado);

    /**
     * Servicio que consulta el departamento con la marca de excepción que aplica al Subsidio Familiar de Vivienda urbano
     * @return Objeto <code>DepartamentoModeloDTO</code> con la información del departamento
     */
    @GET
    @Path("/consultarDepartamentoExcepcionFOVIS")
    DepartamentoModeloDTO consultarDepartamentoExcepcionFOVIS();

    /**
     * Servicio para consultar los ciclos de asignación por el estado enviado.
     * @param estadoCicloAsignacion
     *        Estado del ciclo de asignación para consultar
     * @return Los ciclos de asignación para el estado consultado.
     */
    @GET
    @Path("/consultarCiclosAsignacionPorEstado")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacionPorEstado(
            @NotNull @QueryParam("estadoCicloAsignacion") EstadoCicloAsignacionEnum estadoCicloAsignacion);

    /**
     * Método encargado de consultar las postulaciones de un determinado ciclo de asignación en las que apliquen las condiciones para envío
     * a control interno.
     * @param idCicloAsignacion
     *        Identificador del ciclo de asignación.
     * @return El listado de postulaciones que aplican.
     */
    @GET
    @Path("/obtenerPostulacionesParaControlInterno")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SolicitudPostulacionModeloDTO> obtenerPostulacionesParaControlInterno(
            @NotNull @QueryParam("idCicloAsignacion") Long idCicloAsignacion);

    /**
     * Método encargado de relacionar el documento adjunto de control interno a la solicitud de postulación.
     * @param docAdminEstadoSolicitudDTO
     *        Objeto con la información del documento a persistir.
     */
    @POST
    @Path("/registrarDocumentoSolicitud")
    public void registrarDocumentoSolicitud(@NotNull DocumentoAdministracionEstadoSolicitudDTO docAdminEstadoSolicitudDTO);

    /**
     * Servicio encargado de consultar los integrantes del hogar por el identificador de la postulación FOVIS.
     * @param idPostulacion
     *        Identificador de la postulación.
     * @return Lista de Integrantes del Hogar encontradoss.
     */
    @GET
    @Path("/consultarIntegrantesHogar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<IntegranteHogarModeloDTO> consultarIntegrantesHogar(@QueryParam("idPostulacion") @NotNull Long idPostulacion);

    /**
     * <d>Descripción</b> Servicio encargado de generar el listado de postulantes en un período determinado.
     * @return la lista de PostulantesDTO con los postulantes a FOVIS.
     */
    @GET
    @Path("/consultarPostulantes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PostulanteDTO> consultarPostulantes(@QueryParam("idCicloAsignacion") @NotNull String idCicloAsignacion);

    /**
     * Servicio encargado de consultar el ciclo sucesor de un determinado ciclo.
     * @param idCicloAsignacion
     *        Identificador de ciclo de asignación al que se le buscará el sucesor.
     * @param fecha
     *        Fecha de postulación a partir de la cual se buscará el primer sucesor que se encuentre.
     * @param idCicloPredecesor
     *        Identificador del ciclo que se omitirá si aplica.
     * @return El ciclo sucesor.
     */
    @GET
    @Path("/consultarCicloSucesor")
    @Produces(MediaType.APPLICATION_JSON)
    public CicloAsignacionModeloDTO consultarCicloSucesor(@QueryParam("idCicloAsignacion") Long idCicloAsignacion,
            @QueryParam("fecha") @NotNull Long fecha, @QueryParam("idCicloPredecesor") Long idCicloPredecesor);

    /**
     * Servicio que consulta el histórico de postulaciones FOVIS, por persona
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona
     * @param numeroIdentificacion
     *        Número de identificación de la persona
     * @return Lista de registros con las postulaciones asociadas al hogar de la persona
     */
    @GET
    @Path("/consultarHistoricoPostulaciones")
    List<PostulacionDTO> consultarHistoricoPostulaciones(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio que crea o actualiza una solicitud global en base de datos
     * @param solicitudDTO
     *        Información de la solicitud
     * @return El identificador del registro creado/actualizado
     */
    @POST
    @Path("/guardarSolicitudGlobal")
    Long guardarSolicitudGlobal(@NotNull SolicitudModeloDTO solicitudDTO);

    /**
     * Servicio que consulta si existe una solicitud de clasificación HOGAR para un usuario web.
     * @param nombreUsuario
     * @return identificador de la solicitud.
     */
    @GET
    @Path("/consultarSolicitudUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public SolicitudModeloDTO consultarSolicitudUsuario(@Context UserDTO usuario);

    /**
     * Servicio que consulta las solicitudes de postulación asociadas al jefe del hogar, por los datos de la persona.
     * @param numeroIdentificacion
     *        Número de identificación de la persona.
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona.
     * @return Lista de solicitudes de postulación en proceso encontradas.
     */
    @GET
    @Path("/consultarSolicitudesPostulacionEnProceso")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SolicitudPostulacionModeloDTO> consultarSolicitudesPostulacionEnProceso(
            @QueryParam("numeroIdentificacion") @NotNull String numeroIdentificacion,
            @QueryParam("tipoIdentificacion") @NotNull TipoIdentificacionEnum tipoIdentificacion);

    /**
     * Servicio que consulta las postulaciones existentes en un ciclo de asignacion
     * @param idCicloAsignacion
     *        : Id del ciclo de asignacion a evaluar
     * @return postulacionesDTO : Listado de las postulaciones encontradas en el ciclo de asigancion
     */
    @POST
    @Path("/consultarPostulacionesEnCicloAsignacion")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SolicitudPostulacionModeloDTO> consultarPostulacionesEnCicloAsignacion(@NotNull List<EstadoHogarEnum> estadosHogar,
            @QueryParam("idCicloAsignacion") @NotNull Long idCicloAsignacion);

    /**
     * Servicio que consulta un ciclo de asignación por el identificador.
     * @param idCicloAsignacion
     *        Identificador del ciclo de asignación a consultar.
     * @return El ciclo de asignación.
     */
    @GET
    @Path("/consultarCicloAsignacion")
    @Produces(MediaType.APPLICATION_JSON)
    public CicloAsignacionModeloDTO consultarCicloAsignacion(@QueryParam("idCicloAsignacion") @NotNull Long idCicloAsignacion);

    /**
     * Servicio que almacena definitivamente los resultados de asignación en la tabla
     * <code>ResultadosAsignacionFOVIS</code>
     * 
     * @param solicitudAsignacion
     *        Información del registro a crear
     * @return El registro actualizado
     */
    @POST
    @Path("/guardarResultadoAsignacion")
    public SolicitudAsignacionFOVISModeloDTO guardarResultadoAsignacion(@NotNull SolicitudAsignacionFOVISModeloDTO solicitudAsignacion,
            @Context UserDTO usuario);

    /**
     * Servicio que almacena definitivamente los resultados de asignación en la tabla
     * <code>ResultadosAsignacionFOVIS</code>
     * 
     * @param solicitudAsignacion
     *        Información del registro a crear
     * @return El registro actualizado
     */
    @POST
    @Path("/guardarActaAsignacion")
    public ActaAsignacionFOVISModeloDTO guardarActaAsignacion(@NotNull ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO);

    /**
     * Servicio que obtiene la lista de postulaciones habiles asociadas a la solicitud
     * 
     * @param idSolicitudGlobal
     *        solicitud global asociada
     * 
     * @return Lista de postulaciones que aplican para la asignacion
     */
    @GET
    @Path("/consultarPostulacionesAsignacion")
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesAsignacion(
            @QueryParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal);

    /**
     * Servicio que almacena definitivamente los resultados de asignación en la tabla
     * <code>ResultadosAsignacionFOVIS</code>
     * 
     * @param solicitudAsignacion
     *        Información del registro a crear
     * @return El objeto <code>SolicitudAsignacionFOVISModeloDTO</code> con los datos de la
     *         solicitud con la informacion actualizada de la solicitud
     */
    @POST
    @Path("/rechazarResultadoAsignacion")
    public SolicitudAsignacionFOVISModeloDTO rechazarResultadoAsignacion(@NotNull SolicitudAsignacionFOVISModeloDTO solicitudAsignacion);

    /**
     * Servicio que consulta la información de una solicitud de asignacion, por
     * identificador
     * 
     * @param idSolicitud
     *        Identificador de la solicitud
     * @return El objeto <code>SolicitudAsignacionFOVISModeloDTO</code> con los datos de la
     *         solicitud
     */
    @GET
    @Path("/consultarSolicitudAsignacion/{idSolicitud}")
    public SolicitudAsignacionFOVISModeloDTO consultarSolicitudAsignacion(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que obtiene la lista de hogares que aplican para cómputo de
     * calificación de postulación FOVIS, por ciclo de asignación
     * 
     * @param idCicloAsignacion
     *        Identificador del ciclo de asignación
     * @return Lista de hogares que aplican para calificación de postulaciones
     */
    @GET
    @Path("/consultarHogaresAplicanCalificacionPostulacion")
    public List<PostulacionFOVISModeloDTO> consultarHogaresAplicanCalificacionPostulacion(
            @QueryParam("idCicloAsignacion") Long idCicloAsignacion, @QueryParam("calificados") Boolean calificados);

    /**
     * Servicio que calcula el total de recursos de un hogar (suma el ahorro previo y los recursos complementarios) por identificador de
     * postulación
     * @param idPostulacion
     *        Identificador de postulación
     * @return El valor total de recursos del hogar
     */
    @GET
    @Path("/calcularTotalRecursosHogar")
    public BigDecimal calcularTotalRecursosHogar(@QueryParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio que calcula el total de ahorro previo de un hogar por identificador de
     * postulación
     * @param idPostulacion
     *        Identificador de postulación
     * @return El valor total de recursos del hogar
     */
    @GET
    @Path("/calcularAhorroPrevio")
    public BigDecimal calcularAhorroPrevio(@QueryParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio que obtiene la lista de hogares que aplican para cómputo de
     * calificación de postulación FOVIS, por ciclo de asignación
     * 
     * @param nombreCicloAsignacion
     *        Nombre del ciclo de asignación
     * @return Lista de hogares que aplican para calificación de postulaciones
     */
    @GET
    @Path("/consultarHogaresCalificacionPostulacion")
    List<PostulacionFOVISModeloDTO> consultarHogaresCalificacionPostulacion(
            @QueryParam("nombreCicloAsignacion") String nombreCicloAsignacion);

    /**
     * Servicio que consulta la lista de condiciones especiales para una persona, por identificación
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona
     * @param numeroIdentificacion
     *        Número de identificación de la persona
     * @return La lista de condiciones especiales
     */
    @GET
    @Path("/consultarCondicionEspecialPersona")
    List<CondicionEspecialPersonaModeloDTO> consultarCondicionEspecialPersona(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
    		@QueryParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio que registra los requisitos que no cumplieron en un determinado intento de postulación.
     * @param intentoPostulacionRequisitosDTO
     *        Lista con los requisitos por intento de postulación a registrar.
     */
    @POST
    @Path("/registrarIntentoPostulacionRequisito")
    public void registrarIntentoPostulacionRequisito(List<IntentoPostulacionRequisitoModeloDTO> intentoPostulacionRequisitosDTO);

    /**
     * Servicio que indica si está o no habilitada la Postulación Web en la parametrización.
     * @return indicador de si está o no habilitada la Postulación Web.
     */
    @GET
    @Path("/postulacionWebHabilitada")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean postulacionWebHabilitada();

    /**
     * Servicio encargado de consultar los integrantes de un hogar por postulacion
     * @param idPostulacion
     *        Identificador de la postulacion que se busca
     * @return Lista de integrantes
     */
    @GET
    @Path("/consultarIntegrantesHogarPostulacion")
    public List<IntegranteHogarModeloDTO> consultarIntegrantesHogarPostulacion(@NotNull @QueryParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio que obtiene el número de meses de ahorro programado para los cuales un hogar puede recibir aumento de puntaje FOVIS durante
     * el proceso de asignación. Paso 5 - HU-048
     * @param idPostulacionFOVIS
     *        Identificador único de la postulación
     * @return El número de meses calculado
     */
    @GET
    @Path("/calcularTotalMesesAhorroProgramado/{idPostulacionFOVIS}")
    public Integer calcularTotalMesesAhorroProgramado(@PathParam("idPostulacionFOVIS") Long idPostulacionFOVIS);

    /**
     * Servicio que registra la informacion de la solicitud de asignación
     * @param solicitudAsignacion
     *        DTO con la informacion de la solicitud de asignacion a crear
     */
    @POST
    @Path("/guardarSolicitudAsignacion")
    public SolicitudAsignacionFOVISModeloDTO guardarSolicitudAsignacion(@NotNull SolicitudAsignacionFOVISModeloDTO solicitudAsignacion);

    /**
     * Servicio que obtiene la cantidad de asignaciones en las que ha hecho parte la postulacion
     * @param identificadorPostulacion
     *        Identificador de la postulacion
     * @return Numero de asignaciones previas de la postulación
     */
    @GET
    @Path("/consultarAsignacionesPreviasHogar")
    public Integer consultarCantidadAsignacionesPreviasHogar(@QueryParam("identificadorPostulacion") Long identificadorPostulacion);

    /**
     * Servicio que consulta la parametrización de una modalidad FOVIS, por nombre
     * @param nombreParametrizacionModalidad
     *        Nombre de la modalidad
     * @return Objeto <code>ParametrizacionModalidadModeloDTO</code> con la información de la parametrización
     */
    @GET
    @Path("/consultarParametrizacionModalidad/{nombreParametrizacionModalidad}")
    public ParametrizacionModalidadModeloDTO consultarParametrizacionModalidad(
            @PathParam("nombreParametrizacionModalidad") ModalidadEnum nombreParametrizacionModalidad);

    /**
     * Servicio que obtiene la lista de postulaciones por solicitud de asignacion y resultado de asignacion de la postulacion
     * @param idSolicitudAsignacion
     *        Identificador de la solicitud de asignación
     * 
     * @param resultadoAsignacion
     *        Resultado de la asignación
     * @return Lista de postulaciones que cumplen con los parámetros de consulta
     */
    @GET
    @Path("/consultarPostulacionesSolicitudPorResultado")
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesSolicitudPorResultado(
            @QueryParam("idSolicitudAsignacion") Long idSolicitudAsignacion,
            @QueryParam("resultadoAsignacion") ResultadoAsignacionEnum resultadoAsignacion);

    /**
     * Servicio que consulta un ahorro previo/programado, por identificador de la postulación y el tipo de ahorro
     * @param idPostulacion
     *        Identificador único de la postulación
     * @param tipoAhorro
     *        Tipo de ahorro
     * @return El registro con la información del ahorro
     */
    @GET
    @Path("/consultarAhorroPrevio")
    public AhorroPrevioModeloDTO consultarAhorroPrevio(@QueryParam("idPostulacion") Long idPostulacion,
            @QueryParam("tipoAhorro") TipoAhorroPrevioEnum tipoAhorro);

    /**
     * Servicio que obtiene las cartas de asignacion fovis generadas por año o ciclo de asignacion
     * el proceso de asignación.
     * @param anoAsignacion
     *        año ingresado para consultar las cartas
     * 
     * @param cicloAsignacion
     *        Identificador del ciclo de asignacion
     * @return Lista con las cartas generadas para los filtros de busqueda ingresados
     */
    @GET
    @Path("/consultarCartasAsignacionPorCicloAnoAsignacion")
    public List<CartasAsignacionDTO> consultarCartasAsignacionPorCicloAnoAsignacion(@QueryParam("anoAsignacion") Integer anoAsignacion,
            @QueryParam("cicloAsignacion") Long cicloAsignacion);

    /**
     * Servicio que consulta la información de una solicitud de postulación global, por identificador de la postulación
     * @param idPostulacion
     *        Identificador único de la postulación
     * @return La información de la solicitud global asociada
     */
    @GET
    @Path("/consultarSolicitudGlobalPostulacion/{idPostulacion}")
    public SolicitudModeloDTO consultarSolicitudGlobalPostulacion(@PathParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio que obtiene una lista de años de los ciclos de asignacion que estan con estado cerrado
     * el proceso de asignación.
     * 
     * @return Lista de años asociados al ciclo de asignacion
     */
    @GET
    @Path("/consultarAnosCicloAsignacionEstadoCerrado")
    public List<Integer> consultarAnosCicloAsignacionEstadoCerrado();

    /**
     * Servicio que obtiene los ciclos de asignacion que se encuentren en estado cerrado por año
     * 
     * @param anoAsignacion
     *        año ingresado como filtro de busqueda para la consulta
     * @return Lista con los ciclos de asignacion en estado cerrado para el año ingresado
     */
    @GET
    @Path("/consultarCiclosAsignacionEstadoCerradoPorAnio")
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacionEstadoCerradoPorAnio(@QueryParam("anoAsignacion") Integer anoAsignacion);

    /**
     * Consulta la solicitud de postulacion por el identificador de solicitud
     * @param idSolicitudPostulacion
     *        Identificador solicitud postulacion
     * @return Solicitud postulacion DTO
     */
    @GET
    @Path("/consultarSolicitudPostulacionById")
    public SolicitudPostulacionModeloDTO consultarSolicitudPostulacionById(
            @QueryParam("idSolicitudPostulacion") Long idSolicitudPostulacion);

    /**
     * Servicio que consulta si una persona, ya sea jefe o integrante del hogar, ha sido beneficiario de una postulacion asignada en una
     * determinada modalidad
     * @param tipoIdentificacionPersona
     *        Tipo de identificación de la persona.
     * @param numeroIdentificacionPersona
     *        Número de identficación de la persona.
     * @param modalidad
     *        Modalidad a filtrar.
     * @return indicador de si es beneficiaria o no.
     */
    @GET
    @Path("/personaBeneficiariaPostulacionModalidad")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean personaBeneficiariaPostulacionModalidad(
            @QueryParam("tipoIdentificacionPersona") TipoIdentificacionEnum tipoIdentificacionPersona,
            @QueryParam("numeroIdentificacionPersona") String numeroIdentificacionPersona,
            @QueryParam("modalidad") ModalidadEnum modalidad);

    /**
     * Servicio que consulta los datos para la pantalla inicial del generación comprobante de asignación
     * @param idSolicitudGlobal
     *        identificador de la solicitud global
     * @return DTO con los datos requeridos
     */
    @GET
    @Path("/consultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal")
    public InformacionDocumentoActaAsignacionDTO consultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal(
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que consulta la solicitud de asignacion por el identificador de la solicitud global
     * 
     * @param idSolicitudGlobal
     *        identificador de la solicitud global
     * @return DTO con la informacion de la solicitud de asignacion
     */
    @GET
    @Path("/consultarSolicitudAsignacionPorSolicitudGlobal")
    public SolicitudAsignacionFOVISModeloDTO consultarSolicitudAsignacionPorSolicitudGlobal(
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que inactiva los integrantes del hogar de un determinado Jefe y Postulacion y que no existen en la lista enviada como
     * párametro <code>integrantesPermanecientes</code>
     * @param idJefeHogar
     *        Identificador Jefe Hogar
     * @param idPostulacion
     *        Identificador Postulacion
     * @param integrantesPermanecientes
     *        Lista de identificadores de integrantes que no se inactivan
     */
    @POST
    @Path("/inactivarIntegrantesHogarNoRelacionados")
    public void inactivarIntegrantesHogarNoRelacionados(@QueryParam("idJefeHogar") @NotNull Long idJefeHogar,
            @QueryParam("idPostulacion") @NotNull Long idPostulacion, List<Long> integrantesPermanecientes);

    /**
     * Consulta las postulaciones asociadas a cruces internos.
     * @param estadosHogar
     * @param idCicloAsignacion
     * @return
     */
    @POST
    @Path("/consultarPostulacionesCrucesInternos")
    public List<SolicitudPostulacionModeloDTO> consultarPostulacionesCrucesInternos(List<EstadoHogarEnum> estadosHogar,
            @QueryParam("idCicloAsignacion") Long idCicloAsignacion);

    /**
     * Consulta el documento de la solicitud por id de solicitud global
     * @param idSolcitudGlobal
     * @return
     */
    @GET
    @Path("/consultarDocumentoSolicitud")
    public DocumentoAdministracionEstadoSolicitudDTO consultarDocumentoSolicitud(
            @QueryParam("idSolicitudGlobal") @NotNull Long idSolcitudGlobal);

    /**
     * Servicio encargado de actualizar o crear el ciclo de asignacion
     * @param cicloAsignacionModelDTO
     *        DTO con los valores del ciclo de asignacion
     * @return ciclo de asignacion creado o actualizado
     */
    @POST
    @Path("/guardarActualizarCicloAsignacion")
    public CicloAsignacionModeloDTO guardarActualizarCicloAsignacion(@NotNull CicloAsignacionModeloDTO cicloAsignacionModelDTO);

    /**
     * Servicio que consulta el acta de asignacion por el identificador de la solicitud de asignacion
     * @param idSolicitudAsignacion
     *        identificador del acta de asignacion
     * @return DTO con la informacion del acta de asignacion
     */
    @GET
    @Path("/consultarActaAsignacionPorIdSolicitudAsignacion")

    public ActaAsignacionFOVISModeloDTO consultarActaAsignacionPorIdSolicitudAsignacion(
            @QueryParam("idSolicitudAsignacion") Long idSolicitudAsignacion);

    /**
     * Consulta medio de Pago asociado a un Proyecto de vivienda.
     * @param idProyectoVivienda
     * @return
     */
    @GET
    @Path("/consultarMedioPagoProyectoVivienda")
    public MedioDePagoModeloDTO consultarMedioPagoProyectoVivienda(@NotNull @QueryParam("idProyectoVivienda") Long idProyectoVivienda);

    /**
     * Servicio que crea o actualiza las postulaciones procesadas por las novedades automaticas
     * @param solicitudNovedadPersonaFovis
     *        Informacion solicitud novedad persona fovis
     * @return Lista de postulaciones fovis registrada y/oactualizada
     */
    @POST
    @Path("/crearActualizarListaPostulacionFOVIS")
    public void crearActualizarListaPostulacionFOVIS(List<PostulacionFOVISModeloDTO> listaPostulaciones);

    /**
     * Servicio para la actualizacion del estado hogar
     * @param idPostulacion
     *        Identificador postulación del hogar
     * @param estadoHogar
     *        Estado del hogar a actualizar
     */
    @POST
    @Path("/{idPostulacion}/estadoHogar")
    public void actualizarEstadoHogar(@PathParam("idPostulacion") Long idPostulacion,
            @QueryParam("estadoHogar") EstadoHogarEnum estadoHogar);

    /**
     * Servicio encargado de consultar un integrante de hogar por tipo y numero de documento y el identificador de postulación
     * @param tipoIdentificacion
     *        Tipo identificación Integrante hogar
     * @param numeroIdentificacion
     *        Numero identificación Integrante Hogar
     * @param idPostulacion
     *        Identificador de la postulación en la que se espera se encuentre asociado
     * @return Integrante hogar encontrado
     */
    @GET
    @Path("/consultarIntegranteHogarPorIdentificacion")
    public IntegranteHogarModeloDTO consultarIntegranteHogarPorIdentificacion(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("idPostulacion") Long idPostulacion);

    /**
     * Servicio que obtiene la lista de hogares que aplican para cómputo de
     * cálculo de calificación postulación FOVIS, por ciclo de asignación y que
     * se les registró una novedad de Habilitación de postulación rechazada y
     * en el campo Motivo de habilitación de la postulación tiene el valor Reclamación procedente.
     * 
     * @param idCicloAsignacion
     *        Identificador del ciclo de asignación
     * @return Lista de hogares que aplican para calificación de postulaciones
     */
    @GET
    @Path("/consultarHogaresAplicanCalificacionConReclamacionProcedente")
    public List<PostulacionFOVISModeloDTO> consultarHogaresAplicanCalificacionConReclamacionProcedente(
            @QueryParam("idCicloAsignacion") Long idCicloAsignacion);
    
    /**
     * Servicio que consulta que una persona tuvo una postulacion
     * y perdio la vivienda por imposibilidad de pago
     * 
     @param tipoIdentificacion
     *        Tipo identificación jefe hogar
     * @param numeroIdentificacion
     *        Numero identificación jefe Hogar
     * @return Lista de postulaciones que ha tenido la persona
     */
    @GET
    @Path("/consultarPersonaPerdioViviendaImposibilidadPago")
    public List<Integer> ConsultarPersonaPerdioViviendaImposibilidadPago(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
    		@QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio que obtiene los estados de cruces asociados al hogar
     * 
     * @param numeroRadicacion
     *        Numero de radicacion de la postulacion
     * @return Lista de cruces generador para la postulacion
     */
    @GET
    @Path("/consultarEstadoCrucesHogar")
    public List<HistoricoCrucesFOVISDTO> consultarEstadoCrucesHogar(@QueryParam("numeroRadicacion") String numeroRadicacion);

	/**
	 * Servicio que consulta el historico de las solicitudes de postulacion
	 * 
	 * @param numeroSolicitud
	 *            Numero de solicitud
	 * @param tipoSolicitud
	 *            Tipo de solicitud a consultar
	 * @param estadoSolicitudPostulacion
	 *            Estado de solicitud de postulacion
	 * @param fechaExactaRadicacion
	 *            Fecha de radicacion de la solicitud
	 * @param fechaInicio
	 *            Fecha inicio
	 * @param fechaFin
	 *            Fecha Fin
	 * @return ConsultarHistoricoSolicitudesFOVISDTO DTO con las solicitudes
	 *         consultadas con los paramentros enviados
	 */
	@GET
	@Path("/consultarHistoricoSolicitudesPostulacion")
	public List<SolicitudPostulacionFOVISDTO> consultarHistoricoSolicitudesPostulacion(
			@QueryParam("numeroSolicitud") String numeroSolicitud,
			@QueryParam("tipoSolicitud") TipoSolicitudEnum tipoSolicitud,
			@QueryParam("estadoSolicitudPostulacion") EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion,
			@QueryParam("fechaExactaRadicacion") Long fechaExactaRadicacion,
			@QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin);
	
	/**
	 * Servicio que obtiene el historico de asignacion por tipo y numero de
	 * identificacion del jefe de hogar
	 * 
	 * @param tipoIdentificacion
	 *            tipo de identificacion del jefe de hogar
	 * 
	 * @param numeroIdentificacion
	 *            numero de identificacion del jefe de hogar
	 * @return Lista con el historico de asigncion FOVIS
	 */
	@GET
	@Path("/consultarHistoricoAsignacion")
	public List<HistoricoAsignacionFOVISDTO> consultarHistoricoAsignacion(@QueryParam("numeroRadicacion") String numeroRadicacion);


    /**
     * Servicio encargado de consultar una solicitud de verificación Fovis por el id de la solicitud global.
     * @param idSolicitudGlobal
     *        Identificador global de la solicitud de postulación.
     * @return Solicitud de verificación Fovis encontrada.
     */
    @GET
    @Path("/consultarSolicitudVerificacionFovis")
    @Produces(MediaType.APPLICATION_JSON)
    public SolicitudVerificacionFovisModeloDTO consultarSolicitudVerificacionFovis(
            @QueryParam("idSolicitud") @NotNull Long idSolicitudGlobal);

    /**
     * Servicio que actualiza el estado de la solicitud de verificación Fovis.
     * @param idSolicitudGlobal
     *        id de la solicitud.
     * @param estadoSolicitud
     *        Nuevo estado para la solicitud de postulación.
     */
    @POST
    @Path("/{idSolicitudGlobal}/estadoSolicitudVerificacion")
    public void actualizarEstadoSolicitudVerificacionFovis(@PathParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal,
            @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudVerificacionFovisEnum estadoSolicitud);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>SolicitudVerificacionFovis</code>
     * 
     * @param solicitudVerificacionFovisDTO
     *        La información del registro a crear/actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarSolicitudVerificacionFovis")
    public SolicitudVerificacionFovisModeloDTO crearActualizarSolicitudVerificacionFovis(
            @NotNull SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO);
    
	/**
	 * Servicio que obtiene la lista de hogares que estan en gestion de cruce
	 * interno en proceso
	 * 
	 * @param nombreCicloAsignacion
	 *            Nombre del ciclo de asignación
	 * @return Lista de hogares con gestion de cruce interno en proceso
	 */
	@GET
	@Path("/validarHogaresConGestionCruceInternoEnProceso")
    public Boolean validarHogaresConGestionCruceInternoEnProceso(
			@QueryParam("nombreCicloAsignacion") String nombreCicloAsignacion);

    /**
     * Consulta el avance de la calificación del ciclo asignacion enviado
     * @param idCicloAsignacion
     *        Identificador ciclo asignacion
     * @return Informacion del total de postulaciones a calificar y las postulaciones ya calificadas
     */
    @GET
    @Path("/consultarAvanceCalificacion")
    public Map<String, Object> consultarAvanceCalificacion(@QueryParam("idCicloAsignacion") Long idCicloAsignacion);

    /**
     * Servicio que cel historico de solicitudes FOVIS
     * 
     * @param numeroSolicitud
     *        Numero de solicitud
     * @param tipoSolicitud
     *        Tipo de solicitud a consultar
     * @param estadoSolicitudNovedad
     *        Estado de la solicitud de novedad
     * @param estadoSolicitudlegalizacion
     *        Estado de solicitud de legalizacion
     * @param estadoSolicitudPostulacion
     *        Estado de solicitud de postulacion
     * @param fechaExactaRadicacion
     *        Fecha de radicacion de la solicitud
     * @param fechaInicio
     *        Fecha inicio
     * @param fechaFin
     *        Fecha Fin
     * @param idPostulacion
     *        Identificacion de la postulación
     * @return ConsultarHistoricoSolicitudesFOVISDTO DTO con las solicitudes
     *         consultadas con los paramentros enviados
     */
    @GET
    @Path("/consultarHistoricoSolicitudesFOVIS")
    public List<ResultadoHistoricoSolicitudesFovisDTO> consultarHistoricoSolicitudesFOVIS(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @QueryParam("numeroSolicitud") String numeroSolicitud,
            @QueryParam("tipoSolicitud") TipoSolicitudEnum tipoSolicitud,
            @QueryParam("estadoSolicitudNovedad") EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad,
            @QueryParam("estadoSolicitudlegalizacion") EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudlegalizacion,
            @QueryParam("estadoSolicitudPostulacion") EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion,
            @QueryParam("fechaExactaRadicacion") Long fechaExactaRadicacion, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("idPostulacion") Long idPostulacion);

    /**
     * Consulta la informacion del hogar postulado por numero de radicado
     * @param numeroRadicado
     *        Numero de radicacion de solicitud de postulacion
     * @param aplicaVista360
     *        Indica si la información es para la vista 360 (se verifica el json de la postulación)
     * @return Informacion solicitud postulacion
     */
    @GET
    @Path("/consultarInformacionHogar")
    public SolicitudPostulacionFOVISDTO consultarInformacionHogar(@NotNull @QueryParam("numeroRadicado") String numeroRadicado,
            @QueryParam("aplicaVista360") Boolean aplicaVista360);

    /**
     * Servicio que actualiza el estado de la solicitud de asignación.
     * @param idSolicitudGlobal
     *        id de la solicitud.
     * @param estadoSolicitud
     *        Nuevo estado para la solicitud de postulación.
     */
    @POST
    @Path("/{idSolicitudGlobal}/estadoSolicitudAsignacion")
    public void actualizarEstadoSolicitudAsignacion(@PathParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal,
            @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudAsignacionEnum estadoSolicitud);

    /**
     * Servicio en cargado de obtener la información de la solicitud de subsidio fovis por los parametros de busqueda
     * @param numeroRadicado
     *        Número de radicado de la solicitud de postulación
     * @param tipoIdentificacion
     *        Tipo de identificación del Jefe Hogar
     * @param numeroIdentificacion
     *        Número de identificación del Jefe Hogar
     * @return Informacion de subsidio fovis
     */
    @GET
    @Path("/consultarInformacionSubsidioFovis")
    public InformacionSubsidioFOVISDTO consultarInformacionSubsidioFovis(@QueryParam("numeroRad") String numeroRadicado,
            @QueryParam("tipoID") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroID") String numeroIdentificacion);

    /**
     * Servicio encargado de actualizar las postulaciones que se encuentran asociadas a ciclos cerrados y que ahora tienen ciclo sucesor
     */
    @POST
    @Path("/actualizarPostulacionesCalificadasSinCambioCiclo")
    public void actualizarPostulacionesCalificadasSinCambioCiclo();
    
    /**
     * Servicio encargado de actualizar las postulaciones que se encuentran asociadas a ciclos cerrados y que ahora tienen ciclo sucesor
     */
    @POST
    @Path("/actualizarPostulacionesNovedadesAsociadasCicloPredecesor")
    public void actualizarPostulacionesNovedadesAsociadasCicloPredecesor();

    /**
     * Consulta la informacion de una postulación
     * @param idPostulacion
     *        Identificador de la postulación
     * @return Informacion de la solicitud de postulacion
     */
    @GET
    @Path("/consultarInfoPostulacion")
    public SolicitudPostulacionFOVISDTO consultarInfoPostulacion(@NotNull @QueryParam("idPostulacion") Long idPostulacion);

    /**
     * Crea o actualiza la calificacion de la postulación
     * @param calificacionPostulacionDTO
     *        Informacion de la calificacion a crear o actualizar
     * @return Calificacion creada/actualizada
     */
    @POST
    @Path("/crearActualizarCalificacion")
    public CalificacionPostulacionDTO crearActualizarCalificacion(CalificacionPostulacionDTO calificacionPostulacionDTO);

    /**
     * Crea o actualiza la relacion de postulación y la asignación
     * @param postulacionAsignacionDTO
     *        Información de la postulación y el proceso de asignación
     * @return Registro creado y/o actualizado
     */
    @POST
    @Path("/crearActualizarPostulacionAsignacion")
    public PostulacionAsignacionDTO crearActualizarPostulacionAsignacion(PostulacionAsignacionDTO postulacionAsignacionDTO);

    /**
     * Crea o actualiza la lista de objetos enviados
     * @param listPostulaciones
     *        Lista de información de postulaciones
     */
    @POST
    @Path("/crearActualizarListaPostulacionAsignacion")
    public void crearActualizarListaPostulacionAsignacion(@NotNull List<PostulacionAsignacionDTO> listPostulaciones);

    /**
     * Consulta las postulaciones que estan asociadas en la asignacion por medio del id de solicitud
     * @param idSolicitudAsignacion
     *        Identificador de asignación
     * @return Lista de postulaciones en la asignacion
     */
    @GET
    @Path("/consultarListaPostulacionAsignacion")
    public List<PostulacionAsignacionDTO> consultarListaPostulacionAsignacion(
            @NotNull @QueryParam("idSolicitudAsignacion") Long idSolicitudAsignacion);
    /**
     * Consulta los valores de los parametros SMMLV y TOPE_VALOR_INGRESOS_HOGAR
     * Adionalmente, multiplica estos valores a fin de obtener el tope en terminos monetarios
     * @return Map con los valores descritos
     */
    @GET
    @Path("/consultarTopeParametrizadoFovis")
    public Map<String, BigDecimal> consultarTopeParametrizadoFovis();

    /**
     * Consulta la información de los miembros de hogar (incluye el jefe de hogar) de la postulación partiendo de la información al momento
     * de la asignación y la información actual
     * @param idPostulacion
     *        Identificador de la postulación
     * @return Lista de miembros de hogar asociados a la postulación al momento de asignación con la información actual, o lista vacía
     * @throws IOException 
     */
    @GET
    @Path("/consultarMiembrosHogarPostulacion")
    public List<MiembroHogarDTO> consultarMiembrosHogarPostulacion(@NotNull @QueryParam("idPostulacion") Long idPostulacion) throws IOException;

    /**
     * Realiza la actualización de información de ingresos de los miembros de hogar (incluye el jefe de hogar)
     * @param listaMiembrosHogar
     *        Lista de miembros de hogar a actualizar
     */
    @POST
    @Path("/actualizarIngresosMiembrosHogar")
    public void actualizarIngresosMiembrosHogar(@NotNull List<MiembroHogarDTO> listaMiembrosHogar);

    /**
     * Realiza la actualización del json con la información de postulación actualizada
     * @param idPostulacion
     *        Identificador postulación
     * @param solicitudPostulacion
     *        DTO con la información actualizada de la postulación
     * @throws IOException
     *         Lanzada si ocurre un error en la conversión de información
     */
    @POST
    @Path("/actualizarJsonPostulacion")
    public void actualizarJsonPostulacion(@NotNull @QueryParam("idPostulacion") Long idPostulacion,
            @NotNull SolicitudPostulacionFOVISDTO solicitudPostulacion) throws IOException;
    
    /**
     * Servicio que consulta el SMMLV del año
     * 
     * @param anio
     *        año por el cual se va a consultar
     * @return Salario minimo mensual legal vigente
     */
    @GET
    @Path("/consultarSMMLVPorAnio")
    String consultarSMMLVPorAnio(@QueryParam("anio") String anio);
    
    /**
     * Servicio que consulta los recursos prioridad
     * 
     * @param estado
     *        estado del recurso
     * @return Recursos prioridad habilitados
     */
    @GET
    @Path("/consultarRecursosPrioridad")
    List<String> consultarRecursosPrioridad(@QueryParam("estado") Boolean estado);
    
    /**
     * Servicio que consulta el recurso prioridad con el cual fue asignado el hogar
     * 
     * @param idPostulacion
     *        Identificador postulación
     * @param idSolicitudAignacion
     *        Identificador de la asignacion  
     * @return Recurso prioridad de la asignacion
     */
    @GET
    @Path("/consultarRecursosPrioridadPostulacionAsignacion")
    String consultarRecursosPrioridadPostulacionAsignacion(@QueryParam("idPostulacion") Long idPostulacion, @QueryParam("idSolicitudAsignacion") Long idSolicitudAsignacion);

    /**
     * Servicio que consulta las tareas heredadas
     *
     * @param numeroRadicacion Numero de radicado de la solicitud
     * @param usuario          Usuario al cual le quedo asignada la solicitud
     * @param tipoTransaccion
     * @return Lista de tareas
     */
    @GET
    @Path("/consultarTareasHeredadas")
    List<TareasHeredadasDTO> consultarTareasHeredadas(@QueryParam("numeroRadicacion") String numeroRadicacion, @QueryParam("usuario") String usuario, @QueryParam("tipoTransaccion") String tipoTransaccion);



    /**
     * Servicio trae informacion necesaria para redireccionar las tareas
     *
     * @param numeroRadicacion
     *        Numero de radicado de la solicitud
     * @return Informacion de la tarea
     */
    @GET
    @Path("/redireccionarTarea")
    RedireccionarTareaDTO redireccionarTarea(@QueryParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que actualizar la solicitud
     *
     * @param instanciaProceso Instancia proceso de la solicitud     *
     * @return
     */
    @GET
    @Path("/consultarSolicitudFovis")
    Solicitud consultarSolicitudFovis(@QueryParam("instanciaProceso") Long instanciaProceso);
    /**
     * Método encargado de consultar las postulaciones de un determinado ciclo de asignación
     * @param idCicloAsignacion
     *        Identificador del ciclo de asignación.
     * @return El listado de postulaciones que aplican.
     */
    @GET
    @Path("/obtenerPostulacionesPorCiclo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SolicitudPostulacionModeloDTO> obtenerPostulacionesPorCiclo(
            @NotNull @QueryParam("idCicloAsignacion") Long idCicloAsignacion);

    /**
     * Servicio para consultar los ciclos de asignación excluyendo el estado enviado
     *
     * @param estadoCicloAsignacion Estado del ciclo de asignación para consultar
     * @param fecha
     * @return Los ciclos de asignación para el estado consultado.
     */
    @GET
    @Path("/consultarCiclosAsignacion")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacion(
            @NotNull @QueryParam("estadoCicloAsignacion") EstadoCicloAsignacionEnum estadoCicloAsignacion,
            @NotNull @QueryParam("fecha") String fecha);

    @GET
    @Path("/consultarCalificacionPostulacion")
    void consultarCalificacionPostulacion(@QueryParam("idPostulacion") Long idPostulacion, @QueryParam("idCicloAsignacion") Long idCiclo);

    @GET
    @Path("/consultarNovedadesPostulacionRangoFecha")
    List<PostulacionFOVISModeloDTO> consultarNovedadesPostulacionRangoFecha(@QueryParam("idCicloAsignacion") Long idCicloAsignacion,
                                                                            @QueryParam("fechaInico") String fechaInico,
                                                                            @QueryParam("fechaFin")  String fechaFin);
    @GET
    @Path("/consultarFechaResultadoEjecucionProgramada")
    Object  consultarFechaResultadoEjecucionProgramada(@QueryParam("id")  Long id);


     @GET
     @Path("/consultarIngresosHogarFovis")
     Object  consultarIngresosHogarFovis(@QueryParam("idPostulacion") Long idPostulacion,@QueryParam("tipoIdentificacion") String tipoIdentificacion,@QueryParam("numeroIdentificacion")  String numeroIdentificacion);

    @POST
    @Path("/crearActualizarPostulacionFovisDev")
    void crearActualizarPostulacionFovisDev(PostulacionFovisDevDTO postulacionFovisDev);

    @POST
    @Path("/crearActualizarPostulacionProveedor")
    void crearActualizarPostulacionProveedor(PostulacionProvOfeDTO postulacionProvOfeDTO);

    @GET
    @Path("/consultaModalidadEnCicloVigente")
    public List<CicloAsignacionModeloDTO>  consultaModalidadEnCicloVigente(@QueryParam("idCicloAsignacion") Long idCicloAsignacion,@QueryParam("modalidad") ModalidadEnum modalidad);
}
