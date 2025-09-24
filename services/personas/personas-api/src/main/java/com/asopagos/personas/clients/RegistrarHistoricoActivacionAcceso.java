package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.HistoricoActivacionAccesoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/registrarHistoricoActivacion
 */
public class RegistrarHistoricoActivacionAcceso extends ServiceClient { 
    	private HistoricoActivacionAccesoModeloDTO activacionAcceso;
  
  
 	public RegistrarHistoricoActivacionAcceso (HistoricoActivacionAccesoModeloDTO activacionAcceso){
 		super();
		this.activacionAcceso=activacionAcceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(activacionAcceso == null ? null : Entity.json(activacionAcceso));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setActivacionAcceso (HistoricoActivacionAccesoModeloDTO activacionAcceso){
 		this.activacionAcceso=activacionAcceso;
 	}
 	
 	public HistoricoActivacionAccesoModeloDTO getActivacionAcceso (){
 		return activacionAcceso;
 	}
  
}