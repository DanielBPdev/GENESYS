package com.asopagos.novedades.fovis.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/actualizarEstadoSolicitudAnalisisNovedadFOVIS
 */
public class ActualizarEstadoSolicitudAnalisisNovedadFOVIS extends ServiceClient { 
   	private EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud;
  	private Long idSolicitudGlobal;
   
  
 	public ActualizarEstadoSolicitudAnalisisNovedadFOVIS (EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud,Long idSolicitudGlobal){
 		super();
		this.estadoSolicitud=estadoSolicitud;
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("estadoSolicitud", estadoSolicitud)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setEstadoSolicitud (EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudAnalisisNovedadFovisEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
}