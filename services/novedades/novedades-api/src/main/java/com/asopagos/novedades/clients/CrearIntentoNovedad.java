package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.novedades.dto.IntentoNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/intentoNovedad
 */
public class CrearIntentoNovedad extends ServiceClient { 
    	private IntentoNovedadDTO intentoNovedadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearIntentoNovedad (IntentoNovedadDTO intentoNovedadDTO){
 		super();
		this.intentoNovedadDTO=intentoNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(intentoNovedadDTO == null ? null : Entity.json(intentoNovedadDTO));
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

 
  
  	public void setIntentoNovedadDTO (IntentoNovedadDTO intentoNovedadDTO){
 		this.intentoNovedadDTO=intentoNovedadDTO;
 	}
 	
 	public IntentoNovedadDTO getIntentoNovedadDTO (){
 		return intentoNovedadDTO;
 	}
  
}