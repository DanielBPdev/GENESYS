package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.SolicitudModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarSolicitudGlobalCartera
 */
public class GuardarSolicitudGlobalCartera extends ServiceClient { 
    	private SolicitudModeloDTO solicitudDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudModeloDTO result;
  
 	public GuardarSolicitudGlobalCartera (SolicitudModeloDTO solicitudDTO){
 		super();
		this.solicitudDTO=solicitudDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudDTO == null ? null : Entity.json(solicitudDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudModeloDTO) response.readEntity(SolicitudModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudDTO (SolicitudModeloDTO solicitudDTO){
 		this.solicitudDTO=solicitudDTO;
 	}
 	
 	public SolicitudModeloDTO getSolicitudDTO (){
 		return solicitudDTO;
 	}
  
}