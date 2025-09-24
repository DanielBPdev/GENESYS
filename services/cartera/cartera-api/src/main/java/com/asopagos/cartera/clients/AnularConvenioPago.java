package com.asopagos.cartera.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/anularConvenioPago/{numeroConvenio}
 */
public class AnularConvenioPago extends ServiceClient { 
  	private Long numeroConvenio;
    
  
 	public AnularConvenioPago (Long numeroConvenio){
 		super();
		this.numeroConvenio=numeroConvenio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroConvenio", numeroConvenio)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNumeroConvenio (Long numeroConvenio){
 		this.numeroConvenio=numeroConvenio;
 	}
 	
 	public Long getNumeroConvenio (){
 		return numeroConvenio;
 	}
  
  
  
}