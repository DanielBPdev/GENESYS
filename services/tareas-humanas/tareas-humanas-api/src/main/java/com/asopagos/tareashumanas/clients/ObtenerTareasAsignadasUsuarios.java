package com.asopagos.tareashumanas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.tareashumanas.dto.TareaDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tareasHumanas/asignadas/usuarios
 */
public class ObtenerTareasAsignadasUsuarios extends ServiceClient {
 
  
  	private List<String> taskOwner;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TareaDTO> result;
  
 	public ObtenerTareasAsignadasUsuarios (List<String> taskOwner){
 		super();
		this.taskOwner=taskOwner;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("taskOwner", taskOwner.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TareaDTO>) response.readEntity(new GenericType<List<TareaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TareaDTO> getResult() {
		return result;
	}

 
  	public void setTaskOwner (List<String> taskOwner){
 		this.taskOwner=taskOwner;
 	}
 	
 	public List<String> getTaskOwner (){
 		return taskOwner;
 	}
  
}