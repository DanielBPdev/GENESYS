package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.aportes.PilaAccionTransitorioEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/procesarPlanillaBandejaTransitoria
 */
public class ProcesarPlanillaBandejaTransitoria extends ServiceClient { 
   	private Long indicePlanilla;
  	private PilaAccionTransitorioEnum accion;
   
  
 	public ProcesarPlanillaBandejaTransitoria (Long indicePlanilla,PilaAccionTransitorioEnum accion){
 		super();
		this.indicePlanilla=indicePlanilla;
		this.accion=accion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("indicePlanilla", indicePlanilla)
			.queryParam("accion", accion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIndicePlanilla (Long indicePlanilla){
 		this.indicePlanilla=indicePlanilla;
 	}
 	
 	public Long getIndicePlanilla (){
 		return indicePlanilla;
 	}
  	public void setAccion (PilaAccionTransitorioEnum accion){
 		this.accion=accion;
 	}
 	
 	public PilaAccionTransitorioEnum getAccion (){
 		return accion;
 	}
  
  
}