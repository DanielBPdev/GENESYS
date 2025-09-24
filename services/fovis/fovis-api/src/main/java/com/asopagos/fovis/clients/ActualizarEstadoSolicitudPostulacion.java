package com.asopagos.fovis.clients;

import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/{idSolicitudGlobal}/estadoSolicitud
 */
public class ActualizarEstadoSolicitudPostulacion extends ServiceClient { 
  	private Long idSolicitudGlobal;
   	private EstadoSolicitudPostulacionEnum estadoSolicitud;
   
  
 	public ActualizarEstadoSolicitudPostulacion (Long idSolicitudGlobal,EstadoSolicitudPostulacionEnum estadoSolicitud){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.estadoSolicitud=estadoSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
			.queryParam("estadoSolicitud", estadoSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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
  
  	public void setEstadoSolicitud (EstadoSolicitudPostulacionEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudPostulacionEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  
  
}