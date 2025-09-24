package com.asopagos.fovis.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisComposite/cancelarPostulacion/{idSolicitudGlobal}
 */
public class CancelarPostulacion extends ServiceClient {
 
  	private Long idSolicitudGlobal;
  
  
  
 	public CancelarPostulacion (Long idSolicitudGlobal){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
}