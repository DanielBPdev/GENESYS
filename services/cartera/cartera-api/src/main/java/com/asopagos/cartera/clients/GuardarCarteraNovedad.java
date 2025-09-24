package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CarteraNovedadModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarCarteraNovedad
 */
public class GuardarCarteraNovedad extends ServiceClient { 
    	private CarteraNovedadModeloDTO carteraNovedadDTO;
  
  
 	public GuardarCarteraNovedad (CarteraNovedadModeloDTO carteraNovedadDTO){
 		super();
		this.carteraNovedadDTO=carteraNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(carteraNovedadDTO == null ? null : Entity.json(carteraNovedadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCarteraNovedadDTO (CarteraNovedadModeloDTO carteraNovedadDTO){
 		this.carteraNovedadDTO=carteraNovedadDTO;
 	}
 	
 	public CarteraNovedadModeloDTO getCarteraNovedadDTO (){
 		return carteraNovedadDTO;
 	}
  
}