package com.asopagos.notificaciones.clients;

import com.asopagos.notificaciones.dto.NotificacionDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/enviarCorreo/enviarCorreoMasivo
 */
public class EnviarCorreoMasivo extends ServiceClient { 
    	private List<NotificacionDTO> notificaciones;
  
  
 	public EnviarCorreoMasivo (List<NotificacionDTO> notificaciones){
 		super();
		this.notificaciones=notificaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(notificaciones == null ? null : Entity.json(notificaciones));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setNotificaciones (List<NotificacionDTO> notificaciones){
 		this.notificaciones=notificaciones;
 	}
 	
 	public List<NotificacionDTO> getNotificaciones (){
 		return notificaciones;
 	}
  
}