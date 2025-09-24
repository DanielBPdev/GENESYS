package com.asopagos.solicitudes.clients;

import com.asopagos.dto.EscalamientoSolicitudDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/solicitudes/{idSolicitud}/escalamientoSolicitud
 */
public class ActualizarSolicitudEscalada extends ServiceClient { 
  	private Long idSolicitud;
    	private EscalamientoSolicitudDTO escalamientoSolAfilEmpleador;
  
  
 	public ActualizarSolicitudEscalada (Long idSolicitud,EscalamientoSolicitudDTO escalamientoSolAfilEmpleador){
 		super();
		this.idSolicitud=idSolicitud;
		this.escalamientoSolAfilEmpleador=escalamientoSolAfilEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.put(escalamientoSolAfilEmpleador == null ? null : Entity.json(escalamientoSolAfilEmpleador));
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
  
  
  	public void setEscalamientoSolAfilEmpleador (EscalamientoSolicitudDTO escalamientoSolAfilEmpleador){
 		this.escalamientoSolAfilEmpleador=escalamientoSolAfilEmpleador;
 	}
 	
 	public EscalamientoSolicitudDTO getEscalamientoSolAfilEmpleador (){
 		return escalamientoSolAfilEmpleador;
 	}
  
}