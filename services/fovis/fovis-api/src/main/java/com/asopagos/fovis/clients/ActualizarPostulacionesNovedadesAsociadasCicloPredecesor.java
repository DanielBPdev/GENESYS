package com.asopagos.fovis.clients;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/actualizarPostulacionesNovedadesAsociadasCicloPredecesor
 */
public class ActualizarPostulacionesNovedadesAsociadasCicloPredecesor extends ServiceClient { 
    
  
 	public ActualizarPostulacionesNovedadesAsociadasCicloPredecesor (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  
}