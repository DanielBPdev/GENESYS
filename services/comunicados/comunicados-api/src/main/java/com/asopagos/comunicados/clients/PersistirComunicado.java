package com.asopagos.comunicados.clients;

import com.asopagos.notificaciones.dto.NotificacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/persistirComunicado
 */
public class PersistirComunicado extends ServiceClient { 
    	private NotificacionDTO notificacion;
  
  
 	public PersistirComunicado (NotificacionDTO notificacion){
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
	

 
  
  	public void setNotificacion (NotificacionDTO notificacion){
 		this.notificacion=notificacion;
 	}
 	
 	public NotificacionDTO getNotificacion (){
 		return notificacion;
 	}
  
}