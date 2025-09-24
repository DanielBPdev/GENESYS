package com.asopagos.parametros.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/parametros/replicarActualizacionListaEspecialRevision
 */
public class ReplicarActualizacionListaEspecialRevision extends ServiceClient { 
    	private String mensaje;
  
  
 	public ReplicarActualizacionListaEspecialRevision (String mensaje){
 		super();
		this.mensaje=mensaje;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(mensaje == null ? null : Entity.json(mensaje));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setMensaje (String mensaje){
 		this.mensaje=mensaje;
 	}
 	
 	public String getMensaje (){
 		return mensaje;
 	}
  
}