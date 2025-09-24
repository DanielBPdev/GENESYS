package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.novedades.fovis.composite.dto.AnalisisSolicitudNovedadFovisDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovisComposite/finalizarAnalisisNovedadFovisBack
 */
public class FinalizarAnalisisNovedadFovisBack extends ServiceClient { 
    	private AnalisisSolicitudNovedadFovisDTO resultadoEscalamiento;
  
  
 	public FinalizarAnalisisNovedadFovisBack (AnalisisSolicitudNovedadFovisDTO resultadoEscalamiento){
 		super();
		this.resultadoEscalamiento=resultadoEscalamiento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(resultadoEscalamiento == null ? null : Entity.json(resultadoEscalamiento));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setResultadoEscalamiento (AnalisisSolicitudNovedadFovisDTO resultadoEscalamiento){
 		this.resultadoEscalamiento=resultadoEscalamiento;
 	}
 	
 	public AnalisisSolicitudNovedadFovisDTO getResultadoEscalamiento (){
 		return resultadoEscalamiento;
 	}
  
}