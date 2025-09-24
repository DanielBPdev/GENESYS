package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoTransDetaSubsidio;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarArchivoTransDetaSubsidioTodos
 */
public class ConsultarArchivoTransDetaSubsidioTodos extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ArchivoTransDetaSubsidio> result;
  
 	public ConsultarArchivoTransDetaSubsidioTodos (){
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
		this.result = (List<ArchivoTransDetaSubsidio>) response.readEntity(new GenericType<List<ArchivoTransDetaSubsidio>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ArchivoTransDetaSubsidio> getResult() {
		return result;
	}

 
  
}