package com.asopagos.novedades.fovis.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/{idSolicitudGlobal}/estadoSolicitud
 */
public class ActualizarEstadoSolicitudNovedadFovis extends ServiceClient { 
  	private Long idSolicitudGlobal;
   	private EstadoSolicitudNovedadFovisEnum estadoSolicitud;
   
  
 	public ActualizarEstadoSolicitudNovedadFovis (Long idSolicitudGlobal,EstadoSolicitudNovedadFovisEnum estadoSolicitud){
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
  
  	public void setEstadoSolicitud (EstadoSolicitudNovedadFovisEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudNovedadFovisEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  
  
}