package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.fovis.dto.PostulanteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarPostulantes
 */
public class ConsultarPostulantes extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PostulanteDTO> result;
  
 	public ConsultarPostulantes (){
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
		this.result = (List<PostulanteDTO>) response.readEntity(new GenericType<List<PostulanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PostulanteDTO> getResult() {
		return result;
	}

 
  
}