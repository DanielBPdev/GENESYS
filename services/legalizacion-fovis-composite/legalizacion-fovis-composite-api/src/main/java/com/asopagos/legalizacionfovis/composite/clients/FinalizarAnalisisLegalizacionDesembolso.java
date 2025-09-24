package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.legalizacionfovis.composite.dto.ResultadoAnalisisLegalizacionDesembolsoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/finalizarAnalisisLegalizacionDesembolso
 */
public class FinalizarAnalisisLegalizacionDesembolso extends ServiceClient { 
    	private ResultadoAnalisisLegalizacionDesembolsoDTO resultadoAnalisis;
  
  
 	public FinalizarAnalisisLegalizacionDesembolso (ResultadoAnalisisLegalizacionDesembolsoDTO resultadoAnalisis){
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
	

 
  
  	public void setResultadoAnalisis (ResultadoAnalisisLegalizacionDesembolsoDTO resultadoAnalisis){
 		this.resultadoAnalisis=resultadoAnalisis;
 	}
 	
 	public ResultadoAnalisisLegalizacionDesembolsoDTO getResultadoAnalisis (){
 		return resultadoAnalisis;
 	}
  
}