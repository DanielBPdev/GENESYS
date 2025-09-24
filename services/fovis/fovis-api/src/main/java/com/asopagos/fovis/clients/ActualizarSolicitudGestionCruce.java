package com.asopagos.fovis.clients;

import com.asopagos.dto.SolicitudGestionCruceDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/actualizarSolicitudGestionCruce
 */
public class ActualizarSolicitudGestionCruce extends ServiceClient { 
    	private SolicitudGestionCruceDTO solicitudGestionCruce;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudGestionCruceDTO result;
  
 	public ActualizarSolicitudGestionCruce (SolicitudGestionCruceDTO solicitudGestionCruce){
 		super();
		this.solicitudGestionCruce=solicitudGestionCruce;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudGestionCruce == null ? null : Entity.json(solicitudGestionCruce));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudGestionCruceDTO) response.readEntity(SolicitudGestionCruceDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudGestionCruceDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudGestionCruce (SolicitudGestionCruceDTO solicitudGestionCruce){
 		this.solicitudGestionCruce=solicitudGestionCruce;
 	}
 	
 	public SolicitudGestionCruceDTO getSolicitudGestionCruce (){
 		return solicitudGestionCruce;
 	}
  
}