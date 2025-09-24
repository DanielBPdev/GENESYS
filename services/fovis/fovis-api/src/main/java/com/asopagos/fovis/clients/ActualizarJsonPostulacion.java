package com.asopagos.fovis.clients;

import java.lang.Long;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/actualizarJsonPostulacion
 */
public class ActualizarJsonPostulacion extends ServiceClient { 
   	private Long idPostulacion;
   	private SolicitudPostulacionFOVISDTO solicitudPostulacion;
  
  
 	public ActualizarJsonPostulacion (Long idPostulacion,SolicitudPostulacionFOVISDTO solicitudPostulacion){
 		super();
		this.idPostulacion=idPostulacion;
		this.solicitudPostulacion=solicitudPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPostulacion", idPostulacion)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudPostulacion == null ? null : Entity.json(solicitudPostulacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
  	public void setSolicitudPostulacion (SolicitudPostulacionFOVISDTO solicitudPostulacion){
 		this.solicitudPostulacion=solicitudPostulacion;
 	}
 	
 	public SolicitudPostulacionFOVISDTO getSolicitudPostulacion (){
 		return solicitudPostulacion;
 	}
  
}