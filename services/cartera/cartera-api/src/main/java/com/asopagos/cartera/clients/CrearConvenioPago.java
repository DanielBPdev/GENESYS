package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ConvenioPagoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarConvenioPago
 */
public class CrearConvenioPago extends ServiceClient { 
    	private ConvenioPagoModeloDTO convenioPagoDTO;
  
  
 	public CrearConvenioPago (ConvenioPagoModeloDTO convenioPagoDTO){
 		super();
		this.convenioPagoDTO=convenioPagoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(convenioPagoDTO == null ? null : Entity.json(convenioPagoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setConvenioPagoDTO (ConvenioPagoModeloDTO convenioPagoDTO){
 		this.convenioPagoDTO=convenioPagoDTO;
 	}
 	
 	public ConvenioPagoModeloDTO getConvenioPagoDTO (){
 		return convenioPagoDTO;
 	}
  
}