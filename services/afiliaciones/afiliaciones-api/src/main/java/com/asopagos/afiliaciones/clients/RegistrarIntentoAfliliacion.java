package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/intentosAfiliacion
 */
public class RegistrarIntentoAfliliacion extends ServiceClient { 
    	private IntentoAfiliacionInDTO intentoAfiliacionInDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarIntentoAfliliacion (IntentoAfiliacionInDTO intentoAfiliacionInDTO){
 		super();
		this.intentoAfiliacionInDTO=intentoAfiliacionInDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(intentoAfiliacionInDTO == null ? null : Entity.json(intentoAfiliacionInDTO));
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

 
  
  	public void setIntentoAfiliacionInDTO (IntentoAfiliacionInDTO intentoAfiliacionInDTO){
 		this.intentoAfiliacionInDTO=intentoAfiliacionInDTO;
 	}
 	
 	public IntentoAfiliacionInDTO getIntentoAfiliacionInDTO (){
 		return intentoAfiliacionInDTO;
 	}
  
}