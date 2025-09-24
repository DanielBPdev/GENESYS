package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearRegistroGeneral
 */
public class CrearRegistroGeneral extends ServiceClient { 
    	private RegistroGeneralModeloDTO registroGeneralDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearRegistroGeneral (RegistroGeneralModeloDTO registroGeneralDTO){
 		super();
		this.registroGeneralDTO=registroGeneralDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroGeneralDTO == null ? null : Entity.json(registroGeneralDTO));
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

 
  
  	public void setRegistroGeneralDTO (RegistroGeneralModeloDTO registroGeneralDTO){
 		this.registroGeneralDTO=registroGeneralDTO;
 	}
 	
 	public RegistroGeneralModeloDTO getRegistroGeneralDTO (){
 		return registroGeneralDTO;
 	}
  
}