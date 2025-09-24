package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.modelo.ActaAsignacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/asignacionFovisComposite/aprobarDocumentoAsignacion
 */
public class AprobarDocumentoAsignacion extends ServiceClient { 
    	private ActaAsignacionFOVISModeloDTO actaAsignacionFOVISDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ActaAsignacionFOVISModeloDTO result;
  
 	public AprobarDocumentoAsignacion (ActaAsignacionFOVISModeloDTO actaAsignacionFOVISDTO){
 		super();
		this.actaAsignacionFOVISDTO=actaAsignacionFOVISDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(actaAsignacionFOVISDTO == null ? null : Entity.json(actaAsignacionFOVISDTO));
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

 
  
  	public void setActaAsignacionFOVISDTO (ActaAsignacionFOVISModeloDTO actaAsignacionFOVISDTO){
 		this.actaAsignacionFOVISDTO=actaAsignacionFOVISDTO;
 	}
 	
 	public ActaAsignacionFOVISModeloDTO getActaAsignacionFOVISDTO (){
 		return actaAsignacionFOVISDTO;
 	}
  
}