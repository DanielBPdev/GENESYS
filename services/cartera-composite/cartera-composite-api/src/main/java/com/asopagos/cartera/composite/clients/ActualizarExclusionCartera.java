package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.ExclusionCarteraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/actualizarExclusionCartera
 */
public class ActualizarExclusionCartera extends ServiceClient { 
    	private ExclusionCarteraDTO exclusionCarteraDTO;
  
  
 	public ActualizarExclusionCartera (ExclusionCarteraDTO exclusionCarteraDTO){
 		super();
		this.exclusionCarteraDTO=exclusionCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(exclusionCarteraDTO == null ? null : Entity.json(exclusionCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setExclusionCarteraDTO (ExclusionCarteraDTO exclusionCarteraDTO){
 		this.exclusionCarteraDTO=exclusionCarteraDTO;
 	}
 	
 	public ExclusionCarteraDTO getExclusionCarteraDTO (){
 		return exclusionCarteraDTO;
 	}
  
}