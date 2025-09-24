package com.asopagos.empresas.service;

import java.util.List;
import javax.validation.Valid;
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
import com.asopagos.dto.DatosRegistroSucursalPilaDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.LegalizacionDesembolosoProveedorModeloDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.dto.modelo.ProveedorModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validation.annotation.ValidacionActualizacion;
import com.asopagos.validation.annotation.ValidacionCreacion;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de empresas <b>Módulo:</b> Asopagos - transversal<br/>
 *
 * @author Sergio Briñez <a href="sbrinez:sbrinez@heinsohn.com.co"> sbrinez</a>
 */
@Path("empresas")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface EmpresasService {

    /**
     * <b>Descripción</b>Metodo que se encarga de consultar las sucursales
     * asociadas a una empresa
     *
     * @param idEmpresa
     *        id de la empresa
     * @return Lista se sucursales
     */
    @GET
    @Path("/{idEmpresa}/sucursales")
    public List<SucursalEmpresa> consultarSucursalesEmpresa(@PathParam("idEmpresa") Long idEmpresa);

    /**
     * <b>Descripción</b>Método que se encarga de srear sucursales
     * 
     * @param idEmpresa
     *        Id de la empresa a la que se asociarán las sucursales
     * @param listadoSucursales
     *        listado de secursales a registrar
     * @return se retorna listado con los id de las sucursales
     */
    @POST
    @Path("/{idEmpresa}/sucursales")
    public List<Long> crearSucursalEmpresa(@PathParam("idEmpresa") Long idEmpresa,
            @NotNull @Size(min = 1) @ValidacionCreacion List<SucursalEmpresa> listadoSucursales);

    /**
     * <b>Descripción</b>Método que se encarga de actualizar la información de
     * una Sucursal
     *
     * @param sucursalesEmpresa
     *        Es la infomracion de las Sucursales que se va a actualizar
     * @param idEmpresa
     *        id de la empresa al cual se le va a actualizar la Sucursal
     */
    @PUT
    @Path("/{idEmpresa}/sucursales")
    public void actualizarSucursalEmpresa(@PathParam("idEmpresa") Long idEmpresa,
            @NotNull @Size(min = 1) @Valid @ValidacionActualizacion List<SucursalEmpresa> sucursalesEmpresa);

    /**
     * <b>Descripción</b>Método encargado de consultar las ubicaciones para una
     * empresa
     *
     * @param idEmpresa
     *        Id de la empresa que se va a consultar
     * @return listado de ubicaciones de la empresa
     */
    @GET
    @Path("/{idEmpresa}/ubicaciones")
    public List<UbicacionEmpresa> consultarUbicacionesEmpresa(@PathParam("idEmpresa") Long idEmpresa);

    /**
     * <b>Descripción</b>Método encargado de un crear ubicaciones para una
     * empresa
     *
     * @param idEmpresa
     *        Id de la empresa que se va a consultar
     * @param ubicaciones
     *        listado de ubicaciones a registrar
     * @return listado de ids de las ubicaciones creadas
     */
    @POST
    @Path("/{idEmpresa}/ubicaciones")
    public List<Long> crearUbicacionesEmpresa(@PathParam("idEmpresa") Long idEmpresa,
            @NotNull @Size(min = 1) List<UbicacionEmpresa> ubicaciones);

    /**
     * <b>Descripción</b>Método encargado de actualizar las ubicaciones de una
     * empresa
     * 
     * @param idEmpresa
     *        Id de la empresa que se va a consultar
     * @param listUbicaciones
     *        listado de ubicaciones actualuzadas para registrar
     */
    @PUT
    @Path("/{idEmpresa}/ubicaciones")
    public void actualizarUbicacionesEmpresa(@PathParam("idEmpresa") Long idEmpresa,
            @NotNull @Size(min = 1) List<UbicacionEmpresa> listUbicaciones);

    /**
     * Método que se encargad de actualizar una empresa
     * @param empresa
     *        que se va a actualizar.
     */
    @POST
    @Path("/actualizarEmpresa")
    public void actualizarEmpresa(Empresa empresa);

    /**
     * Método que se encargad de actualizar una ubicación.
     * @param ubicacion
     *        que se va a actualizar.
     */
    @POST
    @Path("/actualizarUbicacion")
    public void actualizarUbicacion(Ubicacion ubicacion);

    /**
     * <b>Descripción:</b> Método que se encarga de consultar la sucursal por el Id
     * @param idSucursal,
     *        identificador de la sucursal
     * @return SucursalEmpresa
     */
    @GET
    @Path("{idSucursal}/sucursal")
    public SucursalEmpresaModeloDTO consultarSucursal(@PathParam("idSucursal") Long idSucursal);

    /**
     * Consulta el código Disponible de una Sucursal Empresa.
     * @param idEmpresa
     *        id de la empresa a consultar.
     * @return codigo para almacenar una nueva sucursal.
     */
    @GET
    @Path("{idEmpresa}/obtenerCodigoDisponibleSucursal")
    public String obtenerCodigoDisponibleSucursal(@PathParam("idEmpresa") Long idEmpresa);

    /**
     * <b>Descripción:</b> Método que permite registrar una empresa en la base de datos. De igual manera
     * si la empresa ya está registrada, retorna su id.
     * @param empresa
     *        el objeto de tipo empresa que será persistido
     * @return el id de la empresa persistida
     */
    @POST
    @Path("/crearEmpresa")
    public Long crearEmpresa(EmpresaModeloDTO empresaModeloDTO);

    /**
     * <b>Descripción:</b> Método que proces la información de la sucursal de empresa recibida por PILA.
     * @param sucursalEmpresaModeloDTO
     *        contiene los datos de sucursal recibidos.
     * 
     * @return SucursalEmpresa con los datos de la sucursal ya procesada.
     */
    @POST
    @Path("/procesarDatosSucursalPila")
    public SucursalEmpresa procesarDatosSucursalPila(DatosRegistroSucursalPilaDTO datosRegistroSucursal);

    /**
     * Servicio que crea o actualiza un proveedor en la tabla
     * <code>Oferente</code>
     * 
     * @param proveedorDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/crearActualizarProveedor")
    public ProveedorModeloDTO crearActualizarProveedor(@NotNull ProveedorModeloDTO proveedorDTO);
    
    
    /**
     * Servicio que crea o actualiza un oferente en la tabla
     * <code>Oferente</code>
     * 
     * @param oferenteDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/crearActualizarOferente")
    public OferenteModeloDTO crearActualizarOferente(@NotNull OferenteModeloDTO oferenteDTO);


    /**
     * Servicio que consulta un oferente en la tabla <code>Oferente</code>
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del oferente
     * @param numeroIdentificacion
     *        Número de identificación del oferente
     * @return Objeto <code>OferenteModeloDTO</code>
     */
    @GET
    @Path("/consultarOferente")
    public OferenteModeloDTO consultarOferente(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);
    
    
    /**
     * Servicio que consulta un Proveedor en la tabla <code>Proveedor</code>
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del Proveedor
     * @param numeroIdentificacion
     *        Número de identificación del Proveedor
     * @return Objeto <code>ProveedorModeloDTO</code>
     */
    @GET
    @Path("/consultarProveedor")
    public ProveedorModeloDTO consultarProveedor(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    
    /**
     * Servicio que consulta un Proveedor en la tabla <code>Proveedor</code>
     * 
     * @param idlegalizacionDesembolosoProveedor
     * @param numeroRadicacion
     * @param tipoIdentificacion
     *        Tipo de identificación del Proveedor
     * @param numeroIdentificacion
     *        Número de identificación del Proveedor
     * @return Objeto <code>ProveedorModeloDTO</code>
     */
    // 49270BORRADOKT comentado ya que el glpi 49270 contempla la tabla LegalizacionDesembolosoProveedor pero esta nunca se inserta y no se utiliza
   /* @GET 
    @Path("/consultarLegalizacionDesembolsoProveedor")
    public List<LegalizacionDesembolosoProveedorModeloDTO> consultarLegalizacionDesembolsoProveedor(@QueryParam("idlegalizacionDesembolosoProveedor") String idlegalizacionDesembolosoProveedor,
            @QueryParam("numeroRadicacion") String numeroRadicacion,@QueryParam("sldId") String sldId);
*/
    
    /**
     * Servicio que crea o actualiza un proveedor en la tabla
     * <code>Oferente</code>
     * 
     * @param legalizacionDesembolosoProveedorModeloDTO
     *        Información del registro a crear/actualizar
     * @return El registro actualizado
     */
    @POST
    @Path("/crearActualizarLegalizacionDesembolsoProveedor")
    public LegalizacionDesembolosoProveedorModeloDTO crearActualizarLegalizacionDesembolsoProveedor(@NotNull LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedorModeloDTO);
    
    

    /**
     * Servicio encargado de consultar la sucursal de empresa dados su codigo y la empresa a la cual pertenece
     * 
     * @param codigoSucursal
     *        es el codigo de la sucursal
     * 
     * @param tipoIdAportante
     *        el tipo de identificación de la empresa
     * 
     * @param numeroIdAportante
     *        el numero de identificación de la empresa
     * 
     * @return SucursalEmpresaModeloDTO con los datos de la sucursal
     */
    @GET
    @Path("/obtenerSucursalEmpresa")
    public SucursalEmpresaModeloDTO obtenerSucursalEmpresa(@QueryParam("codigoSucursal") String codigoSucursal,
            @QueryParam("tipoIdAportante") TipoIdentificacionEnum tipoIdAportante,
            @QueryParam("numeroIdAportante") String numeroIdAportante);

    /**
     * Método que se encarga de consultar una empresa dado el tipo de identificación y el número de identificación
     * @param tipoIdentificacion
     *        valor del tipo de identificación
     * @param numeroIdentificacion
     *        valor del número de identificación
     * 
     * @author rlopez
     * @return DTO con la información de la empresa
     */
    @GET
    @Path("/consultar")
    public List<EmpresaModeloDTO> consultarEmpresa(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("razonSocial") String razonSocial);

    /**
     * Servicio que consulta un oferente en la tabla <code>Oferente</code>
     * 
     * @param razonSocialNombre
     *        razon social del oferente
     * 
     * @return Objeto <code>OferenteModeloDTO</code>
     */
    @GET
    @Path("/consultarOferentePorRazonSocial")
    public List<OferenteModeloDTO> consultarOferentePorRazonSocial(@QueryParam("razonSocialNombre") String razonSocialNombre);

    /**
     * Servicio que consulta un proveedor en la tabla <code>Oferente</code>
     * 
     * @param razonSocialNombre
     *        razon social del proveedor
     * 
     * @return Objeto <code>ProveedorModeloDTO</code>
     */
    @GET
    @Path("/consultarProveedorPorRazonSocial")
    public List<ProveedorModeloDTO> consultarProveedorPorRazonSocial(@QueryParam("razonSocialNombre") String razonSocialNombre);

    
    /**
     * <b>Descripción</b>Método que se encarga de crear una sucursal para aportante creado por PILA
     * 
     * @param idEmpresa
     *        Id de la empresa a la que se asociarán las sucursales
     * @param sucursal
     *        DTO de la sucursal a crear
     * @return <b>Long</b>se retorna el id de la sucursal creada
     * @author abaquero
     */
    @POST
    @Path("/{idEmpresa}/sucursalPila")
    public void crearSucursalEmpresaPila(@PathParam("idEmpresa") Long idEmpresa, @NotNull SucursalEmpresaModeloDTO sucursalDTO);

    /**
     * Servicio para la consulta de una empresa por medio de su ID
     * @param idEmpresa
     *        ID de la empresa consultada
     * @return <b>EmpresaModeloDTO</b>
     *         DTO con los datos de la empresa consultada
     * @author abaquero
     */
    @GET
    @Path("/consultarEmpresaPorId")
    public EmpresaModeloDTO consultarEmpresaPorId(@QueryParam("idEmpresa") @NotNull Long idEmpresa);

    /**
     * Servicio para consulta de representante legal asociado a la empresa
     * @param idEmpresa
     *        Identificador de la empresa
     * @param titular
     *        Indicador si es representante suplente o principal
     * @return Información de persona representante o <code>null</code> en caso de no encontrar representantes para la empresa indicada.
     */
    @GET
    @Path("/representantesLegales")
    public Persona consultarRepresentantesLegalesEmpresa(@NotNull @QueryParam("idEmpresa") Long idEmpresa,
            @NotNull @QueryParam("titular") Boolean titular);
    
    /**
     * Servicio encargado de consultar la sucursal de empresa dados su codigo y la empresa a la cual pertenece
     * 
     * @param codigoSucursal
     *        es el codigo de la sucursal
     * 
     * @param tipoIdEmpleador
     *        el tipo de identificación de la empresa
     * 
     * @param numeroIdEmpleador
     *        el numero de identificación de la empresa
     * 
     * @return SucursalEmpresaModeloDTO con los datos de la sucursal
     */
    @GET
    @Path("/obtenerSucursalEmpresaByCodigoAndEmpleador")
    public SucursalEmpresaModeloDTO obtenerSucursalEmpresaByCodigoAndEmpleador(
        @QueryParam("codigoSucursal") String codigoSucursal,
            @QueryParam("tipoIdEmpleador") TipoIdentificacionEnum tipoIdEmpleador,
            @QueryParam("numeroIdEmpleador") String numeroIdEmpleador) ;
    
    /**
     * Servicio encargado de consultar la sucursal de empresa dados su nombre y la empresa a la cual pertenece
     * 
     * @param nombreSucursal
     *        es el nombre de la sucursal
     * 
     * @param tipoIdEmpleador
     *        el tipo de identificación de la empresa
     * 
     * @param numeroIdEmpleador
     *        el numero de identificación de la empresa
     * 
     * @return SucursalEmpresaModeloDTO con los datos de la sucursal
     */
    @GET
    @Path("/obtenerSucursalEmpresaByNombreAndEmpleador")
    public SucursalEmpresaModeloDTO obtenerSucursalEmpresaByNombreAndEmpleador(
        @QueryParam("nombreSucursal") String nombreSucursal,
            @QueryParam("tipoIdEmpleador") TipoIdentificacionEnum tipoIdEmpleador,
            @QueryParam("numeroIdEmpleador") String numeroIdEmpleador) ;
}
