package com.asopagos.pila.clients;

import java.util.List;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/registrarNoRegistrarCorreccionesEnBloque
 */
public class RegistrarNoRegistrarCorreccionesEnBloque extends ServiceClient { 
    	private List<RegistrarCorreccionAdicionDTO> registrarCorreccionDTO;
  
  
 	public RegistrarNoRegistrarCorreccionesEnBloque (List<RegistrarCorreccionAdicionDTO> registrarCorreccionDTO){
 		super();
		this.registrarCorreccionDTO=registrarCorreccionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registrarCorreccionDTO == null ? null : Entity.json(registrarCorreccionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRegistrarCorreccionDTO (List<RegistrarCorreccionAdicionDTO> registrarCorreccionDTO){
 		this.registrarCorreccionDTO=registrarCorreccionDTO;
 	}
 	
 	public List<RegistrarCorreccionAdicionDTO> getRegistrarCorreccionDTO (){
 		return registrarCorreccionDTO;
 	}
  
}