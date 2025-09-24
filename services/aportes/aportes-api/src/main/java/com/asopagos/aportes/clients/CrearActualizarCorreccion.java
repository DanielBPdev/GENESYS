package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.CorreccionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearActualizarCorreccion
 */
public class CrearActualizarCorreccion extends ServiceClient { 
    	private CorreccionModeloDTO correccionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarCorreccion (CorreccionModeloDTO correccionDTO){
 		super();
		this.correccionDTO=correccionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(correccionDTO == null ? null : Entity.json(correccionDTO));
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

 
  
  	public void setCorreccionDTO (CorreccionModeloDTO correccionDTO){
 		this.correccionDTO=correccionDTO;
 	}
 	
 	public CorreccionModeloDTO getCorreccionDTO (){
 		return correccionDTO;
 	}
  
}