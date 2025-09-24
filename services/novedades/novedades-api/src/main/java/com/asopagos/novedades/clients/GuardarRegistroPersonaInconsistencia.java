package com.asopagos.novedades.clients;

import java.util.List;
import com.asopagos.novedades.dto.RegistroPersonaInconsistenteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/guardarRegistroPersonaInconsistencia
 */
public class GuardarRegistroPersonaInconsistencia extends ServiceClient { 
    	private List<RegistroPersonaInconsistenteDTO> dto;
  
  
 	public GuardarRegistroPersonaInconsistencia (List<RegistroPersonaInconsistenteDTO> dto){
 		super();
		this.dto=dto;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(dto == null ? null : Entity.json(dto));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDto (List<RegistroPersonaInconsistenteDTO> dto){
 		this.dto=dto;
 	}
 	
 	public List<RegistroPersonaInconsistenteDTO> getDto (){
 		return dto;
 	}
  
}