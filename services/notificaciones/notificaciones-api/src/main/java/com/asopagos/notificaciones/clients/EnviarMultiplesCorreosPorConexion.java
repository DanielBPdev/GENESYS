package com.asopagos.notificaciones.clients;

import java.util.List;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/enviarCorreo/enviarMultiplesCorreosPorConexion
 */
public class EnviarMultiplesCorreosPorConexion extends ServiceClient { 
    	private List<NotificacionParametrizadaDTO> notificaciones;
  
  
 	public EnviarMultiplesCorreosPorConexion (List<NotificacionParametrizadaDTO> notificaciones){
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
	

 
  
  	public void setNotificaciones (List<NotificacionParametrizadaDTO> notificaciones){
 		this.notificaciones=notificaciones;
 	}
 	
 	public List<NotificacionParametrizadaDTO> getNotificaciones (){
 		return notificaciones;
 	}
  
}