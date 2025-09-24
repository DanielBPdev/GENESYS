package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ParametrizacionPreventivaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarParametrizacionPreventivaCartera
 */
public class GuardarParametrizacionPreventivaCartera extends ServiceClient { 
    	private ParametrizacionPreventivaModeloDTO parametrizacionDTO;
  
  
 	public GuardarParametrizacionPreventivaCartera (ParametrizacionPreventivaModeloDTO parametrizacionDTO){
 		super();
		this.parametrizacionDTO=parametrizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionDTO == null ? null : Entity.json(parametrizacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionDTO (ParametrizacionPreventivaModeloDTO parametrizacionDTO){
 		this.parametrizacionDTO=parametrizacionDTO;
 	}
 	
 	public ParametrizacionPreventivaModeloDTO getParametrizacionDTO (){
 		return parametrizacionDTO;
 	}
  
}