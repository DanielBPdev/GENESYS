package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.UbicacionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/crearActualizarUbicacion
 */
public class CrearActualizarUbicacion extends ServiceClient { 
    	private UbicacionModeloDTO ubicacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private UbicacionModeloDTO result;
  
 	public CrearActualizarUbicacion (UbicacionModeloDTO ubicacionDTO){
 		super();
		this.ubicacionDTO=ubicacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(ubicacionDTO == null ? null : Entity.json(ubicacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (UbicacionModeloDTO) response.readEntity(UbicacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public UbicacionModeloDTO getResult() {
		return result;
	}

 
  
  	public void setUbicacionDTO (UbicacionModeloDTO ubicacionDTO){
 		this.ubicacionDTO=ubicacionDTO;
 	}
 	
 	public UbicacionModeloDTO getUbicacionDTO (){
 		return ubicacionDTO;
 	}
  
}