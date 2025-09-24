package com.asopagos.afiliaciones.empleadores.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/solicitudAfiliacionEmpleador/{idSolicitudAfiliacionEmpleador}/estado
 */
public class ActualizarEstadoSolicitudAfiliacion extends ServiceClient { 
  	private Long idSolicitudAfiliacionEmpleador;
    	private EstadoSolicitudAfiliacionEmpleadorEnum estado;
  
  
 	public ActualizarEstadoSolicitudAfiliacion (Long idSolicitudAfiliacionEmpleador,EstadoSolicitudAfiliacionEmpleadorEnum estado){
 		super();
		this.idSolicitudAfiliacionEmpleador=idSolicitudAfiliacionEmpleador;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudAfiliacionEmpleador", idSolicitudAfiliacionEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.put(estado == null ? null : Entity.json(estado));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudAfiliacionEmpleador (Long idSolicitudAfiliacionEmpleador){
 		this.idSolicitudAfiliacionEmpleador=idSolicitudAfiliacionEmpleador;
 	}
 	
 	public Long getIdSolicitudAfiliacionEmpleador (){
 		return idSolicitudAfiliacionEmpleador;
 	}
  
  
  	public void setEstado (EstadoSolicitudAfiliacionEmpleadorEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoSolicitudAfiliacionEmpleadorEnum getEstado (){
 		return estado;
 	}
  
}