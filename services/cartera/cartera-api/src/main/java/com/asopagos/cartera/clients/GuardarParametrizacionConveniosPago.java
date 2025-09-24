package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ParametrizacionConveniosPagoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarParametrizacionConveniosPago
 */
public class GuardarParametrizacionConveniosPago extends ServiceClient { 
    	private ParametrizacionConveniosPagoModeloDTO parametrizacionConvenioPagoDTO;
  
  
 	public GuardarParametrizacionConveniosPago (ParametrizacionConveniosPagoModeloDTO parametrizacionConvenioPagoDTO){
 		super();
		this.parametrizacionConvenioPagoDTO=parametrizacionConvenioPagoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionConvenioPagoDTO == null ? null : Entity.json(parametrizacionConvenioPagoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionConvenioPagoDTO (ParametrizacionConveniosPagoModeloDTO parametrizacionConvenioPagoDTO){
 		this.parametrizacionConvenioPagoDTO=parametrizacionConvenioPagoDTO;
 	}
 	
 	public ParametrizacionConveniosPagoModeloDTO getParametrizacionConvenioPagoDTO (){
 		return parametrizacionConvenioPagoDTO;
 	}
  
}