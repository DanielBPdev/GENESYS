package com.asopagos.usuarios.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.usuarios.dto.CambiarContrasenaDTO;
import com.asopagos.usuarios.dto.GrupoDTO;
import com.asopagos.usuarios.dto.InformacionReenvioDTO;
import com.asopagos.usuarios.dto.PreguntaUsuarioDTO;
import com.asopagos.usuarios.dto.ResultadoDTO;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.usuarios.dto.UsuarioEmpleadorDTO;
import com.asopagos.usuarios.dto.UsuarioTemporalDTO;
import com.asopagos.usuarios.dto.UsuarioGestionDTO;
import com.asopagos.usuarios.dto.CambiarContrasenaGestionUsuarioDTO;
import com.asopagos.usuarios.dto.DatosConsultaGestionUsuariosDTO;
import java.io.IOException;
import javax.ws.rs.core.Response;

/**
 * 
 */
@Path("usuarios")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface UsuariosService {

	/**
	 * <b>Descripción</b>Método que se encarga de obtener los usuarios activos
	 * de una caja de compensación familiar
	 *
	 * * @return se retorna la lista con los datos del usuario
	 *
	 */
	@GET
	@Path("/ccf/{nombreUsuario}")
	public UsuarioCCF obtenerDatosUsuarioCajaCompensacion(@PathParam("nombreUsuario") String nombreUsuario,
			@DefaultValue("false") @QueryParam(value = "roles") Boolean roles,
			@QueryParam("primerNombre") String primerNombre, @QueryParam("primerApellido") String primerApellido);

	@POST
	@Path("empleador/admon")
	public void crearUsuarioAdminEmpleador(UsuarioEmpleadorDTO user);

	/**
	 * <b>Descripción</b>Método que se encarga de indicar si un usuario esta
	 * activo o no
	 *
	 * * @return retorna true si esta activo
	 *
	 */
	@GET
	@Path("/estaActivo")
	public boolean estaUsuarioActivo(@QueryParam("nombreUsuario") String nombreUsuario);

	/**
	 * <b>Descripción</b>Método que se encarga de indicar si un usuario esta
	 * activo o no
	 *
	 * * @return retorna true si esta activo
	 *
	 */
	@DELETE
	@Path("empleador/admon")
	public void borrarUsuarioAdminEmpleador(@QueryParam("nombreUsuario") String nombreUsuario);

	/**
	 * <b>Descripción: </b>Método que retorna las preguntas parametrizadas para
	 * el usuario
	 * 
	 * @param nombreUsuario
	 *            Es el usuario
	 * @param respuestas
	 *            Define si se retornan o no las respuestas
	 * @return Listado de preguntas y respuestas
	 */
	@GET
	@Path("preguntas")
	public List<PreguntaUsuarioDTO> consultarPreguntasUsuario(@QueryParam("nombreUsuario") String nombreUsuario,
			@QueryParam("respuestas") boolean respuestas, @Context UserDTO user);

	/**
	 * <b>Descripción: </b>Método que realiza la recuperación de la contraseña
	 * 
	 * @param respuestas
	 *            Son las respuestas dadas por el usuario
	 * @param user
	 *            Es el usuario en contexto
	 * @return true, si fue recuperada la contraseña; false en el caso contrario
	 */
	@POST
	@Path("recuperarContrasena")
	public Boolean recuperarContrasena(@QueryParam("nombreUsuario") String nombreUsuario,
			Map<String, String> respuestas, @Context UserDTO user);

	/**
	 * <b>Descripción: </b>Método que realiza actualiza pas preguntas y
	 * respuestas de un usuario
	 * 
	 * @param nombreUsuario
	 *            Es el nombre del usuario
	 * @param preguntas
	 *            Son las preguntas a actualizar
	 * @param user
	 *            Es el usuario en contexto
	 */
	@PUT
	@Path("preguntas")
	public void actualizarPreguntasUsuario(@QueryParam("nombreUsuario") String nombreUsuario,
			List<PreguntaUsuarioDTO> preguntas, @Context UserDTO user);

	/**
	 * <b>Descripción: </b>Método que realiza la creación de un usuario de la
	 * CCF
	 * 
	 * @param usuario
	 *            es el usuario a crear
	 */
	@POST
	@Path("ccf")
	public void crearUsuario(UsuarioCCF usuario, @Context UserDTO user);

	@POST
	@Path("reenviarCorreoEnrolamiento")
	public void reenviarCorreoEnrolamiento(@NotNull @Valid InformacionReenvioDTO notificacion,
			@Context UserDTO userDTO);

	/**
	 * Método encargado de consultar los usuarios vinculados por la sede enviada
	 * como parametro
	 * 
	 * @param idSede,
	 *            identificador de la sede
	 * @param estado,
	 *            estado de los usuarios a consultar
	 * @return listado de usuarios
	 */
	@GET
	@Path("sede/{idSede}")
	public List<UsuarioDTO> consultarUsuarioSede(@PathParam("idSede") @NotNull Long idSede,
			@QueryParam("estado") EstadoActivoInactivoEnum estado, @QueryParam("pertenecenCaJa") boolean pertenecenCaJa);

	/**
	 * <b>Descripción: </b>Método que realiza actualiza la información básica
	 * del usuario y sus permisos
	 * 
	 * * @param usuario es el usuario a actualizar
	 */
	@PUT
	@Path("ccf")
	public ResultadoDTO actualizarUsuarioCCF(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * Metodo encargado de cambiar la contrasenia a un usuario
	 * 
	 * @param dto,
	 *            datos de entrada
	 * @return resultado cambio contrasenia
	 */
	@POST
	@Path("cambiarContrasena")
	public ResultadoDTO cambiarContrasena(CambiarContrasenaDTO dto);

	/**
	 * Metodo que retorna los roles
	 * 
	 * @author <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
	 * @return lista de roles
	 */
	@GET
	@Path("consultarUsuarios")
	public List<UsuarioDTO> consultarUsuarios();

	/**
	 * Metodo que retorna los usuarios con la fecha de expiracion de contraseña
	 * 
	 * @return
	 */
	@GET
	@Path("consultarUsuariosAuditoria")
	public List<UsuarioDTO> consultarUsuariosAuditoria(@QueryParam("nombreUsuario") String nombreUsuario,
			@Context UriInfo uri, @Context HttpServletResponse response);

	/**
	 * Servicio encargado de actualizar el estado de activacion de un usuario
	 * 
	 * @param nombreUsuario,
	 *            usuario a realizar la actualizacion
	 * @param estado,
	 *            estado de la actualizacion
	 */
	@PUT
	@Path("/actualizarEstadoActivacionUsuario/{nombreUsuario}/{estado}")
	public void actualizarEstadoActivacionUsuario(@PathParam("nombreUsuario") @NotNull String nombreUsuario,
			@PathParam("estado") @NotNull boolean estado);

	/**
	 * Servicio encargado de actualizar el estado de activacion de un lista de
	 * usuarios
	 * 
	 * @param estado,
	 *            estado a realizar la actualizacion
	 * @param lstNombreUsuario,
	 *            lista de los usuarios a actualizar Metodo encargado de crear
	 *            un usuario temporal
	 * @param usuario,
	 *            usuario
	 */
	@PUT
	@Path("/actualizarEstadoUsuarioMasivo/{estado}")
	public void actualizarEstadoUsuarioMasivo(@PathParam("estado") @NotNull boolean estado,
			List<String> lstNombreUsuario);

	@POST
	@Path("/temporal")
	public void crearActualizarUsuarioTemporal(UsuarioTemporalDTO usuario);

	/**
	 * Metodo encargado de consultar un usuario temporal
	 * 
	 * @param tipoIden,
	 *            tipo identificacion
	 * @param valorNI,
	 *            numero identificacion
	 */
	@GET
	@Path("/temporal")
	public UsuarioTemporalDTO consultarUsuarioTemporal(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIden,
			@QueryParam("numeroIdentificacion") String valorNI);

	/**
	 * Metodo encargado de consultar el usuario por nombre usuario e inactivarlo
	 * 
	 * @param nombreUsuario,
	 *            nombre de usuario
	 */
	@POST
	@Path("inactivar/{nombreUsuario}")
	public void inactivarUsuario(@PathParam("nombreUsuario") String nombreUsuario,
			@DefaultValue("false") @QueryParam("isInmediate") Boolean isInmediate);

	/**
	 * Metodo encargado de inactivar los usuarios temporales
	 */
	@POST
	@Path("/revisar/temporal")
	public void inactivarActivarUsuariosTemporales();

	/**
	 * Servicio encargado de aceptar los derechos del usuario
	 * 
	 * @param nombreUsuario,
	 *            nombre del usuario el cual acepta los derechos
	 * @param debeAceptarTerminos,
	 *            booleano que acepta los derechos del usuario
	 */
	@POST
	@Path("/aceptarDerechos/{nombreUsuario}")
	public void aceptarDerechosAcceso(@NotNull @PathParam("nombreUsuario") String nombreUsuario,
			@NotNull @QueryParam("debeAceptarTerminos") Boolean debeAceptarTerminos);

	/**
	 * Metodo encargado de bloquear las cuentas de usuario vinculados a la caja
	 * de compensacion
	 */
	@POST
	@Path("/bloquearUsuarioCCF")
	public void bloquearCuentasUsuarioCCF();

	/**
	 * Metodo encargado de notificar a todos los usuarios activos, cuando su
	 * contraseña esta cerca de expirar
	 */
	@POST
	@Path("/generarAlertaClave")
	public void generarAvisoVencimientoClaveUsuario();

	/**
	 * Metodo encargado de obtener el listado de grupos al cual pertenece un
	 * usuario
	 * 
	 * @param nombreUsuario
	 * @param user,
	 *            usuario del contexto
	 * @return Listado de grupos
	 */
	@GET
	@Path("/{nombreUsuario}/grupos")
	public List<GrupoDTO> obtenerGruposUsuario(@NotNull @PathParam("nombreUsuario") String nombreUsuario,
			@Context UserDTO user);

	/**
	 * Metodo encargado de desbloquear la cuenta de un usuario
	 * 
	 * @param nombreUsuario,
	 *            nombre del usuario
	 * @param user,
	 *            usuario del contexto
	 */
	@PUT
	@Path("/desbloquear")
	public void desbloquearCuentaUsuario(@NotNull @QueryParam("nombreUsuario") String nombreUsuario,
			@Context UserDTO user);

	/**
	 * Metodo encargado de inactivar los usuarios temporales
	 */
	@POST
	@Path("/revisar/usuariosRetirados")
	public List<UsuarioDTO> bloquearUsuariosPendienteInactivacion(
			@DefaultValue("false") @QueryParam("consulta") Boolean consulta);

	/**
	 * Metodo encargado de inactivar usuarios masivamente.
	 */
	@POST
	@Path("/inactivarUsuariosMasivo")
	public void inactivarUsuariosMasivo(@NotNull List<String> usuarios,
			@DefaultValue("false") @QueryParam("isInmediate") Boolean inmediate);

	@GET
	@Path("/usuariosEnSesion")
	public List<UsuarioDTO> usuariosEnSesion();

	/**
	 * Metodo encargado de consultar el usuario por nombre usuario e inactivarlo
	 * 
	 * @param nombreUsuario,
	 *            nombre de usuario
	 */
	@POST
	@Path("activar/{nombreUsuario}")
	public void activarUsuario(@PathParam("nombreUsuario") String nombreUsuario,
			@DefaultValue("false") @QueryParam("isInmediate") Boolean isInmediate);

	/**
	 * Se hace reset al password del usuario empleador
	 * @param user
	 */
	@POST
	@Path("empleador/reasignarContrasena")
    public void reasignarContrasena(UsuarioEmpleadorDTO user);
	
	/**
	 * Valida si la contraseña del usuario esta proxima a expirar
	 * @param usuarioDTO
	 * @return Mensaje #356 en caso de estar proxima a expirar
	 */
	@POST
	@Path("validarDiasVencimientoClaveUsuario")
	public Long validarDiasVencimientoClaveUsuario(UsuarioDTO usuario);

	@POST
	@Path("empleador/crearUsuarioAdminEmpleadorMasivo")
	public String crearUsuarioAdminEmpleadorMasivo(UsuarioEmpleadorDTO user);

	/**
	 * GLPI 82800 Gestion Consultar Empleadores
	 *
	 */
	@GET
	@Path("/empleador")
	public List<UsuarioGestionDTO> gestionConsultarEmpleador(
		@QueryParam("nombreUsuario") String nombreUsuario,
        @DefaultValue("false") @QueryParam(value = "roles") Boolean roles,
        @QueryParam("tipoIdentificacion") String tipoIdentificacion,
        @QueryParam("numIdentificacion") String numIdentificacion,
        @QueryParam("razonSocial") String razonSocial,
        @QueryParam("primerNombre") String primerNombre,
        @QueryParam("primerApellido") String primerApellido,
        @QueryParam("segundoNombre") String segundoNombre,
        @QueryParam("segundoApellido") String segundoApellido,
        @QueryParam("usuarioActivo") Boolean usuarioActivo,
        @QueryParam("fechaCreacion") String fechaCreacion,
        @QueryParam("creadoPor") String creadoPor,
        @QueryParam("fechaModificacion") String fechaModificacion,
        @QueryParam("modificadoPor") String modificadoPor);

	/**
	 * GLPI 82800 Gestion Crear Empleadores
	 *
	 */
	@POST
	@Path("empleador")
	public void gestionCrearEmpleador(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Actualizar Empleadores
	 *
	 */
	@PUT
	@Path("empleador")
	public ResultadoDTO gestionActualizarEmpleador(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Cambiar Contraseña Usuario Empleador
	 *
	 */
	// @POST
	// @Path("gestionRestablecerContrasenaEmpleador")
	// void gestionRestablecerContrasenaEmpleador(CambiarContrasenaGestionUsuarioDTO dto);

	/**
	 * GLPI 82800 Gestion Activar/Inactivar Usuario Empleador
	 *
	 */
	@PUT
	@Path("gestionActivarInactivarEmpleador")
	public ResultadoDTO gestionActivarInactivarEmpleador(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Crear Usuario Empleador Masivo
	 *
	 */
	@POST
	@Path("empleador/gestionMasivosEmpleador")
	public void gestionMasivosEmpleador(List<UsuarioCCF> usuarios, @Context UserDTO userDTO);

	/**
	 * GLPI 82800 Gestion Consultar Persona
	 *
	 */
	@GET
	@Path("/persona")
	public List<UsuarioGestionDTO> gestionConsultarPersona(
		@QueryParam("nombreUsuario") String nombreUsuario,
        @DefaultValue("false") @QueryParam(value = "roles") Boolean roles,
		@QueryParam("tipoIdentificacion") String tipoIdentificacion,
		@QueryParam("numIdentificacion") String numIdentificacion,
        @QueryParam("primerNombre") String primerNombre,
        @QueryParam("primerApellido") String primerApellido,
        @QueryParam("segundoNombre") String segundoNombre,
        @QueryParam("segundoApellido") String segundoApellido,
        @QueryParam("usuarioActivo") Boolean usuarioActivo,
        @QueryParam("fechaCreacion") String fechaCreacion,
        @QueryParam("creadoPor") String creadoPor,
        @QueryParam("fechaModificacion") String fechaModificacion,
        @QueryParam("modificadoPor") String modificadoPor);

	/**
	 * GLPI 82800 Gestion Crear Usuario Persona
	 *
	 */
	@POST
	@Path("persona")
	public void gestionCrearPersona(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Actualizar Usuario Persona
	 *
	 */
	@PUT
	@Path("persona")
	public ResultadoDTO gestionActualizarPersona(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Cambiar Contraseña Usuario Persona
	 *
	 */
	// @POST
	// @Path("gestionRestablecerContrasenaPersona")
	// public void gestionRestablecerContrasenaPersona(CambiarContrasenaGestionUsuarioDTO dto);

	/**
	 * GLPI 82800 Gestion Activar/Inactivar Usuario Persona
	 *
	 */
	@PUT
	@Path("gestionActivarInactivarPersona")
	public ResultadoDTO gestionActivarInactivarPersona(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Crear Usuario Persona Masivo
	 *
	 */
	@POST
	@Path("persona/gestionMasivosPersona")
	public void gestionMasivosPersona(List<UsuarioCCF> usuarios, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Consultar Usuario Ccf
	 *
	 */
	@GET
	@Path("/caja")
	public List<UsuarioGestionDTO> gestionConsultarCcf(
		@QueryParam("nombreUsuario") String nombreUsuario,
        @DefaultValue("false") @QueryParam(value = "roles") Boolean roles,
		@QueryParam("tipoIdentificacion") String tipoIdentificacion,
		@QueryParam("numIdentificacion") String numIdentificacion,
        @QueryParam("primerNombre") String primerNombre,
        @QueryParam("primerApellido") String primerApellido,
        @QueryParam("segundoNombre") String segundoNombre,
        @QueryParam("segundoApellido") String segundoApellido,
        @QueryParam("usuarioActivo") Boolean usuarioActivo,
        @QueryParam("fechaCreacion") String fechaCreacion,
        @QueryParam("creadoPor") String creadoPor,
        @QueryParam("fechaModificacion") String fechaModificacion,
        @QueryParam("modificadoPor") String modificadoPor);
	/**
	 * GLPI 82800 Gestion Crear Usuario Ccf
	 *
	 */
	@POST
	@Path("caja")
	public void gestionCrearCcf(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Actualizar Usuario Ccf
	 *
	 */
	@PUT
	@Path("caja")
	public ResultadoDTO gestionActualizarCcf(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Cambiar Contraseña Usuario Ccf
	 *
	 */
	@POST
	// @Path("gestionRestablecerContrasenaCcf")
	// public void gestionRestablecerContrasenaCcf(CambiarContrasenaGestionUsuarioDTO dto);

	/**
	 * GLPI 82800 Gestion Activar/Inactivar Usuario Ccf
	 *
	 */
	@PUT
	@Path("gestionActivarInactivarCcf")
	public ResultadoDTO gestionActivarInactivarCcf(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Crear Usuario Ccf Masivo
	 *
	 */
	@POST
	@Path("caja/gestionMasivosCcf")
	public void gestionMasivosCcf(List<UsuarioCCF> usuarios, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Consultar Tercero
	 *
	 */
	@GET
	@Path("/terceros")
	public List<UsuarioGestionDTO> gestionConsultarTerceros(
			@QueryParam("nombreUsuario") String nombreUsuario,
			@DefaultValue("false") @QueryParam(value = "roles") Boolean roles,
			@QueryParam("primerNombre") String primerNombre, 
			@QueryParam("primerApellido") String primerApellido,
			@QueryParam("estadoConvenio") String estadoConvenio,
			@QueryParam("nombreConvenio") String nombreConvenio);

	/**
	 * GLPI 82800 Gestion Crear Usuario Tercero
	 *
	 */
	@POST
	@Path("terceros")
	public void gestionCrearTerceros(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Actualizar Usuario Tercero
	 *
	 */
	@PUT
	@Path("terceros")
	public ResultadoDTO gestionActualizarTerceros(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Cambiar Contraseña Usuario Tercero
	 *
	 */
	@POST
	@Path("gestionRestablecerContrasenaTerceros")
	public void gestionRestablecerContrasenaTerceros(CambiarContrasenaDTO dto);

	/**
	 * GLPI 82800 Gestion Activar/Inactivar Usuario Tercero
	 *
	 */
	@PUT
	@Path("gestionActivarInactivarTerceros")
	public ResultadoDTO gestionActivarInactivarTerceros(UsuarioCCF usuario, @Context UserDTO user);

	/**
	 * GLPI 82800 Gestion Crear Usuario Tercero Masivo
	 *
	 */
	@POST
	@Path("terceros/gestionMasivosTerceros")
	public String gestionMasivosTerceros(List<UsuarioCCF> usuarios, @Context UserDTO user);

	/**
	 * GLPI 82800 Inactivar Usuarios Keycloak 
	 *
	 */
	@POST
	@Path("inactivarUsuarioKeycloak")
	public void inactivarUsuarioKeycloak(@QueryParam("idUsuario") String idUsuario);

	@GET
	@Path("/usuariosEnSesionParaInactivar")
	public List<UsuarioDTO> usuariosEnSesionParaInactivar(@QueryParam("clientId") String clientId);

	@GET
	@Path("/usuariosEnSesionParaCerrarSesion")
	public List<UsuarioDTO> usuariosEnSesionParaCerrarSesion(@QueryParam("clientId") String clientId);

	/**
	 * GLPI 82800 Gestionar Cambios de Contraseña Usuarios por Email
	 *
	 */
	@POST
	@Path("restablecerContrasena")
	public void restablecerContrasena(@QueryParam("email") String email) throws Exception;

	/**
	 * GLPI 82800 Exportar Data de la tabla al Consultar Usuarios Empleador
	 *
	 */
	@POST
    @Path("/exportarInformacionConsultaGestionUsuariosEmpleador")
    public Response exportarInformacionConsultaGestionUsuariosEmpleador(
        DatosConsultaGestionUsuariosDTO datosConsultaGestionUsuariosDTO, 
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException;

    /**
     * GLPI 82800 Exportar Data de la tabla al Consultar Usuarios Persona
     *
     */
    @POST
    @Path("/exportarInformacionConsultaGestionUsuariosPersona")
    public Response exportarInformacionConsultaGestionUsuariosPersona(
        DatosConsultaGestionUsuariosDTO datosConsultaGestionUsuariosDTO, 
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException;

    /**
     * GLPI 82800 Exportar Data de la tabla al Consultar Usuarios CCF
     *
     */
    @POST
    @Path("/exportarInformacionConsultaGestionUsuariosCcf")
    public Response exportarInformacionConsultaGestionUsuariosCcf(
        DatosConsultaGestionUsuariosDTO datosConsultaGestionUsuariosDTO, 
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException;

    /**
     * GLPI 82800 Exportar Data de la tabla al Consultar Usuarios Tercero 
     *
     */
    @POST
    @Path("/exportarInformacionConsultaGestionUsuariosTerceros")
    public Response exportarInformacionConsultaGestionUsuariosTerceros(
        DatosConsultaGestionUsuariosDTO datosConsultaGestionUsuariosDTO, 
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException;

		
		/**
	 * <b>Descripción: </b>Método que realiza la recuperación de la contraseña
	 * 
	 * @param respuestas
	 *            Son las respuestas dadas por el usuario
	 * @param user
	 *            Es el usuario en contexto
	 * @return true, si fue recuperada la contraseña; false en el caso contrario
	 */
	@GET
	@Path("actualizarContrasenasUsuariosMasivos")
	public Map<String, String>  actualizarContrasenasUsuariosMasivos(@QueryParam("usuarios") String usuarios, @Context UserDTO user);

	/**
	 * GLPI 95241 
	 *
	 */
	@POST
	@Path("confirmarCambioContrasena")
	void confirmarCambioContrasena(CambiarContrasenaGestionUsuarioDTO dto);

}
