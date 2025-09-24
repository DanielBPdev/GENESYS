package com.asopagos.consola.ejecucion.proceso.asincrono.clients;

import com.asopagos.dto.EjecucionProcesoAsincronoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/consolaEjecucionProcesosAsincronos/actualizarEjecucionProcesoAsincrono
 */
public class ActualizarEjecucionProcesoAsincrono extends ServiceClient { 
    	private EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO;
  
  
 	public ActualizarEjecucionProcesoAsincrono (EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO){
 		super();
		this.ejecucionProcesoAsincronoDTO=ejecucionProcesoAsincronoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(ejecucionProcesoAsincronoDTO == null ? null : Entity.json(ejecucionProcesoAsincronoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEjecucionProcesoAsincronoDTO (EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO){
 		this.ejecucionProcesoAsincronoDTO=ejecucionProcesoAsincronoDTO;
 	}
 	
 	public EjecucionProcesoAsincronoDTO getEjecucionProcesoAsincronoDTO (){
 		return ejecucionProcesoAsincronoDTO;
 	}
  
}