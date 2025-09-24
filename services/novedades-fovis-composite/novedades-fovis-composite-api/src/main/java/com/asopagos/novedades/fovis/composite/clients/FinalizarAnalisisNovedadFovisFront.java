package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.novedades.fovis.composite.dto.AnalisisSolicitudNovedadFovisDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovisComposite/finalizarAnalisisNovedadFovisFront
 */
public class FinalizarAnalisisNovedadFovisFront extends ServiceClient { 
    	private AnalisisSolicitudNovedadFovisDTO resultadoAnalisis;
  
  
 	public FinalizarAnalisisNovedadFovisFront (AnalisisSolicitudNovedadFovisDTO resultadoAnalisis){
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
	

 
  
  	public void setResultadoAnalisis (AnalisisSolicitudNovedadFovisDTO resultadoAnalisis){
 		this.resultadoAnalisis=resultadoAnalisis;
 	}
 	
 	public AnalisisSolicitudNovedadFovisDTO getResultadoAnalisis (){
 		return resultadoAnalisis;
 	}
  
}