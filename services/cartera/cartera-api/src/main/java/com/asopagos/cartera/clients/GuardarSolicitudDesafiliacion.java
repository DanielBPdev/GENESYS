package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.SolicitudDesafiliacionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarSolicitudDesafiliacion
 */
public class GuardarSolicitudDesafiliacion extends ServiceClient { 
    	private SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudDesafiliacionModeloDTO result;
  
 	public GuardarSolicitudDesafiliacion (SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO){
 		super();
		this.solicitudDesafiliacionModeloDTO=solicitudDesafiliacionModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudDesafiliacionModeloDTO == null ? null : Entity.json(solicitudDesafiliacionModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudDesafiliacionModeloDTO) response.readEntity(SolicitudDesafiliacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudDesafiliacionModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudDesafiliacionModeloDTO (SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO){
 		this.solicitudDesafiliacionModeloDTO=solicitudDesafiliacionModeloDTO;
 	}
 	
 	public SolicitudDesafiliacionModeloDTO getSolicitudDesafiliacionModeloDTO (){
 		return solicitudDesafiliacionModeloDTO;
 	}
  
}