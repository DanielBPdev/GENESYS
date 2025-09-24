package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/actualizarActivacionMetodoGestionCobro
 */
public class ActualizarActivacionMetodoGestionCobro extends ServiceClient { 
   	private MetodoAccionCobroEnum metodoAnterior;
   
  
 	public ActualizarActivacionMetodoGestionCobro (MetodoAccionCobroEnum metodoAnterior){
 		super();
		this.metodoAnterior=metodoAnterior;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("metodoAnterior", metodoAnterior)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setMetodoAnterior (MetodoAccionCobroEnum metodoAnterior){
 		this.metodoAnterior=metodoAnterior;
 	}
 	
 	public MetodoAccionCobroEnum getMetodoAnterior (){
 		return metodoAnterior;
 	}
  
  
}