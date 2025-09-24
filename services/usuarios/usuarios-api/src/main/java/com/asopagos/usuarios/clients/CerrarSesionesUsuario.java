package com.asopagos.usuarios.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/autenticacion/cerrarSesion
 */
public class CerrarSesionesUsuario extends ServiceClient { 
    	private String userName;
  
  
 	public CerrarSesionesUsuario (String userName){
 		super();
		this.userName=userName;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(userName == null ? null : Entity.json(userName));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setUserName (String userName){
 		this.userName=userName;
 	}
 	
 	public String getUserName (){
 		return userName;
 	}
  
}