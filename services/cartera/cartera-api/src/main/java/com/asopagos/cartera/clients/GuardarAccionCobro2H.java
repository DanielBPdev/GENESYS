package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro2HModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro2H
 */
public class GuardarAccionCobro2H extends ServiceClient { 
    	private AccionCobro2HModeloDTO accionCobro2HModeloDTO;
  
  
 	public GuardarAccionCobro2H (AccionCobro2HModeloDTO accionCobro2HModeloDTO){
 		super();
		this.accionCobro2HModeloDTO=accionCobro2HModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro2HModeloDTO == null ? null : Entity.json(accionCobro2HModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro2HModeloDTO (AccionCobro2HModeloDTO accionCobro2HModeloDTO){
 		this.accionCobro2HModeloDTO=accionCobro2HModeloDTO;
 	}
 	
 	public AccionCobro2HModeloDTO getAccionCobro2HModeloDTO (){
 		return accionCobro2HModeloDTO;
 	}
  
}