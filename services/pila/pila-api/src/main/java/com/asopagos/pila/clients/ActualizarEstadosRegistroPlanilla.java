package com.asopagos.pila.clients;

import java.lang.Boolean;
import com.asopagos.dto.ActualizacionEstadosPlanillaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/actualizarEstadosRegistroPlanilla
 */
public class ActualizarEstadosRegistroPlanilla extends ServiceClient { 
    	private ActualizacionEstadosPlanillaDTO actualizacionEstadosPlanillaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ActualizarEstadosRegistroPlanilla (ActualizacionEstadosPlanillaDTO actualizacionEstadosPlanillaDTO){
 		super();
		this.actualizacionEstadosPlanillaDTO=actualizacionEstadosPlanillaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(actualizacionEstadosPlanillaDTO == null ? null : Entity.json(actualizacionEstadosPlanillaDTO));
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

 
  
  	public void setActualizacionEstadosPlanillaDTO (ActualizacionEstadosPlanillaDTO actualizacionEstadosPlanillaDTO){
 		this.actualizacionEstadosPlanillaDTO=actualizacionEstadosPlanillaDTO;
 	}
 	
 	public ActualizacionEstadosPlanillaDTO getActualizacionEstadosPlanillaDTO (){
 		return actualizacionEstadosPlanillaDTO;
 	}
  
}