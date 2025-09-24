package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.pila.dto.ProcesoPilaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivosPILA/consultarProcesoValidacionActivo
 */
public class ConsultarProcesosValidacionActivos extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ProcesoPilaDTO> result;
  
 	public ConsultarProcesosValidacionActivos (){
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
		this.result = (List<ProcesoPilaDTO>) response.readEntity(new GenericType<List<ProcesoPilaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ProcesoPilaDTO> getResult() {
		return result;
	}

 
  
}