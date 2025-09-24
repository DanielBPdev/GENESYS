package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro2DModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro2D
 */
public class GuardarAccionCobro2D extends ServiceClient { 
    	private AccionCobro2DModeloDTO accionCobro2DModeloDTO;
  
  
 	public GuardarAccionCobro2D (AccionCobro2DModeloDTO accionCobro2DModeloDTO){
 		super();
		this.accionCobro2DModeloDTO=accionCobro2DModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro2DModeloDTO == null ? null : Entity.json(accionCobro2DModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro2DModeloDTO (AccionCobro2DModeloDTO accionCobro2DModeloDTO){
 		this.accionCobro2DModeloDTO=accionCobro2DModeloDTO;
 	}
 	
 	public AccionCobro2DModeloDTO getAccionCobro2DModeloDTO (){
 		return accionCobro2DModeloDTO;
 	}
  
}