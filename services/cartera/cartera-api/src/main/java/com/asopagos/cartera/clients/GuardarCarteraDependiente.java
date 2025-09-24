package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CarteraDependienteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarCarteraDependiente
 */
public class GuardarCarteraDependiente extends ServiceClient { 
    	private CarteraDependienteModeloDTO carteraDependienteDTO;
  
  
 	public GuardarCarteraDependiente (CarteraDependienteModeloDTO carteraDependienteDTO){
 		super();
		this.carteraDependienteDTO=carteraDependienteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(carteraDependienteDTO == null ? null : Entity.json(carteraDependienteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCarteraDependienteDTO (CarteraDependienteModeloDTO carteraDependienteDTO){
 		this.carteraDependienteDTO=carteraDependienteDTO;
 	}
 	
 	public CarteraDependienteModeloDTO getCarteraDependienteDTO (){
 		return carteraDependienteDTO;
 	}
  
}