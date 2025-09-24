package com.asopagos.solicitudes.clients;

import com.asopagos.dto.EscalamientoSolicitudDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/{idSolicitud}/registrarEscalamientoSolicitud
 */
public class RegistrarEscalamientoSolicitud extends ServiceClient { 
  	private Long idSolicitud;
    	private EscalamientoSolicitudDTO escalamientoSolicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EscalamientoSolicitudDTO result;
  
 	public RegistrarEscalamientoSolicitud (Long idSolicitud,EscalamientoSolicitudDTO escalamientoSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
		this.escalamientoSolicitud=escalamientoSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(escalamientoSolicitud == null ? null : Entity.json(escalamientoSolicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (EscalamientoSolicitudDTO) response.readEntity(EscalamientoSolicitudDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public EscalamientoSolicitudDTO getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
  	public void setEscalamientoSolicitud (EscalamientoSolicitudDTO escalamientoSolicitud){
 		this.escalamientoSolicitud=escalamientoSolicitud;
 	}
 	
 	public EscalamientoSolicitudDTO getEscalamientoSolicitud (){
 		return escalamientoSolicitud;
 	}
  
}