package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.VariablesGestionFOVISDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/registrarParametrizacionNovedades
 */
public class RegistrarParametrizacionNovedades extends ServiceClient { 
    	private VariablesGestionFOVISDTO variables;
  
  
 	public RegistrarParametrizacionNovedades (VariablesGestionFOVISDTO variables){
 		super();
		this.variables=variables;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(variables == null ? null : Entity.json(variables));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setVariables (VariablesGestionFOVISDTO variables){
 		this.variables=variables;
 	}
 	
 	public VariablesGestionFOVISDTO getVariables (){
 		return variables;
 	}
  
}