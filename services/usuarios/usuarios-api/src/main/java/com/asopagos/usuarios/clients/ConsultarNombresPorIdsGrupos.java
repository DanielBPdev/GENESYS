package com.asopagos.usuarios.clients;

import java.util.List;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/grupos/nombres
 */
public class ConsultarNombresPorIdsGrupos extends ServiceClient { 
    	private List<String> ids;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public ConsultarNombresPorIdsGrupos (List<String> ids){
 		super();
		this.ids=ids;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(ids == null ? null : Entity.json(ids));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}

 
  
  	public void setIds (List<String> ids){
 		this.ids=ids;
 	}
 	
 	public List<String> getIds (){
 		return ids;
 	}
  
}