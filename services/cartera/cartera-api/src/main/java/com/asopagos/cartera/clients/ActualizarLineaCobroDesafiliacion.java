package com.asopagos.cartera.clients;

import java.util.List;
import com.asopagos.dto.modelo.DesafiliacionAportanteDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/actualizarLineaCobroDesafiliacion
 */
public class ActualizarLineaCobroDesafiliacion extends ServiceClient { 
   	private String usuarioTraspaso;
   	private List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs;
  
  
 	public ActualizarLineaCobroDesafiliacion (String usuarioTraspaso,List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs){
 		super();
		this.usuarioTraspaso=usuarioTraspaso;
		this.desafiliacionAportanteDTOs=desafiliacionAportanteDTOs;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("usuarioTraspaso", usuarioTraspaso)
			.request(MediaType.APPLICATION_JSON)
			.post(desafiliacionAportanteDTOs == null ? null : Entity.json(desafiliacionAportanteDTOs));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setUsuarioTraspaso (String usuarioTraspaso){
 		this.usuarioTraspaso=usuarioTraspaso;
 	}
 	
 	public String getUsuarioTraspaso (){
 		return usuarioTraspaso;
 	}
  
  	public void setDesafiliacionAportanteDTOs (List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs){
 		this.desafiliacionAportanteDTOs=desafiliacionAportanteDTOs;
 	}
 	
 	public List<DesafiliacionAportanteDTO> getDesafiliacionAportanteDTOs (){
 		return desafiliacionAportanteDTOs;
 	}
  
}