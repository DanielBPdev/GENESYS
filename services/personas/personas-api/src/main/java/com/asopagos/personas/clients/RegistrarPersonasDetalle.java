package com.asopagos.personas.clients;

import java.util.List;
import com.asopagos.dto.modelo.PersonaDetalleModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/registrarPersonasDetalle
 */
public class RegistrarPersonasDetalle extends ServiceClient {
 
  
  
  
 	public RegistrarPersonasDetalle (List<PersonaDetalleModeloDTO> personasDetalle){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
}