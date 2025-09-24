package com.asopagos.entidades.pagadoras.clients;

import com.asopagos.entidades.pagadoras.dto.EntidadPagadoraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/entidadesPagadoras
 */
public class ActualizarEntidadPagadora extends ServiceClient { 
    	private EntidadPagadoraDTO entidadPagadoraDTO;
  
  
 	public ActualizarEntidadPagadora (EntidadPagadoraDTO entidadPagadoraDTO){
 		super();
		this.entidadPagadoraDTO=entidadPagadoraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(entidadPagadoraDTO == null ? null : Entity.json(entidadPagadoraDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEntidadPagadoraDTO (EntidadPagadoraDTO entidadPagadoraDTO){
 		this.entidadPagadoraDTO=entidadPagadoraDTO;
 	}
 	
 	public EntidadPagadoraDTO getEntidadPagadoraDTO (){
 		return entidadPagadoraDTO;
 	}
  
}