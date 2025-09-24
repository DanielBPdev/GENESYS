package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.CriteriosParametrizacionTemporalDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/guardarParametrizacionGestionCobroTemporal
 */
public class GuardarParametrizacionGestionCobroTemporal extends ServiceClient { 
    	private CriteriosParametrizacionTemporalDTO criterioParametrizacionDTO;
  
  
 	public GuardarParametrizacionGestionCobroTemporal (CriteriosParametrizacionTemporalDTO criterioParametrizacionDTO){
 		super();
		this.criterioParametrizacionDTO=criterioParametrizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(criterioParametrizacionDTO == null ? null : Entity.json(criterioParametrizacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCriterioParametrizacionDTO (CriteriosParametrizacionTemporalDTO criterioParametrizacionDTO){
 		this.criterioParametrizacionDTO=criterioParametrizacionDTO;
 	}
 	
 	public CriteriosParametrizacionTemporalDTO getCriterioParametrizacionDTO (){
 		return criterioParametrizacionDTO;
 	}
  
}