package com.asopagos.pila.clients;

import com.asopagos.pila.dto.ConsultasArchivosOperadorFinancieroDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultasArchivosOperadorFinanciero
 */
public class ConsultasArchivosOperadorFinanciero extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConsultasArchivosOperadorFinancieroDTO result;
  
 	public ConsultasArchivosOperadorFinanciero (){
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
		this.result = (ConsultasArchivosOperadorFinancieroDTO) response.readEntity(ConsultasArchivosOperadorFinancieroDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConsultasArchivosOperadorFinancieroDTO getResult() {
		return result;
	}

 
  
}