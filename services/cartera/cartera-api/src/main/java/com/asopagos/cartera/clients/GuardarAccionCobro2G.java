package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro2GModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro2G
 */
public class GuardarAccionCobro2G extends ServiceClient { 
    	private AccionCobro2GModeloDTO accionCobro2GModeloDTO;
  
  
 	public GuardarAccionCobro2G (AccionCobro2GModeloDTO accionCobro2GModeloDTO){
 		super();
		this.accionCobro2GModeloDTO=accionCobro2GModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro2GModeloDTO == null ? null : Entity.json(accionCobro2GModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro2GModeloDTO (AccionCobro2GModeloDTO accionCobro2GModeloDTO){
 		this.accionCobro2GModeloDTO=accionCobro2GModeloDTO;
 	}
 	
 	public AccionCobro2GModeloDTO getAccionCobro2GModeloDTO (){
 		return accionCobro2GModeloDTO;
 	}
  
}