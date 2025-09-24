package com.asopagos.sat.clients;

import com.asopagos.sat.dto.NotificacionSatDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/sat/consultarAfiliacionesSat
 */
public class ConsultarAfiliacionesSat extends ServiceClient { 
    	private NotificacionSatDTO notificacion;
  
  
 	public ConsultarAfiliacionesSat (NotificacionSatDTO notificacion){
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
	

 
  
  	public void setNotificacion (NotificacionSatDTO notificacion){
 		this.notificacion=notificacion;
 	}
 	
 	public NotificacionSatDTO getNotificacion (){
 		return notificacion;
 	}
  
}