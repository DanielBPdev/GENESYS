package com.asopagos.afiliaciones.personas.clients;

import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/obtenerAfiliacionesSinInstanciaProceso
 */
public class ObtenerAfiliacionesSinInstanciaProceso extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudAfiliacionPersonaDTO> result;
  
 	public ObtenerAfiliacionesSinInstanciaProceso (){
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
		this.result = (List<SolicitudAfiliacionPersonaDTO>) response.readEntity(new GenericType<List<SolicitudAfiliacionPersonaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudAfiliacionPersonaDTO> getResult() {
		return result;
	}

 
  
}