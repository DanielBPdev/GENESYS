package com.asopagos.tareashumanas.service;

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
import com.asopagos.enumeraciones.core.EstadoTareaEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.dto.TareaDTO;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para la invocación de
 * servicios relacionados con tareas humanas en el BPM <b>Historia de
 * Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("tareasHumanas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TareasHumanasService {

    /**
     * Método que retorna las tareas que están pendientes por asignación y que
     * por lo tanto son susceptibles de ser asignadas al usuario identificado
     * por <code>usuario</code>
     * 
     * @param estados
     *            Lista de estados para filtrar la lista de tareas
     * @param userDTO
     * @return La lista de tareas pendientes por asignación
     */
    @GET
    @Path("pendientes")
    public List<TareaDTO> obtenerTareasPendientes(@QueryParam("estado") List<EstadoTareaEnum> estados,
            @Context UserDTO userDTO);

    /**
     * Método que retorna las tareas que están asignadas al usuario identificado
     * por <code>usuario</code>
     * 
     * @param estados
     *            Lista de estados para filtrar la lista de tareas
     * @param userDTO
     * @return La lista de tareas asignadas al usuario
     */
    @GET
    @Path("asignadas")
    public List<TareaDTO> obtenerTareasAsignadas(@QueryParam("estado") List<EstadoTareaEnum> estados,
            @Context UserDTO userDTO);
    
    /**
     * Método que retorna las tareas que están asignadas al usuario identificado
     * por <code>usuario</code>
     * 
     * @param nombreUsuario
     * @param userDTO
     * @return La lista de tareas asignadas al usuario
     */
    @GET
    @Path("asignadas/usuario/{nombreUsuario}")
    public List<TareaDTO> obtenerTareasAsignadasUsuario(@PathParam("nombreUsuario") String nombreUsuario,@Context UserDTO userDTO);

    /**
     * Método que retorna las tareas que están pendientes por asignación y
     * también las que están asignadas al usuario identificado por
     * <code>usuario</code>
     * 
     * @param estados
     *            Lista de estados para filtrar la lista de tareas
     * @param userDTO
     * @return La lista de tareas pendientes por asignación o asignadas al
     *         usuario
     */
    @GET
    @Path("pendientesOAsignadas")
    public List<TareaDTO> obtenerTareasPendientesOAsignadas(@QueryParam("estado") List<EstadoTareaEnum> estados,
            @Context UserDTO userDTO);

    /**
     * Método que permite reclamar la tarea para el usuario recibido
     * 
     * @param idTarea
     * @param userDTO
     * @return
     */
    @POST
    @Path("{idTarea}/iniciar")
    public Map<String, Object> iniciarTarea(@PathParam("idTarea") Long idTarea, @Context UserDTO userDTO);

    /**
     * Método que permite reclamar la tarea para el usuario recibido
     * 
     * @param idTarea
     * @param userDTO
     * @return
     */
    @POST
    @Path("{idTarea}/reclamar")
    public Map<String, Object> reclamarTarea(@PathParam("idTarea") Long idTarea, @Context UserDTO userDTO);

    /**
     * Método que permite obtener el detalle de una tarea
     * 
     * @param idTarea
     * @param userDTO
     * @return
     */
    @GET
    @Path("{idTarea}/detalle")
    public Map<String, Object> obtenerDetalleTarea(@PathParam("idTarea") Long idTarea,@Context UserDTO userDTO);

    /**
     * Método que permite obtener el id de la tarea que se encuentra activa para
     * una instancia de proceso
     * 
     * @param idInstanciaProceso
     * @param userDTO
     * @return
     */
    @GET
    @Path("obtenerTareaActiva")
    public Map<String, Object> obtenerTareaActiva(@NotNull @QueryParam("idInstanciaProceso") Long idInstanciaProceso,@Context UserDTO userDTO);

    @GET
    @Path("obtenerTareaActivaSat")
    public Map<String, Object> obtenerTareaActivaSat(@NotNull @QueryParam("idInstanciaProceso") Long idInstanciaProceso, @NotNull @QueryParam("nombreUsuario") String nombreUsuario, @Context UserDTO userDTO);

    /**
     * Método genérico para la terminación de tareas
     * 
     * @param idTarea
     * @param params
     * @param userDTO
     */
    @POST
    @Path("{idTarea}/terminar")
    public void terminarTarea(@PathParam("idTarea") Long idTarea, Map<String, Object> params, @Context UserDTO userDTO);
    
    /**
     * Método que resigna una tarea
     * @param idTarea Identificador de la tarea a reasignar
     * @param usuario Usuario al que se reasigna la tarea
     * @param userDTO
     */
    @POST
    @Path("{idTarea}/reasignar")
    public void reasignarTarea(@PathParam("idTarea")Long idTarea, @QueryParam("usuario") String usuario, @Context UserDTO userDTO);
    
    /**
     * Servicio que retorna la ultima tarea terminada en un proceso.
     * @param idProceso
     * @param userDTO
     * @return Retorna la ultima tarea terminada en un proceso. Si no 
     * existen tareas terminadas retorna null.
     */
    @GET
    @Path("{idProceso}/last")
    public TareaDTO obtenerUltimaTareaTerminada(@PathParam("idProceso")Long idProceso, @Context UserDTO userDTO);
    
    /**
     * Método que retorna las tareas que están asignadas a los usuarios enviados
     * por <code>usuario</code>
     * 
     * @param nombreUsuario
     * @param userDTO
     * @return La lista de tareas asignadas al usuario
     */
    @GET
    @Path("asignadas/usuarios")
    public List<TareaDTO> obtenerTareasAsignadasUsuarios(@QueryParam("taskOwner") List<String> nombreUsuario, @Context UserDTO userDTO);

    /**
     * Servicio que retorna la tarea activa de la instancia enviada como parámetro
     * @param idInstanciaProceso
     * @param userDTO
     * @return 
     */
    @GET
    @Path("obtenerTareaActivaInstancia")
    public TareaDTO obtenerTareaActivaInstancia(@NotNull @QueryParam("idInstanciaProceso") Long idInstanciaProceso, @Context UserDTO userDTO);
    
    /**
     * Servicio que retorna la lista de tareas finalizadas por el usuario
     * @param userDTO
     * @return 
     */
    @GET
    @Path("finalizadas")    
    public List<TareaDTO> obtenerTareasFinalizadas(@Context UserDTO userDTO);
    
    /**
     * Método genérico para la suspensión de tareas
     * (la tarea debe tener estado READY, RESERVED o InPROGRESS)
     * 
     * @param idTarea
     * @param params
     * @param userDTO
     */
    @POST
    @Path("{idTarea}/suspender")
    public void suspenderTarea(@PathParam("idTarea") Long idTarea, Map<String, Object> params, @Context UserDTO userDTO);
    
    /**
     * Método genérico para retomar la tarea a su estado previo a la suspensión.
     * 
     * @param idTarea
     * @param params
     * @param userDTO
     */
    @POST
    @Path("{idTarea}/retomar")
    public void retomarTarea(@PathParam("idTarea") Long idTarea, Map<String, Object> params, @Context UserDTO userDTO);
    
    /**
     * Método genérico para consultar la información asociada a la tarea
     * 
     * @param idTarea
     * @param params
     * @param userDTO
     */
    @GET
    @Path("{idTarea}/consultarEstadoTarea")
    public String consultarEstadoTarea(@PathParam("idTarea") Long idTarea, @Context UserDTO userDTO);
    
    /**
     * Método genérico para la activacion de tareas
     * (la tarea debe tener estado CREATED)
     * 
     * @param idTarea
     * @param params
     * @param userDTO
     */
    @POST
    @Path("{idTarea}/activar")
    public void activarTarea(@PathParam("idTarea") Long idTarea, Map<String, Object> params, @Context UserDTO userDTO);
    
    /**
     * Consulta las tareas en estado CREATED sin actualOwner_id
     * @param userDTO
     */
    @POST
    @Path("/obtenerTareasCreadasSinPropietario")
    public List<TareaDTO> obtenerTareasCreadasSinPropietario(@Context UserDTO userDTO); 
}