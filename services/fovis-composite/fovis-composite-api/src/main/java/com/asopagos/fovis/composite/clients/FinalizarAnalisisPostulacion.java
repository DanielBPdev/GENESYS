package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.ResultadoAnalisisPostulacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/finalizarAnalisisPostulacion
 */
public class FinalizarAnalisisPostulacion extends ServiceClient { 
    	private ResultadoAnalisisPostulacionDTO resultadoAnalisis;
  
  
 	public FinalizarAnalisisPostulacion (ResultadoAnalisisPostulacionDTO resultadoAnalisis){
 		super();
		this.resultadoAnalisis=resultadoAnalisis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(resultadoAnalisis == null ? null : Entity.json(resultadoAnalisis));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setResultadoAnalisis (ResultadoAnalisisPostulacionDTO resultadoAnalisis){
 		this.resultadoAnalisis=resultadoAnalisis;
 	}
 	
 	public ResultadoAnalisisPostulacionDTO getResultadoAnalisis (){
 		return resultadoAnalisis;
 	}
  
}