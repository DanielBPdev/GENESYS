package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro1FModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobro1F
 */
public class GuardarAccionCobro1F extends ServiceClient { 
    	private AccionCobro1FModeloDTO accionCobro1FModeloDTO;
  
  
 	public GuardarAccionCobro1F (AccionCobro1FModeloDTO accionCobro1FModeloDTO){
 		super();
		this.accionCobro1FModeloDTO=accionCobro1FModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobro1FModeloDTO == null ? null : Entity.json(accionCobro1FModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobro1FModeloDTO (AccionCobro1FModeloDTO accionCobro1FModeloDTO){
 		this.accionCobro1FModeloDTO=accionCobro1FModeloDTO;
 	}
 	
 	public AccionCobro1FModeloDTO getAccionCobro1FModeloDTO (){
 		return accionCobro1FModeloDTO;
 	}
  
}