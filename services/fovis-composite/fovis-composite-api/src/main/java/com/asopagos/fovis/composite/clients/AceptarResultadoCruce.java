package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.AsignaResultadoCruceDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/aceptarResultadoCruce
 */
public class AceptarResultadoCruce extends ServiceClient { 
    	private AsignaResultadoCruceDTO asignarResultadoCruce;
  
  
 	public AceptarResultadoCruce (AsignaResultadoCruceDTO asignarResultadoCruce){
 		super();
		this.asignarResultadoCruce=asignarResultadoCruce;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(asignarResultadoCruce == null ? null : Entity.json(asignarResultadoCruce));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAsignarResultadoCruce (AsignaResultadoCruceDTO asignarResultadoCruce){
 		this.asignarResultadoCruce=asignarResultadoCruce;
 	}
 	
 	public AsignaResultadoCruceDTO getAsignarResultadoCruce (){
 		return asignarResultadoCruce;
 	}
  
}