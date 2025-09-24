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
 * /rest/grupos/identificadores
 */
public class ConsultarIdsPorNombresGrupos extends ServiceClient { 
    	private List<String> nombreGrupos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public ConsultarIdsPorNombresGrupos (List<String> nombreGrupos){
 		super();
		this.nombreGrupos=nombreGrupos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(nombreGrupos == null ? null : Entity.json(nombreGrupos));
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

 
  
  	public void setNombreGrupos (List<String> nombreGrupos){
 		this.nombreGrupos=nombreGrupos;
 	}
 	
 	public List<String> getNombreGrupos (){
 		return nombreGrupos;
 	}
  
}