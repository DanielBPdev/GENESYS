package com.asopagos.comunicados.service;

import java.util.List;
import java.util.Map;
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

import com.asopagos.comunicados.dto.InfoJsonTemporalDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.JsonPayloadDatoTemporalComunicadoDTO;
import com.asopagos.dto.modelo.ComunicadoModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalComunicado;
import com.asopagos.entidades.ccf.comunicados.Comunicado;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.comunicados.PlantillaProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.notificaciones.dto.ComunicadoPersistenciaDTO;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Interfaz de servicios Web REST para adminsitración de
 * comunicados<br/>
 * <b>Historia de Usuario:</b> HU 111-331- gestion de comunicados <br/>
 *
 * @author <a href="mailto:jerodriguez@heinsohn.com.co"> jerodriguez</a>
 */
@Path("comunicados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ComunicadosService {

    /**
     * <b>Descripción</b>Método que se encarga de crear un comunicado<br/>
     * <code>comunicado es el objeto que se va a guardar </code>
     *
     * @param comunicado informaciòn a registrar
     * @return Retorna el id del nuevo comunicado
     */
    @POST
    public Long crearComunicado(Comunicado comunicado);

    /**
     * <b>Descripción</b>Método encargado de retornar la plantilla comunicado por id Instancia proceso
     * con todas sus variables resueltas dentro de la información de email y en
     * su posible adjunto<br/>
     *
     * @param etiquetaPlantillaComunicadoEnum Etiqueta de la plantilla a consultar
     * @param idSolicitud,                    el id que representa la solicitud de una persona, empleador o
     *                                        novedad tanto web como presencial
     * @param map,Variables                   a resolver
     * @return PlantillaComunicado con las variables del comunicado ya resueltas
     */
    @POST
    @Path("/{EtiquetaPlantillaComunicadoEnum}/resolverPlantillaVariablesComunicado/{idInstancia}")
    public PlantillaComunicado resolverPlantillaVariablesComunicado(
            @PathParam("EtiquetaPlantillaComunicadoEnum") @NotNull EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            @PathParam("idInstancia") @NotNull Long idInstancia, Map<String, Object> map);

    /**
     * <b>Descripción</b>Método encargado de retornar la plantilla comunicado por id de solicitud
     * con todas sus variables resueltas dentro de la información de email y en
     * su posible adjunto<br/>
     *
     * @param etiquetaPlantillaComunicadoEnum Etiqueta de la plantilla a consultar
     * @param idSolicitud,                    el id que representa la solicitud de una persona, empleador o
     *                                        novedad tanto web como presencial
     * @param map,                            Variables a resolver
     * @return PlantillaComunicado con las variables del comunicado ya resueltas
     */
    @POST
    @Path("/{EtiquetaPlantillaComunicadoEnum}/resolverPlantillaVariablesComunicadoPorSolicitud/{idSolicitud}")
    public PlantillaComunicado resolverPlantillaVariablesComunicadoPorSolicitud(
            @PathParam("EtiquetaPlantillaComunicadoEnum") @NotNull EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            @PathParam("idSolicitud") @NotNull Long idSolicitud, Map<String, Object> map);


    /**
     * Servicio encargado de resolver la las constantes del comunicado sin
     * idInstancia y IdSolicitud
     *
     * @param etiquetaPlantillaComunicadoEnum, Etiqueta a resolverConstantes
     * @param map
     * @return retorna la plantilla comunicado
     */
    @POST
    @Path("/{EtiquetaPlantillaComunicadoEnum}/resolverPlantillaConstantesComunicado")
    public PlantillaComunicado resolverPlantillaConstantesComunicado(
            @PathParam("EtiquetaPlantillaComunicadoEnum") @NotNull EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            ParametrosComunicadoDTO parametros);

    /**
     * Servicio que se encarga de consultar un comunicado por id de comunicado.
     *
     * @param idComunicado id del comunicado.
     * @return
     */
    @GET
    @Path("/{idComunicado}/consultarComunicado")
    public ComunicadoModeloDTO consultarComunicado(@PathParam("idComunicado") Long idComunicado);

    /**
     * Servicio que consulta un comunicado por id de solicitud.
     *
     * @param idSolicitud
     * @return comunicadoModeloDTO
     */
    @GET
    @Path("/{idSolicitud}/consultarComunicadoPorSolicitud")
    public List<ComunicadoModeloDTO> consultarComunicadoPorSolicitud(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que persiste un objeto del tipo DatoTemporalComunicado.
     *
     * @param datoTemporalComunicado objeto con la info temporal para el comunicado.
     */
    @POST
    @Path("/guardarDatoTemporalComunicado")
    public void guardarDatoTemporalComunicado(@NotNull DatoTemporalComunicado datoTemporalComunicado);

    /**
     * Servicio que obtiene los datos para el comunicado dado un id de tarea.
     *
     * @param idTarea identificador de la tarea a la cual pertenecen los datos a buscar.
     * @return DatoTemporalComunicado con la información para el comunicado.
     */
    @POST
    @Path("/obtenerDatoTemporalComunicado")
    public DatoTemporalComunicado obtenerDatoTemporalComunicado(@NotNull Long idTarea);

    /**
     * Servicio que eliminar el registro de un DatoTemporalComunicado dado el id de tarea.
     *
     * @param idTarea identificador de tarea para el cual se busca eliminar el registro
     *                de DatoTemporalComunicado.
     */
    @POST
    @Path("/eliminarDatoTemporalComunicado")
    public void eliminarDatoTemporalComunicado(@NotNull Long idTarea);

    /**
     * Servicio que guarda un comunicado en el storage, obtiene la información del archivo almacenado Y EL FLUJO DE BYTE
     *
     * @param tipoTransaccion        Tipo de transacción
     * @param plantilla              Plantilla del comunicado
     * @param parametroComunicadoDTO Parámetros del comunicado
     * @return Identificador del archivo guardardo en el ECM
     */
    @POST
    @Path("/guardarObtenerComunicadoECM")
    public InformacionArchivoDTO guardarObtenerComunicadoECM(@QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion,
                                                             @QueryParam("plantilla") EtiquetaPlantillaComunicadoEnum plantilla, @NotNull ParametrosComunicadoDTO parametroComunicadoDTO);

    /**
     * Servicio que guarda un comunicado en el storage y a su vez obtiene la información complementaria del archivo almacenado SIN EL ARCHIVO ESPECIFICO
     *
     * @param tipoTransaccion        Tipo de transacción
     * @param plantilla              Plantilla del comunicado
     * @param parametroComunicadoDTO Parámetros del comunicado
     * @return Identificador del archivo guardardo en el ECM
     */
    @POST
    @Path("/guardarObtenerInfoArchivoComunicado")
    public InformacionArchivoDTO guardarObtenerInfoArchivoComunicado(@QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion,
                                                                     @QueryParam("plantilla") EtiquetaPlantillaComunicadoEnum plantilla, @NotNull ParametrosComunicadoDTO parametroComunicadoDTO);

    /**
     * Servicio que guarda un comunicado en el ECM
     *
     * @param tipoTransaccion        Tipo de transacción
     * @param plantilla              Plantilla del comunicado
     * @param parametroComunicadoDTO Parámetros del comunicado
     * @return Identificador del archivo guardardo en el ECM
     */
    @POST
    @Path("/guardarComunicadoECM")
    public String guardarComunicadoECM(@QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion,
                                       @QueryParam("plantilla") EtiquetaPlantillaComunicadoEnum plantilla, @NotNull ParametrosComunicadoDTO parametroComunicadoDTO);

    /**
     * Servicio encargado de retornar las notificaciones enviadas a una determinada persona
     *
     * @param etiquetaPlantillaComunicadoEnum
     * @param numIdentificacion
     * @param tipoIdentificacion
     * @param fechaEnvio
     * @return
     */
    @POST
    @Path("/obtenerNotificacion")
    public List<Object> obtenerNotificacion(
            @QueryParam("etiquetaPlantillaComunicadoEnum") EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            @QueryParam("numIdentificacion") String numIdentificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("fechaEnvio") String fechaEnvio,
            @QueryParam("esEmpleador") Boolean esEmpleador);

    /**
     * Servicio que obtiene la lista de comunicados asociados a un número de radicado
     *
     * @param numeroRadicado Número de radicado
     * @return La lista de comunicados
     */
    @GET
    @Path("/consultarComunicadoPorRadicado")
    List<ComunicadoModeloDTO> consultarComunicadoPorRadicado(@QueryParam("numeroRadicado") String numeroRadicado);

    /**
     * Servicio que se encarga de persitir un comunicado cunado su medio de comunicado es impreso
     *
     * @param notificacion
     * @param userDTO
     * @param plantilla
     * @return
     */
    @POST
    @Path("/persistirComunicado")
    public void persistirComunicado(NotificacionDTO notificacion, @Context UserDTO userDTO);

    /**
     * Servicio encargado de retornar las notificaciones enviadas a una determinada persona en el proceso PILA
     *
     * @param etiquetaPlantillaComunicadoEnum
     * @param numIdentificacion
     * @param tipoIdentificacion
     * @param fechaEnvio
     * @return
     */
    @POST
    @Path("/obtenerNotificacionPila")
    public List<Object> obtenerNotificacionPila(@QueryParam("etiquetaPlantillaComunicadoEnum") String etiquetaPlantillaComunicadoEnum,
                                                @QueryParam("numIdentificacion") String numIdentificacion,
                                                @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("fechaEnvio") String fechaEnvio,
                                                @QueryParam("esEmpleador") Boolean esEmpleador);

    /**
     * Servicio qye retorna los comunicados asoociados a una persona o empleador
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param proyectoRegistrado
     * @param esEmpleador
     * @return
     */
    @GET
    @Path("/comunicadosEnviados")
    public List<Object> comunicadosEnviados(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                            @QueryParam("numIdentificacion") String numeroIdentificacion, @QueryParam("esEmpleador") Boolean esEmpleador);

    /**
     * Obtiene el correo del usuario Front que radicó la solicitud.
     *
     * @param idSolicitud Id de la solicitud radicada
     * @return email asociado al usuario front
     */
    @GET
    @Path("/obtenerCorreoFront")
    public List<String> obtenerCorreoFront(@NotNull @QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Consulta el dato temporal asociado al comunicado. En caso de no existir genera uno nuevo
     *
     * @param info
     * @return Entidad DatoTemporalComunicado
     */
    @POST
    @Path("/obtenerGenerarDatoTemporalComunicado")
    public DatoTemporalComunicado obtenerGenerarDatoTemporalComunicado(InfoJsonTemporalDTO info);

    /**
     * Construye y persiste un comunicado a partir de la información de ComunicadoPersistenciaDTO
     * Creando una nueva transacción que garantice que el comunicado se persiste previo a cualquier
     * otra operacion/proceso que necesite asociarse al mismo.
     *
     * @param comunicadoPersistencia
     * @param userDTO
     * @return
     */
    @POST
    @Path("/construirPersistirComunicado")
    public Comunicado construirPersistirComunicado(ComunicadoPersistenciaDTO comunicadoPersistencia, @Context UserDTO userDTO);

    /**
     * Método que genera el comunicado consolidado de cartera y retorna el flujo de bytes para la
     * generacion del reporte
     *
     * @param etiquetaPlantillaComunicadoEnum
     * @param idSolicitud
     * @param map
     * @return
     */
    @POST
    @Path("/{EtiquetaPlantillaComunicadoEnum}/generarComunicadoCarteraConsolidado/{idSolicitud}")
    @Produces({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
    public byte[] generarComunicadoCarteraConsolidado(@PathParam("EtiquetaPlantillaComunicadoEnum") @NotNull EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                      @PathParam("idSolicitud") @NotNull Long idSolicitud, Map<String, Object> map);

    /**
     * Servicio que retorna la plantila comunicaso de consolidado de cartera completamente resuelta
     *
     * @param plantillaComunicado
     * @param etiquetaPlantillaComunicadoEnum
     * @param idSolicitud
     * @param map
     * @return
     */
    @POST
    @Path("/{EtiquetaPlantillaComunicadoEnum}/resolverPlantillaCarteraConsolidadoComunicado")
    public PlantillaComunicado resolverPlantillaCarteraConsolidadoComunicado(
            @PathParam("EtiquetaPlantillaComunicadoEnum") @NotNull EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, Map<String, Object> map);

    /**
     * Servicio encargado de generar y guardar el dato temporal del comunicado
     *
     * @param jsonPayloadDatoTemporalComunicadoDTO Objeto con toda la información necesaria para crear el dato temporal del comunicado
     * @return jsonPayload del dato temporal del comunicado
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     */
    @POST
    @Path("/generarYGuardarDatoTemporalComunicado")
    public String generarYGuardarDatoTemporalComunicado(@NotNull JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO);

    @POST
    @Path("/guardarObtenerComunicadoPrescripcion")
    public InformacionArchivoDTO guardarObtenerComunicadoPrescripcion(
            @QueryParam("plantilla") EtiquetaPlantillaComunicadoEnum plantilla, @QueryParam("idCuentaAdmonSubsidio") Long idCuentaAdmonSubsidio);
}
