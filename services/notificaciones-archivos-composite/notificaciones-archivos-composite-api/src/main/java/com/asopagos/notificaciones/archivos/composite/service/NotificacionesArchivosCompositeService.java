package com.asopagos.notificaciones.archivos.composite.service;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.asopagos.archivos.dto.RespuestaECMExternoDTO;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.dto.modelo.ResultadoEnvioComunicadoCarteraDTO;

/**
 * Servicio que se compone de los servicios de notificaciones y archivos
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
@Path("notificaciones")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NotificacionesArchivosCompositeService {

	/**
	 * Método que se encarga de envíar un correo a la direción especificada con
	 * adjuntos
	 * 
	 * @param notificacion
	 * @param userDTO
	 */
	@POST
	@Path("/enviarNotificacionAdjuntos")
	public void enviarNotificacionAdjuntos(@NotNull NotificacionDTO notificacion, @Context UserDTO userDTO);

	/**
     * Método que se encarga de envíar un correo a la direción especificada con
     * adjunto asincronamente.
     * 
     * @param notificacion
     * @param userDTO
     */
    @POST
    @Path("/enviarNotificacionComunicadoAsincrono")
	public void enviarNotificacionComunicadoAsincrono(NotificacionParametrizadaDTO notificacion, UserDTO userDTO);
	
    /**
     * Método que se encarga de envíar un correo a la direción especificada con
     * adjunto que se trata de un comunicado según 
     * 
     * @param notificacion
     * @param userDTO
     */
	@POST
    @Path("/enviarNotificacionComunicado")
    public void enviarNotificacionComunicado(@NotNull NotificacionParametrizadaDTO notificacion, @Context UserDTO userDTO);
	
	/**
     * Método que se encarga de envíar una lista de correo a la direción especificada con
     * adjunto que se trata de un comunicado según 
     * 
     * @param notificacion
     * @param userDTO
     */
	@POST
    @Path("/enviarListaNotificacionComunicado")
    public void enviarListaNotificacionComunicados(@NotNull List<NotificacionParametrizadaDTO> notificaciones, @Context UserDTO userDTO);
	
	/**
     * Método que se encarga de consultar el tipo de transacción asociado a la solicitud y posteriormente envíar un correo a la direción especificada.
     * 
     * @param notificacion
     * @param userDTO
     */
    @POST
    @Path("/buscarTransaccionEnviarNotificacionComunicado")
    public void buscarTransaccionEnviarNotificacionComunicado(@NotNull NotificacionParametrizadaDTO notificacion, @Context UserDTO userDTO);
    
    /**
     * Metodo que valida si se envía la totalidad de correos (al productor de la cola) y si la estructura de las direcciones email es valida
     * 
     * @param notificaciones
     * @param userDTO
     * @return En el caso en que uno de los correos no tenga un formato correo u ocurra alguna excepcion en el envío al productor, retorna false
     */
    @POST
    @Path("/envioExitosoComunicados")
    public Boolean envioExitosoComunicados(@NotNull List<NotificacionParametrizadaDTO> notificaciones, @Context UserDTO userDTO);
    

    @POST
    @Path("/envioExitosoComunicadosCartera")
    public ResultadoEnvioComunicadoCarteraDTO envioExitosoComunicadosCartera(@NotNull List<NotificacionParametrizadaDTO> notificaciones);

    /**
     * Método que recibe la información devuelta por el ECM externo al enviarle la metadata de un archivo, persistiendola y 
     * enviando el(los) comunicado(s) si es necesario.
     * 
     * @param respuesta <code>com.asopagos.archivos.dto.RespuestaECMExternoDTO</code>
     * 			DTO con la información enviada por el ECM externo. 
     */
    @POST
    @Path("/persistirRespuestaECMExterno")
    public void persistirRespuestaECMExterno(RespuestaECMExternoDTO respuesta);

    @POST
    @Path("/enviarNotificacionSeven")
	void enviarNotificacionSeven(NotificacionParametrizadaDTO notificacion);
   
}
