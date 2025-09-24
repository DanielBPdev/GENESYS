package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.dto.ParametrizacionCondicionesSubsidioCajaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/gestionarCondiciones
 */
public class GestionarCondiciones extends ServiceClient { 
    	private ParametrizacionCondicionesSubsidioCajaDTO condiciones;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public GestionarCondiciones (ParametrizacionCondicionesSubsidioCajaDTO condiciones){
 		super();
		this.condiciones=condiciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(condiciones == null ? null : Entity.json(condiciones));
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

 
  
  	public void setCondiciones (ParametrizacionCondicionesSubsidioCajaDTO condiciones){
 		this.condiciones=condiciones;
 	}
 	
 	public ParametrizacionCondicionesSubsidioCajaDTO getCondiciones (){
 		return condiciones;
 	}
  
}