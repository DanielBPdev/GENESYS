package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.UsuarioCCF;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/ccf
 */
public class CrearUsuario extends ServiceClient { 
    	private UsuarioCCF usuario;
  
  
 	public CrearUsuario (UsuarioCCF usuario){
 		super();
		this.usuario=usuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(usuario == null ? null : Entity.json(usuario));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setUsuario (UsuarioCCF usuario){
 		this.usuario=usuario;
 	}
 	
 	public UsuarioCCF getUsuario (){
 		return usuario;
 	}
  
}