package com.asopagos.fovis.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/{idSolicitudGlobal}/estadoSolicitudVerificacion
 */
public class ActualizarEstadoSolicitudVerificacionFovis extends ServiceClient { 
  	private Long idSolicitudGlobal;
   	private EstadoSolicitudVerificacionFovisEnum estadoSolicitud;
   
  
 	public ActualizarEstadoSolicitudVerificacionFovis (Long idSolicitudGlobal,EstadoSolicitudVerificacionFovisEnum estadoSolicitud){
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
  
  	public void setEstadoSolicitud (EstadoSolicitudVerificacionFovisEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudVerificacionFovisEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  
  
}