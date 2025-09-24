package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/reprocesarPlanillasM1
 */
public class ReprocesarPlanillasM1 extends ServiceClient { 
	private List<Long> indicePlanilla;
	private List<Long> result;
	
  
  
 	public ReprocesarPlanillasM1 (List<Long> indicePlanilla){
 		super();
		this.indicePlanilla=indicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(indicePlanilla == null ? null : Entity.json(indicePlanilla));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Long> getResult() {
		return result;
	}

	

 
  
  	public void setIndicePlanilla (List<Long> indicePlanilla){
 		this.indicePlanilla=indicePlanilla;
 	}
 	
 	public List<Long> getIndicePlanilla (){
 		return indicePlanilla;
 	}
  
}