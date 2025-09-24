package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.ResultadoCierreDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cierre/emitirResultadoAnalista
 */
public class EmitirResultadoAnalista extends ServiceClient { 
    	private ResultadoCierreDTO resultadoCierre;
  
  
 	public EmitirResultadoAnalista (ResultadoCierreDTO resultadoCierre){
 		super();
		this.resultadoCierre=resultadoCierre;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(resultadoCierre == null ? null : Entity.json(resultadoCierre));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setResultadoCierre (ResultadoCierreDTO resultadoCierre){
 		this.resultadoCierre=resultadoCierre;
 	}
 	
 	public ResultadoCierreDTO getResultadoCierre (){
 		return resultadoCierre;
 	}
  
}