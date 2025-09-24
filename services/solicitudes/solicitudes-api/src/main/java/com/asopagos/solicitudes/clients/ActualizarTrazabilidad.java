package com.asopagos.solicitudes.clients;

import com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/actualizarTrazabilidad
 */
public class ActualizarTrazabilidad extends ServiceClient { 
    	private RegistroEstadoAporteModeloDTO registroEstadoAporteModeloDTO;
  
  
 	public ActualizarTrazabilidad (RegistroEstadoAporteModeloDTO registroEstadoAporteModeloDTO){
 		super();
		this.registroEstadoAporteModeloDTO=registroEstadoAporteModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroEstadoAporteModeloDTO == null ? null : Entity.json(registroEstadoAporteModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRegistroEstadoAporteModeloDTO (RegistroEstadoAporteModeloDTO registroEstadoAporteModeloDTO){
 		this.registroEstadoAporteModeloDTO=registroEstadoAporteModeloDTO;
 	}
 	
 	public RegistroEstadoAporteModeloDTO getRegistroEstadoAporteModeloDTO (){
 		return registroEstadoAporteModeloDTO;
 	}
  
}