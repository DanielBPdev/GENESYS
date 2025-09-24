package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/SubsidioMonetarioService/rest/subsidioMonetario/ejecucionAvisoPrescripcionSubsidio
 */
public class EjecucionAvisoPrescripcionSubsidio extends ServiceClient { 
    
  
 	public EjecucionAvisoPrescripcionSubsidio (){
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