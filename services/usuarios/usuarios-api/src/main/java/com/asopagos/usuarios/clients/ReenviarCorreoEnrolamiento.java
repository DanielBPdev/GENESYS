package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.InformacionReenvioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/reenviarCorreoEnrolamiento
 */
public class ReenviarCorreoEnrolamiento extends ServiceClient { 
    	private InformacionReenvioDTO notificacion;
  
  
 	public ReenviarCorreoEnrolamiento (InformacionReenvioDTO notificacion){
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
	

 
  
  	public void setNotificacion (InformacionReenvioDTO notificacion){
 		this.notificacion=notificacion;
 	}
 	
 	public InformacionReenvioDTO getNotificacion (){
 		return notificacion;
 	}
  
}