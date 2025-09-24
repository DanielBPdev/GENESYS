package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/verificarCicloFiscalizacionAportante
 */
public class VerificarCicloFiscalizacionAportante extends ServiceClient { 
    	private SimulacionDTO simulacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public VerificarCicloFiscalizacionAportante (SimulacionDTO simulacionDTO){
 		super();
		this.simulacionDTO=simulacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(simulacionDTO == null ? null : Entity.json(simulacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setSimulacionDTO (SimulacionDTO simulacionDTO){
 		this.simulacionDTO=simulacionDTO;
 	}
 	
 	public SimulacionDTO getSimulacionDTO (){
 		return simulacionDTO;
 	}
  
}