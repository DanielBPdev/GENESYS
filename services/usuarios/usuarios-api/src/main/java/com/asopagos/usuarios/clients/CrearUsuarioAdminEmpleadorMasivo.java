package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.UsuarioEmpleadorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.core.GenericType;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/empleador/crearUsuarioAdminEmpleadorMasivo
 */
public class CrearUsuarioAdminEmpleadorMasivo extends ServiceClient { 
    	private UsuarioEmpleadorDTO user;
		private String result;
  
 	public CrearUsuarioAdminEmpleadorMasivo (UsuarioEmpleadorDTO user){
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
			this.result = (String) response.readEntity(new GenericType<String>(){});
	}
	 public String  getResult() {
		return result;
	}
  	public void setUser (UsuarioEmpleadorDTO user){
 		this.user=user;
 	}
 	
 	public UsuarioEmpleadorDTO getUser (){
 		return user;
 	}
  
}