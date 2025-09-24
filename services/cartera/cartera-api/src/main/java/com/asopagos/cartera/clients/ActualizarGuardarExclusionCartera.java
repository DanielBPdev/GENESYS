package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.ExclusionCarteraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/actualizarGuardarExclusionCartera
 */
public class ActualizarGuardarExclusionCartera extends ServiceClient { 
    	private ExclusionCarteraDTO exclusionCarteraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ExclusionCarteraDTO result;
  
 	public ActualizarGuardarExclusionCartera (ExclusionCarteraDTO exclusionCarteraDTO){
 		super();
		this.exclusionCarteraDTO=exclusionCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(exclusionCarteraDTO == null ? null : Entity.json(exclusionCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ExclusionCarteraDTO) response.readEntity(ExclusionCarteraDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ExclusionCarteraDTO getResult() {
		return result;
	}

 
  
  	public void setExclusionCarteraDTO (ExclusionCarteraDTO exclusionCarteraDTO){
 		this.exclusionCarteraDTO=exclusionCarteraDTO;
 	}
 	
 	public ExclusionCarteraDTO getExclusionCarteraDTO (){
 		return exclusionCarteraDTO;
 	}
  
}