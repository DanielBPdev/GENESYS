package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/enviarSolicitudCambioIdenPila
 */
public class EnviarSolicitudCambioIdenPila extends ServiceClient { 
   	private Long numeroIdentificacion;
   	private InconsistenciaDTO inconsistencias;
  
  
 	public EnviarSolicitudCambioIdenPila (Long numeroIdentificacion,InconsistenciaDTO inconsistencias){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.inconsistencias=inconsistencias;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(inconsistencias == null ? null : Entity.json(inconsistencias));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroIdentificacion (Long numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public Long getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  
  	public void setInconsistencias (InconsistenciaDTO inconsistencias){
 		this.inconsistencias=inconsistencias;
 	}
 	
 	public InconsistenciaDTO getInconsistencias (){
 		return inconsistencias;
 	}
  
}