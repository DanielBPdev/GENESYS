package com.asopagos.reportes.clients;

import com.asopagos.reportes.dto.ParametrizacionMetaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/reportes/actualizarParametrizacionMeta
 */
public class ActualizarParametrizacionMeta extends ServiceClient { 
    	private ParametrizacionMetaDTO parametrizacionMetasDTO;
  
  
 	public ActualizarParametrizacionMeta (ParametrizacionMetaDTO parametrizacionMetasDTO){
 		super();
		this.parametrizacionMetasDTO=parametrizacionMetasDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionMetasDTO == null ? null : Entity.json(parametrizacionMetasDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionMetasDTO (ParametrizacionMetaDTO parametrizacionMetasDTO){
 		this.parametrizacionMetasDTO=parametrizacionMetasDTO;
 	}
 	
 	public ParametrizacionMetaDTO getParametrizacionMetasDTO (){
 		return parametrizacionMetasDTO;
 	}
  
}