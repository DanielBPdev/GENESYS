package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/AportesComposite/procesarAportesNovedadesByIdPlanillaSincrono
 */
public class ProcesarAportesNovedadesByIdPlanillaSincrono extends ServiceClient {
 
  
  	private Long indicePlanilla;
  
  
 	public ProcesarAportesNovedadesByIdPlanillaSincrono (Long indicePlanilla){
 		super();
		this.indicePlanilla=indicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
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