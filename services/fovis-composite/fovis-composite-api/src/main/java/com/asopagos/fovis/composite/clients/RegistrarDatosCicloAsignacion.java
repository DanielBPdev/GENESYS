package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/registrarDatosCicloAsignacion
 */
public class RegistrarDatosCicloAsignacion extends ServiceClient { 
    	private CicloAsignacionModeloDTO cicloAsignacionModelDTO;
  
  
 	public RegistrarDatosCicloAsignacion (CicloAsignacionModeloDTO cicloAsignacionModelDTO){
 		super();
		this.cicloAsignacionModelDTO=cicloAsignacionModelDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cicloAsignacionModelDTO == null ? null : Entity.json(cicloAsignacionModelDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCicloAsignacionModelDTO (CicloAsignacionModeloDTO cicloAsignacionModelDTO){
 		this.cicloAsignacionModelDTO=cicloAsignacionModelDTO;
 	}
 	
 	public CicloAsignacionModeloDTO getCicloAsignacionModelDTO (){
 		return cicloAsignacionModelDTO;
 	}
  
}