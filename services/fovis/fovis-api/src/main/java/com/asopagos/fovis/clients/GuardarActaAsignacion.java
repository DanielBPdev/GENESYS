package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.ActaAsignacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/guardarActaAsignacion
 */
public class GuardarActaAsignacion extends ServiceClient { 
    	private ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ActaAsignacionFOVISModeloDTO result;
  
 	public GuardarActaAsignacion (ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO){
 		super();
		this.actaAsignacionFOVISModeloDTO=actaAsignacionFOVISModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(actaAsignacionFOVISModeloDTO == null ? null : Entity.json(actaAsignacionFOVISModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ActaAsignacionFOVISModeloDTO) response.readEntity(ActaAsignacionFOVISModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ActaAsignacionFOVISModeloDTO getResult() {
		return result;
	}

 
  
  	public void setActaAsignacionFOVISModeloDTO (ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO){
 		this.actaAsignacionFOVISModeloDTO=actaAsignacionFOVISModeloDTO;
 	}
 	
 	public ActaAsignacionFOVISModeloDTO getActaAsignacionFOVISModeloDTO (){
 		return actaAsignacionFOVISModeloDTO;
 	}
  
}