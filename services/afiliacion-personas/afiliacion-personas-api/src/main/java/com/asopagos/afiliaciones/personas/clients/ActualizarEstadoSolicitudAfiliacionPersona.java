package com.asopagos.afiliaciones.personas.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliaciones/solicitudAfiliacionPersona/{idSolicitudGlobal}/estado
 */
public class ActualizarEstadoSolicitudAfiliacionPersona extends ServiceClient { 
  	private Long idSolicitudGlobal;
    	private EstadoSolicitudAfiliacionPersonaEnum estado;
  
  
 	public ActualizarEstadoSolicitudAfiliacionPersona (Long idSolicitudGlobal,EstadoSolicitudAfiliacionPersonaEnum estado){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.put(estado == null ? null : Entity.json(estado));
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
  
  
  	public void setEstado (EstadoSolicitudAfiliacionPersonaEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoSolicitudAfiliacionPersonaEnum getEstado (){
 		return estado;
 	}
  
}