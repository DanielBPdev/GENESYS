package com.asopagos.usuarios.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/activar/{nombreUsuario}
 */
public class ActivarUsuario extends ServiceClient { 
  	private String nombreUsuario;
   	private Boolean isInmediate;
   
  
 	public ActivarUsuario (String nombreUsuario,Boolean isInmediate){
 		super();
		this.nombreUsuario=nombreUsuario;
		this.isInmediate=isInmediate;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("nombreUsuario", nombreUsuario)
			.queryParam("isInmediate", isInmediate)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
  	public void setIsInmediate (Boolean isInmediate){
 		this.isInmediate=isInmediate;
 	}
 	
 	public Boolean getIsInmediate (){
 		return isInmediate;
 	}
  
  
}