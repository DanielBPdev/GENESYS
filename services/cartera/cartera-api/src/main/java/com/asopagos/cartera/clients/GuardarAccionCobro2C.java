package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro2CModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro2C
 */
public class GuardarAccionCobro2C extends ServiceClient { 
    	private AccionCobro2CModeloDTO accionCobro2CModeloDTO;
  
  
 	public GuardarAccionCobro2C (AccionCobro2CModeloDTO accionCobro2CModeloDTO){
 		super();
		this.accionCobro2CModeloDTO=accionCobro2CModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro2CModeloDTO == null ? null : Entity.json(accionCobro2CModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro2CModeloDTO (AccionCobro2CModeloDTO accionCobro2CModeloDTO){
 		this.accionCobro2CModeloDTO=accionCobro2CModeloDTO;
 	}
 	
 	public AccionCobro2CModeloDTO getAccionCobro2CModeloDTO (){
 		return accionCobro2CModeloDTO;
 	}
  
}