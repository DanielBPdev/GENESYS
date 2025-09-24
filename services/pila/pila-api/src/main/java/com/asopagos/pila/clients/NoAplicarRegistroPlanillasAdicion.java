package com.asopagos.pila.clients;

import com.asopagos.dto.pila.DetalleTablaAportanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/noAplicarRegistroPlanillasAdicion
 */
public class NoAplicarRegistroPlanillasAdicion extends ServiceClient { 
    	private DetalleTablaAportanteDTO registroPlanillaAdicion;
  
  
 	public NoAplicarRegistroPlanillasAdicion (DetalleTablaAportanteDTO registroPlanillaAdicion){
 		super();
		this.registroPlanillaAdicion=registroPlanillaAdicion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroPlanillaAdicion == null ? null : Entity.json(registroPlanillaAdicion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRegistroPlanillaAdicion (DetalleTablaAportanteDTO registroPlanillaAdicion){
 		this.registroPlanillaAdicion=registroPlanillaAdicion;
 	}
 	
 	public DetalleTablaAportanteDTO getRegistroPlanillaAdicion (){
 		return registroPlanillaAdicion;
 	}
  
}