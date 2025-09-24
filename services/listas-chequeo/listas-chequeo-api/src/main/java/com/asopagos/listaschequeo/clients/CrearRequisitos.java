package com.asopagos.listaschequeo.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.listaschequeo.dto.RequisitoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/requisitos
 */
public class CrearRequisitos extends ServiceClient { 
    	private List<RequisitoDTO> requisitos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public CrearRequisitos (List<RequisitoDTO> requisitos){
 		super();
		this.requisitos=requisitos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(requisitos == null ? null : Entity.json(requisitos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 
  
  	public void setRequisitos (List<RequisitoDTO> requisitos){
 		this.requisitos=requisitos;
 	}
 	
 	public List<RequisitoDTO> getRequisitos (){
 		return requisitos;
 	}
  
}