package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.AsignaResultadoCruceDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/registrarResultadoCruces
 */
public class RegistrarResultadoCruces extends ServiceClient { 
    	private AsignaResultadoCruceDTO asignaResultadoCruce;
  
  
 	public RegistrarResultadoCruces (AsignaResultadoCruceDTO asignaResultadoCruce){
 		super();
		this.asignaResultadoCruce=asignaResultadoCruce;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(asignaResultadoCruce == null ? null : Entity.json(asignaResultadoCruce));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAsignaResultadoCruce (AsignaResultadoCruceDTO asignaResultadoCruce){
 		this.asignaResultadoCruce=asignaResultadoCruce;
 	}
 	
 	public AsignaResultadoCruceDTO getAsignaResultadoCruce (){
 		return asignaResultadoCruce;
 	}
  
}