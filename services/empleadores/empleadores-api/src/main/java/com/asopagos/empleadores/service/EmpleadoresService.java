package com.asopagos.empleadores.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.EmpleadorDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.InformacionContactoDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.Vista360EmpleadorBusquedaParamsDTO;
import com.asopagos.dto.Vista360EmpleadorRespuestaDTO;
import com.asopagos.dto.modelo.BeneficioEmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.validation.annotation.ValidacionCreacion;
import com.asopagos.enumeraciones.core.TipoCertificadoMasivoEnum;
import java.util.Map;
import javax.ws.rs.core.Response; 
import javax.ws.rs.core.MediaType;
import com.asopagos.dto.ControlCertificadosMasivosDTO;
/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de empleadores <b>Módulo:</b> Asopagos - transversal<br/>
 *
 * @author Jerson Zambrano
 *         <a href="jzambrano:jzambrano@heinsohn.com.co"> jzambrano</a>
 */
@Path("empleadores")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface EmpleadoresService {

	/**
	 * <b>Descripción</b>Metodo que se encarga de Crear empleadores
	 * <code>empleador es el empleador que se va a crear</code><br/>
	 *
	 * @param empleador
	 *            Empleador a crear en la base de datos
	 * @return id del empleador
	 */
	@POST
	@Path("")
	public Long crearEmpleador(Empleador empleador);

	/**
	 * <b>Descripción</b>Método que se encarga de crear un socio a un empleador
	 * <code>idempleador, id del emmpleador a consultar, listSocios
	 * lista de socios a guardar</code><br/>
	 *
	 * @param idEmpleador,
	 *            id del empleador a consultar
	 * @param listSocios
	 *            listado para guardar
	 * @return listado de los ids de los socios empleadores
	 *
	 */
	@POST
	@Path("/{idEmpleador}/sociosEmpleador")
	@Deprecated
	public List<Long> crearSocioEmpleador(@PathParam("idEmpleador") Long idEmpleador, List<SocioEmpleador> listSocios);

	/**
	 * <b>Descripción</b>Método que se encarga de actualizar el socio empleador
	 * <code>idempleador: id del empleador, socioEmpleador información a  a modificar,
	     * idSocioEmpleador el id del socio que se va a modificar </code><br/>
	 *
	 * @param idEmpleador,
	 *            id del empleador a consultar
	 * @param socioEmpleador
	 *            información a registrar
	 * @param idSocioEmpleador
	 *            id del Socio a modificar
	 *
	 */
	@PUT
	@Path("/{idEmpleador}/sociosEmpleador/{idSocioEmpleador}")
	@Deprecated
	public void actualizarSocioEmpleador(@PathParam("idEmpleador") Long idEmpleador,
			@PathParam("idSocioEmpleador") Long idSocioEmpleador, SocioEmpleador socioEmpleador);

	/**
	 * <b>Descripción</b> Método que se encarga de Crear los
	 * RolContactoEmpleadores
	 * <code>idEmpleador, id del Empleador que se le asignarán los
	     *        roles de contacto (1-responsableAfiliacion, 2-responsableAportes,
	 *        3 ResponsableSubsidios ), rolesContacto es la lista de
	 *        roles a guardar.</code><br/>
	 *
	 * @param idEmpleador
	 *            Id del empleador a la que se asociara al RolContactoEmpleador
	 * @param rolesContacto
	 *            informacion que se asignrá al empleador
	 * @return Se retorna listado de id de roles de contacto del empleador
	 *
	 */
	@POST
	@Path("/{idEmpleador}/rolesContacto")
	public List<Long> crearRolContactoEmpleador(@PathParam("idEmpleador") Long idEmpleador,
			@NotNull @Size(min = 1, max = 3) @ValidacionCreacion List<RolContactoEmpleador> rolesContacto);

	/**
	 * Actualiza el empleador enviado en <code>empleador</code>. Se obtiene el
	 * empleador a partir de la persona asociada. <i>Revisar si se requiere el
	 * envío del id de la base de datos </i>
	 * 
	 * @param idEmpleador
	 *            <i>Parámetro ignorado y para eliminar puesto que el empleador
	 *            se obtine a partir de la persona</i>
	 * @param empleador
	 *            Es el empleador con la información actualizada.
	 * 
	 */
	@PUT
	@Path("/{idEmpleador}")
	public void actualizarEmpleador(@PathParam("idEmpleador") Long idEmpleador, EmpleadorDTO empleador);

	/**
	 * <b>Descripción</b> Método que se encarga de Crear los
	 * RolContactoEmpleadores
	 * <code>idEmpleador, id del Empleador que se le asignarán los
	     *        roles de contacto (1-responsableAfiliacion, 2-responsableAportes,
	 *        3 ResponsableSubsidios ), rolesContacto es la lista de
	 *        roles a guardar.</code><br/>
	 *
	 * @param idEmpleador
	 *            Id del empleador a la que se asociara al RolContactoEmpleador
	 * @param rolesContacto
	 *            informacion que se asignrá al empleador
	 * @return Se retorna listado de id de roles de contacto del empleador
	 *
	 */
	@PUT
	@Path("/{idEmpleador}/rolesContacto")
	public void actualizarRolContactoEmpleador(@PathParam("idEmpleador") Long idEmpleador,
			@NotNull @Size(min = 1, max = 3) @ValidacionCreacion List<RolContactoEmpleador> rolesContacto);

	/**
	 * <b>Descripción</b>Metodo que se encarga de listar los empleadores
	 * afiliados en un rango de fechas
	 * <code>fechaInicio es la fecha desde donde inicia la lista de afiliacion y fechaFin
	 * es la fecha hasta donde se listan los empleadores afiliados en el back</code>
	 * <br/>
	 * <br />
	 *
	 * @param fechaInicio
	 *            (timestamp) fecha inicial desde donde se inicia la consulta
	 * @param fechaFin
	 *            (timestamp) fecha final donde se finaliza la consulta
	 * @return se retorna la lista de los empleadores afiiliados
	 *
	 */
	@GET
	@Path("/afiliados")
	public List<EmpleadorDTO> generarReporteDeEmpleadoresAfiliados();

	/**
	 * <b>Descripción</b>Método que se encarga de consultar listado de
	 * empleadores
	 * <code>tipoIdentificacion: es el tipo de identificación del empleadro,
	     * numeroIdentificacion: es el numero de identificación del empleador,
	 * digitoVerificacion: es el numero de verificacion del tipo NIT y
	 * razonSocial: es la razon social del empleador</code><br/>
	 *
	 * @param tipoIdentificacion,
	 *            tipo de identificación del empleador, de tipo enumeración
	 * @param numeroIdentificacion
	 *            Numero de identificación del empleador
	 * @param digitoVerificacion
	 *            Digito de verificación del documento
	 * @param razonSocial
	 *            Razon social del empleador
	 * @param aplicaPre
	 *            Indica si debe consultar empeladores que se encuentren en
	 *            estado no formalizado con información
	 * @return lista de empleadores
	 *
	 */
	@GET
	@Path("")
	public List<Empleador> buscarEmpleador(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("razonSocial") String razonSocial, @QueryParam("aplicaPre") Boolean aplicaPre);
	

	/**
	 * <b>Descripción</b>Metodo encargado de consultar listados de socios de un
	 * empleador
	 *
	 * @param idEmpleador
	 *            Id del empleador que se va a consultar
	 * @return listado de socios del empleador
	 */
	@GET
	@Path("/{idEmpleador}/sociosEmpleador")
	public List<SocioEmpleador> consultarSocioEmpleador(@PathParam("idEmpleador") Long idEmpleador);

	/**
	 * <b>Descripción</b>Método encargado de un crear representante legal o
	 * suplente para un empleador
	 *
	 * @param idEmpleador
	 *            Id del empleador que se va a consultar
	 * @param representante
	 *            información del representante
	 * @param titular
	 *            valor booleano, true si es representante legal principal o
	 *            false si es representante legal suplente
	 * @return id de la persona (representante legal)
	 */
	@POST
	@Path("/{idEmpleador}/representantesLegales")
	public Long crearRepresentanteLegal(@PathParam("idEmpleador") Long idEmpleador,
			@QueryParam("titular") Boolean titular, Persona representante);

	/**
	 * <b>Descripción</b>Método encargado de consultar listado de roles de
	 * contacto de un empleador
	 *
	 * @param idEmpleador
	 *            Id del empleador que se va a consultar
	 * @return listado de roles de contacto del empleador
	 */
	@GET
	@Path("/{idEmpleador}/rolesContacto")
	public List<RolContactoEmpleador> consultarRolesContactoEmpleador(@PathParam("idEmpleador") Long idEmpleador);

	/**
	 * <b>Descripción</b> Retorna el representante legal o suplente con la ubicación principal del
	 * empleador al que pertenece.
	 *
	 * @param idEmpleador
	 *            Id del empleador que se va a consultar
	 * @param titular
	 *            Valor booleano que indica si es representante legal principal
	 *            o suplente
	 * @return representante legal de tipo Persona o <code>null</code> si no existen datos relaciones con el empleador
	 */
	@GET
	@Path("/{idEmpleador}/representantesLegales")
	public Persona consultarRepresentantesLegalesEmpleador(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			@QueryParam("titular") Boolean titular);

	/**
	 * <b>Descripción</b>Método encargado de actualizar un representante legal
	 * del empleador
	 * 
	 * @param representante
	 *            información actualizada del representante legal o suplente
	 * @param idEmpleador
	 *            Id del empleador que se va a consultar
	 * @param titular
	 *            Valor booleano que indica si es representante legal principal
	 *            o suplente
	 */
	@PUT
	@Path("/{idEmpleador}/representantesLegales")
	public void actualizarRepresentanteLegalEmpleador(@PathParam("idEmpleador") Long idEmpleador,
			@QueryParam("titular") Boolean titular, Persona representante);

	/**
	 * <b>Descripción</b>Método encargado de actualizar el estado de un
	 * empleador
	 * 
	 * @param idEmpleador
	 *            Id del empleador que se va a consultar
	 * @param nuevoEstado
	 *            nuevo estado del empleador
	 */
	@PUT
	@Path("/{idEmpleador}/estado")
	public void actualizarEstadoEmpleador(@PathParam("idEmpleador") Long idEmpleador, EstadoEmpleadorEnum nuevoEstado);

	/**
	 * <b>Descripción</b>Método encargado de consultar un empleador por el Id.
	 * <code>idEmpleador es el id del empleador en el sistema</code>
	 * 
	 * @param idEmpleador
	 *            Id del empleador que se va a consultar
	 * @return empleador de tipo Empleador
	 */
	@GET
	@Path("/{idEmpleador}")
	public Empleador consultarEmpleador(@PathParam("idEmpleador") Long idEmpleador);

	/**
	 * <b>Descripción</b>Método encargado de consultar un empleador por el Id.
	 * <code>idEmpleador es el id del empleador en el sistema</code>
	 * 
	 * @param idEmpleador
	 * @param socios
	 * @return La lista de Ids de los socios que quedaron registrados al
	 *         empleador
	 */
	@PUT
	@Path("/{idEmpleador}/sociosEmpleador/gestionar")
	public List<Long> gestionarSociosEmpleador(@PathParam("idEmpleador") Long idEmpleador, List<SocioEmpleador> socios);

	/**
	 * <b>Descripción</b>Método que permite establecer los responsables de
	 * contacto con el empleador para la caja de compensación.
	 * 
	 * @param idEmpleador
	 * @param usuariosCajaCompensacion
	 * @return
	 */
	@POST
	@Path("{idEmpleador}/responsablesCajaCompensacion")
	public List<Long> establecerResponsablesCajaCompensacion(@PathParam("idEmpleador") Long idEmpleador,
			@NotNull @Size(min = 2, max = 2) List<String> usuariosCajaCompensacion);

	/**
	 * <b>Descripción</b>Método que permite actualizar los responsables de
	 * contacto con el empleador para la caja de compensación.
	 * 
	 * @param idEmpleador
	 * @param usuariosCajaCompensacion
	 */
	@PUT
	@Path("{idEmpleador}/responsablesCajaCompensacion")
	public void actualizarResponsablesCajaCompensacion(@PathParam("idEmpleador") Long idEmpleador,
			@NotNull @Size(min = 2, max = 2) List<String> usuariosCajaCompensacion);

	/**
	 * <b>Descripción</b>Método que permite actualizar los responsables de
	 * contacto con el empleador para la caja de compensación.
	 * 
	 * @param idEmpleador
	 * @return
	 */
	@GET
	@Path("{idEmpleador}/responsablesCajaCompensacion")
	public List<String> consultarResponsablesCajaCompensacion(@PathParam("idEmpleador") Long idEmpleador);

	/**
	 * Fitra solicitudes de afiliación por tipoIdentificación, número de
	 * identificación y número de radicado
	 * 
	 * @param idEmpleador
	 * @return
	 */
	@GET
	@Path("/consultarInformacionContacto")
	public InformacionContactoDTO consultarInformacionContacto(@QueryParam("idEmpleador") Long idEmpleador);

	/**
	 * Método que guarda los datos temporales de una solicitud de afiliación
	 * 
	 * @param idSolicitud
	 *            Es la solicitud a la que se asocian los temporales
	 * @param jsonPayload
	 *            Son los datos a guardar
	 * @param numeroIdentificacion
	 *            El tipo de numeroIdentificacion de la persona o empleador a la
	 *            que esta asociada el data temporal
	 * @param tipoIdentificacion
	 *            El tipo de identificación de la persona o empleador a la que
	 *            esta asociada el data temporal
	 * @return El identificador de los datos temporales
	 */
	@POST
	@Path("/guardarDatosTemporales")
	public Long guardarDatosTemporalesEmpleador(@QueryParam("idSolicitudGlobal") Long idSolicitud, String jsonPayload,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion);

	/**
	 * Método que consulta los datos temporales de una solicitud de afiliación
	 * 
	 * @param idSolicitud
	 *            Es la solicitud a la que se asocian los temporales
	 * @return El Dto del composite para la HU 110
	 */
	@GET
	@Path("/datosTemporales")
	public String consultarDatosTemporalesEmpleador(@QueryParam("idSolicitudGlobal") Long idSolicitud);

	/**
	 * Método que consulta la clasificación de la última solicitud radicada
	 * 
	 * @param idEmpleador
	 *            identificador del empleador para realizar la consulta
	 * @return ClasificacionEnum la clasificación de la ultima solicitud
	 *         radicada
	 */
	@GET
	@Path("/{idEmpleador}/clasificacion")
	public ClasificacionEnum consultarUltimaClasificacion(@NotNull @PathParam("idEmpleador") Long idEmpleador);

	/**
	 * Actualiza el empleador enviado en <code>empleador</code>.
	 * 
	 * @param empleador
	 *            Es el empleador con la información actualizada.
	 */
	@POST
	@Path("/actualizarDatosEmpleador")
	public void actualizarDatosEmpleador(@NotNull Empleador empleador);

	/**
	 * Método encargado de consultar los empleadores asociados a inactivación
	 * masiva ley 1429 de 2010.
	 * 
	 * @return empleadores a inactivar masivamente.
	 */
	@GET
	@Path("/consultarEmpleadoresInactivar1429")
	public List<Long> consultarEmpleadoresInactivar1429();

	/**
	 * Método encargado de consultar los empleadores asociados a inactivación
	 * masiva ley 590 de 2000.
	 * 
	 * @return empleadores a inactivar masivamente.
	 */
	@GET
	@Path("/consultarEmpleadoresInactivar590")
	public List<Long> consultarEmpleadoresInactivar590();

	/**
	 * Método encargado de almacenar una solicitud de novedad masiva para
	 * Empleadores.
	 * 
	 * @param idSolicitudNovedad
	 *            id de la solicitudNovedad.
	 * @param empleadores
	 *            Empleadores asociados a la solicitud.
	 */
	@POST
	@Path("{idSolicitudNovedad}/almacenarSolicitudNovedadMasiva")
	public void almacenarSolicitudNovedadMasiva(@NotNull @PathParam("idSolicitudNovedad") Long idSolicitudNovedad,
			@NotNull List<Long> idEmpleadores);

	/**
	 * Operación para Inactivar Automáticamente Beneficios Ley 1429 de 2010
	 * 
	 * @param empleadores
	 *            Empleadores a inactivar masivamente.
	 */
	@POST
	@Path("{tipoBeneficio}/inactivarBeneficios")
	public void inactivarBeneficios(@NotNull @PathParam("tipoBeneficio") TipoBeneficioEnum tipoBeneficio,
			@NotNull List<Long> idEmpleadores);

	/**
	 * Método encargado de consultar los empleadores con estado inactivo y con
	 * fecha de Retiro asociada
	 * 
	 * @return empleadores a inactivar masivamente.
	 */
	@POST
	@Path("/consultarEmpleadoresInactivarCuentaWeb")
	public List<Long> consultarEmpleadoresInactivarCuentaWeb(List<UsuarioDTO> usuarios);

	/**
	 * Operación para Consultar los nombres de usuario de los empleadores,
	 * comprendidos por el tipo y número de identificación.
	 * 
	 * @param empleadores
	 *            Empleadores a inactivar masivamente.
	 * @return Lista con los nombres de usuario de cada empleador.
	 */
	@POST
	@Path("/consultarNombreUsuarioEmpleadores")
	public List<String> consultarNombreUsuarioEmpleadores(@NotNull List<Long> idEmpleadores);

	/**
	 * Operación para consultar un Beneficio Empleador por Tipo.
	 * 
	 * @param idEmpleador
	 *            Identificación del Empleador
	 * @param tipoBeneficio
	 *            Tipo de Beneficio a Consultar.
	 * @return Beneficio Empleador
	 */
	@GET
	@Path("/{idEmpleador}/consultarBeneficioEmpleador")
	public BeneficioEmpleadorModeloDTO consultarBeneficioEmpleador(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			@NotNull @QueryParam("tipoBeneficio") TipoBeneficioEnum tipoBeneficio);

	/**
	 * Actualiza el BeneficioEmpleador enviado en
	 * <code>beneficioEmpleador</code>.
	 * 
	 * @param beneficioEmpleador
	 */
	@POST
	@Path("/actualizarBeneficioEmpleador")
	public void actualizarBeneficioEmpleador(@NotNull BeneficioEmpleadorModeloDTO beneficioEmpleador);

	/**
	 * Servicio que consulta un empleador por id,
	 * 
	 * @return empleadorModeloDTO dto del empleador.
	 */
	@GET
	@Path("/{idEmpleador}/consultarEmpleadorId")
	public EmpleadorModeloDTO consultarEmpleadorId(@PathParam("idEmpleador") Long idEmpleador);

	/**
	 * Servicio encargado de actualizar un empelador.
	 * 
	 * @param empleadorModeloDTO
	 *            dto a actualizar.
	 */
	@POST
	@Path("modificarEmpleador")
	public void modificarEmpleador(@NotNull EmpleadorModeloDTO empleadorModeloDTO);

	/**
	 * Servicio encargado de actualizar un empelador.
	 * 
	 * @param empleadorModeloDTO
	 *            dto a actualizar.
	 */
	@POST
	@Path("consultarEmailEmpleadores")
	public List<String> consultarEmailEmpleadores(@NotNull List<Long> idEmpleadores);

	/**
	 * Servicio encargado de consultar empleador por tipo y número de
	 * identificación.
	 * 
	 * @param tipoIdentificacion
	 *            tipo de identificación.
	 * @param numeroIdentificacion
	 *            numero de identificación.
	 * @return empleador encontrado o null si no se encontró.
	 */
	@GET
	@Path("consultarEmpleadorTipoNumero/{tipoIdentificacion}/{numeroIdentificacion}")
	public EmpleadorModeloDTO consultarEmpleadorTipoNumero(
			@PathParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@PathParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio encargado de verificar si existe un empleador asociado a una
	 * empresa dada.
	 * 
	 * @param personaModeloDTO
	 *            la persona (que contiene el tipo y número de identificación, y
	 *            dígito de verificación) con la cual se va a realizar la
	 *            búsqueda.
	 * @return EmpleadorModeloDTO si encuentra el resultado, de lo contrario
	 *         devuelve null.
	 */
	@POST
	@Path("/verificarExisteEmpleadorAsociado")
	public EmpleadorModeloDTO verificarExisteEmpleadorAsociado(PersonaModeloDTO personaModeloDTO);

	/**
	 * Inactiva una lista de empleadores.
	 * 
	 * @param idEmpleadores
	 */
	@POST
	@Path("/inactivarEmpleadores")
	public void inactivarEmpleadores(@NotNull List<Long> idEmpleadores,
			@QueryParam("motivoDesafiliacion") MotivoDesafiliacionEnum motivoDesafiliacionEnum);

	/**
	 * Servicio encargado de consultar Empleadores con Cero trabajadores
	 * 
	 * @return Lista de identificadores de los empleadores a inactivar.
	 */
	@GET
	@Path("/consultarEmpleadoresCeroTrabajadores")
	public List<Long> consultarEmpleadoresCeroTrabajadores();

	/**
	 * Consulta empleadorer por los filtros contenido como paremetros
	 * 
	 * @param tipoIdentificacion
	 *            Tipo identificacion de la persona empleador
	 * @param numeroIdentificacion
	 *            Numero de identificacion de la persona empleador
	 * @param razonSocial
	 *            Razon social empleador
	 * @param estadoCaja
	 *            Estado con respecto a la caja del empleador
	 * @return Lista de empleadores que cumplen los criterios de consulta
	 */
	@GET
	@Path("/consultarEmpleadorEstadoCaja")
	public List<EmpleadorModeloDTO> consultarEmpleadorEstadoCaja(
	        @Context UriInfo uriInfo, @Context HttpServletResponse response,
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("razonSocial") String razonSocial, @NotNull @QueryParam("estadoCaja") EstadoEmpleadorEnum estadoCaja,
			@QueryParam("textoFiltro") String textoFiltro);

	@POST
	@Path("/actualizarEstadoEmpleadorPorAportes")
//	public void actualizarEstadoEmpleadorPorAportes(@QueryParam("idAportante") Long idAportante,
//			@QueryParam("nuevoEstado") EstadoEmpleadorEnum nuevoEstado);
	public void actualizarEstadoEmpleadorPorAportes(ActivacionEmpleadorDTO datosReintegro);

	/**
	 * Consulta el estado caja de un empleador por su tipo y numero de documento
	 * 
	 * @param valorTI
	 *            Tipo identificacion de busqueda
	 * @param valorNI
	 *            Numero identificacion de busqueda
	 * @param idEmpleador
	 *            Identificador empleador
	 * @return Estado de afiliacion respecto a la caja
	 * @throws IOException
	 */
	@GET
	@Path("/consultarEstadoCajaEmpleador")
	public EstadoDTO consultarEstadoCajaEmpleador(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum valorTI,
			@QueryParam("numeroIdentificacion") String valorNI) throws IOException;

	/**
	 * Método encargado de consultar ultima clasificacion del empleador
	 * 
	 * @param tipoIdentificacion,
	 *            tipo de identificación del empleador
	 * @param numeroIdentificacion,
	 *            número de identificación del empleador
	 * @return retorna la clasificación
	 */
	@GET
	@Path("/consultarUltimaClasificacionEmpleador")
	public ClasificacionEnum consultarUltimaClasificacionEmpleador(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio encargado de consultar la ubicación de notificación Judicial de
	 * una empresa.
	 * 
	 * @param idPersona,
	 *            id de la persona
	 * @return retorna la ubicación de notificación.
	 */
	@GET
	@Path("/consultarUbicacion")
	public UbicacionDTO consultarUbicacionEmpresa(@NotNull @QueryParam("idPersona") Long idPersona,
			@NotNull @QueryParam("tipoUbicacion") TipoUbicacionEnum tipoUbicacion);

	/**
	 * <b>Descripción</b>Método encargado de un crear representante legal o
	 * suplente para una empresa
	 *
	 * @param idEmpresa
	 *            Id de la empresa que se va a consultar
	 * @param representante
	 *            información del representante
	 * @param titular
	 *            valor booleano, true si es representante legal principal o
	 *            false si es representante legal suplente
	 * @return id de la persona (representante legal)
	 */
	@POST
	@Path("/{idEmpresa}/crearActualizarRepresentanteLegal")
	public Long crearActualizarRepresentanteLegal(@PathParam("idEmpresa") Long idEmpresa,
			@QueryParam("titular") Boolean titular, PersonaModeloDTO representante);

	/**
	 * Guarda la informacion del empleador enviado en <code>empleador</code>.
	 * 
	 * @param empleador
	 *            Es el empleador con nueva la información.
	 */
	@POST
	@Path("/guardarDatosEmpleador")
	public void guardarDatosEmpleador(EmpleadorModeloDTO empleadorDTO);

	/**
	 * Servicio encargado de consultar la ubicación de empresa por tipo
	 * 
	 * @param idPersona,
	 *            id de la persona
	 * @param tipoUbicacion,
	 *            tipo de ubicación
	 * @return retorna la lista de ubicaciones pertenecientes a la empresa
	 */
	@GET
	@Path("/consultarUbicacionEmpresaPorTipo/{idPersona}")
	public List<UbicacionDTO> consultarUbicacionEmpresaPorTipo(@NotNull @PathParam("idPersona") Long idPersona,
			@QueryParam("tipoUbicacion") List<TipoUbicacionEnum> tipoUbicacion);

	/**
	 * Servicio encargado de consultar la ultima solicitud de afiliación
	 * asociada al empleador, aprobada
	 * 
	 * @param idEmpleador
	 *            , id del empleador
	 * @return
	 */
	@GET
	@Path("/ultimaSolicitudAfiliacion/{idEmpleador}")
	public SolicitudDTO consultarUltimaSolicitudEmpleador(@PathParam("idEmpleador") Long idEmpleador);

	/**
	 * Anular solicitudes de una lista de empleadores.
	 * 
	 * @param idEmpleadores
	 */
	@POST
	@Path("/anularSolicitudes")
	public void anularSolictudesEmpleador(List<Long> idsEmpleadores);

	/**
	 * Metodo que permite actualizar la solicitud de afiliacion de la persona
	 * 
	 * @param idSolicitudGlobal,
	 *            identificador global de la solicitud
	 * @param solicitudDTO,
	 *            solicitud
	 */
	@PUT
	@Path("/solicitudAfiliacion/{idSolicitudGlobal}")
	public void actualizarSolicitudAfiliacion(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal,
			SolicitudDTO solicitudDTO);

	/**
	 * Servicio encargado de consultar la ubicación de un rol contacto empleador
	 * 
	 * @param idPersona,
	 *            Identificador de la persona que es empleador
	 * @param tipoRolContactoEmpleador,
	 *            tipo de co
	 * @return retorna la ubicación del rol Contacto empleador
	 */
	@GET
	@Path("/consultarUbicacionRolContactoEmpleador/{idPersona}")
	public UbicacionDTO consultarUbicacionRolContactoEmpleador(@NotNull @PathParam("idPersona") Long idPersona,
			@NotNull @QueryParam("tipoRolContactoEmpleador") TipoRolContactoEnum tipoRolContactoEmpleador);

	/**
	 * Obtiene el beneficio de Ley actual para el empleador
	 * 
	 * @param idEmpleador,
	 *            Id del empleador
	 * @return Tipo de Beneficio del empleador o null
	 */
	@GET
	@Path("/obtenerBeneficioEmpleador/{idEmpleador}")
	public TipoBeneficioEnum obtenerBeneficioEmpleador(@NotNull @PathParam("idEmpleador") Long idEmpleador);

	/**
	 * Obtiene los beneficios que tiene o ha tenido un empleador
	 * 
	 * @param idEmpleador
	 * @return
	 */
	@GET
	@Path("/obtenerBeneficiosEmpleador/{idEmpleador}")
	public List<BeneficioEmpleadorModeloDTO> obtenerBeneficiosEmpleadorTab(
			@NotNull @PathParam("idEmpleador") Long idEmpleador);

	/**
	 * Metodo que obtiene las variables adicionales de la cabecera de la vista
	 * 360
	 * 
	 * @param idEmpleador
	 * @return
	 */
	@GET
	@Path("/obtenerValoresAdicionalesCabeceraVista360/{tipoIdentificacion}/{numeroIdentificacion}")
	public EmpleadorModeloDTO obtenerValoresAdicionalesCabeceraVista360(
			@PathParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@PathParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Metodo para consulta de las solicitudes del empleador
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/obtenerSolicitudesEmpleador")
	public List<Vista360EmpleadorRespuestaDTO> obtenerSolicitudesEmpleador(Vista360EmpleadorBusquedaParamsDTO params);

	/**
	 * Metodo para obtener los comunicados asociados a un empleador
	 * 
	 * @param idEmpleador
	 * @return
	 */
	@GET
	@Path("/obtenerComunicadosSolicitudEmpleador/{idSolicitud}")
	public List<Vista360EmpleadorRespuestaDTO> obtenerComunicadosSolicitudEmpleador(
			@NotNull @PathParam("idSolicitud") Long idSolicitud);

	/**
     * <b>Descripción</b>Método encargado de consultar un empleador por el Id para la vista 360
     * <code>idEmpleador es el id del empleador en el sistema</code>
     * 
     * @param idEmpleador
     *            Id del empleador que se va a consultar
     * @return empleador de tipo Empleador
     */
    @GET
    @Path("/consultarEmpleador360/{idEmpleador}")
    public Empleador consultarEmpleador360(@PathParam("idEmpleador") Long idEmpleador);
    
    /**
     * Metodo que obtiene una entidad DatoTemporalSolicitud con la informacion temporal del empleador
     * @return DatoTemporalSolicitud
     */
    @GET
	@Path("/consultarInfoTemporalEmpleador")
    public DatoTemporalSolicitud consultarInfoTemporalEmpleador(@QueryParam("idSolicitudGlobal") Long idSolicitud);

	@GET
    @Path("/consultarPersonaEmpleador/{idEmpleador}")
    public Persona consultarPersonaEmpleador(@PathParam("idEmpleador") Long idEmpleador);

	// comunicados masivos
	@POST
	@Path("/consultarAfiliadosCertificadosMsivos/{idEmpleador}/{tipoCertificado}")
	public List<Map<String,Object>> consultarAfiliadosCertificadosMasivos(@NotNull @PathParam("idEmpleador") Long idEmpleador,@NotNull @PathParam("tipoCertificado") TipoCertificadoMasivoEnum tipoCertificado, @QueryParam("fechaDesde") Long fechaDesde, @QueryParam("fechaHasta") Long fechaHasta);

	@POST
	@Path("{tipoCertificado}/generarCertificadoMasivo")
	public Response generarCertificadosMasivos(List<Map<String,Object>> afiliados,@QueryParam("dirigido") String dirigidoA,@NotNull @QueryParam("idEmpleador") Long idEmpleador,@NotNull @PathParam("tipoCertificado") TipoCertificadoMasivoEnum tipoCertificado,@NotNull @QueryParam("esCargue") Boolean esCargueArchivo,@QueryParam("nombreArchivo") String nombreArchivo);

	@GET
	@Path("{idEmpleador}/consultarCertificadosMasivos")
	public List<ControlCertificadosMasivosDTO> consultarCertificadosMasivos(@PathParam("idEmpleador") Long idEmpleador);
}
