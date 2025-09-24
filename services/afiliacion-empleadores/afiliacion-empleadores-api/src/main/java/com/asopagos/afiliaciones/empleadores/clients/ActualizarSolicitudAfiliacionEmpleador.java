package com.asopagos.afiliaciones.empleadores.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/solicitudAfiliacionEmpleador/{idSolicitudAfiliacionEmpleador}
 */
public class ActualizarSolicitudAfiliacionEmpleador extends ServiceClient { 
  	private Long idSolicitudAfiliacionEmpleador;
    	private SolicitudAfiliacionEmpleador solAfiliacionEmpleador;
  
  
 	public ActualizarSolicitudAfiliacionEmpleador (Long idSolicitudAfiliacionEmpleador,SolicitudAfiliacionEmpleador solAfiliacionEmpleador){
 		super();
		this.idSolicitudAfiliacionEmpleador=idSolicitudAfiliacionEmpleador;
		this.solAfiliacionEmpleador=solAfiliacionEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudAfiliacionEmpleador", idSolicitudAfiliacionEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.put(solAfiliacionEmpleador == null ? null : Entity.json(solAfiliacionEmpleador));
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
  
  
  	public void setSolAfiliacionEmpleador (SolicitudAfiliacionEmpleador solAfiliacionEmpleador){
 		this.solAfiliacionEmpleador=solAfiliacionEmpleador;
 	}
 	
 	public SolicitudAfiliacionEmpleador getSolAfiliacionEmpleador (){
 		return solAfiliacionEmpleador;
 	}
  
}