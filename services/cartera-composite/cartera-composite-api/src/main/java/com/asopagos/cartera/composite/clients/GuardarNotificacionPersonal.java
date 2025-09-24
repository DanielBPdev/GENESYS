package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.modelo.NotificacionPersonalModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/guardarNotificacionPersonal
 */
public class GuardarNotificacionPersonal extends ServiceClient { 
    	private NotificacionPersonalModeloDTO notificacionPersonalDTO;
  
  
 	public GuardarNotificacionPersonal (NotificacionPersonalModeloDTO notificacionPersonalDTO){
 		super();
		this.notificacionPersonalDTO=notificacionPersonalDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(notificacionPersonalDTO == null ? null : Entity.json(notificacionPersonalDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setNotificacionPersonalDTO (NotificacionPersonalModeloDTO notificacionPersonalDTO){
 		this.notificacionPersonalDTO=notificacionPersonalDTO;
 	}
 	
 	public NotificacionPersonalModeloDTO getNotificacionPersonalDTO (){
 		return notificacionPersonalDTO;
 	}
  
}