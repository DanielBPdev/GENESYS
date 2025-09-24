package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobroBModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobroB
 */
public class GuardarAccionCobroB extends ServiceClient { 
    	private AccionCobroBModeloDTO accionCobroBModeloDTO;
  
  
 	public GuardarAccionCobroB (AccionCobroBModeloDTO accionCobroBModeloDTO){
 		super();
		this.accionCobroBModeloDTO=accionCobroBModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobroBModeloDTO == null ? null : Entity.json(accionCobroBModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobroBModeloDTO (AccionCobroBModeloDTO accionCobroBModeloDTO){
 		this.accionCobroBModeloDTO=accionCobroBModeloDTO;
 	}
 	
 	public AccionCobroBModeloDTO getAccionCobroBModeloDTO (){
 		return accionCobroBModeloDTO;
 	}
  
}