package com.asopagos.solicitud.composite.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.FiltroSolicitudDTO;
import com.asopagos.dto.ResultadoConsultaSolicitudDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.afiliaciones.RemisionBackDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.solicitud.composite.dto.DatosAbortarSolicitudDTO;
import com.asopagos.solicitud.composite.dto.DatosSeguimientoSolicitudesDTO;

import javax.ws.rs.PathParam;

/**
 * <b>Descripci�n:</b> Interfaz de servicios Web REST para composici�n de
 * Solicitudes <b>Historia de Usuario:</b> HU-TRA-114
 * 
 * @author Jerson zambrano <jzambrano@heinsohn.com.co>
 */
@Path("solicitudComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SolicitudCompositeService {

    /**
     * M�todo que permite cambiar el estado a una solicitud y terminar
     * sus tareas pendientes en el bpm
     * 
     * @param inDTO
     * @param userDTO
     */
    @POST
    @Path("cambiarEstadoSolicitudFinalizarGestion")
    public void cambiarEstadoSolicitudFinalizarGestion(CambiarEstadoSolicitudFinGestionDTO inDTO, @Context UserDTO userDTO);

    /**
     * M�todo que permite cambiar el estado a una solicitud y terminar
     * sus tareas pendientes en el bpm
     * 
     * @param inDTO
     * @param userDTO
     */
    @POST
    @Path("cambiarEstadoSolicitudAbortarProceso")
    public void cambiarEstadoSolicitudAbortarProceso(DatosAbortarSolicitudDTO inDTO, @Context UserDTO userDTO);

    /**
     * Servicio que permite consultar las distintas solicitudes en proceso, asociadas al usuario logueado.
     * 
     * @param userDTO
     *        Usuario del contexto de seguridad.
     * @return Los datos de las solicitudes en proceso.
     */
    @GET
    @Path("consultarSolicitudesGeneralesEnProceso")
    public DatosSeguimientoSolicitudesDTO consultarSolicitudesGeneralesEnProceso(@Context UserDTO userDTO);
    
    /**
     * 
     * Servicio que permite consultar las distintas solicitude que se encuentran en el momento a traves del n�mero de radicado.
     * 
     * @param numeroRadicado
     * @return
     */
    @GET
    @Path("consultarSolicitudeAdmin")
    public List<SolicitudDTO> consultarSolicitudeAdmin(@QueryParam("numeroRadicado") String numeroRadicado);
    
    /**
     * M�todo que resigna una tarea
     * @param idProceso
     * @param idTarea
     * @param usuarioActual
     * @param usuarioNuevo
     * @param userDTO
     */
    @POST
    @Path("{idProceso}/{idTarea}/reasignar")
    public void reasignarTareaSolicitud(@PathParam("idProceso") Long idProceso, @PathParam("idTarea") Long idTarea,
            @QueryParam("usuarioActual") String usuarioActual, @QueryParam("usuarioNuevo") String usuarioNuevo, @Context UserDTO userDTO);
    
    /**
     * <b>Descripci�n</b>M�todo encargado de asociar las solicitudes a la caja 
     * de correspondencia abierta
     * @param codigoSede
     * @param listaRadicados
     * @param userDTO
     */
    @POST
    @Path("asociarCajaCorrespondencia/{sedeCajaCompensacion}")
    public void asociarSolicitudesACajaCorrespondencia(
            @PathParam("sedeCajaCompensacion") String codigoSede,
            @NotNull @Size(min = 1) List<String> listaRadicados,
            @Context UserDTO userDTO);
    /**
     * Servicio que consulta todas las solicitudes del sistema
     * utilizando filtros de la solicitud
     * 
     * @param filtroSolicitud
     * @return
     */
    @POST
    @Path("/consultarSolicitudesFiltro")
    public List<ResultadoConsultaSolicitudDTO> consultarSolicitudesFiltro(@NotNull FiltroSolicitudDTO filtroSolicitud, @Context UriInfo uri, @Context HttpServletResponse response);
    
    /**
     * <b>Descripción</b>Método encargado de resolver el listado de 
     * las solicitudes  remisión back <br/>
     * @param fechaInicial,
     *              fecha inicial
     * @param fechaFinal ,
     *              fecha final
     * @param proceso,
     *              enum con el tipo de proceso
     * @param userDTO,
     *              usuario de la sesión
     * @return  RemisionBackDTO objeto con la lista de detalles y resumen
     */
    @GET
    @Path("/remisionBackAsignado")
    public RemisionBackDTO generarListadoSolicitudesRemisionBackAsignado(
            @QueryParam("fechaInicial") @NotNull Long fechaInicial, 
            @QueryParam("fechaFinal") @NotNull Long fechaFinal, 
            @QueryParam("proceso") @NotNull ProcesoEnum proceso, 
            @Context UserDTO userDTO);
    
    /**
     * Corresponde al utilitario encargado de retomar las tareas en estado CREATED
     * que quedaron sin usuario propietario por errores técnicos de BPM
     * @param codigoSede
     */
    @POST
    @Path("/retomarTareasErrorBPM")
    public String retomarTareasErrorBPM(@Context UserDTO user);
    
    /**
     * Corresponde al utilitario encargado de rechazar las solcitudes
     * que quedaron sin tarea por errores técnicos de BPM
     * @param codigoSede
     */
    @POST
    @Path("/rechazarSolicitudesErrorBPM")
    public String rechazarSolicitudesErrorBPM(@Context UserDTO usuario);
}
