package com.asopagos.asignaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/asignacionSolicitud/parametrizacionesMetodos
 */
public class ConsultarParametrizacionMetodoAsignacion extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ParametrizacionMetodoAsignacion> result;
  
 	public ConsultarParametrizacionMetodoAsignacion (){
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
		this.result = (List<ParametrizacionMetodoAsignacion>) response.readEntity(new GenericType<List<ParametrizacionMetodoAsignacion>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ParametrizacionMetodoAsignacion> getResult() {
		return result;
	}

 
  
}