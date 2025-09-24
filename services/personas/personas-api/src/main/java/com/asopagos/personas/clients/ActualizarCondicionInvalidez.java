package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/actualizarCondicionInvalidez
 */
public class ActualizarCondicionInvalidez extends ServiceClient { 
    	private CondicionInvalidezModeloDTO condicionInvalidezModeloDTO;
  
  
 	public ActualizarCondicionInvalidez (CondicionInvalidezModeloDTO condicionInvalidezModeloDTO){
 		super();
		this.condicionInvalidezModeloDTO=condicionInvalidezModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(condicionInvalidezModeloDTO == null ? null : Entity.json(condicionInvalidezModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCondicionInvalidezModeloDTO (CondicionInvalidezModeloDTO condicionInvalidezModeloDTO){
 		this.condicionInvalidezModeloDTO=condicionInvalidezModeloDTO;
 	}
 	
 	public CondicionInvalidezModeloDTO getCondicionInvalidezModeloDTO (){
 		return condicionInvalidezModeloDTO;
 	}
  
}