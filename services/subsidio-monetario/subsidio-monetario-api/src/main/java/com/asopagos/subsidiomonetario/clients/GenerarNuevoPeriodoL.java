package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/generarNuevoPeriodoL
 */
public class GenerarNuevoPeriodoL extends ServiceClient { 
   	private Long periodoL;
   
  
 	public GenerarNuevoPeriodoL (Long periodoL){
 		super();
		this.periodoL=periodoL;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("periodoL", periodoL)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setPeriodoL (Long periodoL){
 		this.periodoL=periodoL;
 	}
 	
 	public Long getPeriodoL (){
 		return periodoL;
 	}
  
  
}