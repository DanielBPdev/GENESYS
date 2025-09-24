package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro2EModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro2E
 */
public class GuardarAccionCobro2E extends ServiceClient { 
    	private AccionCobro2EModeloDTO accionCobro2EModeloDTO;
  
  
 	public GuardarAccionCobro2E (AccionCobro2EModeloDTO accionCobro2EModeloDTO){
 		super();
		this.accionCobro2EModeloDTO=accionCobro2EModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro2EModeloDTO == null ? null : Entity.json(accionCobro2EModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro2EModeloDTO (AccionCobro2EModeloDTO accionCobro2EModeloDTO){
 		this.accionCobro2EModeloDTO=accionCobro2EModeloDTO;
 	}
 	
 	public AccionCobro2EModeloDTO getAccionCobro2EModeloDTO (){
 		return accionCobro2EModeloDTO;
 	}
  
}