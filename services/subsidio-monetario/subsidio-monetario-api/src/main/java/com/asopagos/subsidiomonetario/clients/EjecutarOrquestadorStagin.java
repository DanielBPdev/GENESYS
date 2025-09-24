package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/ejecutarOrquestadorStagin
 */
public class EjecutarOrquestadorStagin extends ServiceClient { 
   	private Long fechaActual;
   
  
 	public EjecutarOrquestadorStagin (Long fechaActual){
 		super();
		this.fechaActual=fechaActual;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("fechaActual", fechaActual)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setFechaActual (Long fechaActual){
 		this.fechaActual=fechaActual;
 	}
 	
 	public Long getFechaActual (){
 		return fechaActual;
 	}
  
  
}