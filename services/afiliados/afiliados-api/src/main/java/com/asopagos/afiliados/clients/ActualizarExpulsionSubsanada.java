package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.ExpulsionSubsanadaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/actualizarExpulsionSubsanada
 */
public class ActualizarExpulsionSubsanada extends ServiceClient { 
    	private ExpulsionSubsanadaModeloDTO expulsionSubsanadaModeloDTO;
  
  
 	public ActualizarExpulsionSubsanada (ExpulsionSubsanadaModeloDTO expulsionSubsanadaModeloDTO){
 		super();
		this.expulsionSubsanadaModeloDTO=expulsionSubsanadaModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(expulsionSubsanadaModeloDTO == null ? null : Entity.json(expulsionSubsanadaModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setExpulsionSubsanadaModeloDTO (ExpulsionSubsanadaModeloDTO expulsionSubsanadaModeloDTO){
 		this.expulsionSubsanadaModeloDTO=expulsionSubsanadaModeloDTO;
 	}
 	
 	public ExpulsionSubsanadaModeloDTO getExpulsionSubsanadaModeloDTO (){
 		return expulsionSubsanadaModeloDTO;
 	}
  
}