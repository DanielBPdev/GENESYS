package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobroAModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarAccionCobroA
 */
public class GuardarAccionCobroA extends ServiceClient { 
    	private AccionCobroAModeloDTO accionCobroAModeloDTO;
  
  
 	public GuardarAccionCobroA (AccionCobroAModeloDTO accionCobroAModeloDTO){
 		super();
		this.accionCobroAModeloDTO=accionCobroAModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(accionCobroAModeloDTO == null ? null : Entity.json(accionCobroAModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAccionCobroAModeloDTO (AccionCobroAModeloDTO accionCobroAModeloDTO){
 		this.accionCobroAModeloDTO=accionCobroAModeloDTO;
 	}
 	
 	public AccionCobroAModeloDTO getAccionCobroAModeloDTO (){
 		return accionCobroAModeloDTO;
 	}
  
}