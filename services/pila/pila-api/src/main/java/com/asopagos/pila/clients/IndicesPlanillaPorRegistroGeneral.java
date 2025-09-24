package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/indicesPlanillaPorRegistroGeneral
 */
public class IndicesPlanillaPorRegistroGeneral extends ServiceClient { 
    	private List<Long> regGenerales;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public IndicesPlanillaPorRegistroGeneral (List<Long> regGenerales){
 		super();
		this.regGenerales=regGenerales;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(regGenerales == null ? null : Entity.json(regGenerales));
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

 
  
  	public void setRegGenerales (List<Long> regGenerales){
 		this.regGenerales=regGenerales;
 	}
 	
 	public List<Long> getRegGenerales (){
 		return regGenerales;
 	}
  
}