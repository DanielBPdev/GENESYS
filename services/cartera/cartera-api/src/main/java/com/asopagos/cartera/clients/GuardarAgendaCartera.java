package com.asopagos.cartera.clients;

import java.util.List;
import com.asopagos.dto.modelo.AgendaCarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAgendaCartera
 */
public class GuardarAgendaCartera extends ServiceClient { 
    	private List<AgendaCarteraModeloDTO> agendasCarteraDTO;
  
  
 	public GuardarAgendaCartera (List<AgendaCarteraModeloDTO> agendasCarteraDTO){
 		super();
		this.agendasCarteraDTO=agendasCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(agendasCarteraDTO == null ? null : Entity.json(agendasCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAgendasCarteraDTO (List<AgendaCarteraModeloDTO> agendasCarteraDTO){
 		this.agendasCarteraDTO=agendasCarteraDTO;
 	}
 	
 	public List<AgendaCarteraModeloDTO> getAgendasCarteraDTO (){
 		return agendasCarteraDTO;
 	}
  
}