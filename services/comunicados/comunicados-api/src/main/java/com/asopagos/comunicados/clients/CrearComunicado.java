package com.asopagos.comunicados.clients;

import com.asopagos.entidades.ccf.comunicados.Comunicado;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados
 */
public class CrearComunicado extends ServiceClient { 
    	private Comunicado comunicado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearComunicado (Comunicado comunicado){
 		super();
		this.comunicado=comunicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(comunicado == null ? null : Entity.json(comunicado));
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

 
  
  	public void setComunicado (Comunicado comunicado){
 		this.comunicado=comunicado;
 	}
 	
 	public Comunicado getComunicado (){
 		return comunicado;
 	}
  
}