package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionModalidadModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarParametrizacionModalidades
 */
public class ConsultarParametrizacionModalidades extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ParametrizacionModalidadModeloDTO> result;
  
 	public ConsultarParametrizacionModalidades (){
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
		this.result = (List<ParametrizacionModalidadModeloDTO>) response.readEntity(new GenericType<List<ParametrizacionModalidadModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ParametrizacionModalidadModeloDTO> getResult() {
		return result;
	}

 
  
}