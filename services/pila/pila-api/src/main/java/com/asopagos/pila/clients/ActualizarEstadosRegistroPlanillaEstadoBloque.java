package com.asopagos.pila.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/actualizarEstadosRegistroPlanillaEstadoBloque
 */
public class ActualizarEstadosRegistroPlanillaEstadoBloque extends ServiceClient { 
   	private String numeroPlanilla;
   
  
 	public ActualizarEstadosRegistroPlanillaEstadoBloque (String numeroPlanilla){
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
	

 
  	public void setNumeroPlanilla (String numeroPlanilla){
 		this.numeroPlanilla=numeroPlanilla;
 	}
 	
 	public String getNumeroPlanilla (){
 		return numeroPlanilla;
 	}
  
  
}