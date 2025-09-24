package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/asignacionFovisComposite/calcularCalificacionPostulacion
 */
public class CalcularCalificacionPostulacion extends ServiceClient { 
    	private PostulacionFOVISModeloDTO postulacionFOVISDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private PostulacionFOVISModeloDTO result;
  
 	public CalcularCalificacionPostulacion (PostulacionFOVISModeloDTO postulacionFOVISDTO){
 		super();
		this.postulacionFOVISDTO=postulacionFOVISDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(postulacionFOVISDTO == null ? null : Entity.json(postulacionFOVISDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (PostulacionFOVISModeloDTO) response.readEntity(PostulacionFOVISModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public PostulacionFOVISModeloDTO getResult() {
		return result;
	}

 
  
  	public void setPostulacionFOVISDTO (PostulacionFOVISModeloDTO postulacionFOVISDTO){
 		this.postulacionFOVISDTO=postulacionFOVISDTO;
 	}
 	
 	public PostulacionFOVISModeloDTO getPostulacionFOVISDTO (){
 		return postulacionFOVISDTO;
 	}
  
}