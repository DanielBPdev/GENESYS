package com.asopagos.personas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/consultarNombreUsuarioPersonas
 */
public class ConsultarNombreUsuarioPersonas extends ServiceClient { 
    	private List<Long> idPersonas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<String> result;
  
 	public ConsultarNombreUsuarioPersonas (List<Long> idPersonas){
 		super();
		this.idPersonas=idPersonas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idPersonas == null ? null : Entity.json(idPersonas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<String>) response.readEntity(new GenericType<List<String>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<String> getResult() {
		return result;
	}

 
  
  	public void setIdPersonas (List<Long> idPersonas){
 		this.idPersonas=idPersonas;
 	}
 	
 	public List<Long> getIdPersonas (){
 		return idPersonas;
 	}
  
}