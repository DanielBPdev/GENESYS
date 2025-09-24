package com.asopagos.tareashumanas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.tareashumanas.dto.TareaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/tareasHumanas/obtenerTareasCreadasSinPropietario
 */
public class ObtenerTareasCreadasSinPropietario extends ServiceClient { 
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TareaDTO> result;
  
 	public ObtenerTareasCreadasSinPropietario (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<TareaDTO>) response.readEntity(new GenericType<List<TareaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<TareaDTO> getResult() {
		return result;
	}

 
  
  
}