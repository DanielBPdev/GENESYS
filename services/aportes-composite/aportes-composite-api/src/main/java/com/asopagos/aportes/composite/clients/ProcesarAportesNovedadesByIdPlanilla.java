package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/AportesComposite/procesarAportesNovedadesByIdPlanilla
 */
public class ProcesarAportesNovedadesByIdPlanilla extends ServiceClient {
 
  
  	private Long indicePlanilla;
  
  
 	public ProcesarAportesNovedadesByIdPlanilla (Long indicePlanilla){
 		super();
		this.indicePlanilla=indicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {

		System.out.println(" ProcesarAportesNovedadesByIdPlanilla -> PATH "+path);
		Response response = webTarget.path(path)
									.queryParam("indicePlanilla", indicePlanilla)
						.request(MediaType.APPLICATION_JSON).get();
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