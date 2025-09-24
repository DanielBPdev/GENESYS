package com.asopagos.pila.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/reprocesarByids
 */
public class ReprocesarMundo1 extends ServiceClient { 
    	private List<Long> indicesPlanilla;
  
  
 	public ReprocesarMundo1 (List<Long> indicesPlanilla){
 		super();
		this.indicesPlanilla=indicesPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(indicesPlanilla == null ? null : Entity.json(indicesPlanilla));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIndicesPlanilla (List<Long> indicesPlanilla){
 		this.indicesPlanilla=indicesPlanilla;
 	}
 	
 	public List<Long> getIndicesPlanilla (){
 		return indicesPlanilla;
 	}
  
}