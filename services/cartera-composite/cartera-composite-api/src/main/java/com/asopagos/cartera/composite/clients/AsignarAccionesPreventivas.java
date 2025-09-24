package com.asopagos.cartera.composite.clients;

import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/asignarAccionesPreventivas
 */
public class AsignarAccionesPreventivas extends ServiceClient { 
   	private Boolean automatico;
   
  
 	public AsignarAccionesPreventivas (Boolean automatico){
 		super();
		this.automatico=automatico;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("automatico", automatico)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setAutomatico (Boolean automatico){
 		this.automatico=automatico;
 	}
 	
 	public Boolean getAutomatico (){
 		return automatico;
 	}
  
  
}