package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pilaBandeja/anularPlanillaOF
 */
public class AnularPlanillaOF extends ServiceClient { 
    	private InconsistenciaDTO inconsistencia;
  
  
 	public AnularPlanillaOF (InconsistenciaDTO inconsistencia){
 		super();
		this.inconsistencia=inconsistencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(inconsistencia == null ? null : Entity.json(inconsistencia));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInconsistencia (InconsistenciaDTO inconsistencia){
 		this.inconsistencia=inconsistencia;
 	}
 	
 	public InconsistenciaDTO getInconsistencia (){
 		return inconsistencia;
 	}
  
}