package com.asopagos.solicitud.composite.clients;

import com.asopagos.solicitud.composite.dto.DatosSeguimientoSolicitudesDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudComposite/consultarSolicitudesGeneralesEnProceso
 */
public class ConsultarSolicitudesGeneralesEnProceso extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosSeguimientoSolicitudesDTO result;
  
 	public ConsultarSolicitudesGeneralesEnProceso (){
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
		this.result = (DatosSeguimientoSolicitudesDTO) response.readEntity(DatosSeguimientoSolicitudesDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatosSeguimientoSolicitudesDTO getResult() {
		return result;
	}

 
  
}