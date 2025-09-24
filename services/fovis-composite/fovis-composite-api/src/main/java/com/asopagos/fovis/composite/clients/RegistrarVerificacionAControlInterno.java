package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.RegistrarVerificacionControlInternoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/registrarVerificacionControlInterno
 */
public class RegistrarVerificacionAControlInterno extends ServiceClient { 
    	private RegistrarVerificacionControlInternoDTO registroVerificacionDTO;
  
  
 	public RegistrarVerificacionAControlInterno (RegistrarVerificacionControlInternoDTO registroVerificacionDTO){
 		super();
		this.registroVerificacionDTO=registroVerificacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroVerificacionDTO == null ? null : Entity.json(registroVerificacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRegistroVerificacionDTO (RegistrarVerificacionControlInternoDTO registroVerificacionDTO){
 		this.registroVerificacionDTO=registroVerificacionDTO;
 	}
 	
 	public RegistrarVerificacionControlInternoDTO getRegistroVerificacionDTO (){
 		return registroVerificacionDTO;
 	}
  
}