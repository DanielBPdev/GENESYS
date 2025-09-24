package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.entidades.subsidiomonetario.pagos.RegistroInconsistenciaTarjeta;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/persistirRegistroInconsistenciaTarjeta
 */
public class PersistirRegistroInconsistenciaTarjeta extends ServiceClient { 
    	private RegistroInconsistenciaTarjeta registroInconsistencia;
  
  
 	public PersistirRegistroInconsistenciaTarjeta (RegistroInconsistenciaTarjeta registroInconsistencia){
 		super();
		this.registroInconsistencia=registroInconsistencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroInconsistencia == null ? null : Entity.json(registroInconsistencia));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRegistroInconsistencia (RegistroInconsistenciaTarjeta registroInconsistencia){
 		this.registroInconsistencia=registroInconsistencia;
 	}
 	
 	public RegistroInconsistenciaTarjeta getRegistroInconsistencia (){
 		return registroInconsistencia;
 	}
  
}