package com.asopagos.aportes.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/validarProcesamientoNovedadFutura
 */
public class ValidarProcesamientoNovedadFutura extends ServiceClient {
 
  
  	private Long fechaValidacion;
  
  
 	public ValidarProcesamientoNovedadFutura (Long fechaValidacion){
 		super();
		this.fechaValidacion=fechaValidacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaValidacion", fechaValidacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setFechaValidacion (Long fechaValidacion){
 		this.fechaValidacion=fechaValidacion;
 	}
 	
 	public Long getFechaValidacion (){
 		return fechaValidacion;
 	}
  
}