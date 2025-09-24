package com.asopagos.entidaddescuento.service;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoDTO;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import com.asopagos.entidaddescuento.dto.ArchivoSalidaDescuentoSubsidioModeloDTO;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion: </b> Clase que representa el servicio encargado de gestionar las entidades de descuento <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

@Path("entidadDescuento")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface EntidadDescuentoService {

    /**
     * <b>Descripción: </b>Método que se encarga de consultar una entidad de descuento por su código
     * @param codigoEntidadDescuento
     *        valor del codigo de la entidad
     * @return DTO con la información de la entidad
     */
    @GET
    @Path("/consultarEntidadDescuento/{codigoEntidadDescuento}")
    public EntidadDescuentoModeloDTO consultarEntidadDescuento(@NotNull @PathParam("codigoEntidadDescuento") String codigoEntidadDescuento);

    /**
     * <b>Descripción: </b>Método que se encarga de consultar una entidad de descuento por su identificador
     *
     * @author rlopez
     *
     * @param codigoEntidadDescuento
     *        valor del codigo de la entidad
     * @return DTO con la información de la entidad
     */
    @GET
    @Path("/consultarEntidadDescuentoId/{idEntidadDescuento}")
    public EntidadDescuentoModeloDTO consultarEntidadDescuentoId(@PathParam("idEntidadDescuento") Long idEntidadDescuento);

    /**
     * <b>Descripción: </b>Método que se encarga de consultar una entidad de descuento por su nombre y codigo
     * @param nombre
     *        valor para el nombre de la entidad
     * @param codigo
     *        valor para el codigo de la entidad
     * @return lista de DTO con la información de las entidades
     */
    @GET
    @Path("consultarEntidadDescuentoPorNombreCodigo")
    public List<EntidadDescuentoModeloDTO> consultarEntidadDescuentoPorNombreCodigo(@QueryParam("nombre") String nombre,
                                                                                    @QueryParam("codigo") String codigo);

    /**
     * <b>Descripción: </b>Método que se encarga de consultar las prioridades disponibles para las entidades de descuento
     *
     * @return Lista de prioridades disponibles
     */
    @GET
    @Path("consultarPrioridadesDisponibles")
    public List<String> consultarPrioridadesDisponibles();

    /**
     * <b>Descripción:</b>Método que se encarga de crear la entidad de descuento si no existe el identificador
     * de negocio en la base de datos; si existe, se editara dicha entidad de descuento.
     *
     * @param entidadDescuentoModeloDTO
     *        Entidad de descuento para ser creada o editada
     *
     * @return id de la entidad de descuento.
     */
    @POST
    @Path("gestionarEntidadDescuento")
    public String gestionarEntidadDescuento(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO);

    /**
     * <b>Descripción:</b>Metodo que encarga de traer el código maximo encontrado de las entidades de descuento
     * y sumarle un uno.
     *
     * @return próximo código a ser asignado a una entidad de descuento
     */
    @GET
    @Path("buscarProximoCodigoEntidadDescuento")
    public Long buscarProximoCodigoEntidadDescuento();

    /**
     * Método encargado de realizar la validación del archivo de descuentos
     * @param informacionArchivoDTO
     *        DTO con la información del archivo
     * @param userDTO
     *        DTO con la información del usuario
     * @author rlopez
     * @return DTO con el resultado de la validación del archivo
     */
    @POST
    @Path("/validarEstructuraArchivoDescuentos/{idTrazabilidad}")
    public ResultadoValidacionArchivoDTO validarEstructuraArchivoDescuentos(@NotNull InformacionArchivoDTO informacionArchivoDTO,
                                                                            @NotNull @PathParam("idTrazabilidad") Long idTrazabilidad, @Context UserDTO userDTO);

    /**
     * Método que se encarga de registrar la información de trazabilidad del archivo de descuentos
     * @param informarcionTrazabilidadDTO
     *        DTO con la información de trazabilidad
     * @return identificador de la trazabilidad registrada
     */
    @POST
    @Path("/archivoDescuentos/gestionarTrazabilidad")
    public Map<String, Object> gestionarTrazabilidadArchivoDescuentos(
            @NotNull @Valid ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO);

    /**
     * Método que permite obtener todos los archivos enviados por las entidades de descuento
     * @return lista de DTO´s con la información de los archivos
     */
    @GET
    @Path("/archivoDescuentos/obtenerTodos")
    public List<ArchivoEntidadDescuentoDTO> obtenerArchivosDescuento();

    /**
     * Método que permite obtener la lista de registros asociados a la trazabilidad de los archivos de descuento con estado CARGADO
     * @author rlopez
     * @return DTO´s con la información de trazabilidad
     */
    @GET
    @Path("/archivoDescuentos/obtenerTrazabilidad")
    public List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> obtenerInformacionTrazabilidad(
            @QueryParam("nombresArchivos") List<String> nombresArchivos);

    /**
     * Método que se encarga de generar los archivos de resultado para un proceso de liquidación
     * @author rlopez
     * @param idLiquidacion
     *        identificador de la liquidación asociada a la pignoración ejecutada
     */
    @POST
    @Path("/archivoDescuentos/generarResultados")
    public InformacionArchivoDTO generarResultadosArchivoDescuento(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO trazabilidadDTO);

    /**
     * Método que permite obtener la lista de indentificadores de Entidades de descuento asociados a un proceso de radicación
     * mediante la definición de su correspondiente número de radicación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author rlopez
     * @return lista de indentificadores de trazabilidad
     */
    @GET
    @Path("/archivoDescuentos/trazabilidad/{numeroRadicacion}")
    public List<Long> obtenerEntidadesDescuentoRadicacion(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Método que permite acutalizar la información de los archivos de descuento para una liquidación cancelada
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author rlopez
     */
    @PUT
    @Path("/acutalizarArchivosDescuento/liquidacionCancelada/{numeroRadicacion}")
    public void actualizarArchivosDescuentoLiquidacionCancelada(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que permite registrar el archivo de salida de descuentos de subsidios.
     * @param infoArchivo
     * @author flopez
     */
    @POST
    @Path("/archivoDescuentos/crearRegistroArchivoSalida")
    public void crearRegistroArchivoSalidaDescuento(ArchivoSalidaDescuentoSubsidioModeloDTO infoArchivo);

    /**
     * Servicio que obtiene el Archivo de Salida de Descuentos para una solicitud y entidad de descuento
     * @param numeroRadicacion
     * @param idEntidadDescuento
     * @return
     */
    @GET
    @Path("/archivoDescuentos/obtenerArchivosSalidaDescuentos")
    public String obtenerArchivosSalidaDescuentos(@QueryParam("numeroRadicacion")String numeroRadicacion, @QueryParam("idEntidadDescuento") Long idEntidadDescuento);

    /**
     * Servicio que permite ejecutar el SP ACTUALIZAR_DESCUENTOS_NUEVO_ARCHIVO para actualizar los archivos sin descuentos pendientes
     * @param idArchivo
     * @param codigoEntidad
     * @return
     */
    @GET
    @Path("/archivoDescuentos/ejecutarActualizacionArchivosDescuento")
    public Long ejecutarActualizacionArchivosDescuento(@QueryParam("idArchivo") Long idArchivo, @QueryParam("codigoEntidad") Long codigoEntidad);
}
