package com.asopagos.afiliaciones.clients;

import com.asopagos.entidades.ccf.general.Solicitud;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/solicitudes
 */
public class ActualizarSolicitud extends ServiceClient { 
   	private Long idSolicitud;
   	private Solicitud solicitud;
  
  
 	public ActualizarSolicitud (Long idSolicitud,Solicitud solicitud){
 		super();
		this.idSolicitud=idSolicitud;
		this.solicitud=solicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitud == null ? null : Entity.json(solicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setSolicitud (Solicitud solicitud){
 		this.solicitud=solicitud;
 	}
 	
 	public Solicitud getSolicitud (){
 		return solicitud;
 	}
  
}