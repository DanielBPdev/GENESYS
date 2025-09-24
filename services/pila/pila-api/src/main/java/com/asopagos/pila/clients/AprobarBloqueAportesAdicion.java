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
 * /rest/pila/aprobarBloqueAportesAdicion
 */
public class AprobarBloqueAportesAdicion extends ServiceClient { 
    	private List<RegistrarCorreccionAdicionDTO> criteriosSimulacion;
  
  
 	public AprobarBloqueAportesAdicion (List<RegistrarCorreccionAdicionDTO> criteriosSimulacion){
 		super();
		this.criteriosSimulacion=criteriosSimulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(criteriosSimulacion == null ? null : Entity.json(criteriosSimulacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCriteriosSimulacion (List<RegistrarCorreccionAdicionDTO> criteriosSimulacion){
 		this.criteriosSimulacion=criteriosSimulacion;
 	}
 	
 	public List<RegistrarCorreccionAdicionDTO> getCriteriosSimulacion (){
 		return criteriosSimulacion;
 	}
  
}