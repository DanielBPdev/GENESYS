package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.UsuarioEmpleadorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/empleador/admon
 */
public class CrearUsuarioAdminEmpleador extends ServiceClient { 
    	private UsuarioEmpleadorDTO user;
  
  
 	public CrearUsuarioAdminEmpleador (UsuarioEmpleadorDTO user){
 		super();
		this.user=user;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(user == null ? null : Entity.json(user));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setUser (UsuarioEmpleadorDTO user){
 		this.user=user;
 	}
 	
 	public UsuarioEmpleadorDTO getUser (){
 		return user;
 	}
  
}