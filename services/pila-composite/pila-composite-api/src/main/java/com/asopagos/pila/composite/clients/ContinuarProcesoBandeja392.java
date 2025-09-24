package com.asopagos.pila.composite.clients;

import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/continuarProcesoBandeja392
 */
public class ContinuarProcesoBandeja392 extends ServiceClient { 
    	private InconsistenciaDTO inconsistencia;
  
  
 	public ContinuarProcesoBandeja392 (InconsistenciaDTO inconsistencia){
 		super();
		this.inconsistencia=inconsistencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inconsistencia == null ? null : Entity.json(inconsistencia));
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