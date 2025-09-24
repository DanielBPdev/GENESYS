package com.asopagos.solicitudes.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import javax.ws.rs.core.UriInfo;

import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.FiltroSolicitudDTO;
import com.asopagos.dto.ResultadoConsultaSolicitudDTO;
import com.asopagos.dto.Solicitud360DTO;
import com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.enumeraciones.aportes.ActividadEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para adminsitración de
 * solicitudes <b>Historia de Usuario:</b> Transversal - Solicitudes
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("solicitudes")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SolicitudesService {

    /**
     * <b>Descripcion</b> Metodo que se encarga de registrar un escalmiento de
     * una solicitud de afiliación
     *
     * @param idSolicitud
     *        Identificardor de la solictud a escalar
     * @param escalamientoSolicitud
     *        Representa los datos
     */
    @POST
    @Path("/{idSolicitud}/escalamientoSolicitud")
    public void escalarSolicitud(@PathParam("idSolicitud") Long idSolicitud, EscalamientoSolicitudDTO escalamientoSolicitud);

    /**
     * <b>Descripcion</b> Metodo que se encarga de registrar un escalmiento de
     * una solicitud para Postulaciones FOVIS
     *
     * @param idSolicitud
     *        Identificardor de la solictud a escalar
     * @param escalamientoSolicitud
     *        Representa los datos
     * @return Datos del escalamiento.
     */
    @POST
    @Path("/{idSolicitud}/registrarEscalamientoSolicitud")
    public EscalamientoSolicitudDTO registrarEscalamientoSolicitud(@PathParam("idSolicitud") Long idSolicitud, EscalamientoSolicitudDTO escalamientoSolicitud);
    
    /**
     * Actualiza el escalamiento de la solictud, segun la solicitud de
     * afiliación empleador
     *
     * @param idSolicitud
     *        Identificador que pertence la solictud a actualizar
     * @param escalamientoSolAfilEmpleador
     *        Solicitud que se desea actualizar asociada al afiliación
     *        empleador
     */
    @PUT
    @Path("/{idSolicitud}/escalamientoSolicitud")
    public void actualizarSolicitudEscalada(@PathParam("idSolicitud") Long idSolicitud,
            EscalamientoSolicitudDTO escalamientoSolAfilEmpleador);

    /**
     * Consulta la Solicitud de escala de una afiliación empleador
     *
     * @param idSolicitud
     *        Id de la solicitud
     * @return Retorna una Escalada de solitud afiliación empleador
     */
    @GET
    @Path("/{idSolicitud}/escalamientoSolicitud")
    public EscalamientoSolicitudDTO consultarSolicitudEscalada(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Método que guarda los datos temporales de una solicitud de afiliación
     * 
     * @param idSolicitud
     *        Es la solicitud a la que se asocian los temporales
     * @param jsonPayload
     *        Son los datos a guardar
     * @return El identificador de los datos temporales
     */
    @POST
    @Path("/datosTemporales")
    public Long guardarDatosTemporales(@QueryParam("idSolicitudGlobal") Long idSolicitud, String jsonPayload);

    /**
     * Método que consulta los datos temporales de una solicitud de afiliación
     * 
     * @param idSolicitud
     *        Es la solicitud a la que se asocian los temporales
     * @return jsonPayload
     */
    @GET
    @Path("/datosTemporales")
    public String consultarDatosTemporales(@QueryParam("idSolicitudGlobal") Long idSolicitud);

    /**
     * Método que consulta los datos temporales de una solicitud de afiliación
     * 
     * @param idSolicitud
     *        Es la solicitud a la que se asocian los temporales
     * @return jsonPayload
     */
    @POST
    @Path("/guardarDocAdminsSolicitudes/{numeroRadicado}")
    public void guardarDocumentosAdminSolicitudes(@PathParam("numeroRadicado") String numeroRadicado,
            List<DocumentoAdministracionEstadoSolicitudDTO> administracionEstadoSolicituds);

    /**
     * Metodo encargado de actualizar el destinatario de la solicitud
     * 
     * @param idSolicitud,
     *        identificador de la solcitud
     * @param usuario,
     *        usuario al cual se le asigna la solicitud
     */
    @POST
    @Path("/{idInstanciaProceso}/destinatario")
    public void actualizarDestinatarioSolicitud(@PathParam("idInstanciaProceso") String idInstanciaProceso,
            @QueryParam("usuario") String usuario);

    /**
     * Servicio que se encarga de crear o actualizar un registro de
     * trazabilidad.
     * 
     * @param trazabilidadDTO:
     *        dto con la información de la trazabilidad.
     */
    @POST
    @Path("/crearTrazabilidad")
    public void crearTrazabilidad(@NotNull RegistroEstadoAporteModeloDTO trazabilidadDTO);

    /**
     * Servicio que se encarga de consultar la trazabilidad de una solicitud.
     * 
     * @param idSolicitud
     *        de la solicitud global.
     * @return lista de las trazabilidades.
     */
    @GET
    @Path("/{idSolicitud}/consultarTrazabilidad")
    public List<RegistroEstadoAporteModeloDTO> consultarTrazabilidad(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Método que consulta los datos temporales de una solicitud de afiliación
     * por tipo y numero de identificacion
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return idSolicitudGlobal
     */
    @GET
    @Path("/consultarDatosTemporales")
    public List<SolicitudAfiliacionEmpleador> consultarDatosTempPorPersona(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Método que consulta los documentos de una solicitud para una actividad
     * especifica.
     * 
     * @param idSolicitud
     *        id de la solicitud.
     * @param actividad
     *        activida a buscar los documentos.
     * @return lista de documentos.
     */
    @GET
    @Path("/{idSolicitud}/actividad")
    public List<DocumentoAdministracionEstadoSolicitudDTO> consultarDocumentos(@PathParam("idSolicitud") Long idSolicitud,
            @QueryParam("actividad") ActividadEnum actividad);

    /**
     * Servicio encargado de actualizar una trazabilidad
     * 
     * @param registroEstadoAporteModeloDTO,
     *        trazabilidad a actualizar
     */
    @POST
    @Path("/actualizarTrazabilidad")
    public void actualizarTrazabilidad(RegistroEstadoAporteModeloDTO registroEstadoAporteModeloDTO);

    /**
     * Servicio encargado de consultar la trazabilidad a la que se le debe asingar el comunicado.
     * @param idSolicitud
     *        id de la solicitud que tiene los registros de trazabilidad.
     * @param actividad
     *        actividad que se realizó con el comunicado.
     * @param idComunicado
     *        id del comunicado para verificar que no este asignado.
     * @return registro estado del aporte.
     */
    @GET
    @Path("/{idSolicitud}/consultarTrazabilidadPorActividad")
    public RegistroEstadoAporteModeloDTO consultarTrazabilidadPorActividad(@PathParam("idSolicitud") Long idSolicitud,
            @NotNull @QueryParam("actividad") ActividadEnum actividad);

    /**
     * Consulta Consulta los escalamientos de una determinada solicitud
     *
     * @param idSolicitud
     *        Id de la solicitud global
     * @return Retorna los escalamientos de la solicitud
     */
    @GET
    @Path("/{idSolicitud}/escalamientosSolicitud")
    public List<EscalamientoSolicitudDTO> consultarEscalamientosSolicitud(@NotNull @PathParam("idSolicitud") Long idSolicitud);
    
    
    /**
     * Servicio que consulta la información de una solicitud global, por
     * identificador
     * 
     * @param idSolicitud
     *            Identificador de la solicitud
     * @return El objeto <code>SolicitudModeloDTO</code> con los datos de la
     *         solicitud
     */
    @GET
    @Path("/consultarSolicitudGlobal/{idSolicitud}")
    public SolicitudModeloDTO consultarSolicitudGlobal(@PathParam("idSolicitud") Long idSolicitud);
    
    /**
     * Método que guarda los datos temporales de una solicitud
     * @param idSolicitud
     *        Es la solicitud a la que se asocian los temporales
     * @param jsonPayload
     *        Son los datos a guardar
     * @param numeroIdentificacion
     *        Numero identificacion persona asociada
     * @param tipoIdentificacion
     *        Tipo identificacion persona asociada
     * @return El identificador de los datos temporales
     */
    @POST
    @Path("/datosTemporalesPersona")
    public Long guardarDatosTemporalesPersona(@QueryParam("idSolicitudGlobal") Long idSolicitud, String jsonPayload,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion);

	/**
	 * Servicio que obtiene la lista de solicitudes asociadas a un aportante, dependiendo de los filtros enviados como par�metros
	 * @param filtros Filtros de consulta de solicitudes
	 * @return La lista de solicitudes correspondientes
	 */
    @POST
    @Path("/consultarListaSolicitudes")
    List<Solicitud360DTO> consultarListaSolicitudes(@NotNull Solicitud360DTO filtros);

    /**
     * Método que resigna una tarea
     * @param idProceso
     * @param usuarioActual
     * @param usuarioNuevo
     * @param userDTO
     */
    @POST
    @Path("{idProceso}/reasignar")
    public void reasignarSolicitud(@PathParam("idProceso") Long idProceso, 
            @QueryParam("usuarioActual") String usuarioActual, @QueryParam("usuarioNuevo") String usuarioNuevo, @Context UserDTO userDTO);
    
    /**
     * Se encarga de consultar las solicitudes transversales en el sistema, basado en un conjunto de filtros
     * definidos en un DTO ( PT-INGE-035-TRA-337)
     * @param filtroSolicitud
     * @return
     */
    @POST
    @Path("/consultarSolicitudesFiltroSolicitud")
    List<ResultadoConsultaSolicitudDTO> consultarSolicitudesFiltroSolicitud(
    		@NotNull FiltroSolicitudDTO filtroSolicitud, @Context UriInfo uri, @Context HttpServletResponse response);
    /**
     * Persiste la informacion referente a la ejecución del utilitario
     * que corrige solciitudes en estado CREATED sin propietario en BPM
     * @param filtroSolicitud
     * @return
     */
    @POST
    @Path("/persistirResultadoUtiliarioBPM")
    public void persistirResultadoUtiliarioBPM(@QueryParam("numeroradicado") String numeroradicado,
            @QueryParam("resultado") String resultado, @QueryParam("usuario") String usuario, @Context UserDTO user);

    @POST
    @Path("/calcularTiempoDesistirSolicitud")
    public void calcularTiempoDesistirSolicitud(@QueryParam("idSolicitud") Long idSolicitud, @QueryParam("idTarea") String idTarea,
            @QueryParam("estadoSolicitud") String estadoSolicitud, @QueryParam("tipoTransaccion") String tipoTransaccion);

    @POST
    @Path("/desistirSolicitudesAutomatico")
    public void desistirSolicitudesAutomatico();
}
