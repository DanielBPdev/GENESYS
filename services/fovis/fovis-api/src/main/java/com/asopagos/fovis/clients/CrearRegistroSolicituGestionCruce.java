package com.asopagos.fovis.clients;

import com.asopagos.dto.SolicitudGestionCruceDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/crearRegistroSolicituGestionCruce
 */
public class CrearRegistroSolicituGestionCruce extends ServiceClient { 
    	private SolicitudGestionCruceDTO solicitudGestionCruceDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudGestionCruceDTO result;
  
 	public CrearRegistroSolicituGestionCruce (SolicitudGestionCruceDTO solicitudGestionCruceDTO){
 		super();
		this.solicitudGestionCruceDTO=solicitudGestionCruceDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudGestionCruceDTO == null ? null : Entity.json(solicitudGestionCruceDTO));
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

 
  
  	public void setSolicitudGestionCruceDTO (SolicitudGestionCruceDTO solicitudGestionCruceDTO){
 		this.solicitudGestionCruceDTO=solicitudGestionCruceDTO;
 	}
 	
 	public SolicitudGestionCruceDTO getSolicitudGestionCruceDTO (){
 		return solicitudGestionCruceDTO;
 	}
  
}