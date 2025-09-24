package com.asopagos.novedades.clients;

import com.asopagos.dto.modelo.RegistroNovedadFuturaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/crearNovedadFutura
 */
public class CrearNovedadFutura extends ServiceClient { 
    	private RegistroNovedadFuturaModeloDTO novedadFuturaDTO;
  
  
 	public CrearNovedadFutura (RegistroNovedadFuturaModeloDTO novedadFuturaDTO){
 		super();
		this.novedadFuturaDTO=novedadFuturaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(novedadFuturaDTO == null ? null : Entity.json(novedadFuturaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setNovedadFuturaDTO (RegistroNovedadFuturaModeloDTO novedadFuturaDTO){
 		this.novedadFuturaDTO=novedadFuturaDTO;
 	}
 	
 	public RegistroNovedadFuturaModeloDTO getNovedadFuturaDTO (){
 		return novedadFuturaDTO;
 	}
  
}