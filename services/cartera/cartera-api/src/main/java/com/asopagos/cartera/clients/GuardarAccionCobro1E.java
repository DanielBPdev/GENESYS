package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro1EModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro1E
 */
public class GuardarAccionCobro1E extends ServiceClient { 
    	private AccionCobro1EModeloDTO accionCobro1EModeloDTO;
  
  
 	public GuardarAccionCobro1E (AccionCobro1EModeloDTO accionCobro1EModeloDTO){
 		super();
		this.accionCobro1EModeloDTO=accionCobro1EModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro1EModeloDTO == null ? null : Entity.json(accionCobro1EModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro1EModeloDTO (AccionCobro1EModeloDTO accionCobro1EModeloDTO){
 		this.accionCobro1EModeloDTO=accionCobro1EModeloDTO;
 	}
 	
 	public AccionCobro1EModeloDTO getAccionCobro1EModeloDTO (){
 		return accionCobro1EModeloDTO;
 	}
  
}