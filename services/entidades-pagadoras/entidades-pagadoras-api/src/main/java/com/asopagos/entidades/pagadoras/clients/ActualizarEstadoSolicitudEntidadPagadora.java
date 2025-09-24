package com.asopagos.entidades.pagadoras.clients;

import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadesPagadoras/actualizarEstadoSolicitudEntidadPagadora
 */
public class ActualizarEstadoSolicitudEntidadPagadora extends ServiceClient {
 
  
  
  
 	public ActualizarEstadoSolicitudEntidadPagadora (Long idSolicitudGlobal,EstadoSolicitudPersonaEntidadPagadoraEnum estado){
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
	}
	

 
  
}