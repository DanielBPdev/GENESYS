package com.asopagos.sat.clients;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/ConsultarSolicitudAfiliacionSATPrimervez
 */
public class ConsultarSolicitudAfiliacionaSATPrimeravez extends ServiceClient { 
    
  
 	public ConsultarSolicitudAfiliacionaSATPrimeravez (){
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