package com.asopagos.cartera.composite.clients;

import com.asopagos.cartera.composite.dto.ResultadoGestionPreventivaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/registrarGestionPreventiva
 */
public class RegistrarGestionPreventiva extends ServiceClient { 
    	private ResultadoGestionPreventivaDTO resultadoGestionPreventivaDTO;
  
  
 	public RegistrarGestionPreventiva (ResultadoGestionPreventivaDTO resultadoGestionPreventivaDTO){
 		super();
		this.resultadoGestionPreventivaDTO=resultadoGestionPreventivaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(resultadoGestionPreventivaDTO == null ? null : Entity.json(resultadoGestionPreventivaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setResultadoGestionPreventivaDTO (ResultadoGestionPreventivaDTO resultadoGestionPreventivaDTO){
 		this.resultadoGestionPreventivaDTO=resultadoGestionPreventivaDTO;
 	}
 	
 	public ResultadoGestionPreventivaDTO getResultadoGestionPreventivaDTO (){
 		return resultadoGestionPreventivaDTO;
 	}
  
}