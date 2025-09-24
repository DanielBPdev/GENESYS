package com.asopagos.aportes.clients;

import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/actualizarEstadoSolicitudCorreccion/{idSolicitudGlobal}
 */
public class ActualizarEstadoSolicitudCorreccion extends ServiceClient { 
  	private Long idSolicitudGlobal;
   	private EstadoSolicitudAporteEnum estadoSolicitud;
   
  
 	public ActualizarEstadoSolicitudCorreccion (Long idSolicitudGlobal,EstadoSolicitudAporteEnum estadoSolicitud){
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
  
  	public void setEstadoSolicitud (EstadoSolicitudAporteEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudAporteEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  
  
}