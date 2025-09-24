package com.asopagos.notificaciones.service;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.notificaciones.dto.GrupoRolPrioridadDTO;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import java.util.Map;
import com.asopagos.dto.modelo.ResultadoEnvioComunicadoCarteraDTO;


/**
 * Servicio de notificaciones
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */
@Path("enviarCorreo")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NotificacionesService {

	/**
	 * Método que se encarga de envíar un correo a la dirección especificada de
	 * forma asincrona
	 * 
	 * @param notificacion
	 * @param userDTO
	 */
	@POST
	@Path("")
	public void enviarCorreo(@NotNull @Valid NotificacionDTO notificacion, @Context UserDTO userDTO);

	/**
	 * Método que se encarga de envíar un correo a la dirección especificada a
	 * partir de una plantilla de comunicados,
	 * 
	 * @param notificacion
	 * @param userDTO
	 * @return
	 */
	@POST
	@Path("enviarCorreoParametrizado")
	public void enviarCorreoParametrizado(@NotNull @Valid NotificacionParametrizadaDTO notificacion,
			@Context UserDTO userDTO);

	@POST
	@Path("enviarCorreoParametrizadoCartera")
	public ResultadoEnvioComunicadoCarteraDTO EnviarCorreoParametrizadoCartera(@NotNull NotificacionParametrizadaDTO notificacion,
			@Context UserDTO userDTO);
	
	/**
	 * Método encargado de enviar correos masivos
	 * @param notificaciones
	 */
	@POST
	@Path("enviarCorreoMasivo")
	public void enviarCorreoMasivo(@NotNull List<NotificacionDTO> notificaciones, @Context UserDTO userDTO);

    /**
     * Método que se encarga de retornar los correos para el grupo con prioridad 1
     *   
     * @param notificacion
     * @param userDTO
     * @return List<String> 
     */
	@POST
	@Path("obtenerCorreosPrioridad")
	public List<CorreoPrioridadPersonaDTO> obtenerCorreosPrioridad(NotificacionParametrizadaDTO notificacion,
            @Context UserDTO userDTO);
	
	/**
	 * Método que se encarga de devolver una lista con todos los grupos de la prioridad de correos
	 * @param proceso
	 * @param etiqueta
	 * @return List<GrupoPrioridad>
	 */
	@GET
	@Path("obtenerGruposPrioridad")
	public List<GrupoRolPrioridadDTO> obtenerGruposPrioridad(@QueryParam("proceso") String proceso);
	
	/**
	 * Método que se encarga de actualoizar las prioridades de los grupos en la parametrización de roles
	 * @param proceso
	 * @param gruposPrioridadDTO
	 */
	@POST
	@Path("actualizarGruposPrioridadLista/{proceso}")
	public void actualizarGruposPrioridadLista(@PathParam("proceso") String proceso, List<GrupoRolPrioridadDTO> gruposPrioridadDTO);
	
	/**
	 * Método que se encarga de enviar el correo de modo masivo en una sola conexión,
	 * sin hacer uso de la priorización de los destinatarios parametrizados
	 * 
	 * @param notificaciones
	 * @param userDTO
	 */
	@POST
	@Path("enviarMultiplesCorreosPorConexion")
	public void enviarMultiplesCorreosPorConexion(@NotNull @Valid List<NotificacionParametrizadaDTO> notificaciones,
			@Context UserDTO userDTO);
	
	/**
	 * Método que se encarga de buscar el tipo de transacción asociado a una solicitud para enviar una notificación (usado en los procesos BPM)
	 * 
	 * @param idSolicitud
	 * @return
	 */
	@POST
	@Path("buscarTipoTransaccionSolicitud")
	public TipoTransaccionEnum buscarTipoTransaccionSolicitud(Long idSolicitud);

	@GET
    @Path("/ArchivoAdjunto")
        public Map<String, Object> consultarArchivoAdjunto (
                @QueryParam("pcoEtiqueta") EtiquetaPlantillaComunicadoEnum visualizarArchivoAdjunto
        );
}
