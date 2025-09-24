package com.asopagos.afiliados.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.asopagos.afiliados.dto.ActivacionAfiliadoDTO;
import com.asopagos.afiliados.dto.AfiliadoIndependienteVista360DTO;
import com.asopagos.afiliados.dto.AfiliadoPensionadoVista360DTO;
import com.asopagos.afiliados.dto.CriteriosConsultaModeloInfraestructuraDTO;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.afiliados.dto.HistoricoAfiBeneficiario360DTO;
import com.asopagos.afiliados.dto.InfoAfiliadoRespectoEmpleadorDTO;
import com.asopagos.afiliados.dto.InfoRelacionLaboral360DTO;
import com.asopagos.afiliados.dto.PresentacionResultadoModeloInfraestructuraDTO;
import com.asopagos.afiliados.dto.RespuestaServicioInfraestructuraDTO;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.afiliados.dto.SolicitudGlobalPersonaDTO;
import com.asopagos.dto.AfiliacionJefeHogarDTO;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.AfiliadoSolicitudDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.BeneficiarioHijoPadreDTO;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.dto.CategoriaDTO;
import com.asopagos.dto.CertificadoEscolarBeneficiarioDTO;
import com.asopagos.dto.ConsultaEstadoAfiliacionDTO;
import com.asopagos.dto.ConsultarAfiliadoOutDTO;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import com.asopagos.dto.GrupoFamiliarDTO;
import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.InformacionPersonaCompletaDTO;
import com.asopagos.dto.InformacionBeneficiarioCompletaDTO;
import com.asopagos.dto.ConsultaBeneficiarioRequestDTO;
import com.asopagos.dto.PersonaEmpresaDTO;
import com.asopagos.dto.PersonaRetiroNovedadAutomaticaDTO;
import com.asopagos.dto.TrabajadorEmpleadorDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import com.asopagos.dto.modelo.ExpulsionSubsanadaModeloDTO;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import com.asopagos.dto.modelo.InfraestructuraModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.NovedadDetalleModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SitioPagoModeloDTO;
import com.asopagos.dto.modelo.TipoInfraestructuraModeloDTO;
import com.asopagos.dto.modelo.TipoTenenciaModeloDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoTarjetaEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.entidades.ccf.personas.PuebloIndigena;
import com.asopagos.entidades.ccf.personas.Resguardo;
import com.asopagos.dto.RolafiliadoNovedadAutomaticaDTO;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.dto.RespuestaPaginacionDTO;
import javax.ws.rs.core.Response; 
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de empleadores <b>Módulo:</b> Asopagos - transversal<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 *
 */

