package com.asopagos.entidades.pagadoras.service;

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
import com.asopagos.dto.modelo.SolicitudAsociacionPersonaEntidadPagadoraModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.entidades.pagadoras.dto.ConsultarEntidadPagadoraOutDTO;
import com.asopagos.entidades.pagadoras.dto.DocumentoEntidadPagadoraDTO;
import com.asopagos.entidades.pagadoras.dto.EntidadPagadoraDTO;
import com.asopagos.entidades.pagadoras.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoAfiliacionEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.validation.annotation.ValidacionActualizacion;
import com.asopagos.validation.annotation.ValidacionCreacion;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con las entidades pagadoras <b>Historia de Usuario:</b> 133
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
@Path("entidadesPagadoras")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface EntidadesPagadorasService {

	/**
	 * <b>Descripción</b>Método que se encarga de buscar las entidades
	 *
	 * @param tipoIdentificacion
	 *            tipo de identificación
	 * @param numeroIdentificacion
	 * @param razonSocial
	 * @return lista de objetos con las entidades pagadoras
	 *
	 */
	@GET
	@Path("/buscar")
	public List<ConsultarEntidadPagadoraOutDTO> buscarEntidadPagadora(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("razonSocial") String razonSocial);

	/**
	 * <b>Descripción</b>Método que se encarga de crear o actualizar 
	 * la entidad pagadora
	 *
	 * @param entidadPagadoraDTO
	 *            parámetro payload
	 * @return Long idEntidadPagadora, identificador de la entidad pagadora
	 *
	 */
	@POST
	public Long crearEntidadPagadora(@NotNull @Valid @ValidacionCreacion EntidadPagadoraDTO entidadPagadoraDTO);

	/**
	 * <b>Descripción</b>Método que se encarga de buscar las entidades
	 *
	 * @param entidadPagadoraDTO
	 *            parámetro payload
	 *
	 */
	@PUT
	public void actualizarEntidadPagadora(
			@NotNull @Valid @ValidacionActualizacion EntidadPagadoraDTO entidadPagadoraDTO);

	/**
	 * <b>Descripción</b> Método que se encarga de guardar los documentos de las
	 * entidades pagadoras
	 * 
	 * @param documentos
	 *            lista de documentos de la entidad pagadora
	 */
	@POST
	@Path("/documentos")
	public void guardarDocumentosAdjuntos(@NotNull List<DocumentoEntidadPagadoraDTO> documentos);

	/**
	 * <b>Descripción</b>Método que se encarga de consultar las entidades
	 * 
	 * @param tipoIdentificacion
	 *            tipo de identifiación
	 * @param numeroIdentificacion
	 *            Número de identificación
	 * @return EntidadPagadoraDTO Objeto con la entidada pagadora consultada
	 */
	@GET
	@Path("/consultar")
	public EntidadPagadoraDTO consultarEntidadPagadora(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, 
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);
	
	/**
	 * <b>Descripción</b>Método que se encarga de consultar 
	 * todas las entidades pagadoras
	 * 
	 * @return List<ConsultarEntidadPagadoraOutDTO>
	 * 					lista con todas la entidade pagadoras consultadas
	 */
	@GET
    public List<ConsultarEntidadPagadoraOutDTO> consultarTodasEntidadesPagadoras(@Context UriInfo uri,
            @Context HttpServletResponse response);

	/**
	 * <b>Descripción</b>Método que se encarga de consultar las solicitudes
	 * asociadas a la persona de la entidad pagadora
	 *
	 * @param idEntidadPagadora
	 *            Identificador de la entidad pagadora
	 * @param tipoGestion
	 *            tipo de gestión de la solicitud
	 * @param consecutivoGestion
	 * 			  consecutivo de la gestión
	 * @param numeroRadicado
	 * 			  número de radicado
	 * @return List<SolicitudAsociacionPersonaEntidadPagadoraDTO> lista con las
	 *         solicitudes consultadas
	 */
	@GET
	@Path("/{idEntidadPagadora}/solicitudesAsociacionPersonas")
	public List<SolicitudAsociacionPersonaEntidadPagadoraDTO> consultarSolicitudesAsociacionPersonas(
			@NotNull @PathParam("idEntidadPagadora") Long idEntidadPagadora,
			@QueryParam("tipoGestion") TipoGestionSolicitudAsociacionEnum tipoGestion,
			@QueryParam("consecutivoGestion") String consecutivoGestion,
			@QueryParam("numeroRadicado") String numeroRadicado);

	/**
	 * <b>Descripción</b> Método que se encarga de ejecutar 
	 * las validaciones de la gestión de solicitudes de asociación
	 * 
	 * @param idEntidadPagadora
	 *            Identificador de la entidad pagadora
	 *            
	 * @param solicitudes
	 * 			lista con las solicitudes a validar
	 *
	 * @param tipoGestion
	 *            Tipo de gestión para la solicitud
	 * 
	 * @return Boolean true si las validaciones son exitosas false si falla
	 *         alguna validación
	 */
	@POST
	@Path("/{idEntidadPagadora}/solicitudesAsociacionPersonas/validarGestion/{TipoGestionSolicitudAsociacionEnum}")
	public Boolean ejecutarValidacionesSolicitudesAsociacion(
			@NotNull @PathParam("idEntidadPagadora") Long idEntidadPagadora,
			@NotNull List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes,
			@NotNull @PathParam("TipoGestionSolicitudAsociacionEnum") TipoGestionSolicitudAsociacionEnum tipoGestion);

	/**
	 * <b>Descripción</b> Método que se encarga de registrar la gestión de las
	 * solicitudes de asociación
	 * 
	 * @param idEntidadPagadora
	 *            Identificador de la entidad pagadora
	 *            
	 *  @param solicitudes
	 * 			lista con las solicitudes a actualizar
	 * 
	 * @param user
	 * 			usuario que realiza la actulización
	 * 
	 * @return Long consecutivoGestion consecutivo de la gestión
	 */
	@POST
	@Path("/{idEntidadPagadora}/solicitudesAsociacionPersonas/gestionar")
	public String actualizarGestionSolicitudesAsociacion(
			@NotNull @PathParam("idEntidadPagadora") Long idEntidadPagadora,
			@ValidacionActualizacion @NotNull List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes,
			@Context UserDTO user);

	/**
	 * <b>Descripción</b>Método que se encarga de generar los archivos de
	 * gestión de solicitudes de asociación
	 *
	 * @param idEntidadPagadora
	 *            Identificador de la entidad pagadora
	 * @param consecutivoGestion
	 *            número consecutivo de la gestión
	 *
	 * @return Response
	 * 			resultado del archivo generado
	 */
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM) 
	@Path("/{idEntidadPagadora}/solicitudesAsociacionPersonas/generarArchivo/{consecutivoGestion}")
	public Response generarArchivoGestionSolicitudesAsociacion(
			@NotNull @PathParam("idEntidadPagadora") Long idEntidadPagadora,
			@NotNull @PathParam("consecutivoGestion") String consecutivoGestion);

	/**
	 * <b>Descripción</b> Método que se encarga de guardar el identificador del
	 * archivo carta
	 * 
	 * @param idEntidadPagadora
	 *            Identificador de la entidad pagadora
	 *            
	 * @param tipoGestion
	 * 			tipo de gestión 
	 *
	 * @param numerosRadicado
	 *            lista con números de radicado
	 */
	@POST
	@Path("/{idEntidadPagadora}/solicitudesAsociacionPersonas/guardarCarta")
	public void guardarIdentificadorArchivoCarta(@NotNull @PathParam("idEntidadPagadora") Long idEntidadPagadora,
			@QueryParam("tipoGestion") TipoGestionSolicitudAsociacionEnum tipoGestion,
			@QueryParam("identificadorArchivoCarta") String identificadorArchivoCarta, List<String> numerosRadicado);

	/**
	 * <b>Descripción</b> Método que se encarga de 
	 * guardar el identificador del archivo carta
	 * 
	 * @param idEntidadPagadora
	 *            Identificador de la entidad pagadora
	 *
	 * @param consecutivoGestion
	 *            número consecutivo de la gestión
	 *            
	 *  @param identificadorArchivo
	 *  		Identificador archivo
	 */
	@POST
	@Path("/{idEntidadPagadora}/solicitudesAsociacionPersonas/guardar/{consecutivoGestion}")
	public void guardarIdentificadorArchivo(@NotNull @PathParam("idEntidadPagadora") Long idEntidadPagadora,
			@NotNull @PathParam("consecutivoGestion") String consecutivoGestion,
			@NotNull String identificadorArchivo);


    /**
     * Consulta las entidades pagadoras habilitadas por el tipo de afiliacion
     * @param tipoAfiliacion
     *        Tipo de afiliacion de la entidad pagadora
     * @return lista de entidades habilitadas del tipo afiliacion enviado
     */
    @GET
    @Path("/consultarEntidadesPorTipo")
    public List<ConsultarEntidadPagadoraOutDTO> consultarEntidadesByTipoAfiliacion(
            @QueryParam("tipoAfiliacion") TipoAfiliacionEntidadPagadoraEnum tipoAfiliacion);

    /**
     * Consulta las entidades pagadoras con el id de solicitud global
     * @param idSolicitudGlobal
     *        Identificador de la solicitud global
     * @return Solicitud de entidad pagadora correspondiente al identificador de solicitud global
     */
    @GET
    @Path("/consultarSolicitudEntidadPagadora")
    public SolicitudAsociacionPersonaEntidadPagadoraModeloDTO consultarSolicitudEntidadPagadora(Long idSolicitudGlobal);

    /**
     * Consulta las entidades pagadoras con el id de solicitud global
     * @param idSolicitudGlobal
     *        Identificador de la solicitud global
     * @return Solicitud de entidad pagadora correspondiente al identificador de solicitud global
     */
    @GET
    @Path("/actualizarEstadoSolicitudEntidadPagadora")
    public void actualizarEstadoSolicitudEntidadPagadora(Long idSolicitudGlobal, EstadoSolicitudPersonaEntidadPagadoraEnum estado);

    /**
     * Servicio encargado de validar si la entidad y la CCF tienen convenio para la generacion del archivo
     *
     * @param tipoIdentificacion
     *        Tipo identificacion entidad pagadora
     * @param numeroIdentificacion
     *        Numero identificacion entidad pagadora
     * @return True si existe convenio false en caso contrario
     */
    @GET
    @Path("/validarConvenioEntidad")
    public Boolean validarConvenioEntidad(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Realiza la consulta de personas asociadas a la entidad pagadora por id entidad pagadora <br>
     * Consulta paginada
     * 
     * @return List<SolicitudAsociacionPersonaEntidadPagadoraDTO> lista con las
     *         solicitudes consultadas
     */
    @GET
    @Path("/consultarPersonasAsociadasEntidad")
    public List<SolicitudAsociacionPersonaEntidadPagadoraDTO> consultarPersonasAsociadasEntidad(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @NotNull @QueryParam("idEntidadPagadora") Long idEntidadPagadora);

}
