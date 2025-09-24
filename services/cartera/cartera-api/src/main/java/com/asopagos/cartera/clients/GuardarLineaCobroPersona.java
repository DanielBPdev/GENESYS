package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.LineaCobroPersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarLineaCobroPersona
 */
public class GuardarLineaCobroPersona extends ServiceClient { 
    	private LineaCobroPersonaModeloDTO lineaCobroPersonaModeloDTO;
  
  
 	public GuardarLineaCobroPersona (LineaCobroPersonaModeloDTO lineaCobroPersonaModeloDTO){
 		super();
		this.lineaCobroPersonaModeloDTO=lineaCobroPersonaModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(lineaCobroPersonaModeloDTO == null ? null : Entity.json(lineaCobroPersonaModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setLineaCobroPersonaModeloDTO (LineaCobroPersonaModeloDTO lineaCobroPersonaModeloDTO){
 		this.lineaCobroPersonaModeloDTO=lineaCobroPersonaModeloDTO;
 	}
 	
 	public LineaCobroPersonaModeloDTO getLineaCobroPersonaModeloDTO (){
 		return lineaCobroPersonaModeloDTO;
 	}
  
}