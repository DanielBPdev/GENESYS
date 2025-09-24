package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ParametrizacionDesafiliacionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarParametrizacionDesafiliacion
 */
public class GuardarParametrizacionDesafiliacion extends ServiceClient { 
    	private ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO;
  
  
 	public GuardarParametrizacionDesafiliacion (ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO){
 		super();
		this.parametrizacionDesafiliacionModeloDTO=parametrizacionDesafiliacionModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionDesafiliacionModeloDTO == null ? null : Entity.json(parametrizacionDesafiliacionModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionDesafiliacionModeloDTO (ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO){
 		this.parametrizacionDesafiliacionModeloDTO=parametrizacionDesafiliacionModeloDTO;
 	}
 	
 	public ParametrizacionDesafiliacionModeloDTO getParametrizacionDesafiliacionModeloDTO (){
 		return parametrizacionDesafiliacionModeloDTO;
 	}
  
}