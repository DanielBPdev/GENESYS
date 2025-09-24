package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.EmpleadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/afiliados
 */
public class GenerarReporteDeEmpleadoresAfiliados extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<EmpleadorDTO> result;
  
 	public GenerarReporteDeEmpleadoresAfiliados (){
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
		this.result = (List<EmpleadorDTO>) response.readEntity(new GenericType<List<EmpleadorDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<EmpleadorDTO> getResult() {
		return result;
	}

 
  
}