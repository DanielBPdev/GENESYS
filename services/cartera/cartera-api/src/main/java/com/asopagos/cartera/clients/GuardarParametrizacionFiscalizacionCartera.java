package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ParametrizacionFiscalizacionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarParametrizacionFiscalizacion
 */
public class GuardarParametrizacionFiscalizacionCartera extends ServiceClient { 
    	private ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO;
  
  
 	public GuardarParametrizacionFiscalizacionCartera (ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO){
 		super();
		this.parametrizacionFiscalizacionModeloDTO=parametrizacionFiscalizacionModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionFiscalizacionModeloDTO == null ? null : Entity.json(parametrizacionFiscalizacionModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionFiscalizacionModeloDTO (ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO){
 		this.parametrizacionFiscalizacionModeloDTO=parametrizacionFiscalizacionModeloDTO;
 	}
 	
 	public ParametrizacionFiscalizacionModeloDTO getParametrizacionFiscalizacionModeloDTO (){
 		return parametrizacionFiscalizacionModeloDTO;
 	}
  
}