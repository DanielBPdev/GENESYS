package com.asopagos.pila.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/procesarAutomaticoPlanillaManual
 */
public class ProcesarAutomaticoPlanillaManual extends ServiceClient { 
   	private Long numeroPlanilla;
   
  
 	public ProcesarAutomaticoPlanillaManual (Long numeroPlanilla){
 		super();
		this.numeroPlanilla=numeroPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroPlanilla", numeroPlanilla)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroPlanilla (Long numeroPlanilla){
 		this.numeroPlanilla=numeroPlanilla;
 	}
 	
 	public Long getNumeroPlanilla (){
 		return numeroPlanilla;
 	}
  
  
}