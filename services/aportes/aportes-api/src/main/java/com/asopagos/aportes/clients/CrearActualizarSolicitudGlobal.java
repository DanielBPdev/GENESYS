package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearActualizarSolicitudGlobal
 */
public class CrearActualizarSolicitudGlobal extends ServiceClient { 
    	private SolicitudModeloDTO solicitudDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarSolicitudGlobal (SolicitudModeloDTO solicitudDTO){
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
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setSolicitudDTO (SolicitudModeloDTO solicitudDTO){
 		this.solicitudDTO=solicitudDTO;
 	}
 	
 	public SolicitudModeloDTO getSolicitudDTO (){
 		return solicitudDTO;
 	}
  
}