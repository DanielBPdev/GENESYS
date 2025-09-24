package com.asopagos.fovis.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/{idSolicitudPostulacion}/actualizarSolicitudesGestionCruceASubsanadas
 */
public class ActualizarSolicitudesGestionCruceASubsanadas extends ServiceClient { 
  	private Long idSolicitudPostulacion;
    
  
 	public ActualizarSolicitudesGestionCruceASubsanadas (Long idSolicitudPostulacion){
 		super();
		this.idSolicitudPostulacion=idSolicitudPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudPostulacion", idSolicitudPostulacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudPostulacion (Long idSolicitudPostulacion){
 		this.idSolicitudPostulacion=idSolicitudPostulacion;
 	}
 	
 	public Long getIdSolicitudPostulacion (){
 		return idSolicitudPostulacion;
 	}
  
  
  
}