@Path("afiliados")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliadosService {

    /**
     * Metodo encargado de consultar los afiliados dependendo de los parametros
     * enviados
     * 
     * @param tipoIdentificacion,
     *        tipo documentos
     * @param numeroIdentificacion,
     *        numero documento
     * @param primerNombre,
     *        primer nombre
     * @param primerApellido,
     *        primer apellido
     * @param fechaNacimiento,
     *        fecha de nacimiento
     * @param tipoAfiliado,
     *        tipo de afiliado
     * @return Listado de personas que cumplen con los parametros de busqueda
     */
    @GET
    @Path("/search")
    public List<PersonaDTO> buscarAfiliados(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("primerNombre") String primerNombre,
            @QueryParam("primerApellido") String primerApellido, @QueryParam("fechaNacimiento") Long fechaNacimiento,
            @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado);

    /**
     * Metodo encargado de actualizar un beneficiario masivamente
     *
     *
     * @param inDTO,
     *        afiliado a actualizar
     */
    @PUT
    @Path("/actualizarBeneficiariomasivamente/{idGrupoFamiliar}/{idAfiliado}")
    public void actualizarBeneficiariomasivamente(@NotNull @PathParam("idGrupoFamiliar") Long idGrupoFamiliar,@NotNull @PathParam("idAfiliado") Long idAfiliado);

    /**
     * Metodo encargado de crear afiliados a la caja de compenscion familiar
     * 
     * @param inDTO,
     *        Afiliado a Ingresar
     * @param userDTO,
     *        Usuario del sistema
     * @return el identificador dle afiliado en el sistema
     */
    @POST
    @Path("")
    public AfiliadoInDTO crearAfiliado(AfiliadoInDTO inDTO);

    /**
     * Metodo encargado de actualizar un afiliado de la cada de compensacion
     * familiar
     * 
     * @param inDTO,
     *        afiliado a actualizar
     */
    @PUT
    @Path("/{idAfiliado}")
    public void actualizarAfiliado(@NotNull @PathParam("idAfiliado") Long idAfiliado, AfiliadoInDTO inDTO);

    /**
     * Metodo encargado de consultar los datos de un afiliado
     * 
     * @param tipoIdentificacion,
     *        tipo de identificacion
     * @param numeroIdentificacion,
     *        numero de idnetificacion
     * @return datos identificacion
     */
    @GET
    @Path("")
    public ConsultarAfiliadoOutDTO consultarAfiliado(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("ubicacionesIdentificacion") Boolean ubicacionesIdentificacion);

    /**
     * Metodo que retorna el listado de trabajadores asociados a un empleador
     * @param idEmpleador
     *        Identificador empleador
     * @param estado
     *        Estado de afiliacion de los trabajadores
     * @param idEmpresa
     *        Identificador empresa
     * @return Listado de trabajadores o lista vacia
     */
    @GET
    @Path("/trabajadores")
    public List<TrabajadorEmpleadorDTO> consultarTrabajadoresEmpleador(@QueryParam("idEmpleador") Long idEmpleador,
            @QueryParam("estado") EstadoAfiliadoEnum estado, @QueryParam("idEmpresa") Long idEmpresa);

    @GET
    @Path("/trabajadores2")
    public List<TrabajadorEmpleadorDTO> consultarTrabajadoresEmpleador2(@QueryParam("idEmpleador") Long idEmpleador,
            @QueryParam("idEmpresa") Long idEmpresa,@Context UriInfo uriInfo, @Context HttpServletResponse response);

    @GET
    @Path("/trabajadores2Conteo")
    public Integer consultarTrabajadoresEmpleador2Conteo(@QueryParam("idEmpleador") Long idEmpleador,
            @QueryParam("idEmpresa") Long idEmpresa);

    /**
     * Metodo que determina la categoria de un Afiliado a la caja de
     * compensacion familiar
     * 
     * @param inDTO,
     *        Datos basicos de identificacion
     * @return Categoria del Afiliado
     */
    @POST
    @Path("/calcularCategoriasAfiliado")
    public Map<TipoAfiliadoEnum, CategoriaPersonaEnum> calcularCategoriasAfiliado(CategoriaDTO categoriaDTO);

    /**
     * Método encargado de guardar los datos de identificacion y ubicacion de la
     * persona
     * 
     * @param inDTO,
     *        identificacion y ubicacion
     */
    @POST
    @Path("/datosIdentificacionYUbicacion")
    public void guardarDatosIdentificacionYUbicacion(IdentificacionUbicacionPersonaDTO inDTO);

    /**
     * Metodo encargado de guardar la informacion laboral del trabajador
     * 
     * @param inDTO,
     *        informacion laboral trabajador
     */
    @POST
    @Path("/informacionLaboral")
    public void guardarInformacionLaboral(InformacionLaboralTrabajadorDTO inDTO);

    /**
     * Método encargado de consultar las tarjetas del afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param estado,
     *        estado de la tarjeta
     * @return
     */
    @GET
    @Path("/{idAfiliado}/tarjetas")
    public List<String> consultarTarjetasAfiliado(@PathParam("idAfiliado") Long idAfiliado, @QueryParam("estado") EstadoTarjetaEnum estado);

    /**
     * Metodo encargado de consultar el grupo familiar de un afiliado
     * 
     * @param idAfiliado,
     *        afiliado a consultar el grupo familir
     * @return el grupo familiar
     */
    @GET
    @Path("/{idAfiliado}/gruposFamiliares")
    public List<GrupoFamiliarDTO> consultarGrupoFamiliar(@PathParam("idAfiliado") Long idAfiliado);

    /**
     * Método encargado de crear un grupo familiar a un afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param grupoFamiliarDTO,
     *        grupo familiar del afiliado
     * @return identificador del grupo familiar
     */

    /**
     * Método encargado de crear el grupo familiar para el afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param grupoFamiliarDTO,
     *        grupo familiar del afiliado
     * @return GrupoFamiliarDTO
     */
    @POST
    @Path("/{idAfiliado}/gruposFamiliares")
    public GrupoFamiliarDTO crearGrupoFamiliar(@NotNull @PathParam("idAfiliado") Long idAfiliado, GrupoFamiliarDTO grupoFamiliarDTO);

    /**
     * Método encargado de actualizar el grupo familiar para el afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param grupoFamiliarDTO,
     *        grupo familiar del afiliado
     * @return identificador del grupo familiar
     */
    @PUT
    @Path("/{idAfiliado}/gruposFamiliares")
    public void actualizarGrupoFamiliar(@PathParam("idAfiliado") Long idAfiliado, GrupoFamiliarDTO grupoFamiliarDTO);

    /**
     * Método encargado de eliminar el grupo familiar de un afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param idGrupoFamiliar,
     *        identificador del grupo familiar
     */
    @DELETE
    @Path("/{idAfiliado}/gruposFamiliares/{idGrupoFamiliar}")
    public void eliminarGrupoFamiliar(@NotNull @PathParam("idAfiliado") Long idAfiliado,
            @NotNull @PathParam("idGrupoFamiliar") Long idGrupoFamiliar);

    /**
     * Metodo encargado de registrar beneficiarios a un afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param beneficiarioDTO,
     *        beneficiario a agregar
     * @return identificador del beneficiario
     */
    @POST
    @Path("/{idAfiliado}/beneficiarios")
    public Long registrarBeneficiario(@NotNull @PathParam("idAfiliado") Long idAfiliado, BeneficiarioDTO beneficiarioDTO);

    /**
     * Metodo encargado de registrar beneficiarios a un afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param beneficiarioDTO,
     *        beneficiario a agregar
     * @return identificador del beneficiario
     */
    @POST
    @Path("/{idAfiliado}/beneficiariosMultiple")
    public List<Long> registrarBeneficiarios(@NotNull @PathParam("idAfiliado") Long idAfiliado,
            @NotNull List<BeneficiarioDTO> beneficiariosDTO);

    /**
     * Metodo encargado de registrar beneficiarios a un afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param beneficiarioDTO,
     *        beneficiario a agregar
     * @return identificador del beneficiario
     */
    @PUT
    @Path("/{idAfiliado}/beneficiarios/desasociar/{idBeneficiario}")
    public void desasociarBeneficiario(@NotNull @PathParam("idAfiliado") Long idAfiliado,
            @NotNull @PathParam("idBeneficiario") Long idBeneficiario);

    /**
     * Metodo encargado de registrar la informacion de beneficiario conyugue
     * 
     * @param inDTO,
     *        identificacion ubicacion persona
     * @return identificador de conyugue
     */
    @POST
    @Path("/{idAfiliado}/beneficiarios/conyugue")
    public Long registrarInformacionBeneficiarioConyugue(@NotNull @PathParam("idAfiliado") Long idAfiliado,
            IdentificacionUbicacionPersonaDTO inDTO);

    /**
     * Metodo encargado de registrar la informacion del beneficiario hijo -
     * padre
     * 
     * @param inDTO,beneficiario
     *        hijo - padre
     * @return identificador del beneficiario
     */
    @POST
    @Path("/{idAfiliado}/beneficiarios/hijos")
    public Long registrarInformacionBeneficiarioHijoPadre(@NotNull @PathParam("idAfiliado") Long idAfiliado,
            BeneficiarioHijoPadreDTO inDTO);

    /**
     * Metodo encargado de consultar beneficiaros
     * 
     * @param idAfiliado,
     *        identificador del Afiliado
     * @return Listado de Beneficiarios
     */
    @GET
    @Path("/{idAfiliado}/beneficiarios")
    public List<BeneficiarioDTO> consultarBeneficiarios(@PathParam("idAfiliado") Long idAfiliado,@QueryParam("sinDesafiliacion") Boolean sinDesafiliacion);
    @GET
    @Path("/{idAfiliado}/beneficiariosMismaFecha")
    public List<BeneficiarioDTO> consultarBeneficiariosMismaFecha(@PathParam("idAfiliado") Long idAfiliado,@QueryParam("sinDesafiliacion") Boolean sinDesafiliacion);

    /**
     * Metodo encargado de consultar beneficiaros
     * 
     * @param idAfiliado,
     *        identificador del Afiliado
     * @return Listado de Beneficiarios
     */
    @GET
    @Path("/{idAfiliado}/beneficiariosSolicitud")
    public List<BeneficiarioDTO> consultarBeneficiariosSolicitud(@PathParam("idAfiliado") Long idAfiliado,
            @QueryParam("sinDesafiliacion") Boolean sinDesafiliacion, @QueryParam("idSolicitud") Long idSolicitud);
    
    /**
     * Metodo encargado de asociar el beneficiario a un grupo familiar
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param idGrupoFamiliar,
     *        identificador del grupo familiar
     * @param inDTO,
     *        datos basicos de identificacion
     */
    @POST
    @Path("/{idAfiliado}/gruposFamiliares/{idGrupoFamiliar}/asociarBeneficiario")
    public void asociarBeneficiarioAGrupoFamiliar(@PathParam("idAfiliado") Long idAfiliado,
            @PathParam("idGrupoFamiliar") Long idGrupoFamiliar, DatosBasicosIdentificacionDTO inDTO);

    /**
     * Metodo encargado de eliminar un beneficiario
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param inDTO,
     *        datos basicos de identificacion
     */
    @DELETE
    @Path("/{idAfiliado}/beneficiarios/eliminar")
    public void eliminarBeneficiario(@PathParam("idAfiliado") Long idAfiliado, DatosBasicosIdentificacionDTO inDTO);

    /**
     * Método que se encarga de actualizar el campo "estadoBeneficiarioAfiliado"
     * del benficiario
     * 
     * @param idBeneficiario
     * @param estado
     */
    @PUT
    @Path("/{idBeneficiario}/beneficiario/estado")
    public void actualizarEstadoBeneficiario(@PathParam("idBeneficiario") Long idBeneficiario, EstadoAfiliadoEnum estado,
            @QueryParam("motivoDesafiliacion") MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion);

    /**
     * Metodo encargo de actualizar el estado del rolAfiliado
     * 
     * @param idRolAfiliado,
     *        rol afiliado a actualizar el estado
     * @param estado,
     *        con que actualizara el estado rolAfiliado
     */
    @PUT
    @Path("/rolAfiliado/{idRolAfiliado}/estado")
    public void actualizarEstadoRolAfiliado(@NotNull @PathParam("idRolAfiliado") Long idRolAfiliado, @NotNull EstadoAfiliadoEnum estado);

    /**
     * Metodo que se encarga de actualizar la fecha de afiliacion del rol
     * afiliado
     * 
     * @param idRolAfiliado,
     *        id rol afiliado al cual se le actuaizara la fecha de
     *        afiliacion
     * @param fecha,
     *        fecha de afiliacion con la que quedara el rol afiliado
     */
    @PUT
    @Path("/rolAfiliado/{idRolAfiliado}/fecha")
    public void actualizarFechaAfiliacionRolAfiliado(@NotNull @PathParam("idRolAfiliado") Long idRolAfiliado, @NotNull Long fecha);

    /**
     * Servicio encargo de consultar el estado rol afiliado
     * 
     * @param tipoIdentificacion,
     *        tipo de identificaicon
     * @param numeroIdentificacion,
     *        numero identificacion a consultar
     * @param tipoAfiliado,
     *        tipo afiliado
     * @return retorna el estado del rolAfiliado
     */
    @GET
    @Path("consultarEstadoRolAfiliado")
    public EstadoAfiliadoEnum consultarEstadoRolAfiliado(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado,
            @QueryParam("idEmpleador") Long idEmpleador);

    /**
     * Método encargado de consultar los trabajadores de un empleador para ciertas sucursales.
     * @param idEmpleador
     *        id del empleador.
     * @param idSucursales
     *        id de las sucursales.
     * @return
     */
    @POST
    @Path("consultarTrabajadoresSucursales")
    public List<TrabajadorEmpleadorDTO> consultarTrabajadoresEmpresaSucursal(@QueryParam("idEmpleadorOrigen") Long idEmpleadorOrigen,
            @QueryParam("idEmpleadorDestino") Long idEmpleadorDestino, @QueryParam("fechaLabores") Long fechaFinLabores,
            List<Long> idSucursales);

    /**
     * Método encargado de consultar los trabajadores de un empleador para ciertas sucursales.
     * Por traslado de trabajadores.
     * @param idEmpleador
     *        id del empleador.
     * @param idSucursales
     *        id de las sucursales.
     * @return lista de los trabajadores.
     */
    @POST
    @Path("consultarTrabajadoresEmpleadorSucursal")
    public List<TrabajadorEmpleadorDTO> consultarTrabajadoresSucursal(@QueryParam("idEmpleador") Long idEmpleador,
            @QueryParam("idSucursal") Long idSucursal);

    /**
     * Servicio encargado de consultar las categorias
     * 
     * @param tipoIdentificacion
     *        tipo de identificacion
     * @param numeroIdentificacion
     *        numero de identificacion
     * @param tipoAfiliado
     *        tipo afiliado
     * @return Una lista de categorias de un afiliado
     */
    @GET
    @Path("consultarCategoriasAfiliado")
    public List<CategoriaDTO> consultarCategoriasAfiliado(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado);

    /**
     * Servicio encargado de consultar las categorias de un beneficiario
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoAfiliado
     * @param idAfiliado
     * @return Una lista de categorias de un beneficiario
     */
    @GET
    @Path("consultarCategoriasBeneficiario")
    public List<CategoriaDTO> consultarCategoriasBeneficiario(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado, @QueryParam("idAfiliado") Long idAfiliado);

    /**
     * Servicio encargado de consultar los empleadores de un afiliado.
     * @param idPersona
     *        id de la persona afiliada.
     * @param tipoAfiliado
     *        tipo de afiliado (DEPENDIENTE, INDEPENDIENTE, PENSIONADO)
     * @return lista de empleadores.
     */
    @GET
    @Path("consultarRolesAfiliado")
    public List<RolAfiliadoEmpleadorDTO> consultarRolesAfiliado(
            @QueryParam("tipoIdentificacion") @NotNull TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") @NotNull String numeroIdentificacion,
            @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado);

    /**
     * Servicio encargado de consultar los roles afiliados por id del empleador y ids de personas afiliadas.
     * @param idEmpleador
     *        id del empleador.
     * @param idsPersona
     *        id de las personas afiliadas.
     * @return roles de los afiliados con respecto al empleador.
     */
    @POST
    @Path("consultarRolesEmpleadorAfiliado")
    public List<RolAfiliadoModeloDTO> consultarRolesEmpleadorAfiliado(@QueryParam("idEmpleador") @NotNull Long idEmpleador,
            @NotNull List<Long> idsPersona);

    /**
     * Servicio encargado de consultar las clasificaciones de un afiliado.
     * @param tipoIdentificacion
     *        tipo de identificacion del afiliado.
     * @param numeroIdentificacion
     *        numero de identificacion del afiliado.
     * @return lista de clasificaciones del afiliado.
     */
    @GET
    @Path("consultarClasificacionesAfiliado")
    public List<ClasificacionEnum> consultarClasificacionesAfiliado(
            @QueryParam("tipoIdentificacion") @NotNull TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") @NotNull String numeroIdentificacion);
            

    @GET
    @Path("consultarClasificacionesAfiliadoFovis")
    public Response consultarClasificacionesAfiliadoFovis(
            @QueryParam("tipoIdentificacion") @NotNull TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") @NotNull String numeroIdentificacion,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);
    /**
     * Servicio encargado de actualizar una lista de roles afiliado.
     * @param rolesDTO
     *        roles afiliado a actualizar.
     */
    @POST
    @Path("actualizarRolesAfiliado")
    public void actualizarRolesAfiliado(@NotNull List<RolAfiliadoModeloDTO> rolesDTO);

    /**
     * Servicio que consulta todos los roles afiliados de un empleado y el estado de su afiliador.
     * @param idEmpleador
     *        id del empleador a buscar los afiliados.
     * @param estadoAfiliado
     *        estado de los afiliados.
     */
    @GET
    @Path("/{idEmpleador}/consultarRolesAfiliadosEmpleador")
    public List<RolAfiliadoModeloDTO> consultarRolesAfiliadosEmpleador(@PathParam("idEmpleador") Long idEmpleador,
            @QueryParam("estadoAfiliado") EstadoAfiliadoEnum estadoAfiliado);

    /**
     * <b>Descripción:</b> Método encargado de actualizar un Beneficiario como llegue en el DTO,
     * debe contener el Id- Novedades Persona
     * @param beneficiario
     * @return Identificador del beneficiario
     */
    @POST
    @Path("/actualizarBeneficiario")
    public Long actualizarBeneficiario(@NotNull BeneficiarioModeloDTO beneficiarioModeloDTO);

    /**
     * <b>Descripción:</b> Método encargado de actualizar un Grupo Familiar como llegue en el DTO,
     * debe contener el Id- Novedades Persona
     * @param grupoFamiliarDTO
     * @return id del grupo familiar.
     */
    @POST
    @Path("/actualizarGrupoFamiliarPersona")
    public Long actualizarGrupoFamiliarPersona(GrupoFamiliarModeloDTO grupoFamiliarModeloDTO);

    /**
     * <b>Descripción:</b> Método que se encarga de actualizar un Rol Afiliado como llegue en el DTO,
     * debe contener el Id- Novedades Persona
     * @param rolAfiliadoModeloDTO
     *        Rol Afiliado a modificar.
     */
    @POST
    @Path("/actualizarRolAfiliado")
    public void actualizarRolAfiliado(@NotNull RolAfiliadoModeloDTO rolAfiliadoModeloDTO);

    /**
     * <b>Descripción:</b> Método que se encarga de actualizar un Rol Afiliado como llegue en el DTO,
     * debe contener el Id- Novedades Persona
     * @param rolAfiliadoModeloDTO
     *        Rol Afiliado a modificar.
     */
    @POST
    @Path("/crearRolAfiliado")
    public Long crearRolAfiliado(@NotNull RolAfiliadoModeloDTO rolAfiliadoModeloDTO);

    /**
     * <b>Descripción:</b> Método encargado de consultar el Beneficiario por identificación.
     * 
     * @param idBeneficiario
     *        Identificación del beneficiario.
     * @return BeneficiarioModeloDTO
     */
    @GET
    @Path("/{idBeneficiario}/consultarBeneficiario")
    public BeneficiarioModeloDTO consultarBeneficiario(@NotNull @PathParam("idBeneficiario") Long idBeneficiario);

    /**
     * <b>Descripción:</b> Método encargado de consultar el Grupo Familiar por identificación.
     * 
     * @param idGrupoFamiliar
     *        Identificación del Grupo Familiar.
     * @return GrupoFamiliarModeloDTO
     */
    @GET
    @Path("/{idGrupoFamiliar}/consultarDatosGrupoFamiliar")
    public GrupoFamiliarModeloDTO consultarDatosGrupoFamiliar(@NotNull @PathParam("idGrupoFamiliar") Long idGrupoFamiliar);

    /**
     * <b>Descripción:</b> Método encargado de consultar el Rol Afiliado por identificación.
     * 
     * @param idRolAfiliado
     *        Identificación del Rol Afiliado
     * @return RolAfiliadoDTO
     */
    @GET
    @Path("/{idRolAfiliado}/consultarRolAfiliado")
    public RolAfiliadoModeloDTO consultarRolAfiliado(@NotNull @PathParam("idRolAfiliado") Long idRolAfiliado);

    /**
     * Consulta Las Personas que tengan vencimiento de incapacidades.
     * @return Identificación de Personas con vencimiento de Incapacidades.
     */
    @GET
    @Path("/consultarVencimientoIncapacidades")
    public List<Long> consultarVencimientoIncapacidades();

    /**
     * Inactiva Novedad Por Vencimiento de Incapacidad.
     * @param idPersonasInactivar
     */
    @POST
    @Path("/inactivarVencimientoIncapacidades")
    public void inactivarVencimientoIncapacidades(@NotNull List<Long> idPersonasInactivar);

    /**
     * ActualizarNovedadPila
     * @param novedadPilaModeloDTO
     */
    @POST
    @Path("/actualizarNovedadPila")
    public void actualizarNovedadPila(@NotNull NovedadDetalleModeloDTO novedadPilaModeloDTO);

    /**
     * Consulta los datos de un Afiliado
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return AfiliadoModeloDTO
     */
    @GET
    @Path("/consultarDatosAfiliado")
    public AfiliadoModeloDTO consultarDatosAfiliado(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Actualiza el Afiliado a partir de AfiliadoModeloDTO.
     * @param afiliadoModeloDTO
     */
    @POST
    @Path("/actualizarDatosAfiliado")
    public void actualizarDatosAfiliado(AfiliadoModeloDTO afiliadoModeloDTO);

    /**
     * Método encargado de validar si un empleador tiene cero trabajadores activos.
     * @param idEmpleador
     *        id del empleador a validar.
     * @return true si hay cero trabajadores activos, false si al menos hay alguno trabajador activo.
     */
    @GET
    @Path("{idEmpleador}/validarEmpleadorCeroTrabajadores")
    public Boolean validarEmpleadorCeroTrabajadores(@PathParam("idEmpleador") Long idEmpleador);

    /**
     * Método encargado de validar si un trabajador debe ser inactivado en caja.
     * @param idAfiliado
     *        id del afiliado a validar.
     * @return true si los trabajadores se encuentran todos inactivos, false si al menos hay algun trabajador activo.
     */
    @GET
    @Path("{idAfiliado}/validarDesactivarAfiliadoCaja")
    public Boolean validarDesactivarAfiliadoCaja(@PathParam("idAfiliado") Long idAfiliado);

    /**
     * Actualiza el Medio de Pago asociado a la Persona
     * @param medioDePagoModeloDTO
     */
    @POST
    @Path("/actualizarMedioDePagoPersona")
    public void actualizarMedioDePagoPersona(MedioDePagoModeloDTO medioDePagoModeloDTO);

    /**
     * Actualiza el Medio de Pago asociado al grupo Familiar
     * @param medioDePagoModeloDTO
     */
    @POST
    @Path("/actualizarMedioDePagoGrupoFamiliar")
    public void actualizarMedioDePagoGrupoFamiliar(MedioDePagoModeloDTO medioDePagoModeloDTO);

    /**
     * Consulta el Medio de Pago por Identificador.
     * @param idMedioPago
     * @return
     */
    @GET
    @Path("/consultarMedioDePago")
    public MedioDePagoModeloDTO consultarMedioDePago(@QueryParam("idGrupoFamiliar") Long idGrupoFamiliar,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoMedioDePagoEnum") TipoMedioDePagoEnum tipoMedioDePagoEnum);

    /**
     * Método encargado de verificar si existe un Afiliado/RolAfiliado asociado a una persona
     * @param personaEmpresaDTO
     *        la persona y el empleador en base a los cuales se realizará la búsqueda
     * @return RolAfiliado si encuentra uno asociado a la persona. devuelve null en caso contrario.
     */
    @POST
    @Path("/verificarExisteAfiliadoAsociado")
    public RolAfiliado verificarExisteAfiliadoAsociado(@NotNull PersonaEmpresaDTO personaEmpresaDTO);

    /**
     * Método encargado de consultar los identificadores de personas
     * beneficiarios con mayor edad y tipo identificacion Tarjeta Identidad
     * 
     * @return Lista de beneficiarios
     */
    @GET
    @Path("/consultarBeneficariosMayorEdadConTI")
    public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosMayorEdadConTI();

    /**
     * Retira masivamente los beneficiarios mayores a 18 con Tarjeta Identidad
     * 
     * @param idsBeneficiario
     *        Identificadores de beneficiario
     */
    @POST
    @Path("/retirarBeneficiarioMayorEdadConTI")
    public void retirarBeneficiarioMayorEdadConTI(@NotNull List<Long> idsBeneficiario);

    /**
     * Método encargado de consultar los beneficiarios con X (parametro EDAD_RETIRO_BENEFICIARIO) edad
     * 
     * @return Lista de beneficiarios
     */
    @GET
    @Path("/consultarBeneficariosXEdad")
    public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosXEdad();

    /**
     * Retira masivamente los beneficiarios que cumplan X Edad
     * 
     * @param idsBeneficiario
     *        Identificadores de beneficiario
     */
    @POST
    @Path("/retirarBeneficiarioXEdad")
    public void retirarBeneficiarioXEdad(@NotNull List<Long> idsBeneficiario);

    /**
     * Método encargado de consultar los beneficiarios con X (parametro EDAD_CAMBIO_CATEGORIA_BENEFICIARIO) edad
     * para cambiarles la categoria
     * 
     * @return Lista de identificadores Personas beneficiarios
     */
    @GET
    @Path("/consultarBeneficariosXEdadCambioCategoria")
    public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosXEdadCambioCategoria();

    /**
     * Cambia masivamente la categoria a los beneficiarios que llegan como parametro
     * 
     * @param idsBeneficiario
     *        Identificadores de beneficiario
     */
    @POST
    @Path("/cambiarCategoriaBeneficiarioXEdad")
    public void cambiarCategoriaBeneficiarioXEdad(@NotNull List<Long> idsBeneficiario);

        /**
     * Método encargado de consultar los beneficiarios con X (parametro EDAD_CAMBIO_CATEGORIA_BENEFICIARIO) edad
     * para cambiarles la categoria
     * 
     * @return Lista de identificadores Personas beneficiarios
     */
    @GET
    @Path("/consultarBeneficariosPadresXEdadCambioCategoria")
    public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosPadresXEdadCambioCategoria();

    /**
     * Cambia la categpria de los beneficiarios hijos que lleguen por parametro
     * 
     *  @param idsBeneficiariosHijos
     *         Identificadores de beneficiarios (Hijos)
     */

     @POST
     @Path("/cambiarCategoriaBeneficiarioHijosCertificadoEscolar")
     public void cambiarCategoriaBeneficiarioHijosCertificadoEscolar(@NotNull List<Long> idsBeneficiariosHijos);

      /**
     * Método encargado de consultar los beneficiarios con X (parametro EDAD_CAMBIO_CATEGORIA_BENEFICIARIO) edad
     * para cambiarles la categoria
     * 
     * @return Lista de identificadores Personas beneficiarios
     */
    @GET
    @Path("/consultarBeneficariosCertificadoEscolarCambioCategoria")
    public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosCertificadoEscolarCambioCategoria(@QueryParam("sinCertificado") String sinCertificado);

    /**
     * Consulta los Roles Afiliado Empleador por estado que están asociados a una serie de Empleadores
     * @param idEmpleadores
     * @param estadoAfiliado
     * @return Lista de roles Afiliado.
     */
    @POST
    @Path("/consultarRolesAfiliadosEmpleadorMasivo")
    public List<RolAfiliadoModeloDTO> consultarRolesAfiliadosEmpleadorMasivo(List<Long> idEmpleadores,
            @QueryParam("idEmpleador") @NotNull EstadoAfiliadoEnum estadoAfiliado);

    @POST
     @Path("/cambiarMarcaEmpresaTrasladadaCCF")
     public void cambiarMarcaEmpresaTrasladadaCCF(@NotNull DatosEmpleadorNovedadDTO datosEmpleador);

    /**
     * Consulta las personas que presentan mora en aportes para un tiempo determinado en meses.
     * @return List<PersonaRetiroNovedadAutomaticaDTO>
     */
    @GET
    @Path("/consultarPersonasMoraAportes")
    public List<PersonaRetiroNovedadAutomaticaDTO> consultarPersonasMoraAportes();

    /**
     * Registra el retiro de los roles afiliado enviados
     * @param rolesAfiliado
     *        Lista de roles afiliados a inactivar
     */
    @POST
    @Path("/inactivarAfiliadosMasivo")
    public void inactivarAfiliadosMasivo(List<RolAfiliadoModeloDTO> rolesAfiliado);

    /**
     * Actualiza el registro de expulsion subsanada
     * @param expulsionSubsanadaModeloDTO
     *        Informacion de la expulsion a realizar
     */
    @POST
    @Path("/actualizarExpulsionSubsanada")
    public void actualizarExpulsionSubsanada(ExpulsionSubsanadaModeloDTO expulsionSubsanadaModeloDTO);

    /**
     * cambia el estado del afiliado en rol afiliado por ACTIVO
     * 
     * @param tipoIdAfiliado
     *        tipo de identificacion del afiliado
     * 
     * @param numeroIdAfiliado
     *        número de identificacion del afiliado
     * 
     * @param tipoAfiliado
     *        el tipo de afiliación que tiene la persona
     * 
     * @param tipoIdAportante
     *        tipo de identificacion del aportante o empleador
     * 
     * @param numeroIdAportante
     *        número de identificacion del aportante o empleador
     */
    @POST
    @Path("/activarAfiliado")
    public void activarAfiliado(ActivacionAfiliadoDTO datosActivacion);

    /**
     * Consulta la persona y sus registros como beneficiario
     * @param tipoIdentificacion
     *        Tipo identificacion persona
     * @param numeroIdentificacion
     *        Numero identificacion persona
     * @return Lista de beneficiarios
     */
    @GET
    @Path("/consultaBeneficiarioTipoNroIdentificacion")
    public List<BeneficiarioModeloDTO> consultarBeneficiarioTipoNroIdentificacion(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Consulta los beneficiarios que cumplan con los criterios de busqueda
     * @param tipoIdentificacion
     *        Tipo identificacion beneficiario
     * @param numeroIdentificacion
     *        Numero identificacion beneficiario
     * @param primerNombre
     *        Primer nombre
     * @param primerApellido
     *        Primer apellido
     * @param fechaFinCertificado
     *        Fecha fin certificado escolar
     * @param clasificacion
     *        Lista tipo beneficiario
     * @param estadoRespectoAfiliado
     *        Estado respecto afiliado
     * @param tipoHijo
     *        Indica si se buscan solo beneficiarios de tipo hijo
     * @return Lista de beneficiarios
     */
    @GET
    @Path("/consultarBeneficiario")
    public List<BeneficiarioModeloDTO> consultarBeneficiarioByTipo(@Context UriInfo uriInfo, @Context HttpServletResponse response,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("primerNombre") String primerNombre,
            @QueryParam("segundoNombre") String segundoNombre, @QueryParam("primerApellido") String primerApellido,
            @QueryParam("segundoApellido") String segundoApellido, @QueryParam("fechaFinCertificado") Long fechaFinCertificado,
            @QueryParam("tipoBeneficiario") List<ClasificacionEnum> clasificacion,
            @QueryParam("estadoRespectoAfiliado") EstadoAfiliadoEnum estadoRespectoAfiliado, @QueryParam("tipoHijo") Boolean tipoHijo,
            @QueryParam("textoFiltro") String textoFiltro);

    /**
     * Consulta la informacion del afiliado por el identificado del mismo
     * @param idAfiliado
     *        Identificador afiliado
     * @return Informacion afiliado
     */
    @GET
    @Path("/consultarAfiliadoPorId")
    public AfiliadoModeloDTO consultarAfiliadoPorId(@QueryParam("idAfiliado") Long idAfiliado);

    /**
     * Capacidad que permite la creación de un Tipo de Infraestructura
     * @param tipoInfraestructura
     *        DTO que contiene la información del nuevo tipo de infraestructura a registrar
     * @return <b>RespuestaServicioInfraestructuraDTO</b>
     *         DTO con el resultado de la operación
     * @author abaquero
     */
    @POST
    @Path("/registrarTipoInfraestructura")
    public RespuestaServicioInfraestructuraDTO registrarTipoInfraestructura(TipoInfraestructuraModeloDTO tipoInfraestructura);

    /**
     * Capacidad que permite la consulta de los tipos de infraestructura activos en el sistema
     * @param estado
     *        Estado de los tipos consultados (nulo para todos los estados)
     * @return <b>List<TipoInfraestructuraModeloDTO></b>
     *         Listado de DTO con la información de los tipos de infraestructura activos en la parametrización
     * @author abaquero
     */
    @GET
    @Path("/consultarTiposInfraestructura")
    public List<TipoInfraestructuraModeloDTO> consultarTiposInfraestructura(@QueryParam("estado") Boolean estado);

    /**
     * Capacidad que permite la consulta de un tipo de infraestructura activo específico por ID en el sistema
     * @param idTipoInfraestructura
     *        ID interno de la infraestructura
     * @return <b>TipoInfraestructuraModeloDTO</b>
     *         DTO con la información del tipo de infraestructura solicitado
     * @author abaquero
     */
    @GET
    @Path("/consultarTipoInfraestructuraPorID/{idTipoInfraestructura}")
    public TipoInfraestructuraModeloDTO consultarTipoInfraestructuraPorID(
            @PathParam("idTipoInfraestructura") @NotNull Long idTipoInfraestructura);

    /**
     * Capacidad que permite la consulta de un tipo de infraestructura activo específico por su ID asigando por la SSF en el sistema
     * @param idSsfTipoInfraestructura
     *        Código asignado por la SSF para el tipo de infraestructura
     * @return <b>TipoInfraestructuraModeloDTO</b>
     *         DTO con la información del tipo de infraestructura solicitado
     * @author abaquero
     */
    @GET
    @Path("/consultarTipoInfraestructuraPorIdSSF/{idSsfTipoInfraestructura}")
    public TipoInfraestructuraModeloDTO consultarTipoInfraestructuraPorIdSSF(
            @PathParam("idSsfTipoInfraestructura") @NotNull String idSsfTipoInfraestructura);

    /**
     * Capacidad que permite activar o inactivar un tipo de infraestructura presente en la parametrización
     * @param idTipoInfraestructura
     *        ID del tipo de infraestructura a modificar
     * @param activar
     *        Indica que se va a activar (true) o a inactivar (false) el tipo de infraestructura
     * @return <b>RespuestaServicioInfraestructuraDTO</b>
     *         DTO con el resultado de la operación
     * @author abaquero
     */
    @PUT
    @Path("/inactivarActivarTipoInfraestructura/{idTipoInfraestructura}")
    public RespuestaServicioInfraestructuraDTO inactivarActivarTipoInfraestructura(
            @PathParam("idTipoInfraestructura") @NotNull Long idTipoInfraestructura, @QueryParam("activar") @NotNull Boolean activar);

    /**
     * Capacidad que permite la creación de una nueva Infraestructura
     * @param infraestructura
     *        DTO que contiene la información del nuevo tipo de infraestructura a registrar
     * @return <b>RespuestaServicioInfraestructuraDTO</b>
     *         DTO con el resultado de la operación
     * @author abaquero
     */
    @POST
    @Path("/registrarInfraestructura")
    public RespuestaServicioInfraestructuraDTO registrarInfraestructura(InfraestructuraModeloDTO infraestructura);

    /**
     * Capacidad que permite la consulta de las infraestructuras de la CCF
     * @param criterios
     *        Listado de criterios de búsqueda, basados en la estructura del entity de infraestructuras
     * @param uri
     *        Información de la petición
     * @param response
     *        Respuesta HTML
     * @return <b>List<PresentacionResultadoModeloInfraestructuraDTO></b>
     *         Listado de DTO con la información de las infraestructuras consultadas para la CCF
     * @author abaquero
     */
    @POST
    @Path("/consultarInfraestructuras")
    public List<PresentacionResultadoModeloInfraestructuraDTO> consultarInfraestructuras(
            CriteriosConsultaModeloInfraestructuraDTO criterios, @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Capacidad que permite la consulta de una infraestructura activa específica por ID en el sistema
     * @param idInfraestructura
     *        ID interno de la infraestructura
     * @return <b>TipoInfraestructuraModeloDTO</b>
     *         DTO con la información de la infraestructura solicitada
     * @author abaquero
     */
    @GET
    @Path("/consultarInfraestructuraPorID/{idInfraestructura}")
    public InfraestructuraModeloDTO consultarInfraestructuraPorID(@PathParam("idInfraestructura") @NotNull Long idInfraestructura);

    /**
     * Capacidad que permite activar o inactivar una infraestructura de la CCF
     * @param datosActualizados
     *        DTO que contiene la información a modificar de la infraestructura, incluye su ID para identificarlo
     * @return <b>RespuestaServicioInfraestructuraDTO</b>
     *         DTO con el resultado de la operación
     * @author abaquero
     */
    @PUT
    @Path("/actualizarInfraestructura")
    public RespuestaServicioInfraestructuraDTO actualizarInfraestructura(InfraestructuraModeloDTO datosActualizados);

    /**
     * Capacidad que permite la creación de un Sitio de Pago
     * @param sitioPago
     *        DTO que contiene la información del nuevo Sitio de Pago a registrar
     * @return <b>RespuestaServicioInfraestructuraDTO</b>
     *         DTO con el resultado de la operación
     * @author abaquero
     */
    @POST
    @Path("/registrarSitioPago")
    public RespuestaServicioInfraestructuraDTO registrarSitioPago(SitioPagoModeloDTO sitioPago);

    /**
     * Capacidad que permite la consulta de los Sitios de Pago en el sistema
     * @param criterios
     *        Listado de criterios de búsqueda, basados en la estructura del entity de sitios de pago
     * @param uri
     *        Información de la petición
     * @param response
     *        Respuesta HTML
     * @return <b>List<PresentacionResultadoModeloInfraestructuraDTO></b>
     *         Listado de DTO con la información de los sitios de pago consultados para la CCF
     * @author abaquero
     */
    @PUT
    @Path("/consultarSitiosPago")
    public List<PresentacionResultadoModeloInfraestructuraDTO> consultarSitiosPago(CriteriosConsultaModeloInfraestructuraDTO criterios,
            @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Capacidad que permite la consulta de un Sitio de Pago activo específico por ID en el sistema
     * @param idSitioPago
     *        ID interno de la infraestructura
     * @return <b>SitioPagoModeloDTO</b>
     *         DTO con la información del Sitio de Pago solicitado
     * @author abaquero
     */
    @GET
    @Path("/consultarSitioPagoPorID/{idSitioPago}")
    public SitioPagoModeloDTO consultarSitioPagoPorID(@PathParam("idSitioPago") @NotNull Long idSitioPago);

    /**
     * consulta el medio sitio de pago por defecto en la caja
     * @return <b>SitioPagoModeloDTO</b>
     *         DTO con la información del Sitio de Pago solicitado
     * @author Asopagos
     */
    @GET
    @Path("/consultarIdSitioPagoPredeterminado")
    public Long consultarIdSitioPagoPredeterminado();

    /**
     * Capacidad que permite activar o inactivar un Sitio de Pago presente para la CCF
     * @param datosActualizados
     *        DTO que contiene la información a modificar del sitio de pago, incluye su ID para identificarlo
     * @return <b>RespuestaServicioInfraestructuraDTO</b>
     *         DTO con el resultado de la operación
     * @author abaquero
     */
    @PUT
    @Path("/actualizarSitioPago")
    public RespuestaServicioInfraestructuraDTO actualizarSitioPago(SitioPagoModeloDTO datosActualizados);

    /**
     * Capacidad que permite la creación de un Tipo de Tenencia
     * @param tipoTenencia
     *        DTO que contiene la información del nuevo Tipo de Tenencia a registrar
     * @return <b>RespuestaServicioInfraestructuraDTO</b>
     *         DTO con el resultado de la operación
     * @author abaquero
     */
    @POST
    @Path("/registrarTipoTenencia")
    public RespuestaServicioInfraestructuraDTO registrarTipoTenencia(TipoTenenciaModeloDTO tipoTenencia);

    /**
     * Capacidad que permite la consulta de los Tipos de Tenencia activos en el sistema
     * @param estado
     *        Estado de los tipos consultados (nulo para todos los estados)
     * @return <b>List<TipoTenenciaModeloDTO></b>
     *         Listado de DTO con la información de los Tipos de Tenencia activos para la CCF
     * @author abaquero
     */
    @GET
    @Path("/consultarTiposTenencia/")
    public List<TipoTenenciaModeloDTO> consultarTiposTenencia(@QueryParam("estado") Boolean estado);

    /**
     * Capacidad que permite la consulta de un Tipo de Tenencia activo específico por ID en el sistema
     * @param idTipoTenencia
     *        ID interno de la infraestructura
     * @return <b>TipoTenenciaModeloDTO</b>
     *         DTO con la información del Tipo de Tenencia solicitado
     * @author abaquero
     */
    @GET
    @Path("/consultarTipoTenenciaPorID/{idTipoTenencia}")
    public TipoTenenciaModeloDTO consultarTipoTenenciaPorID(@PathParam("idTipoTenencia") @NotNull Long idTipoTenencia);

    /**
     * Capacidad que permite activar o inactivar un Tipo de Tenencia presente para la CCF
     * @param idTipoTenencia
     *        ID del Tipo de Tenencia a modificar
     * @param activar
     *        Indica que se va a activar (true) o a inactivar (false) el Tipo de Tenencia
     * @return <b>RespuestaServicioInfraestructuraDTO</b>
     *         DTO con el resultado de la operación
     * @author abaquero
     */
    @PUT
    @Path("/inactivarActivarTipoTenencia/{idTipoTenencia}")
    public RespuestaServicioInfraestructuraDTO inactivarActivarTipoTenencia(@PathParam("idTipoTenencia") @NotNull Long idTipoTenencia,
            @QueryParam("activar") @NotNull Boolean activar);

    /**
     * Método encargado de realizar las desafiliaciones de los beneficiarios pertenecientes a las afiliaciones de un empleador
     * @param motivoDesafiliacion,
     *        Motivo de desafiliación de los beneficiarios pertenecientes a los afiliados del empleador
     * @param roles,
     *        listado de afiliados del empleador
     */
    @POST
    @Path("/desafiliarAfiliacionesEmpleador")
    public List<BeneficiarioDTO> desafiliarAfiliacionesEmpleador(
            @NotNull @QueryParam("motivoDesafiliacion") MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion,
            List<RolAfiliadoModeloDTO> roles);

    /**
     * Servicio encargado de desafiliar un beneficiario y sus afiliaciones respecto al afiliado principal
     * @param idAfiliado,
     *        identificador del afiliado principal
     * @param idRolAfiliado,
     *        identificador de la afiliacion
     * @param motivoDesafiliacion,
     *        motivo de desafiliación
     */
    @POST
    @Path("/desafiliarBeneficiarioAfiliado/{idAfiliado}")
    public List<BeneficiarioDTO> desafiliarBeneficiarioAfiliado(@NotNull @PathParam("idAfiliado") Long idAfiliado,
            @QueryParam("idRolAfiliado") Long idRolAfiliado,
            @NotNull @QueryParam("motivoDesafiliacion") MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion,
            @NotNull @QueryParam("fechaRetiroAfiliado") Long fechaRetiroAfiliado);

    /**
     * Servicio encargado de desafiliar los beneficiarios respecto al afiliado principal y pertenecientes al empleador
     * @param idEmpleador,
     *        id del empleador
     * @param motivoDesafiliacion,
     *        motivo de desafiliación del beneficiario
     */
    @POST
    @Path("/desafiliarBeneficiarioEmpleador/{idEmpleador}")
    public void desafiliarBeneficiarioEmpleador(@PathParam("idEmpleador") Long idEmpleador,
            @NotNull @QueryParam("motivoDesafiliacion") MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion);

    /**
     * Servicio que consulta los ingresos mensuales de una persona, por identificación
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona
     * @param numeroIdentificacion
     *        Número de identificación de la persona
     * @return El total de ingresos mensuales de la persona
     */
    @GET
    @Path("/consultarValorIngresoMensual")
    BigDecimal consultarValorIngresoMensual(@NotNull @QueryParam("tipoIdentificacionJefe") TipoIdentificacionEnum tipoIdentificacionJefe,
            @NotNull @QueryParam("numeroIdentificacionJefe") String numeroIdentificacionJefe,
            @QueryParam("tipoIdentificacionIntegrante") TipoIdentificacionEnum tipoIdentificacionIntegrante,
            @QueryParam("numeroIdentificacionIntegrante") String numeroIdentificacionIntegrante);

    /**
     * Servicio encargado de buscar el tipo de Persona
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return retorna el listado de personas DTO
     */
    @GET
    @Path("/buscarTipoAfiliacionPersona")
    public PersonaDTO buscarTipoAfiliacionPersona(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * <b>Descripción:</b>Servicio que consulta el afiliado o afiliados que coincidan con los filtros de busqueda.</br>
     * 
     * @param tipoIdentificacion
     *        tipo de identificación del afiliado
     * @param numeroIdentificacion
     *        número de identificación del afiliado
     * @param primerNombre
     *        primer nombre del afiliado
     * @param segundoNombre
     *        segundo nombre del afiliado
     * @param primerApellido
     *        primer apellido del afiliado
     * @param segundoApellido
     *        segundo apellido del afiliado
     * @return afiliado o afiliados que coinciden con los filtros.
     * @author mosorio
     */
    @GET
    @Path("/consultarAfiliadoPrincipal")
    public List<AfiliadoModeloDTO> consultarAfiliadoPrincipal(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("primerNombre") String primerNombre,
            @QueryParam("segundoNombre") String segundoNombre, @QueryParam("primerApellido") String primerApellido,
            @QueryParam("segundoApellido") String segundoApellido);

    /**
     * <b>Descripción:</b>Servicio que consulta el beneficiario o beneficiarios que coincidan con los filtros de busqueda.</br>
     * 
     * @param tipoIdentificacion
     *        tipo de identificación del beneficiario
     * @param numeroIdentificacion
     *        número de identificación del beneficiario
     * @param primerNombre
     *        primer nombre del beneficiario
     * @param segundoNombre
     *        segundo nombre del beneficiario
     * @param primerApellido
     *        primer apellido del beneficiario
     * @param segundoApellido
     *        segundo apellido del beneficiario
     * @return beneficiario o beneficiarios que coincidan con los filtros.
     * @author mosorio
     */
    @GET
    @Path("/consultarBeneficiarioPrincipal")
    public List<BeneficiarioModeloDTO> consultarBeneficiarioPrincipal(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("primerNombre") String primerNombre,
            @QueryParam("segundoNombre") String segundoNombre, @QueryParam("primerApellido") String primerApellido,
            @QueryParam("segundoApellido") String segundoApellido);

    /**
     * <b>Descripción:</b>Servicio que consulta los beneficiario asociados a un grupo familiar indicado por parametro</br>
     * @author Ricardo Hernandez Cediel <hhernandez@heinsohn.com.co>
     * 
     * @param idGrupoFamiliar
     *        <code>Long</code>
     *        El identificador del grupo Familiar
     * 
     * @return <code>List<BeneficiarioModeloDTO></code>
     *         La lista de los beneficiarios asociados al grupo familiar
     */
    @GET
    @Path("/consultar/grupoFamiliar/beneficiarios")
    public List<BeneficiarioModeloDTO> consultarBeneficiariosPorGrupoFamiliar(@QueryParam("idGrupoFamiliar") Long idGrupoFamiliar);

    /**
     * Servicio encargado de consultar un rol afiliado especifico para un afiliado
     * @param tipoIdentificacion,
     *        tipo identificación del afiliado
     * @param numeroIdentificacion,
     *        numeró identificación del afiliado
     * @param tipoAfiliado,
     *        tipo de afiliado del rol especifico
     * @param idEmpleador,
     *        id del empleador al que se encuentra asociado el rolAfiliado
     * @return
     */
    @GET
    @Path("/consultarRolAfiliadoEspecifico")
    public RolAfiliadoModeloDTO consultarRolAfiliadoEspecifico(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado,
            @QueryParam("idEmpleador") Long idEmpleador);

    /**
     * Servicio encargado de consultar una solitud asociada a una persona
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    @GET
    @Path("/consultarSolicitudGlobalPersona")
    public SolicitudGlobalPersonaDTO consultarSolicitudGlobalPersona(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio que consulta la información detallada de un afiliado como pensionado
     * 
     * @param tipoIdentificacion
     *        el tipo de identificacion del afiliado
     * 
     * @param numeroIdentificacion
     *        el numero de dentificacion del afiliado
     * 
     * @return AfiliadoPensionadoVista360DTO con la información del afiliado como pensionado.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerInfoDetalladaAfiliadoComoPensionado")
    public AfiliadoPensionadoVista360DTO obtenerInfoDetalladaAfiliadoComoPensionado(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Consulta lasa categorías del beneficiario conyuge activo asociado al afiliado principal
     * 
     * @param tipoIdentificacion
     *        el tipo de identificacion del afiliado
     * 
     * @param numeroIdentificacion
     *        el numero de identificacion del afiliado
     * 
     * @return List<CategoriaDTO> con la infrmación de las categorías obtenidas.
     */
    @GET
    @Path("/consultarCategoriaConyugeAfiliado")
    public List<CategoriaDTO> consultarCategoriaConyugeAfiliado(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Consulta los tipos de afiliacion con los que está registrada una persona dada.
     * 
     * @param tipoIdentificacion
     *        el tipo de identificacion de la persona.
     * 
     * @param numeroIdentificacion
     *        el numero de identificacion de la persona.
     * 
     * @return List<TipoAfiliadoEnum> con los tipos de afiliacion que tiene la persona
     */
    @GET
    @Path("/consultarTiposAfiliacionAfiliado")
    public List<TipoAfiliadoEnum> consultarTiposAfiliacionAfiliado(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * consu
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    @GET
    @Path("/obtenerInfoDetalladaAfiliadoComoIndependiente")
    public AfiliadoIndependienteVista360DTO obtenerInfoDetalladaAfiliadoComoIndependiente(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @GET
    @Path("/obtenerInfoAfiliadoRespectoEmpleador")
    public InfoAfiliadoRespectoEmpleadorDTO obtenerInfoAfiliadoRespectoEmpleador(
            @QueryParam("tipoIdAfiliado") TipoIdentificacionEnum tipoIdAfiliado, @QueryParam("numeroIdAfiliado") String numeroIdAfiliado,
            @QueryParam("tipoIdEmpleador") TipoIdentificacionEnum tipoIdEmpleador,
            @QueryParam("numeroIdEmpleador") String numeroIdEmpleador);

    @GET
    @Path("/obtenerEmpleadoresRelacionadosAfiliado")
    public List<EmpleadorRelacionadoAfiliadoDTO> obtenerEmpleadoresRelacionadosAfiliado(
            @QueryParam("tipoIdAfiliado") TipoIdentificacionEnum tipoIdAfiliado, @QueryParam("numeroIdAfiliado") String numeroIdAfiliado,
            @QueryParam("tipoIdEmpleador") TipoIdentificacionEnum tipoIdEmpleador,
            @QueryParam("numeroIdEmpleador") String numeroIdEmpleador, @QueryParam("razonSocial") String razonSocial);

    //    /**
    //     * Servicio encargado de consultar las categorias propias del afiliado principal para 
    //     * las vistas 360.
    //     * 
    //     * @param tipoIdAfiliado
    //     *          tipo de identificación del afiliado principal.
    //     *          
    //     * @param numeroIdAfiliado
    //     *          número de identificación del afiliado principal.
    //     *          
    //     * @param tipoAfiliado
    //     *          tipo del afiliado principal (TRABAJADOR_DEPENDIENTE, TRABAJADOR_INDEPENDIENTE 
    //     *          o PENSIONADO).
    //     * 
    //     * @return CategoriasComoAfiliadoPrincipal360DTO con las categorias 
    //     * actuales del afiliado y el histórico de las mismas.
    //     * 
    //     * @author squintero
    //     */
    //    @GET
    //    @Path("/obtenerCategoriasPropiasAfiliado")
    //    public CategoriasComoAfiliadoPrincipal360DTO obtenerCategoriasPropiasAfiliado(
    //            @QueryParam("tipoIdAfiliado")TipoIdentificacionEnum tipoIdAfiliado,
    //            @QueryParam("numeroIdAfiliado")String numeroIdAfiliado,
    //            @QueryParam("tipoAfiliado")TipoAfiliadoEnum tipoAfiliado
    //            ); 

    //    /**
    //     * Servicio encargado de consultar las categorias del conyuge del afiliado principal para 
    //     * las vistas 360.
    //     * 
    //     * @param tipoIdAfiliado
    //     *          tipo de identificación del afiliado principal.
    //     *          
    //     * @param numeroIdAfiliado
    //     *          número de identificación del afiliado principal.
    //     *          
    //     * @param tipoAfiliado
    //     *          tipo del afiliado principal (TRABAJADOR_DEPENDIENTE, TRABAJADOR_INDEPENDIENTE 
    //     *          o PENSIONADO).
    //     * 
    //     * @return CategoriasComoAfiliadoPrincipal360DTO con las categorias 
    //     * actuales del conyuge del afiliado y el histórico de las mismas.
    //     * 
    //     * @author squintero
    //     */
    //    @GET
    //    @Path("/obtenerCategoriasConyugeAfiliado")
    //    public CategoriasComoAfiliadoPrincipal360DTO obtenerCategoriasConyugeAfiliado(
    //            @QueryParam("tipoIdAfiliado")TipoIdentificacionEnum tipoIdAfiliado,
    //            @QueryParam("numeroIdAfiliado")String numeroIdAfiliado,
    //            @QueryParam("tipoAfiliado")TipoAfiliadoEnum tipoAfiliado
    //            );

    //    /**
    //     * Servicio encargado de consultar las categorias heredadas del afiliado principal o del 
    //     * afiliado secundario.
    //     * 
    //     * @param tipoIdAfiliado
    //     *          tipo de identificacion del afiliado que en este caso, se busca como un 
    //     *          beneficiario.
    //     *          
    //     * @param numeroIdAfiliado
    //     *          número de identificacion del afiliado que en este caso, se busca como un 
    //     *          beneficiario.
    //     *          
    //     * @return CategoriasComoBeneficiario360DTO con la información del afiliado principal o 
    //     *         secundario, las categorias heredadas actuales y el histórico de categorías 
    //     *         heredadas.
    //     * 
    //     */
    //    @GET
    //    @Path("/obtenerCategoriasHeredadas")
    //    public CategoriasComoBeneficiario360DTO obtenerCategoriasHeredadas(
    //            @QueryParam("tipoIdAfiliado")TipoIdentificacionEnum tipoIdAfiliado,
    //            @QueryParam("numeroIdAfiliado")String numeroIdAfiliado,
    //            @QueryParam("isAfiliadoPrincipal")Boolean isAfiliadoPrincipal);

    /**
     * Servicio encargado de consultar y obtener la información de la relación laboral
     * entre un empleador y un afiliado
     * 
     * @param idRolAfiliado
     *        es el identificador del rolAfiliado en bdat.
     * 
     * @return InfoRelacionLaboral360DTO con la información solicitada.
     */
    @GET
    @Path("/consultarInformacionRelacionLaboral")
    public InfoRelacionLaboral360DTO consultarInformacionRelacionLaboral(@QueryParam("idRolAfiliado") Long idRolAfiliado);

    /**
     * Servicio encargado de consultar la información histórica del afiliado como beneficiario.
     * 
     * @param tipoIdAfiliado
     *        tipo de identificación de afiliado.
     * 
     * @param numeroIdAfiliado
     *        numero de identificación del afiliado.
     * 
     * @return List<HistoricoAfiBeneficiario360DTO> con la información histórica del afiliado como beneficiario.
     * 
     * @author squintero
     */
    @GET
    @Path("/consultarHistoricoAfiliadoComoBeneficiario")
    public List<HistoricoAfiBeneficiario360DTO> consultarHistoricoAfiliadoComoBeneficiario(
            @QueryParam("tipoIdAfiliado") TipoIdentificacionEnum tipoIdAfiliado, @QueryParam("numeroIdAfiliado") String numeroIdAfiliado);

    /**
     * Consulta del estado de afiliación respecto a la CCF de una persona (no empresa)
     * 
     * @param tipoIdPersona
     *        Tipo de identificación de la persona
     * @param numIdPersona
     *        Número de identificación de la persona
     * @param idPersona
     *        ID de registro de persona
     * @return <b>ConsultaEstadoAfiliacionDTO</b>
     *         DTO que contiene la respuesta de la consulta del estado de afiliación respecto a la CCF de la persona
     * @author abaquero
     */
    @GET
    @Path("/consultarEstadoAfiliacionRespectoCCF")
    public ConsultaEstadoAfiliacionDTO consultarEstadoAfiliacionRespectoCCF(
            @QueryParam("tipoIdPersona") TipoIdentificacionEnum tipoIdPersona, @QueryParam("numIdPersona") String numIdPersona,
            @QueryParam("idPersona") Long idPersona);

    /**
     * Servicio para la actualización simple de un beneficiario por reintegro de afiliado principal
     * 
     * @param beneficiario
     *        DTO que representa al entity a actualizar
     * @author abaquero
     */
    @POST
    @Path("/actualizarBeneficiarioSimple")
    public void actualizarBeneficiarioSimple(@NotNull BeneficiarioDTO beneficiario);

    /**
     * Servicio para la actualización masiva de un beneficiario
     *
     * @param beneficiario
     *        DTO que representa al entity a actualizar
     * @author abaquero
     */
    @POST
    @Path("/actualizarBeneficiarioMasivo")
    public void actualizarBeneficiarioMasivo(@NotNull BeneficiarioDTO beneficiario);

    /**
     * Consulta del estado de afiliación respecto a la CCF de una persona (no empresa) por tipo de afiliación
     * 
     * @param tipoIdPersona
     *        Tipo de identificación de la persona
     * @param numIdPersona
     *        Número de identificación de la persona
     * @param idPersona
     *        ID de registro de persona
     * @param tipoIdEmpleador
     *        Tipo de identificación de la persona
     * @param numIdEmpleador
     *        Número de identificación de la persona
     * @param idPerEmpleador
     *        ID de registro de persona
     * @param tipoAfiliado
     *        Tipo de afiliación para la consulta
     * @return <b>ConsultaEstadoAfiliacionDTO</b>
     *         DTO que contiene la información del estado de afiliación
     */
    @GET
    @Path("/consultarEstadoAfiliacionRespectoTipoAfiliacion")
    public ConsultaEstadoAfiliacionDTO consultarEstadoAfiliacionRespectoTipoAfiliacion(
            @QueryParam("tipoIdPersona") TipoIdentificacionEnum tipoIdPersona, @QueryParam("numIdPersona") String numIdPersona,
            @QueryParam("idPersona") Long idPersona, @QueryParam("tipoIdEmpleador") TipoIdentificacionEnum tipoIdEmpleador,
            @QueryParam("numIdEmpleador") String numIdEmpleador, @QueryParam("idPerEmpleador") Long idPerEmpleador,
            @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado);

    /**
     * Servicio encargado de crear y actualizar la información de roles afiliado por sustitucion patronal
     * @param listRolDTO
     *        Lista de roles a crear y actualizar
     */
    @POST
    @Path("/crearActualizarRolSustitucionPatronal")
    public void crearActualizarRolSustitucionPatronal(@NotNull List<RolAfiliadoModeloDTO> listRolDTO);

    /**
     * Consulta la información de la tarjeta ACTIVA relacionada con la información de la persona enviada como parametro 
     * @param tipoIdentificacion
     *        Tipo identificación persona
     * @param numeroIdentificacion
     *        Numero identificación persona
     * @return Información de la tarjeta en anibol, <code>null</code> en caso de excepción
     */
    @GET
    @Path("/consultarTarjetaVista360")
    public MedioDePagoModeloDTO consultarTarjetaVista360(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Consulta los beneficiarios relacionados al afiliado principal
     * @param idAfiliado
     *        identificador del afiliado principal
     * @param idRolAfiliado
     *        identificador de la afiliacion
     * @return Lista de beneficiarios asociados a la afiliado o lista vacia
     */
    @GET
    @Path("/consultarBeneficiariosAfiliacion")
    public List<BeneficiarioModeloDTO> consultarBeneficiariosAfiliacion(@QueryParam("idAfiliado") Long idAfiliado,
            @QueryParam("idRolAfiliado") Long idRolAfiliado);

    /**
     * Consulta la información de la afiliación del jefe de hogar, en caso de estar como multiafiliado se toma la información de la primera
     * afiliación
     * @param tipoIdentificacion
     *        Tipo de identificación del jefe de hogar
     * @param numeroIdentificacion
     *        Numero de identificacion del jefe de hogar
     * @return Información de afiliación
     */
    @GET
    @Path("/consultarAfiliacionJefeHogar")
    public AfiliacionJefeHogarDTO consultarAfiliacionJefeHogar(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);
    
    /**
     * (TEMPORAL) 
     */
    @GET
    @Path("/consultarAfiliadosUnGrupoFamiliar")
    public List<AfiliadoSolicitudDTO> consultarAfiliadosUnGrupoFamiliar();
    
    /**
     * Registra los datos de las personas asociadas como beneficiarios en solicitudes de afiliación abreviada.
     * @param idAfiliado
     * @param beneficiariosDTO
     * @return
     */
    @POST
    @Path("/{idAfiliado}/registrarPersonasBeneficiariosAbreviada")
    public List<Long> registrarPersonasBeneficiariosAbreviada(@NotNull @PathParam("idAfiliado") Long idAfiliado,
            @NotNull List<BeneficiarioDTO> beneficiariosDTO);
    /**
     * Registra los datos basicos de la persona asociada como beneficiario en una solicitud de afiliación abreviada.
     * @param idAfiliado
     * @param beneficiarioDTO
     * @return
     */
    @POST
    @Path("/registrarDatosPersonaBeneficiarioAfiAbreviada")
    public Long registrarDatosPersonaBeneficiarioAfiAbreviada(@QueryParam("idAfiliado")Long idAfiliado, @NotNull BeneficiarioDTO beneficiarioDTO);
    
    /**
     * Consulta el certificado escolar vigente a la persona.
     * @param idPersona
     * @return Certificado Escolar.
     */
    @GET
    @Path("/consultarCertificadoEscolarVigentePersona")
    public CertificadoEscolarBeneficiarioDTO consultarCertificadoEscolarVigentePersona(@QueryParam("idPersona")Long idPersona);
    
    /**
     * Consulta la condición de invalidez vigente a la persona
     * 
     * @author Francisco Alejandro Hoyos Rojas
     * @param idPersona
     * @return Certificado Escolar.
     */
    @GET
    @Path("/consultarCondicionInvalidezVigentePersona")
    public CondicionInvalidezModeloDTO consultarCondicionInvalidezVigentePersona(@QueryParam("idPersona")Long idPersona);
    
    /**
     * Consulta si el beneficiario se encuentra activo con respecto al afiliado
     * @param tipoIdentificacionAfiliado
     * @param numeroIdentificacionAfiliado
     * @param tipoIdentificacionBeneficiario
     * @param numeroIdentificacionBeneficiario
     * @return
     */
    @GET
    @Path("/beneficiarioActivoAfiliado")
    public Boolean beneficiarioActivoAfiliado(@QueryParam("tipoIdentificacionAfiliado") TipoIdentificacionEnum tipoIdentificacionAfiliado,
            @QueryParam("numeroIdentificacionAfiliado") String numeroIdentificacionAfiliado,
            @QueryParam("tipoIdentificacionBeneficiario") TipoIdentificacionEnum tipoIdentificacionBeneficiario,
            @QueryParam("numeroIdentificacionBeneficiario") String numeroIdentificacionBeneficiario);

    /**
     * Consulta el estado de una persona con respecto a un empleador
     * @param tipoIdentificacionAfiliado
     * @param numeroIdentificacionAfiliado
     * @param tipoIdentificacionEmpleador
     * @param numeroIdentificacionEmpleador
     * @return
     */
    @POST
    @Path("/consultarEstadoRolAfiliadoConEmpleador")
	public EstadoAfiliadoEnum consultarEstadoRolAfiliadoConEmpleador(ActivacionAfiliadoDTO datosAfiliado);

    @GET
    @Path("/consultarRolAfiliadoEmpresa")
    public Long consultarRolAfiliadoEmpresa(@QueryParam("tipoIdentificacionAfiliado") TipoIdentificacionEnum tipoIdentificacionAfiliado,
        @QueryParam("numeroIdentificacionAfiliado") String numeroIdentificacionAfiliado,@QueryParam("tipoIdentificacionEmpresa") TipoIdentificacionEnum tipoIdentificacionEmpresa,
        @QueryParam("numeroIdentificacionEmpresa") String numeroIdentificacionEmpresa);

        
    @GET
    @Path("/consultarListaResguardo")
    public List<String> consultarListaResguardo();

    @GET
    @Path("/consultarListaPuebloIndigena")
    public List<String> consultarListaPuebloIndigena();  

    @GET
    @Path("/consultarExVeteranos")
    public List<RolafiliadoNovedadAutomaticaDTO> consultarExVeteranos();

    @POST
    @Path("/actualizarAfiliadosExVeteranos")
    public void actualizarAfiliadosExVeteranos(@QueryParam("rolafiliados")List<Long> rolafiliados);

    @GET
    @Path("/calcularEdad/{edad}")
    public int calcularEdad(@PathParam("edad") Long edad);

    @GET
    @Path("/consultarCondicionInvalidezConyugeCuidador")
    public List<CondicionInvalidez> consultarCondicionInvalidezConyugeCuidador(@QueryParam("tipoIdentificacionConyuge")TipoIdentificacionEnum tipoIdentificacionConyuge, 
        @QueryParam("numeroIdentificacionConyuge") String numeroIdentificacionConyuge);

    @GET
    @Path("/{idSolicitud}/archivoAfiliacion")
    public Map<String,String> consultarArchivoAfiliacion(@PathParam("idSolicitud") Long idSolicitud);
    
    @GET
    @Path("/obtenerInfoPersona")
    public InformacionPersonaCompletaDTO ObtenerInfoPersona(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @POST
    @Path("/obtenerInfoBeneficiario")
    public InformacionBeneficiarioCompletaDTO obtenerInfoBeneficiario(ConsultaBeneficiarioRequestDTO request);  

    @POST
    @Path("/consultarBeneficiariosPorIds")
    public List<BeneficiarioModeloDTO> consultarBeneficiariosPorIds(@NotNull List<Long> idsBeneficiarios);

// ================ MASIVO TRANSFERENCIA

        @GET
        @Path("/obtenerIdsCargueTransferencia")
        public List<Long> consultarIdsGruposFamiliaresCargueMasivoTransferencia(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
        @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

        @POST
        @Path("/correccionCategoriaAfiliadoBeneficiario")
        public void correccionCategoriaAfiliadoBeneficiario();
}
