package com.asopagos.notificaciones.archivos.composite.clients;

import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/notificaciones/enviarNotificacionComunicadoAsincrono
 */
public class EnviarNotificacionComunicadoAsincrono extends ServiceClient { 
    	private NotificacionParametrizadaDTO notificacion;
  
  
 	public EnviarNotificacionComunicadoAsincrono (NotificacionParametrizadaDTO notificacion){
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
	}
	

 
  
  	public void setNotificacion (NotificacionParametrizadaDTO notificacion){
 		this.notificacion=notificacion;
 	}
 	
 	public NotificacionParametrizadaDTO getNotificacion (){
 		return notificacion;
 	}
  
}