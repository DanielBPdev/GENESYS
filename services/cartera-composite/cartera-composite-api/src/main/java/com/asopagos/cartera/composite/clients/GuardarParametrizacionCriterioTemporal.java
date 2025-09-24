package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/guardarParametrizacionCriterioTemporal
 */
public class GuardarParametrizacionCriterioTemporal extends ServiceClient { 
    	private ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionDTO;
  
  
 	public GuardarParametrizacionCriterioTemporal (ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionDTO){
 		super();
		this.parametrizacionDTO=parametrizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionDTO == null ? null : Entity.json(parametrizacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionDTO (ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionDTO){
 		this.parametrizacionDTO=parametrizacionDTO;
 	}
 	
 	public ParametrizacionCriteriosGestionCobroModeloDTO getParametrizacionDTO (){
 		return parametrizacionDTO;
 	}
  
}