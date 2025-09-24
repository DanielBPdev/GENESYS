package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ParametrizacionExclusionesModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarParametrizacionExclusiones
 */
public class GuardarParametrizacionExclusiones extends ServiceClient { 
    	private ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTO;
  
  
 	public GuardarParametrizacionExclusiones (ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTO){
 		super();
		this.parametrizacionExclusionesModeloDTO=parametrizacionExclusionesModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionExclusionesModeloDTO == null ? null : Entity.json(parametrizacionExclusionesModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionExclusionesModeloDTO (ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTO){
 		this.parametrizacionExclusionesModeloDTO=parametrizacionExclusionesModeloDTO;
 	}
 	
 	public ParametrizacionExclusionesModeloDTO getParametrizacionExclusionesModeloDTO (){
 		return parametrizacionExclusionesModeloDTO;
 	}
  
}