package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.afiliaciones.dto.ActualizacionEstadoListaEspecialDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/solicitudAfiliacionEmpleador/cambiarEstadoEmpleadorRegistroListaEspecial
 */
public class CambiarEstadoEmpleadorRegistroLista extends ServiceClient { 
    	private ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO;
  
  
 	public CambiarEstadoEmpleadorRegistroLista (ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO){
 		super();
		this.actualizacionEstadoListaEspecialDTO=actualizacionEstadoListaEspecialDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(actualizacionEstadoListaEspecialDTO == null ? null : Entity.json(actualizacionEstadoListaEspecialDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setActualizacionEstadoListaEspecialDTO (ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO){
 		this.actualizacionEstadoListaEspecialDTO=actualizacionEstadoListaEspecialDTO;
 	}
 	
 	public ActualizacionEstadoListaEspecialDTO getActualizacionEstadoListaEspecialDTO (){
 		return actualizacionEstadoListaEspecialDTO;
 	}
  
}