package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro1CModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro1C
 */
public class GuardarAccionCobro1C extends ServiceClient { 
    	private AccionCobro1CModeloDTO accionCobro1CModeloDTO;
  
  
 	public GuardarAccionCobro1C (AccionCobro1CModeloDTO accionCobro1CModeloDTO){
 		super();
		this.accionCobro1CModeloDTO=accionCobro1CModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro1CModeloDTO == null ? null : Entity.json(accionCobro1CModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro1CModeloDTO (AccionCobro1CModeloDTO accionCobro1CModeloDTO){
 		this.accionCobro1CModeloDTO=accionCobro1CModeloDTO;
 	}
 	
 	public AccionCobro1CModeloDTO getAccionCobro1CModeloDTO (){
 		return accionCobro1CModeloDTO;
 	}
  
}