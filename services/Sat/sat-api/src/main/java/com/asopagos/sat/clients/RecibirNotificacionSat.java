package com.asopagos.sat.clients;

import com.asopagos.sat.dto.RespuestaNotificacionSatDTO;
import com.asopagos.sat.dto.NotificacionSatDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/sat/recibirNotificacionSat
 */
public class RecibirNotificacionSat extends ServiceClient { 
    	private NotificacionSatDTO notificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaNotificacionSatDTO result;
  
 	public RecibirNotificacionSat (NotificacionSatDTO notificacion){
 		super();
		this.notificacion=notificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(notificacion == null ? null : Entity.json(notificacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RespuestaNotificacionSatDTO) response.readEntity(RespuestaNotificacionSatDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RespuestaNotificacionSatDTO getResult() {
		return result;
	}

 
  
  	public void setNotificacion (NotificacionSatDTO notificacion){
 		this.notificacion=notificacion;
 	}
 	
 	public NotificacionSatDTO getNotificacion (){
 		return notificacion;
 	}
  
}