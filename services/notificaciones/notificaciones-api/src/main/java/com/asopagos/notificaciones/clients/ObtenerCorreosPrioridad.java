package com.asopagos.notificaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/enviarCorreo/obtenerCorreosPrioridad
 */
public class ObtenerCorreosPrioridad extends ServiceClient { 
    	private NotificacionParametrizadaDTO notificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CorreoPrioridadPersonaDTO> result;
  
 	public ObtenerCorreosPrioridad (NotificacionParametrizadaDTO notificacion){
 		super();
		this.notificacion=notificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(notificacion == null ? null : Entity.json(notificacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CorreoPrioridadPersonaDTO>) response.readEntity(new GenericType<List<CorreoPrioridadPersonaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CorreoPrioridadPersonaDTO> getResult() {
		return result;
	}

 
  
  	public void setNotificacion (NotificacionParametrizadaDTO notificacion){
 		this.notificacion=notificacion;
 	}
 	
 	public NotificacionParametrizadaDTO getNotificacion (){
 		return notificacion;
 	}
  
}