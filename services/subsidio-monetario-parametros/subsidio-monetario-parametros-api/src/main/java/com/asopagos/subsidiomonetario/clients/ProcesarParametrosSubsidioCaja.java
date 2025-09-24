package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.ParametrizacionCondicionesSubsidioCajaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/procesarParametrosSubsidioCaja
 */
public class ProcesarParametrosSubsidioCaja extends ServiceClient { 
    	private ParametrizacionCondicionesSubsidioCajaDTO parametrizacionCondicionesSubsidioCaja;
  
  
 	public ProcesarParametrosSubsidioCaja (ParametrizacionCondicionesSubsidioCajaDTO parametrizacionCondicionesSubsidioCaja){
 		super();
		this.parametrizacionCondicionesSubsidioCaja=parametrizacionCondicionesSubsidioCaja;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionCondicionesSubsidioCaja == null ? null : Entity.json(parametrizacionCondicionesSubsidioCaja));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionCondicionesSubsidioCaja (ParametrizacionCondicionesSubsidioCajaDTO parametrizacionCondicionesSubsidioCaja){
 		this.parametrizacionCondicionesSubsidioCaja=parametrizacionCondicionesSubsidioCaja;
 	}
 	
 	public ParametrizacionCondicionesSubsidioCajaDTO getParametrizacionCondicionesSubsidioCaja (){
 		return parametrizacionCondicionesSubsidioCaja;
 	}
  
}