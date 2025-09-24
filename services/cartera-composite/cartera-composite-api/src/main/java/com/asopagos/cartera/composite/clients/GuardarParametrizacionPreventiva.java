package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.modelo.ParametrizacionPreventivaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/guardarParametrizacionPreventiva
 */
public class GuardarParametrizacionPreventiva extends ServiceClient { 
    	private ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO;
  
  
 	public GuardarParametrizacionPreventiva (ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO){
 		super();
		this.parametrizacionPreventivaModeloDTO=parametrizacionPreventivaModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionPreventivaModeloDTO == null ? null : Entity.json(parametrizacionPreventivaModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionPreventivaModeloDTO (ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO){
 		this.parametrizacionPreventivaModeloDTO=parametrizacionPreventivaModeloDTO;
 	}
 	
 	public ParametrizacionPreventivaModeloDTO getParametrizacionPreventivaModeloDTO (){
 		return parametrizacionPreventivaModeloDTO;
 	}
  
}