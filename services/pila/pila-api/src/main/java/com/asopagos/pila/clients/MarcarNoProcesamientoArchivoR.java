package com.asopagos.pila.clients;

import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/marcarNoProcesamientoArchivoR
 */
public class MarcarNoProcesamientoArchivoR extends ServiceClient { 
    	private PlanillaGestionManualDTO planillaDTO;
  
  
 	public MarcarNoProcesamientoArchivoR (PlanillaGestionManualDTO planillaDTO){
 		super();
		this.planillaDTO=planillaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(planillaDTO == null ? null : Entity.json(planillaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setPlanillaDTO (PlanillaGestionManualDTO planillaDTO){
 		this.planillaDTO=planillaDTO;
 	}
 	
 	public PlanillaGestionManualDTO getPlanillaDTO (){
 		return planillaDTO;
 	}
  
}