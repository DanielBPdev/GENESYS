package com.asopagos.aportes.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportesPila/copiarDatosAportesPlanilla
 */
public class CopiarDatosAportesPlanilla extends ServiceClient { 
   	private Long indicePlanilla;
   
  
 	public CopiarDatosAportesPlanilla (Long indicePlanilla){
 		super();
		this.indicePlanilla=indicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("indicePlanilla", indicePlanilla)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIndicePlanilla (Long indicePlanilla){
 		this.indicePlanilla=indicePlanilla;
 	}
 	
 	public Long getIndicePlanilla (){
 		return indicePlanilla;
 	}
  
  
}