package com.asopagos.fovis.clients;

import com.asopagos.dto.fovis.IntentoPostulacionDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/registrarIntentoPostulacionFOVIS
 */
public class RegistrarIntentoPostulacionFOVIS extends ServiceClient { 
    	private IntentoPostulacionDTO intentoPostulacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarIntentoPostulacionFOVIS (IntentoPostulacionDTO intentoPostulacionDTO){
 		super();
		this.intentoPostulacionDTO=intentoPostulacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(intentoPostulacionDTO == null ? null : Entity.json(intentoPostulacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setIntentoPostulacionDTO (IntentoPostulacionDTO intentoPostulacionDTO){
 		this.intentoPostulacionDTO=intentoPostulacionDTO;
 	}
 	
 	public IntentoPostulacionDTO getIntentoPostulacionDTO (){
 		return intentoPostulacionDTO;
 	}
  
}