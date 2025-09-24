package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.fovis.EjecucionAsignacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/asignacionFovisComposite/aceptarResultadosEjecucionAsignacion
 */
public class AceptarResultadosEjecucionAsignacion extends ServiceClient { 
    	private EjecucionAsignacionDTO ejecucionAsignacionDTO;
  
  
 	public AceptarResultadosEjecucionAsignacion (EjecucionAsignacionDTO ejecucionAsignacionDTO){
 		super();
		this.ejecucionAsignacionDTO=ejecucionAsignacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(ejecucionAsignacionDTO == null ? null : Entity.json(ejecucionAsignacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEjecucionAsignacionDTO (EjecucionAsignacionDTO ejecucionAsignacionDTO){
 		this.ejecucionAsignacionDTO=ejecucionAsignacionDTO;
 	}
 	
 	public EjecucionAsignacionDTO getEjecucionAsignacionDTO (){
 		return ejecucionAsignacionDTO;
 	}
  
}