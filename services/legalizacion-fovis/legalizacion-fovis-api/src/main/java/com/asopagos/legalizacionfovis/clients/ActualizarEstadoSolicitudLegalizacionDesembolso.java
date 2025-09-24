package com.asopagos.legalizacionfovis.clients;

import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/{idSolicitudGlobal}/estadoSolicitud
 */
public class ActualizarEstadoSolicitudLegalizacionDesembolso extends ServiceClient { 
  	private Long idSolicitudGlobal;
   	private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud;
   
  
 	public ActualizarEstadoSolicitudLegalizacionDesembolso (Long idSolicitudGlobal,EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud){
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
  
  	public void setEstadoSolicitud (EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  
  
}