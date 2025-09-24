package com.asopagos.asignaciones.clients;

import java.util.List;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/asignacionSolicitud/asignaciones
 */
public class ActualizarMetodoAsignacion extends ServiceClient { 
    	private List<ParametrizacionMetodoAsignacion> parametrizaciones;
  
  
 	public ActualizarMetodoAsignacion (List<ParametrizacionMetodoAsignacion> parametrizaciones){
 		super();
		this.parametrizaciones=parametrizaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizaciones == null ? null : Entity.json(parametrizaciones));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizaciones (List<ParametrizacionMetodoAsignacion> parametrizaciones){
 		this.parametrizaciones=parametrizaciones;
 	}
 	
 	public List<ParametrizacionMetodoAsignacion> getParametrizaciones (){
 		return parametrizaciones;
 	}
  
}