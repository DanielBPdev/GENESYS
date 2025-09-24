package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro1DModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro1D
 */
public class GuardarAccionCobro1D extends ServiceClient { 
    	private AccionCobro1DModeloDTO accionCobro1DModeloDTO;
  
  
 	public GuardarAccionCobro1D (AccionCobro1DModeloDTO accionCobro1DModeloDTO){
 		super();
		this.accionCobro1DModeloDTO=accionCobro1DModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro1DModeloDTO == null ? null : Entity.json(accionCobro1DModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro1DModeloDTO (AccionCobro1DModeloDTO accionCobro1DModeloDTO){
 		this.accionCobro1DModeloDTO=accionCobro1DModeloDTO;
 	}
 	
 	public AccionCobro1DModeloDTO getAccionCobro1DModeloDTO (){
 		return accionCobro1DModeloDTO;
 	}
  
}