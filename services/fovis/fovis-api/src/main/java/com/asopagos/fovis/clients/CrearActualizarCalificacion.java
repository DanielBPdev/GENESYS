package com.asopagos.fovis.clients;

import com.asopagos.dto.CalificacionPostulacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarCalificacion
 */
public class CrearActualizarCalificacion extends ServiceClient { 
    	private CalificacionPostulacionDTO calificacionPostulacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CalificacionPostulacionDTO result;
  
 	public CrearActualizarCalificacion (CalificacionPostulacionDTO calificacionPostulacionDTO){
 		super();
		this.calificacionPostulacionDTO=calificacionPostulacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(calificacionPostulacionDTO == null ? null : Entity.json(calificacionPostulacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (CalificacionPostulacionDTO) response.readEntity(CalificacionPostulacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CalificacionPostulacionDTO getResult() {
		return result;
	}

 
  
  	public void setCalificacionPostulacionDTO (CalificacionPostulacionDTO calificacionPostulacionDTO){
 		this.calificacionPostulacionDTO=calificacionPostulacionDTO;
 	}
 	
 	public CalificacionPostulacionDTO getCalificacionPostulacionDTO (){
 		return calificacionPostulacionDTO;
 	}
  
}