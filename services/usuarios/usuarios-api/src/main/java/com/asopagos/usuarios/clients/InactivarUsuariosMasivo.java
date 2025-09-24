package com.asopagos.usuarios.clients;

import java.util.List;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/inactivarUsuariosMasivo
 */
public class InactivarUsuariosMasivo extends ServiceClient { 
   	private Boolean isInmediate;
   	private List<String> usuarios;
  
  
 	public InactivarUsuariosMasivo (Boolean isInmediate,List<String> usuarios){
 		super();
		this.isInmediate=isInmediate;
		this.usuarios=usuarios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("isInmediate", isInmediate)
			.request(MediaType.APPLICATION_JSON)
			.post(usuarios == null ? null : Entity.json(usuarios));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIsInmediate (Boolean isInmediate){
 		this.isInmediate=isInmediate;
 	}
 	
 	public Boolean getIsInmediate (){
 		return isInmediate;
 	}
  
  	public void setUsuarios (List<String> usuarios){
 		this.usuarios=usuarios;
 	}
 	
 	public List<String> getUsuarios (){
 		return usuarios;
 	}
  
}