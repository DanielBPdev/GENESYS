package com.asopagos.entidades.pagadoras.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidades.pagadoras.dto.ConsultarEntidadPagadoraOutDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadesPagadoras
 */
public class ConsultarTodasEntidadesPagadoras extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsultarEntidadPagadoraOutDTO> result;
  
 	public ConsultarTodasEntidadesPagadoras (){
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
		this.result = (List<ConsultarEntidadPagadoraOutDTO>) response.readEntity(new GenericType<List<ConsultarEntidadPagadoraOutDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ConsultarEntidadPagadoraOutDTO> getResult() {
		return result;
	}

 
  
}