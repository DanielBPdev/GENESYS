package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.pila.dto.ArchivosProcesadosFinalizadosOFDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/verArchivosProcesadosFinalizados
 */
public class VerArchivosProcesadosFinalizados extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ArchivosProcesadosFinalizadosOFDTO> result;
  
 	public VerArchivosProcesadosFinalizados (){
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
		this.result = (List<ArchivosProcesadosFinalizadosOFDTO>) response.readEntity(new GenericType<List<ArchivosProcesadosFinalizadosOFDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ArchivosProcesadosFinalizadosOFDTO> getResult() {
		return result;
	}

 
  
}