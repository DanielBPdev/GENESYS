package com.asopagos.personas.service;

import java.util.List;
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
import javax.ws.rs.core.UriInfo;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.dto.ComparacionFechasDefuncionYAporteDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.Vista360PersonaCabeceraDTO;
import com.asopagos.dto.modelo.AdministradorSubsidioModeloDTO;
import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import com.asopagos.dto.modelo.HistoricoActivacionAccesoModeloDTO;
import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.PersonaDetalleModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.personas.dto.ConsultaEstadoCajaPersonaDTO;
import com.asopagos.personas.dto.DetalleBeneficiarioGrupoFamiliarDTO;
import com.asopagos.personas.dto.Ubicacion360DTO;
import com.asopagos.personas.dto.PaisDTO;
import com.asopagos.personas.dto.DetalleBeneficiarioDTO;
import com.asopagos.personas.dto.ConsultarPersonaProcesoOffcoreDTO;
import com.asopagos.dto.modelo.RegistroDespliegueAmbienteDTO;
import com.asopagos.dto.ActualizarExclusionSumatoriaSalarioDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la consulta de Personas <b>Historia de Usuario:</b> 104
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
@Path("personas")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface PersonasService {

	/**
	 * <b>Descripción</b>Metodo que se encarga validar el digito de verificación
	 * <code>idEmpleador, con este id se consultara el Empleador, listadoSucursales
	     * son las sucursales que se asignarán al empleador</code><br/>
	 *
	 * @param persona
	 *                Es la persona a la cual se le aplica la validación
	 * @return true si la validación es exitosa
	 *
	 */
	@POST
	@Path("/validarDigitoVerificacion")
	public Boolean validarDigitoVerificacion(@NotNull Persona persona);

	/**
	 * <b>Descripción</b>Metodo que se encarga de consultar las personas por los
	 * filtros enviados
	 * <code>idEmpleador, con este id se consultara el Empleador, listadoSucursales
	     * son las sucursales que se asignarán al empleador</code><br/>
	 *
	 * @param persona
	 *                Es la persona a la cual se le aplica la validación
	 * @return true si la validación es exitosa
	 *
	 */

	/**
	 * <b>Descripción:</b>Metodo que se encarga de consultar las personas por
	 * los filtros enviados
	 * 
	 * @param valorTI,
	 *                         valor del tipo de documento
	 * @param valorNI,
	 *                         valor del numero de identificacion
	 * @param primerNombre,
	 *                         valor del primer nombre
	 * @param primerApellido,
	 *                         valor del primer apellido
	 * @param fechaNacimiento,
	 *                         fecha de nacimiento
	 * @return listado de personas que cumplen con los filtros de búsqueda
	 */
	@GET
	@Path("")
	public List<PersonaDTO> buscarPersonas(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum valorTI,
			@QueryParam("numeroIdentificacion") String valorNI, @QueryParam("primerNombre") String primerNombre,
			@QueryParam("primerApellido") String primerApellido, @QueryParam("fechaNacimiento") Long fechaNacimiento,
			@QueryParam("idEmpleador") Long idEmpleador, @QueryParam("segundoNombre") String segundoNombre,
			@QueryParam("segundoApellido") String segundoApellido, @QueryParam("esVista360Web") Boolean esVista360Web);

	/**
	 * <b>Descripción</b>Metodo que se encarga de consultar las personas por los
	 * filtros enviados
	 * <code>idEmpleador, con este id se consultara el Empleador, listadoSucursales
	     * son las sucursales que se asignarán al empleador</code><br/>
	 *
	 * @param persona
	 *                Es la persona a la cual se le aplica la validación
	 * @return true si la validación es exitosa
	 *
	 */

	/**
	 * <b>Descripción:</b> Método que se encarga de consultar una persona por el
	 * id.
	 * 
	 * @param idPersona
	 *                  id de la persona a consultar
	 * @return persona encontrada por el id.
	 */
	@GET
	@Path("/consultarPersona")
	public PersonaModeloDTO consultarPersona(@QueryParam("idPersona") Long idPersona);


	@GET
	@Path("/consultarBeneficiario")
	public DetalleBeneficiarioDTO consultarBeneficiario(@QueryParam("tipoIdentificacion") String tipoIdentificacion,
		@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * <b>Descripción:</b> Método que se encarga de consultar una persona por el
	 * id.
	 * 
	 * @param persona
	 *                a modificar.
	 */
	@POST
	@Path("")
	public void actualizarPersona(Persona persona);

	/**
	 * <b>Descripción:</b> Método que se encarga de consultar la ubicacion por
	 * el id
	 * 
	 * @param idUbicacion,
	 *                     identificador de la ubicacion
	 * @return ubicacion dto
	 */
	@GET
	@Path("/ubicacion/{idUbicacion}")
	public UbicacionDTO consultarUbicacion(@PathParam("idUbicacion") Long idUbicacion);

	/**
	 * <b>Descripción:</b> Método que se encarga de actualizar una Persona como
	 * llegue en el DTO, debe contener el Id- Novedades Persona
	 * 
	 * @param personaDTO
	 *                   persona a modificar.
	 */
	@POST
	@Path("/actualizarDatosPersona")
	public void actualizarDatosPersona(PersonaModeloDTO personaDTO);

	/**
	 * <b>Descripción:</b> Método encargado de consultar el Grupo Familiar por
	 * identificación.
	 * 
	 * @param idGrupoFamiliar
	 *                        Identificación del Grupo Familiar.
	 * @return GrupoFamiliarDTO
	 */
	@GET
	@Path("/consultarDatosPersona")
	public PersonaModeloDTO consultarDatosPersona(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * <b>Descripción:</b> Método encargado de consultar los Identificadores de
	 * las Personas que se les venció el certificado de Escolaridad.
	 * 
	 * @return Lista de Beneficiarios objeto de la novedad
	 */
	@GET
	@Path("/consultarVencimientoCertificadoEscolaridad")
	public List<BeneficiarioNovedadAutomaticaDTO> consultarVencimientoCertificadoEscolaridad();

	/**
	 * Inactivar Certificados Escolaridad
	 * 
	 * @param idPersonasInactivar
	 */
	@POST
	@Path("/inactivarCertificadoEscolaridad")
	public void inactivarCertificadoEscolaridad(@NotNull List<Long> idBeneficiariosInactivar);

	/**
	 * <b>Descripción:</b> Método encargado de consultar los Identificadores de
	 * las Personas que se debe inactivar su Cuenta Web
	 * 
	 * @return Lista de Identificadores de Personas.
	 */
	@GET
	@Path("/consultarPersonasInactivarCtaWeb")
	public List<Long> consultarPersonasInactivarCtaWeb();

	/**
	 * 
     * Servicio para actualizar la marca para la sumatoria de salarios de una persona
     * @param ActualizarExclusionSumatoriaSalarioDTO  Datos devueltos por la novedad realizada (relevante fechas de exclusion padre/conyuge)
     * @return true si fue actualizada la marca
	 * 
     */
	@POST
	@Path("/actualizarExclusionSumatoriaSalario")
	public ActualizarExclusionSumatoriaSalarioDTO actualizarExclusionSumatoriaSalario(@NotNull ActualizarExclusionSumatoriaSalarioDTO datosPersonaNovedadDTO);

	/**
	 * Construye el nombre de usuario de Keycloak, asociado a la personas.
	 * 
	 * @param idEmpleadores
	 * @return nombresUsuarioPersonas
	 */
	@POST
	@Path("/consultarNombreUsuarioPersonas")
	public List<String> consultarNombreUsuarioPersonas(@NotNull List<Long> idPersonas);

	/**
	 * <b>Descripción:</b> Método que se encarga de actualizar una Condición de
	 * Invalidez
	 * 
	 * @param personaDTO
	 *                   persona a modificar.
	 */
	@POST
	@Path("/actualizarCondicionInvalidez")
	public void actualizarCondicionInvalidez(@NotNull CondicionInvalidezModeloDTO condicionInvalidezModeloDTO);

	/**
	 * <b>Descripción:</b> Método que se encarga de consultar la condicion de
	 * invalidez asociada a una persona
	 * 
	 * @param numeroIdentificacion
	 *                             Número de identificación de la persona
	 * @param tipoIdentificacion
	 *                             Tipo de identificación de la persona
	 * @return CondicionInvalidezModeloDTO
	 */
	@GET
	@Path("/consultarCondicionInvalidez/{numeroIdentificacion}/{tipoIdentificacion}")
	public CondicionInvalidezModeloDTO consultarCondicionInvalidez(
			@PathParam("numeroIdentificacion") String numeroIdentificacion,
			@PathParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion);

	/**
	 * <b>Descripción:</b> Método que permite registrar una persona en la base
	 * de datos. De igual manera si la persona ya está registrada, retorna su
	 * id.
	 * 
	 * @param persona
	 *                el objeto de tipo persona que será persistido
	 * @return el id de la persona persistida
	 */
	@POST
	@Path("/crearPersona")
	public Long CrearPersona(PersonaModeloDTO personaModeloDTO);

	@POST
	@Path("/ConsultarAportePosteriorFechaFallecimiento")
	public Boolean ConsultarAportePosteriorFechaFallecimiento(
			@NotNull ComparacionFechasDefuncionYAporteDTO comparacion);

	@GET
	@Path("/consultarFechaNacimientoPersona/{numeroIdentificacion}/{tipoIdentificacion}")
	public Long consultarFechaNacimientoPersona(@PathParam("numeroIdentificacion") String numeroIdentificacion,
			@PathParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion);

	/**
	 * Consulta la informacion basica de una persona por los filtros asociados
	 * 
	 * @param valorTI
	 *                       Tipo de identificacion de busqueda
	 * @param valorNI
	 *                       Numero de identificacion de busqueda
	 * @param primerNombre
	 *                       Primer nombre de busqueda
	 * @param primerApellido
	 *                       Segundo nombre de busqueda
	 * @param estadoCaja
	 *                       Estado afiliado contra la caja
	 * @return Lista de personas que cumplen el filtro de busqueda
	 */
	@GET
	@Path("/consultarPersonasEstadoCaja")
	public List<PersonaDTO> consultarPersonasEstadoCaja(
			@Context UriInfo uriInfo, @Context HttpServletResponse response,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum valorTI,
			@QueryParam("numeroIdentificacion") String valorNI, @QueryParam("primerNombre") String primerNombre,
			@QueryParam("primerApellido") String primerApellido,
			@QueryParam("estadoCaja") EstadoAfiliadoEnum estadoCaja,
			@QueryParam("textoFiltro") String textoFiltro);

	/**
	 * Servicio que realiza el registro o actualización de miembro de hogar fovis
	 * 
	 * @param integranteHogarModeloDTO
	 *                                 Información del miembro de hogar
	 * @return Miembro de hogar creado o actualizado
	 */
	@POST
	@Path("/crearActualizarIntegranteHogar")
	public IntegranteHogarModeloDTO crearActualizarIntegranteHogar(IntegranteHogarModeloDTO integranteHogarModeloDTO);

	/**
	 * Servicio que ejecuta el sp de core a subsidio para sumatoria
	 */
	@GET
	@Path("/ejecutarSPCoreSubsidio")
	public void ejecutarSPCoreSubsidio();

	/**
	 * <b>Descripción:</b> Método que permite registrar las condiciones
	 * especiales asociadas a una persona en la base de datos.
	 * 
	 * @param condicionesEspeciales
	 *                              lista de las condiciones especiales asociadas a
	 *                              una persona
	 * @param idPersona
	 *                              id de la persona a la que se le asociaran las
	 *                              condiciones
	 *                              especiales
	 */
	@POST
	@Path("/registrarCondicionesEspecialesFOVIS/{idPersona}")
	public void registrarCondicionesEspecialesFOVIS(@NotNull List<NombreCondicionEspecialEnum> condicionesEspeciales,
			@PathParam("idPersona") Long idPersona);

	/**
	 * Servicio que crea o actualiza un registro en la tabla
	 * <code>JefeHogar</code>
	 * 
	 * @param jefeHogarDTO
	 *                     La información del registro a actualizar
	 * @return La información del registro actualizado
	 */
	@POST
	@Path("/crearActualizarJefeHogar")
	public JefeHogarModeloDTO crearActualizarJefeHogar(@NotNull JefeHogarModeloDTO jefeHogarDTO);

	/**
	 * Servicio que consulta la lista de integrantes del hogar, relacionados a
	 * un jefe de hogar -> 3.1.1.7
	 * 
	 * @param tipoIdentificacion
	 *                             Tipo de identificación del jefe de hogar
	 * @param numeroIdentificacion
	 *                             Número de identificación del jefe de hogar
	 * @return La lista de integrantes del hogar
	 */
	@GET
	@Path("/consultarListaIntegranteHogar")
	public List<IntegranteHogarModeloDTO> consultarListaIntegranteHogar(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@NotNull @QueryParam("idPostulacion") Long idPostulacion);

	/**
	 * Servicio que consulta un jefe de hogar, por identificación
	 * 
	 * @param tipoIdentificacion
	 *                             Tipo de identificación del jefe de hogar
	 * @param numeroIdentificacion
	 *                             Número de identificación del jefe de hogar
	 * @return Objeto <code>JefeHogarModeloDTO</code> con la información del
	 *         jefe de hogar
	 */
	@GET
	@Path("/consultarJefeHogar")
	JefeHogarModeloDTO consultarJefeHogar(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio que crea o actualiza un registro en la tabla
	 * <code>Ubicacion</code>
	 * 
	 * @param ubicacionDTO
	 *                     La información del registro a actualizar
	 * @return La información del registro actualizado
	 */
	@POST
	@Path("/crearActualizarUbicacion")
	UbicacionModeloDTO crearActualizarUbicacion(@NotNull UbicacionModeloDTO ubicacionDTO);

	/**
	 * Servicio encargado de registrar personas detalle.
	 * 
	 * @param personasDetalle
	 *                        Lista de personas detalle a registrar.
	 */
	@GET
	@Path("/registrarPersonasDetalle")
	public void registrarPersonasDetalle(List<PersonaDetalleModeloDTO> personasDetalle);

	/**
	 * Servicio que consulta un registro en <code>PersonaDetalle</code>
	 * 
	 * @param tipoIdentificacion
	 *                             Tipo de identificación de la persona
	 * @param numeroIdentificacion
	 *                             Número de identificación de la persona
	 * @return Objeto <code>PersonaDetalleModeloDTO</code> con la información
	 *         del detalle de la persona
	 */
	@GET
	@Path("/registrarPersonasDetalle")
	PersonaDetalleModeloDTO consultarPersonaDetalle(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio que actualiza una lista de registros en
	 * <code>PersonaDetalle</code>
	 * 
	 * @param listaPersonaDetalleDTO
	 *                               Lista de registros
	 */
	@POST
	@Path("/actualizarPersonaDetalle")
	void actualizarPersonaDetalle(@NotNull List<PersonaDetalleModeloDTO> listaPersonaDetalleDTO);

	/**
	 * Consulta el estado caja de una persona por su tipo y numero de documento
	 * y el identificador del empleador
	 * 
	 * @param valorTI
	 *                    Tipo identificacion de busqueda
	 * @param valorNI
	 *                    Numero identificacion de busqueda
	 * @param idEmpleador
	 *                    Identificador empleador
	 * @return Estado de afiliacion respecto a la caja
	 */
	@GET
	@Path("/consultarEstadoCajaPersona")
	public EstadoAfiliadoEnum consultarEstadoCajaPersona(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum valorTI,
			@QueryParam("numeroIdentificacion") String valorNI, @QueryParam("idEmpleador") Long idEmpleador);

	/**
	 * Servicio encargado de consultar el estado de la caja para cada una de las
	 * personas
	 * 
	 * @param lstPersonas,
	 *                     dto que contiene el listado de personas a consultar el
	 *                     estado
	 * @return retorna el listado de personas con el estado de afiliacion
	 */
	@POST
	@Path("/buscarEstadoCajaPersonasMasivo")
	public List<ConsultaEstadoCajaPersonaDTO> buscarEstadoCajaPersonasMasivo(
			@NotNull List<ConsultaEstadoCajaPersonaDTO> lstPersonas);

	/**
	 * Servicio que consulta la información de una persona jefe de hogar, por
	 * identificador
	 * 
	 * @param idJefeHogar
	 *                    Identificador único del jefe del hogar
	 * @return Objeto <code>PersonaModeloDTO</code> con la información de la
	 *         persona jefe de hogar
	 */
	@GET
	@Path("/consultarPersonaJefeHogar")
	PersonaModeloDTO consultarPersonaJefeHogar(@QueryParam("idJefeHogar") Long idJefeHogar);

	/**
	 * Servicio encargado de consultar personas sin detalle
	 * 
	 * @param tipoIdentificacion,
	 *                              Tipo de identificación de la persona
	 * @param numeroIdentificacion,
	 *                              Número de identificación de la persona
	 * @param primerNombre,
	 *                              primer nombre de la persona
	 * @param primerApellido,primer
	 *                              apellido de la persona
	 * @param segundoNombre,segundo
	 *                              nombre de la persona
	 * @param segundoApellido,
	 *                              segundo Apellido de la persona
	 * @param segundoApellido,
	 *                              Razón social
	 * @return retorna el listado de personas DTO
	 */
	@GET
	@Path("/buscarPersonasSinDetalle")
	public List<PersonaDTO> buscarPersonasSinDetalle(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("primerNombre") String primerNombre, @QueryParam("primerApellido") String primerApellido,
			@QueryParam("segundoNombre") String segundoNombre, @QueryParam("segundoApellido") String segundoApellido,
			@QueryParam("razonSocial") String razonSocial);

	/**
	 * Servicio encargado de consultar
	 * 
	 * @param tipoIdentificacion,
	 *                              tipo de identificación de la persona
	 * @param numeroIdentificacion,
	 *                              número de identificación de la persona
	 * @param razonSocial,
	 *                              razón social a consultar de la persona
	 * @return retorna la lista de personas encontradas
	 */
	@GET
	@Path("/consultarPersonaRazonSocial")
	public List<PersonaDTO> consultarPersonaRazonSocial(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("razonSocial") String razonSocial);

	/**
	 * <b>Descripción:</b>Servicio que consulta el administrador o los
	 * administradores de subsidio que coincidan con los filtros de busqueda.
	 * Solo se busca el administrador de subsidio por medio de pago efectivo ya
	 * que se devuelve el codigo del municipio</br>
	 * 
	 * @param tipoIdentificacion
	 *                             tipo de identificación del administrador de
	 *                             subsidio
	 * @param numeroIdentificacion
	 *                             número de identificación del administrador de
	 *                             subsidio.
	 * @param primerNombre
	 *                             primer nombre del administrador de subsidio.
	 * @param segundoNombre
	 *                             segundo nombre del administrador de subsidio.
	 * @param primerApellido
	 *                             primer apellido del administrador de subsido.
	 * @param segundoApellido
	 *                             segundo apellido del administrador de subsidio.
	 * @return administrador de subsidio o administradores de subsidios que
	 *         coincidan con los filtros.
	 * @author mosorio
	 */
	@GET
	@Path("/consultarAdministradorSubsidio")
	public List<AdministradorSubsidioModeloDTO> consultarAdministradorSubsidio(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("primerNombre") String primerNombre, @QueryParam("segundoNombre") String segundoNombre,
			@QueryParam("primerApellido") String primerApellido, @QueryParam("segundoApellido") String segundoApellido);

	/**
	 * Servicio que crea nuevos registros de medio de pago, de acuerdo al tipo
	 * de pago
	 * 
	 * @param medioDePagoModeloDTO
	 *                             Información del pago a persistir
	 * @return La información del pago actualizada
	 */
	@POST
	@Path("/guardarMedioDePago")
	MedioDePagoModeloDTO guardarMedioDePago(@NotNull MedioDePagoModeloDTO medioDePagoModeloDTO);

	/**
	 * Servicio encargado de sonultar la ubicación de la persona
	 * 
	 * @param idPersona,
	 *                   id de la personas a consultar la ubicación
	 * @return retorna la ubicación de la persona
	 */
	@GET
	@Path("/consultarUbicacionPersona")
	public UbicacionDTO consultarUbicacionPersona(@NotNull @QueryParam("idPersona") Long idPersona);

	/**
	 * Obtiene el numero de trabajadores asociado a una empresa
	 * 
	 * @param idEmpleador,
	 *                     Id del empleador
	 * @return Número de trabajadores asociado
	 */
	@GET
	@Path("/obtenerTrabajadoreActivosEmpleador/{idEmpleador}")
	public Long obtenerTrabajadoreActivosEmpleador(@NotNull @PathParam("idEmpleador") Long idEmpleador);

	/**
	 * <b>Descripción:</b> Método encargado de consultar datos de personas por
	 * tipo y lista de numeros identificacion
	 * 
	 * @param tipoIdentificacion
	 *                             Tipo identificacion
	 * @param numeroIdentificacion
	 *                             Lista Numero identificaicon
	 * @return Lista de personas
	 */
	@GET
	@Path("/consultarDatosPersonaListNumeroIdentificacion")
	public List<PersonaModeloDTO> consultarDatosPersonaListNumeroIdentificacion(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") List<String> numeroIdentificacion);

	/**
	 * Metodo que contiene la cabecera de la vista 360
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	@GET
	@Path("/cabeceraVista360Persona")
	public Vista360PersonaCabeceraDTO cabeceraVista360Persona(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * <b>Descripción:</b>Servicio que consulta el administrador o los
	 * administradores de subsidio que coincidan con los filtros de busqueda,
	 * sin tener en cuenta el medio de pago</br>
	 * 
	 * @param tipoIdentificacion
	 *                             tipo de identificación del administrador de
	 *                             subsidio
	 * @param numeroIdentificacion
	 *                             número de identificación del administrador de
	 *                             subsidio.
	 * @param primerNombre
	 *                             primer nombre del administrador de subsidio.
	 * @param segundoNombre
	 *                             segundo nombre del administrador de subsidio.
	 * @param primerApellido
	 *                             primer apellido del administrador de subsido.
	 * @param segundoApellido
	 *                             segundo apellido del administrador de subsidio.
	 * @return administrador de subsidio o administradores de subsidios que
	 *         coincidan con los filtros.
	 * @author mosorio
	 */
	@GET
	@Path("/consultarAdministradorSubsidioGeneral")
	public List<AdministradorSubsidioModeloDTO> consultarAdministradorSubsidioGeneral(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("primerNombre") String primerNombre, @QueryParam("segundoNombre") String segundoNombre,
			@QueryParam("primerApellido") String primerApellido, @QueryParam("segundoApellido") String segundoApellido);

	/**
	 * <b>Descripción:</b> Servicio encargado de buscar el detalle de un
	 * beneficiario dado su grupo familiar y su parentezco con el afiliado
	 * principal
	 * 
	 * @param idGrupo
	 *                             identificador del grupo al cual pertence el
	 *                             beneficiario.
	 * 
	 * @param parentezco
	 *                             parentezco con el afiliado principal.
	 * 
	 * @param tipoIdentificacion
	 *                             tipo de identificación del beneficiario.
	 * 
	 * @param numeroIdentificacion
	 *                             número de identificacion del beneficiario.
	 * 
	 * @return DetalleBeneficiarioGrupoFamiliarDTO con el detalle del
	 *         beneficiario.
	 * 
	 * @author squintero
	 */
	@GET
	@Path("/obtenerInfoDetalladaBeneficiarioGrupo")
	public DetalleBeneficiarioGrupoFamiliarDTO obtenerInfoDetalladaBeneficiarioGrupo(
			@NotNull @QueryParam("idGrupo") Long idGrupo,
			@NotNull @QueryParam("parentezco") ClasificacionEnum parentezco,
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio encargado de consultar la ubicación de la persona para las
	 * vistas 360
	 * 
	 * @param idPersona,
	 *                   id de la personas a consultar la ubicación
	 * @return Ubicacion360DTO retorna la ubicación de la persona
	 * 
	 * @author squintero
	 */
	@GET
	@Path("/consultarUbicacionPersona360")
	public Ubicacion360DTO consultarUbicacionPersona360(@NotNull @QueryParam("idPersona") Long idPersona);

	/**
	 * Servicio que consulta la información de una persona registrada en la CCF
	 * como aportante
	 * 
	 * @param tipoAportante
	 *                             Tipo de aportante
	 * @param tipoIdentificacion
	 *                             Tipo de identificación
	 * @param numeroIdentificacion
	 *                             Número de identificación
	 * @param razonSocial
	 *                             Razón social
	 * @param primerNombre
	 *                             Primer nombre
	 * @param segundoNombre
	 *                             Segundo nombre
	 * @param primerApellido
	 *                             Primer apellido
	 * @param segundoApellido
	 *                             Segundo apellido
	 * @return La lista de aportantes que cumplen con los criterios de búsqueda
	 */
	@GET
	@Path("/consultarAportantesCaja")
	List<PersonaDTO> consultarAportantesCaja(
			@QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("razonSocial") String razonSocial, @QueryParam("primerNombre") String primerNombre,
			@QueryParam("segundoNombre") String segundoNombre, @QueryParam("primerApellido") String primerApellido,
			@QueryParam("segundoApellido") String segundoApellido);

	/**
	 * Servicio encargado de registrar Historico de activacion.
	 * 
	 * @param activacionAcceso
	 *                         modificacion a registrar
	 */
	@POST
	@Path("/registrarHistoricoActivacion")
	public void registrarHistoricoActivacionAcceso(HistoricoActivacionAccesoModeloDTO activacionAcceso);

	/**
	 * Servicio encargado de registrar personas detalle.
	 * 
	 * @param personasDetalle
	 *                        Lista de personas detalle a registrar.
	 */
	@GET
	@Path("/consultarHistoricoActivacion")
	public List<HistoricoActivacionAccesoModeloDTO> consultarHistoricoActivacion(@NotNull @QueryParam("id") Long id);

	/**
	 * Servicio encargado de consultar el nombre de un pais
	 * 
	 * @param idPais
	 *               Lista de personas detalle a registrar.
	 */
	@GET
	@Path("/obtenerNombrePais/{idPais}")
	public PaisDTO obtenerNombrePais(@NotNull @PathParam("idPais") Long idPais);

	/**
	 * Servicio encargado de consultar un pais por el codigo
	 *
	 * @param codigoPais codigo de pais
	 * @return PaisDTO
	 */
	@GET
	@Path("/consultarPaisPorCodigo/{codigoPais}")
	public PaisDTO consultarPaisPorCodigo(@NotNull @PathParam("codigoPais") Long codigoPais);

	/**
     * Servicio para la consulta de los datos de una persona en el proceso off-core
     *
     * @param tipoIdentificacion   Tipo de Identificación de la persona
     * @param numeroIdentificacion Número de Identificación de la persona
     * @return Datos asociados a la Persona.
     */
    @GET
    @Path("/consultarPersonaProcesoOffcore")
    public ConsultarPersonaProcesoOffcoreDTO consultarPersonaProcesoOffcore(
            @NotNull @QueryParam("tipoIdentificacion") String tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

	@GET
	@Path("/consultarVersionamiento")
	public RegistroDespliegueAmbienteDTO consultarVersionamiento();

	@POST
	@Path("/registrarTarjetaExpedicion")
	public MedioDePagoModeloDTO registrarTarjetaExpedicion(@NotNull MedioDePagoModeloDTO medioDePagoModeloDTO);

	@POST
	@Path("/reemplazarMedioDePagoGrupoFamiliar")
	public void reemplazarMedioDePagoGrupoFamiliar(@NotNull List<Long> idGruposFamiliares, @NotNull @QueryParam("idMedioPagoTarjeta") Long idMedioPagoTarjeta);

	@POST
	@Path("/registrarActualizacionTarjetaGrupoFamiliar")
	public void registrarActualizacionTarjetaGrupoFamiliar(@QueryParam("idGrupo") Long idGrupo, @NotNull MedioDePagoModeloDTO medioDePagoModeloDTO);
}
