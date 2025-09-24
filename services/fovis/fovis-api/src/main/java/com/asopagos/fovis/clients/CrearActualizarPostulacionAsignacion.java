package com.asopagos.fovis.clients;

import com.asopagos.dto.PostulacionAsignacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarPostulacionAsignacion
 */
public class CrearActualizarPostulacionAsignacion extends ServiceClient { 
    	private PostulacionAsignacionDTO postulacionAsignacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private PostulacionAsignacionDTO result;
  
 	public CrearActualizarPostulacionAsignacion (PostulacionAsignacionDTO postulacionAsignacionDTO){
 		super();
		this.postulacionAsignacionDTO=postulacionAsignacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(postulacionAsignacionDTO == null ? null : Entity.json(postulacionAsignacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (PostulacionAsignacionDTO) response.readEntity(PostulacionAsignacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public PostulacionAsignacionDTO getResult() {
		return result;
	}

 
  
  	public void setPostulacionAsignacionDTO (PostulacionAsignacionDTO postulacionAsignacionDTO){
 		this.postulacionAsignacionDTO=postulacionAsignacionDTO;
 	}
 	
 	public PostulacionAsignacionDTO getPostulacionAsignacionDTO (){
 		return postulacionAsignacionDTO;
 	}
  
}