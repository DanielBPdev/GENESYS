package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.SolicitudAfiliacionRolDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesEspecialesComposite/ejecutarActualizacionSolicitud
 */
public class EjecutarActualizacionSolicitud extends ServiceClient { 
    	private SolicitudAfiliacionRolDTO solicitudAfiliacionRolDTO;
  
  
 	public EjecutarActualizacionSolicitud (SolicitudAfiliacionRolDTO solicitudAfiliacionRolDTO){
 		super();
		this.solicitudAfiliacionRolDTO=solicitudAfiliacionRolDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAfiliacionRolDTO == null ? null : Entity.json(solicitudAfiliacionRolDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolicitudAfiliacionRolDTO (SolicitudAfiliacionRolDTO solicitudAfiliacionRolDTO){
 		this.solicitudAfiliacionRolDTO=solicitudAfiliacionRolDTO;
 	}
 	
 	public SolicitudAfiliacionRolDTO getSolicitudAfiliacionRolDTO (){
 		return solicitudAfiliacionRolDTO;
 	}
  
}