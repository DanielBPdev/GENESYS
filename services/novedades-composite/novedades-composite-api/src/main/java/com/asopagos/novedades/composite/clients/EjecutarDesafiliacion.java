package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.EmpleadorAfiliadosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesEspecialesComposite/ejecutarDesafiliacion
 */
public class EjecutarDesafiliacion extends ServiceClient { 
    	private EmpleadorAfiliadosDTO desafiliacionDTO;
  
  
 	public EjecutarDesafiliacion (EmpleadorAfiliadosDTO desafiliacionDTO){
 		super();
		this.desafiliacionDTO=desafiliacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(desafiliacionDTO == null ? null : Entity.json(desafiliacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDesafiliacionDTO (EmpleadorAfiliadosDTO desafiliacionDTO){
 		this.desafiliacionDTO=desafiliacionDTO;
 	}
 	
 	public EmpleadorAfiliadosDTO getDesafiliacionDTO (){
 		return desafiliacionDTO;
 	}
  
}