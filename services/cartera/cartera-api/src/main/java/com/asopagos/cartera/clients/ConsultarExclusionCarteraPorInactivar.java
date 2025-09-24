package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.dto.cartera.ExclusionCarteraDTO;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarExclusionCarteraPorInactivar
 */
public class ConsultarExclusionCarteraPorInactivar extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ExclusionCarteraDTO> result;
  
 	public ConsultarExclusionCarteraPorInactivar (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ExclusionCarteraDTO>) response.readEntity(new GenericType<List<ExclusionCarteraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ExclusionCarteraDTO> getResult() {
		return result;
	}

 
  
}