package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.afiliaciones.personas.web.composite.dto.ConsultaConceptoEscalamientoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/consultaConceptoEscalamiento
 */
public class ConsultaConceptoEscalamiento extends ServiceClient { 
    	private ConsultaConceptoEscalamientoDTO inDTO;
  
  
 	public ConsultaConceptoEscalamiento (ConsultaConceptoEscalamientoDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInDTO (ConsultaConceptoEscalamientoDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public ConsultaConceptoEscalamientoDTO getInDTO (){
 		return inDTO;
 	}
  
}