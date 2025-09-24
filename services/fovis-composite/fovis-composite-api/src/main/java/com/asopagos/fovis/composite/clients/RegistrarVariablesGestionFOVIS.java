package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.VariablesGestionFOVISDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/registrarVariablesGestionFOVIS
 */
public class RegistrarVariablesGestionFOVIS extends ServiceClient { 
    	private VariablesGestionFOVISDTO variablesGestionFOVISDTO;
  
  
 	public RegistrarVariablesGestionFOVIS (VariablesGestionFOVISDTO variablesGestionFOVISDTO){
 		super();
		this.variablesGestionFOVISDTO=variablesGestionFOVISDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(variablesGestionFOVISDTO == null ? null : Entity.json(variablesGestionFOVISDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setVariablesGestionFOVISDTO (VariablesGestionFOVISDTO variablesGestionFOVISDTO){
 		this.variablesGestionFOVISDTO=variablesGestionFOVISDTO;
 	}
 	
 	public VariablesGestionFOVISDTO getVariablesGestionFOVISDTO (){
 		return variablesGestionFOVISDTO;
 	}
  
}