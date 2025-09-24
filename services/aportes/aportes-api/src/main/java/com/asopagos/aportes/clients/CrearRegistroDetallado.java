package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearRegistroDetallado
 */
public class CrearRegistroDetallado extends ServiceClient { 
    	private RegistroDetalladoModeloDTO registroDetalladoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearRegistroDetallado (RegistroDetalladoModeloDTO registroDetalladoDTO){
 		super();
		this.registroDetalladoDTO=registroDetalladoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroDetalladoDTO == null ? null : Entity.json(registroDetalladoDTO));
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

 
  
  	public void setRegistroDetalladoDTO (RegistroDetalladoModeloDTO registroDetalladoDTO){
 		this.registroDetalladoDTO=registroDetalladoDTO;
 	}
 	
 	public RegistroDetalladoModeloDTO getRegistroDetalladoDTO (){
 		return registroDetalladoDTO;
 	}
  
}