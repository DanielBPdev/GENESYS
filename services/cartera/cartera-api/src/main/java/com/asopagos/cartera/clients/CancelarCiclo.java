package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CicloCarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/cancelarCiclo
 */
public class CancelarCiclo extends ServiceClient { 
    	private CicloCarteraModeloDTO cicloFiscalizacionModeloDTO;
  
  
 	public CancelarCiclo (CicloCarteraModeloDTO cicloFiscalizacionModeloDTO){
 		super();
		this.cicloFiscalizacionModeloDTO=cicloFiscalizacionModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cicloFiscalizacionModeloDTO == null ? null : Entity.json(cicloFiscalizacionModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCicloFiscalizacionModeloDTO (CicloCarteraModeloDTO cicloFiscalizacionModeloDTO){
 		this.cicloFiscalizacionModeloDTO=cicloFiscalizacionModeloDTO;
 	}
 	
 	public CicloCarteraModeloDTO getCicloFiscalizacionModeloDTO (){
 		return cicloFiscalizacionModeloDTO;
 	}
  
}